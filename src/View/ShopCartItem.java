package View;


import Model.Dish;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
//Choose dish from shopping cart
public class ShopCartItem {

	@FXML
    private Label dishName;
	
	@FXML
	private Label price;
	
    private Dish dish;
    private MyListener myListener;
    

    public void click(MouseEvent event) {
    	myListener.onClickListener(dish);
    }
    public void setData(Dish dish, MyListener myListener) {
    	this.dish = dish;
    	dishName.setText(dish.getDishName());
    	price.setText(ProfitRelation.round( dish.getPrice(), 1)+"");
    	this.myListener =  myListener;	
    }
    
    




}
