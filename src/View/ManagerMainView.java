package View;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

//Navigate in manager view
public class ManagerMainView {
	private Stage stage;

	public void switchToScenceLogIn(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("LogIn", this, stage.getScene(), stage);
	}

	public void switchToScenceShopCart(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("ShopCart", this, stage.getScene(), stage);

	}

	public void switchToScenceUpdatePersonData(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("UpdatePersonalData", this, stage.getScene(), stage);
	}

	public void switchToScenceQueries(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("QueriesHome", this, stage.getScene(), stage);

	}

	public void switchToScenceEditDish(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("EditDish", this, stage.getScene(), stage);
	}

	public void switchToScenceMenu(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Menu menuController = SceneManager.MoveToScene("Menu", this, stage.getScene(), stage);
		menuController.setCustomerAndRefreshTable(null);
	}

	public void switchToScenceAddNewDish(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("AddNewDish", this, stage.getScene(), stage);
	}

	public void switchToScenceManagerOrders(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("OrdersManager", this, stage.getScene(), stage);
	}

	public void switchToScenceEditAreas(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("AreasNNeighberhoods", this, stage.getScene(), stage);
	}
	
	public void switchToScenceManageAreas(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("ManageAreas", this, stage.getScene(), stage);
	}

	public void switchToScenceAddComponent(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("ManageComponents", this, stage.getScene(), stage);
	}

	public void switchToScenceCookByExpertise(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("CookByExpertise", this, stage.getScene(), stage);
	}
	public void switchToScenceManageDeliveries(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.MoveToScene("ManageDeliveries", this, stage.getScene(), stage);
	}
}
