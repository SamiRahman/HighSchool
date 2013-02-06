// The "Fraction" class.
/*1.    A reduce method that changes a fraction to lowest terms.        A.reduce();
2.      Add, subtract, multiply and divide methods that operate on fractions.   F.add (A, B);   or  F = A.add (B);
3.      Improve the read method so that it reads a fraction in as a string without prompt messages and does
error-checking to ensure valid input.
4.      Improve the toString method so that it displays special cases in the most presentable way (3/1 as 3,  0/5 as 0).
5.      Have the class handle negative fractions properly during calculations and display.
6.      A GUI-driven program to fully test all functions of the class.
*/
import java.awt.*;
import hsa.Console;


public class MixedFractionTest
{
    static Console c;

    public static void main (String[] args)
    {
	c = new Console ();

	// declare fractions 0/1 and 1/2
	MixedFraction A = new MixedFraction (2, 1, 1);
	MixedFraction B = new MixedFraction (1, 1, 2);
	Fraction F = new Fraction ();
	Fraction G = new Fraction();
	

	//A.read (c);
	//B.read (c);
	c.print ("The mixed fractions are: ");
	c.println (A + " and " + B);
	A.toFraction ();
	B.toFraction ();
	//A.reduce ();
	//B.reduce ();
	c.print ("Improper Fractions: ");
	c.println (A +" and "+B);
	c.print ("Reduced is: ");
	c.println (A + " and " + B);
	c.print ("Added is: ");
	//F = A.add (B);
	F.reduce ();
	c.println ("" + F);
	c.print ("Subtracted is: ");
	//F = A.subtract (B);
	F.reduce ();
	c.println ("" + F);
	c.print ("Multiplied is: ");
	//F = A.multiply (B);
	F.reduce ();
	c.println ("" + F);
	c.print ("Divided is: ");
	// F = A.divide (B);
	F.reduce ();
	c.println ("" + F);
    } // main method
} // FractionTest class

class Fraction
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
    public void read (Console c)
    {
	c.print ("Enter fraction: ");
	String frac = c.readString ();
	int end = frac.indexOf ("/");
	numerator = Integer.parseInt (frac.substring (0, end));
	denominator = Integer.parseInt (frac.substring (end + 1));
	if (numerator < 0 && denominator < 0 || (numerator > 0 && denominator < 0))
	{
	    numerator *= -1;
	    denominator *= -1;
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

// Fraction class

class MixedFraction extends Fraction /////////////////////////////////////////////////////////////////////
{
    protected int whole;  // new data field

    public MixedFraction ()
    {
	whole = 0;
    }


    public MixedFraction (int whole, int num, int den)
    {
	super (num, den);
	this.whole = whole;
    }


    public Fraction toFraction ()
    {
	Fraction A = new MixedFraction ();
	A.numerator = whole * denominator + numerator;
	A.denominator = denominator;
	return A;
    }
    
    public void reduce()
    {
	super.reduce();
    }


    public void toMixed (Fraction A)
    {
	whole = A.numerator/A.denominator;
	denominator = A.denominator;
	numerator = A.numerator%A.denominator;
    }


    public String toString ()
    {
	if (whole == 0)
	    return "" + super.toString ();
	else
	    return whole + " " + super.toString ();
    }
}

