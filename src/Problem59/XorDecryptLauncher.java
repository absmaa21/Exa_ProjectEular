package Problem59;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class XorDecryptLauncher {
    private int[] loadData(String filePath) {
        int[] cipherAscii = new int[0];

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String[] strings = bufferedReader.readLine().split(",");
            cipherAscii = new int[strings.length];
            for(int i = 0; i < strings.length; i++)
                cipherAscii[i] = Integer.parseInt(strings[i]);

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cipherAscii;
    }

    private String runCalculation(int[] cipherAscii) {
        ExecutorService pool = Executors.newFixedThreadPool(26);
        XorDecryptWorker[] workersArray = new XorDecryptWorker[26];
        for(int i = 0; i < workersArray.length; i++)
            workersArray[i] = new XorDecryptWorker((char)(i + 97), cipherAscii);

        try {
            return pool.invokeAny(Arrays.stream(workersArray).toList());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }

        return "NO_KEY_FOUND";
    }

    public static void main(String[] args) {
        XorDecryptLauncher xorDecryptLauncher = new XorDecryptLauncher();
        int[] cipherAscii = xorDecryptLauncher.loadData("src/Problem59/cipher.txt");
        System.out.println("The correct key is: " + xorDecryptLauncher.runCalculation(cipherAscii));
    }
}
