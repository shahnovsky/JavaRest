package View;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

//Queries menu
public class QueriesHome {
	
	@FXML
	private Button submit;
    @FXML
    private Button back;

    @FXML
    private Button q1;

    @FXML
    private Button q2;

    @FXML
    private Button q3;

    @FXML
    private Button q4;

    @FXML
    private Button q5;

    @FXML
    private Button q6;
   


	public void switchToScenceQ1(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.MoveToScene("Q1", this, stage.getScene(), stage);	 
	}
	
	public void switchToScenceQ2(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.MoveToScene("Q2", this, stage.getScene(), stage);	 
	}
	public void switchToScenceQ3(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.MoveToScene("Q3", this, stage.getScene(), stage);	 
	}
	public void switchToScencePopularComponents(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.MoveToScene("PopularComponents", this, stage.getScene(), stage);
	}
	public void switchToScenceQ5(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();	 
		 SceneManager.MoveToScene("Q5", this, stage.getScene(), stage);
	}
	public void switchToScenceProfitRelation(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.MoveToScene("ProfitRelation", this, stage.getScene(), stage);	 
	}
	public void switchToScenceMain(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.GoBack(stage);
	}
	public void switchToScenceCookByExpertise(ActionEvent event) throws IOException {
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 SceneManager.MoveToScene("CookByExpertise", this, stage.getScene(), stage);
	}


}
