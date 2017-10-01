package com.pedash.controllers.admin;

import com.pedash.dao.GroupDao;
import com.pedash.dto.UserDto;
import com.pedash.entities.Group;
import com.pedash.entities.Role;
import com.pedash.services.GroupService;
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

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yuliya Pedash on 06.06.2017.
 */
@RequestMapping(value = "admin")
@Controller
public class GroupController {
    @Resource
    GroupDao groupDao;
    @Resource
    GroupService groupService;

    @RequestMapping(value = "addGroup", method = RequestMethod.GET)
    public ModelAndView addGroup() {
        ModelAndView model = new ModelAndView();
        model.addObject("roles", Role.values());
        model.setViewName("admin/addGroup");
        return model;
    }

    @RequestMapping(value = "saveGroup", method = RequestMethod.POST)
    public ModelAndView saveGroup(@ModelAttribute("group") Group group, BindingResult result) {
        boolean isGroupAdded = false;
        ModelAndView model = new ModelAndView();
        try {
            isGroupAdded = groupDao.insert(group);
        } catch (DuplicateKeyException e) {
            model.addObject("result", "Group with this name already exists");
        }
        model.addObject("result", isGroupAdded ?
                "Group added successfully" : "Error while adding group");
        model.setViewName("admin/addGroup");
        return model;
    }

    @RequestMapping(value = "viewGroups", method = RequestMethod.GET)
    public ModelAndView viewGroups() {
        ModelAndView model = new ModelAndView();
        List<Group> groups = groupDao.getAll();
        model.addObject("groups", groups);
        model.setViewName("admin/viewGroups");
        return model;
    }

    @RequestMapping(value = "deleteGroup", method = RequestMethod.POST)
    public ModelAndView deleteGroup(@RequestParam(name = "id") Integer groupId) {
        ModelAndView model = new ModelAndView();
        boolean wasDeleted = groupService.deleteGroupAndSetAllUsersToDefaultGroup(groupId);
        if (wasDeleted) {
            model.addObject("result", "Group was deleted");
        } else {
            model.addObject("result", "Sorry!Something went wrong");
        }
        List<Group> groups = groupDao.getAll();
        model.addObject("groups", groups);
        model.setViewName("admin/viewGroups");
        return model;
    }
}
