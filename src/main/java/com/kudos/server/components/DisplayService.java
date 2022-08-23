package com.kudos.server.components;

import com.kudos.server.config.AppConfig;
import com.kudos.server.model.jpa.KudosCard;
import com.kudos.server.model.dto.ui.DisplayCard;
import com.kudos.server.model.dto.ui.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisplayService {

  private final AppConfig appConfig;
  private final KudosCardService kudosCardService;

  public DisplayService(@Autowired KudosCardService kudosCardService,
                        @Autowired AppConfig appConfig) {
    this.kudosCardService = kudosCardService;
    this.appConfig = appConfig;
  }

  public DisplayCard toDisplayCard(KudosCard card) {
    DisplayCard result = new DisplayCard();
    result.created = localDateTime(card.getCreated());
    result.formattedDate = formatDate(card.getCreated());
    result.imageId = card.getBackgroundImage() == null ? -1 : card.getBackgroundImage().getId();
    result.title = card.getType().getFormattedText();
    result.writer = card.getWriter();
    result.message = card.getMessage();
    return result;
  }

  public CardList getDisplayCards(int weeksAgo) {
    final List<KudosCard> kudosCards = kudosCardService.getKudosCards(weeksAgo);
    kudosCards.sort(Comparator.comparing(KudosCard::getCreated));
    return new CardList(kudosCards.stream().map(this::toDisplayCard).collect(Collectors.toList()), appConfig.getLocale());
  }

  public ZonedDateTime localDateTime(Instant time) {
    return time.atZone(ZoneId.systemDefault());
  }


  public String formatDate(Instant edited) {
    return localDateTime(edited).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(appConfig.getLocale()));
  }

}
