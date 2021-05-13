package mediabiblioteket;


/* Borrower klassen med låntagaren i Bibliotekssystemet såsom namn, personnummer och telefonnr */

public class Borrower
{
	private String name = "";
	private String ssn;
	private String phoneNumber;

	/* Låntagarens namn, peronnummer och Låntagarens telefonnummer */
	
	public Borrower(String name, String ssn, String phoneNumber)
	{
		this.name = name;
		this.ssn = ssn;
		this.phoneNumber = phoneNumber;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}

	public void setphoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getSsn()
	{
		return ssn;
	}

	public String getName()
	{
		return name;
	}

	public String getphoneNumber()
	{
		return phoneNumber;
	}

}
