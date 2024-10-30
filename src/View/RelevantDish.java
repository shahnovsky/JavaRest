package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Model.Customer;
import Model.Dish;
import Model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RelevantDish implements Initializable {
	@FXML
	private Label customerName;

	@FXML
	private GridPane dishList;
	@FXML
	private Button back;
	@FXML
	private VBox vbox;
	@FXML
	private ScrollPane sp;

	private Customer customer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void display() {
		sp.setStyle("-fx-background-color:transparent;");

		customerName.setText(customer.getFirstName() + " " + customer.getLastName());
		ArrayList<Dish> list = new ArrayList<Dish>();
		list.addAll(Restaurant.getInstance().getReleventDishList(customer));
		for (int i = 0; i < list.size(); i++) {
			Label text = new Label(list.get(i).getDishName());
			vbox.getChildren().add(text);
		}
	}

	public void switchToScenceCustomerView(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
