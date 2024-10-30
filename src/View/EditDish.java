package View;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import Exceptions.NoComponentsExceptions;
import Model.Component;
import Model.Dish;
import Model.Restaurant;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EditDish implements Initializable {
	@FXML
	private ComboBox<String> dishList;

	@FXML
	private Button back;

	@FXML
	private Button removeComp;

	@FXML
	private TableView<Map.Entry<Component, Integer>> myTable;

	@FXML
	private TableColumn<Map.Entry<Component, Integer>, String> column1;

	@FXML
	private TableColumn<Map.Entry<Component, Integer>, String> column2;

	@FXML
	private ComboBox<Integer> qtyList;

	@FXML
	private Button addComp;
	@FXML
	private ComboBox<String> compList;

	private Stage stage;
	private HashMap<Component, Integer> hmCompCurrent;
	// HM of components from restaurant
	private HashMap<String, Component> hmComponents;
	// HM of dishes from restaurant
	private HashMap<String, Dish> hmDishes;
	private HashMap<Integer, Dish> dishes;
	private Dish currentDish;
	private ObservableList<Map.Entry<Component, Integer>> items;
	private Component currentComp;
	private Integer qty;
	private static HashMap<Component, Integer> convertedhmfromList;
    private ObservableList<String> itemsDishes;
    private ObservableList<String> numbers;
    
    private EditDishPostCallback editDishPostCallback = null;


    //Class to edit dish: remove and edit components
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		removeComp.setWrapText(true);
		hmComponents = new HashMap<String, Component>();
		hmDishes = new HashMap<String, Dish>();
		for (Dish d : Restaurant.getInstance().getDishes().values()) {
			hmDishes.put(d.getDishName(), d);
		}
		for (Component c : Restaurant.getInstance().getComponenets().values()) {
			hmComponents.put(c.getComponentName(), c);
		}

		compList.getItems().addAll(hmComponents.keySet());
		qtyList.getItems().addAll(1, 2, 3, 4, 5);
		itemsDishes = FXCollections.observableArrayList(hmDishes.keySet());
		dishList.setItems(itemsDishes);
		dishList.setOnAction(this::setTableForSelectedDish);
	}
	//Present on list components of the dish
	public void setTablePredefinedDish(Dish dish) {
		this.currentDish = dish;
		dishList.setValue(dish.getDishName());
		refreshDataAboutDish();
	}
	
	public void setCallbackAfterDishEdit(EditDishPostCallback callback) {
		this.editDishPostCallback = callback;
	}
	//Upon list of components display refresh table and dish to apply changes
	public void refreshDataAboutDish() {
		hmCompCurrent.clear();
		for (Component comp : currentDish.getComponenets()) {
			int count = 0;
			if (hmCompCurrent.containsKey(comp)) {
				count = hmCompCurrent.get(comp);
			}
			hmCompCurrent.put(comp, count + 1);
		}

		column1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Component, Integer>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Component, Integer>, String> p) {

						return new SimpleStringProperty(p.getValue().getKey().getComponentName());
					}
				});
		numbers = FXCollections.observableArrayList("1","2","3","4","5","6","7");
		column2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Component, Integer>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Component, Integer>, String> p) {
						// for second column we use value
						return new SimpleStringProperty(p.getValue().getValue().toString());
					}
				});

		column2.setCellFactory(ComboBoxTableCell.forTableColumn(numbers));
		column2.setOnEditCommit(new EventHandler<CellEditEvent<Map.Entry<Component, Integer>, String>>() {

			@Override
			public void handle(CellEditEvent<Entry<Component, Integer>, String> event) {
				// TODO Auto-generated method stub
				Entry<Component, Integer> count = event.getRowValue();
				
				hmCompCurrent.put(count.getKey(), Integer.parseInt(event.getNewValue()));
				currentDish = hmDishes.get(dishList.getSelectionModel().getSelectedItem());

				updateQtyInOriginalDish();
				displaySavedChange();
				refreshDataAboutDish();
				
				
			}
		});

		items = FXCollections.observableArrayList(hmCompCurrent.entrySet());
		myTable.setItems(items);
		myTable.setEditable(true);
		if (this.editDishPostCallback != null)
			this.editDishPostCallback.onCallback();
	}
	//Dispaly componentsof current dish
	public void setTableForSelectedDish(ActionEvent event) {
		String value = dishList.getValue();
		hmCompCurrent = new HashMap<Component, Integer>();
		dishes = Restaurant.getInstance().getDishes();
		for (Dish d : dishes.values()) {
			if (!d.getDishName().equals(value))
				continue;
			this.currentDish = d;
			break;
		}
		refreshDataAboutDish();
	}

	public void addComponentToDish(ActionEvent event) throws IOException {
		if (!getDishnComponentnQty()) {
			return;
		} else {
			if (hmCompCurrent.containsKey(currentComp)) {
				qty = qty + hmCompCurrent.get(currentComp);
			} else
			{
				Map.Entry<Component, Integer> entry = new AbstractMap.SimpleEntry<Component, Integer>(currentComp, qty);
				myTable.getItems().add(entry);
				myTable.refresh();
				items = FXCollections.observableArrayList(hmCompCurrent.entrySet());
			}
			hmCompCurrent.put(currentComp, qty);
			getDishnComponentnQty();
			updateQtyInOriginalDish();
			displaySavedChange();
			refreshDataAboutDish();
		}
	}
	//Get new quantities 
	public boolean getDishnComponentnQty() {
		if (dishList.getSelectionModel().getSelectedItem() == null
				|| compList.getSelectionModel().getSelectedItem() == null
				|| qtyList.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Can't edit dish");
			alert.setContentText(
					"Please make sure you selected dish, component and quantity to update. Then try again.");
			alert.showAndWait();
			return false;
		} else {
			// selected dish in scene
			currentDish = hmDishes.get(dishList.getSelectionModel().getSelectedItem());
			// select component from list
			currentComp = hmComponents.get(compList.getSelectionModel().getSelectedItem());
			// selected quantity to add
			qty = qtyList.getSelectionModel().getSelectedItem();
			return true;
		}

	}
	//Message upon completion
	public void displaySavedChange() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Change saved");
		alert.setContentText("The change you applied has been saved to the dish.");
		alert.showAndWait();
	}
	//Method to convert HashMap to ArrayList
	public static HashMap<Component, Integer> fromArrayListToHashMap(List<Component> comps){
		convertedhmfromList = new HashMap<Component, Integer>();
		for (Component c : comps) {
			if (convertedhmfromList.containsKey(c)) {
				convertedhmfromList.put(c, convertedhmfromList.get(c) + 1);
			} else {
				convertedhmfromList.put(c, 1);
			}
		}
		return convertedhmfromList;
	}
	
	//Update Restaurant
	public void updateQtyInOriginalDish() {
		// update dish
		// convert ArrayList of component from dish to HashMap
		HashMap<Component, Integer> currentDishCount = new HashMap<Component, Integer>();
		currentDishCount = fromArrayListToHashMap(currentDish.getComponenets());
		
		// compare HashMap of dish and the updated one
		for (Component c : hmCompCurrent.keySet()) {
			if (!currentDishCount.containsKey(c)) {
				for (int i = 0; i < hmCompCurrent.get(c); i++) {
					currentDish.addComponent(c);
				}
			} else if (currentDishCount.get(c) != hmCompCurrent.get(c)) {
				int delta = hmCompCurrent.get(c) - currentDishCount.get(c);
				if (delta > 0) {
					for (int i = 0; i < delta; i++) {
						currentDish.addComponent(c);
					}
				} else {
					for (int i = 0; i < delta*-1; i++) {
						try {
							currentDish.removeComponent(c);
						} catch (NoComponentsExceptions e) {
							// TODO Auto-generated catch block
							dishList.getSelectionModel().clearSelection();
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Edit dish");
							alert.setContentText("This dish "+ currentDish.getDishName() +" does not include components.\nDish is removed.");
							alert.showAndWait();
						}
					}
				}
			}
		}
		currentDish.setPrice(currentDish.calcDishPrice());
	}
	//Go back to previous stage
	public void switchToScenceManagerView(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}

	// update dish when deleting component
	public void deleteRow(ActionEvent event) {
		if (dishList.getSelectionModel().getSelectedItem() != null) {
			hmCompCurrent.remove(myTable.getSelectionModel().getSelectedItem().getKey());
			System.out.println("current hm " + hmCompCurrent);
			HashMap<Component, Integer> currentDishCount = new HashMap<Component, Integer>();
			currentDishCount = fromArrayListToHashMap(currentDish.getComponenets());
			System.out.println("original dish before" + currentDishCount);
			
			for (Component c : currentDishCount.keySet()) {
				
				if ( !hmCompCurrent.containsKey(c)) {
					while(currentDish.getComponenets().contains(c) ) {
						try {
						Restaurant.getInstance().getDishes().get(currentDish.getId()).removeComponent(c);
						}
						catch (NoComponentsExceptions e){
							dishList.getSelectionModel().clearSelection();
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Edit dish");
							alert.setContentText(e.getMessage() +"\nDish is removed.");
							alert.showAndWait();
						}
					}
				}
			}
			
			myTable.getItems().removeAll(myTable.getSelectionModel().getSelectedItems());
			myTable.refresh();
			refreshDataAboutDish();
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Can't edit dish");
			alert.setContentText("Please make sure you selected dish, then try again");
			alert.showAndWait();
		}
	}

}
