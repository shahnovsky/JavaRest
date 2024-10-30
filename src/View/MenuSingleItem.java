package View;

import java.util.Arrays;
import java.util.List;
import Model.Component;
import Model.Dish;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class MenuSingleItem{
	@FXML
    private AnchorPane anchorPane;
	
	@FXML
	private Label nameLabel;

	@FXML
	private Label priceLabel;
	
	@FXML
	private FlowPane flowPane;
	
	@FXML
	private List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);
	
	private ContextMenu contextMenu;
	private MenuItem editDishContext;
	private MenuItem deleteDishContext;
	private Dish dish;
	private MyListener onDeleteListener;
	private MyListener onEditListener;

	//Pane of menu item
	public void setData(Dish dish, MyListener onDeleteListener, MyListener onEditListener) {
		this.dish = dish;
		this.onDeleteListener = onDeleteListener;
		this.onEditListener = onEditListener;
		
		if ((this.onDeleteListener != null) && (this.onEditListener != null)) {
			contextMenu = new ContextMenu();
	        editDishContext = new MenuItem("Edit dish");
	        deleteDishContext = new MenuItem("Delete dish");
	        contextMenu.getItems().addAll(editDishContext, deleteDishContext);
	        
	        editDishContext.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                System.out.println("Edit dish pressed");
	                onEditListener.onClickListener(dish);
	            }
	        });
	        
	        deleteDishContext.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                System.out.println("Delete dish pressed");
	                onDeleteListener.onClickListener(dish);
	            }
	        });	
		}
		
		nameLabel.setText(dish.getDishName());
		nameLabel.setStyle("-fx-font-weight: bold");
		priceLabel.setText(ProfitRelation.round(dish.getPrice(), 1) + "");
		for (Component comp : dish.getComponenets()) {
			Label lbl = new Label();
			lbl.setText(comp.getComponentName() + " | ");
			flowPane.getChildren().add(lbl);
		}

	}

	public void click(MouseEvent event) {
		if (contextMenu != null)
			contextMenu.show(anchorPane, event.getScreenX(), event.getScreenY());
	}

	
	

}
