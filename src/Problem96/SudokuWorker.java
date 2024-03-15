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
        initialize();
    }

    private void initialize() {
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
            position += insertPossibleNumber(position, sudoku[getCol(position)][getRow(position)]);

            if (lastPosition > position && wasInit[getCol(position)][getRow(position)]) {
                position = backtrack(position);
                continue;
            }

            if (position == 81 && !isSolved()) {
                logger.log("Sudoku has problems at last position.");
                logger.log(sudoku, wasInit);
                position = backtrack(position);
            }

            if (position == 81) {
                if (isSolved())
                    return calculateSolutionValue();
                logger.log("Sudoku was not possible to solve!");
                return 0;
            }
        }

        return calculateSolutionValue();
    }

    private int backtrack(int position) {
        while (wasInit[getCol(position)][getRow(position)]) {
            position--;
        }
        return position;
    }

    private int calculateSolutionValue() {
        logger.log("A Sudoku was solved! It took only " + (new Date().getTime() - startTimeOfWorker.getTime()) + "ms");
        return sudoku[0][0] * 100 + sudoku[1][0] * 10 + sudoku[2][0];
    }

    private int insertPossibleNumber(int position, int startNum) {
        if (wasInit[getCol(position)][getRow(position)])
            return 1;

        if (startNum > 0) {
            row[getRow(position)].remove(startNum);
            col[getCol(position)].remove(startNum);
            grid[getGridNum(position)].remove(startNum);
            sudoku[getCol(position)][getRow(position)] = 0;
        }

        for (int i = startNum + 1; i <= 9; i++) {
            if (!row[getRow(position)].contains(i) && !col[getCol(position)].contains(i) && !grid[getGridNum(position)].contains(i)) {
                sudoku[getCol(position)][getRow(position)] = i;
                row[getRow(position)].add(i);
                col[getCol(position)].add(i);
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
        return (getCol(position) / 3) + (getRow(position) / 3) * 3;
    }

    private int getCol(int position) {
        return position % 9;
    }

    private int getRow(int position) {
        return position / 9;
    }
}
