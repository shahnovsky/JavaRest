package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import Model.Component;
import Model.Customer;
import Model.Dish;
import Model.Restaurant;
import Utils.DishType;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ShoppingCart implements Initializable {
	private Customer customer;
	@FXML
	private GridPane shopCartGrid;

	@FXML
	private GridPane StartersGridPane;
	@FXML
	private GridPane MainsGridPane;
	@FXML
	private GridPane DessertsGridPane;
	@FXML
	private Button back;
	@FXML
	private Button add;
	@FXML
	private Button proceed;
	@FXML
	private Label name;
	@FXML
	private Label cartUpdated;
	@FXML
	private ScrollPane scrollOfComponents;
	@FXML
	private VBox vbox;
	@FXML
	private Hyperlink link;
	@FXML
	private Button addDishButton;
	@FXML
	private Spinner<Integer> spinner;
	private MyListener myListener;
	private Stage stage;
	private int count = 1;

	private Collection<Dish> currentDishes = Restaurant.getInstance().getDishes().values();
	private ArrayList<Dish> dishes;
	public static final String CURRENCY = "$";

	private HashMap<Dish, Integer> ordersHm;
	private Dish dish;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		updateView();
	}

	public void updateView() {
		proceed.setDisable(true);

		scrollOfComponents.setStyle("-fx-background-color:transparent;");
		dishes = new ArrayList<Dish>();

		SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
		value.setValue(count);
		spinner.setValueFactory(value);

		for (DishType dt : DishType.values()) {
			dishes.clear();
			for (Dish d : currentDishes) {
				if (d.getType().equals(dt)) {
					dishes.add(d);

				}
			}
			setMenuByType(dishes, dt);
		}
		name.setWrapText(true);
		if (dishes.size() != 0) {
			name.setText(dishes.get(0).getDishName());
			addToVboxComponents(dishes.get(0));
			setDish(dishes.get(0));
			
			name.setVisible(true);
			link.setVisible(true);
			spinner.setVisible(true);
			addDishButton.setVisible(true);
		} else {
			name.setVisible(false);
			link.setVisible(false);
			spinner.setVisible(false);
			addDishButton.setVisible(false);
		}
	}
	//Add dish to shopping list
	public void addDish(ActionEvent e) {
		Dish d = (Dish) name.getUserData();
		ordersHm.put(d, count);
		proceed.setDisable(false);

		cartUpdated.setText("Dish " + d.getDishName() + " was updated in the shopping cart");
		cartUpdated.setVisible(true);

		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> cartUpdated.setVisible(false));
		visiblePause.play();
	}

	//Displat carte
	public void setMenuByType(ArrayList<Dish> dishes, DishType dt) {
		ordersHm = new HashMap<Dish, Integer>();
		myListener = new MyListener() {

			@Override
			public void onClickListener(Dish dish) {
				vbox.getChildren().clear();
				setDish(dish);

				ordersHm.put(dish, 1);
				name.setText(dish.getDishName());
				name.setUserData(dish);
				addToVboxComponents(dish);
				
				name.setVisible(true);
				link.setVisible(true);
				spinner.setVisible(true);
				addDishButton.setVisible(true);

			}

		};
		spinner.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				count = spinner.getValue();

			}
		});

		int column = 0;
		int row = 0;
		try {
			for (int i = 0; i < dishes.size(); i++) {
				FXMLLoader fxmlloader = new FXMLLoader();
				fxmlloader.setLocation(getClass().getResource("ShopCartItem.fxml"));
				AnchorPane anchorPane = fxmlloader.load();

				ShopCartItem shopCartItem = fxmlloader.getController();
				shopCartItem.setData(dishes.get(i), myListener);

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
				GridPane.setMargin(anchorPane, new Insets(2));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void goBack(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void switchToCheckOut(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		CheckOutShoppingCart secController = SceneManager.MoveToScene("CheckOutShoppingCart", this, stage.getScene(),
				stage);
		secController.setList(this.ordersHm);
		secController.setCustomer(this.customer);
		secController.display();
	}
	
	//Display chosen component
	public void addToVboxComponents(Dish d) {
		for (Component c : d.getComponenets()) {
			Label comp = new Label();
			HBox hBox = new HBox();
			hBox.getChildren().add(comp);
			hBox.setAlignment(Pos.CENTER);
			comp.setText(c.getComponentName());
			vbox.getChildren().add(hBox);
			vbox.setSpacing(5);

		}
	}
	//Create new dish by customer
	public void pressHyperlink(ActionEvent e) throws IOException {
		AddNewDish.setDish(dish);
		SceneManager.OpenNewStage("AddNewDish", this);
		AddNewDish.setDish(null);
		updateView();

	}

	public void setDish(Dish d) {
		this.dish = d;
	}

}
