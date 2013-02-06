// The "Deck" class.
import java.awt.*;
import java.util.*;
import hsa.Console;

public class Deckmain
{
    static Console c;
    // The output console

    public static void main (String[] args)
    {
	c = new Console (13, 155);//sets console size
	deck blah = new deck ();//creates new deck
	boolean flipped = false; //deck is flipped
	blah.show (c);//shows deck
	c.setCursor (15, 1);
	char choice = displaymenu ();//display menu
	while (choice != 'q')
	{
	    if (choice == '1')
	    {
		blah.shuffle ();
		c.clear ();//clear screen
		blah.show (c);//shows deck
		choice = displaymenu ();
	    }
	    if (choice == '2')
	    {
		Card temp = new Card ();
		c.setCursor (11, 17); // -> Aesthetic purposes
		c.print ("Random card (y/n)?");//Random card added?
		char random = c.getChar ();
		while (random != 'y' && random != 'n')
		{
		    random = c.getChar ();
		}
		if (random == 'y')
		    temp = new Card ((int) (Math.random () * 52 + 1));//Generates random card
		else
		{
		    c.setCursor (11, 17);
		    c.print ("What suit(SHDC)?       ");
		    char suit = c.getChar ();//gets suit
		    int suitnum = 0;
		    while (suit != 's' && suit != 'S' && suit != 'h' && suit != 'H' && suit != 'd' && suit != 'D' && suit != 'c' && suit != 'C')//checks if input is valid
		    {
			suit = c.getChar ();
		    }
		    if (suit == 's' || suit == 'S')//sets suit number to proper int values from char
			suitnum = 1;
		    if (suit == 'h' || suit == 'H')
			suitnum = 2;
		    if (suit == 'd' || suit == 'D')
			suitnum = 3;
		    if (suit == 'c' || suit == 'C')
			suitnum = 4;
		    c.setCursor (11, 17);
		    c.print ("What rank(1-13)?       ");//gets rank
		    c.setCursor (12, 17);
		    int rank = c.readInt ();
		    while (rank < 1 && rank > 13)//checks if rank number is correct
		    {
			c.setCursor (12, 17);
			rank = c.getChar ();
		    }
		    temp = new Card (suitnum, rank);
		}
		if (flipped)//flips added card if deck is already flipped
		    temp.flip ();
		blah.add (temp);
		c.clear ();
		blah.show (c);
		choice = displaymenu ();
	    }
	    if (choice == '3')
	    {
		c.setCursor (11, 33);// code here to get desired suit and rank is the same as above for choice 2
		c.print ("What suit(SHDC)?       ");
		char suit = c.getChar ();
		int suitnum = 0;
		while (suit != 's' && suit != 'S' && suit != 'h' && suit != 'H' && suit != 'd' && suit != 'D' && suit != 'c' && suit != 'C')
		{
		    suit = c.getChar ();
		}
		if (suit == 's' || suit == 'S')
		    suitnum = 1;
		if (suit == 'h' || suit == 'H')
		    suitnum = 2;
		if (suit == 'd' || suit == 'D')
		    suitnum = 3;
		if (suit == 'c' || suit == 'C')
		    suitnum = 4;
		c.setCursor (11, 33);
		c.print ("What rank(1-13)?       ");
		c.setCursor (12, 33);
		int rank = c.readInt ();
		while (rank < 1 && rank > 13)
		{
		    c.setCursor (12, 33);
		    rank = c.readInt ();
		}
		c.clear ();
		c.setCursor (11, 33);
		c.print ("Your card is at:");
		c.setCursor (12, 32);
		c.print (blah.find (suitnum, rank));
		blah.show (c);
		choice = displaymenu ();//display menu
	    }
	    if (choice == '4')
	    {
		Card temp = blah.deal (1);//deals card from top of the deck
		c.clear ();
		blah.show (c);
		temp.show (Graphics g, 20, 30);
		c.setCursor (15, 1);
		choice = displaymenu ();//display menu

	    }
	    if (choice == '5')
	    {
		blah.quicksort ();
		c.clear ();
		blah.show (c);
		choice = displaymenu ();//display menu
	    }
	    if (choice == '6')
	    {
		blah.selectionsort ();
		c.clear ();
		blah.show (c);
		choice = displaymenu ();//display menu
	    }
	    if (choice == '7')
	    {
		blah.flip ();//flips cards
		if (flipped == true)//sets boolean for the flip
		    flipped = false;
		else if (flipped == false)
		    flipped = true;
		c.clear ();
		blah.show (c);
		choice = displaymenu ();//display menu
	    }

	}
	c.close ();
	// Place your program here.  'c' is the output console
    } // main method


    public static char displaymenu ()//display menu
    {
	c.setCursor (10, 1);
	c.print ("1. Shuffle\t2.Add\t\t3.Find\t\t4.Deal\t\t5.Quick Sort\t\t6. Selection Sort\t\t7.Flip\t\tq.Quit");
	char choice = c.getChar ();
	return (choice);
    }
}
class deck
{
    private ArrayList cards = new ArrayList ();//initializes arraylist for deck
    public deck ()
    {

	for (int x = 1 ; x <= 52 ; x++)//adds all cards in deck from cardnumber 1 to 52
	{
	    Card temp = new Card (x);
	    cards.add (temp);
	}
    }


    public void show (Console c)//shows deck
    {
	for (int x = 0 ; x < cards.size () ; x++)
	{
	    Card temp = (Card) (cards.get (x));
	    if (x >= 52)//aesthetics, skips to next line if more than 52 cards in a line
		temp.show (c, (x * 20) - 1040 * (int) (x / 52) + 120, 10 + 40 * (int) (x / 52));
	    else
		temp.show (c, (x * 20) + 120, 10);

	}
    }


    public void add (Card temp)//adds cards, limit of 104 cards (or two lines)
    {
	if (cards.size () != 104)
	    cards.add (temp);
    }


    public Card deal (int position)//deals first card
    {

	Card drawn = (Card) (cards.get (position - 1));//creates duplicate card in the desired draw position
	if (cards.size () != 1)
	    cards.remove (position - 1);//removes card at position
	return (drawn);

    }


    public void flip ()//flips cards in the deck
    {
	for (int x = 0 ; x < cards.size () ; x++)//goes through all cards
	{
	    Card tempcard = (Card) (cards.get (0));//creates temp card
	    tempcard.flip ();//flips temp card
	    cards.remove (0);//removes temp card position from deck
	    cards.add (tempcard);//adds temp card to end
	}
    }


    public String find (int suit, int rank)//finds a card in the deck
    {
	String positions = "";//creates string for positions
	for (int x = 0 ; x < cards.size () ; x++)//scanrs through deck
	{
	    Card tempcard = (Card) (cards.get (x));
	    if (tempcard.returnsuit () == suit && tempcard.returnrank () == rank)//if card at position is equal to the card in the deck, adds position value to positions string
	    {
		positions = positions + (" " + (x + 1));
	    }

	}
	return (positions);
    }


    public void shuffle ()
    {
	Card temp = new Card ();//initializes new card
	for (int x = 1 ; x < 200 ; x++)//repeats 200 times
	{
	    int random = (int) (Math.random () * cards.size ());//generates random position
	    temp = (Card) (cards.get (random));//creates duplicate card for the card at said position
	    cards.remove (random);//removes card from position
	    cards.add (temp);//adds card to the end of the deck
	}
    }


    public void quicksort ()//sorts by number, suits not cared about
    {
	int counter = 0;//counts for position in deck to place card
	for (int x = 1 ; x <= 13 ; x++)//loop for card values
	{
	    for (int y = 0 ; y < cards.size () ; y++)//scans through deck
	    {
		Card temp = (Card) (cards.get (y));//sets temp card for the card in deck at position
		if (temp.returnrank () == x)//if the rank of the card is equal to x, then swaps if for the current position (counter)
		{
		    Collections.swap (cards, counter, y);
		    counter++;
		}
	    }
	}
    }


    public void selectionsort ()
    {
	int counter = 0;//counts for position in deck to place card
	for (int z = 1 ; z <= 4 ; z++)//loops for card suit
	{
	    for (int x = 1 ; x <= 13 ; x++)//loops for card rank
	    {
		for (int y = 0 ; y < cards.size () ; y++)//scans through deck
		{
		    Card temp = (Card) (cards.get (y));//sets temp card for the card in deck at position
		    if (temp.returnrank () == x && temp.returnsuit () == z)//if the rank of the card is equal to x and suit is equal to z, then swaps if for the current position (counter)
		    {
			Collections.swap (cards, counter, y);
			counter++;
		    }
		}
	    }
	}

    }
}


