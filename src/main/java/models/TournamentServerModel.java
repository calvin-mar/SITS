package models;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import views.*;

public class TournamentServerModel {
	UserClient serverClient;
	ObservableList<String> moveList;
	Scene scene;
	
	public TournamentServerModel(Scene scene) {
		ObservableList<String> moveList = FXCollections.observableArrayList();
		this.scene = scene;
	}
	
	public UserClient getServerClient() {
		return serverClient;
	}

	public void setServerClient(UserClient serverClient) {
		this.serverClient = serverClient;
	}

	public void showServerList() throws IOException {
		System.out.println("Switch to TournamentListView");
		
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(TournamentServerModel.class
	        .getResource("../views/TournamentListView.fxml"));
	    try {
	      Pane view = loader.load();
	      TournamentListController cont = loader.getController();
	      cont.setModel(this);
	      
	      scene.setRoot(view);
	      
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
		
	}
	
	public void showServerPicker() throws IOException {
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(TournamentServerModel.class
	        .getResource("../views/ConnectToServerView.fxml"));
	    try {
	      Pane view = loader.load();
	      ConnectToServerController cont = loader.getController();
	      cont.setModel(this);
	      
	      scene.setRoot(view);
	      
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	}
	
	public ObservableList<String> getMoveList(){
		return moveList;
	}
}
