package com.pedash.controllers.user;

/**
 * Created by Yuliya Pedash on 20.06.2017.
 */

import com.pedash.dao.LocalDocDao;
import com.pedash.dao.UserDao;
import com.pedash.entities.LocalDoc;
import com.pedash.entities.User;
import com.pedash.services.DocumentsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.faces.bean.SessionScoped;

@SessionScoped
@RequestMapping(value = "local")
@Controller
public class Local {
    @Resource
    LocalDocDao localDocDao;
    @Resource
    UserDao userDao;
    @Resource
    DocumentsService documentsService;
    User user;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showLocalRep() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        user = userDao.getByName(userDetail.getUsername());
        ModelAndView model = new ModelAndView();
        Integer userId = user.getId();
        if (documentsService.hasUserClonedRep(userId)) {
            model.addObject("documents", localDocDao.getAllDocumentsByUser(userId));

        } else {
            model.addObject("errorMsg", "Sorry! To have a local repo you need to clone a remote repo first.");
        }
        model.setViewName("user/local");
        return model;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RedirectView showLocalRep(@RequestParam(name = "name") String docName, RedirectAttributes redirectAttributes) {
        boolean wasCreated = documentsService.createDoc(docName, user.getId());
        redirectAttributes.addFlashAttribute("result", wasCreated ? "Thank you! The document '" + docName + "' was created successfully" :
                "Sorry! An error occurred while creating this doc.");
        return new RedirectView("");
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView showLocalRep(@RequestParam(name = "docId") Integer docId) {
        ModelAndView modelAndView = new ModelAndView();
        LocalDoc localDoc = localDocDao.getDocById(docId);
        modelAndView.addObject("document", localDoc);
        modelAndView.setViewName("user/editFile");
        return modelAndView;
    }

    @RequestMapping(value = "saveEdit", method = RequestMethod.POST)
    public String saveEditedDocument(@RequestParam(name = "docId") Integer docId, @RequestParam(name = "newContent") String newContent, RedirectAttributes redirectAttributes) {
        boolean wasUpdated = documentsService.updateDoc(docId, newContent);
        redirectAttributes.addFlashAttribute("result", wasUpdated ?
                "Your document was updated!" :
                "Sorry! Error while updating this document.");
        return "redirect:/local/edit?docId=" + docId;

    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public RedirectView deleteDoc(@RequestParam(name = "docId") Integer docId, RedirectAttributes redirectAttributes) {
        boolean wasDeleted = documentsService.deleteDoc(docId);
        redirectAttributes.addFlashAttribute("result", wasDeleted ?
                "This local document was deleted!" :
                "Sorry! Error while deleting this local document.");
        return new RedirectView("");
    }
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RedirectView deleteDoc( RedirectAttributes redirectAttributes) {
      documentsService.cloneRep(user.getId());
        redirectAttributes.addFlashAttribute("result",
                "This repository was updated successfully");
        return new RedirectView("");
    }
    @RequestMapping(value = "commit", method = RequestMethod.POST)
    public RedirectView commit(RedirectAttributes redirectAttributes) {
        boolean canBeCommitted = documentsService.canUserCommit(user.getId());
        if (!canBeCommitted) {
            redirectAttributes.addFlashAttribute("result",
                    "Can not commit. Please make update of project first!");
            return new RedirectView("");

        }
        boolean wasCommitSuccessful = documentsService.commitChanges(user.getId());
        redirectAttributes.addFlashAttribute("result", wasCommitSuccessful ?
                "All changes were committed!" :
                "Sorry! Error while committing these changes.");
        return new RedirectView("");
    }
}
