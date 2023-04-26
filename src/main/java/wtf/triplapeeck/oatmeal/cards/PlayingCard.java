package wtf.triplapeeck.oatmeal.cards;

import wtf.triplapeeck.oatmeal.Emoji;

public class PlayingCard {
    private Suit suit;
    private Face face;
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;

        public String toString() {
            return switch(this) {
                case CLUBS -> "clubs";
                case DIAMONDS -> "diamonds";
                case HEARTS -> "hearts";
                case SPADES -> "spades";
            };
        }
    }
    public enum Face {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;
        public String toString() {
            return switch (this) {
                case ACE -> "ace";
                case TWO ->"two";
                case THREE->"three";
                case FOUR->"four";
                case FIVE->"five";
                case SIX->"six";
                case SEVEN->"seven";
                case EIGHT->"eight";
                case NINE->"nine";
                case TEN->"ten";
                case JACK->"jack";
                case QUEEN->"queen";
                case KING->"king";

            };
        }
    }

    public Face getFace() {
        return face;
    }

    public Suit getSuit() {
        return suit;
    }

    public PlayingCard(Suit set_suit, Face set_face) {
        suit=set_suit; face=set_face;
    }
    public String getEmoji() {
        return Emoji.getEmoji(suit, face);
    }
    public boolean sameFace(PlayingCard card) {
        return (face==card.face);
    }

    public boolean sameValue(PlayingCard card) {
        if (face==Face.ACE || face==Face.TWO || face==Face.THREE || face==Face.FOUR || face==Face.FIVE || face==Face.SIX || face==Face.SEVEN || face==Face.EIGHT || face==Face.NINE) {
            return (face==card.face);
        } else {
            return (card.face==Face.TEN || card.face==Face.JACK || card.face==Face.QUEEN || card.face==Face.KING);
        }



    }
    public boolean sameSuit(PlayingCard card) {
        return (suit==card.suit);
    }
    public boolean compareTo(PlayingCard card) {
        return (sameFace(card) && sameSuit(card));
    }
    @Override
    public String toString() {
        return "the " + face.toString() + " of " + suit.toString();
    }

}
