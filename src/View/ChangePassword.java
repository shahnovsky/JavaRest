package View;

import java.io.IOException;
import java.util.Optional;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangePassword  {

	@FXML
	private PasswordField oldP;

	@FXML
	private PasswordField newP;

	@FXML
	private PasswordField confirmP;
	@FXML
	private Text fillAllFields;
	private String realPass;
	private static Customer u;

	
	public static void setCustomer( Customer u) {
		ChangePassword.u = u;
	}
	
	//Check if old pass is correct and if new is identical to the one typed again
	public void checkPassword(ActionEvent event) throws IOException {
		realPass = u.getPassword();

		if (oldP.getText().equals(realPass) && newP.getText().equals(confirmP.getText()) && !newP.getText().isBlank()
				&& !confirmP.getText().isBlank()) {
			u.setPassword(newP.getText());
			u.setItFirstLogIn(false);

			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("OK");
			alert1.setContentText("password changed succesfully!");
			Optional<ButtonType> result1 = alert1.showAndWait();
			if (result1.get() == ButtonType.OK) {
				//SwitchToScence.SwitchScences("CustomerView", stage, this);
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				stage.close();
			}

		} else if (oldP.getText().isEmpty() || newP.getText().isEmpty() || confirmP.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("fill missing password");
			alert.showAndWait();

		} else if (!oldP.getText().equals(u.getPassword())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("wrong password");
			alert.showAndWait();

		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("passwords don't match");
			alert.showAndWait();
		}
	}

}
