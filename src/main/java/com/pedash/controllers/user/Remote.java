package com.pedash.controllers.user;

/**
 * Created by Yuliya Pedash on 20.06.2017.
 */

import com.pedash.dao.RemoteDocDao;
import com.pedash.dao.RevisionHistoryDao;
import com.pedash.dao.UserDao;
import com.pedash.entities.RemoteDoc;
import com.pedash.entities.Revision;
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
import java.io.Serializable;
import java.util.List;

@SessionScoped
@RequestMapping(value = "remote")
@Controller
public class Remote implements Serializable {
    @Resource
    RemoteDocDao remoteDocDao;
    @Resource
    UserDao userDao;
    @Resource
    DocumentsService documentsService;
    @Resource
    RevisionHistoryDao revisionHistoryDao;
    User user;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showDocuments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        user = userDao.getByName(userDetail.getUsername());
        ModelAndView model = new ModelAndView();
        model.addObject("documents", remoteDocDao.getAllDocuments());
        model.setViewName("user/remote");
        return model;
    }

    @RequestMapping(value = "clone", method = RequestMethod.POST)
    public RedirectView clone(RedirectAttributes redirectAttributes) {
        documentsService.cloneRep(user.getId());
        redirectAttributes.addFlashAttribute("result", "This repository was cloned successfully");
        return new RedirectView("");
    }

    @RequestMapping(value = "viewHistory", method = RequestMethod.GET)
    public ModelAndView clone(@RequestParam(name = "docId") Integer docId) {
        ModelAndView modelAndView = new ModelAndView();
        RemoteDoc remoteDoc = remoteDocDao.getById(docId);
        List<Revision> revisions = revisionHistoryDao.getAllRevisionsByDocId(docId);
        modelAndView.addObject("revisions", revisions);
        modelAndView.addObject("name", remoteDoc.getName());
        modelAndView.setViewName("user/viewHistory");
        return modelAndView;
    }
}
