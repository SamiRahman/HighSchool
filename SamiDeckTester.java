// The "CardTester" class.
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.imageio.*; // allows image loading
import java.io.*; // allows file access
import java.util.*;

public class SamiDeckTester extends JFrame
{
    JButton processBtn; //declaration of variables
    JButton shuffleBtn; 
    JButton dealBtn;
    JButton addBtn;
    JButton csortBtn;
    JButton qsortBtn;
    JButton searchBtn;
    JTextField _dealTF = new JTextField (2);
    JLabel title = new JLabel ("Deck Tester!", JLabel.CENTER);
    String[] cardRankStrings;
    String[] cardSuitStrings;
    JComboBox rankList, suitList;

    Card card;
    Deck deck;

    public SamiDeckTester ()
    {
	processBtn = new JButton ("Flip Cards");
	shuffleBtn = new JButton ("Shuffle");
	dealBtn = new JButton ("Deal (Enter #)");
	addBtn = new JButton ("Add (Name, #)");
	qsortBtn = new JButton ("QSort");
	csortBtn = new JButton ("CSort");
	searchBtn = new JButton ("Search (Name)");
	String[] cardRankStrings = {"", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	String[] cardSuitStrings = {"", "S", "D", "H", "C"};
	rankList = new JComboBox (cardRankStrings); //make a drop down box with these options
	suitList = new JComboBox (cardSuitStrings);
	
	processBtn.addActionListener (new ProcessBtnListener ()); // Connect button to listener class
	shuffleBtn.addActionListener (new ProcessBtnListener ()); // Connect button to listener class
	dealBtn.addActionListener (new ProcessBtnListener ()); // Connect button to listener class
	qsortBtn.addActionListener (new ProcessBtnListener ()); // Connect button to listener class
	csortBtn.addActionListener (new ProcessBtnListener ()); // Connect button to listener class
	addBtn.addActionListener (new ProcessBtnListener ()); // Connect button to listener class
	searchBtn.addActionListener (new ProcessBtnListener ()); // Connect button to listener class

	deck = new Deck (52);
	
	JPanel border = new JPanel (); // Create a content pane
	border.setLayout (new BorderLayout ()); // Use BorderLayout for panel
	JPanel content = new JPanel (); // Create a content pane
	content.setLayout (new GridLayout (5, 2)); // Use GridLayout for panel, the options
	JPanel twoColPane = new JPanel ();
	twoColPane.setLayout (new GridLayout (1, 2));
	JPanel twoColPane2 = new JPanel ();
	twoColPane.setLayout (new GridLayout (1, 2));

	DrawArea table = new DrawArea (400, 500, deck);

	title.setForeground (Color.blue);
	title.setFont (new Font ("Helvetica", Font.BOLD, 30)); //set title choices
	content.add (processBtn);              // Add buttons
	content.add (shuffleBtn);
	content.add (dealBtn);
	content.add (_dealTF);
	content.add (addBtn);
	content.add (twoColPane);
	twoColPane.add (rankList);
	twoColPane.add (suitList);
	content.add (searchBtn);
	content.add (twoColPane2);
	twoColPane2.add (rankList);
	twoColPane2.add (suitList);
	content.add (qsortBtn);
	content.add (csortBtn);
	border.add (content, "West"); //add options
	border.add (table, "East"); //add cards
	border.add (title, "North"); //add title

	// 4... Set this window's attributes.
	setContentPane (border);
	pack ();
	setTitle ("Deck Tester");
	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo (null);           // Center window.
    }



    class ProcessBtnListener implements ActionListener // Inner class for handling events
    {
	public void actionPerformed (ActionEvent e)
	{
	    String rankLetter = (String) rankList.getSelectedItem (); //select the selected drop down option 
	    String suitLetter = (String) suitList.getSelectedItem ();
	    String pos;
	    String outOfRange = "";
	    Card dealt;

	    if (e.getActionCommand ().equals ("Flip Cards")) //e.getSource () == processBtn
		deck.flip ();
	    else if (e.getActionCommand ().equals ("Shuffle"))
		deck.shuffle ();
	    else if (e.getActionCommand ().equals ("Deal (Enter #)"))
	    {
		if (_dealTF.getText ().equals (""))
		    dealt = deck.deal (); //by default, deal from top
		else if (Integer.parseInt (_dealTF.getText ()) > deck.cards.size () || Integer.parseInt (_dealTF.getText ()) < 0)
		{
		    dealt = deck.deal (); //by default, deal from top
		    outOfRange = "Out of range. Default: ";
		}
		else
		    dealt = deck.deal (Integer.parseInt (_dealTF.getText ())); //deal card from specified position

		JOptionPane.showMessageDialog (null, outOfRange + "Your dealt card is the " + dealt.name.charAt (0) + " of " + dealt.name.charAt (1) + "."); //pop up window informing of what card was dealt
		outOfRange = ""; //reset
	    }
	    else if (e.getActionCommand ().equals ("Add (Name, #)") && !rankLetter.equals ("") && !suitLetter.equals ("")) //if drop down is not empty and button is clicked
	    {
		if (_dealTF.getText ().equals ("")) 
		    deck.add ("" + rankLetter + suitLetter); //add specified card (from drop down menus) to the end
		else if (Integer.parseInt (_dealTF.getText ()) > deck.cards.size () || Integer.parseInt (_dealTF.getText ()) < 0) //if text field range is out of range
		{
		    deck.add ("" + rankLetter + suitLetter); //by default, add to end
		}
		else
		    deck.add ("" + rankLetter + suitLetter, Integer.parseInt (_dealTF.getText ())); //add card to specified position (from text field)
	    }
	    else if (e.getActionCommand ().equals ("Search (Name)") && !rankLetter.equals ("") && !suitLetter.equals (""))
	    {
		pos = deck.search ("" + rankLetter + suitLetter); //return the position(s) of card
		JOptionPane.showMessageDialog (null, "Your card is located at position(s):" + pos + ".");
	    }
	    else if (e.getActionCommand ().equals ("QSort"))
		deck.quicksort ();
	    else if (e.getActionCommand ().equals ("CSort"))
		deck.combsort ();

	    repaint (); // update display
	}
    }


    public static void main (String[] args)
    {
	SamiDeckTester window = new SamiDeckTester ();
	window.setVisible (true);
    } // main method
} // CardTester class


class DrawArea extends JPanel
{
    Card card;
    Deck deck;
    int i = 0;

    public DrawArea (int width, int height, Deck d)
    {
	this.setPreferredSize (new Dimension (width, height)); // size
	deck = d; // connect the Deck objects
    }


    public void paintComponent (Graphics g)
    {
	int x = 0, y = 0;

	for (int z = 0 ; z < (deck.cards).size () ; z++)
	{
	    deck.show (g, 50 + 15 * x, 3 + 95 * y, z); //draws the cards in the deck
	    x++;
	    if (x == 13) //switches to next row
	    {
		x = 0;//start from beginning of row
		y++;
	    }
	}
    }
}


class Card
{
    protected int rank, suit;
    protected String name;
    private Image image;
    private boolean faceup;
    private static Image cardback; // same back for all cards

    public Card (String item)  // Creates card from 2-character name ex. js is Jack of Spades
    {
	name = item.toUpperCase ();
	int number = getValue (); // convert string to equivalent integer 0-51

	if (number != -1) // valid card
	{
	    rank = number % 13 + 1; // rank = 1-13 (2, 3,... A)
	    suit = number / 13 + 1; // suitnum = 1-4 (S, H, C, D)
	    faceup = true;

	    // loads image from cards folder
	    image = null;
	    try
	    {
		image = ImageIO.read (new File ("cards\\" + name + ".gif")); // load file into Image object
		cardback = ImageIO.read (new File ("cards\\b.gif")); // load file into Image object
	    }
	    catch (IOException e)
	    {
	    }
	}
    }


    public Card (int number)  // Creates card from integer 0-51 (2 of spades to ace of diamonds)
    {
	rank = number % 13 + 1; // rank = 1-13 (2, 3,... A)
	suit = number / 13 + 1; // suitnum = 1-4 (S, H, C, D)
	name = getName (); // retrieve string name of card
	faceup = true;

	// loads image from cards folder
	image = null;
	try
	{
	    image = ImageIO.read (new File ("cards\\" + name + ".gif")); // load file into Image object
	    cardback = ImageIO.read (new File ("cards\\b.gif")); // load file into Image object
	}
	catch (IOException e)
	{
	}
    }


    public String getName ()  // converts rank and value of card to 2-character name
    {
	String shownvalue, shownsuit;        // shownvalue and shownsuit make up file name (ex. JS.GIF is jack of spades)
	if (rank <= 8)
	    shownvalue = "" + (rank + 1); // 1-8 will become 2-9 for cards
	else if (rank == 9)
	    shownvalue = "T";
	else if (rank == 10)
	    shownvalue = "J";
	else if (rank == 11)
	    shownvalue = "Q";
	else if (rank == 12)
	    shownvalue = "K";
	else
	    shownvalue = "A"; // 13 is ace

	if (suit == 1)
	    shownsuit = "S";
	else if (suit == 2)
	    shownsuit = "H";
	else if (suit == 3)
	    shownsuit = "D";
	else
	    shownsuit = "C";
	return shownvalue + shownsuit;
    }


    public int getValue ()  // converts string name to integer from 0-51
    {
	name = name.toUpperCase ();
	char rank = name.charAt (0), suit = name.charAt (1);

	int numrank, numsuit;

	if (rank <= '9')
	    numrank = (int) rank - 48;
	else if (rank == 'T')
	    numrank = 10;
	else if (rank == 'J')
	    numrank = 11;
	else if (rank == 'Q')
	    numrank = 12;
	else if (rank == 'K')
	    numrank = 13;
	else if (rank == 'A')
	    numrank = 14;
	else
	    return -1;      // invalid

	if (suit == 'S')
	    numsuit = 1;
	else if (suit == 'H')
	    numsuit = 2;
	else if (suit == 'D')
	    numsuit = 3;
	else if (suit == 'C')
	    numsuit = 4;
	else
	    return -1; // invalid

	return (numsuit - 1) * 13 + (numrank - 2);
    }


    public void show (Graphics g, int x, int y)  // draws card face up or face down
    {
	if (faceup)
	    g.drawImage (image, x, y, null);
	else
	    g.drawImage (cardback, x, y, null);
    }


    public int getRank ()  // returns card rank
    {
	return rank;
    }


    public int getSuit ()  // returns card suit
    {
	return suit;
    }


    public void flip ()  // turns card over
    {
	faceup = !faceup;
    }


    public boolean getFaceup ()  // returns card suit
    {
	return faceup;
    }
}


class Deck
{
    protected ArrayList cards;
    private Card temp;
    private String name;
    private boolean faceup;
    private Image image;
    private static Image cardback; // same back for all cards

    public Deck (int amt)
    {
	cards = new ArrayList (amt); //creates new arraylist of specified amount
	for (int a = 0 ; a < amt ; a++)
	    cards.add (new Card (a)); // adds a new card object
    }


    public Deck ()
    {
	cards = new ArrayList (); //creates new arraylist of 10 spaces
    }


    public void show (Graphics g, int x, int y, int i)  // draws card face up
    {
	temp = (Card) (cards.get (i)); //get specified card
	temp.show (g, x, y); //draw it
    }


    public void shuffle ()
    {
	Collections.shuffle (cards); //shuffles the list
    }


    public Card deal ()  //deals card from top of deck, default
    {
	Card drawn = (Card) (cards.get (0)); //creates duplicate card from the top of deck
	if (cards.size () != 1)
	    cards.remove (0); //removes card from top
	return (drawn); //returns card object
    }


    public Card deal (int position)  //deals card from position
    {
	if (position > 0 && position <= cards.size ())
	{
	    Card drawn = (Card) (cards.get (position - 1)); //creates duplicate card in the desired draw position
	    if (cards.size () != 1)
		cards.remove (position - 1); //removes card at position
	    return (drawn); //returns card object
	}
	else
	    return deal (); //returns card from top of deck by default
    }


    public void flip ()  // turns card over
    {
	for (int i = 0 ; i < cards.size () ; i++)
	{
	    temp = (Card) (cards.get (i));
	    temp.flip ();
	}
    }


    public void add (String item, int pos)  //add accepts a new Card entry to be added to the specified position
    {
	name = item.toUpperCase ();
	temp = new Card (name);
	cards.add (pos, temp);
    }


    public void add (String item)  //add accepts a new Card entry to be added to the end of the deck.
    {
	name = item.toUpperCase ();
	temp = new Card (name);
	cards.add (temp);
    }


    public String search (String item)  //search accepts a Card object and returns its position in the deck (or -1 if not found).
    {
	name = item.toUpperCase ();
	temp = new Card (name);
	String positions = ""; //creates string for positions
	for (int x = 0 ; x < cards.size () ; x++) //scans through deck
	{
	    Card tempcard = (Card) (cards.get (x));
	    if ((tempcard.name).equals (temp.name)) //if card at position is equal to the card in the deck, adds position value to positions string, compare names of cards
		positions = positions + (" " + (x + 1));
	}
	if (positions == "") //if no positions are found
	    return -1 + "";
	else
	    return (positions);
    }


    public void quicksort ()  //quicksort uses the recursive quick sort algorithm and returns the deck in order by value (A,2,…K) and doesn't care about suit order.
    {
	int counter = 0; //counts for position in deck to place card
	for (int x = 1 ; x <= 13 ; x++) //loop for card values
	{
	    for (int y = 0 ; y < cards.size () ; y++) //scans through deck
	    {
		Card temp = (Card) (cards.get (y)); //sets temp card for the card in deck at position
		if (temp.rank == x) //if the rank of the card is equal to x, then swaps if for the current position (counter)
		{
		    Collections.swap (cards, counter, y); //list, int a, int b
		    counter++;
		}
	    }
	}
    }


    public void combsort ()
    {
	int gap = cards.size (); //initialize gap size
	boolean swapped = true;
	while (gap > 1 || swapped)
	{
	    //update the gap value for a next comb. Below is an example
	    if (gap > 1)
		gap = (int) (gap / 1.247330950103979);
	    //gap = 1;

	    int i = 0;
	    swapped = false; //see bubblesort for an explanation

	    //a single "comb" over the input list
	    while (i + gap < cards.size ())
	    {
		Card temp = (Card) cards.get (i);
		Card temp2 = (Card) cards.get (i + gap);
		if (temp.getValue () > temp2.getValue ())
		{
		    Collections.swap (cards, temp.getValue (), temp2.getValue ());
		    swapped = true; // Flag a swap has occurred, so the list is not guaranteed sorted
		}
		i++;
	    }
	}
    }
}
