package wtf.triplapeeck.oatmeal.cards;

import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.Logger;

import java.util.ArrayList;

public class Hand {
    public int getValue() {
        Logger.customLog("Hand", "Evaluating Hand");
        if (hand.size() == 0) {
            return 0;
        } else {
            ArrayList<Integer> possibilities = new ArrayList<Integer>();
            possibilities.add(0);
            int index = hand.size() - 1;
            do {
                switch (hand.get(index).getFace()) {
                    case ACE:
                        possibilities.addAll(possibilities);
                        for (int j = 0; j < (possibilities.size() / 2); j++) {
                            possibilities.set(j, possibilities.get(j) + 1);
                        }
                        for (int j = possibilities.size() / 2; j < possibilities.size(); j++) {
                            possibilities.set(j, possibilities.get(j) + 11);
                        }
                        break;
                    case JACK:
                    case QUEEN:
                    case KING:
                    case TEN:
                        for (int i = 0; i < possibilities.size(); i++) {
                            possibilities.set(i, possibilities.get(i) + 10);
                        }
                        break;
                    case TWO:
                        for (int i = 0; i < possibilities.size(); i++) {
                            possibilities.set(i, possibilities.get(i) + 2);
                        }
                        break;
                    case THREE:
                        for (int i = 0; i < possibilities.size(); i++) {
                            possibilities.set(i, possibilities.get(i) + 3);
                        }
                        break;
                    case FOUR:
                        for (int i = 0; i < possibilities.size(); i++) {
                            possibilities.set(i, possibilities.get(i) + 4);
                        }
                        break;
                    case FIVE:
                        for (int i = 0; i < possibilities.size(); i++) {
                            possibilities.set(i, possibilities.get(i) + 5);
                        }
                        break;
                    case SIX:
                        for (int i = 0; i < possibilities.size(); i++) {
                            possibilities.set(i, possibilities.get(i) + 6);
                        }
                        break;
                    case SEVEN:
                        for (int i = 0; i < possibilities.size(); i++) {
                            possibilities.set(i, possibilities.get(i) + 7);
                        }
                        break;
                    case EIGHT:
                        for (int i = 0; i < possibilities.size(); i++) {
                            possibilities.set(i, possibilities.get(i) + 8);
                        }
                        break;
                    case NINE:
                        for (int i = 0; i < possibilities.size(); i++) {
                            possibilities.set(i, possibilities.get(i) + 9);
                        }
                        break;
                }
                index--;
            }
            while (index >= 0);


            boolean isUnder21 = false;
            int largestValueUnder21 = 0;

            for (Integer i : possibilities) {
                if (i <= 21) {
                    isUnder21 = true;
                    if (i > largestValueUnder21) {
                        largestValueUnder21 = i;
                    }
                }
            }

            if (isUnder21) {
                return largestValueUnder21;
            } else {
                return possibilities.get(0);
            }
        }


    }

    public Hand(@NotNull Deck deck) {
        for (PlayingCard card : deck.deck) {
            hand.add(card);
        }
    }

    @Override
    public String toString() {

        String str="Hand with value (" + getValue() + ") {";
        int count=0;
        for (PlayingCard card: hand) {
            count++;
            str+=card.toString();
            if (count<hand.size()) {
                str+=", ";
            } else {
                str+="}";
            }
        }
        return str;
    }
    public Hand(@NotNull ArrayList<PlayingCard> list) {
        hand = list;
    }

    public ArrayList<PlayingCard> hand = new ArrayList<>();

    public Hand() {

    }
}
