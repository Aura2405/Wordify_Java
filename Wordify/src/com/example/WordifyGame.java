package com.example;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

class WordifyGame extends JFrame implements ActionListener {
    
    String word_list_path="C:/Users/ANUBRATA/Downloads/Documents/5_letter_words.txt";
    private final String solution = RandWord(); // Solution for the Wordle game
    
    private final int maxTurns = 6;
    private int currentTurn = 0;

    private final JTextField[][] grid = new JTextField[maxTurns][solution.length()];
    private final JButton enterButton = new JButton("ENTER");
    private final JButton solveButton = new JButton("SOLVE");

    String RandWord(){
     
     try{
         List<String> words = Files.readAllLines(Paths.get(word_list_path));
         
         Random ran = new Random();
         
         String randomWord = words.get(ran.nextInt(words.size()));
         
         return randomWord.toUpperCase();
     }
     catch(Exception e){
         return "";    
     }
    }
    public WordifyGame() {
        // Set up the main frame
        setTitle("Wordify!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the frame on the screen
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Background panel that resizes dynamically
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(230, 230, 250)); // Light lavender background
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); // Use GridBagLayout to center content

        // Main content panel with fixed size
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(300, 400)); // Fixed size for the main content
        mainPanel.setOpaque(false); // Make transparent to let background show

        // Title panel
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false); // Make transparent
        JLabel titleLabel = new JLabel("Wordify!", SwingConstants.CENTER);
        JLabel subtitleLabel = new JLabel("Welcome to the game!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        // Create the grid panel
        JPanel gridPanel = new JPanel(new GridLayout(maxTurns, solution.length(), 5, 5));
        gridPanel.setPreferredSize(new Dimension(200, 200));
        gridPanel.setBackground(new Color(230, 230, 250));
        for (int i = 0; i < maxTurns; i++) {
            for (int j = 0; j < solution.length(); j++) {
                grid[i][j] = new JTextField();
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                grid[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                grid[i][j].setBackground(Color.WHITE);
                grid[i][j].setEditable(i == 0); // Only the first row is editable initially
                addDocumentFilter(grid[i][j], i, j);
                gridPanel.add(grid[i][j]);
            }
        }

        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        inputPanel.setOpaque(false); // Make transparent
        inputPanel.add(enterButton);
        inputPanel.add(solveButton);

        // Add action listeners to buttons
        enterButton.addActionListener(this);
        enterButton.setBackground(Color.BLACK);
        enterButton.setForeground(Color.WHITE);
        enterButton.setFont(new Font("Brittanic",Font.BOLD,14));
        solveButton.addActionListener(this);
        solveButton.setBackground(Color.BLACK);
        solveButton.setForeground(Color.WHITE);
        solveButton.setFont(new Font("Brittanic",Font.BOLD,14));
        
        // Assemble main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Add main panel to the background panel
        backgroundPanel.add(mainPanel);

        // Add background panel to the frame
        add(backgroundPanel);

        setVisible(true);
    }

    private void addDocumentFilter(JTextField textField, int row, int col) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text != null && fb.getDocument().getLength() + text.length() <= 1) {
                    text = text.toUpperCase();
                    super.replace(fb, offset, length, text, attrs);

                    // Automatically move the cursor to the next cell
                    if (col < solution.length() - 1) {
                        grid[row][col + 1].requestFocus();
                    }
                }
            }
        });
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                try {
                    if (textField.getDocument().getLength() == 0 && col > 0) {
                        grid[row][col - 1].requestFocus();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    });
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                try {
                    int caret = textField.getCaretPosition();
                    if (textField.getDocument().getText(0,caret).length() == 0 && col > 0) {
                        grid[row][col - 1].requestFocus();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    });
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                try {
                    int caret = textField.getCaretPosition();
                    int length = textField.getDocument().getLength() - caret;
                    if (textField.getDocument().getText(caret,length).length() == 0 && col < 4)  {
                        grid[row][col + 1].requestFocus();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    });
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    StringBuilder guess = new StringBuilder();
                    for (int j = 0; j < solution.length(); j++) {
                    String cellValue = grid[currentTurn][j].getText();
                    if (cellValue.length() != 1) {
                       showMessage("Error", "Please complete the word.");
                       return;
                     }
                    guess.append(cellValue);
                    }
                    
                    List<String> words = Files.readAllLines(Paths.get(word_list_path));
                    if(!(words.contains(guess.toString().toLowerCase()))){
                        showMessage("Error", "Word not found in dictionary.");
                        return;
                    }

                    boolean correct = updateGrid(guess.toString());

                if (correct) {
                 showMessage("Congrats!", "Congrats! You guessed the word!");
                 disableCurrentRow();
                 enterButton.setEnabled(false);
                 solveButton.setEnabled(false);
                 } else if (currentTurn == maxTurns - 1) {
                   showMessage("Game Over", "The word was: " + solution + ". Better luck next time!");
                   enterButton.setEnabled(false);
                   solveButton.setEnabled(false);
                 } else {
                   disableCurrentRow();
                   currentTurn++;
                   enableCurrentRow();
                 }
              }  catch (Exception ex) {
                    ex.printStackTrace();
              }
           }
        }
    }); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == solveButton) {
            showSolution();
        }
        else if (e.getSource() == enterButton) {
            handleEnter();
        }
    }

    private void handleEnter() {
        StringBuilder guess = new StringBuilder();
        for (int j = 0; j < solution.length(); j++) {
            String cellValue = grid[currentTurn][j].getText();
            if (cellValue.length() != 1) {
                showMessage("Error", "Each cell must contain exactly one letter.");
                return;
            }
            guess.append(cellValue);
        }

        boolean correct = updateGrid(guess.toString());

        if (correct) {
            showMessage("Congrats!", "You guessed the word!");
            disableCurrentRow();
            enterButton.setEnabled(false);
            solveButton.setEnabled(false);
        } else if (currentTurn == maxTurns - 1) {
            showMessage("Game Over", "The word was: " + solution + ". Better luck next time!");
            enterButton.setEnabled(false);
            solveButton.setEnabled(false);
        } else {
            disableCurrentRow();
            currentTurn++;
            enableCurrentRow();
        }
    }

    private boolean updateGrid(String guess) {
        StringBuilder feedback = new StringBuilder("BBBBB");
        boolean[] solutionUsed = new boolean[solution.length()];

        // Mark greens (G)
        for (int i = 0; i < solution.length(); i++) {
            if (guess.charAt(i) == solution.charAt(i)) {
                feedback.setCharAt(i, 'G');
                solutionUsed[i] = true;
            }
        }

        // Mark yellows (Y)
        for (int i = 0; i < solution.length(); i++) {
            if (feedback.charAt(i) == 'G') continue;
            for (int j = 0; j < solution.length(); j++) {
                if (!solutionUsed[j] && guess.charAt(i) == solution.charAt(j)) {
                    feedback.setCharAt(i, 'Y');
                    solutionUsed[j] = true;
                    break;
                }
            }
        }

        // Update the grid colors and text
        boolean correct = true;
        for (int i = 0; i < solution.length(); i++) {
            switch (feedback.charAt(i)) {
                case 'G':
                    grid[currentTurn][i].setBackground(Color.GREEN);
                    break;
                case 'Y':
                    grid[currentTurn][i].setBackground(Color.YELLOW);
                    correct = false;
                    break;
                case 'B':
                    grid[currentTurn][i].setBackground(Color.BLACK);
                    grid[currentTurn][i].setForeground(Color.WHITE);
                    correct = false;
                    break;
            }
        }

        return correct;
    }

    private void enableCurrentRow() {
        for (int j = 0; j < solution.length(); j++) {
            grid[currentTurn][j].setEditable(true);
            grid[currentTurn][j].setText("");
        }
        
        grid[currentTurn][0].requestFocus();
    }

    private void disableCurrentRow() {
        for (int j = 0; j < solution.length(); j++) {
            grid[currentTurn][j].setEditable(false);
        }
    }

    private void showSolution() {
        showMessage("Solution", "The word was: " + solution + ".");
        disableCurrentRow();

        // Reveal solution in the first available row
        for (int i = 0; i < solution.length(); i++) {
            grid[currentTurn][i].setText(String.valueOf(solution.charAt(i)));
            grid[currentTurn][i].setBackground(Color.GREEN);
        }
        enterButton.setEnabled(false);
        solveButton.setEnabled(false);
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new WordifyGame();
    }
}
