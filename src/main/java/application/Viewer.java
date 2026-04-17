package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import models.*;
import views.*;

public class Viewer extends Application{
	
	@Override
	public void start(Stage stage) throws Exception{
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(Viewer.class.getResource("../views/ConnectToServerView.fxml"));
		Pane view = loader.load();
		
		ConnectToServerController controller = loader.getController();
		Scene s = new Scene(view);
		controller.setModel(new TournamentServerModel(s));
		
		stage.setScene(s);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
