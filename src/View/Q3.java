package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Q3 implements Initializable{
	@FXML
	private Text revenue;
	@FXML
	private Button back;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		revenue.setText( String.valueOf(Restaurant.getInstance().revenueFromExpressDeliveries()));
	}
	public void switchToScenceQueriesHome(ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);	 
	}
	
	

}
