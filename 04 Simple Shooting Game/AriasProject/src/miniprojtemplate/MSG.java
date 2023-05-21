package miniprojtemplate;

public class MSG extends PowerUp{
	public final static int MSG_VALUE = 50;


	public MSG(int xPos, int yPos, Shakey ship) {
		super(xPos, yPos, ship);
		this.loadImage(PowerUp.MSG_IMAGE);
	}

	//Adds strength to Shakey
	public void triggerPowerUp(){
		this.myShakey.addStrength(MSG.MSG_VALUE);
	}
}
