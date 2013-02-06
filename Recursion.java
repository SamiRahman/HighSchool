// The "Recursion" class.
//Sami Rahman
import java.awt.*;
import hsa.Console;

public class Recursion
{
    static Console c;           // The output console

    public static int getPositive ()
    {
	int input = c.readInt (); //input an integer value
	while (input <= 0) //if input is zero or negative
	{
	    c.print ("Number is not positive. Enter again: ");
	    input = c.readInt (); //re-input
	}
	return input;
    }


    public static int gcfNorm (int a, int b)  // method header
    {
	int temp; //declare variable
	while (a != b) //if the numbers are equal, then the GCF is the number
	{
	    if (a < b) //switches the larger number to the first spot, sets up for the next step
	    {
		temp = a; //numbers switch
		a = b;
		b = temp;
	    }
	    else if (a > b) //decreases the numbers by the differences between till the numbers are the same
	    {
		temp = a - b;
		a = b;
		b = temp;
	    }
	}
	return a;
    }


    public static void gcfNormDriver ()
    {
	int num1, num2, gcf; //declaration
	c.print ("Enter first number: ");
	num1 = getPositive ();
	c.print ("Enter second number: ");
	num2 = getPositive (); //inputs
	c.print ("Greatest Common Factor of " + num1 + " & " + num2 + " is " + gcfNorm (num1, num2) + "."); //output, processing
    }


    public static int gcfRec (int a, int b)
    {
	if (a == b) //if the numbers are equal, then the GCF is the number
	    return a;
	else if (a < b) //switches the larger number to the first spot, sets up for the next step
	    return (gcfRec (b, a));
	else //when larger number is in first spot (a > b)
	    return (gcfRec (b, a - b)); //decreases the numbers by the differences between till the numbers are the same
    }


    public static void gcfRecDriver ()
    {
	int num1, num2, gcf; //declare
	c.print ("Enter first number: ");
	num1 = getPositive ();
	c.print ("Enter second number: ");
	num2 = getPositive (); //input
	c.print ("Greatest Common Factor of " + num1 + " & " + num2 + " is " + gcfRec (num1, num2) + "."); //output, processing
    }


    public static int fib (int n)
    {
	int temp = 1, sum = 1, temp2; //declare
	for (int x = 3 ; x <= n ; x++) //the first 2 terms are 1
	{
	    temp2 = temp;//the previous term
	    temp = sum;//the other previous term
	    sum += temp2;//add up the last 2 terms
	}
	return sum;
    }


    public static void fibDriver ()
    {
	int term;
	c.print ("Enter a term of the Fibonacci series: ");
	term = getPositive (); //input 
	c.print ("Term " + term + " of the Fibonacci series is " + fib (term)+"."); //output and processing
    }


    public static int fibRec (int n)
    {
	if (n <= 2) //first 2 terms are 1
	    return 1;
	else //adds the number previous to the term and the number before that in the fib series
	    return (fibRec(n - 1) + fibRec(n - 2));
    }


    public static void fibRecDriver ()
    {
	int term;
	c.print ("Enter a term of the Fibonacci series: ");
	term = getPositive (); //input
	c.print ("Term " + term + " of the Fibonacci series is " + fibRec (term)+"."); //output, processing
    }


    public static void main (String[] args)
    {
	c = new Console ();
	char choice;  //declare
	do //menu 
	{
	    c.clear (); 
	    c.println ("Without Recursion\n1. GCF\n2. Fibonacci\nWith Recursion\n3. GCF\n4. Fibonacci\n0. Exit"); //menu options
	    choice = c.getChar (); //input
	    c.clear (); //clears menu options, leads way for Drivers
	    if (choice == '1')
		gcfNormDriver ();
	    else if (choice == '2')
		fibDriver ();
	    else if (choice == '3')
		gcfRecDriver ();
	    else if (choice == '4')
		fibRecDriver();
	    c.print ("\n\nPress any key to continue.");
	    c.getChar ();
	}
	while (choice != 0); //till you choose to exit
	c.close ();
    } // main method
} // Recursion class
