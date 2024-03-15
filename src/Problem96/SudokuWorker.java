package Problem96;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class SudokuWorker implements Callable<Integer> {
    private final int[][] sudoku;
    private final Set<Integer>[] grid = new HashSet[9];
    private final Set<Integer>[] row = new HashSet[9];
    private final Set<Integer>[] col = new HashSet[9];
    private final boolean[][] wasInit = new boolean[9][9];
    private final Date startTimeOfWorker = new Date();
    private final Logger logger = Logger.getInstance();

    public SudokuWorker(int[][] sudoku) {
        this.sudoku = sudoku;
        for (int i = 0; i < 9; i++) {
            grid[i] = new HashSet<>();
            col[i] = new HashSet<>();
            row[i] = new HashSet<>();
        }

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (this.sudoku[x][y] == 0) {
                    wasInit[x][y] = false;
                    continue;
                }

                wasInit[x][y] = true;
                row[y].add(sudoku[x][y]);
                col[x].add(sudoku[x][y]);
                grid[getGridNum(x, y)].add(sudoku[x][y]);
            }
        }
    }

    @Override
    public Integer call() {
        int position = 0;
        while (position < 81) {
            if (position < 0) {
                logger.log("Sudoku had to restart entirely.");
                logger.log(sudoku, wasInit);
                position = 0;
                sudoku[0][0]++;
            }

            int lastPosition = position;
            position += insertPossibleNumber(position, sudoku[getX(position)][getY(position)]);

            if (lastPosition > position && wasInit[getX(position)][getY(position)]) {
                //logger.log("Go multiple back");
                while (wasInit[getX(position)][getY(position)]) {
                    position--;
                }
                continue;
            }

            if (position == 81 && !isSolved()) {
                logger.log("Sudoku has problems at last position.");
                logger.log(sudoku, wasInit);
                position--;
            }

            if (position == 81) {
                if (isSolved()) {
                    logger.log("A Sudoku was solved! It took only " + (new Date().getTime() - startTimeOfWorker.getTime()) + "ms");
                    return sudoku[0][0] * 100 + sudoku[1][0] * 10 + sudoku[2][0];
                }
                logger.log("Sudoku was not possible to solve!");
                return 0;
            }
        }

        logger.log("A Sudoku was solved! It took only " + (new Date().getTime() - startTimeOfWorker.getTime()) / 1000 + "s");
        return sudoku[0][0] * 100 + sudoku[1][0] * 10 + sudoku[2][0];
    }

    private int insertPossibleNumber(int position, int startNum) {
        if (wasInit[getX(position)][getY(position)])
            return 1;

        if (startNum > 0) {
            row[getY(position)].remove(startNum);
            col[getX(position)].remove(startNum);
            grid[getGridNum(position)].remove(startNum);
            sudoku[getX(position)][getY(position)] = 0;
        }

        for (int i = startNum + 1; i <= 9; i++) {
            if (!row[getY(position)].contains(i) && !col[getX(position)].contains(i) && !grid[getGridNum(position)].contains(i)) {
                sudoku[getX(position)][getY(position)] = i;
                row[getY(position)].add(i);
                col[getX(position)].add(i);
                grid[getGridNum(position)].add(i);
                return 1;
            }
        }

        return -1;
    }

    private boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            if (row[i].size() != 9 || col[i].size() != 9 || grid[i].size() != 9)
                return false;
        }
        return true;
    }

    private int getGridNum(int x, int y) {
        return (x / 3) + (y / 3) * 3;
    }

    private int getGridNum(int position) {
        return (getX(position) / 3) + (getY(position) / 3) * 3;
    }

    private int getX(int position) {
        return position % 9;
    }

    private int getY(int position) {
        return position / 9;
    }
}
