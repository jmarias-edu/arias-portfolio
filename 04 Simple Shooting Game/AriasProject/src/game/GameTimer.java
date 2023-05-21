package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import miniprojtemplate.BossSlug;
import miniprojtemplate.Salt;
import miniprojtemplate.Slug;
import miniprojtemplate.Ghost;
import miniprojtemplate.MSG;
import miniprojtemplate.PowerUp;
import miniprojtemplate.Shakey;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{

	private GraphicsContext gc;
	private Scene theScene;
	private GameStage gamestage;
	private long timer;
	private Shakey myShakey;
	private long startSpawn;
	private int slugsKilled;
	private boolean secondTrigger;
	private boolean bossSpawn;
	private BossSlug boss;
	private ArrayList<Slug> slugs;
	private ArrayList<PowerUp> powerUps;
	public static final int MAX_NUM_SLUGS = 7;

	GameTimer(GraphicsContext gc, Scene theScene, GameStage gamestage){
		this.gc = gc;
		this.theScene = theScene;
		this.myShakey = new Shakey("Shakey",150,250);
		//instantiate the ArrayList of Slugs and powerups
		this.slugs = new ArrayList<Slug>();
		this.powerUps = new ArrayList<PowerUp>();
		//Timing related attributes
		this.startSpawn = System.nanoTime();
		this.secondTrigger = false;
		this.slugsKilled = 0;
		this.bossSpawn = false;
		this.gamestage = gamestage;
		//call the spawnFishes method
		this.spawnSlugs(GameTimer.MAX_NUM_SLUGS);
		//call method to handle mouse click event
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime) {
		//variables for timing
		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
		long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn);

		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

		this.updateStats();
		this.endingCheck();

		//code block that ticks every second
		if(this.timer != currentSec-startSec && this.secondTrigger){ //only triggers when the current computation for time is different from the attribute time
			this.timer = currentSec - startSec;
			this.perSecCheck(this.timer);
			this.secondTrigger = false;
		}else{
			this.secondTrigger = true;
			this.timer = currentSec - startSec;
		}

		//movement and collision related functions
		this.checkCollisions();
		this.myShakey.move();
		this.moveSalts();
		this.moveSlugs();
		//renders all needed sprites
		this.renderAll();
	}

	//Checks for game ending conditions
	private void endingCheck(){
		//triggers losing endscreen when shakey is dead
		if(!this.myShakey.isAlive()){
			this.gamestage.flashGameOver(GameStage.BAD_ENDING);
			this.stop();
		}
		//triggers winning endscreen when shakey survives for 60 seconds
		if(this.timer==60){
			this.gamestage.flashGameOver(GameStage.GOOD_ENDING);
			this.stop();
		}
	}

	//Calls all render functions at once
	private void renderAll(){
		this.renderShip();
		this.renderSalts();
		this.renderSlugs();
		this.renderPowerUps();
	}

	//handles all functions that use seconds
	private void perSecCheck(long time){
		//spawns 3 slugs every 5 seconds
		if(time%5==0 && time!=0){
			this.spawnSlugs(3);
		}

		//code block for reducing shakey's invulnerability
		if(this.myShakey.getVulnerability()){
			this.myShakey.setVulnerabilityTime(this.myShakey.getVulnerabilityTime()-1);
			if(this.myShakey.getVulnerabilityTime()==0){
				this.myShakey.setVulnerability(false);
			}
		}

		//code block for spawning powerups
		if(time%10==0 && time!=0){
			this.spawnPowerUp();
			System.out.println("PowerUp Spawned!");
		}

		//code block for spawning boss at 30 seconds
		if(time%30==0 && time!=0){
			this.spawnBoss();
		}

		//code block responsible for despawning of powerups
		for(int i=0; i<this.powerUps.size();i++){
			if(this.powerUps.get(i).getDuration()==0){
				powerUps.remove(i);
				continue;
			}
			this.powerUps.get(i).countDown();
		}
	}

	//method that will render/draw the Ship to the canvas
	private void renderShip(){
		if(this.myShakey.isAlive()){
			this.myShakey.render(this.gc);
		}
	}

	//method that will render/draw the fishes to the canvas
	private void renderSlugs() {
		for (Slug f : this.slugs){
			if(f.isAlive()) f.render(this.gc);
		}
	}

	//method that will render/draw the salts to the canvas
	private void renderSalts() {
		/*
		 *TODO: Loop through the bullets arraylist of myShip
		 *				and render each bullet to the canvas
		 */
		for (Salt b: this.myShakey.getSalts()){
			b.render(this.gc);
		}
	}

	//method that will render/draw the PowerUps to the canvas
	private void renderPowerUps(){
		for(PowerUp p: this.powerUps){
			p.render(this.gc);
		}
	}

	//method that will spawn/instantiate num amount of fishes at a random x,y location in a gridlike fashion
	private void spawnSlugs(int num){
		System.out.println("Slugs Spawned");
		Random r = new Random();
		for(int i=0;i<num;i++){
			int x = r.nextInt(GameStage.WINDOW_WIDTH/100)*50+GameStage.WINDOW_WIDTH/2;
			int y = r.nextInt(GameStage.WINDOW_HEIGHT/50)*50;
			/*
			 *TODO: Add a new object Fish to the fishes arraylist
			 */
			Slug f = new Slug(x, y);
			this.slugs.add(f);
		}
	}

	//Spawns the Boss Slug
	private void spawnBoss(){
		if(!this.bossSpawn){
			this.bossSpawn = true;
			System.out.println("Boss Spawned");
			Random r = new Random();
			int x = r.nextInt(GameStage.WINDOW_WIDTH/(BossSlug.BOSS_WIDTH*2))*BossSlug.BOSS_WIDTH+BossSlug.BOSS_WIDTH/2;
			int y = r.nextInt(GameStage.WINDOW_HEIGHT/BossSlug.BOSS_WIDTH)*BossSlug.BOSS_WIDTH;
			BossSlug bf = new BossSlug(x, y);
			this.slugs.add(bf);
			this.boss = bf;
		}
	}


	//Spawns PowerUp randomly between MSG and Ghost
	private void spawnPowerUp(){
		Random r = new Random();

		int x = r.nextInt(GameStage.WINDOW_WIDTH/100)*50;
		int y = r.nextInt(GameStage.WINDOW_HEIGHT/50)*50;

		PowerUp p;
		//random spawn between the two powerups
		int type = r.nextInt(2);
		if(type == 1){
			p = new MSG(x,y, this.myShakey);
		}
		else{
			p = new Ghost(x,y, this.myShakey);
		}
		this.powerUps.add(p);
	}

	//method that will move the bullets shot by a ship
	private void moveSalts(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Salt> bList = this.myShakey.getSalts();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Salt b = bList.get(i);
			/*
			 * TODO:  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			 */
			if(b.visible){
				b.move();
			}
			else{
				bList.remove(b);
			}
		}
	}

	//method that will move the fishes
	private void moveSlugs(){
		//Loop through the fishes arraylist
		for(int i = 0; i < this.slugs.size(); i++){
			Slug f = this.slugs.get(i);
			/*
			 * TODO:  *If a fish is alive, move the fish. Else, remove the fish from the fishes arraylist.
			 */
			if(f.isAlive()){
				f.move();
			}
		}
	}

	private void checkCollisions(){
		//Checks Bullet-Fish Collisions
		ArrayList<Salt> sList = this.myShakey.getSalts();
		//Double for loop for checking Bullet fish collisions
		for(int i = 0; i < sList.size(); i++){
			for(int j = 0; j < this.slugs.size(); j++){

				//double conditions for damaging/killing the fish depending if they are the boss or not
				if(this.slugs.get(j).collidesWith(sList.get(i)) && !sList.get(i).getHit() && this.slugs.get(j).isAlive()){
					if(this.slugs.get(j) instanceof BossSlug){ //damages boss slug
						BossSlug bf = (BossSlug) this.slugs.get(j);
						bf.damageBoss(sList.get(i).getDmg());
						if(bf.getHealth() <=0){
							bf.kill();
						}
					}
					else{//kills normal slugs
						this.slugs.get(j).kill();
						++this.slugsKilled;
					}
					//kills bullet
					sList.get(i).kill();
				}
			}
		}
		//Removes Bullets killed from bullet list
		for(int i = 0; i <sList.size();i++){
			if(sList.get(i).getHit()){
				sList.remove(i);
			}
		}
		//Checks Ship-Fish Collisions
		for(int j = 0; j < this.slugs.size(); j++){
			if(this.slugs.get(j).collidesWith(this.myShakey) && this.slugs.get(j).isAlive() && this.myShakey.isAlive()){
				//checks if shakey will get damaged if he's invulnerable or not
				if(!this.myShakey.getVulnerability()){
					this.myShakey.damage(this.slugs.get(j).getStrength());
				}
				//checks if fish is not the boss to despawn on impact with shakey
				if(!(this.slugs.get(j) instanceof BossSlug)){
					this.slugs.get(j).kill();
				}
				//adds invulnerability to shakey for 1 second when colliding with boss for balance reasons
				else{
					if(!this.myShakey.getVulnerability()){
						this.myShakey.setVulnerabilityTime(1);
						this.myShakey.setVulnerability(true);
					}
				}
			}
		}
		//Checks Ship-PowerUps Collisions
		for(int i = 0; i <this.powerUps.size();i++){
			if(this.powerUps.get(i).collidesWith(this.myShakey)){
				powerUps.get(i).triggerPowerUp();
				powerUps.remove(i);
			}

		}
	}

	//Draws game info in game
	private void updateStats(){
		//setting up style of text
		Font font = Font.font("Rockwell", FontWeight.BOLD, 20);
		this.gc.setFont(font);
		this.gc.setFill(Color.BLANCHEDALMOND);
		//main code for displaying stats
		this.gc.fillText("Timer: "+this.timer+"\nShakey Strength: "+this.myShakey.getStrength()+"\nSlugs Killed: "+this.slugsKilled, 0, GameStage.WINDOW_HEIGHT*0.05);
		//displays boss health
		if(this.bossSpawn && this.boss.isAlive()){
			this.gc.fillText("Boss Health: "+this.boss.getHealth(), GameStage.WINDOW_WIDTH*0.4, GameStage.WINDOW_HEIGHT*0.05);
		}
		//displays if boss has been killed
		else if (this.bossSpawn && !this.boss.isAlive()){
			this.gc.fillText("Boss has been slain!", GameStage.WINDOW_WIDTH*0.4, GameStage.WINDOW_HEIGHT*0.05);
		}
		//displays when Shakey is invulnerable
		if(this.myShakey.getVulnerability()){
			this.gc.fillText("Invulnerablility: "+this.myShakey.getVulnerabilityTime(), GameStage.WINDOW_WIDTH*0.4, GameStage.WINDOW_HEIGHT*0.2);
		}
	}


	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyShip(code);
		            }
		        });
    }

	//method that will move the ship depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP) this.myShakey.setDY(-3);

		if(ke==KeyCode.LEFT) this.myShakey.setDX(-3);

		if(ke==KeyCode.DOWN) this.myShakey.setDY(3);

		if(ke==KeyCode.RIGHT) this.myShakey.setDX(3);

		if(ke==KeyCode.SPACE) this.myShakey.shoot();

		System.out.println(ke+" key pressed.");
   	}

	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyShip(KeyCode ke){
		this.myShakey.setDX(0);
		this.myShakey.setDY(0);
	}
}
