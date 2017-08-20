package pl.waw.sgh.bank;

import javax.persistence.Entity;

/**
 * Created by prubac on 4/15/2016.
 */
@Entity
public class DebitCards extends Card {

    public DebitCards() {
        super();
    }

    public DebitCards(Long cardID, CardPile cardPile) {
        super(cardID, cardPile);
    }


    public DebitCards(CardPile cardPile) {
        super(cardPile, false);
    }
}
