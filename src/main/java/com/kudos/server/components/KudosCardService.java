package com.kudos.server.components;

import com.kudos.server.model.jpa.KudosCard;
import com.kudos.server.model.dto.ui.CreateCard;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface KudosCardService {
  /**
   * @param weeksAgo
   * @return a list of {@link KudosCard} sorted by date.
   */
  List<KudosCard> getKudosCards(int weeksAgo);

  Set<String> getWriters(int weeksAgo);

  void createCard(CreateCard createCard);

  void importCards();

  void deleteCard(Long id);
}
