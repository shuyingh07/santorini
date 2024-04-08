import React from 'react';

/**
 * Grid interface includes the properties to represent the state of a grid in the game.
 */
interface Grid {
    height: number; 
    hasDome: boolean;
    occupyStatus: number; // Represents occupation status: 0 for player 0, 1 for player 1
}

/**
 * GridInfo interface defines the props that the grid component expects.
 */
interface GridInfo {
    grid: Grid;
    onCellClick: (x: number, y: number) => void;
    x: number;
    y: number;
    className?: string;
}

const Cell: React.FC<GridInfo> = ({ grid, onCellClick, x, y, className }) => {
    const { height, hasDome, occupyStatus } = grid;
    
    let content = hasDome ? "Dome" : height.toString();
    let cellStyle = className;
    
    if (occupyStatus === 0) {
        cellStyle += " yellow-grid";
    } else if (occupyStatus === 1) {
        cellStyle += " blue-grid"; 
    }

    return (
        <button className={cellStyle} onClick={() => onCellClick(x, y)}>
            {content}
        </button>
    );
};

export { Cell, Grid };