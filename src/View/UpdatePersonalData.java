package View;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Stream;
import Model.Cook;
import Model.Customer;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Person;
import Model.Restaurant;
import Utils.Expertise;
import Utils.Gender;
import Utils.Neighberhood;
import Utils.Vehicle;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

//Class to display and manage people in resraurant: customers, cooks and curriers
public class UpdatePersonalData implements Initializable {

    @FXML
    private RadioButton cooksRB;
    
    @FXML
    private RadioButton cusromersRB;
    
    @FXML
    private ToggleGroup personType;

    @FXML
    private RadioButton deliveryPersonRB;
    
    @FXML
    private Button addDeliveryPerson;
    
    @FXML
    private Button back;

    @FXML  
    private TableView dataTable;

    @FXML
    private TableColumn<Person, Integer> id;

    @FXML
    private TableColumn<Person, String> firstName;

    @FXML
    private TableColumn<Person, String> lastName;

    @FXML
    private TableColumn<Person, LocalDate> birthDate;

    @FXML
    private TableColumn<Person, Gender> gender;
    @FXML
    private Button addCustomer;
    @FXML
    private Button addCook;
    @FXML
    private Button addToBlackList;
    @FXML
    private Button removePerson;
   
	private static Restaurant myRest;
	private HashMap<Integer, Cook> cooks;
	private HashMap<Integer, Customer> customers;
	private HashMap<Integer, DeliveryPerson> deliveryPersons;
	
    private TableColumn<Customer, String> isSensitiveGluten;
    private TableColumn<Customer, String> isSensitiveToLactose;
    private TableColumn<Customer, String> neighborhoods;
    private TableColumn<Customer, String> isBlackList;
    private TableColumn<DeliveryPerson, String>  vehicles;
    private TableColumn<DeliveryPerson, String>  areas;
    private TableColumn<Cook, String> isChef;
    private TableColumn<Cook, String> expertise;


    private ObservableList<Person> list;
    private ObservableList<Person> listDels;
    private ObservableList<Person> listCooks;
    private ObservableList<String> booleanOptions;
    private ObservableList<String> listArea;
    
    //Open new stage of add currier
	public void openNewAddDeliveryPersonWindow(ActionEvent event) throws IOException {
		 SceneManager.OpenNewStage("AddDeliveryPerson", this);
		 deliveryPersonRB.setSelected(true);
		 cusromersRB.setSelected(false);
		 cooksRB.setSelected(false);
		 updateTable();
	}
	//Back to previous stage
	public void switchToScenceManagerMainView(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();	 
		 SceneManager.GoBack(stage);
	}
    //Open new stage of add customer
	public void switchToScenceAddCustomer(ActionEvent event) throws IOException {
		 SceneManager.OpenNewStage("AddCustomer", this);
		 deliveryPersonRB.setSelected(false);
		 cusromersRB.setSelected(true);
		 cooksRB.setSelected(false);
		 updateTable();
	} 
    
    //Open new stage of add cook
	public void switchToScenceAddCook(ActionEvent event) throws IOException {
		 SceneManager.OpenNewStage("AddCook", this);
		 deliveryPersonRB.setSelected(false);
		 cusromersRB.setSelected(false);
		 cooksRB.setSelected(true);
		 updateTable();
	} 
	
	//set table parameters of Person
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		id.setCellValueFactory(new PropertyValueFactory<Person,Integer>("id"));
		firstName.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
		birthDate.setCellValueFactory(new PropertyValueFactory<Person,LocalDate>("birthDay"));
		gender.setCellValueFactory(new PropertyValueFactory<Person,Gender>("gender"));

		firstName.setCellFactory(TextFieldTableCell.forTableColumn());
		firstName.setOnEditCommit(
		    new EventHandler<CellEditEvent<Person, String>>() {
		        @Override
		        public void handle(CellEditEvent<Person, String> t) {
		            ((Person) t.getTableView().getItems().get(
		                t.getTablePosition().getRow())
		                ).setFirstName(t.getNewValue());
		        }
		    }
		);
		
		lastName.setCellFactory(TextFieldTableCell.forTableColumn());
		lastName.setOnEditCommit(
		    new EventHandler<CellEditEvent<Person, String>>() {
		        @Override
		        public void handle(CellEditEvent<Person, String> t) {
		            ((Person) t.getTableView().getItems().get(
		                t.getTablePosition().getRow())
		                ).setLastName(t.getNewValue());
		        }
		    }
		);

		dataTable.setEditable(true);
	
		booleanOptions = FXCollections.observableArrayList(
				String.valueOf(true),
				String.valueOf(false));

	}
	
	//Get HashMaps from class Restaurant of persons 
	public UpdatePersonalData() {
		myRest = Restaurant.getInstance();
		cooks = myRest.getCooks();	
		customers = myRest.getCustomers();
		deliveryPersons = myRest.getDeliveryPersons();
	}
	
	//Method to add column of generic type
	private <S,T> TableColumn<S,T> createColumn(String title, Function<S, Property<T>> prop) {
		TableColumn<S,T> column = new TableColumn<>(title);
	    column.setCellValueFactory(cellData -> prop.apply(cellData.getValue()));
	    return column;
	}
	
	public void updateTable()
	{
		for (TableColumn<?, ?> c : Arrays.asList(isSensitiveGluten, isSensitiveToLactose, neighborhoods, vehicles, areas, isChef, expertise, isBlackList)) {
			if (c != null) {
				dataTable.getColumns().remove(c);
				c.setCellFactory(null);
				c.setCellValueFactory(null);
			}
		}

		if(cusromersRB.isSelected()) {
			dataTable.getItems().clear();
			ObservableList<String> neibs = FXCollections.observableArrayList(Stream.of(Neighberhood.values()).map(Neighberhood::name).toArray(String[]::new));
			list = FXCollections.observableArrayList(customers.values());
	
			dataTable.setItems(list);
			
			isSensitiveGluten = createColumn("is Sensitive Gluten", item -> new SimpleStringProperty(String.valueOf(item.isSensitiveToGluten())));
			isSensitiveGluten.setCellFactory(ComboBoxTableCell.forTableColumn(booleanOptions));
			isSensitiveGluten.setOnEditCommit(
				    new EventHandler<CellEditEvent<Customer, String>>() {
				        public void handle(CellEditEvent<Customer, String> t) {
				            (t.getTableView().getItems().get(
				                t.getTablePosition().getRow())
				                ).setSensitiveToGluten(t.getNewValue() == String.valueOf(true));
				        }
				    }
				);
			isSensitiveToLactose = createColumn("is Sensitive Lactose", item -> new SimpleStringProperty(String.valueOf(item.isSensitiveToLactose())));
			isSensitiveToLactose.setCellFactory(ComboBoxTableCell.forTableColumn(booleanOptions));
			isSensitiveToLactose.setOnEditCommit(
				    new EventHandler<CellEditEvent<Customer, String>>() {
				        public void handle(CellEditEvent<Customer, String> t) {
				            (t.getTableView().getItems().get(
				                t.getTablePosition().getRow())
				                ).setSensitiveToLactose(t.getNewValue() == String.valueOf(true));
				        }
				    }
				);
			neighborhoods = createColumn("Neighborhood", item -> new SimpleStringProperty((item.getNeighberhood().toString())));
			neighborhoods.setCellFactory(ComboBoxTableCell.forTableColumn(neibs));
			neighborhoods.setOnEditCommit(
				    new EventHandler<CellEditEvent<Customer, String>>() {
				        public void handle(CellEditEvent<Customer, String> t) {
				            (t.getTableView().getItems().get(
				                t.getTablePosition().getRow())
				                ).setNeighberhood(Neighberhood.valueOf(t.getNewValue()) );
				            
				        }
				    }
				);
			isBlackList = createColumn("Black List", item -> new SimpleStringProperty(String.valueOf(Restaurant.getInstance().getBlackList().contains(item))));

	
			dataTable.getColumns().addAll(isSensitiveGluten, isSensitiveToLactose, neighborhoods,isBlackList);
		}
		else if (deliveryPersonRB.isSelected()) {
			dataTable.getItems().clear();
			
			HashMap<String, DeliveryArea> hmArea = new HashMap<String, DeliveryArea>();
			
			for (Map.Entry<Integer,DeliveryArea> entry : myRest.getAreas().entrySet()) {
				hmArea.put(entry.getValue().getAreaName() , entry.getValue());
			}
			
			ObservableList<String> vehiclesList = FXCollections.observableArrayList(Stream.of(Vehicle.values()).map(Vehicle::name).toArray(String[]::new));
			ObservableList<String> arealist = FXCollections.observableArrayList(hmArea.keySet());

			listDels = FXCollections.observableArrayList(deliveryPersons.values());
	
			dataTable.setItems(listDels);
			
			vehicles = createColumn("Vehicle", item -> new SimpleStringProperty((item.getVehicle().toString())));
			vehicles.setCellFactory(ComboBoxTableCell.forTableColumn(vehiclesList));
			vehicles.setOnEditCommit(
				    new EventHandler<CellEditEvent<DeliveryPerson, String>>() {
				        public void handle(CellEditEvent<DeliveryPerson, String> t) {
				            (t.getTableView().getItems().get(
				                t.getTablePosition().getRow())
				                ).setVehicle(Vehicle.valueOf(t.getNewValue()) );
				            
				        }
				    }
				);
			

			listArea = FXCollections.observableArrayList(hmArea.keySet());

			areas = createColumn("Areas", item -> new SimpleStringProperty((item.getArea().getAreaName())));
			areas.setCellFactory(ComboBoxTableCell.forTableColumn(listArea));
			areas.setOnEditCommit(
				    new EventHandler<CellEditEvent<DeliveryPerson, String>>() {
				        public void handle(CellEditEvent<DeliveryPerson, String> t) {
				            (t.getTableView().getItems().get(
				                t.getTablePosition().getRow())
				                ).setArea( hmArea.get(t.getNewValue()) );
				            ///I AM HEREEEEEEEEEEEEEEEEEEE
				        }
				    }
				);
			
			dataTable.getColumns().addAll(vehicles, areas);


		}
		else if (cooksRB.isSelected()){
			dataTable.getItems().clear();
			listCooks = FXCollections.observableArrayList(cooks.values());
	
			dataTable.setItems(listCooks);
			

			isChef = createColumn("is Chef", item -> new SimpleStringProperty(String.valueOf(item.getIsChef())));
			isChef.setCellFactory(ComboBoxTableCell.forTableColumn(booleanOptions));
			isChef.setOnEditCommit(
				    new EventHandler<CellEditEvent<Cook, String>>() {
				        public void handle(CellEditEvent<Cook, String> t) {
				            (t.getTableView().getItems().get(
				                t.getTablePosition().getRow())
				                ).setIsChef(t.getNewValue() == String.valueOf(true));
				        }
				    }
				);
			ObservableList<String> expertiseList = FXCollections.observableArrayList(Stream.of(Expertise.values()).map(Expertise::name).toArray(String[]::new));

			
			
			expertise = createColumn("Expertise", item -> new SimpleStringProperty((item.getExpert().toString())));
			expertise.setCellFactory(ComboBoxTableCell.forTableColumn(expertiseList));
			expertise.setOnEditCommit(
				    new EventHandler<CellEditEvent<Cook, String>>() {
				        public void handle(CellEditEvent<Cook, String> t) {
				            (t.getTableView().getItems().get(
				                t.getTablePosition().getRow())
				                ).setExpert(Expertise.valueOf(t.getNewValue()) );
				            
				        }
				    }
				);
			dataTable.getColumns().addAll(isChef,expertise);

			
			
		}
		else {
			System.out.println("now");
		}
	}
	
	
	public void buttonselected(ActionEvent event) {
		updateTable();
	}
	
	public void setCustomertoBlackList(ActionEvent event) {
		if ((dataTable.getSelectionModel().getSelectedItem() != null) && (cusromersRB.isSelected())) {
			Restaurant.getInstance().addCustomerToBlackList((Customer) dataTable.getSelectionModel().getSelectedItem());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Customer assignement to black list");
			alert.setContentText("Customer succesfully added to black list.");
			dataTable.refresh();
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Customer assignement to black list");
			alert.setContentText("Please select customer.");
			alert.showAndWait();
		}
	}

	
	public void removePerson(ActionEvent event) {
		if ((dataTable.getSelectionModel().getSelectedItem() != null) && (personType.getSelectedToggle()!=null)){
			Alert alert = new Alert(AlertType.INFORMATION);
			if (deliveryPersonRB.isSelected()) {
				Restaurant.getInstance().removeDeliveryPerson((DeliveryPerson) dataTable.getSelectionModel().getSelectedItem());
				dataTable.getItems().remove((DeliveryPerson) dataTable.getSelectionModel().getSelectedItem());
				alert.setTitle("Removing delivery person");
				alert.setContentText("Delivery person succesfully removed.");
				alert.showAndWait();
			}
			else if (cusromersRB.isSelected()) {
				Restaurant.getInstance().removeCustomer((Customer) dataTable.getSelectionModel().getSelectedItem());
				dataTable.getItems().remove((Customer) dataTable.getSelectionModel().getSelectedItem());
				alert.setTitle("Removing customer");
				alert.setContentText("Customer succesfully removed.");
				alert.showAndWait();
			}
			else {
				Restaurant.getInstance().removeCook((Cook) dataTable.getSelectionModel().getSelectedItem());
				dataTable.getItems().remove((Cook) dataTable.getSelectionModel().getSelectedItem());
				alert.setTitle("Removing cook");
				alert.setContentText("Cook succesfully removed.");
				alert.showAndWait();
			}
			dataTable.getSelectionModel().clearSelection();
			
			dataTable.refresh();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Removing person");
			alert.setContentText("Please select person to delete.");
			alert.showAndWait();
		}			
	}
	
	

	
}
