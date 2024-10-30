package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Model.Dish;
import Model.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProfitRelation implements Initializable{

    @FXML
    private TableView<Dish> profitRationTable;

    @FXML
    private TableColumn<Dish, String> dishCol;

    @FXML
    private TableColumn<Dish, Double> priceCol;

    @FXML
    private TableColumn<Dish, Integer> timeCol;
    private ObservableList<Dish> list;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		list = FXCollections.observableArrayList(Restaurant.getInstance().getProfitRelation());
		
		dishCol.setCellValueFactory(new PropertyValueFactory<Dish,String>("dishName"));
		dishCol.setCellFactory(param -> {
	        return new TableCell<Dish, String>() {
	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);

	                if (item == null || empty) {
	                    setText(null);
	                    setStyle("");
	                } else {
	                    Text text = new Text(item);
	                    text.setStyle("-fx-text-alignment:justify;");                      
	                    text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(35));
	                    setGraphic(text);
	                }
	            }
	        };
	    });
		priceCol.setCellValueFactory(new PropertyValueFactory<Dish,Double>("price"));
		priceCol.setCellFactory(param -> {
	        return new TableCell<Dish, Double>() {
	            @Override
	            protected void updateItem(Double item, boolean empty) {
	                super.updateItem(item, empty);

	                if (item == null || empty) {
	                    setText(null);
	                    setStyle("");
	                } else {
	                	Math.round(item);
	                	
	                    Text text = new Text(round(item,1)+"");
	                    text.setStyle("-fx-text-alignment:justify;");                      
	                    text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(35));
	                    setGraphic(text);
	                }
	            }
	        };
	    });
		
		timeCol.setCellValueFactory(new PropertyValueFactory<Dish,Integer>("timeToMake"));
		profitRationTable.setItems(list);


	}
	public void switchToScenceQueriesHome(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.GoBack(stage);	 
	}
	public static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}

}
