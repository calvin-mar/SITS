package views;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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

import tournament.Bracket;
import tournament.Game;
import tournament.IteratedPrisonerDilemna;
import tournament.RoundRobinBracket;
import tournament.Tournament;
import tournamentServer.TournamentServer;
import models.*;


@SpringBootTest(
		webEnvironment=WebEnvironment.RANDOM_PORT,
		classes = TournamentServer.class) 
@ExtendWith(ApplicationExtension.class)
class ConnectToServerControllerTest {
	Tournament tournament;
	Bracket bracket;
	Game game;
	
	Tournament tournament2;
	Bracket bracket2;
	Game game2;
	
	Tournament tournament3;
	Bracket bracket3;
	Game game3;

	@Autowired
	private TournamentServer server;
	
	@Start
	private void start(Stage stage) throws IOException{
		bracket = new RoundRobinBracket();
		game = new IteratedPrisonerDilemna(3);
		game.setTimeDelay(0);
		tournament = new Tournament(game, bracket);
		
		bracket2 = new RoundRobinBracket();
		game2 = new IteratedPrisonerDilemna(3);
		game2.setTimeDelay(0);
		tournament2 = new Tournament(game2, bracket2);
		
		bracket3 = new RoundRobinBracket();
		game3 = new IteratedPrisonerDilemna(3);
		game3.setTimeDelay(0);
		tournament3 = new Tournament(game3, bracket3);
		
		server.addTournament("RRPrisoners", tournament);
		server.addTournament("RRPrisoners 2", tournament2);
		server.addTournament("RRPrisoners 3", tournament3);
		
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(Viewer.class.getResource("../views/ConnectToServerView.fxml"));
		Pane view = loader.load();
		
		ConnectToServerController controller = loader.getController();
		Scene s = new Scene(view);
		controller.setModel(new TournamentServerModel(s));
		
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

	@Test
	public void test(FxRobot robot) {
		server.beginRegistration("RRPrisoners");
		server.beginRegistration("RRPrisoners 3");
		enterServerInfo(robot,server.getIP().getHostAddress(), String.valueOf(server.getPort()));
		connect(robot);
		robot.clickOn("RRPrisoners");
		robot.clickOn("RRPrisoners 3");
		robot.clickOn("#backServerButton");
		enterServerInfo(robot, "10.14.1.84", "4");
		
	}
}
