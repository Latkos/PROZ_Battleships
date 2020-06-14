package Client;

/**
 * The enum Enum cell states.
 */
public enum EnumCellStates {
    /**
     * Blank - just blank.
     */
    BLANK,
    /**
     * Ship - here is a ship.
     */
    SHIP,
    /**
     * Missed - the enemy missed.
     */
    MISSED,
    /**
     * Shot - the enemy shoot the player ship.
     */
    SHOT,
    /**
     * Sank - the ship was here, but now it is sank.
     */
    SANK,
    /**
     * Prohibited - the player cannot set up a ship here.
     */
    PROHIBITED
}
