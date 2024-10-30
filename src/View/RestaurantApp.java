package View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import Model.Restaurant;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class RestaurantApp extends Application {

	public static void LaunchApp() {
		// myRest = Restaurant.getInstance();
		launch();
	}

	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
			stage.setTitle("Java Eat");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setTitle("JavaEat");
			stage.setResizable(false);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					System.out.println("Closing now");

					try {
						FileOutputStream fileOut = new FileOutputStream("RestDatabase.ser");
						ObjectOutputStream out = new ObjectOutputStream(fileOut);
						out.writeObject(Restaurant.getInstance());
						out.close();
						fileOut.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					Platform.exit();
					System.exit(0);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
