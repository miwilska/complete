package pl.waw.sgh.bank;

import javax.persistence.Entity;

/**
 * Created by prubac on 4/15/2016.
 */
@Entity
public class SavingsCards extends Card {

    public SavingsCards() {
        super();
    }

    public SavingsCards(Long cardID, CardPile cardPile) {
        super(cardID, cardPile);
    }

    public SavingsCards(CardPile cardPile) {
        super(cardPile, true);
    }

}
