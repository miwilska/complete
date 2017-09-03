package complet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByCardPile(CardPile cardPile);

    Card findByCardID(Long cardId);

    Card findBySuit(Suit suit);
}
