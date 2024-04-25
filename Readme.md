# Santorini Board Game

This is a frontend and backend program for the Santorini board game, allowing players to run and experience the online version of this board game locally. 
(Note: Please use main branch, not HW6 branch)

## How to Run

### Running the Backend

1. Navigate to the backend folder by executing `cd my-project`.
2. Install dependencies by running `mvn install`.
3. Call the `main` method of `s24-hw3-santorini-shuyingh07\my-project\src\main\java\main\java\com\example\App.java`.
4. The backend will run on port 8080 of your local machine.

### Running the Frontend

1. Navigate to the frontend folder by executing `cd frontend`.
2. Install dependencies by running `npm install`.
3. Start the frontend server by running `npm start`.
4. The frontend code will run on port 3000 of your local machine.

Open `localhost:3000` in your browser to access the application.

## Usage Instructions

- Click the "New Game" button in the middle to start a new game.
- Player1 and Player2 take turns initializing workers, moving workers, and building. Grids occupied by Player1 are displayed in yellow, and those occupied by Player2 are displayed in blue. The currently selected Grid is displayed in purple.
- Note: During the worker movement phase, first select the worker to move, then select the target Grid.
- The current height is displayed in the middle of each Grid. When the height is 4, a Dome is displayed, indicating that further construction is not possible.
- Below the board can check the currentPlayer and current action, and also notify the player whether the past action is valid.

Enjoy playing the online version of Santorini board game!

