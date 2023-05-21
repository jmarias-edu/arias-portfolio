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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class About {
	private Stage stage;
	private Scene scene;
	private StackPane root;
	private Canvas canvas;
	private VBox vbox;
	private final static BackgroundImage aboutbgimg = new BackgroundImage(new Image("images/about.png",GameStage.WINDOW_WIDTH,
			GameStage.WINDOW_HEIGHT,false,false), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);

	About(){
		//sets up stackpane vbox combo
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.LIGHTBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.vbox = this.createVBox();
		this.root.getChildren().add(canvas);
		this.root.getChildren().add(vbox);
		Background bg = new Background(About.aboutbgimg);
		this.root.setBackground(bg);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		//set stage elements here
		this.stage = stage;
		this.stage.setTitle("Slugs and Salts");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	//creates the vbox with button
	private VBox createVBox(){
		VBox v = new VBox();
		v.setPadding(new Insets(20));
		v.setAlignment(Pos.BOTTOM_CENTER);

		Button b1 = WelcomeStage.createButton("Return to Menu");
		v.getChildren().add(b1);

		this.setMouseHandler1(b1);

		return v;
	}
	//setMouseHandler Functions
    private void setMouseHandler1(Button b1) {
    	b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	WelcomeStage menu = new WelcomeStage();
        		menu.setStage(stage);
        		System.out.println("Back to Menu");
            }
        });
    }

}
