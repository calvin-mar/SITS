package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import models.*;
import views.*;

public class Viewer extends Application{
	
	ConfigurableApplicationContext context;
	
	@Override
	public void start(Stage stage) throws Exception{
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(Viewer.class.getResource("../views/ConnectToServerView.fxml"));
		Pane view = loader.load();
		
		ConnectToServerController controller = loader.getController();
		Scene s = new Scene(view);
		TournamentServerModel model = new TournamentServerModel(s);
		controller.setModel(model);
		
		this.context = SpringApplication.run(UserServer.class);
		UserServer server = this.context.getBean(UserServer.class);
		server.setModel(model);
		
		stage.setScene(s);
		stage.show();
	}
	
	@Override
	public void stop() {
		SpringApplication.exit(this.context, () -> 0);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
