import java.awt.Image;
import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. 
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 *  a base class for the other spaceships or any other option you will choose.
 *  
 * @author oop
 */
public class SpaceShip{
    /** The human-controlled spaceship physics*/
    protected SpaceShipPhysics physics;

    /** A health level between 0 and 22*/
    protected int health;

    /** If the shield is on its true, false otherwise*/
    protected boolean shielded;

    /** A maximal energy level*/
    protected int maximalEnergy;

    /** A current energy level, which is between 0 and the maximal energy level*/
    protected int currentEenergy;

    private int roundCounter;

    /** The energy price of firing (CONST)*/
    private final int shieldPrice = 3;

    /** The energy price of firing (CONST)*/
    private final int firePrice = 19;

    /** The energy price of using the teleport (CONST)*/
    private final int teleportPrice = 140;

    /** Getting hit price*/
    private final int reduceMaxEnergy = 10;

    /** bashing price (CONST)*/
    private final int bashedPrice = 18;

    /**
     * Creates a new instance of a HumanShip with the given params.
     *
     * @param maximalEnergy integer.
     * @param currentEenergy integer 0 - maximal energy
     * @param integer health level 0 - 22
     */
    public SpaceShip(int maximalEnergy, int currentEenergy, int health) {
        this.health = health;
        this.physics = new SpaceShipPhysics();
        this.shielded = false;
        this.maximalEnergy = maximalEnergy;
        this.currentEenergy = currentEenergy;
        this.shielded = false;
        this.roundCounter = 0;
    }

    public SpaceShip(int health) {
        this.health = health;
        this.physics = new SpaceShipPhysics();
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage(){
        if (this.shielded) return GameGUI.SPACESHIP_IMAGE_SHIELD;
        else return GameGUI.SPACESHIP_IMAGE;
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        if (this.roundCounter > 0){
            this.roundCounter--;
        }
        if (this.currentEenergy < this.maximalEnergy) {
            this.currentEenergy++; // charging
        }
    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip(){
        gettingHit();
        if (this.shielded) {
            this.maximalEnergy += bashedPrice;
            this.currentEenergy += bashedPrice;
        }
    }

    /**
     * This method is called whenever the ship is either collided
     * with another ship or is getting hit by a fire shot.
     */
    private void gettingHit(){
        if (this.shielded){
            // if the shields are down then:
            if (this.currentEenergy > (this.maximalEnergy - reduceMaxEnergy)) {
                this.maximalEnergy -= reduceMaxEnergy;
                this.currentEenergy = this.maximalEnergy;
            }
            else {
                this.maximalEnergy -= reduceMaxEnergy;
            }
        }
        else {
            this.health --;
        }
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        // resets all the given params
        this.maximalEnergy = SpaceShipFactory.MAXIMALENERGY;
        this.currentEenergy = SpaceShipFactory.CURRENTENERGY;
        this.health = SpaceShipFactory.HEALTH;
        this.physics = new SpaceShipPhysics(); // re-create the physics
        this.roundCounter = 0; // The firing cool-down reset
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return (this.health == 0);
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.physics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        gettingHit();
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if ((this.currentEenergy >= this.firePrice) && (roundCounter == 0)) {
            this.currentEenergy -= this.firePrice;
            game.addShot(this.physics);
            this.roundCounter = 7;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (this.currentEenergy >= this.shieldPrice) {
            this.currentEenergy -= this.shieldPrice;
            this.shielded = true;
        }
        else this.shielded = false;
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (this.currentEenergy >= this.teleportPrice) {
            this.physics = new SpaceShipPhysics();
            this.currentEenergy -= this.teleportPrice;
        }
    }

    /**
     * changes the health field value.
     * @param heal change the health to this parameter
     */
    public void changeHealth(int heal) {
        this.health = heal;
    }

    /**
     * Setter for the current energy
     * @param newEnergy new energy value. (int)
     */
    public void changeEnergy(int newEnergy) {
        this.currentEenergy = newEnergy;
    }
}