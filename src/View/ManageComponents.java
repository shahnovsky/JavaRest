package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Model.Component;
import Model.Restaurant;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ManageComponents implements Initializable {
	@FXML
	private TableView<Component> compData;

	@FXML
	private TableColumn<Component, Integer> idCol;

	@FXML
	private TableColumn<Component, String> nameCol;

	@FXML
	private TableColumn<Component, Boolean> dairyCol;

	@FXML
	private TableColumn<Component, Boolean> glutenCol;

	@FXML
	private TableColumn<Component, Double> priceCol;

	@FXML
	private Button removeButton;

	private Stage stage;
	private ObservableList<Component> compList;

	public void goBack(ActionEvent event) {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}

	public void initialize(URL location, ResourceBundle resources) {
		this.fillMainOdersTable();
	}

	private void fillMainOdersTable() {
		removeButton.setDisable(true);

		this.compList = FXCollections.observableArrayList(Restaurant.getInstance().getComponenets().values());

		// Columns factories
		idCol.setCellValueFactory(new PropertyValueFactory<Component, Integer>("id"));
		nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getComponentName()));
		dairyCol.setCellValueFactory(new PropertyValueFactory<Component, Boolean>("hasLactose"));
		glutenCol.setCellValueFactory(new PropertyValueFactory<Component, Boolean>("hasGluten"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("price"));
		compData.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				Component thisComp = compData.getSelectionModel().getSelectedItem();
				if (thisComp != null) {
					removeButton.setDisable(false);
				}
			}
		});
		compData.setItems(this.compList);
		compData.refresh();

	}

	// Remove component
	public void removeComp(ActionEvent event) {
		Component thisComp = compData.getSelectionModel().getSelectedItem();
		if (thisComp != null) {
			removeButton.setDisable(false);
			if (Restaurant.getInstance().removeComponent(thisComp)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Component manager");
				alert.setContentText("Component " + thisComp.getComponentName() + " was removed.");
				alert.showAndWait();
				compData.getItems().removeAll(compData.getSelectionModel().getSelectedItems());
				compData.refresh();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Component manager");
				alert.setContentText("Action failed. Component " + thisComp.getComponentName() + " was not removed.");
				alert.showAndWait();
			}
		}
	}

	public void switchToScenceAddComponent(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AddComponent addCompCont = SceneManager.MoveToScene("AddComponent", this, stage.getScene(), stage);
		addCompCont.setScreenComponentsList(compList);
	}


}
