package View;

import java.io.IOException;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Class to navigate between classes of specific customer
public class CustomerView {
	@FXML
	private Button menu;

	@FXML
	private Button getRelevantDish;

	@FXML
	private Button getCookByExpert;

	@FXML
	private Button getPopularComponent;

	@FXML
	private Button logOff;

	@FXML
	private Button updatePersonalData;

	@FXML
	private Hyperlink link;

	@FXML
	private Button shoppingCart;

	@FXML
	private Button orders;
	@FXML
	private Text customerName;
	@FXML
	private Label label;
	public Customer customer;

	public void switchToScenceLogin(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("LogIn", this, stage.getScene(), stage);
	}

	public void switchToScenceShopCart(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ShoppingCart secController = SceneManager.MoveToScene("ShopCart", this, stage.getScene(), stage);
		secController.setCustomer(this.customer);
	}

	public void switchToScenceDisplayOrders(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		DisplayOrders secController = SceneManager.MoveToScene("DisplayOrders", this, stage.getScene(), stage);
		secController.setCustomer(this.customer);
	}

	public void switchToScenceMenu(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Menu menuController = SceneManager.MoveToScene("Menu", this, stage.getScene(), stage);
		menuController.setCustomerAndRefreshTable(this.customer);
	}

	public void switchToScenceRelevantDish(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		RelevantDish secController = SceneManager.MoveToScene("RelevantDish", this, stage.getScene(), stage);
		secController.setCustomer(this.customer);
		secController.display();
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		customerName.setText(customer.getFirstName());
	}

	public void switchToScencePopularComponents(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("PopularComponents", this, stage.getScene(), stage);
	}

	public void switchToScenceEditPersonalInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AddCustomer secController = SceneManager.MoveToScene("AddCustomer", this, stage.getScene(), stage);
		secController.setCustomer(customer);
	}

	public void switchToScenceCookByExpertise(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("CookByExpertise", this, stage.getScene(), stage);
	}

}
