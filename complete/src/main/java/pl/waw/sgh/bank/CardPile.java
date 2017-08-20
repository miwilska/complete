package pl.waw.sgh.bank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by prubac on 4/15/2016.
 */
@Entity
public class CardPile {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long cardPileID;

    private String pileNumber;

    private String pileTyp;

    public CardPile() {
    }

    public CardPile(Long cardPileID,
                    String pileNumber,
                    String pileTyp) {
        this.cardPileID = cardPileID;
        this.pileNumber = pileNumber;
        this.pileTyp = pileTyp;
    }

    public CardPile(String pileNumber,
                    String pileTyp) {
        this.pileNumber = pileNumber;
        this.pileTyp = pileTyp;
    }

    public Long getCardPileID() {
        return cardPileID;
    }

    public void setCardPileID(Long cardPileID) {
        this.cardPileID = cardPileID;
    }

    public String getPileNumber() {
        return pileNumber;
    }

    public void setPileNumber(String pileNumber) {
        this.pileNumber = pileNumber;
    }

    public String getPileTyp() {
        return pileTyp;
    }

    public void setPileTyp(String pileTyp) {
        this.pileTyp = pileTyp;
    }

    @Override
    public String toString() {
        return "CardPile{" +
                "" + cardPileID +
                ", " + pileNumber + '\'' +
                ", " + pileTyp + '\'' +
                '}';
    }
}
