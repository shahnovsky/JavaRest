package View;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Model.Cook;
import Model.Restaurant;
import Utils.Expertise;
import Utils.Gender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

//Class to display query cook by expertise
public class CookByExpertise implements Initializable{

    @FXML
    private Button back;
    
    @FXML
    private TableView<Cook> data;
    
    @FXML
    private TableColumn<Cook, String> firstName;

    @FXML
    private TableColumn<Cook, String> lastName;

    @FXML
    private TableColumn<Cook, LocalDate> birth;

    @FXML
    private TableColumn<Cook, Gender> gender;

    @FXML
    private TableColumn<Cook, Boolean> ischef;

    @FXML
    private ComboBox<Expertise> expertise;
    
    private ObservableList<Expertise> list;
    private ObservableList<Cook> cookList;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		list = FXCollections.observableArrayList(Expertise.values());

		expertise.setItems(list);
	}

	
	public void switchToScenceCustomerView(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.GoBack(stage); 
	}
	
	public void setTable(ActionEvent event) {
		Expertise currentExp = expertise.getSelectionModel().getSelectedItem();
		cookList = FXCollections.observableArrayList( Restaurant.getInstance().GetCooksByExpertise(currentExp));
		data.setItems(cookList);
		
		
		birth.setCellValueFactory(new PropertyValueFactory<Cook,LocalDate>("birthDay"));
		firstName.setCellValueFactory(new PropertyValueFactory<Cook,String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<Cook,String>("lastName"));
		gender.setCellValueFactory(new PropertyValueFactory<Cook,Gender>("gender"));
		ischef.setCellValueFactory(new PropertyValueFactory<Cook,Boolean>("isChef"));
	}
	public void switchToScenceBack(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.GoBack(stage);		 
	}


}
