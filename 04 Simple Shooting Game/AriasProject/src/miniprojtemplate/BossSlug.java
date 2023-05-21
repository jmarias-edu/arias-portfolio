package miniprojtemplate;

import javafx.scene.image.Image;

public class BossSlug extends Slug{
	private int health;
	public final static int BOSS_WIDTH=200;
	public final static Image BOSS_SLUG_IMAGE = new Image("images/boss.png",BossSlug.BOSS_WIDTH,BossSlug.BOSS_WIDTH,false,false);

	//Constructor Method for BossFish
	public BossSlug(int x, int y) {
		super(x, y);
		this.strength = 50;
		this.health = 3000;
		this.loadImage(BOSS_SLUG_IMAGE);
		System.out.println("Boss Slug Spawned");
		this.width = BossSlug.BOSS_WIDTH;
	}

	//Method for damaging the boss
	public void damageBoss(int dmg){
		this.health -= dmg;
		System.out.println("Damaged boss! Health: "+this.health);
	}

	//getter method for returning the boss health
	public int getHealth(){
		return this.health;
	}

}
