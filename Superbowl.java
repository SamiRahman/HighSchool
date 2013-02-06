// The "Superbowl" class.
//Sami Rahman, ICS4U1, Due: Wed Feb 09 2011
//Assignment 1 

import java.awt.*;
import hsa.Console;

public class Superbowl
{
    static Console c;           // The output console

    public static void main (String[] args)
    {
	c = new Console ();

	int yleft = 70, yards = 0, tyards = 0, player = 1, puntyards; //initializing variables
	char choice;
	double decimal, fgprob;
	boolean score = false;

	c.println ("It's overtime in Super Bowl XLV. First score wins. Go get 'em!!");
	c.println ("*Punting is only possible on the last down & if you're behind the 50 yard line.");
	c.println ("*Field goals are only possible after the 50 yard line.");
	c.println ("------------------------------------------------------");

	while (score == false) //keep going till a score (TD or FG)
	{
	    c.println ("Player " + player + ": " + (yleft) + " yards left.");

	    for (int x = 1 ; x <= 3 && yleft > 0 && score == false ; x++) // loop while there are 3 or less downs, yardage to be gained, and no TD/FG
	    {
		if (x == 3 && yleft < 50) //field goal possible: on last down & less than 50 yards to go
		{
		    c.print (x + ". Run/Pass/Field Goal(f): ");
		    choice = c.getChar (); //input
		    c.println (choice); //display choice
		    if (choice == 'f' && yleft > 30) //far field goal (30-50 yds)
		    {
			fgprob = Math.random ();
			if (fgprob <= 0.6) // 60% chance of failure
			{
			    if (player == 1) //changes possession
				player = 2;
			    else if (player == 2)
				player = 1;
			    yleft = 100 - yleft;
			    tyards = 0; //reset yards in drive
			    x = 0; //reset counter
			    c.println ("Aw! You missed the field goal. TURNOVER.");
			    c.println ("Player " + player + ": " + (yleft) + " yards left.");
			}
			else //success
			{
			    score = true;
			    c.println ("You made the field goal! SCORE!! The crowd goes wild!");
			}

		    }
		    else if (choice == 'f' && yleft <= 30 && yleft >= 1) //close field goal (1-30 yards)
		    {
			fgprob = Math.random ();
			if (fgprob <= 0.3) // 30% chance of failure
			{

			    if (player == 1) //changes possession
				player = 2;
			    else if (player == 2)
				player = 1;
			    c.println ("Aw! You missed the field goal. TURNOVER.");
			    yleft = 100 - yleft;
			    tyards = 0; 
			    x = 0;
			    c.println ("Player " + player + ": " + (yleft) + " yards left.");
			}
			else //success
			{
			    c.println ("You made the field goal! SCORE!! The crowd goes wild!");
			    score = true;
			}
		    }
		} //end of field goal process
		else if (x == 3 && yleft > 50) //last down, punt possibility with more than 50 yards to go
		{
		    c.print (x + ". Run/Pass/Punt(k): ");
		    choice = c.getChar ();
		    c.println (choice);
		    if (choice == 'k') //if you choose to punt
		    {
			puntyards = (int) (Math.random () * 30) + 20; //punts between 20-50 yards
			if (player == 1) //changes possession
			    player = 2;
			else if (player == 2)
			    player = 1;
			c.println ("You punted " + puntyards + " yards. Team " + player + "'s ball."); //info
			yleft = 100 - (yleft - puntyards); //yards to go changes for the other team
			tyards = 0; //drive yards become 0
		    }
		}
		else //not in field goal range/possibility & not in punt possibility, so downs 1 & 2:
		{
		    c.print (x + ". Run/Pass: ");
		    choice = c.getChar ();
		    c.println (choice);
		}

		if (choice == 'r') //run choice
		{
		    yards = (int) (Math.random () * 5) + 1;
		    c.print ("You made " + yards + " yards.\n");
		}
		else if (choice == 'p') //pass choice
		{
		    decimal = Math.random ();
		    if (decimal <= 0.4) //success 40% of the time
		    {
			yards = (int) (Math.random () * 10) + 5; //yards gained between 5 - 15
			c.println ("You made " + yards + " yards.");
		    }
		    else //failure 
		    {
			yards = 0; //no yards gained
			c.println ("Incomplete.");
		    }
		}

		tyards = tyards + yards; //total yards accumulated in the current drive

		if (tyards >= 10) //pass first down line in the drive, get first down
		{
		    yleft = yleft - tyards;
		    if (yleft > 0) // not in endzone
			c.println ("First down! Nice work moving the chains. Team " + player + ": " + yleft + " yards left.");
		    else if (yleft <= 0) //in endzone
		    {
			c.println ("Touchdown");
			score = true;
		    }
		    tyards = 0; //yards gained in down attempts reset to 0, starts fresh
		    x = 0; // counter restarts, down 1
		}

		int tdyleft = yleft - tyards; //touchdown yards left, ensures drive doesn't continue if team's already in the endzone
		if (yleft <= 0 || tdyleft <= 0) //reached endzone
		    score = true; //gets out of loop, TD scores
		else if (x == 3 && yleft > 0 && choice != 'k' && choice != 'f') //no more downs left & not in endzone
		{
		    if (player == 1) //changes possession
			player = 2;
		    else if (player == 2)
			player = 1;
		    yleft = 100 - yleft; // change distance
		    tyards = 0; //yards gained in drive reset
		    c.println ("Shucks, TURNOVER.");
		}
	    }
	}
	
	c.println ("\nTOUCHDOWN!! YOU WIN SUPER BOWL XLV! You're the best QB ever!! Congrats Team "+player+"!");
    } // main method
} // Superbowl class
