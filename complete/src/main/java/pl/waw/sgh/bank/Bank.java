package pl.waw.sgh.bank;

import pl.waw.sgh.bank.exceptions.AccountNotFoundException;
import pl.waw.sgh.bank.exceptions.IllegalDataException;
import pl.waw.sgh.bank.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prubac on 4/15/2016.
 */
public class Bank {

    List<Card> cardList = new ArrayList<>();

    List<CardPile> cardPileList = new ArrayList<>();

    Long lastCustomerId = 1L;

    Long lastAccountId = 1L;

    public List<CardPile> getCardPileList() {
        return cardPileList;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public List<Card> getAccountListForCustomer(CardPile cardPile) {
        List<Card> customerCardList = new ArrayList<>();
        for (Card a : cardList) {
            if (a.getCardPile().equals(cardPile))
                customerCardList.add(a);
        }
        return customerCardList;
    }

    public void deleteAccount(Card acc) {
        cardList.remove(acc);
    }

    public CardPile createCustomer(String firstName,
                                   String lastName) {
        CardPile c1 = new CardPile(lastCustomerId,
                firstName, lastName);
        lastCustomerId++;
        cardPileList.add(c1);
        return c1;
    }

    public Card createAccount(CardPile owner,
                              boolean isSavings) {
        Card acc;
        //= null;
        if (isSavings) {
            acc = new SavingsCards(lastAccountId, owner);
        }
        else
            acc = new DebitCards(lastAccountId, owner);
        lastAccountId++;
        cardList.add(acc);
        return acc;
    }

    public void transfer(Long fromID,
                         Long toID, Double amount) throws AccountNotFoundException,
            NotEnoughMoneyException, IllegalDataException {
        //if (amount > 10000)
        //    throw new RuntimeException("can't transfer more than 10000");

        BigDecimal bgAmount = new BigDecimal(amount);
        Card fromAcc = getAccountById(fromID);
        Card toAcc = getAccountById(toID);

        if (fromAcc.getBalance().compareTo(bgAmount)>0) {
            fromAcc.charge(bgAmount);
            toAcc.deposit(bgAmount);
        } else {
            throw new NotEnoughMoneyException("Can't transfer - not enough money");
            //System.out.println("Can't transfer - not enough money");
        }
    }

    public Card getAccountById(Long accId) throws AccountNotFoundException {

        for (Card acc : cardList) {
            if (acc.getCardID().equals(accId))
                return acc;
        }
        throw new AccountNotFoundException("Card with ID: " + accId + " not found!!!");
        //return null;
    }

    public CardPile getCustomerById(Long customerId) {
        for (CardPile cardPile : cardPileList) {
            if (cardPile.getCustomerID().equals(customerId))
                return cardPile;
        }
        return null;
    }


    @Override
    public String toString() {
        return "Bank{" +
                "\n accs=" + cardList +
                "\n custs=" + cardPileList +
                "\n lastCustId=" + lastCustomerId +
                "\n lastAccId=" + lastAccountId +
                "\n}";
    }
}
