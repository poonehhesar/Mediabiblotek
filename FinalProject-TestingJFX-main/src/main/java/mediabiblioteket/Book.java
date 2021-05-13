package mediabiblioteket;

public class Book extends Media
{
	String Author;

	/* Mediatypen, titlen till boken, Unika ID kopplad till boken, året på boken samt författaren till boken */
	
	public Book(String mediaType, String title, String objectID, int year, String author)
	{
		super(mediaType, title, objectID, year);
		Author = author;
	}

	public String getAuthor()
	{
		return Author;
	}

	public void setAuthor(String author)
	{
		Author = author;
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
					
		return mediaType + " - " + borrowed + " - " + title + " - " + getYear() + " - " + Author;
	}
	
	/* Slå ihop attribut för en viss Bok i en stränge */
	
	public String listInfo()
	{
		String theInfoString = "Title: " + title + " \n";
		
		theInfoString += "Year: " + getYear() + "\n";
		theInfoString += "Author: " + Author + "\n";
		theInfoString +="Type: " + mediaType + "\n";
		
		if(isBorrowed())
		{
			theInfoString += "Taken\n";
			theInfoString += "Borrower: " + getThisMediaBorrower().getName() + "\n";
		}
		else
			theInfoString += "Free\n";
		
		theInfoString += "ID: " +  getObjectID() + "\n";
		
		return theInfoString;
	}
	
}
