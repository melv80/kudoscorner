package com.kudos.server.repositories;

import com.kudos.server.model.jpa.KudosCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KudosCardRepository extends JpaRepository<KudosCard, Long> {
}
