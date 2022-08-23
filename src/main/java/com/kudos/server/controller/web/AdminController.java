package com.kudos.server.controller.web;

import com.kudos.server.config.AppConfig;
import com.kudos.server.components.KudosCardService;
import com.kudos.server.repositories.KudosCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

  private static final Logger logger = LoggerFactory.getLogger("AdminController");

  @Autowired
  private AppConfig config;


  @Autowired
  private KudosCardRepository repositories;

  @Autowired
  private KudosCardService kudosCardService;

  @GetMapping("/admin")
  public String admin(final Model model) {
    model.addAttribute("kudoscards", kudosCardService.getKudosCards(1));
    model.addAttribute("contributors", kudosCardService.getWriters(1));
    model.addAttribute("title", config.getCornerTitle());
    return "admin";
  }

  @PostMapping("/admin/deletecard")
  public ModelAndView delete(@RequestParam(name = "id") Long id) {
    kudosCardService.deleteCard(id);
    return new ModelAndView("redirect:/admin");
  }

  @PostMapping("/admin/import")
  public ModelAndView importCards() {
    kudosCardService.importCards();
    return new ModelAndView("redirect:/admin");
  }


}
