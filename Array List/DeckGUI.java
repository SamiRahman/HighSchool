//Robert Lin
//Deck -> ArrayList
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.imageio.*; // allows image loading
import java.io.*;
import java.util.*;
import java.util.ArrayList;

class DeckGUI extends JFrame
{

    protected Deck deck = new Deck (); //initializing test
    private Card u; //Dealt Card
    private JTextField rank = new JTextField (2); //rank field
    private JTextField suit = new JTextField (2); //suit field
    private JLabel findlabel = new JLabel (""); //label (for find and add)
    private JLabel dealarea = new JLabel (""); //initializes deal area
    private boolean deckflipped = false; //deck is flipped boolean, for flipping the dealt card
    public JPanel content; //GUI frame
    public Drawing area = new Drawing (deck); //deck drawing area
    public DeckGUI ()
    {
	content = new JPanel ();
	content.setLayout (new FlowLayout ()); //setting panel layout type
	JButton shuffle = new JButton ("Shuffle"); //shuffling button
	shuffle.addActionListener (new shuffleListener ());
	JButton add = new JButton ("Add"); //add button
	add.addActionListener (new addListener ());
	JButton find = new JButton ("Find"); //find button
	find.addActionListener (new findListener ());
	JButton quicksort = new JButton ("Quick Sort"); //quick sort button
	quicksort.addActionListener (new quicksortListener ());
	JButton selectionsort = new JButton ("Selection Sort"); //selection sort button
	selectionsort.addActionListener (new selectionsortListener ());
	JButton flip = new JButton ("Flip"); //flip button
	flip.addActionListener (new flipListener ());
	JButton deal = new JButton ("Deal"); //deal button
	deal.addActionListener (new dealListener ());
	JLabel suitl = new JLabel ("Suit[ S/1,H/2, D/3, C/4]");
	JLabel rankl = new JLabel ("Rank(1-9,T,J,Q,K)");

	content.add (shuffle); //adding buttons, labels, etc to content.
	content.add (add);
	content.add (rankl);
	content.add (rank);
	content.add (suitl);
	content.add (suit);
	content.add (find);
	content.add (quicksort);
	content.add (selectionsort);
	content.add (flip);
	content.add (deal);
	content.add (findlabel);
	content.add (area);
	content.add (dealarea);


	setContentPane (content);
	setSize (1200, 500); //setting pane size
	setTitle ("DeckGUI");
	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo (null);


    }


    class shuffleListener implements ActionListener //shuffle listener
    {
	public void actionPerformed (ActionEvent e)
	{
	    deck.shuffle ();
	    showDeckListener d = new showDeckListener (); //reshowing deck
	    d.actionPerformed (e);
	    findlabel.setText ("");
	}
    }


    class addListener implements ActionListener//add button listener
    {
	public void actionPerformed (ActionEvent e)
	{
	    String suitin = "" + suit.getText (); //getting text from rank and suit fields
	    String rankin = "" + rank.getText ();
	    int rankn = 1; //initializing variables
	    int suitn = 1;
	    boolean add = true; //boolean for checking if input is valid enough
	    if (suitin.length () != 0 && rankin.length () != 0)
	    {
		char suitinput = suitin.charAt (0); //getting first character from input from suit and rank fields
		char rankinput = rankin.charAt (0);
		if (suitinput == 'C' || suitinput == 'c') //getting suit and setting suit number
		    suitn = 4;
		else if (suitinput == 'D' || suitinput == 'd')
		    suitn = 3;
		else if (suitinput == 'H' || suitinput == 'h')
		    suitn = 2;
		else if (suitinput == 'S' || suitinput == 's')
		    suitn = 1;
		else if (suitinput >= '1' && suitinput <= '4')
		    suitn = (int) (suitinput) - 48;
		else
		    add = false;

		if (rankinput == 'J' || rankinput == 'k')//getting rank and setting rank number
		    rankn = 11;
		else if (rankinput == 'Q' || rankinput == 'q')
		    rankn = 12;
		else if (rankinput == 'K' || rankinput == 'k')
		    rankn = 13;
		else if (rankinput >= '1' && rankinput <= '9')
		    rankn = rankinput - 48;
		else if (rankinput == 'T' || rankinput == 't')
		{
		    rankn = 10;
		}
		else
		    add = false;

		if (add)
		{
		    Card newcard = new Card ((suitn - 1) * 13 + rankn);//sets proper card
		    if (deckflipped)//checks for deck flip and accounts for that
			newcard.flip ();
		    deck.add (newcard);//adds card to deck
		    showDeckListener d = new showDeckListener ();
		    d.actionPerformed (e);
		    findlabel.setText ("");
		}
		else//sets text to invalid if input invalid, deletes input for rank and suit.
		{
		    findlabel.setText ("Invalid");
		    rank.setText ("");
		    suit.setText ("");
		}
	    }
	    else//if no input adds a random card
	    {
		Card random = new Card ((int) (Math.random () * 52 + 1));
		if (deckflipped)
		    random.flip ();
		deck.add (random);
		showDeckListener d = new showDeckListener ();
		d.actionPerformed (e);
		findlabel.setText ("Random card added.");
	    }
	}
    }


    class findListener implements ActionListener//find button listener
    {
	public void actionPerformed (ActionEvent e)
	{
	    String suitin = "" + suit.getText ();//getting text from suit, rank fields
	    String rankin = "" + rank.getText ();
	    int rankn = 1;//initializing rank, suit number
	    int suitn = 1;
	    boolean find = true;//boolean for checking for valid input
	    if (suitin.length () != 0 && rankin.length () != 0)
	    {
		char suitinput = suitin.charAt (0);
		char rankinput = rankin.charAt (0);
		if (suitinput == 'C' || suitinput == 'c')//setting suit number
		    suitn = 4;
		else if (suitinput == 'D' || suitinput == 'd')
		    suitn = 3;
		else if (suitinput == 'H' || suitinput == 'h')
		    suitn = 2;
		else if (suitinput == 'S' || suitinput == 's')
		    suitn = 1;
		else if (suitinput >= '1' || suitinput <= '4')
		    suitn = (int) (suitinput) - 48;
		else
		    find = false;

		if (rankinput == 'J' || rankinput == 'k')//setting rank number
		    rankn = 11;
		else if (rankinput == 'Q' || rankinput == 'q')
		    rankn = 12;
		else if (rankinput == 'K' || rankinput == 'k')
		    rankn = 13;
		else if (rankinput >= '1' || rankinput <= '9')
		    rankn = rankinput - 48;
		else if (rankinput == 'T' || rankinput == 't')
		{
		    rankn = 10;
		}
		else
		    find = false;
		if (find)
		{
		    if ((deck.find (suitn, rankn)).length () != 0)//if card is found, displays string for positions
			findlabel.setText ("Card at:" + deck.find (suitn, rankn));
		    else//if card isn't found, sets text
		    {
			findlabel.setText ("Card not found");
			rank.setText ("");
			suit.setText ("");
		    }
		}
		else//for invalid inputs
		{
		    findlabel.setText ("Invalid input");
		    rank.setText ("");
		    suit.setText ("");
		}


	    }
	    else//invalid/no input
	    {
		findlabel.setText ("No input");
		rank.setText ("");
		suit.setText ("");
	    }
	}
    }


    class quicksortListener implements ActionListener//quick sort button listener
    {
	public void actionPerformed (ActionEvent e)
	{
	    deck.quicksort ();//quick sorts deck
	    showDeckListener d = new showDeckListener ();//shows deck
	    d.actionPerformed (e);
	    findlabel.setText ("");//sets findlabel
	}
    }


    class selectionsortListener implements ActionListener//selection sort button listener
    {
	public void actionPerformed (ActionEvent e)
	{
	    deck.selectionsort ();//selection sorts deck
	    showDeckListener d = new showDeckListener ();//shows deck
	    d.actionPerformed (e);
	    findlabel.setText ("");//sets findlabel
	}
    }


    class flipListener implements ActionListener//flip button listener
    {
	public void actionPerformed (ActionEvent e)
	{
	    if (deckflipped)//sets flip boolean
		deckflipped = false;
	    else
		deckflipped = true;
	    deck.flip ();
	    showDeckListener d = new showDeckListener ();//shows deck
	    d.actionPerformed (e);
	    findlabel.setText ("");//sets findlabel
	}
    }


    class dealListener implements ActionListener  //deal listener
    {
	public void actionPerformed (ActionEvent e)
	{
	    u = (Card) deck.deal (1);
	    if (!u.flipped ())//accounts for flipped deck boolean
		u.flip ();
	    dealarea.setIcon (new ImageIcon (u.cardImage));
	    showDeckListener d = new showDeckListener ();//shows deck
	    d.actionPerformed (e);
	    findlabel.setText ("");//sets findlabel
	}
    }
    
    class showDeckListener implements ActionListener//deck graphics
    {
	public void actionPerformed (ActionEvent e)
	{
	    content.remove (area);//removes old deck
	    content.remove (dealarea);
	    //draws new deck
	    area = new Drawing (deck);
	    content.add (area);
	    content.add (dealarea);
	    repaint ();
	    content.revalidate ();

	}
    }


    public static void main (String[] args)//main
    {
	DeckGUI window = new DeckGUI ();
	window.setVisible (true);
    }
    
}

class Drawing extends JPanel//drawing class
{
    private Deck deck;

    public Drawing (Deck deck)
    {
	this.deck = deck;
	this.setPreferredSize (new Dimension (1500, 300));
    }


    public void paintComponent (Graphics g)//painting
    {
	super.paintComponent (g);
	deck.show (g);
    }
}

class Deck
{
    private ArrayList cards = new ArrayList (); //initializes arraylist for deck
    public Deck ()
    {

	for (int x = 1 ; x <= 52 ; x++) //adds all cards in deck from cardnumber 1 to 52
	{
	    Card temp = new Card (x);
	    cards.add (temp);
	}
    }


    public void show (Graphics g)  //shows deck
    {
	for (int x = 0 ; x < cards.size () ; x++)
	{
	    Card temp = (Card) (cards.get (x));
	    if (x >= 52) //aesthetics, skips to next line if more than 52 cards in a line
		temp.show (g, (x * 20) - 1040 * (int) (x / 52) + 200, 10 + 40 * (int) (x / 52));
	    else
		temp.show (g, (x * 20) + 200, 10);

	}

    }


    public void add (Card temp)  //adds cards, limit of 104 cards (or two lines)
    {
	if (cards.size () != 104)
	    cards.add (temp);
    }


    public Card deal (int position)  //deals first card
    {

	Card drawn = (Card) (cards.get (position - 1)); //creates duplicate card in the desired draw position
	if (cards.size () != 1)
	    cards.remove (position - 1); //removes card at position
	return (drawn);

    }


    public void flip ()  //flips cards in the deck
    {
	for (int x = 0 ; x < cards.size () ; x++) //goes through all cards
	{
	    Card tempcard = (Card) (cards.get (0)); //creates temp card
	    tempcard.flip (); //flips temp card
	    cards.remove (0); //removes temp card position from deck
	    cards.add (tempcard); //adds temp card to end
	}
    }


    public String find (int suit, int rank)  //finds a card in the deck
    {
	String positions = ""; //creates string for positions
	for (int x = 0 ; x < cards.size () ; x++) //scans through deck
	{
	    Card tempcard = (Card) (cards.get (x));
	    if (tempcard.returnsuit () == suit && tempcard.returnrank () == rank) //if card at position is equal to the card in the deck, adds position value to positions string
	    {
		positions = positions + ("  " + (x + 1));
	    }

	}
	return (positions);
    }


    public void shuffle ()
    {
	Card temp = new Card (); //initializes new card
	for (int x = 1 ; x < 200 ; x++) //repeats 200 times
	{
	    int random = (int) (Math.random () * cards.size ()); //generates random position
	    temp = (Card) (cards.get (random)); //creates duplicate card for the card at said position
	    cards.remove (random); //removes card from position
	    cards.add (temp); //adds card to the end of the deck
	}
    }


    public void quicksort ()  //sorts by number, suits not cared about
    {
	int counter = 0; //counts for position in deck to place card
	for (int x = 1 ; x <= 13 ; x++) //loop for card values
	{
	    for (int y = 0 ; y < cards.size () ; y++) //scans through deck
	    {
		Card temp = (Card) (cards.get (y)); //sets temp card for the card in deck at position
		if (temp.returnrank () == x) //if the rank of the card is equal to x, then swaps if for the current position (counter)
		{
		    Collections.swap (cards, counter, y);
		    counter++;
		}
	    }
	}
    }


    public void selectionsort ()
    {
	int counter = 0; //counts for position in deck to place card
	for (int z = 1 ; z <= 4 ; z++) //loops for card suit
	{
	    for (int x = 1 ; x <= 13 ; x++) //loops for card rank
	    {
		for (int y = 0 ; y < cards.size () ; y++) //scans through deck
		{
		    Card temp = (Card) (cards.get (y)); //sets temp card for the card in deck at position
		    if (temp.returnrank () == x && temp.returnsuit () == z) //if the rank of the card is equal to x and suit is equal to z, then swaps if for the current position (counter)
		    {
			Collections.swap (cards, counter, y);
			counter++;
		    }
		}
	    }
	}

    }
}

