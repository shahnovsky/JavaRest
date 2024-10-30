package View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import Model.Customer;
import Model.Dish;
import Model.Order;
import Model.Restaurant;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DisplayOrders {
	@FXML
	private ListView<String> listOfOrders;
	
	@FXML
	private ListView<String> listdisesOfSpecificOrder;

	@FXML
	private Button remove;
	
	@FXML
	private Button back;
	
	@FXML
	private Label name;

	@FXML
	private VBox paneVBox;
	
	private ObservableList<String> dishList;
	private TreeSet<Order> ordersOfCustomer;
	private Customer customer;

	private ArrayList<Order> selectedOrders;

	public void setScene() {
		// TODO Auto-generated method stub
		paneVBox.getChildren().clear();
		selectedOrders = new ArrayList<>();
		name.setText(customer.getFullName());
		remove.setDisable(true);
		ordersOfCustomer = Restaurant.getInstance().getOrderByCustomer().get(this.customer);

		if (ordersOfCustomer != null) {

			if (ordersOfCustomer != null) {
				for (Order o : ordersOfCustomer) {
					listdisesOfSpecificOrder = new ListView<String>();
					dishList = FXCollections.observableArrayList();

					HBox hbox = new HBox();
					VBox v = new VBox();
					CheckBox checkBox = new CheckBox();
					checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
						@Override
						public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
								Boolean newValue) {
							if (newValue) {
								selectedOrders.add(o);
								remove.setDisable(false);
							} else {
								selectedOrders.remove(o);
								if (selectedOrders.size() == 0)
									remove.setDisable(true);
							}
						}
					});

					Label orderDescriptor = new Label("Order " + o.getId() + " on date "
							+ o.getDelivery().getDeliveredDate() + ". Delivered? " + o.getDelivery().isDelivered());
					hbox.getChildren().addAll(checkBox, orderDescriptor);
					hbox.setAlignment(Pos.TOP_CENTER);
					hbox.setSpacing(5);
					v.getChildren().add(hbox);
					for (Dish d : o.getDishes()) {
						dishList.add(d.getDishName());
					}
					listdisesOfSpecificOrder.setItems(dishList);
					listdisesOfSpecificOrder.setPrefWidth(Region.USE_COMPUTED_SIZE);
					listdisesOfSpecificOrder.setMinWidth(Region.USE_COMPUTED_SIZE);
					listdisesOfSpecificOrder.setMaxWidth(Region.USE_PREF_SIZE);

					listdisesOfSpecificOrder.setMinHeight(Region.USE_COMPUTED_SIZE);
					listdisesOfSpecificOrder.setPrefHeight(Region.USE_COMPUTED_SIZE);
					listdisesOfSpecificOrder.setMaxHeight(Region.USE_PREF_SIZE);

					v.getChildren().add(listdisesOfSpecificOrder);
					v.setAlignment(Pos.TOP_CENTER);
					v.setSpacing(5);
					paneVBox.setSpacing(10);

					paneVBox.getChildren().add(v);
				}
			}

		} else {
			paneVBox.getChildren().add(new Label("No rusults foubd"));
		}

	}

	public void removeOrder(ActionEvent event) {
		for (Order o : selectedOrders) {
			Alert alert = new Alert(AlertType.INFORMATION);

			if ((o.getDelivery().isDelivered() == true) || (!Restaurant.getInstance().removeOrder(o))) {
				alert.setTitle("Warning");
				alert.setContentText("Could not remove order " + o.toString());
				alert.showAndWait();
			}
		}
		if (selectedOrders.size() > 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("Removed Successfully " + selectedOrders.size() + " orders");
			alert.showAndWait();
		}
		setScene();

	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		setScene();
	}

	public void switchToScenceCustomerView(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}
}
