package View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Exceptions.IllegalCustomerException;
import Exceptions.SensitiveException;
import Model.Customer;
import Model.DeliveryArea;
import Model.Dish;
import Model.Order;
import Model.Restaurant;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

//Upon placing order display summary of shopping cart
public class CheckOutShoppingCart {
	private HashMap<Dish, Integer> ordersHm;

	@FXML
	private VBox vbox;
	
	@FXML
	private Button back;

	@FXML
	private TableColumn<Map.Entry<Dish, Integer>, String> dishColumn;

	@FXML
	private TableColumn<Map.Entry<Dish, Integer>, String> qtyColumn;
	
	@FXML
	private TableView<Map.Entry<Dish, Integer>> table;
	
	@FXML
    private Button deleteButton;
	
	@FXML
	private Button placeOrder;
	
	private Customer customer;
	
	//This is the shopping list
	public void setList(HashMap<Dish, Integer> ordersHm) {
		this.ordersHm = ordersHm;
	}
	//Delete dish from order summury
	@FXML
    void deleteSelectedOrders(ActionEvent event) {
		ObservableList<Map.Entry<Dish, Integer>> selectedRows = table.getSelectionModel().getSelectedItems();
		ArrayList<Map.Entry<Dish, Integer>> rows = new ArrayList<>(selectedRows);
		for (Map.Entry<Dish, Integer> currentRow : rows) {
			ordersHm.remove(currentRow.getKey());
			table.getItems().remove(currentRow);
		}
		if (ordersHm.size() == 0)
			placeOrder.setDisable(true);
    }
	
	public void display() {
		setTable();
	}
	
	//Convert HashMap to ArrayList of type Dish
	public ArrayList<Dish> convertHMtoList() {
		ArrayList<Dish> list = new ArrayList<Dish>();
		for (Map.Entry<Dish, Integer> entry : ordersHm.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				list.add(entry.getKey());
			}
		}
		return list;
	}
	
	//Add Order to restaurant
	public void placeOrder(ActionEvent event) {
		Order o = new Order(this.customer, convertHMtoList(), null);
		try {
			if (Restaurant.getInstance().addOrder(o)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("New Order");
				alert.setContentText("Congrats " + o.getCustomer().getFirstName() + "! \nYour order was placed.");
				alert.showAndWait();
				
				boolean checkDeliveryArea = false;
				for (DeliveryArea da : Restaurant.getInstance().getAreas().values()) {
					if (da.getNeighberhoods().contains(o.getCustomer().getNeighberhood())) {
						checkDeliveryArea = true;
						break;
					}
				}
				if (checkDeliveryArea == false) {
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("No delivery area");
					alert.setContentText("No delivery area exists yet for this order. AI won't work for this order");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("New Order");
				alert.setContentText(o.getCustomer().getFirstName() + ", We couln't place your order.");
				alert.showAndWait();

			}
		} catch (IllegalCustomerException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning");
			alert.setContentText(e.getMessage());
			alert.showAndWait();

		} catch (SensitiveException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	//Display summary order  table
	public void setTable() {
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		ObservableList<Map.Entry<Dish, Integer>> dishList = FXCollections.observableArrayList(ordersHm.entrySet());

		dishColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String> p) {

						return new SimpleStringProperty(p.getValue().getKey().getDishName().toString());
					}
				});
		qtyColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String> p) {
						// for second column use value
						return new SimpleStringProperty(p.getValue().getValue().toString());
					}
				});

		table.setItems(dishList);
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void switchToScenceBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}

}
