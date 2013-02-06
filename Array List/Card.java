import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import hsa.Console;

class Card
{
    private int rank; // from 1 to 13
    private int suit; //from 1 to 4 (1 = Spades, 2 = Hearts, 3 = Diamonds, 4 = Clubs
    private boolean faceup = true;
    public Image cardImage; //Card's image
    private int cardcounter = 0;

    public Card ()  //Default card, ace of spades
    {
	rank = 1;
	suit = 1;
	setImage ();
    }


    public Card (int isuit, int irank)  //creating card when specifying suit and rank
    {
	rank = irank;
	suit = isuit;
	setImage ();
    }


    public Card (int cardnumber)  //from 1 to 52
    {
	rank = (cardnumber - 1) % 13 + 1; //calculates rank
	suit = (cardnumber - 1) / 13 + 1; //calculates suit
	setImage ();
    }


    public void setImage ()  //sets image for card
    {
	if (faceup == false)
	    cardImage = loadImage ("CardImages\\b1fv.png");
	else if (faceup)
	    cardImage = loadImage ("CardImages\\" + ((suit - 1) * 13 + rank) + ".png");
    }


    public int cardnumber ()  //returns card number
    {
	return ((suit - 1) * 13 + rank);
    }


    public static Image loadImage (String name)  //loads a single image
    {
	Image img = null;
	try
	{
	    img = ImageIO.read (new File (name)); // tries to read the file
	}
	catch (IOException e)  // if the file is not found
	{
	}
	return img;
    }


    public void show (Graphics g, int x, int y)  //draws card with specify-able coordinates
    {
	g.drawImage (cardImage, x, y, null);
    }


    public int returnsuit ()  //returns suit
    {
	return (suit);
    }


    public int returnrank ()  //returns rank
    {
	return (rank);
    }


    public boolean flipped ()//checks if card is flipped
    {
	if (faceup)
	    return (true);
	else
	    return (false);
    }


    public void flip ()  //flips card
    {
	if (faceup == true)
	    faceup = false;
	else if (faceup == false)
	    faceup = true;
	setImage (); //resets image to show back of card
    }
}
