package pl.waw.sgh.bank;

import pl.waw.sgh.bank.exceptions.IllegalDataException;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by prubac on 4/15/2016.
 */
@Entity
public abstract class Card {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long cardID;

    @ManyToOne
    private CardPile cardPile;

    private BigDecimal balance;

    private boolean isFaceUp;

    private Suit suit;

    private Face face;


    public Card() {
    }

    public Card(Long cardID, CardPile cardPile) {
        this.cardID = cardID;
        this.cardPile = cardPile;
        this.balance = new BigDecimal(0);
        this.suit = Suit.CLUBS ;
        this.face = Face.ACE;
    }

    public Card(CardPile cardPile, Face f, Suit s, boolean isFaceUp){
        this.cardPile = cardPile;
        this.balance = new BigDecimal(0);
        this.suit = s ;
        this.face = f;
        this.isFaceUp = isFaceUp;
    }

    public Card(CardPile cardPile) {
        this.cardPile = cardPile;
        this.balance = new BigDecimal(0);
    }

    public Card(CardPile cardPile, boolean isFaceUp) {
        this.cardPile = cardPile;
        this.balance = new BigDecimal(0);
        this.isFaceUp = isFaceUp;
    }


    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public Long getCardID() {
        return cardID;
    }

    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    @ManyToOne
    public CardPile getCardPile() {
        return cardPile;
    }

    public void setCardPile(CardPile cardPile) {
        this.cardPile = cardPile;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.isFaceUp = faceUp;
    }

    public void deposit(BigDecimal amount) throws IllegalDataException {
        if (amount.compareTo(new BigDecimal(0))<=0)
            throw new IllegalDataException(
                    "Can't deposit on Card ID: "
                            + cardID +
                            " negative amount: " + amount);
        this.balance = balance.add(amount);
    }

  /*  public void charge(BigDecimal amount)  throws IllegalDataException {
        if (amount.compareTo(new BigDecimal(0))<=0)
            throw new IllegalDataException(
                    "Can't charge Card ID: "
                            + cardID +
                            " negative amount: " + amount);

        balance = balance.subtract(amount);
    }
*/

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                .replace("Card", "") + "{" +
                "ID=" + cardID +
               // "sav=" + isFaceUp +
               // ", " + balance.setScale(2,BigDecimal.ROUND_HALF_EVEN) +
                ", cardPile=" + cardPile.getPileTyp() +
                '}';
    }
}
