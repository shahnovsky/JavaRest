package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Model.DeliveryArea;
import Model.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RemovingArea implements Initializable {
	private static DeliveryArea area;
	@FXML
	private ComboBox<String> areas;

	private ArrayList<String> arr;
	private String getArea;
	private ObservableList<String> items;

	@FXML
	private Button removeAreaButton;
	private static DeliveryArea da;
	private String getAr;
	private Stage stage;

	public void switchToScenceBack(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}

	public static void setArea(DeliveryArea area) {
		RemovingArea.area = area;
	}

	// Remove area from restaurant
	public void removeArea(ActionEvent event) {

		getAr = areas.getValue().replaceAll(".*ID=", "");

		if (Restaurant.getInstance().removeDeliveryArea(area,
				Restaurant.getInstance().getAreas().get(Integer.parseInt(getAr)))) {
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Areas and neighberhoods");
			alert.setContentText("Area removed.");
			alert.showAndWait();
			stage.close();

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Areas and neighberhoods");
			alert.setContentText("Area removal failed.");
			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		removeAreaButton.setDisable(true);
		arr = new ArrayList<String>();
		for (DeliveryArea da : Restaurant.getInstance().getAreas().values()) {
			if (!da.equals(RemovingArea.area)) {
				arr.add(da.getAreaName() + ", ID=" + da.getId());
			}
		}

		items = FXCollections.observableArrayList();
		items.addAll(arr);
		areas.getItems().addAll(arr);
		areas.setOnAction(event -> {
			if (!areas.getSelectionModel().isEmpty()) {
				removeAreaButton.setDisable(false);
			}
		});
	}

}
