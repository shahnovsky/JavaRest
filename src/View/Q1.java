package View;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import Model.Delivery;
import Model.DeliveryPerson;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import Model.Restaurant;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Q1 implements Initializable {
	private static final List<String> months = Arrays.asList("1 - January", "2 - February", "3 - March",
			"4 - April", "5 - May", "6 - June", "7 - July", "8 - August", "9 - September", "10 - October",
			"11 - November", "12 - December");
	private Stage stage;

    @FXML
    private Button OK;

    @FXML
    private ComboBox<String> deliveryPersonList;

    @FXML
    private ComboBox<String> monthList;

    @FXML
    private Button back;
    
    @FXML
    private TableView<Delivery> resultsTable;

    @FXML
    private TableColumn<Delivery, LocalDate> deliveryDay;

    @FXML
    private TableColumn<Delivery, Integer> deliveryID;

    @FXML
    private TableColumn<RegularDelivery, SortedSet<Order>> ordersInDelivery;
    @FXML
    private TableColumn<ExpressDelivery, Order> expressOrders;
    
    private ArrayList<String> arr;
    
    private TreeSet<Delivery> queryResult;
    
    private ObservableList<Delivery> list;
	
	public void initialize(URL location, ResourceBundle resources) {

		monthList.getItems().addAll(months);

		arr = new ArrayList<String>();

		for (DeliveryPerson dp : Restaurant.getInstance().getDeliveryPersons().values()) {
			arr.add(dp.getLastName() + " " + dp.getFirstName()+ ", ID="+ dp.getId() );
		}
		Collections.sort(arr);
		deliveryPersonList.getItems().clear();
		deliveryPersonList.getItems().addAll(arr);
		deliveryPersonList.setVisibleRowCount(5);
		
		
		deliveryDay.setCellValueFactory(new PropertyValueFactory<Delivery,LocalDate>("deliveredDate"));
		deliveryID.setCellValueFactory(new PropertyValueFactory<Delivery,Integer>("id"));
		
		ordersInDelivery.setCellValueFactory(ro -> {
			if ((ro.getValue() instanceof RegularDelivery) && (ro.getValue().getOrders() != null))
				return new SimpleObjectProperty<>(ro.getValue().getOrders());
			else
				return new SimpleObjectProperty<>(null);
			}
		);
		
		expressOrders.setCellValueFactory(eo -> {
		if ((eo.getValue() instanceof ExpressDelivery) && (eo.getValue().getOrder() != null))
			return new SimpleObjectProperty<>(eo.getValue().getOrder());
		else
			return new SimpleObjectProperty<>(null);
		}
	);
		
		OK.setOnAction(this::getRelevantList);
		resultsTable.setVisible(false);
	}
	
	public void getRelevantList(ActionEvent event) {
		String getPerson = deliveryPersonList.getValue();
		if (getPerson == null)
			return;
		getPerson = getPerson.replaceAll(".*ID=", "");
		String getMonth = monthList.getValue();
		getMonth = getMonth.replaceAll(" - .*", "");
		if (getMonth == null)
			return;

		queryResult = Restaurant.getInstance().getDeliveriesByPerson(Restaurant.getInstance().getDeliveryPersons().get(Integer.parseInt(getPerson)), Integer.parseInt(getMonth));
		
		list = FXCollections.observableArrayList(queryResult);
		resultsTable.setItems(list);
		resultsTable.setVisible(true);
	}
	public void switchToScenceQueriesHome(ActionEvent event) throws IOException {
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.GoBack(stage);		 
	}
}


