package views;

import java.io.IOException;
import java.net.InetAddress;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.*;
import models.*;

public class ConnectToServerController {
	TournamentServerModel model;
	
	public void setModel(TournamentServerModel model) {
		this.model = model;
	}
	
	@FXML 
	private TextField IPTextField;
	
	@FXML
	private TextField PortNumTextField;
	
	@FXML
	void onClickConnect(ActionEvent event) throws NumberFormatException, IOException {
		
		System.out.println("Button Clicked");
		
		UserClient uc = new UserClient(InetAddress.getByName(
				IPTextField.getText()), Integer.valueOf(PortNumTextField.getText()));
		
		model.setServerClient(uc);
		
		model.showServerList();
	}
}
