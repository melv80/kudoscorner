package com.kudos.server.controller.web;

import com.kudos.server.components.KudosCardService;
import com.kudos.server.components.DisplayService;
import com.kudos.server.config.AppConfig;
import com.kudos.server.model.jpa.Image;
import com.kudos.server.model.jpa.KudosType;
import com.kudos.server.model.dto.ui.CreateCard;
import com.kudos.server.repositories.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Controller
public class WebController {

  private Logger logger = LoggerFactory.getLogger(WebController.class);

  @Autowired
  AppConfig config;

  @Autowired
  KudosCardService kudosCardService;

  @Autowired
  DisplayService displayService;


  @Autowired
  ImageRepository imageRepository;

  @GetMapping("/")
  public String index(final Model model) {
    model.addAttribute("kudoscards", displayService.getDisplayCards(1));
    model.addAttribute("contributors", kudosCardService.getWriters(1));
    model.addAttribute("title", config.getCornerTitle());
    model.addAttribute("greeting", config.getGreeting());
    model.addAttribute("outro", config.getOutro());
    return "index";
  }

  @GetMapping("/create")
  public String createCard(final Model model) {
    model.addAttribute("types", KudosType.values());
    model.addAttribute("newCard", new CreateCard());
    return "create";
  }

  @PostMapping("/create")
  public String createCard(@Valid @ModelAttribute CreateCard createCard) {
    kudosCardService.createCard(createCard);
    return "redirect:/";
  }


  @RequestMapping(path = "/images/{id}", method = RequestMethod.GET)
  public ResponseEntity<ByteArrayResource> download(@PathVariable("id") Long id) throws IOException {

    final Optional<Image> byId = imageRepository.findById(id);
    if (!byId.isPresent()) throw new IllegalStateException("image not found");
    Path path = config.getBaseDir().resolve(byId.get().pathOnDisk).normalize();
    if (!Files.isReadable(path)) {
      logger.error(String.format("image[ID=%s] missing: %s",id, path));
      return ResponseEntity.notFound().build();
    }

    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

    return ResponseEntity.ok()
        .contentLength(Files.size(path))
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);

  }
}
