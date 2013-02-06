import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
//Sami Rahman



class MixedFractionWindow extends JFrame
{

    //======================================= instance variables
    private JTextField _fraction1TF = new JTextField (6);
    private JTextField _fraction2TF = new JTextField (6);
    static JTextArea _displayTA = new JTextArea (); // Multi-line text field
    public static String outStr5 = ("");

    //======================================================== constructor
    public MixedFractionWindow ()
    {
	// 1... Create/initialize components
	JButton processBtn = new JButton ("Process");
	processBtn.addActionListener (new ProcessBtnListener ()); // Connect button to listener class

	// 2... Create content pane, set layout
	JPanel content = new JPanel ();        // Create a content pane
	content.setLayout (new BorderLayout ()); // Use BorderLayout for panel
	JPanel north = new JPanel ();
	north.setLayout (new FlowLayout ()); // Use FlowLayout for input area

	// 3... Add the components to the input area.
	north.add (new JLabel ("Fraction 1:")); // Create, add label
	north.add (_fraction1TF);            // Add input field
	north.add (new JLabel ("Fraction 2:")); // Create, add label
	north.add (_fraction2TF);          // Add output field
	north.add (processBtn);             // Add button

	content.add (north, "North"); // Input area
	content.add (_displayTA, "South"); // Output area

	// 4... Set this window's attributes.
	setContentPane (content);
	pack ();
	setTitle ("Fraction Converter");
	setSize (400, 230);
	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo (null);           // Center window.
    }


    class ProcessBtnListener implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{
	    String frac1str = _fraction1TF.getText (); // Retrieve values from text fields
	    String frac2str = _fraction2TF.getText ();

	    MixedFraction A = new MixedFraction (); //initialize
	    MixedFraction B = new MixedFraction ();
	    MixedFraction F = new MixedFraction ();

	    A.read (frac1str); // Convert entries to numeric data
	    B.read (frac2str);
	    A.reduce (); //reduce fractions
	    B.reduce ();
	    //F = A.add (B); //add
	    // Create output string for text area
	    if (outStr5 == ("")) //acceptable entry
	    {
		String outStr1 = ("Fractions.\n\nReduced: " + A + " and " + B + "\nAdded: " + F);
		//F = A.subtract (B);
		outStr1 += ("\nSubracted: " + F);
		//F = A.multiply (B);
		outStr1 += ("\nMultiplied: " + F);
		//F = A.divide (B);
		outStr1 += ("\nDivided: " + F + "\n");
		_displayTA.setText (outStr1);
	    }
	    else //unacceptable
	    {
		_displayTA.setText (outStr5); //display "undefined"
		_fraction1TF.setText (""); //clear boxes
		_fraction2TF.setText ("");
		outStr5 = (""); //reset
	    }
	}
    }


    public class Fraction
    {
	// data fields(will explain protected)
	protected int numerator, denominator, gcf, mult1, mult2, newDen, newNum1, newNum2;

	// default constructor
	public Fraction ()
	{
	    numerator = 0;
	    denominator = 1;
	}


	// copy constructor
	public Fraction (Fraction F)
	{
	    numerator = F.numerator;
	    denominator = F.denominator;
	}


	// alternate constructor
	public Fraction (int num, int den)
	{
	    numerator = num;
	    denominator = den;
	}

	// read from keyboard
	public void read (String frac)
	{
	    int end = frac.indexOf ("/"); //returns index where / is
	    try
	    {
		if (end == -1) //no slash, no denom specified
		{
		    numerator = Integer.parseInt (frac);
		    denominator = 1;
		}
		else //if there is a slash
		{
		    numerator = Integer.parseInt (frac.substring (0, end)); //numerator behind the slash
		    denominator = Integer.parseInt (frac.substring (end + 1)); //denom is everything after
		}
		if (numerator < 0 && denominator < 0 || (numerator > 0 && denominator < 0)) //if denom has the negative, or both are negative
		{
		    numerator *= -1;
		    denominator *= -1;
		}
	    }
	    catch (NumberFormatException e)  // Catches failed attempt at conversion
	    {
		outStr5 = ("Undefined. Try again."); //stops from crashing and changes outStr5
	    }
	}

	// override Object toString for printing
	public String toString ()
	{
	    if (numerator == 0 && denominator != 0) //i.e. 0/3 becomes 0
		return (0 + ""); //display zero
	    else if (denominator == 1)
		return (numerator + ""); //display only numerator, 3/1 becomes 3
	    else
		return (numerator + "/" + denominator); //display fraction
	}


	public int gcfRec (int a, int b)
	{
	    if (a == b) //if the numbers are equal, then the GCF is the number
		return a;
	    else if (a < b) //switches the larger number to the first spot, sets up for the next step
		return (gcfRec (b, a));
	    else //when larger number is in first spot (a > b)
		return (gcfRec (b, a - b)); //decreases the numbers by the differences between till the numbers are the same
	}


	public void reduce ()  //reduces fractions to lowest terms
	{
	    if (numerator != 0)
	    {
		gcf = gcfRec (Math.abs (numerator), denominator); //find gcf
		numerator = numerator / gcf; //change the stored numerator to the reduced one
		denominator = denominator / gcf; //change the stored denominator to the reduced one
	    }
	}


	public Fraction add (Fraction B)  //returns a new fraction based on the addition of a fraction and another fraction
	{
	    gcf = gcfRec (denominator, B.denominator); //gets the gcf of the denoms of the fractions
	    mult1 = B.denominator / gcf; //first multiplier
	    newDen = denominator * mult1; //shared, equal denom
	    newNum1 = numerator * mult1;  //new numerator is found for first fraction
	    mult2 = denominator / gcf; //other multiplier
	    newNum2 = B.numerator * mult2; //new numerator is found for second fraction
	    return new Fraction (newNum1 + newNum2, newDen); //return the whole new fraction
	}


	public Fraction subtract (Fraction B)
	{
	    if (numerator != 0 && B.numerator != 0)
	    {
		gcf = gcfRec (denominator, B.denominator); //gets the gcf of the denoms of the fractions
		mult1 = B.denominator / gcf; //first multiplier
		newDen = denominator * mult1; //shared, equal denom
		newNum1 = numerator * mult1;  //new numerator is found for first fraction
		mult2 = denominator / gcf; //other multiplier
		newNum2 = B.numerator * mult2; //new numerator is found for second fraction
		return new Fraction (newNum1 - newNum2, newDen); //return the whole new fraction
	    }
	    else
		return new Fraction (B.numerator * -1, B.denominator); //negative of the non zero fraction
	}


	public Fraction multiply (Fraction B)
	{
	    return new Fraction (numerator * B.numerator, denominator * B.denominator);
	}


	public Fraction divide (Fraction B)
	{
	    return new Fraction (numerator * B.denominator, denominator * B.numerator);
	}
    }


    class MixedFraction extends Fraction
    {
	protected int whole;  // new data field

	public MixedFraction ()
	{
	    whole = 0;
	}


	public MixedFraction (int whole, int num, int den)
	{
	    this.whole = whole;
	    super (num, den);
	}


	public Fraction toFraction ()
	{
	    Fraction A = new MixedFraction ();
	    A.numerator = whole * denominator + numerator;
	    A.denominator = denominator;
	    return A;
	}


	public void toMixed (Fraction A)
	{
	    whole = A.numerator / A.denominator;
	    denominator = A.denominator;
	    numerator = A.numerator % A.denominator;
	}


	public void read (String fract)
	{
	    int space = fract.indexOf (" ");
	    if (space == -1)
	    {
		whole = Integer.parseInt (fract);
		numerator = 1;
		denominator = 1;
	    }
	    else
	    {
		super.read (fract);
		whole = Integer.parseInt (fract.substring (0, space));

	    }
	}
	public String toString ()
	{
	    if (whole == 0)
		return "" + super.toString ();
	    else
		return whole + " " + super.toString ();
	}
    }


    //======================================================== method main
    public static void main (String[] args)
    {
	MixedFractionWindow window = new MixedFractionWindow ();
	window.setVisible (true);
    }
}


