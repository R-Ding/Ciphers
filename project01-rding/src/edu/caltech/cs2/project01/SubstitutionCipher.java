package edu.caltech.cs2.project01;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SubstitutionCipher {
    private String ciphertext;
    private Map<Character, Character> key;

    // Use this Random object to generate random numbers in your code,
    // but do not modify this line.
    private static final Random RANDOM = new Random();

    /**
     * Construct a SubstitutionCipher with the given cipher text and key
     * @param ciphertext the cipher text for this substitution cipher
     * @param key the map from cipher text characters to plaintext characters
     */
    public SubstitutionCipher(String ciphertext, Map<Character, Character> key) {
        this.ciphertext = ciphertext;
        this.key = key;
    }

    /**
     * Construct a SubstitutionCipher with the given cipher text and a randomly
     * initialized key.
     * @param ciphertext the cipher text for this substitution cipher
     */
    public SubstitutionCipher(String ciphertext) {
        this(ciphertext, makeIdentityKey());

        for (int i = 0; i < 10000; i++) {
            this.key = this.randomSwap().key;
        }
    }

    public static Map<Character, Character> makeIdentityKey() {
        Map<Character, Character> key = new HashMap<Character, Character>();
        for (char c = 'A'; c <= 'Z'; c++) {
            key.put(c, c);
        }
        return key;
    }

    /**
     * Returns the unedited cipher text that was provided by the user.
     * @return the cipher text for this substitution cipher
     */
    public String getCipherText() {
        return this.ciphertext;
    }

    /**
     * Applies this cipher's key onto this cipher's text.
     * That is, each letter should be replaced with whichever
     * letter it maps to in this cipher's key.
     * @return the resulting plain text after the transformation using the key
     */
    public String getPlainText() {
        String plainText = "";
        for (int i = 0; i < this.ciphertext.length(); i++) {
            plainText += this.key.get(this.ciphertext.charAt(i));
        }
        return plainText;
    }

    /**
     * Returns a new SubstitutionCipher with the same cipher text as this one
     * and a modified key with exactly one random pair of characters exchanged.
     *
     * @return the new SubstitutionCipher
     */
    public SubstitutionCipher randomSwap() {

        char c1 = (char) (RANDOM.nextInt(26) + 'A');
        char c2 = (char) (RANDOM.nextInt(26) + 'A');
        while (c1 == c2) {
            c2 = (char) (RANDOM.nextInt(26) + 'A');
        }

        Map<Character, Character> newKey = new HashMap<Character, Character>(this.key);

        newKey.put(c1, this.key.get(c2));
        newKey.put(c2, this.key.get(c1));


        return new SubstitutionCipher(this.ciphertext, newKey);
    }

    /**
     * Returns the "score" for the "plain text" for this cipher.
     * The score for each individual quadgram is calculated by
     * the provided likelihoods object. The total score for the text is just
     * the sum of these scores.
     * @param likelihoods the object used to find a score for a quadgram
     * @return the score of the plain text as calculated by likelihoods
     */
    public double getScore(QuadGramLikelihoods likelihoods) {
        double score = 0;
        String plainText = getPlainText();
        for (int i = 0; i < (plainText.length() - 3); i++) {
            score += likelihoods.get(plainText.substring(i, i + 4));
        }
        return score;
    }

    /**
     * Attempt to solve this substitution cipher through the hill
     * climbing algorithm. The SubstitutionCipher this is called from
     * should not be modified.
     * @param likelihoods the object used to find a score for a quadgram
     * @return a SubstitutionCipher with the same ciphertext and the optimal
     *  found through hill climbing
     */
    public SubstitutionCipher getSolution(QuadGramLikelihoods likelihoods) {
        SubstitutionCipher cipher = new SubstitutionCipher(getPlainText());
        double C = cipher.getScore(likelihoods);
        int t = 0;
        while (t < 1000) {
            SubstitutionCipher newCipher = cipher.randomSwap();
            if (newCipher.getScore(likelihoods) > cipher.getScore(likelihoods)) {
                cipher = newCipher;
                t = 0;
            }
            else {
                t++;
            }
        }

        return cipher;
    }
}
