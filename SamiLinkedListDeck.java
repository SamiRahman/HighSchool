import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.imageio.*; // allows image loading
import java.io.*; // allows file access
import java.util.*;
import java.util.Collections;
import java.util.ArrayList;

public class SamiLinkedListDeck extends JFrame
{

    JButton processBtn, showBtn, shuffleBtn, dealBtn, addBtn, searchBtn, sortBtn;
    Card dealtcard;
    JLabel label = new JLabel ("Hello");
    JLabel label2 = new JLabel ("");
    Deck deck = new Deck ();

    public SamiLinkedListDeck ()
    {
	shuffleBtn = new JButton ("Shuffle");
	dealBtn = new JButton ("Deal Card");
	addBtn = new JButton ("Add Card");

	sortBtn = new JButton ("Sort");

	searchBtn = new JButton ("Search");
	JLabel suit1 = new JLabel ("Suit(S/H/D/C):");
	JLabel rank1 = new JLabel ("Rank(2-10,J,Q,K,A):");

	// Connect button to listener class
	shuffleBtn.addActionListener (new ProcessBtnListener ());
	dealBtn.addActionListener (new ProcessBtnListener ());
	addBtn.addActionListener (new ProcessBtnListener ());
	sortBtn.addActionListener (new ProcessBtnListener ());


	JPanel content = new JPanel ();        // Create a content pane
	content.setLayout (new FlowLayout ());

	DrawArea table = new DrawArea (deck);
	JPanel west = new JPanel ();
	JPanel east = new JPanel ();
	west.setLayout (new GridLayout (15, 1));

	// Add button
	west.add (shuffleBtn);
	west.add (dealBtn);
	west.add (addBtn);
	west.add (sortBtn);

	west.add (label2);
	west.add (label);

	east.add (table);              // Add display area

	content.add (west, "West");
	content.add (east, "East");



	// 4... Set this window's attributes.
	setContentPane (content);
	pack ();
	setTitle ("Card Tester");
	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo (null);           // Center window.

    }


    class ProcessBtnListener implements ActionListener // Inner class for handling events
    {
	public void actionPerformed (ActionEvent e)
	{

	    label2.setText ("");
	    label.setText (""); //display text

	    if (e.getActionCommand ().equals ("Shuffle"))
	    {
		deck.shuffle ();
	    }
	    else if (e.getActionCommand ().equals ("Deal Card"))
	    {
		Card a = deck.deal (0);
		int x = a.getSuit ();
		String y = "";
		String q = "";

		if (a.getRank () >= 1 && a.getRank () <= 9) //set which card it really is and display the msg
		{
		    q = "" + (a.getRank () + 1);
		}
		else if (a.getRank () == 10)
		    q = "J";
		else if (a.getRank () == 11)
		    q = "Q";
		else if (a.getRank () == 12)
		    q = "K";
		else if (a.getRank () == 13)
		    q = "A";

		if (x == 1) //convert rank to string
		    y = "Spades";
		else if (x == 2)
		    y = "Hearts";
		else if (x == 3)
		    y = "Diamonds";
		else if (x == 4)
		    y = "Clubs";

		label2.setText ("Dealt: ");
		label.setText (q + " of " + y); //display text



	    }
	    else if (e.getActionCommand ().equals ("Add Card"))
	    {
		Card c = new Card ((int) (Math.random () * 52));
		deck.add (c);

	    }
	    else if (e.getActionCommand ().equals ("Sort"))
	    {
		deck.sort ();

	    }
	    repaint (); // update display
	}
    }


    public static void main (String[] args)
    {
	SamiLinkedListDeck window = new SamiLinkedListDeck ();
	window.setVisible (true);
    } // main method
}

class DrawArea extends JPanel
{
    Deck deck;

    public DrawArea (Deck deck)
    {
	this.deck = deck;
	this.setPreferredSize (new Dimension (800, 400)); // size
    }


    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);
	deck.print (g);
    }
}

class Deck   //new Deck
{
    protected Card first, last; //first and last cards
    protected int size = 0; //deck length
    private boolean firsttime = true;

    public Deck ()
    {
	size = 52; //set size of deck to 52
	for (int y = 0 ; y <= size ; y++)
	{
	    Card c = new Card (y);

	    if (y == 0) //first card
		first = c; // set the starting point for the list
	    else
		last.next = c; // connect the link to the end of the list

	    last = c; // update the ending point for the list
	}

    }


    public void add (Card c) 
    {
	if (size > 0) //if the deck isnt empty
	{
	    last.next = c; //adds new card to the end
	    last = c;
	    size++; //increases deck size
	}
	else
	{
	    first = c; //adds the new card to first
	    last = first;
	    size++; 
	}
    }


    public Card get (int x)  //gets a card
    {
	Card temp = first;

	if (size > 0)
	{
	    for (int y = 0 ; y < x ; y++) //loop til desired position
		temp = temp.next;
	}
	
	return temp; //return found card

    }


    public void shuffle ()
    {
	for (int x = 0 ; x < size ; x++)
	{
	    int num = (int) (Math.random () * size); //random #
	    add (deal (num)); //removing then adding
	}
    }


    public Card deal (int x)
    {
	Card c = get (x); 
	int length = size;
	if (length > 0) //only do something if size >0
	{
	    if (x > 0) //replace card w/ next card (removing it)
	    {
		Card temp = get (x - 1);
		if (x < length - 1)
		    temp.next = get (x + 1);

		else
		{
		    temp.next = null;
		    last = temp; //refesh value
		}
		size--;//resize the deck
	    }
	    else if (x == 0) //if the position is not entered
	    {
		first = get (1); //remove first card
		size--; 
	    }
	}

	return c;
    }



    public void sort ()  //method sorts by rank
    {
	//  sort returns the deck in order by suit (S,H,D,C), then by value (A,K,…2) using any sort method.

	if (size > 0) //if the deck is not empty
	{
	    Card temp = first; //set temp card
	    for (int x = 1 ; x < size ; x++) //go through all the cards
	    {
		for (int y = 0 ; y < size - x ; y++) //goes through deck again
		{
		    Card temp1 = get (y);
		    Card temp2 = get (y + 1);
		    if (temp1.getValue () > temp2.getValue ())
			swap (y, y + 1); //swap 1st card is bigger in value
		}
	    }
	}
	//refreshes
	first = get (0);
	last = get (size - 1);
    }


    public void swap (int x, int y) //swap method to use for shuffle
    {
	if (x == 0)
	{
	    //gets the cards
	    Card c1 = get (x);
	    Card c2 = get (y);
	    
	    Card temp1 = c1; //makes temp
	    Card temp2 = c2.next;
	    first = c2;
	    
	    c2.next = temp1; //swap the cards
	    c2.next.next = temp2;
	}


	else
	{
	    //gets the cards
	    Card c1 = get (x - 1);
	    Card c2 = get (y);
	    
	    Card temp1 = c1.next;
	    Card temp2 = c2.next;
	   
	    c1.next = c2;
	    c2.next = temp1;
	    c2.next.next = temp2;
	}
    }


    public void print (Graphics g)  //shows deck
    {

	int y = -90, z = 0;

	for (int x = 0 ; x < size ; x++) //for loop goes through all cards
	{

	    Card temp = get (x);
	    if (x % 26 == 0) //new row if over 26 cards
	    {
		y += 100;
		z = 0;
	    }
	    temp.show (g, (z * 20) + 200, y); //display
	    z++; //increase counter

	}

    }
}


