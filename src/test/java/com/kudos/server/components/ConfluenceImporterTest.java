package com.kudos.server.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.kudos.server.model.jpa.KudosCard;
import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

public class ConfluenceImporterTest {

  ConfluenceImporter importer = new ConfluenceImporter();

  @Test

  void testOneImport() throws MalformedURLException {
    List<KudosCard> cards = importer.importCardsFromJsonFile(new File("./demoimport/DemoImport.json").toURI().toURL());
    assert cards.size() == 2 : "wrong number of cards imported";
  }

  @Test
  void testAllImports() {
    List<KudosCard> cards = importer.importCardsFromDir(Paths.get("./demoimport"));
    assert cards.size() == 4 : "wrong number of cards imported";
  }
}
