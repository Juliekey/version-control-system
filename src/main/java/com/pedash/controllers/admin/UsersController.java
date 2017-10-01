package com.pedash.controllers.admin;

import com.pedash.dao.GroupDao;
import com.pedash.dao.UserDao;
import com.pedash.dto.UserDto;
import com.pedash.entities.Group;
import com.pedash.entities.User;
import com.pedash.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Yuliya Pedash on 06.06.2017.
 */

@RequestMapping(value = "admin")
@Controller
public class UsersController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    GroupDao groupDao;
    @RequestMapping(value = "viewUsers", method = RequestMethod.GET)
    public ModelAndView viewUsers() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        model.addObject("currentUsername", userDetail.getUsername());
        List<UserDto> users = userService.getAllUserDtos();
        model.addObject("users", users);
        model.setViewName("admin/viewUsers");
        return model;
    }
    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    public ModelAndView deleteUser(@RequestParam(name="id") Integer userId) {
        ModelAndView model = new ModelAndView();
        boolean wasDeleted = userDao.deleteById(userId);
        if (wasDeleted){
            model.addObject("result", "User was deleted");
        }
        else{
            model.addObject("result", "Sorry!Something went wrong");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        model.addObject("currentUsername", userDetail.getUsername());
        List<UserDto> users = userService.getAllUserDtos();
        model.addObject("users", users);
        model.setViewName("admin/viewUsers");
        return model;
    }
    @RequestMapping(value = "saveUser", method = RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute("group") User user, BindingResult result) {
        boolean isUserAdded = false;
        ModelAndView model = new ModelAndView();
        try {
            isUserAdded = userDao.insert(user);
        } catch (DuplicateKeyException e) {
            model.addObject("result", "User with this name already exists");
        }
        model.addObject("result", isUserAdded ?
                "User added successfully" : "Error while adding user");
        model.setViewName("admin/addUser");
        return model;
    }
    @RequestMapping(value = "addUser", method = RequestMethod.GET)
    public ModelAndView addGroup() {
        ModelAndView model = new ModelAndView();
        List<Group> groups = groupDao.getAll();
        model.addObject("groups", groups);
        model.setViewName("admin/addUser");
        return model;
    }

}
