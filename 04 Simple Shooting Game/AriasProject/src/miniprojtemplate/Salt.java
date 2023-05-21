package miniprojtemplate;

import game.GameStage;
import game.Sprite;
import javafx.scene.image.Image;

public class Salt extends Sprite {
	private final static int SALT_SPEED = 20;
	private boolean hit;
	public final static Image SALT_IMAGE = new Image("images/bullet.png",Salt.SALT_WIDTH,Salt.SALT_WIDTH,false,false);
	public final static int SALT_WIDTH = 20;
	private int dmg;

	public Salt(int x, int y, int dmg){
		super(x,y);
		this.loadImage(Salt.SALT_IMAGE);
		this.hit = false;
		this.dmg = dmg;
	}

	//getter method for hit boolean
	public boolean getHit(){
		return this.hit;
	}

	//"Kills" the bullet when it hits an enemy
	public void kill(){
		this.hit = true;
	}

	//method that will move/change the x position of the bullet
	public void move(){
		/*
		 * TODO: Change the x position of the bullet depending on the bullet speed.
		 * 					If the x position has reached the right boundary of the screen,
		 * 						set the bullet's visibility to false.
		 */
		this.x += Salt.SALT_SPEED;
		if(this.x>=GameStage.WINDOW_WIDTH){
			this.visible = false;
		}
	}

	public int getDmg(){
		return this.dmg;
	}
}