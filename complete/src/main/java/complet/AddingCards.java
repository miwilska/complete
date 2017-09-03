package complet;

import javax.persistence.Entity;

/**
 * Created by prubac on 4/15/2016.
 */
@Entity
public class AddingCards extends Card {

    public AddingCards() {
        super();
    }

    public AddingCards(Long cardID, CardPile cardPile) {
        super(cardID, cardPile);
    }

  //  public AddingCards(CardPile cardPile) {
  //      super(cardPile, true);
  //  }

    public AddingCards(CardPile pile, Suit s, Face f){
        super(pile, f, s, true);
    }

    public AddingCards(CardPile cardPile) {
        super(cardPile);
    }

}
