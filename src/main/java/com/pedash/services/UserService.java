package com.pedash.services;

import com.pedash.dao.GroupDao;
import com.pedash.dao.UserDao;
import com.pedash.dto.UserDto;
import com.pedash.entities.Group;
import com.pedash.entities.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yuliya Pedash on 06.06.2017.
 */
@Service
public class UserService {
    @Resource
    UserDao userDao;
    @Resource
    GroupDao groupDao;

    public List<UserDto> getAllUserDtos() {
        List<User> allUsers = userDao.getAll();
        List<Group> groups = allUsers.stream().map(u -> groupDao.getById(u.getGroupId())).collect(Collectors.toList());
        return allUsers.stream()
                .map(u -> new UserDto(u.getId(), u.getName(),
                groups.stream()
                        .filter(g -> g.getId() == u.getGroupId())
                        .collect(Collectors.toList()).get(0).getName()))
                .collect(Collectors.toList());
    }
}
