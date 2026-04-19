package views;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.*;
import models.*;


public class TournamentListController {
	TournamentServerModel model;
	
	public record SpectateInfo(String tournamentName, InetAddress ip, int port) {};

	
	public void setModel(TournamentServerModel model) {
		this.model = model;
		ListOfTournaments.setItems(model.getTournamentList());
	}

	@FXML
	private ListView ListOfTournaments;	
	
	@FXML
	void onClickBack(ActionEvent event) throws IOException {
		System.out.println("Back");
		model.setServerClient(null);
		model.showServerPicker();
	}
	
	@FXML 
	void onClickView(ActionEvent event) {
		System.out.println("Spectate");
		String selected = (String) ListOfTournaments.getSelectionModel().getSelectedItem();
		
		SpectateInfo serverInfo = new SpectateInfo(selected,model.getIp(), model.getPort());
		model.getServerClient().getClient().put().uri("/spectate").body(serverInfo);
		
		try {
			model.showActiveTournament(selected);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
