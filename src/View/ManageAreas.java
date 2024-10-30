package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Restaurant;
import Utils.Neighberhood;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

//Edit areas in restaurant
public class ManageAreas implements Initializable {

    @FXML
    private TextField areaName;

    @FXML
    private ListView<Neighberhood> neighListView;

    @FXML
    private ListView<String> personListView;

    @FXML
    private TextField driveTime;

    @FXML
    private Button backButton;
    
    @FXML
    private Button updateButton;

    @FXML
    private Button createButton;
    
    private DeliveryArea thisDeliveryArea = null;

    @FXML
    void onCreate(ActionEvent event) {
    	ObservableList<Neighberhood> selectedNeigh =  neighListView.getSelectionModel().getSelectedItems();
    	ObservableList<String> selectedPerson =  personListView.getSelectionModel().getSelectedItems();
    	HashSet<Neighberhood> neighberhoods = new HashSet<>(selectedNeigh);
    	
    	if (areaName.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Specify area name");
			alert.setContentText("You must specify area name");
			alert.showAndWait();
			return;
    	}
    	
    	if (driveTime.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Specify drive time");
			alert.setContentText("You must specify drive time");
			alert.showAndWait();
			return;
    	}
    	
    	if (selectedNeigh.size() == 0) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Specify neighberhoods");
			alert.setContentText("You must specify at least one neighberhood");
			alert.showAndWait();
			return;
    	}
    	
    	try {
    		DeliveryArea da = new DeliveryArea(areaName.getText(), neighberhoods, Integer.parseInt(driveTime.getText()));
    		Restaurant.getInstance().addDeliveryArea(da);	
    		
    		if (selectedPerson.size() != 0) {
	    		for (String person : selectedPerson) {
	    			int personId = Integer.parseInt(person.replaceAll(".*ID=", ""));
	    			DeliveryPerson thisPerson = Restaurant.getInstance().getRealDeliveryPerson(personId);
	    			da.addDelPerson(thisPerson);
	    		}
    		}
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("Delivery area successfully created");
			alert.showAndWait();
			return;
    		
    	} catch (java.lang.NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid time");
			alert.setContentText("The delivery time is invalid");
			alert.showAndWait();
			return;
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refreshPage();
	}
	
	//Refresh data 
	public void refreshPage() {
		neighListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		personListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		ObservableList<Neighberhood> neighObsList = FXCollections.observableArrayList(Neighberhood.values());
		
		ArrayList<String> arr = new ArrayList<String>();
		if (thisDeliveryArea != null) {
			for (DeliveryPerson dp : Restaurant.getInstance().getAreas().get(thisDeliveryArea.getId()).getDelPersons()) {
				arr.add(dp.getLastName() + " " + dp.getFirstName()+ ", ID="+ dp.getId() );
			}
		}
		
		
		//for (DeliveryPerson dp : Restaurant.getInstance().getDeliveryPersons().values()) {
			//arr.add(dp.getLastName() + " " + dp.getFirstName()+ ", ID="+ dp.getId() );
		//}
		Collections.sort(arr);
		ObservableList<String> personObsList = FXCollections.observableArrayList(arr);
		personListView.setItems(personObsList);
		neighListView.setItems(neighObsList);
		
		if (this.thisDeliveryArea != null) {
			createButton.setVisible(false);
			updateButton.setVisible(true);
			
			for (Neighberhood n : this.thisDeliveryArea.getNeighberhoods())
				neighListView.getSelectionModel().select(n);
			
			for (DeliveryPerson dp : this.thisDeliveryArea.getDelPersons())
				personListView.getSelectionModel().select(dp.getLastName() + " " + dp.getFirstName()+ ", ID="+ dp.getId() );
					
			
			areaName.setText(this.thisDeliveryArea.getAreaName());
			areaName.setDisable(true);
			driveTime.setText(String.valueOf(this.thisDeliveryArea.getDeliverTime()));
			driveTime.setDisable(true);
		} else {
			createButton.setVisible(true);
			updateButton.setVisible(false);
		}
	}
	
	public void setArea(DeliveryArea da) {
		this.thisDeliveryArea = da;
		refreshPage();
	}
	//Edit area
	@FXML
    void onUpdate(ActionEvent event) {
		ObservableList<Neighberhood> selectedNeigh =  neighListView.getSelectionModel().getSelectedItems();
    	ObservableList<String> selectedPerson =  personListView.getSelectionModel().getSelectedItems();
    	HashSet<Neighberhood> neighberhoods = new HashSet<>(selectedNeigh);
    	
    	if (areaName.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Specify area name");
			alert.setContentText("You must specify area name");
			alert.showAndWait();
			return;
    	}
    	
    	if (driveTime.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Specify drive time");
			alert.setContentText("You must specify drive time");
			alert.showAndWait();
			return;
    	}
    	
    	if (selectedNeigh.size() == 0) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Specify neighberhoods");
			alert.setContentText("You must specify at least one neighberhood");
			alert.showAndWait();
			return;
    	}
    	
    	try {
    		ArrayList<Neighberhood> neighberhoodsToRemove = new ArrayList<>();
    		thisDeliveryArea.setAreaName(areaName.getText());
    		for (Neighberhood n : thisDeliveryArea.getNeighberhoods())
    			neighberhoodsToRemove.add(n);
    		for (Neighberhood n : neighberhoodsToRemove)
    			thisDeliveryArea.removeNeighberhood(n);
    		for (Neighberhood n : neighberhoods)
    			thisDeliveryArea.addNeighberhood(n);
    		
    		ArrayList<DeliveryPerson> delperToRemove = new ArrayList<>();
    		for (DeliveryPerson dp : thisDeliveryArea.getDelPersons())
    			delperToRemove.add(dp);
    		for (DeliveryPerson dp : delperToRemove)
    			thisDeliveryArea.removeDelPerson(dp);
    		for (String person : selectedPerson) {
    			int personId = Integer.parseInt(person.replaceAll(".*ID=", ""));
    			DeliveryPerson thisPerson = Restaurant.getInstance().getRealDeliveryPerson(personId);
    			thisDeliveryArea.addDelPerson(thisPerson);
    		}
    		 		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("Delivery area successfully updated");
			alert.showAndWait();
			return;
    		
    	} catch (java.lang.NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid time");
			alert.setContentText("The delivery time is invalid");
			alert.showAndWait();
			return;
    	}
    }
	
	@FXML
	public void goBack(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();	 
		 SceneManager.GoBack(stage);
    }

}

