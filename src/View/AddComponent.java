package View;

import java.io.IOException;

import Exceptions.BlankInputException;
import Exceptions.InputStringOnlyException;
import Model.Component;
import Model.Restaurant;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddComponent {

    @FXML
    private TextField compName;

    @FXML
    private CheckBox dairyCB;

    @FXML
    private CheckBox glutenCB;

    @FXML
    private TextField price;
    @FXML
    private Button addComponent;
    
    private ObservableList<Component> compList = null;

	private Stage stage;
    
	//Add Component to restaurant
    public void addComponent(ActionEvent event) {
    	try{
    		if (!AddComponent.checkOnlyString(compName.getText()))  
        		throw new InputStringOnlyException();
    		
    		else if(compName.getText().isBlank()) {
    			throw new BlankInputException();
    		}
    		if (Restaurant.getInstance().getComponenets().values().stream().anyMatch(x -> x.getComponentName().equals(compName.getText()))){
    			Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Warning");
				alert.setContentText("Name is already taken");
				alert.showAndWait();
    		}
    		else if (!price.getText().matches("^[0-9]\\d*(\\.\\d+)?$")  ){
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Warning");
    			alert.setContentText("Input must contain digits and \".\" char only");
    			alert.showAndWait();
    		}
    		else {
    			Component c = new Component(compName.getText(),dairyCB.isSelected(), glutenCB.isSelected(), Double.parseDouble(price.getText()));
    			if (Restaurant.getInstance().addComponent(c)) {
    				if (this.compList != null)
    					this.compList.add(c);
    				Alert alert = new Alert(AlertType.CONFIRMATION);
        			alert.setTitle("New Component");
        			alert.setContentText(c.getComponentName() + " has been created");
        			alert.showAndWait();
        			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        			 SceneManager.GoBack(stage);
    			}
    			else {
    				Alert alert = new Alert(AlertType.ERROR);
        			alert.setTitle("New Component");
        			alert.setContentText("Creation failed");
        			alert.showAndWait();
    			}
    		}

    	}catch(InputStringOnlyException e) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning");
			alert.setContentText(e.toString());
			alert.showAndWait();
    	}
    	catch(BlankInputException e) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning");
			alert.setContentText(e.toString());
			alert.showAndWait();
    	}	
    }
    
    //Check if String contains only alphabethe
    public static boolean checkOnlyString(String s) {
    	 int len = s.length();
         for (int i = 0; i < len; i++) {
            // checks whether the character is not a letter
            // if it is not a letter ,it will return false
            if ((Character.isLetter(s.charAt(i)) == false)) {
               return false;
            }
         }
         return true;
    }
    
	public void switchToScenceBack(ActionEvent event) throws IOException {
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.GoBack(stage);		 
	}
	
	public void setScreenComponentsList(ObservableList<Component> compList) {
		this.compList = compList;
	}
}
