package Problem96;

import java.util.concurrent.Callable;

public class SudokuWorker implements Callable<Integer> {
    private final int[][] initSudoku;
    private final boolean useGoodSolution;

    public SudokuWorker(int[][] initSudoku, boolean useGoodSolution) {
        this.initSudoku = initSudoku;
        this.useGoodSolution = useGoodSolution;
    }

    @Override
    public Integer call() {
        return 0;
    }
}
