package Problem96;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SudokuLauncher {
    private List<int[][]> loadData() {
        List<int[][]> sudokuList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/Problem96/sudoku.txt"));
            int[][] currentSudoku = null;
            int currentY = 0;
            for (String line : reader.lines().toList()) {
                if (line.startsWith("Grid")) {
                    if (currentSudoku != null)
                        sudokuList.add(currentSudoku);
                    currentSudoku = new int[9][9];
                    currentY = 0;
                } else {
                    for (int i = 0; i < 9; i++) {
                        if (currentSudoku == null) currentSudoku = new int[9][9];
                        currentSudoku[i][currentY] = Character.getNumericValue(line.charAt(i));
                    }
                    currentY++;
                }
            }
            if (currentSudoku != null) sudokuList.add(currentSudoku);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sudokuList;
    }

    private int runSudokuCalculation(List<int[][]> sudokuList) {
        int sum = 0;

        ExecutorService pool = Executors.newFixedThreadPool(sudokuList.size());
        List<SudokuWorker> workers = new ArrayList<>();
        for (int[][] sudoku : sudokuList)
            workers.add(new SudokuWorker(sudoku));

        try {
            for (Future<Integer> x : pool.invokeAll(workers))
                sum += x.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        pool.shutdown();
        return sum;
    }

    public static void main(String[] args) {
        Date startTime = new Date();
        SudokuLauncher launcher = new SudokuLauncher();
        List<int[][]> sudokuList = launcher.loadData();
        System.out.println("Sum is: " + launcher.runSudokuCalculation(sudokuList));
        System.out.println("The calculation time was: " + (new Date().getTime() - startTime.getTime()) + "ms");
    }
}
