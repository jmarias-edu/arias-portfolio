package game;

import javafx.application.Application;
import menu.WelcomeStage;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage){
		WelcomeStage menu = new WelcomeStage();
		menu.setStage(stage);
	}

}
