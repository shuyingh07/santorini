import React, {useState} from 'react';
import {Grid, GameState} from './game.tsx';
import Cell from './Cell.tsx'
import './App.css';

/**
 * The main React functional component for the board game.
 * It handles the game state, player actions, and rendering of the game board.
 * The component manages the initialization, movement, and building actions,
 * as well as the handling and selection of god cards.
 */
const App: React.FC = () => {
    /**
     * Initialize the game grid with a default structure for each cell.
     * Cells have properties: level, dome status, and occupation status.
     */
    const initialGrid: Grid[][] = Array.from({ length: 5 }, () =>
        Array.from({ length: 5 }, () => ({
            height: 0, // Initial level of construction
            hasDome: false, // Dome status, false initially
            occupyStatus: -1, // 2 means not occupied, 0 means occupied by player 0, 1 means occupied by player 1
        }))
    );

    /**
     * Set up the initial game state with default values.
     * Includes grid, current player, game status, winning condition, and message.
     */
    const initialGameState: GameState = {
        grid: initialGrid,
        currentPlayer: 0, // Start with player 0
        status: 'initialize', // Initial status for game setup
        isWinning: false, // Flag to track if winning condition is met
        message: "" // Game state message
    };

    // State hooks for managing game state
    const [gameState, setGameState] = useState<GameState>(initialGameState);
    const [selectedPiece, setSelectedPiece] = useState<{ x: number, y: number } | null>(null); // Track selected piece
    const [gameStarted, setGameStarted] = useState<boolean>(false); // Flag to track if the game has started
  

    /**
     * Function to handle passing an action.
     * Sends a request to pass the current action and updates the game state.
     */
    const handlePassAction = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/passAction?`);
            await checkAndSetResponse(response);
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };


    /**
     * Handle clicks on cells in the game grid.
     * Depending on the game state, different actions are triggered (initialize, move, build).
     */
    const handleCellClick = async (x: number, y: number) => {
        console.log(`Cell clicked: (${x}, ${y})`);
        try {
            // No action if the game is over
            if (gameState.isWinning) {
                return;
            }

            if (selectedPiece === null) {
                // Perform build action if status is build
                if (gameState.status === 'build') {
                    const response = await fetch(`http://localhost:8080/api/build?x1=${x}&y1=${y}`);
                    await checkAndSetResponse(response);
                } else {
                    // set the selected cell, which will be highlighted
                    setSelectedPiece({x, y});
                }
            } else {
                // Coordinates of the previously selected piece
                let x1: number = selectedPiece.x;
                let y1: number = selectedPiece.y;
                // Perform actions based on the game's current status
                if(gameState.status === 'initialize') {
                    // Initialize worker positions on the board
                    const response = await fetch(`http://localhost:8080/api/initialize?x1=${x1}&y1=${y1}&x2=${x}&y2=${y}`);
                    await checkAndSetResponse(response);
                } else if (gameState.status === 'move') {
                    // Move worker from selected position to clicked position
                    const response = await fetch(`http://localhost:8080/api/move?x1=${x1}&y1=${y1}&x2=${x}&y2=${y}`);
                    await checkAndSetResponse(response);
                }
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };


    /**
     * Starts a new game.
     * Resets the game state and fetches the initial state from the server.
     */
    const newGame = async () => {
        // Set the game as started and reset god cards state
        setGameStarted(true)

        // Fetch the initial game state from the server
        const response = await fetch(`http://localhost:8080/api/newGame`);
        await checkAndSetResponse(response);
    };

    /**
     * Helper function to check the server response and update the game state.
     */
    const checkAndSetResponse = async (response: Response) => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        // If the response is OK, parse it as JSON which returns the new game state
        const newGameState: GameState = await response.json();
        setGameState(newGameState); // Update game state with response data
        setSelectedPiece(null); // Reset selected piece after state update
    };

    return (
      <div>
          <button onClick={newGame} className="new-game-button">New Game</button>
          <div className="game-board">
              {gameState.grid.map((row, rowIndex) => (
                  <div key={rowIndex} className="board-row">
                      {row.map((grid, colIndex) => (
                          <Cell
                              key={`${rowIndex}-${colIndex}`}
                              grid={grid}
                              onCellClick={handleCellClick}
                              x={rowIndex}
                              y={colIndex}
                              className={selectedPiece && selectedPiece.x === rowIndex && selectedPiece.y === colIndex ? 'selected-cell' : ''}
                          />
                      ))}
                  </div>
              ))}
          </div>
          <div className="game-state-message">
              {'Message: ' + gameState.message}
          </div>
          <div className="game-info">
              {gameState.isWinning ? `Player ${gameState.currentPlayer} wins!` : `Player ${gameState.currentPlayer}'s Turn, Status: ${gameState.status}`}
          </div>
      </div>
  );
}


export default App;
