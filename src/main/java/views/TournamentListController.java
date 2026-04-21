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
	
	public record SpectateInfo(String tournamentName, String ip, int port) {};

	
	public void setModel(TournamentServerModel model) {
		this.model = model;
		ListOfTournaments.setItems(model.getTournamentList());
	}

	@FXML
	private ListView ListOfTournaments;	
	
	@FXML
	void onClickBack(ActionEvent event) throws IOException {
		model.setServerClient(null);
		model.showServerPicker();
	}
	
	@FXML 
	void onClickView(ActionEvent event) {
		String selected = (String) ListOfTournaments.getSelectionModel().getSelectedItem();
		if(selected.length() > 10 && selected.substring(selected.length() - 9, selected.length()).equals("(Running)")) {
			selected = selected.substring(0, selected.length() - 10);
			
			SpectateInfo serverInfo = new SpectateInfo(selected,model.getIp().getHostAddress(), model.getPort());
			model.getServerClient().getClient().put().uri("/spectate").body(serverInfo).retrieve().toBodilessEntity();
			try {
				model.showActiveTournament(selected);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
