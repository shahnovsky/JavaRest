package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;
import Model.Customer;
import Model.Dish;
import Model.Restaurant;
import Utils.DishType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Menu implements Initializable {

	@FXML
	private GridPane StartersGridPane;
	@FXML
	private GridPane MainsGridPane;
	@FXML
	private GridPane DessertsGridPane;
	@FXML
	private AnchorPane menuAnchorPane;
	@FXML
	private Button back;
	@FXML
	private Label test;

	private Collection<Dish> currentDishes = Restaurant.getInstance().getDishes().values();
	public static final String CURRENCY = "$";
	private Customer customer = null;

	public void setMenuByType(ArrayList<Dish> dishes, DishType dt) {
		/* Create listener on delete dish on the context menu */
		MyListener onDeleteListener = new MyListener() {
			@Override
			public void onClickListener(Dish dish) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Deleting dish");
				alert.setContentText("Would you like to delete the dish");
				Optional<ButtonType> result = alert.showAndWait();

				if (!result.isPresent())
					return;

				if (result.get() == ButtonType.OK) {
					Restaurant.getInstance().removeDish(dish);
					refreshTable();
				}
			}
		};

		EditDishPostCallback editDishPostCallback = new EditDishPostCallback() {
			@Override
			public void onCallback() {
				refreshTable();

			}
		};

		/* Create listener for edit dish event */
		MyListener onEditListener = new MyListener() {
			@Override
			public void onClickListener(Dish dish) {
				Stage stage = (Stage) menuAnchorPane.getScene().getWindow();
				try {
					EditDish sceController = SceneManager.MoveToScene("EditDish", this, stage.getScene(), stage);
					sceController.setTablePredefinedDish(dish);
					sceController.setCallbackAfterDishEdit(editDishPostCallback);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		/* Build the menu visually */
		int column = 0;
		int row = 0;
		try {
			for (int i = 0; i < dishes.size(); i++) {
				FXMLLoader fxmlloader = new FXMLLoader();
				fxmlloader.setLocation(getClass().getResource("MenuSingleItem.fxml"));
				AnchorPane anchorPane = fxmlloader.load();
				anchorPane.setUserData(dishes.get(i));
				
				MenuSingleItem menuItem = fxmlloader.getController();
				if (this.customer == null) {
					menuItem.setData(dishes.get(i), onDeleteListener, onEditListener);
				} else {
					menuItem.setData(dishes.get(i), null, null);
				}

				if (column == 1) {
					column = 0;
					row++;
				}
				switch (dt) {
				case Starter:

					StartersGridPane.add(anchorPane, column++, row);
					StartersGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
					StartersGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
					StartersGridPane.setMaxWidth(Region.USE_PREF_SIZE);

					StartersGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
					StartersGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
					StartersGridPane.setMaxHeight(Region.USE_PREF_SIZE);

					break;
				case Main:
					MainsGridPane.add(anchorPane, column++, row);
					MainsGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
					MainsGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
					MainsGridPane.setMaxWidth(Region.USE_PREF_SIZE);

					MainsGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
					MainsGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
					MainsGridPane.setMaxHeight(Region.USE_PREF_SIZE);
					break;
				case Dessert:
					DessertsGridPane.add(anchorPane, column++, row);
					DessertsGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
					DessertsGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
					DessertsGridPane.setMaxWidth(Region.USE_PREF_SIZE);

					DessertsGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
					DessertsGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
					DessertsGridPane.setMaxHeight(Region.USE_PREF_SIZE);
					break;
				}
				GridPane.setMargin(anchorPane, new Insets(10));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void refreshTable() {
		StartersGridPane.getChildren().clear();
		MainsGridPane.getChildren().clear();
		DessertsGridPane.getChildren().clear();

		ArrayList<Dish> dishes = new ArrayList<Dish>();
		for (DishType dt : DishType.values()) {
			dishes.clear();
			for (Dish d : currentDishes) {
				if (d.getType().equals(dt)) {
					dishes.add(d);

				}
			}
			setMenuByType(dishes, dt);
		}
	}

	public void switchToScenceManager(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}
	
	public void setCustomerAndRefreshTable(Customer customer) {
		if (customer != null)
			this.customer = customer;
		refreshTable();
	}
}
