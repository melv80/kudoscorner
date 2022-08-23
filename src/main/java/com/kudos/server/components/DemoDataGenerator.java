package com.kudos.server.components;

import com.kudos.server.config.AppConfig;
import com.kudos.server.model.jpa.KudosCard;
import com.kudos.server.model.jpa.KudosType;
import com.kudos.server.repositories.KudosCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class DemoDataGenerator {


  private Logger logger = LoggerFactory.getLogger(DemoDataGenerator.class);


  private final AppConfig appConfig;
  private final ImageServiceImpl imageServiceImpl;
  private KudosCardRepository kudosCardRepository;

  public DemoDataGenerator(@Autowired AppConfig appConfig,
                           @Autowired ImageServiceImpl imageServiceImpl,
                           @Autowired KudosCardRepository kudosCardRepository) {
    this.appConfig = appConfig;
    this.imageServiceImpl = imageServiceImpl;
    this.kudosCardRepository = kudosCardRepository;
  }

  @PostConstruct
  void populateList() {
    if (!appConfig.isGenerateDemoData()) return;

    imageServiceImpl.insertIntoDatabase();
    int cards = 15;
    List<KudosCard> demoList = new ArrayList<>();
    for (int i = 0; i < cards; i++) {
      demoList.add(demoCard(i));
    }
    kudosCardRepository.saveAll(demoList);

    logger.info("demo data added: "+cards);
  }

  private KudosCard demoCard(int index) {
    KudosCard card = new KudosCard();
    card.setType(KudosType.values()[new Random().nextInt(KudosType.values().length)]);
    card.setBackgroundImage(imageServiceImpl.pickRandomImage(card.getType()));
    card.setCreated(Instant.now().minus(index % 7, ChronoUnit.DAYS));
    card.setEdited(Instant.now().minus(index % 7, ChronoUnit.DAYS));
    card.setMessage("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."+index);
    card.setMessage(card.getMessage().substring(0, new Random().nextInt(card.getMessage().length()-250)));

    card.setWriter("Melv ("+index+")");
    return card;
  }

}
