package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import javafx.scene.Node;
import Model.Component;
import Model.Dish;
import Model.Restaurant;
import Utils.DishType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AddNewDish implements Initializable {
	@FXML
	private TextField dishName;

	@FXML
	private ChoiceBox<DishType> dishTypeList;

	@FXML
	private Button calculateDishPrice;

	@FXML
	private Label answerDishPrice;

	@FXML
	private TextField preptime;

	@FXML
	private Button addToDish;

	@FXML
	private TableView<Map.Entry<Component, Integer>> table;

	@FXML
	private TableColumn<Map.Entry<Component, Integer>, String> compName;

	@FXML
	private TableColumn<Map.Entry<Component, Integer>, String> qtyInDish;

	@FXML
	private Button createNewDish;

	@FXML
	private GridPane componentsGrid;
	@FXML
	private Button removeComponent;
	@FXML
	private Button back;
	
	private ArrayList<Component> list;
	private int column;
	private int row;
	private HashMap<Component, Integer> listofNewDish;
	private ObservableList<Map.Entry<Component, Integer>> items;
	private Dish newDish;
	private static Dish dish;
	private boolean flag = false;
	private Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// populate dish type selection
		dishTypeList.getItems().addAll(DishType.values());

		// populate components
		list = new ArrayList<Component>();
		listofNewDish = new HashMap<Component, Integer>();

		list.addAll(Restaurant.getInstance().getComponenets().values());
		ColumnConstraints colConstraints = new ColumnConstraints();
		colConstraints.setHgrow(Priority.SOMETIMES);
		componentsGrid.getColumnConstraints().add(colConstraints);

		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setVgrow(Priority.SOMETIMES);
		componentsGrid.getRowConstraints().add(rowConstraints);

		for (int i = 0; i < list.size(); i++) {

			AnchorPane anchorPane = new AnchorPane();

			Label text = new Label(list.get(i).getComponentName());
			anchorPane.getChildren().add(text);
			anchorPane.setUserData(list.get(i));

			if (column == 3) {
				column = 0;
				row++;
			}

			anchorPane.setOnMousePressed(e -> {
				anchorPane.setStyle("-fx-background-color:#FFC0CB;");
				int count = 0;
				if (listofNewDish.containsKey((Component) anchorPane.getUserData())) {
					count = listofNewDish.get((Component) anchorPane.getUserData());
				}

				listofNewDish.put((Component) anchorPane.getUserData(), count + 1);
			});

			componentsGrid.add(anchorPane, column++, row);
			componentsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
			componentsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
			componentsGrid.setMaxWidth(Region.USE_PREF_SIZE);

			GridPane.setMargin(anchorPane, new Insets(8));

		}
		if (AddNewDish.dish != null)
			setDetails();
	}
	//After user selected components from gridpane, they shall be added to tableView
	public void setListOndisplay() {
		compName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Component, Integer>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Component, Integer>, String> p) {

						return new SimpleStringProperty(p.getValue().getKey().getComponentName().toString());
					}
				});

		qtyInDish.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Component, Integer>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Component, Integer>, String> p) {
						// for second column use value
						return new SimpleStringProperty(p.getValue().getValue().toString());
					}
				});

		qtyInDish.setOnEditCommit(new EventHandler<CellEditEvent<Map.Entry<Component, Integer>, String>>() {
			@Override
			public void handle(CellEditEvent<Entry<Component, Integer>, String> event) {
				// TODO Auto-generated method stub
				Entry<Component, Integer> count = event.getRowValue();
				listofNewDish.put(count.getKey(), Integer.parseInt(event.getNewValue()));
			}
		});
		qtyInDish.setCellFactory(TextFieldTableCell.forTableColumn());
		
		items = FXCollections.observableArrayList(listofNewDish.entrySet());
		table.setItems(items);
		table.setEditable(true);
	}

	public void createNewDish() {
		if (dishName.getText().isBlank() == true) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("You must enter dish name");
			alert.showAndWait();
		} else if (listofNewDish.values().contains(0)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("Change component quantity to other value then 0.");
			alert.showAndWait();
		}
		else if (dishTypeList.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("You must select dish type");
			alert.showAndWait();
		} else if (preptime.getText().isBlank() || !preptime.getText().matches("[0-9]+") || preptime.getText().equals("0")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("You must enter preperation time using integers (without 0)");
			alert.showAndWait();
		} else if (items == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("You must select at least one component");
			alert.showAndWait();
		} else if (!dishName.getText().isBlank()) {
			flag = false;
				for (Dish d : Restaurant.getInstance().getDishes().values()) {
					if (d.getDishName().equals(dishName.getText())) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("Name is already taken");
						alert.showAndWait();
						flag = true;
						break;
						
					}
				}
				if (flag == false) {
					newDish = new Dish(dishName.getText(), dishTypeList.getValue(),
							convertHashMapToArrayList(listofNewDish), Integer.parseInt(preptime.getText()));
					if (false == Restaurant.getInstance().addDish(newDish)) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setContentText("Dish " + newDish.getDishName() + " was not added to the restaurant");
						alert.showAndWait();
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Congratulations");
						alert.setContentText("Dish " + newDish.getDishName() + " succesfully created. Price is: "
								+ ProfitRelation.round(newDish.calcDishPrice(), 1));
						alert.showAndWait();
					}

					// clear values from scene
					dishName.setText("");
					dishTypeList.getSelectionModel().clearSelection();
					preptime.setText("");
					componentsGrid.getChildren().stream().forEach(p -> p.setStyle("-fx-background-color:#00000000;"));
					items.clear();
					listofNewDish.clear();
				}
		}
		
	}

	public void removeComponent(ActionEvent e) {
		if (table.getSelectionModel() .getSelectedItem() ==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("Please select component to remove");
			alert.showAndWait();
		}
		else {
		Component deletedC = table.getSelectionModel().getSelectedItems().get(0).getKey();
		for (Node temp : componentsGrid.getChildren()) {
			if (temp.getUserData() == deletedC)
				temp.setStyle("-fx-background-color:#00000000;");
		}
		table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
		listofNewDish.remove(deletedC);
	}
	}

	public static ArrayList<Component> convertHashMapToArrayList(HashMap<Component, Integer> hm) {
		ArrayList<Component> arr = new ArrayList<Component>();
		for (Entry<Component, Integer> entry : hm.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				arr.add(entry.getKey());
			}
		}
		return arr;
	}
	public static void setDish(Dish dish) {
		AddNewDish.dish = dish;
		
	}
	public void setDetails() {
		back.setVisible(false);
		dishName.setText(dish.getDishName());
		dishTypeList.getSelectionModel().select(dish.getType());
		dishTypeList.setDisable(true);
		preptime.setText( Integer.toString(dish.getTimeToMake()));
		preptime.setDisable(true);
		listofNewDish = EditDish.fromArrayListToHashMap(dish.getComponenets());
		setListOndisplay();
		
	}
	public void switchToScenceBack(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}
}
