package Problem59;

import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class XorDecryptWorker implements Callable<String> {
    private final char firstChar;
    private final int[] cipher;

    public XorDecryptWorker(char firstChar, int[] cipher) {
        this.firstChar = firstChar;
        this.cipher = cipher;
    }

    @Override
    public String call() {
        System.out.println("Thread started with key: " + firstChar);
        StringBuilder string;

        for (char secChar = 'a'; secChar <= 'z'; secChar++) {
            for (char thirdChar = 'a'; thirdChar <= 'z'; thirdChar++) {
                string = new StringBuilder();
                int lengthForTesting = Math.min(cipher.length, 10);
                for (int i = 1; i <= lengthForTesting; i++) {
                    if (i % 3 == 0) {
                        string.append((char) (cipher[i - 1] ^ thirdChar));
                    } else if (i % 2 == 0) {
                        string.append((char) (cipher[i - 1] ^ secChar));
                    } else {
                        string.append((char) (cipher[i - 1] ^ firstChar));
                    }
                }
                int tester = 0;
                for (int i = 0; i < string.length(); i++) {
                    if (
                        (string.charAt(i) >= 'A' && string.charAt(i) <= 'Z') ||
                        (string.charAt(i) >= 'a' && string.charAt(i) <= 'z') || string.charAt(i) == ' '
                    )
                        tester++;
                }

                if (tester == string.length())
                    System.out.println(string);
            }
        }
        return null;
    }
}
