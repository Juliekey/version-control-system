package com.pedash.services;

import com.pedash.dao.LastUpdateDao;
import com.pedash.dao.LocalDocDao;
import com.pedash.dao.RemoteDocDao;
import com.pedash.dao.RevisionHistoryDao;
import com.pedash.diff.Diff;
import com.pedash.entities.LocalDoc;
import com.pedash.entities.RemoteDoc;
import com.pedash.entities.Revision;
import com.pedash.entities.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Yuliya Pedash on 21.06.2017.
 */
@Service
public class DocumentsService {
    @Resource
    RemoteDocDao remoteDocDao;
    @Resource
    LocalDocDao localDocDao;
    @Resource
    LastUpdateDao lastUpdateDao;
    @Resource
    RevisionHistoryDao revisionHistoryDao;

    @Transactional
    public void cloneRep(Integer userId) {
        List<RemoteDoc> remoteDocDaoList = remoteDocDao.getAllDocuments();
        lastUpdateDao.insertLastUpdateOfUser(userId);
        localDocDao.deleteAllFromLocalRepByUser(userId);
        localDocDao.addAllRemoteDocsToLoacalRepOfUser(remoteDocDaoList, userId);
    }

    public boolean hasUserClonedRep(Integer userId) {
        return (lastUpdateDao.getLastUpdate(userId) == null) ? false : true;
    }


    public boolean updateDoc(Integer docId, String content) {
        localDocDao.updateStatusToDocUpdatedIfDocNotAdded(docId);
        return localDocDao.updateContent(docId, content);
    }

    public boolean createDoc(String docName, Integer userId) {
        Integer newId = localDocDao.createDoc(docName, userId);
        return newId == null ? false :
                localDocDao.updateStatusForLocalDoc(newId, Status.DocAdded);

    }

    public boolean deleteDoc(Integer docId) {
        return localDocDao.updateStatusForLocalDoc(docId, Status.DocDeleted);
    }

    public boolean canUserCommit(Integer userId) {
        LocalDateTime timeOfLastCommitOfOtherUsers = revisionHistoryDao.getTimeOfLastCommitNotOfThisUser(userId);
        LocalDateTime lastUpdOfCurrentUser = lastUpdateDao.getLastUpdate(userId);
        return (timeOfLastCommitOfOtherUsers == null) ? true : lastUpdOfCurrentUser.isAfter(timeOfLastCommitOfOtherUsers);

    }

    public boolean commitChanges(Integer userId) {
        List<LocalDoc> docsToCommit = localDocDao.getDocsWithNonZeroStatusForUser(userId);
        for (LocalDoc doc : docsToCommit) {
            switch (doc.getStatus()) {
                case DocAdded:
                    Integer remoteDocId = remoteDocDao.save(doc);
                    Revision addedRevision = new Revision(doc.getRemoteDocId(), Status.DocAdded, doc.getContent(), userId);
                    localDocDao.updateRemoteDocId(doc.getDocId(), remoteDocId);
                    revisionHistoryDao.save(addedRevision);
                    break;
                case DocDeleted:
                    localDocDao.delete(doc.getDocId());
                    if (doc.getRemoteDocId() != null) {
                        remoteDocDao.delete(doc.getRemoteDocId());
                        localDocDao.delete(doc.getDocId());
                        Revision deletedRevision = new Revision(doc.getRemoteDocId(), Status.DocDeleted, "", userId);
                        revisionHistoryDao.save(deletedRevision);
                    }
                    break;
                case Updated:
                    RemoteDoc remoteDoc = remoteDocDao.getById(doc.getRemoteDocId());
                    Diff diff = new Diff(remoteDoc.getContent().toCharArray(), doc.getContent().toCharArray());
                    diff.doDiff();
                    List<Character> deleted = diff.getDeleted();
                    List<Character> added = diff.getAdded();
                    for (Character deletedChar : deleted) {
                        Revision deletedCharRevision = new Revision(doc.getRemoteDocId(), Status.Deleted, deletedChar.toString(), userId);
                        revisionHistoryDao.save(deletedCharRevision);
                    }
                    for (Character addedChar : added) {
                        Revision addedCharRevision = new Revision(doc.getRemoteDocId(), Status.Added, addedChar.toString(), userId);
                        revisionHistoryDao.save(addedCharRevision);
                    }
                    remoteDocDao.updateContent(remoteDoc.getDocId(), doc.getContent());
            }

        }
        localDocDao.setAllStatusesOfUserDocsToZero(userId);
        return true;
    }

}
