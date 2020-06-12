package client;

public class Game
{
    public enum GameState { SHIPS_SETUP, WAITING_FOR_SERVER, PLAYER_MOVE, ENEMY_MOVE, BLOCK, WIN, LOSE};

    private PlayerBoard playerBoard;
    private EnemyBoard enemyBoard;
    private GameState gameState;

    Game()
    {
        gameState = GameState.SHIPS_SETUP;
        playerBoard = new PlayerBoard();
        enemyBoard = new EnemyBoard();
    }

    public PlayerBoard getPlayerBoard()
    {
        return playerBoard;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public EnemyBoard getEnemyBoard()
    {
        return enemyBoard;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }
}