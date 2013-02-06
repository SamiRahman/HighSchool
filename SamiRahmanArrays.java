// Sami Rahman
//TIK2O3
// Array Methods

import java.awt.*;
import hsa.Console;
import hsa.TextInputFile;

public class SamiRahmanArrays
{
    static Console c;           // The output console

    public static String[] fill ()
    {
	c.clear ();

	c.print ("File name to import:  ");
	String fname = c.readLine (); //input

	TextInputFile filein = new TextInputFile (fname); // open file for reading

	c.println ();
	
	String names[] = new String [100000]; //accepts up to 100, 000 lines
	int x = 0;//counter
	while (!filein.eof ()) //file not finished
	{
	    names [x] = filein.readLine ();
	    c.print (x + 1 + ". ");//formatting (2. , 3. , etc.)
	    c.println (names [x]); //prints the names
	    x++;//increase counter
	}

	String names2[] = new String [x]; //resizes array appropriately
	
	for (int y = 0 ; y < names2.length ; y++)
	    names2 [y] = names [y]; //copy down array

	c.print ("\n\n< Hit any key to return to menu >");
	c.getChar ();

	return names2; //return resized array
    }


    public static void showList (String list[]) //properly shows list + error checks
    {
	c.clear ();

	if (list.length == 0)
	    c.println ("Error: Empty list.");

	else
	{
	    c.println ();
	    properPrint (list);
	}

	c.println ("\n< Hit any key to continue >");
	c.getChar ();
    }


    public static String[] enter ()
    {
	c.clear ();

	int howmany = 0; 
	String names[] = new String [60]; //accepts up to 60 names

	c.println ("Enter a list of up to 60 names. Hit enter after each name. Press 'q' to stop:\n");
	c.print ("1. "); //priming formatting
	String temp = c.readLine (); //priming
	while (!temp.equalsIgnoreCase ("q")) //loops until 'q' entered
	{
	    names [howmany] = temp;
	    howmany++;
	    c.print ((howmany + 1) + ". ");//formatting
	    temp = c.readLine (); //input
	}

	String names2[] = new String [howmany]; //create + resizes array

	for (int x = 0 ; x < names2.length ; x++)//copy downu to resized array
	    names2 [x] = names [x];

	c.print ("\n\n< Hit any key to return to menu >");

	c.getChar ();

	return names2;//return resized array
    }


    public static String[] search (String list[], char letter) //char version
    {
	if (list.length > 0) //checks list is not 0 or negative
	{
	    String temp[] = new String [list.length];//create temp
	    int y = 0;

	    for (int x = 0 ; x < list.length ; x++)
	    {
		if (Character.toUpperCase(list [x].charAt (0)) == Character.toUpperCase(letter)) //changes everything to uppercase but doesn't change value
		{
		    temp [y] = list [x];
		    y++;
		}
	    }

	    String temp2[] = new String [y];//creates resized array

	    for (int x = 0 ; x < y ; x++)
	    {
		temp2 [x] = temp [x]; //copy down
	    }

	    return temp2; //return new array
	}

	else
	    return list;//if list is empty return it

    }


    public static String[] search (String list[], String surname)  //search method
    {
	String temp[] = new String [list.length];//create temp array array
	int howmany = 0;

	for (int x = 0 ; x < list.length ; x++)
	{
	    if (list [x].length () >= surname.length ()) //error check: stops crashing if the entered surname is longer than list[x]
	    {
		if (list [x].substring (0, surname.length ()).equalsIgnoreCase (surname)) //checks if list[x] matches  
		{
		    temp [howmany] = list [x];
		    howmany++;//increase howmany
		}
	    }
	}

	String temp2[] = new String [howmany]; //create + resizes new array

	for (int y = 0 ; y < howmany ; y++)
	    temp2 [y] = temp [y];//copy down array

	return temp2; //return new array
    }


    public static String[] finalSearch (String classlist[])
    {
	c.clear ();
	char choice;

	//Input
	c.println ("Would you like to search by:  ");
	c.println ("         a) First letter of name");
	c.println ("     other) Surname");
	
	choice = c.getChar ();

	if (choice == 'a')//searching with first letter
	{
	    c.println ("\nEnter the letter: ");
	    char letter = c.getChar ();//input
	    c.println ("Chosen letter is '" + letter + "'.");
	    String searchresults[] = search (classlist, letter); //run search method

	    if (searchresults.length == 0) // if nothing is returned in the search
		c.println ("\nNo matches were found.");
	    else
	    {
		c.println ();
		properPrint (searchresults);//nicely print list in a list format 
	    }
	    
	    c.print ("\n< Hit any key to continue >");
	    c.getChar ();
	    return searchresults;
	}

	else //searching with surname
	{
	    c.print ("\nEnter the surname: ");
	    String surname = c.readLine ();//input
	    String searchresults[] = search (classlist, surname); //calls search method 


	    if (searchresults.length <= 0) //if nothing is in returned list
		c.println ("\nNo matches were found.");

	    else
	    {
		c.println ();
		properPrint (searchresults); //nicely print list in list format
	    }

	    c.print ("\n< Hit any key to continue >");
	    c.getChar ();
	    return searchresults;

	}

    }


    public static String[] add (String list[], String newname)
    {
	String temp[] = new String [list.length + 1]; //add extra spot to new array

	for (int x = 0 ; x < list.length ; x++) //copies array up to spot
	    temp [x] = list [x];

	temp [list.length] = newname;//last spot is the entered name

	return temp;
    }


    public static String[] finalAdd (String classlist[])
    {
	c.clear ();

	//Input
	c.print ("Enter a name to add: ");
	String name = c.readLine ();//input

	classlist = add (classlist, name); //calls add method

	c.println ();
	properPrint (classlist);//nicely prints list in list format

	c.println ();
	c.print ("< Hit any key to return to menu>");
	c.getChar ();

	return classlist;
    }


    public static String[] remove (String list[], int pos)
    {

	String temp[] = new String [list.length - 1];//creates new array minus one spot 

	if (list.length > 0) //if there's an element in the list
	{
	    for (int x = 0 ; x < pos ; x++)
	    {
		temp [x] = list [x]; //copy array down till pos
	    }
	    for (int x = pos ; x < list.length - 1 ; x++)
	    {
		temp [x] = list [x + 1];//add pos, then copy down +1 from original

	    }
	    return temp;
	}


	else 
	    return list;//if list has no elements
    }


    public static String[] finalRemove (String classlist[])
    {
	c.clear ();

	if (classlist.length == 0) //error check
	    c.print ("Error: Empty list.");

	else
	{
	    String oldlist[] = classlist;
	    showLastList (oldlist); //display old list for ease of use
	    //Input
	    c.setCursor (1, 1);
	    c.print ("Enter position to remove: ");
	    int pos = c.readInt ();

	    int line = 2;
	    while (pos < 0 || pos > (classlist.length - 1)) //prevents crashing, error check for if pos exceeds list
	    {
		showLastList (classlist);
		c.setCursor (line, 1);
		c.print ("Error: Invalid position. Enter again: ");
		pos = c.readInt ();
		line++;//go down one line
	    }

	    classlist = remove (classlist, pos); //calls remove method

	    c.println (); 
	    properPrint (classlist); //nicely print in list format
	    showLastList (oldlist); //also show old list
	}

	c.println("\n< Hit any key to continue >\n");
	c.getChar ();

	return classlist;
    }


    public static String[] sort (String list[])
    {
	boolean completeSort = false;
	String temp;

	for (int x = 0 ; x < list.length - 1 && !completeSort ; x++)
	{
	    completeSort = true;
	    for (int y = 0 ; y < list.length - 1 ; y++)
	    {
		if (list [y].compareTo (list [y + 1]) > 0) //compares strings, switch positions if not in correct positions
		{
		    completeSort = false;
		    temp = list [y];
		    list [y] = list [y + 1];
		    list [y + 1] = temp;
		}
	    }
	}

	return list;
    }


    public static String[] finalSort (String classlist[])
    {
	c.clear ();

	if (classlist.length <= 0) //error check if list is empty 
	    c.print ("Error: Empty list.");

	else
	{
	    c.println ();
	    classlist = sort (classlist); //calls sort method
	    properPrint (classlist);
	}

	c.getChar ();

	return classlist;
    }


    public static void properPrint (String list[]) //nicely prints list in list format
    {
	for (int x = 0 ; x < list.length ; x++) //prints the list
	{
	    c.print (" " + (x + 1) + ". ");//formatting
	    c.println (list [x]);//prints item
	}
    }


    public static void showLastList (String list[]) //shows last list for ease of use, display on right side
    {
	c.setCursor (1, 50);//formatting
	c.println ("Previous List");
	c.setCursor (2, 50);
	c.print ("-------------");//formatting
	for (int x = 0 ; x < list.length ; x++)//go through all elements in list
	{
	    c.setCursor (x + 3, 50); //formatting
	    c.print (x + 1 + ". "); //formatting
	    c.println (list [x]); //print item
	}
    }


    public static void main (String[] args)
    {
	c = new Console ();
	
	//define variables
	char choice;
	
	String classlist[] = new String [0]; //initialize array
	String group[] = new String [0]; //initialize array

	do
	{
	    c.clear ();

	    c.println ("Arrays Methods");
	    c.println ("--------------");

	    c.println ("1. Fill");
	    c.println ("2. Add");
	    c.println ("3. Remove");
	    c.println ("4. Enter");
	    c.println ("5. Search");
	    c.println ("6. Sort");
	    c.println ("7. Show List");
	    c.println ("0. Quit");

	    c.println ("\nBy: Sami Rahman, Period 2");//formatting


	    choice = c.getChar ();

	    if (choice == '1')
		classlist = fill (); 

	    else if (choice == '2')
		classlist = finalAdd (classlist); 

	    else if (choice == '3')
		classlist = finalRemove (classlist);

	    else if (choice == '4')
		classlist = enter ();

	    else if (choice == '5')
		group = finalSearch (classlist);

	    else if (choice == '6')
		classlist = finalSort (classlist);

	    else if (choice == '7')
		showList (classlist);
	}


	while (choice != '0');
	
	c.println();
	c.println("Goodbye!");
	c.print ("\n< Hit any key to close this window >");
	c.getChar();
	
	c.close ();//close window


    } // main method
} // SamiRahmanArrays class
