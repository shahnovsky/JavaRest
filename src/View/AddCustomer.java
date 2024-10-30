package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Model.Customer;
import Model.Restaurant;
import Utils.Gender;
import Utils.Neighberhood;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddCustomer implements Initializable {
	
	@FXML
	private TextField firstName;

	@FXML
	private DatePicker birthDay;
	
	@FXML
	private RadioButton rbCustomer;

	@FXML
	private ToggleGroup brGroup;

	@FXML
	private RadioButton rbCook;

	@FXML
	private RadioButton rbDeliveryPerson;

	@FXML
	private TextField lastName;

	@FXML
	private ToggleGroup gender;

	@FXML
	private ComboBox<Neighberhood> neibList;
	
	@FXML
	private CheckBox glutenSens;

	@FXML
	private CheckBox lactoseSens;

	@FXML
	private RadioButton male;

	@FXML
	private RadioButton female;
	
    @FXML
    private Button create;
    @FXML
    private Button update;
    @FXML
    private Button backButton;
    
    private boolean parameterCheck = true;
    private Stage stage;
    private Customer customer;
    private String selectedGender;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		neibList.getItems().addAll(Neighberhood.values());
		female.setUserData("Female");
		male.setUserData("Male");
	}
	
	//Upon editing display current details
	public void setDetails() {
		create.setVisible(false);
		update.setVisible(true);
		backButton.setVisible(true);
		firstName.setText(customer.getFirstName());
		lastName.setText(customer.getLastName());
		//gender can't be changed
		if (customer.getGender().toString() == "Female") {
			female.setSelected(true);
		}
		else if(customer.getGender().toString() == "Male") {
			male.setSelected(true);
		}
		else {
			female.setSelected(false);
			male.setSelected(false);

		}
		female.setDisable(true);
		male.setDisable(true);
		
		birthDay.setValue(customer.getBirthDay());
		//lock from editing
		birthDay.setDisable(true);
		birthDay.setStyle("-fx-opacity: 1");
		birthDay.getEditor().setStyle("-fx-opacity: 1");
		
		neibList.getSelectionModel().select(customer.getNeighberhood());
		
		lactoseSens.setSelected(customer.isSensitiveToLactose());
		glutenSens.setSelected(customer.isSensitiveToGluten());	
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
		update.setVisible(true);
		backButton.setVisible(true);
		setDetails();
	}
	
	//Edit object
	public void updateCustomer(ActionEvent event) throws IOException {
		if (paramertsCheck()) {
			customer.setFirstName(firstName.getText());
			customer.setLastName(lastName.getText());
			customer.setNeighberhood(neibList.getSelectionModel().getSelectedItem());
			customer.setSensitiveToLactose(lactoseSens.isSelected());
			customer.setSensitiveToGluten(glutenSens.isSelected());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Customer Update");
			alert.setContentText("All changes saved");
			alert.showAndWait();
		}

	}
	
	//Check user values
	public boolean  paramertsCheck() {
		if (firstName.getText().isEmpty() == true) {
			parameterCheck = false;
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("You must type first name");
			alert.showAndWait();
		} else if (lastName.getText().isEmpty() == true) {
			parameterCheck = false;
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("You must type last name");
			alert.showAndWait();
		} else if (birthDay.getValue() == null) {
			parameterCheck = false;
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("You must chose birth date or enter a valid value");
			alert.showAndWait();
		}
		else if (neibList.getValue() == null) {
			parameterCheck = false;
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("You must chose neighborhood");
			alert.showAndWait();
		} else {
			if (gender.getSelectedToggle() == null) {
				selectedGender = "Unknown";
			} else {
				selectedGender = gender.getSelectedToggle().getUserData().toString();
			}
		}
		return parameterCheck;
	}
	
	//Create new Customer object
	public void createCustomer(ActionEvent event) throws IOException {
		create.setVisible(true);
		update.setVisible(false);
		backButton.setVisible(false);
		if (paramertsCheck()) {
		Customer customer = new Customer(firstName.getText(), lastName.getText(), birthDay.getValue(),
					Gender.valueOf(selectedGender), neibList.getValue(), lactoseSens.isSelected(),
					glutenSens.isSelected());
		Restaurant.getInstance().addCustomer(customer);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User created");
		alert.setContentText("The user was created successfully.\nUse the following user name to login: \"" + customer.getUserName() + "\"\nUse the following password to login: \"" + customer.getBirthDay().toString() + "\"");
		alert.showAndWait();

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		}
	}
	public void switchToScenceBack(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}

}
