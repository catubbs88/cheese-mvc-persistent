package org.launchcode.controllers;

import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by catub on 7/20/2017.
 */
@Controller
@RequestMapping(value="menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value="")
    public String index(Model model) {

        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "My Menus");
        return "menu/index";
    }
}