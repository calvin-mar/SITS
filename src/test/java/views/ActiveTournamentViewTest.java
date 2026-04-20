package views;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import application.Viewer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.TournamentServerModel;
import models.UserServer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ExtendWith(ApplicationExtension.class)
class ActiveTournamentViewTest {
	
	ConfigurableApplicationContext context;
	TournamentServerModel model;
	
	
	@Start
	private void start(Stage stage) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(Viewer.class.getResource("../views/ActiveTournamentView.fxml"));
		Pane view = loader.load();
		
		ActiveTournamentController controller = loader.getController();
		Scene s = new Scene(view);
		this.model = new TournamentServerModel(s);
		controller.setModel(this.model, "RRPrisoners");
		
		this.context = SpringApplication.run(UserServer.class);
		UserServer server = this.context.getBean(UserServer.class);
		server.setModel(model);
		
		stage.setScene(s);
		stage.show();
	}
	
	private void sendMove(String p1Name, String p1Score, String p1Action, String p2Name, String p2Score, String p2Action) {
		Platform.runLater(() -> 
		this.model.getMoveList().add("--Participant 1-- \n" +
				"Name: " + p1Name + 
				"\nScore: " + p1Score +
				"\nAction: " + p1Action +
				"\n--Participant 2-- \n" +
				"Name: " + p2Name + 
				"\nScore: " + p2Score +
				"\nAction: " + p2Action)
		);
		WaitForAsyncUtils.waitForFxEvents();
	}
	
	public void verifyUpdate(FxRobot robot, String p1Name, String p1Score, String p1Action, String p2Name, String p2Score, String p2Action) {
		robot.clickOn("--Participant 1-- \n" +
				"Name: " + p1Name + 
				"\nScore: " + p1Score +
				"\nAction: " + p1Action +
				"\n--Participant 2-- \n" +
				"Name: " + p2Name + 
				"\nScore: " + p2Score +
				"\nAction: " + p2Action);
	}
	
	public void testMove(FxRobot robot, String p1Name, String p1Score, String p1Action, String p2Name, String p2Score, String p2Action) {
		sendMove(p1Name, p1Score, p1Action, p2Name, p2Score, p2Action); 
		verifyUpdate(robot, p1Name, p1Score, p1Action, p2Name, p2Score, p2Action);
	}
	
	@Test
	public void testList(FxRobot robot) {
		robot.clickOn("#TournamentNameLabel");
		robot.clickOn("RRPrisoners");
		testMove(robot, "Robot 1", "1", "0", "Robot 2", "1", "0");
		testMove(robot, "Robot 1", "6", "1", "Robot 2", "1", "0");
		testMove(robot, "Robot 1", "6", "0", "Robot 2", "6", "1");
		testMove(robot, "Robot 1", "9", "1", "Robot 2", "9", "1");



	}
}
