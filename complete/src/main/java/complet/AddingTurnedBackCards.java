package complet;

import javax.persistence.Entity;

/**
 * Created by prubac on 4/15/2016.
 */
@Entity
public class AddingTurnedBackCards extends Card {

    public AddingTurnedBackCards() {
        super();
    }

    public AddingTurnedBackCards(Long cardID, CardPile cardPile) {
        super(cardID, cardPile);
    }

    public AddingTurnedBackCards(CardPile cardPile) {
        super(cardPile, false);
    }

    public AddingTurnedBackCards(CardPile pile, Suit s, Face f){
        super(pile, f, s, false);
    }
}
