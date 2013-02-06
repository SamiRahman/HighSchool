// The "MethodPractice" class.
/*Input

getInt accepts two two integers (a low and high value), then forces the user to enter a value between low and high and returns this value.
Variations: getPositive, getLetter, getVowel

Processing

sumRange accepts two integers and returns the sum of all integers in that range.
Variations: sum of only some of the values, product

numFactors accepts an integer and returns the number of factors it has.
Variations: sum of factors, prime, gcf, lowest common multiple, sum of odd

category accepts an integer and returns a string depending on whether it is negative, positive or zero
Variations: many possibilities using if statements

getRandom accepts two integer values, then returns a random value in that range
Variations: coin flip, dice roll, random letter

Include a main driver that demonstrates the workings of these functions */
import java.awt.*;
import hsa.Console;

public class MethodPractice
{
    static Console c;           // The output console

    public static int getInt (int low, int high)
    {
	int input = c.readInt (); //input an integer value
	while (input < low || input > high) //if input is out of range
	{
	    c.print ("Number is out of range. Enter again: ");
	    input = c.readInt (); //re-input
	}
	return input;
    }


    public static void drivergetInt ()
    {
	int lownum, highnum, ans;//declaration

	c.print ("Enter low: ");//input
	lownum = c.readInt ();
	c.print ("Enter high: ");
	highnum = c.readInt ();
	while (highnum < lownum) //error checking
	{
	    c.print ("Invalid. Re-enter: ");
	    highnum = c.readInt ();
	}
	c.print ("Input a number between this range: ");
	getInt (lownum, highnum);
	c.print ("Success!"); //once number is in the range
    }


    public static int getPositive ()
    {
	int input = c.readInt (); //input an integer value
	while (input <= 0) //if input is zero or negative
	{
	    c.print ("Number is not positive. Enter again: "); //keep reentering till a positive number is input
	    input = c.readInt (); //re-input
	}
	return input;
    }


    public static void drivergetPositive ()
    {
	int num;
	c.print ("Enter a positive number: ");
	num = getPositive (); //keep reentering till a positive number is input
	c.print ("Success!");
    }


    public static int sumRange (int low, int high)
    {
	int sum = low; //start off the sum as the low number
	for (int x = 0 ; x < (high - low) ; x++) //loop for as many numbers as there are between the high and low (range)
	{
	    int next = low + (x + 1); //additive
	    sum = sum + next; // add the additive to the old sum (base)
	}
	return sum;
    }


    public static void driversumRange ()
    {
	int lownum, highnum, thesum; //initialization
	c.print ("Enter low: "); //input
	lownum = c.readInt ();
	c.print ("Enter high: ");
	highnum = c.readInt ();
	while (highnum < lownum)//error checking
	{
	    c.print ("Invalid. Re-enter: ");
	    highnum = c.readInt ();
	}
	thesum = sumRange (lownum, highnum);//sum of the numbers in the range
	c.print ("The sum of the numbers in this range is: " + thesum); //output
    }


    public static long productRange (int low, int high)
    {
	long prod = low; //initialization, start off the product as the low number
	for (int x = 0 ; x < (high - low) ; x++) //loop through range
	{
	    int next = low * (x + 1); // multiplicative 
	    prod = prod * next; // multiply the old sum to the multiplicative
	}
	return prod;
    }


    public static void driverproductRange ()
    {
	int lownum, highnum; //initialization
	long theprod;
	c.print ("Enter low: "); //input
	lownum = c.readInt ();
	c.print ("Enter high: ");
	highnum = c.readInt ();
	while (highnum < lownum) //error checking
	{
	    c.print ("Invalid. Re-enter: ");
	    highnum = c.readInt ();
	}
	theprod = productRange (lownum, highnum); //calculate product of the numbers in the range
	c.print ("The product of the numbers in this range is: " + theprod); //output
    }


    public static int numFactors (int num) 
    {
	int factors = 2; //declaration
	for (int x = 2 ; x <= num / 2 ; x++)
	{
	    if (num % x == 0) //if there is a factor
		factors++;//increase number of factors
	}
	return factors;//return number of factors
    }


    public static void drivernumFactors ()
    {
	int input, answer; //declare
	c.print ("Enter a number to find its number of factors: "); 
	input = c.readInt (); //input
	while (input < 0) //error checking
	{
	    c.print ("Invalid number. Retry: ");
	    input = c.readInt ();
	}
	answer = numFactors (input);//processing
	c.print ("Your number, " + input + ", has " + answer + " factors.");//output
    }


    public static boolean prime (int num)
    {
	boolean prime = false;//assume number is not prime
	int factors = 2; //
	for (int x = 2 ; x <= num / 2 ; x++)
	{
	    if (num % x == 0)
		factors++;//increase number of factors by one if a factor is found
	}
	if (num == 0 || num == 1)//0 and 1 are NOT prime, special cases
	    prime = false;
	else if (factors == 2)//if there are only 2 factors (1 and the number)
	    prime = true;//it is prime
	return prime;
    }


    public static void driverprime ()
    {
	int input;
	boolean answer; //declaration
	c.print ("Enter a number to figure out if it is a prime number: "); //input
	input = c.readInt ();
	while (input < 0)//error checking
	{
	    c.print ("Invalid number. Retry: ");
	    input = c.readInt ();//input
	}
	answer = prime (input);//check if the number is a prime number
	if (answer) //outputs
	    c.print ("Your number, " + input + ", is a prime number.");
	else
	    c.print ("Your number, " + input + ", is NOT a prime number.");
    }


    public static int getRandom (int low, int high)
    {
	int randnum = (int) (Math.random () * Math.abs (high - low)) + low;//random number in the range
	return randnum;
    }


    public static void drivergetRandom ()
    {
	int lownum, highnum, randomnum;
	c.print ("Enter low: ");
	lownum = c.readInt ();
	c.print ("Enter high: ");
	highnum = c.readInt ();
	while (highnum < lownum)
	{
	    c.print ("Invalid. Re-enter: ");
	    highnum = c.readInt ();
	}
	randomnum = getRandom (lownum, highnum);
	c.print ("A random number in this range is: " + randomnum);
    }


    public static boolean coinFlip ()
    {
	boolean heads = true;
	double decimal = Math.random ();
	if (decimal < .5) //tails = 50% of the time
	    heads = false; //tails
	return heads; //heads or tails
    }


    public static void drivercoinFlip ()
    {
	boolean h;//declaration
	c.println ("Press any key to flip a coin.");
	c.getChar ();
	h = coinFlip ();
	if (h) //if heads 
	    c.print ("Heads.");
	else //not heads, must be tails
	    c.print ("Tails.");
    }


    public static String category (int num)
    {
	String name;
	if (num < 0) //negative number
	    name = "Negative";
	else if (num == 0) //if its zero
	    name = "Zero";
	else //if its not negative nor zero, it must be positive
	    name = "Positive";
	return name; //return the string
    }


    public static void drivercategory ()
    {
	int num;
	String cat;//declaration
	c.print ("Enter a number for categorization: ");//input
	num = c.readInt ();
	cat = category (num);//figure out the category of this number
	c.print ("This number is: " + cat);//output
    }


    public static String manyPoss (double num)//takes in decimals
    {
	String name; //declaration 
	if (num < 0 && num > -100) //possibilities
	    name = "That's right. It was so bad, they should pay YOU to own it.";
	else if (num == 0)
	    name = "A wise man once said: the best things in life are free.";
	else if (num >= 1 && num <= 5)
	    name = "The pirates are waiting for you in Pacific Mall.";
	else if (num >= 6 && num <= 20)
	    name = "Cheapskate.";
	else if (num == 34.5)
	    name = "You get beeeeest price! Russell Peters agrees.";
	else if (num >= 21 && num <= 40)
	    name = "The price is right! You love to buy things at retail value. You're so normal, it's boring.";
	else if (num >= 41 && num <= 99)
	    name = "HARDCORE FAN! You deserve the collectors edition!!!";
	else if (num >= 100)
	    name = "Please send your cheque to: Sami Rahman, P.O. Box 193, Toronto, Ontario, M1H 4F9";
	else
	    name = "I'd rather watch Spiderman too.";
	return name; 
    }


    public static void drivermanyPoss ()
    {
	double num;//for decimals
	String cat;
	c.print ("How much money would you pay for Avatar on blu-ray?: ");
	num = c.readDouble (); //to read in decimals
	cat = manyPoss (num);  //categorize
	c.print (cat);
    }


    public static void main (String[] args)
    {
	c = new Console ();

	int choice; //menu
	do
	{
	    c.println ("*Basics");
	    c.println ("0. exit");
	    c.println ("1. getInt");
	    c.println ("2. sumRange");
	    c.println ("3. numFactors");
	    c.println ("4. getRandom");
	    c.println ("5. category");
	    c.println ("*Variations");
	    c.println ("6. getPositive");
	    c.println ("7. productRange");
	    c.println ("8. prime");
	    c.println ("9. manyPoss");
	    c.println ("10. coinFlip");
	    c.print ("Your choice?: ");
	    choice = c.readInt ();
	    c.clear ();//after choosing

	    if (choice == 1) //menu options
		drivergetInt ();
	    else if (choice == 2)
		driversumRange ();
	    else if (choice == 3)
		drivernumFactors ();
	    else if (choice == 4)
		drivergetRandom ();
	    else if (choice == 5)
		drivercategory ();
	    else if (choice == 6)
		drivergetPositive ();
	    else if (choice == 7)
		driverproductRange ();
	    else if (choice == 8)
		driverprime ();
	    else if (choice == 9)
		drivermanyPoss ();
	    else if (choice == 10)
		drivercoinFlip ();
	    if (choice > 0 && choice < 11)//was an option and not exit
	    {
		c.println ("\n\nPress any key to return to main menu.");
		c.getChar ();
		c.clear ();
	    }
	}
	while (choice != 0); //till they want to exit

	c.clear (); //exit steps
	c.println ("Goodbye! Press any key to exit.");
	c.getChar ();
	c.close ();
    } // main method
} // MethodPractice class


