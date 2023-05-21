package game;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import menu.WelcomeStage;

public class GameOverStage {

	private Stage stage;
	private Scene scene;
	private StackPane root;
	private Canvas canvas;
	private VBox vbox;
	private Background GameEnding;

	//The two backgrounds for the endings
	private final static BackgroundImage winbgimg = new BackgroundImage(new Image("images/win.png",GameStage.WINDOW_WIDTH,
			GameStage.WINDOW_HEIGHT,false,false), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);

	private final static BackgroundImage losebgimg = new BackgroundImage(new Image("images/lose.png",GameStage.WINDOW_WIDTH,
			GameStage.WINDOW_HEIGHT,false,false), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);

	GameOverStage(int ending){
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.LIGHTBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.setEnding(ending);
		this.vbox = this.createVBox();
		this.root.getChildren().add(canvas);
		this.root.getChildren().add(vbox);
		this.root.setPadding(new Insets(20));
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		//set stage elements here
		this.stage.setTitle("Slugs and Salts");
		this.stage.setScene(this.scene);
		this.stage.show();
	}


	//Selects the ending screen
	public void setEnding(int ending){
		if(ending == GameStage.GOOD_ENDING){
			this.GameEnding = new Background(GameOverStage.winbgimg);
			System.out.println("Win!");
		}
		else if(ending == GameStage.BAD_ENDING){
			this.GameEnding = new Background(GameOverStage.losebgimg);
			System.out.println("Lose!");
		}
		this.root.setBackground(this.GameEnding);
	}

	private VBox createVBox(){
		VBox v = new VBox();
		v.setAlignment(Pos.BOTTOM_CENTER);
		Button b1 = WelcomeStage.createButton("Return to Menu");
		v.getChildren().add(b1);
		this.setMouseHandler1(b1);
		return v;
	}

    public void setMouseHandler1(Button b1) {
    	b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	WelcomeStage menu = new WelcomeStage();
        		menu.setStage(stage);
            }
        });
    }
}
