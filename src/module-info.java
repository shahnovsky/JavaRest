module r {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens View to javafx.graphics, javafx.fxml;
	opens Model to javafx.graphics, javafx.fxml, javafx.base;
}
