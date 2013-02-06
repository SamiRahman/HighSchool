// The "Recursion" class. REVISED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
import java.awt.*;
import hsa.Console;

public class Recursion
{
    static Console c;           // The output console
    
    public static int getPositive ()
    {        
	int choice;
	choice = c.readInt();
	while (choice < 1)//checks if number is positive
	{
	    c.print ("Please enter a positive number: ");//error message
	    choice = c.readInt ();//request number again
	}        
	return choice;
    }
    public static int GCF (int num1, int num2)
    {
	if(num1 == num2)//if numbers are the same then the gcf is that number
	    return num1;
	else if(num1 < num2)//reverses numbers to set up for the next step
	    return (GCF(num2, num1));//recalls the method with the numbers in the right place
	else
	    return (GCF(num2, num1 - num2));//decreases the numbers by the differences between until the numbers are the same
    }
    public static void GCFDriver ()
    {
	c.clear();//input
	c.print("Enter first integer: ");
	int num1 = getPositive();
	c.print("Enter second integer: ");
	int num2 = getPositive();
	//output and processing
	c.print("\nThe Greatest Common Factor is " + GCF(num1, num2));
	c.getChar();
	c.clear();
    }
    public static int GCF2 (int num1, int num2)
    {
	int temp;
	while(num1 != num2)//if numbers are the same then the gcf is that number       
	    if(num1 < num2)//reverses numbers to set up for the next step
	    {
		temp = num1;//sets up temp to be used for number switching
		num1 = num2;
		num2 = temp;
	    }
	    else//decreases the numbers by the differences between until the numbers are the same

	    {
		temp = num1 - num2;
		num1 = num2;
		num2 = temp; 
	    }        
	return num1;
    }
    public static void GCF2Driver ()
    {
	c.clear();//input
	c.print("Enter first integer: ");
	int num1 = getPositive();
	c.print("Enter second integer: ");
	int num2 = getPositive();
	//output and processing
	c.print("\nThe Greatest Common Factor is " + GCF2(num1, num2));
	c.getChar();
	c.clear();
    }
    public static int fibonacci(int num)
    {
	if(num<=2)//the first and second terms are both 1
	    return 1;
	else//adds the previous number and the previous previous number in the fibonacci series
	    return (fibonacci(num-1) + fibonacci(num-2));
    }
    
    public static void fibonacciDriver ()
    {
	c.clear();//input
	c.print("Enter integer: ");
	int num1 = getPositive();
	//output and processing        
	c.print("\nThe " + num1 + "th fibonacci number is " + fibonacci(num1));
	c.getChar();
	c.clear();
    }
    public static int fibonacci2 (int num)
    {
	int sum = 1, temp = 1, temp2;
	for(int x = 3; x <= num; x++)//the first and second terms are both 1
	{
	    temp2 = temp;//sets one of the previous terms
	    temp = sum;//sets the other previous term
	    sum = sum + temp2;//adds the two previous terms
	}
	return sum;
    }
    public static void fibonacci2Driver ()
    {
	c.clear();//input
	c.print("Enter integer: ");
	int num1 = getPositive();
	//output and processing        
	c.print("\nThe " + num1 + "th fibonacci number is " + fibonacci2(num1));
	c.getChar();
	c.clear();
    }
    
    
    public static void main (String[] args)
    {
	c = new Console (); 
	char choice;//declaration
	do
	{   //menu
	    c.print ("Recursion Practice\na. GCFrecursion\nb. GCFnonrecursion\nc. fibonacci rescursion\nd. fibonacci non recursion\n\nq. quit\n\n");
	    choice = c.getChar();//asks for which program
	    if (choice == 'a')//GCF function using recursion            
		GCFDriver ();            
	    else if (choice == 'b')//GCF function without recursion
		GCF2Driver ();
	    else if (choice == 'c')//fibonacci function with recursion
		fibonacciDriver ();
	    else if (choice == 'd')//fibonacci function without recursion
		fibonacci2Driver ();
	}
	while (choice != 'q');//0 to exit        
	c.close ();
	// Place your program here.  'c' is the output console
    } // main method
} // MethodPractice class


