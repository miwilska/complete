package complet;

import complet.exceptions.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prubac on 4/15/2016.
 */
public class Game {

    List<Card> cardList = new ArrayList<>();

    List<CardPile> cardPileList = new ArrayList<>();

    Long lastCardPileId = 1L;

    Long lastCardId = 1L;

    public List<CardPile> getCardPileList() {
        return cardPileList;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public List<Card> getCardsListForCardPile(CardPile cardPile) {
        List<Card> customerCardList = new ArrayList<>();
        for (Card a : cardList) {
            if (a.getCardPile().equals(cardPile))
                customerCardList.add(a);
        }
        return customerCardList;
    }

    public void deleteCard(Card acc) {
        cardList.remove(acc);
    }

    public CardPile createCardPile(String pileNumber,
                                   String pileTyp) {
        CardPile c1 = new CardPile(lastCardPileId,
                pileNumber, pileTyp);
        lastCardPileId++;
        cardPileList.add(c1);
        return c1;
    }

    public Card addCardToPile(CardPile somePile,
                              boolean isSavings) {
        Card acc;
        //= null;
        if (isSavings) {
            acc = new AddingTurnedBackCards(lastCardId, somePile);
        }
        else
            acc = new AddingCards(lastCardId, somePile);
        lastCardId++;
        cardList.add(acc);
        return acc;
    }

 /*   public void transfer(Long fromID,
                         Long toID, Double amount) throws AccountNotFoundException,
            NotEnoughMoneyException, IllegalDataException {
        //if (amount > 10000)
        //    throw new RuntimeException("can't transfer more than 10000");

        BigDecimal bgAmount = new BigDecimal(amount);
        Card fromAcc = getCardById(fromID);
        Card toAcc = getCardById(toID);

        if (fromAcc.getBalance().compareTo(bgAmount)>0) {
            fromAcc.charge(bgAmount);
            toAcc.deposit(bgAmount);
        } else {
            throw new NotEnoughMoneyException("Can't transfer - not enough money");
            //System.out.println("Can't transfer - not enough money");
        }
    }
*/
    public Card getCardById(Long crdId) throws AccountNotFoundException {

        for (Card crd : cardList) {
            if (crd.getCardID().equals(crdId))
                return crd;
        }
        throw new AccountNotFoundException("Card with ID: " + crdId + " not found!!!");
        //return null;
    }

    public CardPile getCardPileById(Long cardPileID) {
        for (CardPile cardPile : cardPileList) {
            if (cardPile.getCardPileID().equals(cardPileID))
                return cardPile;
        }
        return null;
    }


    @Override
    public String toString() {
        return "Game{" +
                "\n cards=" + cardList +
                "\n cardPiles=" + cardPileList +
                "\n lastCardPileId=" + lastCardPileId +
                "\n lastCardId=" + lastCardId +
                "\n}";
    }
}
