package mockSetUp;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import remoteBots.RemoteController;
import tournament.*;
import tournamentServer.TournamentServer;

public class SetUpRemoteBot {
	public static void main(String[] args) {
		String baseURL = "http://10.14.1.75:PORT/";
		
		SpringApplication app = new SpringApplication(RemoteController.class);
		app.setAdditionalProfiles("random");
		ConfigurableApplicationContext context = app.run(args);
		RemoteController botServer = context.getBean(RemoteController.class);
		
		Participant p = new SelfishBot();
		
		botServer.setBotName("Remote Bot");
		botServer.setParticipantType(p);
		botServer.setRegistrar(baseURL);
		botServer.registerForTournament("RRPRisoners");
	}
}
