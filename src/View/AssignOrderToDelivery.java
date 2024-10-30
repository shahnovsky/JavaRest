package View;

import java.net.URL;
import java.util.ResourceBundle;
import Model.Delivery;
import Model.DeliveryArea;
import Model.Order;
import Model.Restaurant;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AssignOrderToDelivery implements Initializable {

	@FXML
	private TableView<Delivery> availableDeliveriesTable;

	@FXML
	private TableColumn<Delivery, Integer> deliveryId;

	@FXML
	private TableColumn<Delivery, String> assignedTo;

	@FXML
	private Button createNewDelivery;

	@FXML
	private TextField customerName;

	@FXML
	private TextField deliveryArea;

	@FXML
	private Button assign;

	static private Order thisOrder;
	static private DeliveryArea thisOrderDa;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		deliveryId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
		assignedTo.setCellValueFactory(
				data -> new SimpleStringProperty(data.getValue().getDeliveryPerson().getFullName()));
		this.customerName.setText(AssignOrderToDelivery.thisOrder.getCustomer().getFullName());
		this.deliveryArea.setText(AssignOrderToDelivery.thisOrderDa.getAreaName());

		refreshAvailableDeliveries();
	}

	@FXML
	//Set order to delivery
	void assignOrderButton(ActionEvent event) {
		Delivery selectedDelivery = availableDeliveriesTable.getSelectionModel().getSelectedItem();
		thisOrder.setDelivery(selectedDelivery);
		selectedDelivery.getArea().addDelivery(selectedDelivery);
		Restaurant.getInstance().orderByCustomer(thisOrder);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	//refresh data upon update
	private void refreshAvailableDeliveries() {
		assign.setDisable(true);

		ObservableList<Delivery> deliveryList = FXCollections
				.observableArrayList(AssignOrderToDelivery.thisOrderDa.getRegularDeliversNotSent());
		deliveryList.removeIf(delivery -> delivery.getDeliveryPerson() == null);
		
		availableDeliveriesTable.setItems(deliveryList);

		availableDeliveriesTable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				assign.setDisable(false);
			}
		});
	}

	public static void setOrder(Order thisOrder) {
		AssignOrderToDelivery.thisOrder = thisOrder;
		AssignOrderToDelivery.thisOrderDa = Restaurant.getInstance().getDeliveryAreaForOrder(thisOrder);
	}

}
