package mockSetUp;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import tournament.*;
import tournamentServer.TournamentServer;

public class SetUpSprintFourDemonstration {
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
		Participant p13 = new SelfishBot();
		Participant p14 = new SelflessBot();
		Participant p15 = new AlternatingBot();
		Participant p16 = new SelfishBot();
		Participant p17 = new SelflessBot();
		Participant p18 = new AlternatingBot();
		Participant p19 = new SelfishBot();
		Participant p20 = new SelflessBot();
		Participant p21 = new AlternatingBot();
		Participant p22 = new SelfishBot();
		Participant p23 = new SelflessBot();
		Participant p24 = new AlternatingBot();
		
		Team team = new RotatingCaptain();
		Participant member1 = new SelfishBot();
		Participant member2 = new AlternatingBot();
		Participant member3 = new AlternatingBot();
		Participant member4 = new SelflessBot();
		team.joinTeam(member1);
		team.joinTeam(member2);
		team.joinTeam(member3);
		team.joinTeam(member4);
		
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
		p11.setName("Genevieve");
		p12.setName("Andrew");
		p13.setName("George");
		p14.setName("Evan");
		p15.setName("Timothy");
		p16.setName("John");
		p17.setName("Emily");
		p18.setName("Geneva");
		p19.setName("Susannah");
		p20.setName("Carlie");
		p21.setName("Carrie");
		p22.setName("Doreen");
		p23.setName("Alice");
		p24.setName("Humphrey");
		
		tournament.addParticipant(team);
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
		tournament.addParticipant(p13);
		tournament.addParticipant(p14);
		tournament.addParticipant(p15);
		tournament.addParticipant(p16);
		tournament.addParticipant(p17);
		tournament.addParticipant(p18);
		tournament.addParticipant(p19);
		tournament.addParticipant(p20);
		tournament.addParticipant(p21);
		tournament.addParticipant(p22);
		tournament.addParticipant(p23);
		tournament.addParticipant(p24);
		
		SpringApplication app = new SpringApplication(TournamentServer.class);
		app.setAdditionalProfiles("random");
		ConfigurableApplicationContext context = app.run(args);
		TournamentServer server = context.getBean(TournamentServer.class);
		System.out.println(server.getPort());
		
		server.addTournament("RRPrisoners", tournament);
		server.addTournament("RRPrisoners 2", tournament2);
		server.addTournament("RRPrisoners 3", tournament3);
		server.beginRegistration("RRPrisoners");
		server.beginRegistration("RRPrisoners 2");
		server.beginRegistration("RRPrisoners 3");
		server.beginTournament(tournament);
	}
}
