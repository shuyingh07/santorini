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

    let content;
    if (hasDome) {
        content = "Dome"; // 如果有穹顶，则显示 "Dome"
    } else {
        let playerSymbol = '';
        if (occupyStatus === 0) {
            playerSymbol = 'X'; // 玩家1占领
        } else if (occupyStatus === 1) {
            playerSymbol = 'O'; // 玩家2占领
        }

        // 根据高度决定显示的内容，同时加入玩家标记
        switch (height) {
            case 0:
                content = playerSymbol; // 高度为 0，仅显示玩家标记
                break;
            case 1:
                content = `[${playerSymbol}]`; // 高度为 1
                break;
            case 2:
                content = `[[${playerSymbol}]]`; // 高度为 2
                break;
            default:
                content = `[${"[".repeat(height - 1)}${playerSymbol}${"]".repeat(height - 1)}]`; // 更高的高度，使用重复的括号包围玩家标记
                break;
        }
    }

    return (
        <button className={className} onClick={() => onCellClick(x, y)}>
            {content}
        </button>
    );
};

export { Cell, Grid };