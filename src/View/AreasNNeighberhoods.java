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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AreasNNeighberhoods implements Initializable {
	@FXML
	private ComboBox<String> area;

	@FXML
	private ArrayList<String> arr;
	private String getArea;
	private ObservableList<String> items;
	private Stage stage;

	@FXML
	private Button removeArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getArea = null;
		refreshAreaList();
	}

	// Upon update data, refresh table
	public void refreshAreaList() {
		arr = new ArrayList<String>();

		for (DeliveryArea da : Restaurant.getInstance().getAreas().values()) {
			arr.add(da.getAreaName() + ", ID=" + da.getId());
		}
		items = FXCollections.observableArrayList(arr);
		area.setItems(items);
		area.setOnAction(this::getNeib);
	}

	// Get selected neighborhood ID
	public void getNeib(ActionEvent event) {
		if (area.getValue() == null)
			getArea = null;
		else
			getArea = area.getValue().replaceAll(".*ID=", "");
	}

	@FXML
	void onEdit(ActionEvent event) throws IOException {
		if (area.getSelectionModel().isEmpty()) {
			return;
		}

		DeliveryArea da = Restaurant.getInstance().getAreas()
				.get(Integer.parseInt(area.getValue().replaceAll(".*ID=", "")));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ManageAreas maCont = SceneManager.MoveToScene("ManageAreas", this, stage.getScene(), stage);
		maCont.setArea(da);

	}

	public void switchToScenceBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}

	public void removeArea(ActionEvent event) throws IOException {
		if (!area.getSelectionModel().isEmpty()) {
			Integer getArea = Integer.parseInt(area.getValue().replaceAll(".*ID=", ""));
			DeliveryArea da = Restaurant.getInstance().getAreas().get(getArea);
			RemovingArea.setArea(da);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			SceneManager.OpenNewStage("RemovingArea", this);
			refreshAreaList();

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Areas and neighberhoods");
			alert.setContentText("Please select an area to remove");
			alert.showAndWait();
		}
	}

}
