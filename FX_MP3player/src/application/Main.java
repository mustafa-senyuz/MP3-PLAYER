package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	
	  
	@Override
	public void start(Stage stage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("extra.fxml"));
 
			Scene scene = new Scene(root );

			
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent arg0) {

						Platform.exit();
						System.exit(0);
					
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
 
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

	 
}
