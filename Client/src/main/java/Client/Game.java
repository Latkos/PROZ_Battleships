package Client;

/**
 * The type Game.
 */
public class Game
{
    /**
     * The enum Game state.
     */
    public enum GameState {
        /**
         * Ships setup game state.
         */
        SHIPS_SETUP,
        /**
         * Waiting for server game state.
         */
        WAITING_FOR_SERVER,
        /**
         * Player move game state.
         */
        PLAYER_MOVE,
        /**
         * Enemy move game state.
         */
        ENEMY_MOVE,
        /**
         * Block game state.
         */
        BLOCK,
        /**
         * Win game state.
         */
        WIN,
        /**
         * Lose game state.
         */
        LOSE};

    private PlayerBoard playerBoard;
    private EnemyBoard enemyBoard;
    private GameState gameState;

    /**
     * Instantiates a new Game.
     */
    Game()
    {
        gameState = GameState.SHIPS_SETUP;
        playerBoard = new PlayerBoard();
        enemyBoard = new EnemyBoard();
    }

    /**
     * Gets player board.
     *
     * @return the player board
     */
    public PlayerBoard getPlayerBoard()
    {
        return playerBoard;
    }

    /**
     * Gets game state.
     *
     * @return the game state
     */
    public GameState getGameState()
    {
        return gameState;
    }

    /**
     * Gets enemy board.
     *
     * @return the enemy board
     */
    public EnemyBoard getEnemyBoard()
    {
        return enemyBoard;
    }

    /**
     * Sets game state.
     *
     * @param gameState the game state
     */
    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }
}