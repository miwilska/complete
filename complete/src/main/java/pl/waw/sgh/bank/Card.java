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

    private boolean savings;

    public Card() {
    }

    public Card(Long cardID, CardPile cardPile) {
        this.cardID = cardID;
        this.cardPile = cardPile;
        this.balance = new BigDecimal(0);
    }

    public Card(CardPile cardPile) {
        this.cardPile = cardPile;
        this.balance = new BigDecimal(0);
    }

    public Card(CardPile cardPile, boolean savings) {
        this.cardPile = cardPile;
        this.balance = new BigDecimal(0);
        this.savings = savings;
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

    public boolean isSavings() {
        return savings;
    }

    public void setSavings(boolean savings) {
        this.savings = savings;
    }

    public void deposit(BigDecimal amount) throws IllegalDataException {
        if (amount.compareTo(new BigDecimal(0))<=0)
            throw new IllegalDataException(
                    "Can't deposit on Card ID: "
                            + cardID +
                            " negative amount: " + amount);
        this.balance = balance.add(amount);
    }

    public void charge(BigDecimal amount)  throws IllegalDataException {
        if (amount.compareTo(new BigDecimal(0))<=0)
            throw new IllegalDataException(
                    "Can't charge Card ID: "
                            + cardID +
                            " negative amount: " + amount);

        balance = balance.subtract(amount);
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                .replace("Card", "") + "{" +
                "ID=" + cardID +
                "sav=" + savings +
                ", " + balance.setScale(2,BigDecimal.ROUND_HALF_EVEN) +
                ", cust=" + cardPile.getPileTyp() +
                '}';
    }
}
