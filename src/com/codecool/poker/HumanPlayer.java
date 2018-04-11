package com.codecool.poker;

import java.util.List;
import java.util.Scanner;


public class HumanPlayer extends Player {

    Hand hand;
    Table table;
    Scanner sc = new Scanner(System.in);

    private int chips=100;
    private int bet=0;
    private boolean isFold = false;
    private int minRaise;

    public HumanPlayer() {
        this.table = table;
    }

    public HumanPlayer(Table table) {
        this.table = table;
    }

    public void addChips(int newChips) {
        this.chips += newChips;
    }

    public void throwChips(int bet) {
        this.chips -= bet;
        this.bet = bet;
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

    public void setDealer() {
        this.position = Position.DEALER;
    }

    public void setSmallBlind() {
        this.position = Position.SMALL_BLIND;
    }

    public void setBigBlind() {
        this.position = Position.BIG_BLIND;
    }

    public void setUTG() {
        this.position = Position.UTG;
    }

    public boolean isDealer() {
        return position.equals(Position.DEALER);
    }

    public boolean isSmallBlind() {
        return position.equals(Position.SMALL_BLIND);
    }

    public boolean isBigBlind() {
        return position.equals(Position.BIG_BLIND);
    }

    public boolean isUTG() {
        return position.equals(Position.UTG);
    }

    public void resetBet() {
        this.bet = 0;
    }

    public boolean isFold() {
        return this.isFold;
    }

    public void resetFold() {
        if (isFold == true && getChips() > 0) {
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
                hand.getCards().remove(i);
                discard++;
            }
        }
        return discard;
    }

    public void postSmallBlind() {
        throwChips(1);
    }

    public void postBigBlind() {
        throwChips(2);
    }

    public int makeAction() {
        System.out.println("Choose your action: ");
        String action = sc.next();
        switch (action) {
            case "fold":
                fold();
                return 0;
            case "call":
                return makeCall();
            case "raise":
                System.out.println("Choose raise size: ");
                int raiseSize = sc.nextInt();
                return makeRaise(raiseSize);
        }
        return 1;
    }

    public int makeRaise (int playersRaise) {
        minRaise = table.getActiveBet() + table.getDiff();
        
        while ((minRaise + playersRaise) > getChips()) {                             // Excessive bet control
            System.out.println("Please choose avaliable size of bet");
            playersRaise = sc.nextInt();
        }
        bet = minRaise + playersRaise;
        throwChips(bet);
        return bet;
    }

    public int makeCall() {
        if ((table.getActiveBet() - getBet()) > getChips() && getChips() > 0) {
            bet = throwChips(getChips());
        } else {
            bet = throwChips(table.getActiveBet() - getBet());
        }
        return bet;
    }

    public int makeCheck(){
        if (table.getMaxBet() == getBet()) {
            return bet;
        }
        return null;
    }

    public int getBet() {
        return bet;
    }

    public void fold() {
        isFold = !isFold;
    }
}