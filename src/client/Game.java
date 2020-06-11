package client;

public class Game
{
    public enum GameState { SHIPS_SETUP, WAITING_FOR_WHO_IS_FIRST, PLAYER_MOVE, ENEMY_MOVE, WIN, LOSE};

    private PlayerBoard playerBoard;
    private EnemyBoard enemyBoard;
    private GameState gameState;

    Game()
    {
        gameState = GameState.SHIPS_SETUP;
        playerBoard = new PlayerBoard();
        enemyBoard = new EnemyBoard();
    }

    PlayerBoard getPlayerBoard()
    {
        return playerBoard;
    }

    GameState getGameState()
    {
        return gameState;
    }
}
