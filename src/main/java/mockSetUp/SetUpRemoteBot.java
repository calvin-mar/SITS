package mockSetUp;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import remoteBots.RemoteController;
import tournament.*;
import tournamentServer.TournamentServer;

public class SetUpRemoteBot {
	public void main(String[] args) {
		String baseURL = "http://" + args[1] + ":" + args[2] + "/";
		
		ConfigurableApplicationContext context = SpringApplication.run(RemoteController.class);
		RemoteController botServer = context.getBean(RemoteController.class);
		
		Participant p = new SelfishBot();
		
		botServer.setRegistrar(baseURL);
		botServer.setBotName("Remote Bot");
		botServer.setParticipantType(p);
	}
}
