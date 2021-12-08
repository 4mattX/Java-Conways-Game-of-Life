public class GameOfLife {

    private boolean[][] cellGrid;
    private boolean[][] cellGrid2;
    private int cellDivider = 10;
    int rows = Display.getHEIGHT() / cellDivider;
    int cols = Display.getWIDTH() / cellDivider;

    public GameOfLife() {
        cellGrid = new boolean[rows][cols];
        cellGrid2 = new boolean[rows][cols];
    }

    public void updateCell(int x, int y) {
        if (cellGrid[x][y] == true) {
            cellGrid[x][y] = false;
            cellGrid2[x][y] = false;
            return;
        }
        cellGrid[x][y] = true;
        cellGrid2[x][y] = true;
    }

    public void updateLife() {
        copyGrid();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                try {
                    if (cellGrid[i][j] == true) {
                        underPopulate(i, j);
                        overPopulate(i, j);
                    } else {
                        reproduction(i, j);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    public void resetLife() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cellGrid[i][j] = false;
                cellGrid2[i][j] = false;
            }
        }
    }

    private void copyGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cellGrid[i][j] = cellGrid2[i][j];
            }
        }
    }

    public void underPopulate(int x, int y) {

        int amountLive = 0;

        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {

                if (i == x && j == y) {
                    continue;
                }

                if (cellGrid[i][j] == true) {
                    amountLive++;
                }

            }
        }

        if (amountLive < 2) {
            cellGrid2[x][y] = false;
            return;
        }

    }

    public void overPopulate(int x, int y) {

        int amountLive = 0;

        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {

                if (i == x && j == y) {
                    continue;
                }

                if (cellGrid[i][j] == true) {
                    amountLive++;
                }

            }
        }

        if (amountLive > 3) {
            cellGrid2[x][y] = false;
        }

    }

    public void reproduction(int x, int y) {
        int amountLive = 0;

        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {

                if (i == x && j == y) {
                    continue;
                }

                if (cellGrid[i][j] == true) {
                    amountLive++;
                }

            }
        }

        if (amountLive == 3) {
            cellGrid2[x][y] = true;
        }
    }


    public boolean[][] getCellGrid() {
        return cellGrid;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getDivider() {
        return cellDivider;
    }
}
