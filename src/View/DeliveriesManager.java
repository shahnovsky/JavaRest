package View;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.Delivery;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import Model.Restaurant;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DeliveriesManager implements Initializable {
	@FXML
	private TableView<Delivery> tabelDeliveries;
	@FXML
	private TableColumn<Delivery, Integer> deliveryIDColumn;

	@FXML
	private TableColumn<Delivery, String> deliveryPersonColumn;

	@FXML
	private TableColumn<Delivery, String> areaColumn;

	@FXML
	private TableColumn<Delivery, Boolean> isDeliveredColumn;

	@FXML
	private TableColumn<Delivery, LocalDate> dateColumn;

	@FXML
	private Pane deliveryPane;

	@FXML
	private TextField thisDeliveryId;

	@FXML
	private TableView<Order> thisOrders;

	@FXML
	private TableColumn<Order, Integer> thisOrderName;

	@FXML
	private TableColumn<Order, String> thisOrderCustomerName;

	@FXML
	private Button removeOrderButton;

	@FXML
	private Button removeDeliveryButton;

	@FXML
	private TextField postage;

	@FXML
	private CheckBox expressCB;

	@FXML
	private CheckBox regularCB;

	private ObservableList<Order> ordersInDelivery;

	@FXML
	void goBack(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.fillMainDeliveriesTable();
	}

	private void fillMainDeliveriesTable() {
		deliveryPane.setVisible(false);
		regularCB.setDisable(true);
		expressCB.setDisable(true);
		ObservableList<Delivery> deliveriesList = FXCollections
				.observableArrayList(Restaurant.getInstance().getDeliveries().values());

		// Columns factories
		deliveryIDColumn.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
		deliveryPersonColumn.setCellValueFactory(
				data -> new SimpleStringProperty(data.getValue().getDeliveryPerson().getFullName()));

		// isDeliveredColumn.setCellValueFactory(new
		// PropertyValueFactory<Delivery,Boolean>("isDelivered"));
		isDeliveredColumn.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isDelivered()));

		areaColumn.setCellValueFactory(new PropertyValueFactory<Delivery, String>("area"));
		areaColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArea().getAreaName()));

		dateColumn.setCellValueFactory(new PropertyValueFactory<Delivery, LocalDate>("deliveredDate"));

		tabelDeliveries.setItems(deliveriesList);
		tabelDeliveries.refresh();

		// Set action when clicking on a certain row
		// Fill the side pane of a specific order
		tabelDeliveries.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				Delivery thisDelivery = (Delivery) tabelDeliveries.getSelectionModel().getSelectedItem();
				if (thisDelivery != null) {
					refreshOrderPane();
					deliveryPane.setVisible(true);
					removeDeliveryButton.setDisable(false);
				}
			}
		});
	}

	public void refreshOrderPane() {
		thisOrders.getItems().clear();
		Delivery thisDelivery = (Delivery) tabelDeliveries.getSelectionModel().getSelectedItem();
		removeDeliveryButton.setDisable(true);
		thisDeliveryId.setText(String.valueOf(thisDelivery.getId()));
		if (thisDelivery.getClass() == RegularDelivery.class) {
			regularCB.setSelected(true);
			expressCB.setSelected(false);
			postage.setText("");
			if (((RegularDelivery) thisDelivery).getOrders() != null) {
				ordersInDelivery = (ObservableList<Order>) (FXCollections
						.observableArrayList(((RegularDelivery) thisDelivery).getOrders()));
				thisOrderName.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
				thisOrderCustomerName.setCellValueFactory(
						data -> new SimpleStringProperty(data.getValue().getCustomer().getFullName()));
				thisOrders.setItems(ordersInDelivery);

				thisOrders.setOnMouseClicked((MouseEvent event) -> {
					if (event.getButton().equals(MouseButton.PRIMARY)) {
						removeOrderButton.setDisable(((RegularDelivery) thisDelivery).getOrders().size() <= 1);
					}

				});
			}
		} else {
			removeOrderButton.setDisable(true);
			expressCB.setSelected(true);
			regularCB.setSelected(false);
			postage.setText(String.valueOf(((ExpressDelivery) thisDelivery).getPostage()));
			if (((ExpressDelivery) thisDelivery).getOrder() != null) {
				ordersInDelivery = (ObservableList<Order>) (FXCollections
						.observableArrayList(((ExpressDelivery) thisDelivery).getOrder()));
				thisOrderName.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
				thisOrderCustomerName.setCellValueFactory(
						data -> new SimpleStringProperty(data.getValue().getCustomer().getFullName()));
				thisOrders.setItems(ordersInDelivery);
			}

		}
	}

	public void removeOrderFromDelivery(ActionEvent event) {
		Order thisOrder = (Order) thisOrders.getSelectionModel().getSelectedItem();
		if (thisOrder.getDelivery().getClass() == RegularDelivery.class && !thisOrder.getDelivery().isDelivered()
				&& Restaurant.getInstance().removeOrderFromDelivery(thisOrder)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Removing order from delivery");
			alert.setContentText("Succesfully removed order from delivery");
			alert.showAndWait();
			// refreshOrderPane();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Removing order");
			alert.setContentText(
					"Removing failed. You can only remove order that is assigned to regular delivery type, that has not been sent yet.");
			alert.showAndWait();
		}
	}

	public void removeDelivery(ActionEvent event) {
		Delivery thisDelivery = (Delivery) tabelDeliveries.getSelectionModel().getSelectedItem();
		if (thisDelivery.isDelivered() == false && Restaurant.getInstance().removeDelivery(thisDelivery)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Removing Delivery");
			alert.setContentText("Succesfully removed Delivery");
			alert.showAndWait();
			fillMainDeliveriesTable();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Removing Delivery");
			alert.setContentText("Removing failed. You can only remove that has not been sent yet.");
			alert.showAndWait();
		}

	}
}
