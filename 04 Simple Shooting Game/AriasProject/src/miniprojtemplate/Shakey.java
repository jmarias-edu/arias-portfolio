package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;

import game.GameStage;
import game.Sprite;
import javafx.scene.image.Image;

public class Shakey extends Sprite{
	private String name;
	private int strength;
	private boolean alive;
	private boolean vulnerable;
	private int vulnerableTimer;

	private ArrayList<Salt> salts;
	public final static Image SHAKEY_IMAGE = new Image("images/shakey.png",Shakey.SHAKEY_WIDTH,Shakey.SHAKEY_WIDTH,false,false);
	private final static int SHAKEY_WIDTH = 50;

	public Shakey(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.strength = r.nextInt(51)+100;
		this.alive = true;
		this.salts = new ArrayList<Salt>();
		this.loadImage(Shakey.SHAKEY_IMAGE);
		this.printState();
		this.vulnerable = false;
		this.vulnerableTimer = 0;
	}
	//checks if Shakey is Alive
	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}
	public String getName(){
		return this.name;
	}

	public void damage(int dmg){
		if(this.strength>dmg){
			this.strength -= dmg;
			System.out.println("Ship got damaged for "+dmg);
		}
		else{
			this.die();
			System.out.println("Ship died");
		}
	}

	public void die(){
    	this.alive = false;
    	this.visible = false;
    }

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Salt> getSalts(){
		return this.salts;
	}

	//method called if spacebar is pressed
	public void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x + this.width/2);
		int y = (int) (this.y + this.height/2);
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */
		Salt b = new Salt(x, y, this.strength);
		this.salts.add(b);
    }

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */
		if(this.x+this.dx<=GameStage.WINDOW_WIDTH-Shakey.SHAKEY_WIDTH && this.x+this.dx>=0){
			this.x += this.dx;
		}
		if(this.y+this.dy<=GameStage.WINDOW_HEIGHT-Shakey.SHAKEY_WIDTH && this.y+this.dy>=0){
			this.y += this.dy;
		}
	}

	//getters and setters for Ship Class
	public boolean getVulnerability(){
		return this.vulnerable;
	}

	public void setVulnerability(boolean x){
		this.vulnerable = x;
	}

	public void addStrength(int str){
		this.strength += str;
	}

	public int getStrength(){
		return this.strength;
	}

	public int getVulnerabilityTime(){
		return this.vulnerableTimer;
	}

	public void setVulnerabilityTime(int x){
		this.vulnerableTimer = x;
	}

	public void printState(){
		System.out.println("===Ship State===");
		System.out.println("Name: "+this.name);
		System.out.println("Strength: "+this.strength);
		System.out.println("Alive: "+this.alive);

	}
}
