import React from 'react';
import { Grid } from './game';

/**
 * CellProps interface defines the props that the Cell component expects.
 * It includes the cell data, a click handler function, coordinates (x, y), and an optional className for styling.
 */
interface CellProps {
    grid: Grid;
    onCellClick: (x: number, y: number) => void;
    x: number;
    y: number;
    className?: string;
}

/**
 * The Cell component represents an individual cell on the game board.
 * It displays the cell's content based on its level, dome status, and occupation.
 * Clicking on the cell triggers an action defined in onCellClick.
 */
const Cell: React.FC<CellProps> = ({ grid, onCellClick, x, y , className}) => {
    // Extract level, hasDome, and isOccupied from cellData
    const { height, hasDome, occupyStatus} = grid;

    /**
     * Generates the content to be displayed in the cell based on its level, dome, and occupation status.
     * Workers are represented as 'W0' or 'W1', a dome is represented as 'O',
     * and the level of the tower is indicated by square brackets around the content.
     */
    const generateContent = (level: number, hasDome: boolean, occupyStatus: number): string => {
        let baseContent = ' ';
        if (occupyStatus === 0) {
            baseContent = 'W0'; // Mark the cell as occupied by worker belongs to player 0
        } else if (occupyStatus === 1) {
            baseContent = 'W1'; // Mark the cell as occupied by worker belongs to player 1
        } else if (hasDome) {
            baseContent = 'O'; // Add a dome symbol if the cell has a dome
        }
        // Add brackets to represent the level of the tower
        for (let i = 0; i < level; i++) {
            baseContent = '[' + baseContent + ']';
        }
        return baseContent;
    };

    // Generate the content for this cell
    let content = generateContent(height, hasDome, occupyStatus);

    // Render the cell as a button with the generated content
    return (
        <button className={className} onClick={() => onCellClick(x, y)}>
            {content}
        </button>
    );
};

export default Cell;
