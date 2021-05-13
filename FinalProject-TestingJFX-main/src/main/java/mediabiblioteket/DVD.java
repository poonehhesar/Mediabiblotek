package mediabiblioteket;
import collections.*;

public class DVD extends Media
{
	LinkedList<String> actors;
	
	/* Mediatypen, titlen till filen DVD, unuka ID kopplar till DVD:n, produktuktionsåret för DVD och en String för samtliga skådespelare */ 
	
	public DVD(String mediaType, String title, String objectID, int year, LinkedList<String> actors)
	{
		super(mediaType, title, objectID, year);
		this.actors = actors;
	}

	public LinkedList<String> getActors()
	{
		return actors;
	}

	public void setActors(LinkedList<String> actors)
	{
		this.actors = actors;
	}

	public String toString()
	{
		String borrowed="";
		if(isBorrowed())
		{
			borrowed += "Borrowed\n";
		}
		else
			borrowed += "Free\n";
					
		return mediaType + " - " + borrowed + " - " + title + " - " + getYear() + " - " + actors.toString();
	}
	/**
	 * Slå ihop alla attribut för en viss DVD i en sträng
	 * @return Konkatenerad sträng med all info rörande en viss DVD
	 */
	public String listInfo()
	{
		String theInfoString = "Title: " + title + " \n";
		
		theInfoString += "Year: " + getYear() + "\n";
		theInfoString += "Actors: " + actors.toString() + "\n";
		
		theInfoString +="Type: " + mediaType + "\n";
		
		if(isBorrowed())
		{
			theInfoString += "Is Borrowed\n";
			theInfoString += "Borrower: " + getThisMediaBorrower().getName() + "\n";
		}
		else
			theInfoString += "Free\n";
		
		theInfoString += "ID: " +  getObjectID() + "\n";
		
		return theInfoString;
	}



}
