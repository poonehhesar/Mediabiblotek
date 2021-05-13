package TestPack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javafx.application.Platform;
import mediabiblioteket.GUI;
import net.sourceforge.marathon.javadriver.JavaDriver;
import net.sourceforge.marathon.javadriver.JavaProfile;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchMode;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchType;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Testing the Mediabibliotek")
public class LibraryTest {
	

	private static WebDriver driver;
	
	static GUI PS;
	int borrowIndex = 5;

	@BeforeAll
	public static void setup() {
		System.setErr(null);
		Platform.setImplicitExit(false);
		Platform.startup(() -> {
			PS = new GUI();
			PS.GUIStart();
		});

		JavaProfile profile = new JavaProfile(LaunchMode.EMBEDDED);
		profile.setLaunchType(LaunchType.FX_APPLICATION);
		driver = new JavaDriver(profile);

		
		// Makes the app run its self !!
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@BeforeEach
	public void prep() {
		if (PS != null) {
			if (!PS.getPS().isShowing()) {
				Platform.runLater(() -> PS.GUIStart());
			}
		}
	}

	@AfterEach
	public void teardown() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				PS.getPS().close();
			}
		});
	}

	@AfterAll
	public static void wrapItUp() {
		if (driver != null)
			driver.quit();
		Platform.setImplicitExit(true);
		Platform.exit();
	}

	@Test
	@DisplayName("1) Checks if an item is free to borrow, then borrows it")
	@Order(1)
	public void BorrowItem() throws Exception {

		login_and_search("891216-1111", "a", true, true, true, true);

		assertTrue(driver.findElement(By.cssSelector("list-view::nth-item(" + borrowIndex + ")")).getAttribute("value")
				.contains("- Free"), "Is the 5th item on the list free for borrowing?");

		driver.findElement(By.cssSelector("list-view::nth-item(" + borrowIndex + ")")).click();
		driver.findElement(By.id("BorrowBTN")).click();
	}

	@Test
	@DisplayName("2) Checks if the borrowed item from the first user is unavailable for other users")
	@Order(2)
	public void CheckAvailability() {

		login_and_search("361025-2222", "a", true, true, true, true);

		assertTrue(driver.findElement(By.cssSelector("list-view::nth-item(" + borrowIndex + ")")).getAttribute("value")
				.toString().contains("- Borrowed"), "Is the 5th item on the list borrowed already?");
		
		driver.findElement(By.cssSelector("list-view::nth-item(" + borrowIndex + ")")).click();
		driver.findElement(By.id("BorrowBTN")).click();
		driver.switchTo().window("Alert");
		driver.findElement(By.tagName("button[text = 'OK']")).click();
	}

	@Test
	@DisplayName("3) Checks if the item will be available after returning it")
	@Order(3)
	public void returnItem() {

		login_and_search("891216-1111", "a", true, true, true, true);

		String borrowedElement = driver.findElement(By.cssSelector("list-view::nth-item(" + borrowIndex + ")"))
				.getAttribute("value").toString();
		driver.findElement(By.id("BorrowedBTN")).click();
		driver.findElement(By.tagName("list-view")).sendKeys(Keys.HOME);
		List<WebElement> TheTextAreaList = driver.findElements(By.cssSelector("list-view::all-items"));
		int x = 0;
		for (; x < TheTextAreaList.size(); x++) {
			if (TheTextAreaList.get(x).getAttribute("value").toString().equals(borrowedElement)) {
				++x;
				break;
			}
		}
		driver.findElement(By.cssSelector("list-view::nth-item(" + x + ")")).click();
		driver.findElement(By.id("BorrowBTN")).click();
		driver.findElement(By.id("SearchBTN")).click();
		driver.findElement(By.tagName("list-view")).sendKeys(Keys.HOME);

		assertTrue(
				driver.findElement(By.cssSelector("list-view::nth-item(" + borrowIndex + ")")).getAttribute("value")
						.toString().contains("- Free"),
				"Is the 5th item on the list available after returning it?");
	}

	@Test
	@DisplayName("Check if charcters Å, Ä and Ö can be searched")
	public void testÅÄÖSearch() {

		login_and_search("891216-1111", "å", true, false, true, true);

		assertTrue((driver.findElements(By.cssSelector("list-view::all-items"))).size() > 0,
				"Can we get any results for searching Å?");

		login_and_search("", "ä", false, false, true, true);

		assertTrue((driver.findElements(By.cssSelector("list-view::all-items"))).size() > 0,
				"Can we get any results for searching Ä?");

		login_and_search("", "ö", false, false, true, true);

		assertTrue((driver.findElements(By.cssSelector("list-view::all-items"))).size() > 0,
				"Can we get any results for searching Ö?");
	}

	@Test
	@DisplayName("Check if the 'Enter' Button works for both login and search")
	public void testEnterBtn() {
		driver.switchTo().window("Login");
		driver.findElement(By.id("LoginTF")).sendKeys("891216-1111");
		driver.findElement(By.id("LoginTF")).sendKeys(Keys.ENTER);
		driver.switchTo().window("Mediabibliotek");
		driver.findElement(By.id("SearchField")).sendKeys("a" + Keys.ENTER);
		driver.findElement(By.tagName("list-view")).sendKeys(Keys.HOME);

		assertTrue((driver.findElements(By.cssSelector("list-view::all-items"))).size() > 0,
				"Can we get any results for searching with 'Enter'?");

	}

	@Test
	@DisplayName("Check if all borrowed items are red")
	public void testingBorrowedColor() {

		login_and_search("891216-1111", "a", true, false, true, true);

		List<WebElement> allItems = driver.findElements(By.cssSelector("list-view::all-items"));
		
		assertEquals(27, allItems.size(), "Check if we are able to get the whole list");

		allItems.stream().filter(e -> {
			return e.getAttribute("value").contains("- Borrowed");
		}).map(e -> {
			return e.getAttribute("style");
		}).forEach(e -> {
			assertEquals("-fx-background-color: rgb(255,153,153);", e, "Are the colors of all borrowed items red?");
		});

	}

	@Test
	@DisplayName("Check if all free items are green")
	public void testingFreeColor() {

		login_and_search("891216-1111", "a", true, false, true, true);

		List<WebElement> allItems = driver.findElements(By.cssSelector("list-view::all-items"));

		assertEquals(27, allItems.size(), "Check if we are able to get the whole list");

		allItems.stream().filter(e -> {
			return e.getAttribute("value").contains("- Free");
		}).map(e -> {
			return e.getAttribute("style");
		}).forEach(e -> {
			assertEquals("-fx-background-color: rgb(152,251,152);", e, "Are the colors of all free items green?");
		});
	}

	@Nested
	@DisplayName("Checks the color of items in users borrowed list")
	class testingBorrowed{
		
		String borrowedElement;
		
		@Test
		@DisplayName("Check if the borrowed item in the users borrowed list is green")
		@Order(1)
		public void testingBorrowedColor() {
			login_and_search("891216-1111", "a", true, true, true, true);

			assertTrue(driver.findElement(By.cssSelector("list-view::nth-item(" + borrowIndex + ")")).getAttribute("value")
					.contains("- Free"), "Is the 5th item on the list free for borrowing?");
			
			borrowedElement = driver.findElement(By.cssSelector("list-view::nth-item(" + borrowIndex + ")"))
					.getAttribute("value").toString();
			driver.findElement(By.cssSelector("list-view::nth-item(" + borrowIndex + ")")).click();
			driver.findElement(By.id("BorrowBTN")).click();
			driver.findElement(By.id("BorrowedBTN")).click();
			driver.findElement(By.tagName("list-view")).sendKeys(Keys.HOME);
			List<WebElement> TheTextAreaList = driver.findElements(By.cssSelector("list-view::all-items"));
			int x = 0;
			for (; x < TheTextAreaList.size(); x++) {
				if (TheTextAreaList.get(x).getAttribute("value").toString().equals(borrowedElement)) {
					++x;
					break;
				}
			}
			
			assertEquals("-fx-background-color: rgb(152,251,152);", driver.findElement(By.cssSelector("list-view::nth-item(" + x + ")")).getAttribute("style"), "Is the borrowed item in users borrowed list, green?");
		}
		@Test
		@DisplayName("Check if the returned item in the users borrowed list is blue")
		@Order(2)
		public void testingReturningColor() {
			
			login_and_search("891216-1111", "a", true, true, true, false);
			
			driver.findElement(By.id("BorrowedBTN")).click();
			driver.findElement(By.tagName("list-view")).sendKeys(Keys.HOME);
			List<WebElement> TheTextAreaList = driver.findElements(By.cssSelector("list-view::all-items"));
			int x = 0;
			for (; x < TheTextAreaList.size(); x++) {
				if (TheTextAreaList.get(x).getAttribute("value").toString().equals(borrowedElement)) {
					++x;
					break;
				}
			}
			driver.findElement(By.cssSelector("list-view::nth-item(" + x + ")")).click();
			driver.findElement(By.id("BorrowBTN")).click();
			assertEquals("-fx-background-color: rgb(135,206,235);", driver.findElement(By.cssSelector("list-view::nth-item(" + x + ")")).getAttribute("style"), "Is the returned item in users borrowed list, blue?");
			}
	}

	public void login_and_search(String SSN, String keyWord, boolean login, boolean titleSearch, boolean search,
			boolean focusOnList) {
		if (login) {
			driver.switchTo().window("Login");
			driver.findElement(By.id("LoginTF")).sendKeys(SSN);
			driver.findElement(By.id("LoginBTN")).click();
			driver.switchTo().window("Mediabibliotek");
			}
		if (search) {
			driver.findElement(By.id("SearchField")).sendKeys(keyWord);
			if (titleSearch)
				driver.findElement(By.id("RadioTitle")).click();
			driver.findElement(By.id("SearchBTN")).click();
			}
		if (focusOnList) {
			driver.findElement(By.tagName("list-view")).sendKeys(Keys.HOME);
			}
		}
	}
