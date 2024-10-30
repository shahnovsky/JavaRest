package View;

import java.io.IOException;
import Model.Cook;
import Model.Restaurant;
import Utils.Expertise;
import Utils.Gender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddCook {
	 @FXML
	    private TextField firstName;

	    @FXML
	    private TextField lastName;

	    @FXML
	    private DatePicker birthDay;

	    @FXML
	    private RadioButton male;

	    @FXML
	    private ToggleGroup gender;

	    @FXML
	    private RadioButton female;

	    @FXML
	    private Button create;

	    @FXML
	    private RadioButton italien;

	    @FXML
	    private ToggleGroup expertise;

	    @FXML
	    private RadioButton mediterranean;

	    @FXML
	    private RadioButton american;

	    @FXML
	    private RadioButton french;

	    @FXML
	    private RadioButton indian;

	    @FXML
	    private CheckBox chef;

	    @FXML
	    private RadioButton asian;
	    
	    private String selectedGender;
		private Stage stage;
	    
		//Add cook to restaurant
	    public void createCook(ActionEvent event) throws IOException {
			female.setUserData("Female");
			male.setUserData("Male");
			italien.setUserData("Italien");;
			mediterranean.setUserData("Mediterranean");
			american.setUserData("American");
			french.setUserData("French");
			indian.setUserData("Indian");
			asian.setUserData("Asian");
								
			if (firstName.getText().isBlank() == true) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setContentText("You must type first name");
				alert.showAndWait();
			} else if (lastName.getText().isBlank() == true) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setContentText("You must type last name");
				alert.showAndWait();
			} else if (birthDay.getValue() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setContentText("You must chose birth date or enter a valid value");
				alert.showAndWait();
			}

			else if (expertise.getSelectedToggle() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setContentText("You must chose expertise");
				alert.showAndWait();
			} else {
				if (gender.getSelectedToggle() == null) {
					selectedGender = "Unknown";
				} else {
					selectedGender = gender.getSelectedToggle().getUserData().toString();
				}
				Cook cook = new Cook(firstName.getText(), lastName.getText(), birthDay.getValue(),
						Gender.valueOf(selectedGender), Expertise.valueOf(expertise.getSelectedToggle().getUserData().toString()), chef.isSelected());

				
				Restaurant.getInstance().addCook(cook);

				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

				stage.close();
			}
		}
}
