package main.java.com.example;

public class Grid {
        private int height;
        private int occupyStatus;
        private boolean hasDome;

        public Grid() {
            this.height = 0;
            this.hasDome = false;
            this.occupyStatus = -1;
        }

        /**
         * Gets the height of the grid.
         * 
         * @return The height of the grid.
         */
        public int getHeight() {
            return height;
        }

        /**
         * Increases the height of the grid by one.
         */
        public void addHeight() {
            this.height += 1;
        }

        /**
         * Sets the height of the grid.
         * 
         * @param height The height to set for the grid.
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * Checks if the grid is occupied.
         * 
         * @return The occupation status of the grid.
         */
        public int getOccupyStatus() {
            return this.occupyStatus;
        }

        /**
         * Sets the occupation status of the grid.
         * 
         * @param occupyStatus The occupation status to set for the grid.
         */
        public void setOccupyStatus(int occupyStatus) {
            this.occupyStatus = occupyStatus;
        }

        /**
         * Checks if the grid has a dome.
         * 
         * @return {@code true} if the grid has a dome, otherwise {@code false}.
         */
        public boolean isHasDome() {
            return hasDome;
        }

        /**
         * Sets whether the grid has a dome.
         * 
         * @param hasDome {@code true} if the grid has a dome, otherwise {@code false}.
         */
        public void setHasDome(boolean hasDome) {
            this.hasDome = hasDome;
        }
}
