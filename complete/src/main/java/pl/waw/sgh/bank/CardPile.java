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
    private Long customerID;

    private String pileNumber;

    private String pileTyp;

    public CardPile() {
    }

    public CardPile(Long customerID,
                    String pileNumber,
                    String pileTyp) {
        this.customerID = customerID;
        this.pileNumber = pileNumber;
        this.pileTyp = pileTyp;
    }

    public CardPile(String pileNumber,
                    String pileTyp) {
        this.pileNumber = pileNumber;
        this.pileTyp = pileTyp;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
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
                "" + customerID +
                ", " + pileNumber + '\'' +
                ", " + pileTyp + '\'' +
                '}';
    }
}
