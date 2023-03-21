package modul;
	
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MyScene.fxml"));
			Scene scene = new Scene(root);
			Image image = new Image(new FileInputStream("src/images/icon.png"));
			primaryStage.getIcons().add(image);
			primaryStage.setTitle("Hello World!");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	} 
	
	public static void main(String[] args) {
		launch(args);
	}
}
