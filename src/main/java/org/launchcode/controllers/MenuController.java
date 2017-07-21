package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu menu, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "menu/add";
        } else {
            menuDao.save(menu);
            return "redirect:view/" + menu.getId();
        }
    }

    @RequestMapping(value="view/{id}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id) {
        Menu menu = menuDao.findOne(id);
        model.addAttribute("title", menu.getName());
        model.addAttribute("menu", menu);
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int id) {
        Menu menu = menuDao.findOne(id);
        AddMenuItemForm form = new AddMenuItemForm(menu, cheeseDao.findAll());
        model.addAttribute("title", "Add Item to "+menu.getName());
        model.addAttribute("form", form);
        return "menu/additem";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddMenuItemForm form, Errors errors, @RequestParam int cheeseId, @RequestParam int menuId) {

        if (errors.hasErrors()) {
            Menu menu = menuDao.findOne(menuId);
            model.addAttribute("title", "Add Item to "+menu.getName());
            return "menu/additem";
        } else {
            Cheese cheese = cheeseDao.findOne(cheeseId);
            Menu menu = menuDao.findOne(menuId);
            menu.addItem(cheese);
            menuDao.save(menu);
            return "redirect:/menu/view/"+menuId;
        }
    }
}
