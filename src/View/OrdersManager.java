package View;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeSet;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Dish;
import Model.Order;
import Model.RegularDelivery;
import Model.Restaurant;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OrdersManager implements Initializable {

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Integer> orderId;

    @FXML
    private TableColumn<Order, String> customerName;

    @FXML
    private TableColumn<Order, Integer> deliveryNo;

    @FXML
    private TableColumn<Order, String> isSent;
    
    @FXML
    private TableColumn<Order, String> areaColumn;
    
    @FXML
    private TableColumn<Order, String> typeColumn;

    @FXML
    private Button back;
    
    @FXML
    private Pane orderPane;

    @FXML
    private TextField thisOrderId;

    @FXML
    private TextField thisCustomerName;

    @FXML
    private TextField thisDeliveryNumber;

    @FXML
    private CheckBox thisSentToCustomer;

    @FXML
    private TableView<Dish> thisDishes;
    
    @FXML
    private Button assignDeliveryButton;
    
    @FXML
    private Button addDish;
    
    @FXML
    private Button delDish;
    
    @FXML
    private Button removeOrderButton;

    @FXML
    private TableColumn<Dish, String> thisDishesDishName;
    
    @FXML
    private Button aiButton;

    @FXML
    void goBack(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();	 
		 SceneManager.GoBack(stage);
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	this.fillMainOdersTable();
    }
    
    private void fillMainOdersTable() {
    	orderPane.setVisible(false);
    	ObservableList<Order> ordersList = FXCollections.observableArrayList(Restaurant.getInstance().getOrders().values());

    	// Columns factories
    	orderId.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
    	customerName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getFullName()));	
    	deliveryNo.setCellValueFactory(data -> {
    		Order thisOrder  = data.getValue();
    		if (thisOrder.getDelivery() != null)
            	return new SimpleIntegerProperty(thisOrder.getDelivery().getId()).asObject();
            return new SimpleObjectProperty<>(null);
    	});
    	
    	isSent.setCellValueFactory(data -> {
    		Order thisOrder  = data.getValue();
    		if (thisOrder.getDelivery() != null)
            	return new SimpleStringProperty(String.valueOf(thisOrder.getDelivery().isDelivered()));
            return new SimpleObjectProperty<>(null);
    	});
    	
    	areaColumn.setCellValueFactory(data -> {
    		Order thisOrder  = data.getValue();
    		if (thisOrder.getDelivery() != null)
            	return new SimpleStringProperty(String.valueOf(thisOrder.getDelivery().getArea().getAreaName()));
            return new SimpleObjectProperty<>(null);
    	});
    	
    	typeColumn.setCellValueFactory(data -> {
    		Order thisOrder  = data.getValue();
    		if (thisOrder.getDelivery() != null)
            	return new SimpleStringProperty(String.valueOf(thisOrder.getDelivery().getClass().toString().replaceAll(".*\\.", "")));
            return new SimpleObjectProperty<>(null);
    	});
    	
    	ordersTable.setItems(ordersList);
    	ordersTable.refresh();
    	
    	// Set action when clicking on a certain row
    	// Fill the side pane of a specific order
    	ordersTable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
            	Order thisOrder = (Order)ordersTable.getSelectionModel().getSelectedItem();
            	if (thisOrder != null) {
            		refreshOrderPane();
            		orderPane.setVisible(true);
            	}
            }
        });
    }
    
    public void refreshOrderPane()
    {
    	Order thisOrder = (Order)ordersTable.getSelectionModel().getSelectedItem();
    	delDish.setDisable(true);
        thisOrderId.setText(String.valueOf(thisOrder.getId()));
        thisCustomerName.setText(String.valueOf(thisOrder.getCustomer().getFullName()));
        
        
        
        if (thisOrder.getDelivery() != null) {
        	thisDeliveryNumber.setText(String.valueOf(thisOrder.getDelivery().getId()));
        	thisSentToCustomer.setSelected(thisOrder.getDelivery().isDelivered());
        	assignDeliveryButton.setDisable(true);   
        	if (thisOrder.getDelivery().isDelivered())
        		removeOrderButton.setDisable(true);
        	else if (thisOrder.getDelivery().getClass() == RegularDelivery.class)
            	removeOrderButton.setDisable(false);
            else
            	removeOrderButton.setDisable(true);
        } else {
        	thisDeliveryNumber.clear();
        	thisSentToCustomer.setSelected(false);
        	assignDeliveryButton.setDisable(false);
        	removeOrderButton.setDisable(true);
        }
        
        ObservableList<Dish> dishesInOrder = FXCollections.observableArrayList(thisOrder.getDishes());
    	thisDishesDishName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDishName()));
    	thisDishes.setItems(dishesInOrder);
    	
    	thisDishes.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                delDish.setDisable(thisOrder.getDishes().size() <= 1);
            }
        });
        
    }
    //Delete dish from dish List
    public void deleteDishFromList(ActionEvent event) {
        Dish thisDish = thisDishes.getSelectionModel().getSelectedItem();
        Order thisOrder = ordersTable.getSelectionModel().getSelectedItem();
        
        thisOrder.removeDish(thisDish);
        refreshOrderPane();
    }
    
    //Add dish
    public void addDishToList(ActionEvent event) {
        Order thisOrder = ordersTable.getSelectionModel().getSelectedItem();
        
        ArrayList<String> availableDishes = new ArrayList<String>();
        for (Dish di : Restaurant.getInstance().getDishes().values()) {
        	availableDishes.add(di.getDishName() + " ID=" + di.getId());
        }
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<String>(availableDishes.get(0), availableDishes);
        
        Optional<String> result = choiceDialog.showAndWait();
        
        result.ifPresent(dishIdString -> {
        	dishIdString = dishIdString.replaceAll(".*ID=", "");
        	Dish thisDish = Restaurant.getInstance().getDishes().get(Integer.valueOf(dishIdString));
        	thisOrder.addDish(thisDish);
        	refreshOrderPane();
        });
    }
    
    @FXML
    public void assignDelivery(ActionEvent event) throws IOException {
        Order thisOrder = ordersTable.getSelectionModel().getSelectedItem();
        if ((thisOrder == null) || (Restaurant.getInstance().getDeliveryAreaForOrder(thisOrder) == null)) {
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Assignment error");
			alert.setContentText("There is no existing delivery area for this order");
			alert.showAndWait();
        } else {
        AssignOrderToDelivery.setOrder(thisOrder);
        SceneManager.OpenNewStage("AssignOrderToDelivery", this);
        fillMainOdersTable();
        }
    }
    
    @FXML
    public void swtichToAI(ActionEvent event) throws IOException {
		 RunAI();
		 fillMainOdersTable();
    }
    
    public void RunAI()
    {
    	if (Restaurant.getInstance().getOrderByDeliveryAreaNOTsent().isEmpty()) {
			System.out.println(Restaurant.getInstance().getDeliveries());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No new order");
			alert.setContentText("There weren't any new orders found.\nThus, no new deliveries were created. ");
			alert.showAndWait();
		} else {

			for (DeliveryArea da : Restaurant.getInstance().getOrderByDeliveryAreaNOTsent().keySet()) {
				// take all order from area

				// ai needs tree set and not hashset
				TreeSet<Order> ordersfromDa = new TreeSet<Order>();
				ordersfromDa.addAll(Restaurant.getInstance().getOrderByDeliveryAreaNOTsent().get(da));
				
				if (da.getDelPersons().size() == 0)
				{
					Alert alert = new Alert(AlertType.ERROR);
	    			alert.setTitle("AI Error");
	    			alert.setContentText("Cannot find a delivery person for area " + da.getAreaName());
	    			alert.showAndWait();
				}

				DeliveryPerson[] arrayCurrier = da.getDelPersons()
						.toArray(new DeliveryPerson[da.getDelPersons().size()]);

				
				// generate a random number
				Random rndm = new Random();

				// this will generate a random number between 0 and
				// HashSet.size - 1
				int rndmNumber = 0;
				if (arrayCurrier.length > 1)
					rndmNumber = rndm.nextInt(arrayCurrier.length - 1);

				// public TreeSet<Delivery>
				TreeSet<Delivery> newDeliveries = Restaurant.getInstance().createAIMacine(arrayCurrier[rndmNumber], da, ordersfromDa);
				System.out.println(newDeliveries);
				System.out.println(Restaurant.getInstance().getDeliveries());
			}
			
			Restaurant.getInstance().getOrderByDeliveryAreaNOTsent().clear();
		}
    }
    //Remove order
    public void removeOrder(ActionEvent event) {
    	Order thisOrder = ordersTable.getSelectionModel().getSelectedItem();
    	if (thisOrder.getDelivery().getClass() == RegularDelivery.class && !thisOrder.getDelivery().isDelivered()
    			&& Restaurant.getInstance().removeOrder(thisOrder)) {
	        		Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setTitle("Removing order");
	    			alert.setContentText("Succesfully removed order");
	    			alert.showAndWait();
	    			fillMainOdersTable();
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Removing order");
			alert.setContentText("Removing failed. You can only remove order that is assigned to regular delivery type, that has not been sent yet.");
			alert.showAndWait();
    	}

    }
}
