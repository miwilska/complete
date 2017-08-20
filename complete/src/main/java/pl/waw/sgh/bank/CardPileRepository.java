package pl.waw.sgh.bank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardPileRepository extends JpaRepository<CardPile, Long> {

    List<CardPile> findByPileTyp(String pileTyp);

    List<CardPile> findByPileTypStartsWithIgnoreCase(String pileTyp);
}
