import java.util.Random;
import java.util.Scanner;

class Card
{
	int value;
	String suit;
	String name;
	
	public String toString()
	{
		return name + " of " + suit;
	}
	
}

public class BlackjackClass {
	
	private int used = 0;
	private Card[] usedCards = new Card[52];
    public String[] visualCards = new String[52];
	public int cardCount = 0, p_total = 0, d_total = 0, numTurn = 1;
    public char turn = 'd';
    public String strTurn = "", hidden_card_display = "";;
    public boolean initial = false, isBlackjack = false, gameover = false;
    public double wager = 0.00, wallet = 10;

    public boolean alreadyUsed ( Card deck ) {

        for (int i = 0; i < usedCards.length; i++) {

            if (usedCards[i].value == deck.value && usedCards[i].suit.equals(deck.suit) && usedCards[i].name.equals(deck.name)) {
                return true;
            }

        }

        return false;
    }

    public Card[] buildDeck() //builds deck of cards
    {
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] names = {"ZERO", "ONE", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "Jack", "Queen", "King", "Ace"};

        int i = 0;

        Card[] deck = new Card[52];

        for (String s : suits) {
            for (int v = 2; v <= 14; v++) {
                Card c = new Card();
                c.suit = s;
                c.name = names[v];
                if (v == 14)
                    c.value = 11;
                else if (v > 10)
                    c.value = 10;
                else
                    c.value = v;
                System.out.println(c);
                deck[i] = c;
                i++;

            }
        }

        if (numTurn == 1) {

            System.out.println("Dealer: \"Welcome to the Blackjack Table, pull up a seat!\" *pulls out a deck of cards*\n");
        } else {
            System.out.println("The cards from the last game disintegrate before your eyes as a new deck appears from out of nowhere. \n");
        }

        deck = shuffleDeck(deck);

        for (int x = 0; x < deck.length; x++)
        {
            System.out.println(deck[x]);
        }

        return deck;

    }

    public void card_display( Card deck ) //function produces the graphics of the program
    {


        String card_value = "", card_display = "";

        if ( deck.name.equals("Jack") )
        {
            card_value = "J";
        }
        else if (deck.name.equals("Queen") )
        {
            card_value = "Q";

        }
        else if (deck.name.equals("King"))
        {
            card_value = "K";

        }
        else if (deck.name.equals("Ace"))
        {
            card_value = "A";
        }
        else {
            card_value = Integer.toString(deck.value);
        }

        String suit = suits(deck);



        card_display =  "+-----------+\n"
                      + "| " + card_value + "         |\n"
                      + "|           |\n"
                      + "|     "+suit+"     |\n"
                      + "|           |\n"
                      + "|        " + card_value + "  |\n"
                      + "+-----------+";




  /* card_display =  "+-----------+ +-----------+\n"
                + "| " + card_value + "         | | " + card_value + "         |\n"
                + "|           | |           |\n"
                + "|     "+suit+"     | |     "+suit+"     |\n"
                + "|           | |           |\n"
                + "|        " + card_value + "  | |        " + card_value + "  |\n"
                + "+-----------+ +-----------+"; */


        if (initial) {

            String hidden_suit = suits(deck);

            hidden_card_display = "+-----------+\n"
                    + "| " + card_value + "         |\n"
                    + "|           |\n"
                    + "|     "+hidden_suit+"     |\n"
                    + "|           |\n"
                    + "|        " + card_value + "  |\n"
                    + "+-----------+";

            card_display =    "+-----------+\n"
                    +"|           |\n"
                    +"|           |\n"
                    +"|   HIDDEN  |\n"
                    +"|           |\n"
                    +"|           |\n"
                    +"+-----------+";

            initial = false;


        }

        System.out.println(card_display);


    }

	/* public static void displayDeck( Card[] deck )
	{
	for ( Card c : deck )
		System.out.println(c.value + "\t" + c);

	} */

    public void getCard (char turn, Card[] deck) {

        // System.out.println(strTurn + " is dealt a " + deck[cardCount] + " out of the deck.");

        if (deck[cardCount].name.equals("Ace")) {
            // System.out.println("This is an Ace!");
            deck[cardCount].value = isAce(turn);
        }



        card_display(deck[cardCount]);

        Total(turn, deck[cardCount].value);

        cardCount = cardCount + 1;

        // hasBust(turn);

    }

    public int getTotal (char player) {
        if (player == 'p') {

            return p_total;

        }

        if (player == 'd') {

            return d_total;
        }

        return 0;
    }

    public void getTurn(char player) {

        if (player == 'p') {

            turn = 'd';
            strTurn = "Dealer";

            System.out.println ("==================");
            System.out.println ("+ Dealer's Turn  +");
            System.out.println ("==================");

        }

        else {

            turn = 'p';
            strTurn = "Player";

            System.out.println ("==================");
            System.out.println ("+ Player's Turn  +");
            System.out.println ("==================");
        }

    }

    public void hasBust(char turn) {

    System.out.println("\n=====hasBust()======");
    System.out.println("Turn = " + turn);
    System.out.println("getTotal(turn) = " + getTotal(turn));
    System.out.println("======================\n");

        if (turn == 'p' && getTotal(turn) > 21) {
            System.out.println();
            System.out.println("========= RESULTS ==========");
            System.out.println("Player's total is " + getTotal('p') + ".");
            System.out.println("Dealer's total is " + getTotal('d') + ".");
            System.out.println("    " + strTurn + " busted!");
            System.out.println("** The dealer snatches up your $" + wager +" with a snicker **");
            System.out.println("========= RESULTS ==========\n");

            gameover = true;

        }

        if (turn == 'd' && getTotal(turn) > 21) {
            System.out.println();
            System.out.println("========= RESULTS ==========");
            System.out.println( "Player's total is " + getTotal('p') + "." );
            System.out.println( "Dealer's total is " + getTotal('d') + "." );
            System.out.println("    " +strTurn + " busted!");
            System.out.println( "** You win $" + (wager) + " and your wager back! **");
            System.out.println("========= RESULTS ==========\n");
            setWallet(wager*2);

            gameover = true;

        }

    }

    public void hasWon(char turn) {

        System.out.println("\n=====hasWon()=====");
        System.out.println("Player total: " + p_total);
        System.out.println("Dealer total: " + d_total);
        System.out.println("==================\n");

        if (p_total > d_total) {

            System.out.println();
            System.out.println("Your total is " + p_total + ".");
            System.out.println("His total is " + d_total + ".");
            System.out.println();
            System.out.println("=======");
            System.out.println("YOU WIN!");
            System.out.println( "** You win your money back plus $" + (wager) + " **");
            System.out.println("=======");
            setWallet(wager);
            getWallet();
        } else if (p_total == d_total) {

            System.out.println();
            System.out.println("Your total is " + p_total + ".");
            System.out.println("His total is " + d_total + ".");
            System.out.println();
            System.out.println("=======");
            System.out.println("YOU PUSH!");
            System.out.println( "** You push and receive your $" + (wager) + " back **");
            System.out.println("=======");
            setWallet(wager);
            getWallet();

        } else if (p_total < d_total) {

            System.out.println();
            System.out.println("Your total is " + p_total + ".");
            System.out.println("His total is " + d_total + ".");
            System.out.println();
            System.out.println("=======");
            System.out.println("YOU LOSE!");
            System.out.println("** The dealer snatches up your $" + wager +" with a snicker **");
            System.out.println("=======");

        }
    }

    public Card[] initial() {

        // initial start

        Card[] deck = buildDeck();

        setWager();

        getTurn(turn); //player

        getCard(turn, deck);

        getCard(turn, deck);

        getTurn(turn); //dealer

        getCard(turn, deck);

        System.out.println(strTurn + " currently has " + getTotal(turn) );

        isBlackjack('p');

        initial = true;

        if (!gameover) { // if player didn't have outright BJ (dealer has a face card)


            getCard(turn, deck);

            getTurn(turn); //player

        }

        initial = false;

        return deck;
    }

    public void isBlackjack (char turn) {

        System.out.println("\n========isBlackjack()=========");
        System.out.println("Turn = " + turn);
        System.out.println("getTotal('p') = " + getTotal('p'));
        System.out.println("getTotal('d') = " + getTotal('d'));
        System.out.println("===============================\n");

        if (turn == 'p' && getTotal('p') == 21 && getTotal('d') < 10){ //outright player BJ

            // System.out.println("Turn is: " + turn + " and getTotal('p') is " + getTotal('p') + " and getTotal('d') is: " + getTotal('d'));

            System.out.println("=============");
            System.out.println("Player gets black-jack!");
            System.out.println( "** You win $" + (wager * 3.5) + " and your wager back! **");
            System.out.println("=============");
            setWallet(wager + (wager * 3.5));

            gameover = true;


        }

        else if (turn == 'd' && getTotal('p') == 21 && getTotal('d') < 21 && initial == true){

            System.out.println("============="); //after second card is revealed
            System.out.println("Player gets black-jack!");
            System.out.println( "** You win $" + (wager * 3.5) + " and your wager back! **");
            System.out.println("=============");
            setWallet(wager + (wager * 3.5));

            gameover = true;

        }
        else if (turn == 'd' && getTotal('p') < 21 && getTotal('d') == 21) {
            // System.out.println("Turn is: " + turn + " and getTotal('p') is " + getTotal('p') + "and getTotal('d') is: " + getTotal('d'));

            System.out.println("=============");
            System.out.println("Dealer gets black-jack (and you automatically lose).");
            System.out.println("** The dealer snatches up your $" + wager +" with a snicker **");
            System.out.println("=============");

            gameover = true;

        }

        isBlackjack = false;
    }


    public int isAce (char player) {

//        System.out.println("player is: " + player + " and their total is: " + getTotal(player));

        if (getTotal(player) >= 11)
        {
            return 1;

        }
        else
        {
            return 11;

        }
    }

    public boolean isValid(double wager) {

        if (wager > wallet) //
        {
            System.out.println("Please enter only an amount that you have within your wallet!");
            return false;
        }
        else if (wager < 0 ) {
            System.out.println("Please enter only an amount that you have within your wallet!");
            return false;
        }

        return true;
    }

    public void newGame() {

            System.out.println( "**You motion to continue playing blackjack to the dealer.**" );

            cardCount = 0;
            p_total = 0;
            d_total = 0;
            turn = 'd';
            initial = false;
            isBlackjack = false;
            gameover = false;
            numTurn = numTurn + 1;

    }

    public Card[] shuffleDeck( Card[] unshuffled ) //shuffles cards
	{
	Card[] shuffled = new Card[52]; 

	for (int x = 0; x < 52; x++ )
	{
		Random r = new Random();
	
		int rand1 = r.nextInt(52);

        while(alreadyUsed(unshuffled[rand1])) {


        }

        int rand2 = r.nextInt(52);


        System.out.println("Unshuffled: " + unshuffled[rand2]);
        System.out.println("Shuffled: " + unshuffled[rand2]);


        Card temp = unshuffled[rand1];
		shuffled[rand1] = unshuffled[rand2];
        unshuffled[rand2] = temp;
	}

        System.out.println( "The dealer looks menacingly at you as the shuffle the deck." );

		return shuffled;

	}

    public String suits (Card deck) {

        if (deck.suit.equals("diamonds")) {
            return "♦";
        }
        else if (deck.suit.equals("hearts")) {
            return "♥";
        }
        else if (deck.suit.equals("clubs")) {
            return "♣";
        }
        else {
            return "♠";
        }

    }

    public void Total (char turn, int turn_total) {

        if (turn == 'p') {
            p_total = p_total + turn_total;

        }
        else {
            d_total = d_total + turn_total;
        }
    }

    /* public void usedCards( Card deck ) {

		Card u = new Card();
		
		u.value = deck.value;
		u.suit = deck.suit;
		u.name = deck.name;
		
		usedCards[used] = u;
		
		System.out.println(usedCards[used].value);
		System.out.println(usedCards[used].suit);
		System.out.println(usedCards[used].name);
		
		used = used + 1;
		
	
	} */

    public double getWallet() {

        return wallet;

    }

    public void setWager() {

        Scanner keyboard = new Scanner(System.in);

        do { //asks user to input a wager before they can play (cannot play without money)
            System.out.println("You currently have $" + getWallet() + " available. Wager: > ");

            while (!keyboard.hasNextDouble()) { // ensures that the input is a number
                System.out.println("Please enter only numbers, thanks!");
                keyboard.next();
            }


            wager = keyboard.nextDouble();

        } while(!isValid(wager));

        setWallet(-wager);
        System.out.println("Your wallet shrinks as you remove $" + (wager) + " from your wallet.");

        // keyboard.nextLine();

    }

    public void setWallet(double wager) {

        wallet = wallet + wager;


        if (wager > 0 ) {
            System.out.println("You stuff $" + wager + " in your wallet.");
        }

    }

}
