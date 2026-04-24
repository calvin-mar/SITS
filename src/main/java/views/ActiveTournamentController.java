package views;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.*;
import models.*;
import views.TournamentListController.SpectateInfo;

public class ActiveTournamentController {
	TournamentServerModel model;
	
	public void setModel(TournamentServerModel model, String name) {
		this.model = model;
		MovesListView.setItems(model.getMoveList());
		TournamentNameLabel.setText(name);
	}
	
	@FXML
	private Label TournamentNameLabel;
	
	@FXML
	private ListView MovesListView;
	
	@FXML
	void onClickBack(ActionEvent event) {
		String tournamentName = TournamentNameLabel.getText();
		SpectateInfo serverInfo = new SpectateInfo(tournamentName,model.getIp().getHostAddress(), model.getPort());
		model.getServerClient().getClient().put().uri("/stopSpectate").body(serverInfo).retrieve().toBodilessEntity();
		try {
			System.out.println("Switching to serverlist.");
			model.showServerList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
