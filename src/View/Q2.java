package View;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import Model.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Q2 implements Initializable {

	@FXML
	private PieChart pieChart;
	@FXML
	private Text year;
	@FXML
	private Button back;

	private Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		year.setText(String.valueOf(LocalDate.now().getYear()));
		ObservableList<PieChart.Data> p = FXCollections.observableArrayList();

		for (Entry<String, Integer> mod : Restaurant.getInstance().getNumberOfDeliveriesPerType().entrySet()) {

			p.add(new PieChart.Data(mod.getKey(), mod.getValue()));
			pieChart.setData(p);
		}

		pieChart.getData().forEach(data -> {
			String percentage = String.format("%.0f", data.getPieValue());
			Tooltip toolTip = new Tooltip(percentage);
			toolTip.setStyle("-fx-font: 18 arial;");
			toolTip.setShowDelay(Duration.seconds(0));
			Tooltip.install(data.getNode(), toolTip);
		});

	}

	public void switchToScenceQueriesHome(ActionEvent event) throws IOException {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		SceneManager.GoBack(stage);
	}
}
