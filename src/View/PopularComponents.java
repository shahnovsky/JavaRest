package View;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import Model.Component;
import Model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Display popular component query
public class PopularComponents implements Initializable{
	private Stage stage;
	
    @FXML
    private Pagination pagination;
    @FXML
    private Button back;
    
    private LinkedList<Component> results;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		results= new LinkedList<Component>(Restaurant.getInstance().getPopularComponents());
		
		if (results.size() == 0)
			return;

		pagination.setPageFactory(img -> {
			Image image = new Image(results.get(img).getImage(),200, 200, false, false);
			ImageView imageView = new ImageView(image);
			Label  lbl = new Label (results.get(img).getComponentName());
			VBox vBox = new VBox(imageView,lbl);
			vBox.setSpacing(5);
			vBox.setAlignment(Pos.CENTER);
			return vBox;	
		});
		pagination.setPageCount(results.size());
	}
	public void switchToScenceQueriesHome(ActionEvent event) throws IOException {
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.GoBack(stage);	 
	}

	
	

}
