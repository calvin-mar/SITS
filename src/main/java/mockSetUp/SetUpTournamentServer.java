package mockSetUp;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import tournamentServer.*;
import tournament.*;

public class SetUpTournamentServer {
	public static void main(String[] args) {
		Bracket bracket = new RoundRobinBracket();
		Game game = new IteratedPrisonerDilemna(3);
		Tournament tournament = new Tournament(game, bracket);
		
		Bracket bracket2 = new RoundRobinBracket();
		Game game2 = new IteratedPrisonerDilemna(3);
		Tournament tournament2 = new Tournament(game2, bracket2);
		
		Bracket bracket3 = new RoundRobinBracket();
		Game game3 = new IteratedPrisonerDilemna(3);
		Tournament tournament3 = new Tournament(game3, bracket3);
		
		Participant p1 = new SelfishBot();
		Participant p2 = new SelflessBot();
		Participant p3 = new AlternatingBot();
		Participant p4 = new SelfishBot();
		Participant p5 = new SelflessBot();
		Participant p6 = new AlternatingBot();
		Participant p7 = new SelfishBot();
		Participant p8 = new SelflessBot();
		Participant p9 = new AlternatingBot();
		Participant p10 = new SelfishBot();
		Participant p11 = new SelflessBot();
		Participant p12 = new AlternatingBot();
		
		p1.setName("Ben");
		p2.setName("Max");
		p3.setName("Sam");
		p4.setName("Zoe");
		p5.setName("Brandon");
		p6.setName("Emma");
		p7.setName("Eli");
		p8.setName("Jonah");
		p9.setName("Edward");
		p10.setName("Helen");
		p5.setName("Genevieve");
		p6.setName("Andrew");

		
		tournament.addParticipant(p1);
		tournament.addParticipant(p2);
		tournament.addParticipant(p3);
		tournament.addParticipant(p4);
		tournament.addParticipant(p5);
		tournament.addParticipant(p6);
		tournament.addParticipant(p7);
		tournament.addParticipant(p8);
		tournament.addParticipant(p9);
		tournament.addParticipant(p10);
		tournament.addParticipant(p11);
		tournament.addParticipant(p12);
		
		SpringApplication app = new SpringApplication(TournamentServer.class);
		app.setAdditionalProfiles("random");
		ConfigurableApplicationContext context = app.run(args);
		TournamentServer server = context.getBean(TournamentServer.class);
		
		server.addTournament("RRPrisoners", tournament);
		server.addTournament("RRPrisoners 2", tournament2);
		server.addTournament("RRPrisoners 3", tournament3);
		server.beginRegistration("RRPrisoners");
		server.beginRegistration("RRPrisoners 2");
		server.beginRegistration("RRPrisoners 3");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.beginTournament(tournament);
	}
}
