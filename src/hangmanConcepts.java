import java.util.*;

public class angmanConcepts {
    private String phrase;
    private String phraseView;
    private Set<Character> guessedLetters;
    private int incorrectGuesses;
    private int maxStrikes;

    private List<String> phraseBank;

    // Constructor initializes the phrase bank and resets the game
    public hangmanConcepts(List<String> phraseBank) {
        this.phraseBank = phraseBank;
        this.maxStrikes = 6;
        resetGame();
    }

    // Reset with random phrase
    public void resetGame() {
        Random rand = new Random();
        this.phrase = phraseBank.get(rand.nextInt(phraseBank.size()));
        this.phraseView = replaceAllLetters(phrase);
        this.guessedLetters = new HashSet<>();
        this.incorrectGuesses = 0;
    }

    // Replace letters with _
    public String replaceAllLetters(String word) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (isItALetter(word.charAt(i))) {
                builder.append('_');
            } else {
                builder.append(word.charAt(i));
            }
        }
        return builder.toString();
    }

    // Removes special characters
    public boolean isItALetter(char character) {
        return Character.isLetter(character);
    }

    // Handle a letter guess
    public boolean guessLetter(char guess) {

        guess = Character.toLowerCase(guess);

        if (guessedLetters.contains(guess)) {
            return false;
        }

        guessedLetters.add(guess);
        boolean isCorrect = false;


        StringBuilder newPhraseView = new StringBuilder(phraseView);
        for (int i = 0; i < phrase.length(); i++) {
            if (Character.toLowerCase(phrase.charAt(i)) == guess) {
                newPhraseView.setCharAt(i, phrase.charAt(i));
                isCorrect = true;
            }
        }

        phraseView = newPhraseView.toString();

        if (!isCorrect) {
            incorrectGuesses++;
        }

        return isCorrect;
    }


    public boolean guessPhrase(String phraseGuess) {
        if (phraseGuess.equalsIgnoreCase(phrase)) {
            phraseView = phrase;
            return true;
        }
        incorrectGuesses++;
        return false;
    }

    // Getters for GUI updates
    public String getPhraseView() {
        return phraseView;
    }

    public int getIncorrectGuesses() {
        return incorrectGuesses;
    }

    public String getPhrase() {
        return phrase;
    }

    public int getMaxStrikes() {
        return maxStrikes;
    }

    public int getRemainingStrikes() {
        return maxStrikes - incorrectGuesses;
    }
}
