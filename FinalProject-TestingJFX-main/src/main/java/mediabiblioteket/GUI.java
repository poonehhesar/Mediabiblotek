package mediabiblioteket;

import javax.swing.DefaultListModel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/* UI fönstret i Bibliotekssystemet som användaren inegrerar med för att söka efter olika nediatyper och låna något specefikt */

public class GUI extends Application {
	/**
	 * Serial number
	 */

	static Scene mainScene;
	static Scene logInScene;
	static Stage PS;
	static boolean searchPage = true;
	
	
	private static final long serialVersionUID = 7991560170685136013L;

	VBox theMainPanel;
	GridPane root = new GridPane();
	BorderPane theNorthPanel;
	GridPane theNorthButtonPanel;

	GridPane theCenterPanel;
	BorderPane theSouthPanel;
	Button borrowButton;
	Button infoButton;

	BorderPane theWestPanel;

	TextField theSearchField;
	Label mainMenu;
	Button searchButton;
	Button searchBorrowedButton;

	TextArea theTextArea;
	DefaultListModel<String> theTextAreaModel = new DefaultListModel<String>();
	ListView<String> theTextAreaList;
	ScrollPane theScroller;

	// Radio buttons
	private GridPane theRadioButtonsLabel = new GridPane();

	private RadioButton radioAll = new RadioButton("All");
	private RadioButton radioTitle = new RadioButton("Title");
	private RadioButton radioID = new RadioButton("ID");

	private ToggleGroup radioToggleGroup = new ToggleGroup();

	LibraryController theController = new LibraryController(this);

	/* Konstruktorn som skapar hela layouten för inloggningsrutan samt huvudfönstret */

	public Scene loginScene() {
		PS.setTitle("Login");
		Button button = new Button("LOGIN");
		VBox vBox = new VBox();
		button.setId("LoginBTN");
		vBox.setPadding(new Insets(10, 10, 10, 10));
		TextField textField = new TextField();
		

		textField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				button.fire();
				ev.consume();
			}
		});

		textField.setPromptText("YYMMDD-XXXX");
		textField.setId("LoginTF");

		Label info = new Label();
		vBox.getChildren().addAll(new Label("Enter SSN(Social Security Number)"), textField, info, button);
		logInScene = new Scene(vBox, 400, 300); /* 400, 100 */

		button.setOnAction(actionEvent -> {
			if (login(textField.getText())) {
				mediaScene();
			} else {
				info.setText("Incorrect SSN");
				info.setTextFill(Color.RED);
			}

		});

		return logInScene;

	}

	public void mediaScene() {

		PS.setTitle("Mediabibliotek");
		root = new GridPane();


		// The Main Panel
		theMainPanel = new VBox(10);
		theMainPanel.setPrefSize(600, 900);
		// The North Panel
		theNorthPanel = new BorderPane();
		theNorthButtonPanel = new GridPane();

		// The South Panel
		theSouthPanel = new BorderPane();
		borrowButton = new Button("Borrow");
		borrowButton.setId("BorrowBTN");
		
		
		borrowButton.setOnAction(e -> {
			if (borrowButton.getText().equals("Borrow")) {
				String selectedText = (String) theTextAreaList.getSelectionModel().getSelectedItem();

				Media selectedMedia = theController.getMediaFromSearchResult(selectedText);

				if (selectedText != null && selectedMedia != null) {
					if (selectedMedia.isBorrowed()) {
						Alert a1 = new Alert(AlertType.NONE, "Cannot borrow, already borrowed", ButtonType.OK); 
						a1.setTitle("Alert");
						a1.show();
					} else {
						theController.borrowMedia(selectedMedia);
						theTextAreaModel.set(theTextAreaList.getSelectionModel().getSelectedIndex(),
								selectedMedia.toString());
						setTextList();
					}
				} else {
					System.out.println("Its null");
				}
			} else {
				String selectedText = (String) theTextAreaList.getSelectionModel().getSelectedItem();
				Media selectedMedia = theController.getMediaFromSearchResult(selectedText);

				if (selectedText != null && selectedMedia != null) {
					if (selectedMedia.isBorrowed() == false) {
						Alert a1 = new Alert(AlertType.NONE, "Cannot returm, already returned", ButtonType.OK);
						a1.setTitle("Alert");
						a1.show();
					} else {
						theController.returnMedia(selectedMedia);
						theTextAreaModel.set(theTextAreaList.getSelectionModel().getSelectedIndex(),
								selectedMedia.toString());
						setTextList();
					}
				}

			}
		});

		infoButton = new Button("Info");
		infoButton.setId("InfoBTN");
		
		infoButton.setOnAction(e -> {
			String selectedText = (String) theTextAreaList.getSelectionModel().getSelectedItem();
			System.out.println(selectedText);
			if (selectedText != null)
				theController.showSelectedMediaInfo(selectedText);
		});

		theSouthPanel.setLeft(borrowButton);
		theSouthPanel.setRight(infoButton);

		theTextAreaList = new ListView<String>();

		theTextAreaList.setCellFactory(param -> new ListCell<String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
					setStyle(null);
				} else {
					setText(item);
					
					if (item.contains("- Borrowed")) {
						if (searchPage) setStyle("-fx-background-color: rgb(50,202,240);");
						else setStyle("-fx-background-color: rgb(152,251,152);");
					} else {
						if (searchPage) setStyle("-fx-background-color: rgb(152,251,152);");
						else setStyle("-fx-background-color: rgb(135,206,235);");
					}
				}
			}
		});

		theTextAreaList.setPrefSize(580, 900);
		theTextAreaList.setId("TheTextAreaList");
		theTextAreaList.minHeight(2000);
		
		theScroller = new ScrollPane(theTextAreaList);
		theScroller.setId("TheScroller");
		theScroller.setPrefSize(800, 800); /* 500, 800 */
		
		theRadioButtonsLabel = new GridPane();

		radioAll = new RadioButton("All");
		radioAll.setSelected(true);
		radioTitle = new RadioButton("Title");
		radioID = new RadioButton("ID");

		radioToggleGroup = new ToggleGroup();

		radioAll.setToggleGroup(radioToggleGroup);
		radioAll.setId("RadioAll");
		radioTitle.setToggleGroup(radioToggleGroup);
		radioTitle.setId("RadioTitle");
		radioID.setToggleGroup(radioToggleGroup);
		radioID.setId("RadioID");

		theRadioButtonsLabel.add(radioAll, 0, 1);
		theRadioButtonsLabel.add(radioTitle, 0, 2);
		theRadioButtonsLabel.add(radioID, 0, 3);

		searchButton = new Button("Search");
		searchButton.setId("SearchBTN");
		
		
		searchButton.setOnAction(e -> {
			searchPage = true;
			String theInput = theSearchField.getText();
			if (theController.checkUserInput(theInput)) {
				borrowButton.setText("Borrow");
				clearTheTextArea();
				if (radioID.isSelected()) {
					if (theController.checkInputOnlyDigits(theInput)) {
						Media temp = theController.getMedia(theInput);
						theController.mediaSearchResults.add(temp);
						if (temp != null)
							setTheTextArea(temp.toString());
					}
				} else if (radioTitle.isSelected()) {
					theController.searchMediaTitleByString(theInput);
				} else if (radioAll.isSelected()) {
					theController.searchMediaAllByString(theInput);
				}
			} else {
				Alert a1 = new Alert(AlertType.NONE, "The textbox contains incorrect characters", ButtonType.OK);
				a1.setTitle("Alert");
				a1.show();
			}
		});

		searchBorrowedButton = new Button("Borrowed");
		searchBorrowedButton.setId("BorrowedBTN");
		
		
		searchBorrowedButton.setOnAction(e -> {
			searchPage = false;
			clearTheTextArea();
			theController.searchBorrowed();
			borrowButton.setText("Return");
		});
		theNorthButtonPanel.add(searchButton, 0, 1);
		theNorthButtonPanel.add(searchBorrowedButton, 0, 2);

		theSearchField = new TextField();
		theSearchField.setId("SearchField");
		theSearchField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				searchButton.fire();
				ev.consume();
			}
		});
		mainMenu = new Label("Welcome to the Media Library!");

		theNorthPanel.setTop(mainMenu);
		theNorthPanel.setCenter(theSearchField);
		theNorthPanel.setRight(theNorthButtonPanel);
		theNorthPanel.setBottom(theRadioButtonsLabel);

		theMainPanel.getChildren().add(theNorthPanel);
		theMainPanel.getChildren().add(theTextAreaList);
		theMainPanel.getChildren().add(theSouthPanel);
		root.getChildren().add(theMainPanel);
		mainScene = new Scene(root);
		PS.setScene(mainScene);
		
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = bounds.getMinX() + (bounds.getWidth() - mainScene.getWidth()) * 1.0f / 2;
		double y = bounds.getMinY() + (bounds.getHeight() - mainScene.getHeight()) * 1.0f / 3;
		PS.setX(x);
		PS.setY(y);
	}

	public Stage getPS() {
		return PS;
	}

	/* Kollar vid inloggningen ifall om en person finns registrerad i Bibliotekssystemet, Peronnummer för låntager */

	public boolean login(String userName) {
		if (theController.checkUserInput(userName)) {
			System.out.println("CheckUserInput passed true");

			if (theController.checkIfBorrowerExist(userName)) {
				return true;
			}
		}
		return false;

	}

	/* Rensar Textarean från resultat */
	
	public void clearTheTextArea() {
		theTextAreaModel.clear();

		theTextAreaList.getItems().clear();
	}

	/* Sätter text till Textarean */
	
	public void setTheTextArea(String addText) {
		theTextAreaModel.addElement(addText);
		theTextAreaList.getItems().clear();
		for (int x = 0; x < theTextAreaModel.size(); x++) {
			theTextAreaList.getItems().add(theTextAreaModel.getElementAt(x));
		}
	}

	public void setTextList() {
		theTextAreaList.getItems().clear();
		for (int x = 0; x < theTextAreaModel.size(); x++) {
			theTextAreaList.getItems().add(theTextAreaModel.getElementAt(x));
		}
	}

	/* Programstarten som öppnar applikationen */
	
	public static void main(String[] arguments) {
		launch(arguments);
	}

	public void GUIStart() {

		PS = new Stage();
		PS.setTitle("Mediabibliotek");
		PS.setScene(loginScene());
		PS.show();
		
		
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = bounds.getMinX() + (bounds.getWidth() - logInScene.getWidth()) * 1.0f / 2;
		double y = bounds.getMinY() + (bounds.getHeight() - logInScene.getHeight()) * 1.0f / 3;
		PS.setX(x);
		PS.setY(y);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		GUIStart();
	}

}
