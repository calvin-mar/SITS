package application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
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

import application.Viewer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tournament.*;
import tournamentServer.TournamentServer;
import views.ConnectToServerController;
import models.*;


@SpringBootTest(
		webEnvironment=WebEnvironment.RANDOM_PORT,
		classes = TournamentServer.class) 
@ExtendWith(ApplicationExtension.class)
class ViewerTest {
	Tournament tournament;
	Bracket bracket;
	Game game;
	
	Tournament tournament2;
	Bracket bracket2;
	Game game2;
	
	Tournament tournament3;
	Bracket bracket3;
	Game game3;
	
	SelfishBot p1;
	SelflessBot p2;
	AlternatingBot p3;
	
	ConfigurableApplicationContext context;

	@Autowired
	private TournamentServer server;
	
	@Start
	private void start(Stage stage) throws IOException{
		bracket = new RoundRobinBracket();
		game = new IteratedPrisonerDilemna(3);
		tournament = new Tournament(game, bracket);
		
		bracket2 = new RoundRobinBracket();
		game2 = new IteratedPrisonerDilemna(3);
		tournament2 = new Tournament(game2, bracket2);
		
		bracket3 = new RoundRobinBracket();
		game3 = new IteratedPrisonerDilemna(3);
		tournament3 = new Tournament(game3, bracket3);
		
		p1 = new SelfishBot();
		p2 = new SelflessBot();
		p3 = new AlternatingBot();
		
		tournament.addParticipant(p1);
		tournament.addParticipant(p2);
		tournament.addParticipant(p3);
		
		server.addTournament("RRPrisoners", tournament);
		server.addTournament("RRPrisoners 2", tournament2);
		server.addTournament("RRPrisoners 3", tournament3);
		
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
	
	private void enterServerInfo(FxRobot robot, String ip, String port) {
		robot.doubleClickOn("#IPTextField");
		robot.write(ip);
		robot.doubleClickOn("#PortNumTextField");
		robot.write(port);
	}
	
	private void connect(FxRobot robot) {
		robot.clickOn("#ConnectToServerButton");
	}
	
	public void testMove(FxRobot robot, String p1Name, String p1Score, String p1Action, String p2Name, String p2Score, String p2Action) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.clickOn("--Participant 1-- \n" +
				"Name: " + p1Name + 
				"\nScore: " + p1Score +
				"\nAction: " + p1Action +
				"\n--Participant 2-- \n" +
				"Name: " + p2Name + 
				"\nScore: " + p2Score +
				"\nAction: " + p2Action);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void viewerApplicationTest(FxRobot robot) {
		server.beginRegistration("RRPrisoners");
		server.beginRegistration("RRPrisoners 3");
		enterServerInfo(robot,server.getIP().getHostAddress(), String.valueOf(server.getPort()));
		server.playTournament(tournament);
		connect(robot);
		robot.clickOn("RRPrisoners (Running)");
		//robot.clickOn("#SpectateButton");
		robot.clickOn("#SpectateButton");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		testMove(robot, "Selfish Bot", "1", "0", "Alternating Bot", "1", "0");
		testMove(robot, "Selfish Bot", "6", "0", "Alternating Bot", "1", "1");
		testMove(robot, "Selfish Bot", "7", "0", "Alternating Bot", "2", "0");
		
		testMove(robot, "Selfless Bot", "3", "1", "Alternating Bot", "3", "1");
		testMove(robot, "Selfless Bot", "3", "1", "Alternating Bot", "8", "0");
		testMove(robot, "Selfless Bot", "6", "1", "Alternating Bot", "11", "1");
	}
	
	@AfterEach
	void tearDown() {
	    if (context != null) {
	        context.close();
	    }
	}

}
