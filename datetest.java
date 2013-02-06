import java.awt.*;
import hsa.Console;

public class DateTest
{
    static Console c;

    public static void main (String[] args)
    {
	c = new Console ();

	// Constructor
	Date today = new Date(10,28);            // set date to October 28th
	FullDate holiday = new FullDate(12,17,2011);      // set date to Dec 17th, 2011

	// Methods
	today.advance(); // advance today (to 10/29)

	c.println ("Tomorrow is " + today + "\n"); // display today in mm/dd format

	// display 16 holiday days starting from 12/17/2011
	for(int x=1; x<=16; x++)
	{
	    c.println(""+holiday);
	    holiday.advance();
	}



    } // main method
} // DateTest class

public class Date
{
    protected int month, day;

    public Date ()
    {
	month = 1;
	day = 1;
    }


    public Date (int m, int d)
    {
	month = m;
	day = d;
    }


    public void advance ()
    {
	day++;
	if ((day == 29 && month == 2) ||  // end of Feb
		(day == 31 && (month == 4 || month == 6 || month == 9 || month == 11)) ||
		(day == 32))
	{
	    day = 1;
	    month++;
	    if (month == 13)
		month = 1;
	}
    }


    public String toString ()
    {
	String date = "";
	if (month < 10)
	    date += "0";
	date += month + "/";
	if (day < 10)
	    date += "0";
	return date + day;
    }
}

public class FullDate extends Date
{
    protected int year;

    public FullDate ()
    {
	year = 2011;
    }


    public FullDate (int m, int d, int y)
    {
	super(m,d);
	year = y;
    }


    public void advance ()
    {
	super.advance ();
	if (month == 1 && day == 1)
	    year++;
    }


    public String toString ()
    {
	return "" + year + "/" + super.toString ();
    }
}
