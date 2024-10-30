package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Restaurant;
import Utils.Gender;
import Utils.Vehicle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddDeliveryPerson implements Initializable{
	
	private ArrayList<String> arr;
	private String selectedArea;
	private String selectedVihecle;
	private String selectedGender;
	private Stage stage;

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
    private ToggleGroup vihecle;
    
    @FXML
    private Button save;
    
    @FXML
    private ListView<String> list;
    
    @FXML
    private RadioButton car;

    @FXML
    private RadioButton motorBike;

    @FXML
    private RadioButton bike;
 
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO Auto-generated method stub
		
		arr = new ArrayList<String>();
		for (DeliveryArea da : Restaurant.getInstance().getAreas().values()) {
			arr.add(da.getAreaName()+ ", ID=" + da.getId());

		}
		list.getItems().addAll(arr);
		
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				selectedArea = list.getSelectionModel().getSelectedItem();
			}
		});
	}
    
    //Create new currier
    public void save(ActionEvent event) throws IOException {
    	
    	female.setUserData("Female");
    	male.setUserData("Male");
    	
    	car.setUserData("Car");
    	bike.setUserData("Bicycle");
    	motorBike.setUserData("Motorcycle");
    	
    	selectedArea = list.getSelectionModel().getSelectedItem();
    	

    	if (selectedArea == null) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText("You must select a delivery area");
    		alert.showAndWait();
    	}
    	else if(firstName.getText().isEmpty() == true) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText("You must type first name");
    		alert.showAndWait();
    	}
    	else if(lastName.getText().isEmpty() == true) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText("You must type last name");
    		alert.showAndWait();
    	}
    	else if(birthDay.getValue() == null) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText("You must chose birth date or enter a valid value");
    		alert.showAndWait();
    	}
    	else if(vihecle.getSelectedToggle() == null) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText("You must select vihecle");
    		alert.showAndWait();
    	}
    	
    	else {

    		if (gender.getSelectedToggle() == null) {
       		 selectedGender = "Unknown";
       	}
    		else {
            	selectedGender = gender.getSelectedToggle().getUserData().toString();
    		}
    	selectedArea = selectedArea.replaceAll(".*ID=", "");

    	DeliveryArea da = Restaurant.getInstance().getRealDeliveryArea(Integer.parseInt(selectedArea)); 
    	
    	selectedVihecle = vihecle.getSelectedToggle().getUserData().toString();
    	

    	DeliveryPerson dp = new DeliveryPerson( firstName.getText(),  lastName.getText(), birthDay.getValue(),Gender.valueOf(selectedGender) ,  Vehicle.valueOf(selectedVihecle), da);
    	Restaurant.getInstance().addDeliveryPerson(dp, da);
    	
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	stage.close();
    }
    }

	

}
