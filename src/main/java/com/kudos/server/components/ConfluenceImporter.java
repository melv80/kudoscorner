package com.kudos.server.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.kudos.server.model.dto.imports.ConfluenceCard;
import com.kudos.server.model.jpa.KudosCard;
import com.kudos.server.model.jpa.KudosType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ConfluenceImporter {
  private final Logger logger = LoggerFactory.getLogger(ConfluenceImporter.class);

  /**
   * @param source one file that contains the data
   * @return list of {@link KudosCard}
   */
  public List<KudosCard> importCardsFromJsonFile(URL source) {
    logger.info("importing cards from: "+source);
    List<KudosCard> result = new ArrayList<>();
    ObjectMapper input = new ObjectMapper();
    try {
      JsonNode formdata = input.readTree(source);
      return importCardsFromJsonFile(formdata);
    } catch (IOException e) {
      logger.error("could not import kudos card", e);
    }

    return result;
  }

  /**
   * @param formdata downloaded json data
   * @return list of {@link KudosCard}
   */
  public List<KudosCard> importCardsFromJsonFile(JsonNode formdata) {
    List<KudosCard> result = new ArrayList<>();
    ObjectMapper input = new ObjectMapper();
    try {
      ArrayNode rowData = (ArrayNode) formdata.get("rows");
      for (JsonNode rowDatum : rowData) {
        ConfluenceCard card = input.readValue(rowDatum.toString(), ConfluenceCard.class);
        result.add(convert(card));
        logger.info("read card from: "+card.userName);
      }
    } catch (IOException e) {
      logger.error("could not import kudos card", e);
    }

    return result;
  }

  public static KudosCard convert(ConfluenceCard card) {
    KudosCard result = new KudosCard();
    result.setImporterID(card.id);
    result.setCreated(DateTimeFormatter.ISO_DATE_TIME.parse(card.created, Instant::from));
    result.setEdited(DateTimeFormatter.ISO_DATE_TIME.parse(card.updated, Instant::from));

    result.setType(mapType(card.fields.get(0).values[0]));
    result.setMessage(card.fields.get(1).values[0]);

    if (result.getType().equals(KudosType.JOKER))
      result.setWriter("The Joker");
    else
      result.setWriter(card.fields.get(2).values[0]);
    return result;
  }

  public static KudosType mapType(String value) {
    switch (value) {
      case "Awesome" : return KudosType.AWESOME;
      case "Appreciation" : return KudosType.APPRECIATION;
      case "Happy" : return KudosType.HAPPY;
      case "Joker" : return KudosType.JOKER;
      default:
      return KudosType.THANK_YOU;
    }
  }

  @Nullable
  private URL toURL(Path path) {
    try {
      return path.toUri().toURL();
    } catch (MalformedURLException e) {
      logger.warn("could not open file: "+path, e);
    }
    return null;
  }

  public List<KudosCard> importCardsFromDir(Path importDir) {
    try (Stream<Path> fileStream = Files.list(importDir)) {
      return fileStream
          .map(this::toURL)
          .filter(Objects::nonNull)
          .map(this::importCardsFromJsonFile)
          .flatMap(List::stream)
          .collect(Collectors.toList());

    } catch (MalformedURLException e) {
      throw new RuntimeException("could not open import directory", e);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
