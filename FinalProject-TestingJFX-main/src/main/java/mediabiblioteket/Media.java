package mediabiblioteket;



public abstract class Media implements Comparable
{
	String mediaType;
	String title;
	String objectID;
	Borrower thisMediaBorrower;
	int year;
	boolean borrowed=false;
	
	
	/*
	 * Sätter ihop mediatypen, Bok, DVD, titlen till median, unika ID till media och åter för median
	 */
	
	public Media(String mediaType, String title, String objectID, int year)
	{
		this.mediaType = mediaType;
		this.title = title;
		this.objectID = objectID;
		this.year = year;
	}

	public String getMediaType()
	{
		return mediaType;
	}

	public void setMediaType(String mediaType)
	{
		this.mediaType = mediaType;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getObjectID()
	{
		return objectID;
	}

	public void setObjectID(String objectID)
	{
		this.objectID = objectID;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public Borrower getThisMediaBorrower()
	{
		return thisMediaBorrower;
	}

	public void setThisMediaBorrower(Borrower thisMediaBorrower)
	{
		this.thisMediaBorrower = thisMediaBorrower;
	}

	public boolean isBorrowed()
	{
		return borrowed;
	}

	public void setBorrowed(boolean borrowed)
	{
		this.borrowed = borrowed;
	}
	
	/**
	 * Abstract, behövs ej implementeras se subklasserna
	 * @return Konkatenerad sträng med all info rörande ett visst Media
	 */
	
	public String listInfo()
	{
		return "";
	}
	

	public int compareTo( Object obj ) 
	{
		
		 if(obj instanceof Media) 
		 {
		 Media media = (Media)obj;
		 
		 if(Integer.parseInt(objectID)==Integer.parseInt(media.getObjectID()))
			 return 0;
		 
		 
		 if(Integer.parseInt(objectID)<Integer.parseInt(media.getObjectID()))
		 	return -1;
		 	
		 if(Integer.parseInt(objectID)>Integer.parseInt(media.getObjectID()))
		 	return 1;
		 }	
		 	
		 return -2;
		
	}
	
	
	public boolean equals( Object obj ) 
	{
		 if(obj instanceof Media) {
		 Media media = (Media)obj;
		 return Integer.parseInt(objectID)==Integer.parseInt(media.getObjectID());
		 }
		 return false;
	}

}
