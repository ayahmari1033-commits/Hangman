import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class hangmanGUI extends JFrame implements ActionListener {
    private Container window;
    private JButton quit;
    private JButton restart;
    private JLabel imageDisplayLabel;
    private ImageIcon image;
    private JPanel panel;
    private JButton[] letters;
    private JTextArea inputField;
    private hangmanConcepts game;
    private List<String> phrases;

    public hangmanGUI() {
        phrases = new ArrayList<>();
        phrases.add("I can't do this!");
        phrases.add("Veni, vidi, vici");
        phrases.add("You're 1 in a million.");
        phrases.add("Live Laugh Love");
        phrases.add("Hangman Game");

        //Start game with random phrase
        Random random = new Random();
        game = new hangmanConcepts(Collections.singletonList(phrases.get(random.nextInt(phrases.size()))));

        setupWindow();
        addComponentsToWindow();
        updateGameState();
    }

    // Create and customize window
    private void setupWindow() {
        window = getContentPane();
        window.setLayout(null);
        window.setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1500, 1000);
        this.setResizable(false);
        this.setTitle("Hangman");
    }

    private void addComponentsToWindow() {
        // Image setup
        image = new ImageIcon("images/hangman0.png");
        imageDisplayLabel = new JLabel();
        imageDisplayLabel.setIcon(image);
        imageDisplayLabel.setBounds(1100, 150, image.getIconWidth(), image.getIconHeight());
        window.add(imageDisplayLabel);

        // Letters

        panel = new JPanel();
        panel.setBounds(200, 100, 600, 150);
        panel.setLayout(new GridLayout(2, 13));
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        letters = new JButton[26];
        for (int i = 0; i < alphabet.length(); i++) {
            char letter = alphabet.charAt(i);
            letters[i] = new JButton(String.valueOf(letter));
            panel.add(letters[i]);
            window.add(panel);
            letters[i].addActionListener(this);
        }

        // Displaying phrase
        inputField = new JTextArea();
        inputField.setBounds(200, 300, 600, 350);
        inputField.setEditable(false);
        window.add(inputField);

        // Quit button
        quit = new JButton("Quit");
        quit.setBounds(650, 700, 150, 50);
        window.add(quit);
        quit.addActionListener(e -> System.exit(0));

        // Restart button
        restart = new JButton("Restart");
        restart.setBounds(200, 700, 150, 50);
        restart.setVisible(false);
        window.add(restart);
        restart.addActionListener(e -> {
            game.resetGame();
            updateGameState();
            enableAllButtons();
            restart.setVisible(false);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the character of the button clicked
        JButton clicked = (JButton) e.getSource();
        char letter = clicked.getText().charAt(0);

        // Disable the clicked button
        clicked.setEnabled(false);

        // Update game after a letter is guessed
        if (game.guessLetter(letter)) {
            updateGameState();
        }

        // Use RemainingStrikes to check if game is over
        if (game.getRemainingStrikes() <= 0) {
            inputField.setText("Game Over! The correct phrase was: " + game.getPhrase());
            window.setBackground(Color.RED);
            showRestartButton();
        } else if (game.getPhraseView().equalsIgnoreCase(game.getPhrase())) {
            inputField.setText("Congratulations! You guessed the phrase: " + game.getPhrase());
            window.setBackground(Color.GREEN);
            showRestartButton();
        }
    }

    // Update display with current game status
    public void updateGameState() {
        inputField.setText("Current phrase: " + game.getPhraseView());
        int incorrectGuesses = game.getIncorrectGuesses();

        // Update images (not fully working)
        image = new ImageIcon("images/hangman" + incorrectGuesses + ".png");
        imageDisplayLabel.setIcon(image);

        // Disable all buttons after player loses or wins
        if (game.getRemainingStrikes() == 0 || game.getPhraseView().equals(game.getPhrase())) {
            disableAllButtons();
        }
    }


    private void showRestartButton() {
        restart.setVisible(true);
    }

    // Enable letter buttons when restarting game
    private void enableAllButtons() {
        for (JButton letterButton : letters) {
            letterButton.setEnabled(true);
            window.setBackground(Color.LIGHT_GRAY);
        }
    }


    private void disableAllButtons() {
        for (JButton letterButton : letters) {
            letterButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        hangmanGUI program = new hangmanGUI();
        program.setVisible(true);
    }
}