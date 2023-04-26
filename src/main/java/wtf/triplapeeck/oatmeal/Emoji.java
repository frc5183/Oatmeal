package wtf.triplapeeck.oatmeal;

import wtf.triplapeeck.oatmeal.cards.PlayingCard;

public class Emoji {
    public static String getEmoji(PlayingCard.Suit suit, PlayingCard.Face face) {
        String rtrn="";
        switch (face) {
            case ACE:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:AceOfSpades:867896589296861225>";
                        break;
                    case HEARTS:
                        rtrn= "<:AceOfHearts:867896634058604615>";
                        break;
                    case DIAMONDS:
                        rtrn= "<:AceOfDiamonds:867896723258605589>";
                        break;
                    case CLUBS:
                        rtrn= "<:AceOfClubs:867896762790445079>";
                        break;
                }
                break;
            case TWO:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:2OfSpades:867897088574095370>";
                        break;
                    case HEARTS:
                        rtrn= "<:2OfHearts:867897401623576607>";
                        break;
                    case DIAMONDS:
                        rtrn= "<:2OfDiamonds:867897441402486824>";
                        break;
                    case CLUBS:
                        rtrn= "<:2OfClubs:867897480280014888>";
                        break;
                }
                break;
            case THREE:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:3OfSpades:867898251164385281>";
                        break;
                    case HEARTS:
                        rtrn= "<:3OfHearts:867898283170725948>";
                        break;
                    case DIAMONDS:
                        rtrn= "<:3OfDiamonds:867898328738037762>";
                        break;
                    case CLUBS:
                        rtrn= "<:3OfClubs:867898358270263317>";
                        break;
                }
                break;
            case FOUR:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:4OfSpades:867898407632371743>";
                    break;
                    case HEARTS:
                        rtrn= "<:4OfHearts:867898443208589343>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:4OfDiamonds:867898473290137632>";
                    break;
                    case CLUBS:
                        rtrn= "<:4OfClubs:867898503715094560>";
                    break;
                }
                break;
            case FIVE:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:5OfSpades:867898631578058822>";
                    break;
                    case HEARTS:
                        rtrn= "<:5OfHearts:867898660532256778>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:5OfDiamonds:867898692408442910>";
                    break;
                    case CLUBS:
                        rtrn= "<:5OfClubs:867898721542078534>";
                    break;
                }
                break;
            case SIX:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:6OfSpades:867898796436094992>";
                    break;
                    case HEARTS:
                        rtrn= "<:6OfHearts:867898830296449065>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:6OfDiamonds:867898864320380968>";
                    break;
                    case CLUBS:
                        rtrn= "<:6OfClubs:867898915042230314>";
                    break;
                }
                break;
            case SEVEN:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:7OfSpades:867898988610191380>";
                    break;
                    case HEARTS:
                        rtrn= "<:7OfHearts:867899033875382283>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:7OfDiamonds:867899066138624020>";
                    break;
                    case CLUBS:
                        rtrn= "<:7OfClubs:867899098765459517>";
                    break;
                }
                break;
            case EIGHT:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:8OfSpades:867899149428981790>";
                    break;
                    case HEARTS:
                        rtrn= "<:8OfHearts:867899183871950849>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:8OfDiamonds:867899217154146364>";
                    break;
                    case CLUBS:
                        rtrn= "<:8OfClubs:867899254831054859>";
                    break;
                }
                break;
            case NINE:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:9OfSpades:867899306321117235>";
                    break;
                    case HEARTS:
                        rtrn= "<:9OfHearts:867899336130429019>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:9OfDiamonds:867899365262491668>";
                    break;
                    case CLUBS:
                        rtrn= "<:9OfClubs:867899395197763584>";
                    break;
                }
                break;
            case TEN:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:10OfSpades:867899588806967336>";
                    break;
                    case HEARTS:
                        rtrn= "<:10OfHearts:867899629927399465>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:10OfDiamonds:867899715534454855>";
                    break;
                    case CLUBS:
                        rtrn= "<:10OfClubs:867899756264685589>";
                    break;
                }
                break;
            case JACK:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:JackOfSpades:867900135191216178>";
                    break;
                    case HEARTS:
                        rtrn= "<:JackOfHearts:867900163553230888>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:JackOfDiamonds:867900189065740288>";
                    break;
                    case CLUBS:
                        rtrn= "<:JackOfClubs:867900218732969984>";
                    break;
                }
                break;
            case QUEEN:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:QueenOfSpades:867900268687785984>";
                    break;
                    case HEARTS:
                        rtrn= "<:QueenOfHearts:867900313507069964>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:QueenOfDiamonds:867900346365902879>";
                    break;
                    case CLUBS:
                        rtrn= "<:QueenOfClubs:867900405466791996>";
                    break;
                }
                break;
            case KING:
                switch (suit) {
                    case SPADES:
                        rtrn= "<:KingOfSpades:867900453421056030>";
                    break;
                    case HEARTS:
                        rtrn= "<:KingOfHearts:867900514380546109>";
                    break;
                    case DIAMONDS:
                        rtrn= "<:KingOfDiamonds:867900554390405170>";
                    break;
                    case CLUBS:
                        rtrn= "<:KingOfClubs:867900591095283752>";
                    break;
                }
                break;
        }
        return rtrn;
    }
}
