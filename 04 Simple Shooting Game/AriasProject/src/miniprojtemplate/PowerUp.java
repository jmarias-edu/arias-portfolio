package miniprojtemplate;

import game.Sprite;
import javafx.scene.image.Image;

//PowerUp SuperClass
public class PowerUp extends Sprite{
	public final static Image MSG_IMAGE = new Image("images/msg.png",PowerUp.POWERUP_WIDTH,PowerUp.POWERUP_WIDTH,false,false);
	public final static Image GHOST_IMAGE = new Image("images/ghost.png",PowerUp.POWERUP_WIDTH,PowerUp.POWERUP_WIDTH,false,false);
	public final static int POWERUP_WIDTH = 50;
	protected Shakey myShakey;
	private int duration;

	public PowerUp(int xPos, int yPos, Shakey ship) {
		super(xPos, yPos);
		this.myShakey = ship;
		this.duration = 5;
	}

	//method to be overridden by the subclasses
	public void triggerPowerUp(){}

	//getter/setter methods that handle the duration of the powerups on the field
	public int getDuration(){
		return this.duration;
	}
	public void countDown(){
		this.duration -= 1;
	}
}
