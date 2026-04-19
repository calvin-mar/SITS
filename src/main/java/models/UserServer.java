package models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javafx.application.Platform;
import remoteBots.RemoteController;

@SpringBootApplication
@RestController
public class UserServer {
	TournamentServerModel model;
	
	public TournamentServerModel getModel() {
		return model;
	}
	public void setModel(TournamentServerModel model) {
		this.model = model;
	}
	
	public record Move(String p1Name, int p1Score, int p1Action, String p2Name, int p2Score, int p2Action) {};

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/updateMoveList")
	public void setNextMove(@RequestBody Move newMove) {
		Platform.runLater(() ->
		model.getMoveList().add("--Participant 1-- \n" +
				"Name: " + newMove.p1Name + 
				"\nScore: " + newMove.p1Score +
				"\nAction: " + newMove.p1Action +
				"\n--Participant 2-- \n" +
				"Name: " + newMove.p2Name + 
				"\nScore: " + newMove.p2Score +
				"\nAction: " + newMove.p2Action + "\n"));
	}
	
	public void run() {
		
	}
	
	public void main(String args) {
		new SpringApplicationBuilder(UserServer.class).profiles("random").run(args);
	}
	
}
