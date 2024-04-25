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

    // 新添加的状态：是否使用神卡，选择的神卡列表
    const [useGodCards, setUseGodCards] = useState(false);  // 是否使用神卡
    const [godCards, setGodCards] = useState<string[]>([]);  // 已选择的神卡列表
    const [gameStarted, setGameStarted] = useState(false);
    const [playerGodCards, setPlayerGodCards] = useState<string[]>(['Null', 'Null']);  // 初始状态，两位玩家的神卡都未选择

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

    // 更新神卡使用的状态
    const handleUseGodCards = (value: boolean) => {
        setUseGodCards(value);
        if (!value) {
            setGodCards([]); // 如果不使用神卡，清空已选择的神卡列表
            setPlayerGodCards(['Null', 'Null']); // 不使用神卡时重置
        }
    };

    // 选择神卡
    const handleSelectGodCard = async (godCardName: string) => {
        const currentPlayer = gameState.currentPlayer;
        const updatedPlayerGodCards = [...playerGodCards];
        updatedPlayerGodCards[currentPlayer] = godCardName;
        setPlayerGodCards(updatedPlayerGodCards);
        setGodCards([...godCards, godCardName]);
        const response = await fetch(`http://localhost:8080/setGodClass?godCard=${godCardName}`);
        await checkAndSetResponse(response);
    };

    // 放弃额外行动
    const handlePassAction = async () => {
        const response = await fetch(`http://localhost:8080/passAction`);
        await checkAndSetResponse(response);
    };


    /**
     * Starts a new game by resetting the game state.
     */
    const newGame = async () => {
        // 重置游戏状态到初始状态
        setGameState(initialGameState);
        setSelectedPiece(null);
        setGodCards([]);  // 重置已选择的神卡列表
        setUseGodCards(false);  // 重置神卡使用状态
        setGameStarted(false);  // 允许重新启用神卡选择
        setPlayerGodCards(['Null', 'Null']);
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

        // 如果游戏状态不是'initialize'，设置gameStarted为true
        if (newGameState.status !== 'initialize') {
            setGameStarted(true);
        }
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'row', height: '100vh' }}>
            <div style={{ width: '200px', padding: '10px', backgroundColor: '#f0f0f0', borderRight: '1px solid #ccc' }}>
                <h4>Tips</h4>
                <p>Player 0: X</p>
                <p>Player 1: O</p>
                <p><span style={{ color: 'purple', fontWeight: 'bold' }}>Purple:</span> Selected Grid</p>
            </div>
            <div style={{ flexGrow: 1, display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
                <div>
                    <button onClick={newGame} className="new-game-button">New Game</button>
                    <button onClick={() => handleUseGodCards(!useGodCards)} className="use-god-card-button" disabled={gameStarted}>
                        {useGodCards ? "Disable God Cards" : "Enable God Cards"}
                    </button>
                    {useGodCards && (
                        <div>
                            <button onClick={() => handleSelectGodCard('Demeter')} disabled={gameStarted} className="god-card-button">Demeter</button>
                            <button onClick={() => handleSelectGodCard('Pan')} disabled={gameStarted} className="god-card-button">Pan</button>
                            <button onClick={() => handleSelectGodCard('Hephaestus')} disabled={gameStarted} className="god-card-button">Hephaestus</button>
                            <button onClick={() => handleSelectGodCard('Minotaur')} disabled={gameStarted} className="god-card-button">Minotaur</button>
                            <button onClick={() => handleSelectGodCard('Apollo')} disabled={gameStarted} className="god-card-button">Apollo</button>
                            <button onClick={handlePassAction} className="pass-button">Pass Action</button>
                        </div>
                    )}
                    <div className="game-board">
                        {gameState.grid.map((row, rowIndex) => (
                            <div key={rowIndex} className="board-row">
                                {row.map((cell, colIndex) => (
                                    <Cell key={`${rowIndex}-${colIndex}`} grid={cell} onCellClick={() => handleCellClick(rowIndex, colIndex)} x={rowIndex} y={colIndex}
                                        className={selectedPiece && selectedPiece.x === rowIndex && selectedPiece.y === colIndex ? 'selected-grid' : ''} />
                                ))}
                            </div>
                        ))}
                    </div>
                    <div className="game-state-message">{'Message: ' + gameState.message}</div>
                    <div className="game-info">
                        {gameState.isWinning ? `Player ${gameState.currentPlayer} wins!` : `Player ${gameState.currentPlayer}'s Turn, Status: ${gameState.status}`}
                    </div>
                </div>
            </div>
            <div style={{ width: '200px', padding: '10px', backgroundColor: '#f0f0f0', borderLeft: '1px solid #ccc' }}>
                <h4>God Cards</h4>
                <p>Player0 God Class: {playerGodCards[0]}</p>
                <p>Player1 God Class: {playerGodCards[1]}</p>
            </div>
        </div>
    );
}


export default App;
