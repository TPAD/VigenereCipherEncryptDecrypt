import java.util.Scanner;

/**
 * Encryptor - CLI program that uses Vigenère cipher to encode and decode messages provided by the user
 * given a key also provided by the user.
 *
 * The famous computer scientist who designed the difference engine and was able to crack this encryption
 * technique is:
 *
 * CHARLES BABBAGE
 *
 */
public class Encryptor {

    private static final String ENCODE = "E";
    private static final String DECODE = "D";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int Z = 90;
    private static final int A = 65;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        boolean end = false;
        while (!end) {
            System.out.println("Enter a message (to quit press RETURN only):");
            String msg = console.nextLine();
            if (msg.isEmpty()) {
                System.out.println("Bye!");
                end = true;
            } else {
                String keyPhrase = getPhrase(console);
                String action = getAction(console);
                String cleanKey = cleanString(keyPhrase);
                String cleanMsg = cleanString(msg);
                if (action.equals(ENCODE)) {
                    String encodedMsg = encode(cleanMsg, cleanKey);
                    System.out.println("message:\t\t\t" + cleanMsg);
                    System.out.println("key phrase:\t\t\t" + cleanKey);
                    System.out.println("translation:\t\t" + encodedMsg);
                }
                else if (action.equals(DECODE)){
                    String decodedMsg = decode(cleanMsg, cleanKey);
                    System.out.println("message:\t\t\t" + cleanMsg);
                    System.out.println("key phrase:\t\t\t" + cleanKey);
                    System.out.println("translation:\t\t" + decodedMsg);
                }
            }
        }
    }

    /**
     * Converts all lowercase letters in str to uppercase and removes all non-letter characters.
     * @param str the string to convert
     * @return a string of only capital letters
     */
    private static String cleanString(String str) {
        return str.replaceAll("[^a-zA-Z]", "").toUpperCase();
    }

    /**
     * Encodes a message given a keyphrase. Assumes both the message and keyphrase are comprised of only
     * capital letters.
     * @param msg the message to encode
     * @param keyPhrase the key to use for encoding
     * @return the encoded message
     */
    private static String encode(String msg, String keyPhrase) {
        StringBuilder sb = new StringBuilder();
        String newKey = getEqualLengthKey(msg, keyPhrase);
        for (int i = 0; i < newKey.length(); i++) {
            char row = newKey.charAt(i);
            char col = msg.charAt(i);
            int idx = ALPHABET.indexOf(row);
            int sum = col + idx;
            if (sum > Z) {
                int diff = sum - Z;
                sb.append((char) (A + diff - 1));
            } else
                sb.append((char) (col + idx));
        }
        return sb.toString();
    }

    /**
     * Decodes a message given a keyphrase. Assumes both the message and the keyphrase are comprised of only
     * capital letters.
     * @param msg the message to decode
     * @param phrase the key to use for decoding
     * @return the decoded message
     */
    private static String decode(String msg, String phrase) {
        StringBuilder sb = new StringBuilder();
        String newKey = getEqualLengthKey(msg, phrase);
        for (int i = 0; i < newKey.length(); i++) {
            char row = newKey.charAt(i);
            char enc = msg.charAt(i);
            int diff = enc - row;
            if (diff >= 0)
                sb.append(ALPHABET.charAt(diff));
            else {
                int idx = ALPHABET.length() + diff;
                sb.append(ALPHABET.charAt(idx));
            }
        }
        return sb.toString();
    }

    /**
     * Helper method that converts the keyphrase to a string of equal length to the message.
     * Assumes key and message strings are "clean".
     * @param msg the message to match
     * @param phrase the phrase being converted
     * @return a keyphrase of equal length to the message for encoding/decoding
     */
    private static String getEqualLengthKey(String msg, String phrase) {
        StringBuilder sb = new StringBuilder();
        int idxPhrase = 0;
        for (int i = 0; i < msg.length(); i++) {
            if (idxPhrase == phrase.length())
                idxPhrase = 0;
            sb.append(phrase.charAt(idxPhrase));
            idxPhrase++;
        }
        return sb.toString();
    }

    /**
     * Helper method that polls the user to enter a non-blank key phrase.
     * @param console a scanner obj that reads from standard input (ASSUMPTION)
     * @return a non-blank key phrase
     */
    private static String getPhrase(Scanner console) {
        System.out.println("Enter a keyphrase:");
        String result = console.nextLine();
        if (result.isEmpty()) {
            System.out.println("Please enter a non-blank key phrase.");
            return getPhrase(console);
        }
        return result;
    }

    /**
     * Helper method that polls the user to enter a non-blank action valid action. The valid actions are
     * e/E for encode and d/D for decode.
     * @param console a scanner obj that reads from standard input (ASSUMPTION)
     * @return a valid encode/decode action
     */
    private static String getAction(Scanner console) {
        System.out.println("Do you wish to encode (E) or decode (D)?");
        String line = console.nextLine();
        String result = line.toUpperCase();
        if (result.isEmpty() || (!result.equals(ENCODE) && !result.equals(DECODE))) {
            System.out.println("You must enter E or D");
            return getAction(console);
        }
        return result;
    }

}
