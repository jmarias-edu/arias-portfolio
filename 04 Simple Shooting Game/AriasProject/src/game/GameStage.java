package game;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameStage {
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	public static final int GOOD_ENDING = 500;
	public static final int BAD_ENDING = 800;
	private Scene scene;
	private Stage stage;
	private StackPane root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private final static BackgroundImage gamebgimg = new BackgroundImage(new Image("images/game.png",GameStage.WINDOW_WIDTH,
			GameStage.WINDOW_HEIGHT,false,false), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);

	//the class constructor
	public GameStage() {
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		//instantiate an animation timer
		this.gametimer = new GameTimer(this.gc,this.scene, this);
		Background bg = new Background(GameStage.gamebgimg);
		this.root.setBackground(bg);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;

		//set stage elements here
		this.root.getChildren().add(canvas);
		this.stage.setTitle("Slugs and Salts");
		this.stage.setScene(this.scene);

		//invoke the start method of the animation timer
		this.gametimer.start();

		this.stage.show();
	}
	//flashes the game over screen
	public void flashGameOver(int ending){
		GameOverStage gameover = new GameOverStage(ending);
		gameover.setStage(stage);
	}
}

