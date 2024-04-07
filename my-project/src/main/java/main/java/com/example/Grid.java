package main.java.com.example;

public class Grid {
        private int height;
        private int occupyStatus;
        private boolean hasDome;

        /**
         * Default constructor for Grid.
         * Initializes the cell data with default values.
         */
        public Grid() {
            this.height = 0;
            this.hasDome = false;
            this.occupyStatus = -1;
        }

        // Getters and setters
        /**
         * Retrieves the height of the cell.
         * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
         * @return The height of the cell.
         */
        public int getHeight() {
            return height;
        }

        // Getters and setters
        /**
         * Retrieves the height of the cell.
         * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
         * @return The height of the cell.
         */
        public void addHeight() {
            this.height += 1;
        }

        /**
         * Sets the height of the cell.
         * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
         * @param height The height to set for the cell.
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * Checks if the cell is occupied.
         * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
         * @return The occupation status of the cell.
         */
        public int getOccupyStatus() {
            return this.occupyStatus;
        }

        /**
         * Sets the occupation status of the cell.
         * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
         * @param occupy_status The occupation status to set for the cell.
         */
        public void setOccupyStatus(int occupyStatus) {
            this.occupyStatus = occupyStatus;
        }

        /**
         * Checks if the cell has a dome.
         * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
         * @return {@code true} if the cell has a dome, otherwise {@code false}.
         */
        public boolean isHasDome() {
            return hasDome;
        }

        /**
         * Sets whether the cell has a dome.
         * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
         * @param hasDome {@code true} if the cell has a dome, otherwise {@code false}.
         */
        public void setHasDome(boolean hasDome) {
            this.hasDome = hasDome;
        }
}