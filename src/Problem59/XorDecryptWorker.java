package Problem59;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class XorDecryptWorker implements Callable<String> {
    private static final Set<String> COMMON_WORDS = new HashSet<>(Arrays.asList(
            "the", "be", "to", "of", "and", "a", "in", "that", "have", "I", "it", "for", "not", "on", "with", "he", "as", "you", "do", "at", "this", "but", "his", "by", "from", "they", "we", "say", "her", "she", "or", "an", "will", "my", "one", "all", "would", "there", "their", "what", "so", "up", "out", "if", "about", "who", "get", "which", "go", "me", "when", "make", "can", "like", "time", "no", "just", "him", "know", "take", "people", "into", "year", "your", "good", "some", "could", "them", "see", "other", "than", "then", "now", "look", "only", "come", "its", "over", "think", "also", "back", "after", "use", "two", "how", "our", "work", "first", "well", "way", "even", "new", "want", "because", "extract", "any", "these", "give", "day", "most", "us"
    ));

    private final byte firstChar;
    private final int[] cipher;

    public XorDecryptWorker(char firstChar, int[] cipher) {
        this.firstChar = (byte) firstChar;
        this.cipher = cipher;
    }

    @Override
    public String call() {
        System.out.println("Thread started with key: " + (char)firstChar);
        StringBuilder string;
        byte[] key = new byte[3];
        key[0] = firstChar;

        for (key[1] = 'a'; key[1] <= 'z'; key[1]++) {
            for (key[2] = 'a'; key[2] <= 'z'; key[2]++) {
                string = decryptWithKey(key);
                int tester = countCommonWords(string);

                if (tester > string.toString().split(" ").length * .2) {
                    System.out.println(new String(key) + " : " + string);
                    int AsciiSum = 0;
                    for(int i = 0; i < string.length(); i++)
                        AsciiSum += string.charAt(i);
                    System.out.println("Ascii Value Sum : " + AsciiSum);
                    return new String(key);
                }
            }
        }

        return null;
    }

    private StringBuilder decryptWithKey(byte[] key) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < cipher.length; i++) {
            decrypted.append((char) (cipher[i] ^ key[i % 3]));
        }
        return decrypted;
    }

    private int countCommonWords(StringBuilder text) {
        int count = 0;
        String[] words = text.toString().split(" ");
        for (String word : words) {
            if (COMMON_WORDS.contains(word.toLowerCase())) {
                count++;
            }
        }
        return count;
    }
}
