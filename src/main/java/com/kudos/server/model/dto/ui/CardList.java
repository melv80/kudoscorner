package com.kudos.server.model.dto.ui;

import java.time.format.TextStyle;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * DTO for displaying cards in gallery
 */
public class CardList implements Iterable<DisplayCard> {
  private List<DisplayCard> cards;
  private Locale locale;

  public CardList(List<DisplayCard> cards, Locale locale) {
    this.cards = cards;
    this.locale = locale;
  }

  public int size() {
    return cards.size();
  }

  public boolean isFirstCardOfDay(int index) {
    if (index == 0) return true;
    DisplayCard previousCard = cards.get(index-1);
    DisplayCard currentCard = cards.get(index);
    return previousCard.created.getDayOfWeek() != currentCard.created.getDayOfWeek();
  }

  // TODO: 25.07.2020 move to display service
  public String getDayOfCard(int index) {
    DisplayCard currentCard = cards.get(index);
    return currentCard.created
        .getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
  }

  @Override
  public Iterator<DisplayCard> iterator() {
    return cards.iterator();
  }
}
