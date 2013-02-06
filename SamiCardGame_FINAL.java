// The "SamiCardGame" class.
// Sami Rahman, Period 2
// Card Game Assignment: "WAR"
// TIK2O3 - Mr. Jay
// Due: Monday November 10 2008

import java.awt.*;
import hsa.Console;


public class SamiCardGame_FINAL
{
    static Console c;       // The output console

    //declare static variables
    static int playerPoints, opponentPoints;
    static char playAgain;
    static String player, opponent;

    public static void main (String[] args)  //main method
    {
	c = new Console (100, 200); //setup console + setup window size

	boolean playerwon; 
	introduction (); //call introduction
	rules (); //call rules
	story (); //call story
	//char playAgain = 'y';

	do
	{
	    c.clear (); //clears screen
	    playerwon = game (); //calls game
	    result (playerwon); //calls result
	    playAgain = forceGetChar ("ynYN"); //calls force method - forces user to enter either y,Y,n, or N before proceeding
	}
	while (playAgain == 'y' || playAgain == 'Y'); //run program again if user hits 'y' or 'Y' to keep playing

	c.clear (); //clears screen
	goodbye (); //calls goodbye method
	c.close (); //closes window
    }


    public static boolean game ()  //actual game -- returns boolean value: winner
    {
	int playerPoints = 26, opponentPoints = 26; //both contestants start with 26 points

	c.setColor (new Color (144, 99, 64)); //brown colour
	c.fillRect (0, 0, 5000, 5000); //fill background

	c.print ("Please enter the PLAYER's name: "); // input name
	String player = c.readLine ();
	c.print ("Please enter the OPPONENT's name: "); // ""
	String opponent = c.readLine ();

	while (playerPoints < 52 && opponentPoints < 52) //get out of loop when anyone reaches 52 points
	{
	    c.setColor (new Color (144, 99, 64)); //brown colour
	    c.fillRect (0, 0, 5000, 5000); //reset background

	    //declare cards
	    int playerCard = (int) (Math.random () * 13 + 1); //generates player's random card
	    int mysuit = (int) (Math.random () * 4 + 1); //generates player's random suit
	    int opponentCard = (int) (Math.random () * 13 + 1); //generates opponent's random card
	    int compsuit = (int) (Math.random () * 4 + 1); //generates opponent's random suit

	    //these are the cards used for war battle
	    int playerCard2 = (int) (Math.random () * 13 + 1); //generates player's random card
	    int mysuit2 = (int) (Math.random () * 4 + 1); //generates player's random suit
	    int opponentCard2 = (int) (Math.random () * 13 + 1); //generates opponent's random card
	    int compsuit2 = (int) (Math.random () * 4 + 1); //generates opponent's random suit

	    c.setColor (Color.black); //revert back to black
	    opponentScoreArea (opponent, opponentPoints); //opponent's score keeping and name displaying method (name, points)
	    playerScoreArea (player, playerPoints); //player's ""

	    c.setColor (Color.green); //set color to green
	    c.setFont (new Font ("Rockwell", java.awt.Font.PLAIN, 20)); // set font type
	    c.drawString ("<Pretend the 'return' key is your gun. Hit it to move on in this war>", 50, 520);

	    nameInfo (); //call nameInfo method -- displays info at bottom right corner
	    sideGraphics (); //calls sideGraphic method -- displays title at side

	    c.setFont (new Font ("Arial", java.awt.Font.BOLD, 14)); //resets font
	    drawHiddenCard (230, 300); //draw player's hidden card
	    drawHiddenCard (230, 50); // draw opponent's hidden card
	    c.getChar (); //hit a key before revealing the cards (turning them over)

	    //reveal (turn over) the cards
	    drawCard (shownValue (playerCard), shownSuit (mysuit), 230, 300); //player's revealed card
	    drawCard (shownValue (opponentCard), shownSuit (compsuit), 230, 50); //opponent's revealed card


	    if (playerCard > opponentCard) // player's card is higher
	    {
		c.drawString ("You WON this battle, Sarg!~ +1 Player", 160, 240);
		c.drawString ("<Hit any key to advance to the next battle>", 140, 265);
		playerPoints++; //add one to player's points
	    }

	    else if (opponentCard > playerCard) // opponent's card is higher
	    {
		c.drawString ("You LOST this battle!~ +1 Opponent", 170, 240);
		c.drawString ("<Hit any key to advance to the next battle>", 150, 265);
		opponentPoints++; //add one to opponent's points
	    }

	    else if (opponentCard == playerCard) // both our cards are tied -- proceed to WAR!
	    {
		c.drawString ("TIME TO GO TO WAR! CHAAARGE!", 200, 240);
		c.drawString ("<Hit any key to advance to war with your troop>", 150, 265);
		c.getChar (); //hit any key to continue
		drawHiddenCard (250, 300); //player 's face down card 1
		drawHiddenCard (250, 50); //opponent's face down card 1

		c.getChar (); //hit any key to continue
		drawHiddenCard (300, 300); //player 's face down card 2
		drawHiddenCard (300, 50); //opponent's face down card 2

		c.getChar (); //hit any key to continue
		drawHiddenCard (350, 300); //player 's face down card 2
		drawHiddenCard (350, 50); //opponent's face down card 3

		c.getChar (); //hit any key to continue
		drawWarCard (shownValue (playerCard2), shownSuit (mysuit2), 400, 300); // face up WAR card - player
		drawWarCard (shownValue (opponentCard2), shownSuit (compsuit2), 400, 50); // face up WAR card - opponent

		c.setColor (new Color (144, 99, 64)); //same color as background
		c.fillRect (150, 220, 450, 70); //fill rect to write over old drawn string
		c.setColor (Color.black); //reset color to black for following text

		if (playerCard2 > opponentCard2) //player's war card is higher
		{
		    c.drawString ("VICTORY!! The " + player + "-ians won this fight!~ +2 Player", 170, 260);
		    playerPoints += 2; //add 2 to player's points

		}
		else if (opponentCard2 > playerCard2)
		{
		    c.drawString ("FATALITY!! The " + opponent + "-ians! won this fight!~ +2 Opponent", 170, 260);
		    opponentPoints += 2; //add 2 to opponent's points
		}
		else if (opponentCard2 == playerCard2)
		{

		    c.drawString ("DRAW: Both parties wave the white flag (no points awarded).", 170, 260);

		}

		opponentScoreArea (opponent, opponentPoints); //update score area
		playerScoreArea (player, playerPoints); //upadate score area
	    }

	    c.getChar (); //hit any key to continue
	} //end of while loop

	if (playerPoints >= 52) //if the player wins
	    return true; // player wins is true
	else //if the opponent wins
	    return false; //player wins is false -- opponent wins
    }


    public static void result (boolean playerwon)  //display result
    {
	c.getChar (); //hit any key to continue
	c.clear (); //clears screen

	c.setFont (new Font ("Impact", java.awt.Font.BOLD, 32)); //sets font type

	if (!playerwon) //if the opponent wins
	{
	    c.drawString ("DUMDUMDUMDUUUUM! You lost this WAR! You are given a second chance,", 100, 300);
	    c.setFont (new Font ("Impact", java.awt.Font.PLAIN, 32));
	    c.drawString ("Try again Sargeant? (Y/N): ", 200, 340);
	}

	else // if player wins
	{
	    c.drawString ("SUCCESS! You won this war! Your planet needs you again!", 200, 300);
	    c.setFont (new Font ("Impact", java.awt.Font.PLAIN, 32));
	    c.drawString ("Go back to War soldier? (Y/N): ", 200, 340);
	}

    }


    public static void introduction ()  //introduction!
    {
	c.setFont (new Font ("Algerian", java.awt.Font.BOLD, 52)); //sets font
	c.drawString ("Sami's  Awesome  WAR! <SAW>", 200, 300); //title
	c.setFont (new Font ("Papyrus", java.awt.Font.ITALIC, 26)); //sets font type
	c.drawString ("Press any key to see the rules", 400, 400);
	c.getChar (); //hit any key to continue
    }


    public static void rules ()  //rules method - displays rules
    {
	c.clear (); //clears the screen
	c.println ("Rules and Instructions for Sami's Awesome War <SAW>");
	c.println ("--------------------------------");
	c.println ("Players           2");
	c.println ("Age Range         6 +");
	c.println ("Setup time        ~2 mins");
	c.println ("Playing Time      2 - 10 mins");
	c.println ("Random chance     High");
	c.println ("Skills required   Counting");
	c.println ("\nSAW uses 2 standard Western fifty-two-playing card decks. One of which is given to each player");
	c.println ("Standard Card Values: Aces are considered the highest card then the King, Queen and so on till 2 which is the lowest.");
	c.println ("Each player starts with 26 points (52/2). The goal is to reach 52 before your opponent!");
	c.println ("Gameplay: Each player flips over the top card from their standard deck; whoever has the highest card takes the number of counter cards from the opponents pile equal to the number of standard cards played by the opponent.");
	c.println ("E.g. Bob draws and plays a 5 and Tom draws a 2. Since 5 > 2, Bob gets one point.");
	c.println ("After the standard cards are played, they are put back into the deck they came from and are inserted in a random place.");
	c.println ("WAR: In case of a tie, each player plays three face-down card and one face-up card, and there is a 'war' between these two face-up cards.");
	c.println ("If during the war, Bob draws a 6 and Tom plays an Ace, Tom wins and gets 2 points (2 pts. awarded for a war win.)");
	c.println ("If there is a tie during the war, a standstill occurs and no players lose or gain anything.");
	c.println ("\tWe hope you enjoy the game! :)");
	c.getChar ();
	c.println ("\n\t< HIT ANY KEY TO GO TO WAR, SOLDIER! >");
	c.getChar (); //hit any key to continue to game

    }


    public static void story ()  //story method - prints the story
    {
	c.clear (); //clears screen
	c.setFont (new Font ("Algerian", java.awt.Font.BOLD, 50));
	c.drawString ("The STORY...", 200, 350);
	c.setCursor (20, 25);
	c.println ("You are the lead Sargeant for the Canadian Armed Forces in World War XXVILMNOP.");
	c.println ("The Transformers have taken over Planet Earth with their evil, super-fast, flying robots.");
	c.println ("It is your duty to stop them from being terrorizing terrorists with air conditioning (some models only).");
	c.println ("Armed with 20th century tools of destruction and your faithful troop, you must take them down.");
	c.getChar (); //hit any key to continue
	c.println ("\nAre you ready? Press any key to head to the battlefield, Sarg.");

	c.getChar (); //hit any key to continue
	c.setCursor (1, 1); //reset cursor
    }


    public static void drawCard (String value, String suit, int x, int y)  //draws a card
    {
	c.setColor (Color.white); //set white colour
	c.fillRoundRect (x, y, 100, 150, 10, 10); //fill rect
	c.setColor (Color.black); //set black colour
	c.drawRoundRect (x, y, 100, 150, 10, 10); //black outline of card

	if (suit.equals ("\u2665") || suit.equals ("\u2666")) //heart or diamonds
	    c.setColor (Color.red);

	c.drawString (value + suit, x + 10, y + 15); //write suit num + suit
	c.drawString (value + suit, x + 75, y + 140); //write suit num + suit
	c.fillRoundRect (x + 20, y + 30, 60, 90, 10, 10); //fill in the inside of the card
	c.setColor (Color.black); //revert back to black
    }


    public static void drawWarCard (String value, String suit, int x, int y)  //draws the card used for WAR
    {
	c.setColor (Color.white);
	c.fillRoundRect (x, y, 100, 150, 10, 10); //draw first card
	c.setColor (Color.black);
	c.drawRoundRect (x, y, 100, 150, 10, 10); //draw second card

	if (suit.equals ("\u2665") || suit.equals ("\u2666")) //hearts or diamonds
	    c.setColor (Color.red);

	c.drawString (value + suit, x + 10, y + 15); //suit num + suit
	c.drawString (value + suit, x + 75, y + 140); //""
	c.fillRoundRect (x + 20, y + 30, 60, 90, 10, 10); //show war card
	c.setColor (Color.black); //reverts colour back to black
    }


    public static void drawHiddenCard (int x, int y)  //draw Hidden card
    {
	c.setColor (new Color (70, 164, 53)); //green
	c.fillRoundRect (x, y, 100, 150, 10, 10); //fill in the inside of the card
	c.setColor (Color.black);
	c.drawRoundRect (x, y, 100, 150, 10, 10); //black outline
	c.setColor (Color.white);
	c.drawRoundRect (x + 20, y + 30, 60, 90, 10, 10); //white interior outline
    }


    public static void opponentScoreArea (String name, int x)  //opponent scoreboard incl. NAME then SCORE under it
    {
	c.setFont (new Font ("Century Gothic", java.awt.Font.BOLD, 14));
	c.drawString (name + "-ians! (Opponent)", 10, 50); //name
	c.setFont (new Font ("Century Gothic", java.awt.Font.BOLD, 52));
	c.drawString ("" + x + " (52)", 10, 100); //score
    }


    public static void playerScoreArea (String name, int x)  //player ""
    {
	c.setFont (new Font ("Century Gothic", java.awt.Font.BOLD, 14));
	c.drawString (name + "-ians! (Player)", 10, 300); //name
	c.setFont (new Font ("Century Gothic", java.awt.Font.BOLD, 52));
	c.drawString ("" + x + " (52)", 10, 360); //score

    }


    public static void nameInfo ()  //displays name and assignment information in bottom right corner
    {
	c.setColour (new Color (126, 102, 176)); //set purple coloue
	c.setFont (new Font ("Georgia", java.awt.Font.BOLD, 26)); //set font type
	//draws the following information in the bottom right corner
	c.drawString ("Sami Rahman", 850, 570);
	c.drawString ("TIK2O3 - Mr. Jay", 850, 600);
	c.drawString ("Card Game Program - WAR", 850, 630);
    }


    public static char forceGetChar (String letters)  //force user to enter a certain character before proceeding
    {
	char playAgain;
	do
	{
	    playAgain = c.getChar ();
	}
	while (letters.indexOf (playAgain) == -1); //goes again when the certain character is not pressed

	return playAgain;
    }


    public static void goodbye ()  //displays goodbye message
    {
	c.drawString ("You have been decommissioned. Goodbye Sargeant.", 250, 280); //display goodbye meesage
	c.drawString ("< Hit any key to exit >.", 250, 320);
	c.getChar (); //hit any key to continue
    }


    public static void sideGraphics ()  //displays side text/title
    {
	c.setColour (new Color (255, 34, 34)); //sets a red colour
	c.setFont (new Font ("Algerian", java.awt.Font.ITALIC, 52)); //set font type
	c.drawString ("Sami's", 800, 80);
	c.drawString ("Awesome", 800, 265);
	c.drawString ("War!", 800, 450);
	c.setColor (Color.black); //reverts back to black
    }


    public static String shownValue (int val)  // convert card rank to shown rank
    {

	if (val == 1)
	    return "A";
	else if (val <= 10)
	    return "" + val;
	else if (val == 11)
	    return "J";
	else if (val == 12)
	    return "Q";
	else if (val == 13)
	    return "K";
	return "X"; //error check
    }


    public static String shownSuit (int suitNum)  // convert suit 1-4 to SHDC
    {
	if (suitNum == 1) // spades
	    return "\u2660";
	else if (suitNum == 2) // hearts
	    return "\u2665";
	else if (suitNum == 3) // diamonds
	    return "\u2666";
	else if (suitNum == 4) // clubs
	    return "\u2663";
	return "X"; //error check
    }


    public static String shownValue2 (int val)  // convert card rank to shown rank
    {

	if (val == 1)
	    return "A";
	else if (val <= 10)
	    return "" + val;
	else if (val == 11)
	    return "J";
	else if (val == 12)
	    return "Q";
	else if (val == 13)
	    return "K";
	return "X"; //error check
    }


    public static String shownSuit2 (int suitNum)  // convert suit 1-4 to SHDC
    {
	if (suitNum == 1) // spades
	    return "\u2660";
	else if (suitNum == 2) // hearts
	    return "\u2665";
	else if (suitNum == 3) // diamonds
	    return "\u2666";
	else if (suitNum == 4) // clubs
	    return "\u2663";
	return "X"; //error check
    }



} // SamiCardGame class



