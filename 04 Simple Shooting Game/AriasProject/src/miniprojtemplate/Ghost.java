package miniprojtemplate;

public class Ghost extends PowerUp{

	public Ghost(int xPos, int yPos, Shakey ship) {
		super(xPos, yPos, ship);
		this.loadImage(PowerUp.GHOST_IMAGE);
	}

	//Makes ship invulnerable for 3 seconds
	public void triggerPowerUp(){
		this.myShakey.setVulnerabilityTime(3);
		this.myShakey.setVulnerability(true);
	}
}
