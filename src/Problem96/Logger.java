package Problem96;

import java.util.Date;

public class Logger {
    private static final Logger instance = new Logger();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static Logger getInstance() {
        return instance;
    }

    public void log(String message) {
        synchronized (instance) {
            System.out.println(new Date() + " : " + message);
        }
    }

    public void log(int[][] sudoku, boolean[][] wasInit) {
        synchronized (instance) {
            for (int i = 0; i < 81; i++) {
                if (i % 9 == 0 && i > 0) System.out.println();

                if (wasInit[i % 9][i / 9])
                    System.out.print(ANSI_RED + sudoku[i % 9][i / 9] + " " + ANSI_RESET);
                else
                    System.out.print(sudoku[i % 9][i / 9] + " ");
            }
            System.out.println("\n");
        }
    }

    public void log(int[][] sudoku) {
        synchronized (instance) {
            for (int i = 0; i < 81; i++) {
                if (i % 9 == 0 && i > 0) System.out.println();
                System.out.print(sudoku[i % 9][i / 9] + " ");
            }
            System.out.println("\n");
        }
    }
}
