import React, {useState} from 'react';
import {Grid, Cell} from './Cell.tsx'
import './App.css';

/**
 * GameState interface defines the useful propoerties of the game.
 */
interface GameState {
    grid: Grid[][];
    currentPlayer: number;
    status: string;
    isWinning: boolean;
    message: string;
}

/**
 * Main functional component responsible for managing the game state, player actions, and rendering of the game board.
 */
const App: React.FC = () => {
    /**
     * Function to create the initial game grid.
     * @param size Size of the grid (default is 5x5)
     * @returns Initial grid for the game board
     */
    function createInitialGrid(size = 5): Grid[][] {
        return Array.from({ length: size }, () =>
            Array.from({ length: size }, () => ({
                height: 0,
                hasDome: false,
                occupyStatus: -1,
            }))
        );
    }
    
    
    // Initialize the game grid
    const initialGrid: Grid[][] = createInitialGrid();

    /// Set up the initial game state
    const initialGameState: GameState = {
        grid: initialGrid,
        currentPlayer: 0, 
        status: 'initialize',
        isWinning: false,
        message: "" 
    };

    // State hooks for managing game state
    const [gameState, setGameState] = useState<GameState>(initialGameState);
    const [selectedPiece, setSelectedPiece] = useState<{ x: number, y: number } | null>(null);


    /**
     * Handle clicks on cells in the game grid.
     * @param x X-coordinate of the clicked cell
     * @param y Y-coordinate of the clicked cell
     */
    const handleCellClick = async (x: number, y: number) => {
        console.log(`Cell clicked: (${x}, ${y})`);
        try {
            // No action if the game has a winner
            if (gameState.isWinning) {
                return;
            }

            if (selectedPiece === null) {
                if (gameState.status === 'build') {
                    const response = await fetch(`http://localhost:8080/build?x1=${x}&y1=${y}`);
                    await checkAndSetResponse(response);
                } else {
                    setSelectedPiece({x, y});
                }
            } else {
                let x1: number = selectedPiece.x;
                let y1: number = selectedPiece.y;
                if(gameState.status === 'initialize') {
                    const response = await fetch(`http://localhost:8080/initialize?x1=${x1}&y1=${y1}&x2=${x}&y2=${y}`);
                    await checkAndSetResponse(response);
                } else if (gameState.status === 'move') {
                    const response = await fetch(`http://localhost:8080/move?x1=${x1}&y1=${y1}&x2=${x}&y2=${y}`);
                    await checkAndSetResponse(response);
                }
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };


    /**
     * Starts a new game by resetting the game state.
     */
    const newGame = async () => {

        const response = await fetch(`http://localhost:8080/newGame`);
        await checkAndSetResponse(response);
    };

    /**
     * Helper function to check the server response and update the game state.
     * @param response Response from the server
     */
    const checkAndSetResponse = async (response: Response) => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const newGameState: GameState = await response.json();
        setGameState(newGameState);
        setSelectedPiece(null);
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'row' }}>
            <div style={{ width: '200px', padding: '10px', backgroundColor: '#f0f0f0', borderRight: '1px solid #ccc' }}>
                <h4>Tips</h4>
                <p><span style={{ color: 'yellow', fontWeight: 'bold' }}>Yellow:</span> Player1</p>
                <p><span style={{ color: 'lightblue', fontWeight: 'bold' }}>Blue:</span> Player2</p>
                <p><span style={{ color: 'purple', fontWeight: 'bold' }}>Purple:</span> Selected Grid</p>
            </div>
            <div style={{ flexGrow: 1 }}>
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
                                    className={selectedPiece && selectedPiece.x === rowIndex && selectedPiece.y === colIndex ? 'selected-grid' : ''}
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
        </div>
    );
}


export default App;
