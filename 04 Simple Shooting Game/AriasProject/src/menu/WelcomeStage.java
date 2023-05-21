package menu;

import game.GameStage;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WelcomeStage {
	private Stage stage;
	private Scene scene;
	private StackPane root;
	private Canvas canvas;
	private HBox hbox;
	private final static BackgroundImage menubgimg = new BackgroundImage(new Image("images/menu.png",GameStage.WINDOW_WIDTH,
			GameStage.WINDOW_HEIGHT,false,false), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);

	public WelcomeStage(){
		//sets up stackpane hbox combo
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.LIGHTBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.hbox = this.createHBox();
		this.root.getChildren().add(canvas);
		this.root.getChildren().add(hbox);
		Background bg = new Background(WelcomeStage.menubgimg);
		this.root.setBackground(bg);
		this.root.setPadding(new Insets(50));
	}
	//method to add the stage elements
	public void setStage(Stage stage) {
		//set stage elements here
		this.stage = stage;
		this.stage.setTitle("Slugs and Salts");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	//method to create the hbox,(buttons in menu)
	private HBox createHBox(){
		HBox v = new HBox();

		v.setAlignment(Pos.BOTTOM_CENTER);

		//creation of buttons
		Button b1 = WelcomeStage.createButton("Start Game");
		Button b2 = WelcomeStage.createButton("Instructions");
		Button b3 = WelcomeStage.createButton("About");
		//addition to hbox
		v.getChildren().add(b2);
		v.getChildren().add(b1);
		v.getChildren().add(b3);
		//formatting
		v.setSpacing(20);
		//sets button functions
		this.setMouseHandler1(b1);
		this.setMouseHandler2(b2);
		this.setMouseHandler3(b3);

		return v;
	}

	//creates button with design and text
	public static Button createButton(String text){
		Button b = new Button(text);
		b.setPadding(new Insets(10));
		b.setStyle("-fx-font: 20 Rockwell; -fx-background-color: #007AC1; -fx-text-fill: #FFFFFF");
		return b;
	}
	//setMouseHandler Functions
    private void setMouseHandler1(Button b1) {
    	b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	GameStage theGameStage = new GameStage();
        		theGameStage.setStage(stage);
        		System.out.println("Game Start");
            }
        });
    }

    private void setMouseHandler2(Button b2) {
    	b2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	Instructions i = new Instructions();
        		i.setStage(stage);
        		System.out.println("Instructions");
            }
        });
    }

    private void setMouseHandler3(Button b3) {
    	b3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	About a = new About();
        		a.setStage(stage);
        		System.out.println("About");
            }
        });
    }

}
