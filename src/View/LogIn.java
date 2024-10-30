package View;

import java.io.IOException;
import java.util.Optional;
import Model.Customer;
import Model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
//First screen to user
public class LogIn {
	
	@FXML
	private Button button;
    
	@FXML
	private Label wrongLogIn;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button register;

	@FXML
	private Text fillAllFields;

	private Scene scene;
	private Stage stage;
	private Parent root;

	public void userLogIn(ActionEvent event) throws IOException {
		checkLogIn(event);
	}

	public void checkLogIn(ActionEvent event) throws IOException {
		
		if (username.getText().toString().equals("m") && password.getText().toString().equals("m")) {
			root = FXMLLoader.load(getClass().getResource("ManagerMainView.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} else if (username.getText().isEmpty() || password.getText().isEmpty()) {
			wrongLogIn.setText("Please enter your data\n");
		}
		else {
			for (Customer c : Restaurant.getInstance().getCustomers().values()) {
				if (username.getText().toString().equals(c.getUserName())
						&& password.getText().toString().equals(c.getPassword())) {
					if (c.isItFirstLogIn() == true) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Warning");
						alert.setContentText("You must change your password!");
						Optional<ButtonType> result = alert.showAndWait();
						
						if (result.get() == ButtonType.OK) {
							password.clear();
							wrongLogIn.setVisible(false);
							ChangePassword.setCustomer(c);
							SceneManager.OpenNewStage("ChangePassword", this);
							break;
						}
					}
					else {
						Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
						CustomerView secController = SceneManager.MoveToScene("CustomerView", this, stage.getScene(), stage);
						secController.setCustomer(c);
					}
				}
				else {
					wrongLogIn.setText("Wrong password or username");
					wrongLogIn.setVisible(true);
				}
			}

		}

	}

	public void SwitchtoSceneAddCustomer(ActionEvent event) throws IOException {
		SceneManager.OpenNewStage("AddCustomer", this);
	}



}
