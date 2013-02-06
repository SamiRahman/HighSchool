// The "CrazyEights" class.
import java.awt.*;
import hsa.Console;
public class CrazyEights
{
    static Console c;           // The output console
    static int[] deck, player, computer; //card arrays
    static int pile, suit; //discard pile, current suit (0-3)
    static boolean deckEmpty;
    public static void main (String[] args)
    {
	//setup console
	c = new Console (30, 100, "Crazy Eights");
	intro (); //splash page, instructions
	char playAgain;
	do
	{
	    c.setFont (new Font ("Arial", java.awt.Font.PLAIN, 14));
	    game ();
	    c.setCursor (30, 32);
	    playAgain = c.getChar ();
	}
	while (playAgain == 'y' || playAgain == 'Y');
	c.close ();
    }

    public static void game ()
    {
	getDeck (); //setup deck
	player = makeHand ();   //deal player
	computer = makeHand (); //and computer's hand
	pile = getCard (); //add one card to discard pile
	while (!emptyHand (1) && !emptyHand (2) && !deckEmpty)
	{
	    render ();
	    playerPlays ();
	   
	    computerPlays ();
	}
	render ();
	printMessage ("Game over. Play again [y/n]? _");
	c.clearRect (350, 215, 101, 151);
	c.setFont (new Font ("Arial", java.awt.Font.BOLD, 24));
	if (emptyHand (1))
	{ //player won
	    c.drawString ("YOU WIN!", c.getWidth () / 2 - 50, c.getHeight () / 2 + 10);
	}
	else if (emptyHand (2))
	{ //computer won
	    c.drawString ("YOU LOSE!", c.getWidth () / 2 - 50, c.getHeight () / 2 + 10);
	}
	else
	{
	    c.drawString ("IT'S A TIE!", c.getWidth () / 2 - 50, c.getHeight () / 2 + 10);
	}
    }

    public static void playerPlays ()
    {
	//check hand for legal cards, label them
	int x = 1, y = 0;
	int[] cards = new int [52];
	c.setCursor (29, 1);
	c.print ("", 100);
	for (int i = 0 ; i < player.length ; i++)
	{
	    if (player [i] > 0)
	    {
		if (cardLegal (player [i]))
		{
		    c.setCursor (28, 6 + y * 5);
		    c.print (x);
		    cards [x] = i;
		    x++;
		}
		y++;
	    }
	}
	if (x > 1)
	{ //has legal cards
	    printMessage ("Enter a card number to play: ");
	    c.setCursor (30, 32);
	    int playCard = c.readInt ();
	    while (playCard < 1 || playCard + 1 > x)
	    {
		printMessage ("Invalid number. Enter a card number to play: ");
		c.setCursor (30, 48);
		playCard = c.readInt ();
	    }
	    pile = player [cards [playCard]];
	    player [cards [playCard]] = 0;
	    if ((pile) % 13 == 8)
	    { //card is eight, change suit
		printMessage ("Enter the first letter of the suit you want to change to: ");
		c.setCursor (30, 61);
		String newSuit = c.readLine ();
		if (newSuit.equalsIgnoreCase ("s"))
		{
		    pile = 8;
		}
		else if (newSuit.equalsIgnoreCase ("h"))
		{
		    pile = 21;
		}
		else if (newSuit.equalsIgnoreCase ("d"))
		{
		    pile = 34;
		}
		else if (newSuit.equalsIgnoreCase ("c"))
		{
		    pile = 47;
		}
	    }
	}
	else
	{ //no legal cards
	    printMessage ("You can't play any of your cards. Press any key to draw a card.");
	    c.getChar ();
	    boolean filled = false;
	    int i = 0;
	    while (!filled && i < player.length)
	    {
		if (player [i] == 0)
		{
		    player [i] = getCard ();
		    if (player [i] == 0)
		    { //deck ran out, quit game
			deckEmpty = true;
		    }
		    filled = true;
		}
		i++;
	    }
	}
	render ();
    }

    public static void computerPlays ()
    {
	if (!emptyHand (1))
	{
	    printMessage ("Press any key to let computer play...");
	    c.getChar ();
	    int eight = -1;
	    boolean played = false;
	    for (int i = 0 ; i < computer.length && !played ; i++)
	    {
		if (computer [i] > 0)
		{
		    if (cardLegal (computer [i]))
		    {
			if ((computer [i]) % 13 == 8)
			{ //card is eight, save it
			    eight = i;
			}
			else
			{ //play card
			    pile = computer [i];
			    computer [i] = 0;
			    played = true;
			}
		    }
		}
	    }
	    if (!played)
	    {
		if (eight != -1)
		{ //play the eight
		    pile = computer [eight];
		    computer [eight] = 0;
		    //change suit to dominant hand suit
		    int spades = 0, hearts = 0, diamonds = 0, clubs = 0;
		    for (int i = 0 ; i < computer.length ; i++)
		    {
			if (computer [i] > 0)
			{
			    int suitnum = (computer [i]) / 13;
			    if (suitnum == 0) // spades
				spades++;
			    else if (suitnum == 1) // hearts
				hearts++;
			    else if (suitnum == 2) // diamonds
				diamonds++;
			    else if (suitnum == 3) // clubsm
				clubs++;
			}
		    }
		    if (spades * 3 > hearts + diamonds + clubs)
			pile = 8;
		    else if (hearts * 3 > spades + diamonds + clubs)
			pile = 21;
		    else if (diamonds * 3 > spades + hearts + clubs)
			pile = 34;
		    else if (clubs * 3 > spades + hearts + diamonds)
			pile = 47;
		}
		else
		{ //draw card    
		    boolean filled = false;
		    int i = 0;
		    while (!filled && i < computer.length)
		    {
			if (computer [i] == 0)
			{
			    computer [i] = getCard ();
			    if (computer [i] == 0)
			    {
				deckEmpty = true;
			    }
			    filled = true;
			}
			i++;
		    }
		}
	    }
	}
	render ();
    }

    public static void printMessage (String message)
    {
	c.setCursor (30, 3);
	c.print (message, 96);
    }

    public static boolean cardLegal (int card)  //check whether card matches suit, rank of pile or is eight
    {
	if ((card) % 13 == 8)
	{ //card is 8
	    return true;
	}
	else if ((card) / 13 == (pile) / 13)
	{ //card matches suit
	    return true;
	}
	else if ((card) % 13 == (pile) % 13)
	{ //card matches rank
	    return true;
	}
	return false;
    }

    public static boolean emptyHand (int whichHand)  //check if specifed hand is empty (1 = player, 2 = computer)
    {
	int[] hand;
	if (whichHand == 1)
	    hand = player;
	else
	    hand = computer;
	for (int x = 0 ; x < hand.length ; x++)
	{
	    if (hand [x] > 0)
		return false;
	}
	return true;
    }

    public static void getDeck ()  //make and shuffle deck array
    {
	deck = new int [52];
	for (int i = 0 ; i < deck.length ; i++)
	{
	    deck [i] = i + 1;
	}
	for (int x = 0 ; x < deck.length * 2 ; x++) // twice as many swaps as cards
	{
	    int ran1 = (int) (Math.random () * deck.length); // select random cards
	    int ran2 = (int) (Math.random () * deck.length);
	    int temp = deck [ran1]; // swap them
	    deck [ran1] = deck [ran2];
	    deck [ran2] = temp;
	}
	deckEmpty = false;
    }

    public static int[] makeHand ()  //deal top 5 cards of deck to hand
    {
	int[] hand = new int [52];
	for (int i = 0 ; i < 5 ; i++)
	{
	    hand [i] = getCard ();
	}
	return hand;
    }

    public static int getCard ()  //retrieve top card from deck
    {
	int index = 0, value = 0;
	while (index < deck.length && deck [index] == 0)
	    index++;
	if (deck.length > index)
	{
	    value = deck [index];
	    deck [index] = 0;
	}
	return value;
    }

    public static String shownValue (int card)  // convert card rank to shown rank
    {
	int val = card % 13;
	if (val == 0)
	    return "A";
	else if (val <= 9)
	    return "" + val;
	else if (val == 10)
	    return "J";
	else if (val == 11)
	    return "Q";
	else if (val == 12)
	    return "K";
	return "X";
    }

    public static String shownSuit (int card)  // convert suit 1-4 to SHDC
    {
	int suitnum = card / 13;
	if (suitnum == 0) // spades
	    return "\u2660";
	else if (suitnum == 1) // hearts
	    return "\u2665";
	else if (suitnum == 2) // diamonds
	    return "\u2666";
	else if (suitnum == 3) // clubsm
	    return "\u2663";
	return "X";
    }

    public static void eraseCard (int x, int y)
    {
	c.setColor (Color.white);
	c.fillRoundRect (x - 5, y - 5, 110, 160, 10, 10);
    }

    public static void drawCard (int value, int x, int y, boolean hidden)
    {
	String rank = shownValue (value);
	String suit = shownSuit (value);
	if (hidden) // card is face down
	{
	    c.setColor (new Color (190, 213, 226));
	    c.fillRoundRect (x, y, 100, 150, 10, 10);
	    c.setColor (Color.black);
	    c.drawRoundRect (x, y, 100, 150, 10, 10);
	}
	else // card is face up
	{
	    c.setColor (Color.white);
	    c.fillRoundRect (x, y, 100, 150, 10, 10);
	    c.setColor (Color.black);
	    c.drawRoundRect (x, y, 100, 150, 10, 10);
	    if (suit.equals ("\u2665") || suit.equals ("\u2666")) // heart or spade
		c.setColor (Color.red);
	    c.drawString (rank + suit, x + 10, y + 15);
	    c.drawString (rank + suit, x + 75, y + 140);
	    c.fillRoundRect (x + 20, y + 30, 60, 90, 10, 10);
	    c.setColor (Color.black);
	}
    }

    public static void intro ()
    {
	c.clear ();
	c.setFont (new Font ("Arial", java.awt.Font.BOLD, 48));
	c.drawString ("CRAZY EIGHTS", c.getWidth () / 2 - 180, c.getHeight () / 2 - 10);
	c.setFont (new Font ("Arial", java.awt.Font.PLAIN, 14));
	c.drawString ("Press any key to begin.", c.getWidth () / 2 - 50, c.getHeight () / 2 + 50);
	c.getChar ();
    }

    public static void render ()
    {
	c.clear ();
	int x = 0;
	for (int i = 0 ; i < computer.length ; i++)
	{ //computer's hand
	    if (computer [i] != 0)
	    {
		drawCard (computer [i], 670 - x * 40, 30, true);
		x++;
	    }
	}
	x = 0;
	for (int i = 0 ; i < player.length ; i++)
	{ //player's hand
	    if (player [i] != 0)
	    {
		drawCard (player [i], 30 + x * 40, 400, false);
		x++;
	    }
	}
	//discard pile
	drawCard (pile, 350, 215, false);

    }
}
