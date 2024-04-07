/**
 * Grid interface defines the structure of data for each cell in the game grid.
 * It includes the properties to represent the state of a cell in the game.
 */
interface Grid {
    height: number; // The height of the cell, representing the height of the tower built on it.
    hasDome: boolean; // A boolean indicating whether the cell has a dome.
    occupyStatus: number; // Represents occupation status: 0 for player 0, 1 for player 1, and any other number for unoccupied.
}

/**
 * GameState interface defines the structure of the overall state of the game.
 * It includes properties that represent the current state of the game.
 */
interface GameState {
    grid: Grid[][]; // A 2D array representing the game grid, where each cell is represented by CellData.
    currentPlayer: number; // The ID of the current player, 0 or 1.
    status: string; // A string representing the current status of the game.
    isWinning: boolean; // A boolean indicating whether a winning condition has been met.
    message: string; // A messages about the game state.
}
export type { GameState, Grid }