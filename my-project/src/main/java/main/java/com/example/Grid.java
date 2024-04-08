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
         * Gets the height of the cell.
         * 
         * @return The height of the cell.
         */
        public int getHeight() {
            return height;
        }

        /**
         * Gets the height of the cell.
         * 
         * @return The height of the cell.
         */
        public void addHeight() {
            this.height += 1;
        }

        /**
         * Sets the height of the cell.
         * 
         * @param height The height to set for the cell.
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * Checks if the cell is occupied.
         * 
         * @return The occupation status of the cell.
         */
        public int getOccupyStatus() {
            return this.occupyStatus;
        }

        /**
         * Sets the occupation status of the cell.
         * 
         * @param occupyStatus The occupation status to set for the cell.
         */
        public void setOccupyStatus(int occupyStatus) {
            this.occupyStatus = occupyStatus;
        }

        /**
         * Checks if the cell has a dome.
         * 
         * @return {@code true} if the cell has a dome, otherwise {@code false}.
         */
        public boolean isHasDome() {
            return hasDome;
        }

        /**
         * Sets whether the cell has a dome.
         * 
         * @param hasDome {@code true} if the cell has a dome, otherwise {@code false}.
         */
        public void setHasDome(boolean hasDome) {
            this.hasDome = hasDome;
        }
}