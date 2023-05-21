package miniprojtemplate;

import java.util.Random;

import game.GameStage;
import game.Sprite;
import javafx.scene.image.Image;

public class Slug extends Sprite {
	public static final int MAX_SLUG_SPEED = 5;
	public final static Image SLUG_IMAGE = new Image("images/slug.png",Slug.SLUG_WIDTH,Slug.SLUG_WIDTH,false,false);
	public final static int SLUG_WIDTH=50;
	private boolean alive;
	//attribute that will determine if a slug will initially move to the right
	private boolean moveRight;
	private int speed;
	protected int strength;
	protected int width;


	public Slug(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(Slug.SLUG_IMAGE);
		this.strength = 30;
		/*
		 *TODO: Randomize speed of slug and moveRight's initial value
		 */
		Random r = new Random();
		this.speed = r.nextInt(Slug.MAX_SLUG_SPEED)+1;
		this.moveRight = r.nextBoolean();
		this.width = Slug.SLUG_WIDTH;
	}

	//method that changes the x position of the slug
	public void move(){
		/*
		 * TODO: 				If moveRight is true and if the fish hasn't reached the right boundary yet,
		 *    						move the fish to the right by changing the x position of the fish depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the fish hasn't reached the left boundary yet,
		 * 	 						move the fish to the left by changing the x position of the fish depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */

		//Code for moving/changing directions
		if(this.moveRight && this.x<=GameStage.WINDOW_WIDTH-this.width){
			this.x += this.speed;
		}
		else if(this.moveRight && this.x>=GameStage.WINDOW_WIDTH-this.width){
			this.moveRight = false;
			this.x -= this.speed;
		}
		else{
			//Code for moving left or right when not at the end of the screen
			if(this.x>0){
				this.x -= this.speed;
			}
			else if(this.x<=0){
				this.moveRight = true;
				this.x += this.speed;
			}
		}
	}

	//getter
	public boolean isAlive() {
		return this.alive;
	}

	public void kill(){
		this.alive = false;
		this.visible = false;
		System.out.println("Slug Died");
	}

	public int getStrength(){
		return this.strength;
	}
}
