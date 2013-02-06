import java.awt.*;
import hsa.Console;
import hsa.TextOutputFile;
import hsa.TextInputFile;

public class map
{
    static Console c;

    public static void main (String[] args)
    {
	c = new Console (30, 120); // set window size

	char choice;
	char house[] [] = startmap (20, 30); // set map size to 20x30 blocks
	// addRandom (house, 5, 't');
	// addRandom (house, 10, 'm');
	// addRandom (house, 5, 'k');
	// addwall (house, 5, 'c');
	// addwall (house, 5, 'r');
	do
	{
	    c.clear ();

	    print (house);

	    cprint ("Map", 4, 90);
	    cprint ("_________", 5, 90);
	    cprint ("1. add", 6, 90);
	    cprint ("2. search", 7, 90);
	    cprint ("3. save", 8, 90);
	    cprint ("4. load", 9, 90);
	    cprint ("5. remove", 10, 90);

	    cprint ("0. quit", 12, 90);

	    choice = c.getChar ();

	    if (choice == '1') //second menu for all the add methods
	    {
		c.clear ();
		print (house);
		cprint ("What would you like to add", 6, 78);
		cprint ("1. Walls", 8, 90);
		cprint ("2. Doors", 9, 90);
		cprint ("3. Items", 10, 90);
		cprint ("4. Single Items", 11, 90);

		char choice2 = c.getChar ();

		if (choice2 == '1')
		    addwalldriver (house);
		else if (choice2 == '2')
		    adddoordriver (house);
		else if (choice2 == '3')
		    addthingsdriver (house);
		else if (choice2 == '4')
		    additemdriver (house);

	    }

	    else if (choice == '2')
		searchdriver (house);

	    else if (choice == '3')
		save (house);

	    else if (choice == '4')
		load (house);

	    else if (choice == '5')
		removedriver (house);

	}
	while (choice != '0');

	c.close ();

    }


    public static void save (char house[] [])
    {
	c.clear (); //resets map
	print (house);

	cprint ("What name would you like to save it as: ", 6, 78);
	String sname = c.readLine ();
	TextOutputFile fileout = new TextOutputFile (sname + ".txt", false);
	String savings = "";
	for (int x = 0 ; x < house.length ; x++)
	{
	    for (int y = 0 ; y < house [0].length ; y++)
	    {
		savings = savings + house [x] [y];
	    }
	    savings += "\n";
	}
	fileout.print (savings);
    }


    public static void load (char house[] [])
    {
	c.clear (); //reset map
	print (house);

	cprint ("File name to load: ", 6, 78);
	String fname = c.readLine ();
	TextInputFile filein = new TextInputFile (fname + ".txt"); // open file for reading

	String line;
	for (int x = 0 ; x < house.length ; x++)
	{
	    line = filein.readLine ();
	    for (int y = 0 ; y < house [0].length ; y++)
		house [x] [y] = line.charAt (y);
	}

    }


    public static int search (char house[] [], char choice)
    {
	int counter = 0; //counts how many targets found
	for (int x = 0 ; x < house.length ; x++) //looks through the whole array
	    for (int y = 0 ; y < house [0].length ; y++)
		if (house [x] [y] == choice) //finds match
		{
		    counter++;
		    house [x] [y] = ('F'); //highlights

		}
	print (house);
	if (choice == 'T') //prints different messages
	    cprint (counter + " treasures were found", 12, 78);
	else if (choice == 'M')
	    cprint (counter + " monsters were found", 12, 78);
	else if (choice == 'K')
	    cprint (counter + " keys were found", 12, 78);
	c.getChar ();
	for (int x = 0 ; x < house.length ; x++) //looks through the whole array
	    for (int y = 0 ; y < house [0].length ; y++)
		if (house [x] [y] == 'F') //changes everything back "un highlight it"
		{
		    house [x] [y] = choice;
		}
	print (house);


	return counter;
    }


    public static void searchdriver (char house[] [])
    {
	c.clear (); //reset map
	print (house);

	cprint ("What do you want to search for ", 6, 78); //menu for search
	cprint ("1. Treasure", 7, 82);
	cprint ("2. Monsters", 8, 82);
	cprint ("3. Doors", 9, 82);
	cprint ("4. Keys", 10, 82);
	char choice;
	do
	    choice = c.getChar ();
	while (choice != '1' && choice != '2' && choice != '3' && choice != '4');

	if (choice == '1') //cho0ses a choice
	    choice = 'T';
	else if (choice == '2')
	    choice = 'M';
	else if (choice == '3')
	    choice = 'D';
	else if (choice == '4')
	    choice = 'K';
	int count = search (house, choice);


	c.getChar ();

    }


    public static char[] [] addRandom (char house[] [], int lim, char choice)  //add items at random places
    {
	for (int z = 0 ; z < lim ; z++) //lim is the number input that says how many to add
	{
	    //randomly selects a spot
	    int x = (int) (Math.random () * 18) + 1; //randomly selects row
	    int y = (int) (Math.random () * 28) + 1; //randomly selects column
	    //if the spot selected is already occupied it chooses another
	    while (house [x] [y] == 'W' || house [x] [y] == 'D' || house [x] [y] == 'T' || house [x] [y] == 'M' || house [x] [y] == 'K')
	    {
		x = (int) (Math.random () * 18) + 1;
		y = (int) (Math.random () * 28) + 1;
	    }
	    if (choice == 't' || choice == 'T') //add item according to the choice input
		house [x] [y] = 'T';
	    else if (choice == 'm' || choice == 'M')
		house [x] [y] = 'M';
	    else if (choice == 'k' || choice == 'K')
		house [x] [y] = 'K';
	}
	return house;
    }


    public static void addthingsdriver (char house[] [])
    {
	c.clear (); //reset map
	print (house);
	char choice;
	//input
	cprint ("What item would you like to add (T/M/K): ", 6, 78); //what item to add
	choice = creadChar ('t', 'm', 'k', 'K', 'T', 'M', 7, 78);
	eprint ("What item would you like to add (T/M/K): " + choice, 6, 78); //reprints message with input

	cprint ("How many would you like to add : ", 7, 78); //howmany to add
	int limlim = countspace (house); //empty spaces
	int lim = getInt (limlim, 0, 8, 78); //checks integer to be within range
	eprint ("How many would you like to add : " + lim, 7, 78); //represents message with input
	house = addRandom (house, lim, choice);

    }


    public static char[] [] addstuff (char house[] [], int x, int y, char choice)  //add method for everything except walls
    {
	if (choice == 't') //according to choice, add an item
	    house [x] [y] = 'T';
	else if (choice == 'm')
	    house [x] [y] = 'M';
	else if (choice == 'd')
	    house [x] [y] = 'D';
	else if (choice == 'k')

	    house [x] [y] = 'K';
	return house;
    }


    public static void adddoordriver (char house[] [])
    {
	c.clear (); //reset map
	print (house);
	printnumbers (); //coordinates
	//input
	cprint ("Which row would you like to add it: ", 6, 78); //add row
	int row = getInt (19, 0, 7, 78);
	eprint ("Which row would you like to add it: " + row, 6, 78); //reprints message with input

	cprint ("Which column would you like to add it: ", 8, 78); //add col
	int col = getInt (29, 0, 9, 78);
	eprint ("Which column would you like to add it: " + col, 8, 78); //reprints msg with input

	while (house [row] [col] != 'W') //cant add doors where no wall so it repeats
	{
	    cprint ("Can't put doors where there's no wall", 9, 78); //output
	    c.getChar ();
	    c.clear ();
	    print (house);
	    printnumbers ();
	    //input
	    cprint ("Which row would you like to add it: ", 6, 78);
	    row = getInt (19, 0, 7, 78);
	    eprint ("Which row would you like to add it: " + row, 6, 78);

	    cprint ("Which column would you like to add it: ", 8, 78);
	    col = getInt (29, 0, 9, 78);
	    eprint ("Which column would you like to add it: " + col, 8, 78);
	}

	addstuff (house, row, col, 'd');

    }


    public static void additemdriver (char house[] [])
    {
	c.clear (); //reset map
	print (house);
	printnumbers (); //coordinates
	//input
	cprint ("Which row would you like to add it: ", 6, 78); //row
	int row = getInt (19, 0, 7, 78);
	eprint ("Which row would you like to add it: " + row, 6, 78); //reprint msg

	cprint ("Which column would you like to add it: ", 8, 78); //column
	int col = getInt (29, 0, 9, 78);
	eprint ("Which column would you like to add it: " + col, 8, 78); //reprint msg

	cprint ("Which item would you like to add (T/M/K): ", 10, 78); //choice treasure/monster/key
	char choice = creadChar ('t', 'm', 'k', 'T', 'M', 'K', 11, 78);
	eprint ("Which item would you like to add (T/M/K): " + choice, 10, 78); //reprint msg
	//if something is already there it asks again
	while (house [row] [col] == 'W' || house [row] [col] == 'D' || house [row] [col] == 'T' || house [row] [col] == 'M' || house [row] [col] == 'K')
	{
	    c.clear ();
	    print (house);
	    printnumbers ();

	    cprint ("Something is already there Try again", 6, 78);
	    c.getChar ();
	    cprint ("Which row would you like to add it: ", 6, 78);
	    row = getInt (19, 0, 7, 78);
	    eprint ("Which row would you like to add it: " + row, 6, 78);
	    cprint ("Which column would you like to add it: ", 8, 78);
	    col = getInt (29, 0, 9, 78);
	    eprint ("Which column would you like to add it: " + col, 8, 78);
	}
	addstuff (house, row, col, choice);

    }


    public static char[] [] addwall (char house[] [], int num, char choice)  //add walls
    {
	if (choice == 'c' || choice == 'C') //if choice is column it adds a column
	    for (int x = 0 ; x < house.length ; x++) // finds the choice column
		house [x] [num] = 'W'; //sets everything in that column as walls
	else if (choice == 'r' || choice == 'R') //if choice is row it adds a row
	    for (int x = 0 ; x < house [0].length ; x++)
		house [num] [x] = 'W';
	return house;
    }


    public static void addwalldriver (char house[] [])
    {
	c.clear (); //reset page
	print (house);
	printnumbers (); //coordinates
	//input
	cprint ("Add a column or a row(r/c): ", 6, 78); //input for row or column
	char choice = creadChar ('r', 'c', 'R', 'C', 7, 78);
	int num = 0;
	if (choice == 'r' || choice == 'R') //prints row request
	{
	    cprint ("Add a column or a row(r/c): Row", 6, 78); //input
	    cprint ("Which row would you like to add: ", 7, 78);
	    num = getInt (19, 0, 8, 78);
	}
	else if (choice == 'c' || choice == 'C') //prints column request
	{
	    cprint ("Add a column or a row(r/c): Column", 6, 78); //input
	    cprint ("Which column would you like to add: ", 7, 78);
	    num = getInt (29, 0, 8, 78);
	}

	house = addwall (house, num, choice);

    }


    public static void removedriver (char house[] [])  //sets a space as blank which is like remove
    {
	c.clear (); //reset map
	print (house);
	printnumbers (); //coordinates
	//input
	cprint ("Which row is the spot to be removed: ", 6, 78);
	int row = getInt (19, 0, 7, 78);
	eprint ("Which row is the spot to be removed: " + row, 6, 78);

	cprint ("Which column is the spot to be removed: ", 8, 78);
	int col = getInt (29, 0, 9, 78);
	eprint ("Which column is the spot to be removed: " + col, 8, 78);
	if (house [row] [col] == 'D') //when you remove a door it turns to a wall
	    house [row] [col] = 'W';
	else
	    house [row] [col] = ' '; //removes the item there

    }



    // Initializes the map with walls and a door
    public static char[] [] startmap (int rows, int cols)
    {
	char house[] [] = new char [rows] [cols]; // define 2-d array size

	for (int row = 0 ; row < house.length ; row++) // house.length is # rows
	    for (int col = 0 ; col < house [0].length ; col++) // house[0].length # cols
	    {
		// put walls along edge of house array
		if (row == 0 || row == rows - 1 || col == 0 || col == cols - 1)
		    house [row] [col] = 'W'; // put up a wall
		else
		    house [row] [col] = ' '; // blank space
	    }

	house [rows - 1] [cols / 2] = 'D'; // make a double door in middle of bottom side
	house [rows - 1] [cols / 2 + 1] = 'D';
	return house;
    }


    // Displays the map on the screen
    public static void print (char house[] [])
    {
	for (int row = 0 ; row < house.length ; row++)
	    for (int col = 0 ; col < house [0].length ; col++)
	    {

		if (house [row] [col] == 'D') // door
		{
		    c.setColor (Color.red);
		    if (row == 0 || row == 19)
			c.fillRect (col * 20, row * 20 + 5, 20, 10);
		    else if (col == 0 || col == 19)
			c.fillRect (col * 20 + 5, row * 20, 10, 20);
		    else if (house [row + 1] [col] == 'W' && house [row - 1] [col] == 'W' || house [row + 1] [col] == 'D' || house [row - 1] [col] == 'D')
			c.fillRect (col * 20 + 5, row * 20, 10, 20); // draw block
		    else
			c.fillRect (col * 20, row * 20 + 5, 20, 10);
		}

		else if (house [row] [col] == 'T') // treasure
		{
		    c.setColor (Color.yellow);
		    c.fillOval (col * 20, row * 20, 20, 20); // draw block
		    c.setColor (Color.black);

		    c.drawOval (col * 20 + 1, row * 20 + 1, 18, 18);
		    c.drawOval (col * 20 + 3, row * 20 + 3, 14, 14);
		    c.drawLine (col * 20 + 3, row * 20 + 8, col * 20 + 17, row * 20 + 8);
		    c.drawLine (col * 20 + 3, row * 20 + 8, col * 20 + 14, row * 20 + 15);
		    c.drawLine (col * 20 + 6, row * 20 + 15, col * 20 + 17, row * 20 + 8);
		    c.drawLine (col * 20 + 10, row * 20 + 3, col * 20 + 14, row * 20 + 15);
		    c.drawLine (col * 20 + 6, row * 20 + 15, col * 20 + 10, row * 20 + 3);


		}

		else if (house [row] [col] == ' ') // space will erase what was there
		{
		    c.setColor (Color.white);
		    c.fillRect (col * 20, row * 20, 20, 20); // draw block
		}
		else if (house [row] [col] == 'F') //highlight
		{
		    c.setColor (Color.green);
		    c.fillRect (col * 20, row * 20, 20, 20); // draw block
		    c.setColor (Color.black);
		    c.drawOval (col * 20, row * 20, 20, 20);
		    c.drawOval (col * 20 + 5, row * 20 + 5, 10, 10);
		    c.drawLine (col * 20 + 10, row * 20, col * 20 + 10, row * 20 + 20);
		    c.drawLine (col * 20, row * 20 + 10, col * 20 + 20, row * 20 + 10);
		}
		else if (house [row] [col] == 'W')
		{
		    c.setColor (Color.black);
		    c.fillRect (col * 20, row * 20, 20, 20); // draw block
		    c.setColor (new Color (167, 118, 69));
		    c.fillRect (col * 20, row * 20 + 2, 9, 8);
		    c.fillRect (col * 20 + 11, row * 20 + 2, 9, 8);
		    c.fillRect (col * 20 + 2, row * 20 + 12, 18, 8);

		}
		else if (house [row] [col] == 'M') //monster(snowman)
		{
		    c.setColor (Color.blue);
		    c.fillOval (col * 20 + 8, row * 20 + 2, 4, 4);
		    c.fillOval (col * 20 + 7, row * 20 + 5, 6, 6);
		    c.fillOval (col * 20 + 5, row * 20 + 10, 10, 10);
		}

		else if (house [row] [col] == 'K') //key
		{
		    c.setColor (Color.orange);
		    c.fillOval (col * 20, row * 20 + 5, 10, 10);
		    c.fillRect (col * 20 + 10, row * 20 + 9, 10, 2);
		    c.fillRect (col * 20 + 12, row * 20 + 11, 2, 2);
		    c.fillRect (col * 20 + 16, row * 20 + 11, 2, 2);
		    c.setColor (Color.white);
		    c.fillOval (col * 20 + 2, row * 20 + 8, 4, 4);
		}

	    }
    }


    public static int getInt (int top, int bottom, int x, int y)  //checks number to see if it is in range
    {
	int exp = c.readInt ();
	while (exp < bottom || exp > top) //make sure input is valid
	{
	    cprint ("                                          ", x, y); //erases a line before printing something on it
	    cprint ("Invalid Number Try Again ", x, y); //error message
	    exp = c.readInt (); //try again

	}

	return exp;
    }


    public static char creadChar (char a, char b, char A, char B, int x, int y)  //checks input to see if it is one of the choices
    {
	char choice = c.readChar ();
	if (choice == A) //if input is Caps it changes it to lowercase
	    choice = a;
	else if (choice == B)
	    choice = b;
	while (choice != a && choice != b) //make sure input is one of the choices
	{
	    cprint ("                         ", x, y); //erases a line before printing something new
	    cprint ("Invalid Input Try Again ", x, y);
	    choice = c.readChar ();
	    if (choice == A) //checks for Caps again
		choice = a;
	    else if (choice == B)
		choice = b;
	}

	return choice;
    }


    public static char creadChar (char a, char b, char d, char A, char B, char D, int x, int y)  //creadChar for 3 inputs
    {
	char choice = c.readChar ();
	if (choice == A)
	    choice = a;
	else if (choice == B)
	    choice = b;
	else if (choice == D)
	    choice = d;
	while (choice != a && choice != b && choice != d)
	{
	    cprint ("                         ", x, y);
	    cprint ("Invalid Input Try Again ", x, y);
	    choice = c.readChar ();
	    if (choice == A)
		choice = a;
	    else if (choice == B)
		choice = b;
	    else if (choice == D)
		choice = d;
	}

	return choice;
    }


    public static void cprint (String message, int x, int y)  //prints the message at a certain spot
    {
	c.setCursor (x, y);
	c.print (message);
    }


    public static void eprint (String message, int x, int y)  //clears a line for printing
    {
	cprint ("                                           ", x, y);
	cprint (message, x, y);
    }


    public static int countspace (char house[] [])  // counts the number of empty spaces for add random limit
    {
	int count = 0;
	for (int x = 0 ; x < house.length ; x++) //goes through whole array
	    for (int y = 0 ; y < house [0].length ; y++)
		if (house [x] [y] == ' ') //finds spaces
		    count++;

	return count;

    }


    public static void printnumbers ()  //print coordinates on the walls
    {
	c.setColor (Color.white);
	String numbers[] = {"0", "5", "10", "15", "20", "25"}; // array for printing numbers
	Font f = new Font ("Times New Roman", Font.PLAIN, 14); // font name, style, point size
	c.setFont (f);

	int ar = 0; //array number
	for (int x = 4 ; x < 505 ; x = x + 100) //loop for numbers on the column
	{
	    c.drawString (numbers [ar], x, 15);
	    ar++;
	}
	ar = 0;
	for (int y = 15 ; y < 416 ; y = y + 100) //loop for numbers on the row
	{
	    c.drawString (numbers [ar], 4, y);
	    ar++;
	}

	c.setColor (Color.black);

    }
} // MapMaker class





