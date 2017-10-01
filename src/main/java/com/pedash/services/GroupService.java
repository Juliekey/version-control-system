package com.pedash.services;

import com.pedash.dao.GroupDao;
import com.pedash.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yuliya Pedash on 06.06.2017.
 */
@Service
public class GroupService {
    @Resource
    GroupDao groupDao;
    @Resource
    UserDao userDao;
    //@Transactional
    public boolean deleteGroupAndSetAllUsersToDefaultGroup(Integer groupId){
            userDao.setAllUsersToDefaultGroup(groupId);
            return groupDao.deleteById(groupId);
    }
}
