import java.util.Scanner;

public class BlackjackRunner {

	public static void main( String[] args ) {
        String new_game = "yes";
        int hit_counter = 0;

        Scanner keyboard = new Scanner(System.in);
        BlackjackClass bj = new BlackjackClass();

        System.out.println("+========================================+");
        System.out.println("|  Welcome to Jeff's Blackjack program!  |");
        System.out.println("+========================================+\n");

        while (new_game.equals("yes")) {

            Card[] deck = bj.initial();

            String choice = "";
            hit_counter = 0;

            System.out.println("==========GAMEOVER==========");
            System.out.println("When: Checking player's BJ");
            System.out.println("True/False: " + bj.gameover);
            System.out.println("============================");

            player:

               do {

                   if (bj.gameover) {

                       break player;
                   }

                   System.out.println("==========GAMEOVER==========");
                   System.out.println("When: Checking player's BJ");
                   System.out.println("True/False: " + bj.gameover);
                   System.out.println("============================");

                    while (!choice.equals("hit") && !choice.equals("stay")) {

                        System.out.println(bj.strTurn + " currently has " + bj.getTotal(bj.turn));
                        System.out.println("Would you like to \"hit\" or \"stay\"?");
                        choice = keyboard.nextLine();

                    }

                    if (choice.equals("hit")) {
                        hit_counter = hit_counter + 1;
                        System.out.println("Hit #" + hit_counter + " Player chose to hit.");

                        bj.getCard(bj.turn, deck);

                        if (bj.gameover) {

                            break player;
                        }

                        System.out.println(bj.strTurn + " currently has " + bj.getTotal(bj.turn));

                        bj.hasBust('p');

                        System.out.println("==========GAMEOVER==========");
                        System.out.println("When: After player hits");
                        System.out.println("True/False: " + bj.gameover);
                        System.out.println("Choice: " + choice);
                        System.out.println("============================");

                        if (bj.gameover) {

                            break player;
                        }

                            System.out.println("Would you like to \"hit\" or \"stay\"?");
                            choice = keyboard.nextLine();

                    }

                    if (choice.equals("stay")) {
                        System.out.println("Player chose to stay.");
                        break player;
                    }

                   bj.hasBust('p');

                   System.out.println("==========GAMEOVER==========");
                   System.out.println("When: Third line (repeats)  ");
                   System.out.println("True/False: " + bj.gameover);
                   System.out.println("Which turn? " + bj.numTurn);
                   System.out.println("How many times hit? " + hit_counter);
                   System.out.println("Player's score? " + bj.p_total);
                   System.out.println("Does choice equal hit?: " + (choice.equals("hit") ) );
                   System.out.println("And it doesn't also say stay?: " + !choice.equals("stay") ) ;
                   System.out.println("============================");

                } while (choice.equals("hit") && !choice.equals("stay"));


            dealer_initial:

            do  {

                //System.out.println("(End of player's turn flag) Is it game over? " + bj.gameover);
                if (bj.gameover) {

                    System.out.println("==========GAMEOVER==========");
                    System.out.println("When: Dealer's Turn  ");
                    System.out.println("True/False: " + bj.gameover);
                    System.out.println("Which turn? " + bj.numTurn);
                    System.out.println("============================");

                    break dealer_initial;
                }

                bj.getTurn(bj.turn); // Dealer's turn.
                hit_counter = 1;

                System.out.println(bj.strTurn + " reveals his hidden card.");
                System.out.println(bj.hidden_card_display);
                System.out.println(bj.strTurn + " currently has " + bj.getTotal(bj.turn));

                bj.isBlackjack('d'); //checks to see if dealer has blackjack OR player has a BJ AND dealer is less than 21

            } while( bj.gameover );

            // System.out.println("bj.getTotal('p') <= 21 && bj.getTotal('d')  < 17) player: " + bj.getTotal('p') + " dealer: " + bj.getTotal('d'));

            dealers_turn:
            while ( bj.getTotal('p') <= 21 && bj.getTotal('d')  < 17) {


                if (bj.gameover) {

                    break dealers_turn;
                }

                System.out.println("Hit #" + hit_counter + " Dealer chose to hit.\n");
                hit_counter = hit_counter + 1;
                bj.getCard(bj.turn, deck);

                System.out.println(bj.strTurn + " currently has " + bj.getTotal(bj.turn) );
                bj.hasBust('d');

                System.out.println("==========GAMEOVER==========");
                System.out.println("When: Checking dealer's bust");
                System.out.println("True/False: " + bj.gameover);
                System.out.println("============================\n");


            }


            if (!bj.gameover) {
                System.out.println("Dealer chose to stay.");
                bj.hasWon(bj.turn);

            }

            if (bj.getWallet() < 1) {
                System.out.println("======================================================================");
                System.out.println("** The casino bouncers kick you to the curb, because you are BROKE! **");
                System.out.println("======================================================================\n");
                bj.wallet = 0;
                break;
            }

            System.out.println("You currently have $" + bj.getWallet() + " available.");

            do {

                Scanner kb = new Scanner(System.in);
                System.out.println("Would you like to continue playing? \"yes\" or \"no\" ?");
                new_game = kb.next();
                //System.out.println("yes".equals(new_game)); // DEBUG
                // System.out.println("no".equals(new_game)); // DEBUG

            } while( !new_game.equals("yes") && !new_game.equals("no"));

            if (new_game.equals("yes") ) {
                bj.newGame();

            }



        }

        System.out.println( "Thanks for playing my program!");
        System.out.println( "You won $" + bj.getWallet() + " total! Don't spend it all in one place!");
    }
}

