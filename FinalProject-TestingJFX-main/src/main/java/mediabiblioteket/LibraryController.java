package mediabiblioteket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import collections.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;


public class LibraryController
{
	Borrower currentBorrower;
	GUI theGUI;
	ArrayList<Media> allMediaObjects;
	ArrayList<String> borrowed;
	ArrayList<Borrower> allBorrowers;

	LinkedList<Media> mediaSearchResults;

	/* Konstruktor set till att samtliga Media, Borrowed och MediaSearchResults i Arraylist samt instanterar GUI fönstret */
	
	LibraryController()
	{
		allMediaObjects = new ArrayList<Media>(24);
		allBorrowers = new ArrayList<Borrower>();
		borrowed = new ArrayList<String>();
		mediaSearchResults = new LinkedList<Media>();
		boot();
	}
	
	/* Samma som ovan */
	 
	LibraryController(GUI parGUI)
	{
		theGUI = parGUI;
		allMediaObjects = new ArrayList<Media>(25);
		allBorrowers = new ArrayList<Borrower>();
		borrowed = new ArrayList<String>();
		mediaSearchResults = new LinkedList<Media>();

		boot();
	}
	

	/* Kontrollerar om användaren matar in data i felaktig format och meddelar om detta, strängen som tas emot och om Dataformatet är korrekt annars False */
	
	public boolean checkUserInput(String inputString)
	{
		String regex = ".*\\S.*";
		try
		{
			if(inputString.matches(regex)==false)
			{
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	/* Strängen får enbart innehålla siffror, används till Media-ObjectID */
	
	boolean checkInputOnlyDigits(String inputString)
	{
		
		/* Regex tillåter enbart heltal */
		
		String regex = "^-?\\d*?\\d+$";
		if(inputString==null)
		{
			
			 Alert a1 = new Alert(AlertType.ERROR,
					 "Incorrect characters only 0-9 are allowed",ButtonType.OK); 
			 a1.setTitle("Error");
				 a1.show();
			return false;
		}
		else if(!inputString.matches(regex))
		{
			 Alert a1 = new Alert(AlertType.ERROR,
					 "Incorrect characters only 0-9 are allowed",ButtonType.OK); 
			 a1.setTitle("Error");
				 a1.show();
			return false;
		}
		try
		{
			if(Integer.parseInt(inputString)<Integer.MIN_VALUE)
			{
				Alert a1 = new Alert(AlertType.ERROR,
				"The number is too low, min is " + Integer.MIN_VALUE,ButtonType.OK); 
				 a1.setTitle("Error");
					 a1.show();
				return false;
			}
		}
		catch(Exception e)
		{
			
			Alert a1 = new Alert(AlertType.ERROR,
					 "The number is too high or low, only this is allowed: " + Integer.MIN_VALUE + " - " +  Integer.MAX_VALUE + Integer.MIN_VALUE,ButtonType.OK); 
			a1.setTitle("Error");
				 a1.show();
			return false;
		}	
		
		return true;
	}


	/* Skapar en fil och skriver innehållet i Content filen, inehållet skrivs över till filen och Sökväg till filen */
	 
	public void writeToFile()
	{
		System.out.println("writeToFile");
		
		try
		{
			PrintWriter theOutPutf = new PrintWriter(new FileOutputStream(new File("files/Utlanade.txt")));
			
			Iterator<String> iter = borrowed.iterator();
			
			while(iter.hasNext())
			{
				theOutPutf.println(iter.next());
				theOutPutf.flush();
			}
				
			theOutPutf.close();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.err.println();
		}

	}
	
	/* Lånar media */
	
	public void borrowMedia(Media theMedia)
	{
		System.out.println("borrowMedia");
		
		borrowed.add(currentBorrower.getSsn()+";"+theMedia.objectID);
		
		writeToFile();
			
		
		theMedia.setBorrowed(true);
		theMedia.setThisMediaBorrower(currentBorrower);
	}
	
	/* Lämnar tillbaka Media och Media objekt */
	
	
	public void returnMedia(Media theMedia)
	{
		borrowed.remove(borrowed.indexOf(0, currentBorrower.getSsn()+";"+theMedia.getObjectID()));
		writeToFile();
		theMedia.setBorrowed(false);
		theMedia.setThisMediaBorrower(null);
	}
	

	/* Kollar om en Låntagare existerar, Låntagarens ID och om den existerar */
	
	public boolean checkIfBorrowerExist(String borrowerID)
	{
		Iterator theIterator = allBorrowers.iterator();

		while (theIterator.hasNext())
		{
			Borrower tempBorrower = (Borrower) theIterator.next();

			if (tempBorrower.getSsn().equals(borrowerID))
			{
				currentBorrower = tempBorrower;
				return true;
			}

		}

		return false;
	}

	/* Sorteting av Media */
	
	public void sortMedia()
	{
		

		for (int i = 0; i < allMediaObjects.size() - 1; i++)
		{
			for (int j = allMediaObjects.size() - 1; j > i; j--)
			{
				if (allMediaObjects.get(j).compareTo(allMediaObjects.get(j-1))<0)
				{
					Media temp = allMediaObjects.get(j);

					allMediaObjects.set(j, allMediaObjects.get(j-1));
					allMediaObjects.set(j - 1, temp);
				}
			}
			
		}
	}
	
	
	/* Hämta Media objekt med ObjectID genom att använda binär sökning */
	
	public Media getMedia(String ID)
	{

        int min = 0, max = allMediaObjects.size() - 1, pos;
        
        int intID = Integer.parseInt(ID);
        
        
        while( min <= max ) 
        {
        	pos = (min + max) / 2;
            if( intID == Integer.parseInt(allMediaObjects.get(pos).objectID))
            {
                return allMediaObjects.get(pos);
            }
            else if( intID < Integer.parseInt(allMediaObjects.get(pos).objectID))
            {
                max = pos - 1;
            }
            else
                min = pos + 1;
        }
       

		return null;
	}
	
	/* Informatiom om ett visst Media, tedt på Media objekt samt detaljerad information */
	
	public void showSelectedMediaInfo(String theString)
	{
		
		Iterator<Media> mediaIterator = mediaSearchResults.iterator();

		while (mediaIterator.hasNext())
		{
			Media tempMedia = mediaIterator.next();
			
			if (tempMedia.toString().equals(theString))
			{
				System.out.println(tempMedia.toString());
				//JOptionPane.showMessageDialog(null, tempMedia.listInfo());
				 Alert a1 = new Alert(AlertType.NONE,
						tempMedia.listInfo(),ButtonType.CLOSE); 
			a1.setTitle("Info");
			
				 a1.show();
				mediaSearchResults.restartList();
				break;
			}

		}

	}
	
	/* Sök Media genom att skriva in valfritt sträng, jämför flera olika attribut */
	
	public void searchMediaAllByString(String theSearchString)
	{
		//mediaSearchResults = new LinkedList<Media>();
		Iterator<Media> mediaIterator = allMediaObjects.iterator();
		
		
		theSearchString=theSearchString.toLowerCase();

		while (mediaIterator.hasNext())
		{
			Media tempSearch = mediaIterator.next();

			if (tempSearch.getTitle().toLowerCase().contains(theSearchString))
			{
				mediaSearchResults.add(tempSearch);
				theGUI.setTheTextArea(tempSearch.toString());
			}
			else if (tempSearch.getObjectID().equals(theSearchString))
			{
				mediaSearchResults.add(tempSearch);
				theGUI.setTheTextArea(tempSearch.toString());
			}
			else if(tempSearch.mediaType.toLowerCase().equals(theSearchString))
			{
				mediaSearchResults.add(tempSearch);
				theGUI.setTheTextArea(tempSearch.toString());
			}
			else if(tempSearch.mediaType.equals("DVD"))
			{
				DVD tempSearchDVD = (DVD) tempSearch;
				if(tempSearchDVD.getActors().toString().toLowerCase().contains(theSearchString))
				{
					mediaSearchResults.add(tempSearch);
					theGUI.setTheTextArea(tempSearch.toString());
				}
			}
			else if(tempSearch.mediaType.equals("Bok"))
			{
				Book tempSearchDVD = (Book) tempSearch;
				if(tempSearchDVD.getAuthor().toString().toLowerCase().contains(theSearchString))
				{
					mediaSearchResults.add(tempSearch);
					theGUI.setTheTextArea(tempSearch.toString());
				}
			}
		}
		
	}
	
	/* Returnera vald Media från sökresultat */
	
	public Media getMediaFromSearchResult(String theString)
	{
		Iterator<Media> mediaIterator = mediaSearchResults.iterator();

		while (mediaIterator.hasNext())
		{
			Media tempMedia = mediaIterator.next();

			if (tempMedia.toString().equals(theString))
			{
				mediaSearchResults.restartList();
				return tempMedia;
			}
		}
		mediaSearchResults.restartList();
		
		return null;
	}
	
	/* Sök Media-titel genom att skriva in en sträng, text och sökta Media */
	
	public void searchMediaTitleByString(String theSearchString)
	{
		mediaSearchResults = new LinkedList<Media>();
		Iterator<Media> mediaIterator = allMediaObjects.iterator();
		
		while (mediaIterator.hasNext())
		{
			Media tempSearch = mediaIterator.next();

			if (tempSearch.getTitle().toLowerCase().contains(theSearchString.toLowerCase()))
			{
				mediaSearchResults.add(tempSearch);
				theGUI.setTheTextArea(tempSearch.toString());
			}

		}
	}

	/* Olika textfilerna i systemet */

	private void boot()
	{
		if (loadFileBorrowers() == false)
		{
			JOptionPane.showMessageDialog(null, "files/Lantagare.txt not found");
		}
		if (loadFileMedia() == false)
		{
			JOptionPane.showMessageDialog(null, "files/Media.txt not found");
		}
		if(loadBorrowedMedia() == false)
		{
			JOptionPane.showMessageDialog(null, "files/Utlanade.txt not found");
		}
	}
	
	/* Returnera låntagare, SSN (personummer) och Låntagare */
	
	public Borrower getBorrower(String Ssn)
	{
		Iterator<Borrower> iter = allBorrowers.iterator();
		
		while(iter.hasNext())
		{
			Borrower temp = iter.next();
			
			if(temp.getSsn().equals(Ssn))
				return temp;
			
		}
		
		return null;
	}
	
	/* Läser in hela utlånade Media objekt */
	
	public boolean loadBorrowedMedia()
	{
		try
		{
			Scanner theScanner = new Scanner(new File("files/Utlanade.txt"));
			StringTokenizer theTokenizer;

			while (theScanner.hasNext())
			{
				String theLine = theScanner.nextLine();
				theTokenizer = new StringTokenizer(theLine, ";");

				String borrower = theTokenizer.nextToken();
				String theMedia = theTokenizer.nextToken();
				
				Media temp = getMedia(theMedia);
				Borrower tempBorrower = getBorrower(borrower);
				
				temp.setBorrowed(true);
				
				temp.setThisMediaBorrower(tempBorrower);
				
				borrowed.add(borrower +";"+theMedia);
			}
			
			
			theScanner.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* Läs in alla Låntagare */
	
	private boolean loadFileBorrowers()
	{
		try
		{

			Scanner theScanner = new Scanner(new File("files/Lantagare.txt"));
			StringTokenizer theTokenizer;

			while (theScanner.hasNext())
			{
				String theLine = theScanner.nextLine();
				theTokenizer = new StringTokenizer(theLine, ";");

				String ssn = theTokenizer.nextToken();
				String name = theTokenizer.nextToken();
				String phoneNbr = theTokenizer.nextToken();

				allBorrowers.add(new Borrower(name, ssn, phoneNbr));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/* Läser in Media.text filen */
	
	private boolean loadFileMedia()
	{

		try
		{
			StringTokenizer theTokenizer;
			Scanner theScanner = new Scanner(new File("files/Media.txt"));

			while (theScanner.hasNext())
			{

				String theLine = theScanner.nextLine();
				theTokenizer = new StringTokenizer(theLine, ";");
				String mediaFormat = theTokenizer.nextToken();

				if (mediaFormat.equals("Bok"))
				{
					String objectID = theTokenizer.nextToken();
					String author = theTokenizer.nextToken();
					String title = theTokenizer.nextToken();
					String year = theTokenizer.nextToken();

					allMediaObjects.add(new Book("Bok", title, objectID, Integer.parseInt(year), author));
				} else
				{
					String objectID = theTokenizer.nextToken();
					String title = theTokenizer.nextToken();
					String year = theTokenizer.nextToken();
					LinkedList<String> theActorList = new LinkedList<String>();
					while (theTokenizer.hasMoreTokens())
					{
						theActorList.add(theTokenizer.nextToken());
					}

					allMediaObjects.add(new DVD("DVD", title, objectID, Integer.parseInt(year), theActorList));

				}

			}
			theScanner.close();
			
			sortMedia();

		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/*
	 * Sök efter utlånade Media objekt
	 */
	public void searchBorrowed()
	{
		Iterator iter = borrowed.iterator();
		StringTokenizer theTokenizer;
		
		while(iter.hasNext())
		{
			theTokenizer = new StringTokenizer((String)iter.next(), ";");
			
			String Ssn = theTokenizer.nextToken();
			String ID = theTokenizer.nextToken();
			
			if(currentBorrower.getSsn().equals(Ssn))
			{
				Media theMedia = getMedia(ID);
				mediaSearchResults.add(theMedia);
				theGUI.setTheTextArea(theMedia.toString());
			}
		}
		
		
		
	}

	

}
