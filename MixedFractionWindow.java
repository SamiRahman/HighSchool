import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
//Sami Rahman
//IntegerTextField extends JTextField
//consume
//constructor (a)
//super(a)

//test run: 9/9


class MixedFractionWindow extends JFrame
{

    //======================================= instance variables
    private JTextField _fraction1TF = new JTextField (6);
    private JTextField _fraction2TF = new JTextField (6);
    static JTextArea _displayTA = new JTextArea (); // Multi-line text field
    public static String outStr5 = ("");
    public static String outStr1;

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
	    MixedFraction Z = new MixedFraction ();
	    Fraction F = new Fraction ();
	    Fraction G = new Fraction ();

	    A.read (frac1str); // Convert entries to numeric data
	    B.read (frac2str);
	    F = A.toFraction ();
	    F.reduce(); 
	    G = B.toFraction ();
	    G.reduce();
	    try
	    {
		// Create output string for text area
		if (outStr5 == ("") && A.denominator != 0 && B.denominator != 0) //acceptable entry
		{
		    outStr1 = ("Mixed Fractions.\n\nReduced: " + A + " and " + B + "\nImproper: " + F + " and " + G);
		    Z = A.add (B);
		    outStr1 += ("\nAdded: " + Z);
		    Z = A.subtract (B);
		    outStr1 += ("\nSubtracted: " + Z);
		    Z = A.multiply (B);
		    outStr1 += ("\nMultiplied: " + Z);
		    Z = A.divide (B);
		    outStr1 += ("\nDivided: " + Z + "\n");
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
	    catch (NumberFormatException f)
	    {
		outStr5 = ("Undefined. Try again");
		JOptionPane.showMessageDialog (null, "Undefined. Try again.");
	    }

	}
    }


    class MixedFraction extends Fraction
    {
	protected int whole;  // new data field

	public MixedFraction ()
	{
	    whole = 0; //num and den are already from fraction class
	}

	public MixedFraction (int whole, int num, int den)
	{
	    super (num, den);
	    this.whole = whole;
	}

	public Fraction toFraction ()
	{
	    Fraction B = new Fraction ();
	    B.numerator = whole * denominator + numerator;
	    B.denominator = denominator;
	    return B;
	}

	public MixedFraction toMixed (Fraction B)
	{
	    MixedFraction A = new MixedFraction ();
	    A.whole = B.numerator / B.denominator;
	    A.denominator = B.denominator;
	    A.numerator = B.numerator % B.denominator;
	    return A;
	}
	
	public MixedFraction add (MixedFraction B)
	{
	    numerator = whole * denominator + numerator;
	    whole = 0; //change object to improper fraction
	    return toMixed (super.add (B.toFraction ())); //add a mixed fraction to the existing object (improperly for both), change back to mixed fraction
	}
	public MixedFraction subtract (MixedFraction B)
	{
	    numerator = whole * denominator + numerator;
	    whole = 0; //change object to improper fraction
	    return toMixed (super.subtract (B.toFraction ())); //subtract a mixed fraction by the existing object (improperly for both), change back to mixed fraction
	}
	public MixedFraction multiply (MixedFraction B)
	{
	    numerator = whole * denominator + numerator;
	    whole = 0; //change object to improper fraction
	    return toMixed (super.multiply (B.toFraction ())); //multiply a mixed fraction to the existing object (improperly for both), change back to mixed fraction
	}
	public MixedFraction divide (MixedFraction B)
	{
	    numerator = whole * denominator + numerator;
	    whole = 0; //change object to improper fraction
	    return toMixed (super.divide (B.toFraction ())); //divide a mixed fraction by the existing object (improperly for both), change back to mixed fraction
	}
	public void reduce ()
	{
	    if (numerator > denominator && whole > 0) //improper remainder fraction with whole, not just improper fraction
	    {
		whole += numerator / denominator; //increase the whole
		numerator = numerator % denominator; //decrease numerator
	    }
	    else if (numerator / denominator == 1)
	    {
		whole++;
		numerator = 0;
		denominator =1;
	    }
	    else
		super.reduce();
	}
	public void read (String fract)
	{
	    int space = fract.indexOf (" ");
	    int end = fract.indexOf ("/");
	    if (space == -1 && end == -1) //whole number
	    {
		whole = Integer.parseInt (fract);
		numerator = 0;
		denominator = 1;
	    }
	    else if (end > -1 && space == -1) //if fraction, not mixed
	    {
		super.read (fract);
		whole = 0;
	    }
	    else //mixed
	    {
		whole = Integer.parseInt (fract.substring (0, space));
		super.read (fract.substring (space + 1));
		if (numerator / denominator == 1)
		{
		    numerator = 0;
		    denominator = 1;
		    whole++;
		}
	    }
	}
	public String toString ()
	{
	    this.reduce ();
	    if (whole == 0) //improper fraction
		return "" + super.toString ();
	    else if (numerator / denominator == 1 || numerator == 0) //fraction part of mixedfraction is equal to 1 or fraction part equals 0
		return "" + whole;
	    else
	    {
		if (numerator < 0)
		    numerator *= -1;
		return whole + " " + super.toString ();
	    }
	}
	public int getDenom (MixedFraction A)
	{
	    return A.denominator;
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

	public int getDenom (Fraction A)
	{
	    return A.denominator;
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
		    if (denominator == 0)
			outStr5 = "Undefined. Try again.";
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


    //======================================================== method main
    public static void main (String[] args)
    {
	MixedFractionWindow window = new MixedFractionWindow ();
	window.setVisible (true);
    }
}


