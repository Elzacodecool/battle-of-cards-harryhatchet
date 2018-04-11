package com.codecool.poker;

import java.util.List;
import java.util.Scanner;


public class HumanPlayer extends Player {

    Hand hand = new Hand(cards);
    Scanner sc = new Scanner(System.in);

    private int chips=100;
    private int bet=0;
    private boolean isFold = false;
    private boolean isDealer = false;
    private boolean isSmallBlind = false;
    private boolean isBigBlind = false;
    private boolean isUTG = false;
    int minRaise;

    public void addChips(int newChips) {
        chips += newChips;
    }

    public void throwChips(int bet) {
        chips -= bet;
    }

    public int getChips() {
        return chips;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    // public int placeBet(int customBet) {
    //     if (isSmallBlind) {

    // }

    public void resetBet() {
        this.bet = 0;
    }

    public void resetFold() {
        if (isFold == true) {
            isFold = false;
        }
    }

    public int changeCards() {
        boolean[] cardStatus = {false, false, false, false, false};
        int cardToDissmiss;
        do {
            System.out.println("Choose cards to change or press 0 to quit");
               cardToDissmiss = sc.nextInt();
            cardStatus[cardToDissmiss-1] = true;
        } while (cardToDissmiss == 0);

        int discard = 0;
        for (int i=4; i >= 0; i--) {
            if (cardStatus[i]) {
                hand.discard(i);
                discard++;
            }
        }
        return discard;
    }


    private int makeRaise (int playersRaise) {
        while (playersRaise > getChips()) {
            System.out.println("Please choose avaliable size of bet");
            playersRaise = sc.nextInt();
        }
            if (table.getDiff() == 1) {
                minRaise = table.getMaxBet() + 2;
            } else {
                minRaise = table.getMaxBet() + table.getDiff();
            }
            bet = minRaise + playersRaise;
            if (bet > getChips()) {
                bet = getChips();
            }
            throwChips(bet);
            return bet;
    }    

    private int makeCall() {
        bet = table.getMaxBet();
        throwChips(bet);
        return bet;
        }

    public int getBet() {
        return bet;
    }

    public void fold() {
        isFold = !isFold;
    }

    public void setDealer() {
        isDealer = true;
        isSmallBlind = false;
        isBigBlind = false;
        isUTG = false;
    }

    public void setSmallBlind() {
        isDealer = false;
        isSmallBlind = true;
        isBigBlind = false;
        isUTG = false;
    }

    public void setBigBlind() {
        isDealer = false;
        isSmallBlind = false;
        isBigBlind = true;
        isUTG = false;
    }

    public void setUTG() {
        isDealer = false;
        isSmallBlind = false;
        isBigBlind = false;
        isUTG = true;
    }

    public boolean getCurrentDealer() {
        return isDealer;
    }

    public boolean getCurrentSmallBlind() {
        return isSmallBlind;
    }

    public boolean getCurrentBigBlind() {
        return isBigBlind;
    }

    public boolean getCurrentUTG() {
        return isUTG;
    }

}