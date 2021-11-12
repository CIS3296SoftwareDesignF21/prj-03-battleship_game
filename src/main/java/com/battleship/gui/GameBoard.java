package com.battleship.gui;

import com.battleship.game.playerpack.Player;
import com.battleship.game.playerpack.PlayerData;
import com.battleship.game.shippack.Ship;
import com.battleship.networking.Client;
import com.battleship.networking.NetworkConnection;
import com.battleship.networking.Server;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;


public class GameBoard {

    private final JButton[][] playerPositions = new JButton[10][10];
    private JPanel mainPanel;
    private JPanel chatPanel;
    private JPanel gameBoard1;
    private JPanel gameBoard2;
    private JScrollPane scrollPane;
    private JTextArea messages;
    private JTextField input;
    private final JButton[][] enemyPositions = new JButton[10][10];
    private final int PLAYING = 0;
    private int yourCurrentScore = 0;
    private int enemyCurrentScore = 0;
    private NetworkConnection connection;
    private final ButtonHandler buttonHandler = new ButtonHandler();
    private final PowerUpHandler powerUpHandler = new PowerUpHandler();
    private final ButtonGroup powerUpButtonGroup = new ButtonGroup();
    private final int SHIP_HIT = 1;
    private final int GAME_WON = 2;
    private int powerUpVal = 0;
    private JFrame frame;
    private JLabel playerFieldLabel;
    private JLabel enemyFieldLabel;
    private JRadioButton btnPowerUpLineVert;
    private JRadioButton btnPowerUpLineHorizontal;
    private JRadioButton btnPowerUpMaxHitDamage;
    private JLabel whosTurnLabel;
    private JButton replayGameButton;
    private JLabel enemyScore;
    private JLabel yourScore;
    private String enemyName = "Enemy Player";
    private boolean isUserDataSet = false;
    private boolean isUserTurn = Player.isHost();

    public GameBoard() {
        frame = new JFrame("Battleship Game");
        $$$setupUI$$$();
        input.addActionListener(buttonHandler);
        btnPowerUpLineVert.addActionListener(powerUpHandler);
        btnPowerUpLineHorizontal.addActionListener(powerUpHandler);
        btnPowerUpMaxHitDamage.addActionListener(powerUpHandler);
        powerUpButtonGroup.add(btnPowerUpLineVert);
        powerUpButtonGroup.add(btnPowerUpLineHorizontal);
        powerUpButtonGroup.add(btnPowerUpMaxHitDamage);
        this.setPlayerButtons();
        this.setShipOnBoard();
        this.setEnemyButtons();
        this.setUserElements();
        this.setTurnLabel();
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(1000, 800); //TODO: set a good size for the game.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        replayGameButton.setVisible(false);
    }

    /**
     * Gets all the Ship Objects from HashMap and read their position
     * Then place them on another (disabled) grid layout of buttons
     */
    private void setShipOnBoard() {
        setScoreLabel();
        for (Ship shipToPlace : ShipPlanner.board.field.values()) {
            int[] h_c = shipToPlace.getHeadCoordinates();
            int length = shipToPlace.getLength();
            // draw the ship on the board
            if (shipToPlace.isVertical()) {
                for (int i = 0; i < length; i++) {
                    playerPositions[h_c[0]][h_c[1] + i].setBackground(Color.BLUE);
                    playerPositions[h_c[0]][h_c[1] + i].setEnabled(true);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    playerPositions[h_c[0] + i][h_c[1]].setBackground(Color.BLUE);
                    playerPositions[h_c[0] + i][h_c[1]].setEnabled(true);
                }
            }
        }
    }

    /**
     * Set the buttons of the first game board.
     * Every ShipUnit correspond to one button
     * Same on the method below but for the second game board.
     */
    private void setPlayerButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) { // TODO: we should see if this implementation is better than the one below
                JButton currentButton = new JButton();
                currentButton.setBackground(Color.LIGHT_GRAY);
                currentButton.setEnabled(false);
                gameBoard1.add(currentButton);
                playerPositions[i][j] = currentButton;
            }
        }
    }

    private void setEnemyButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                enemyPositions[i][j] = new JButton();
                enemyPositions[i][j].setBackground(Color.LIGHT_GRAY);
                gameBoard2.add(enemyPositions[i][j]);
                enemyPositions[i][j].addActionListener(buttonHandler);
            }
        }
    }

    /**
     * Custom constructor of .form file
     */
    private void createUIComponents() {
        gameBoard1 = new JPanel();
        gameBoard2 = new JPanel();
        gameBoard1.setLayout(new GridLayout(10, 10));
        gameBoard2.setLayout(new GridLayout(10, 10));
    }

    private void setUserElements() {
        playerFieldLabel.setText(Player.getName() + "'s field");
        playerFieldLabel.setIcon(Player.getAvatar());
    }

    private void setTurnLabel() {
        whosTurnLabel.setText(isUserTurn ? "Your Turn" : enemyName + "'s Turn");
        whosTurnLabel.setForeground(isUserTurn ? Color.GREEN : Color.RED);
    }

    private void setScoreLabel() {
        yourScore.setText("Your Score: " + yourCurrentScore);
        enemyScore.setText("Enemy Score: " + enemyCurrentScore);
    }


    /**
     * Called if the Player is the server
     *
     * @param port the port where the game will be hosted
     */
    public void createServer(int port) {
        connection = new Server(data -> SwingUtilities.invokeLater(() -> handleData(data)), port);
        try {
            connection.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called if the Player is the client
     *
     * @param ip   the IP of the server to connect
     * @param port the port of the server to connect
     */
    public void createClient(String ip, int port) {
        connection = new Client(data -> SwingUtilities.invokeLater(() -> handleData(data)), ip, port);
        try {
            connection.startConnection();
            try {
                Thread.sleep(300); // inefficient. We should really use another method
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUserData() {
        try {
            connection.send(new PlayerData(Player.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleData(Object data) {
        setTurnLabel();
        if (data instanceof String) {
            // got a chat message
            messages.append(data.toString() + "\n");
        } else if (data instanceof PlayerData) {
            // got the player data
            setPlayerData(data);
        } else {
            // got game data
            handleGameData((int[]) data);
        }
    }

    private void setPlayerData(Object data) {
        if (!isUserDataSet) {
            PlayerData enemy = (PlayerData) data;
            if (enemy.getName().length() > 0) enemyName = enemy.getName();
            enemyFieldLabel.setText(enemy.getName() + "'s field");
            enemyFieldLabel.setIcon(enemy.getAvatar());
            sendUserData();
            isUserDataSet = true;
        }
    }

    private void handleGameData(int[] posToAttack) {
        if (posToAttack[0] == SHIP_HIT && posToAttack[3] != 0) {
            // the other player got hit, so update their board
            updateHitWithPowerUp(posToAttack);
            disableChosenPowerUp(posToAttack[3]);
        } else if (posToAttack[0] == SHIP_HIT) { // The enemy sends back the int array with a 1 in first position to signal that he has been hit
            yourCurrentScore++;
            enemyPositions[posToAttack[1]][posToAttack[2]].setBackground(Color.RED);
            isUserTurn = true;
            setScoreLabel();
            setTurnLabel();
        } else if (posToAttack[0] == GAME_WON) {
            JOptionPane.showMessageDialog(frame,
                    "You won!",
                    "Congratulations",
                    JOptionPane.INFORMATION_MESSAGE);
            replayGameButton.setEnabled(true);
            replayGameButton.setVisible(true);
        } else {
            // check if this player got hit
            if (playerPositions[posToAttack[1]][posToAttack[2]].isEnabled()) {
                enemyCurrentScore++;
                updatePlayerBoard(posToAttack);
                setScoreLabel();
            } else {
                // not hit, it's a new players turn now
                isUserTurn = true;
                setTurnLabel();
            }
        }
    }

    private void updatePlayerBoard(int[] posToAttack) {
        playerPositions[posToAttack[1]][posToAttack[2]].setBackground(Color.BLACK);
        playerPositions[posToAttack[1]][posToAttack[2]].setEnabled(false);
        if (posToAttack[3] == 1) {
            // vertical-line powerup
            for (int i = 0; i < 10; ++i) {
                // vert line
                playerPositions[i][posToAttack[2]].setBackground(Color.BLACK);
                playerPositions[i][posToAttack[2]].setEnabled(false);
            }
        }
        if (posToAttack[3] == 2) {
            for (int i = 0; i < 10; ++i) {
                // horizontal line powerup
                playerPositions[posToAttack[1]][i].setBackground(Color.BLACK);
                playerPositions[posToAttack[1]][i].setEnabled(false);
            }
        }
        if (posToAttack[3] == 3) {
            handleMaxDamagePlayerBoard(posToAttack[1], posToAttack[2]);
        }
        checkForWinAndSendData(posToAttack);
    }

    private void checkForWinAndSendData(int[] posToAttack) {
        try {
            if (hasPlayerWin()) {
                connection.send(new int[]{GAME_WON, posToAttack[1], posToAttack[2], 0});
                JOptionPane.showMessageDialog(frame,
                        "You lost!",
                        "Bad news",
                        JOptionPane.INFORMATION_MESSAGE);
                replayGameButton.setEnabled(true);
                replayGameButton.setVisible(true);
            } else {
                connection.send(new int[]{SHIP_HIT, posToAttack[1], posToAttack[2], posToAttack[3]});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A player can use one of each powerup for each game
     * So disable whichever powerups have already been used
     */
    private void disableChosenPowerUp(int val) {
        switch (val) {
            case 1:
                btnPowerUpLineVert.setEnabled(false);
                powerUpVal = 0;
                break;
            case 2:
                btnPowerUpLineHorizontal.setEnabled(false);
                powerUpVal = 0;
                break;
            case 3:
                btnPowerUpMaxHitDamage.setEnabled(false);
                powerUpVal = 0;
                break;
            default:
                break;
        }
    }

    private void updateHitWithPowerUp(int[] posToAttack) {
        System.out.println("Enemy got hit at pos " + posToAttack[1] + " and " + posToAttack[2]);
        enemyPositions[posToAttack[1]][posToAttack[2]].setBackground(Color.RED);
        int row = posToAttack[1];
        int col = posToAttack[2];
        switch (posToAttack[3]) {
            case 1:
                System.out.println("Vertical powerup enabled");
                for (int i = 0; i < 10; ++i) {
                    enemyPositions[i][col].setBackground(Color.RED);
                }
                break;
            case 2:
                System.out.println("Horizontal powerup enabled");
                for (int i = 0; i < 10; ++i) {
                    enemyPositions[row][i].setBackground(Color.RED);
                }
                break;
            case 3:
                //when player hit enemy with Max Powerup
                yourCurrentScore++;
                System.out.println("Max Damage powerup enabled");
                handleMaxDamageEnemyBoard(posToAttack[1], posToAttack[2]);
                setScoreLabel();
                break;
            default:
                break;
        }
        isUserTurn = true;
        setTurnLabel();
    }

    private void handleMaxDamagePlayerBoard(int row, int col) {
        if (row - 1 >= 0) {
            playerPositions[row - 1][col].setBackground(Color.BLACK);
            playerPositions[row - 1][col].setEnabled(false);
        }
        if (row + 1 <= 9) {
            playerPositions[row + 1][col].setBackground(Color.BLACK);
            playerPositions[row + 1][col].setEnabled(false);

        }
        if (col - 1 >= 0) {
            playerPositions[row][col - 1].setBackground(Color.BLACK);
            playerPositions[row][col - 1].setEnabled(false);
        }
        if (col + 1 <= 9) {
            playerPositions[row][col + 1].setBackground(Color.BLACK);
            playerPositions[row][col + 1].setEnabled(false);
        }
    }

    private void handleMaxDamageEnemyBoard(int row, int col) {
        if (row - 1 >= 0) {
            enemyPositions[row - 1][col].setBackground(Color.RED);
        }
        if (row + 1 <= 9) {
            enemyPositions[row + 1][col].setBackground(Color.RED);
        }
        if (col - 1 >= 0) {
            enemyPositions[row][col - 1].setBackground(Color.RED);
        }
        if (col + 1 <= 9) {
            enemyPositions[row][col + 1].setBackground(Color.RED);
        }
    }

    private boolean hasPlayerWin() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (playerPositions[i][j].isEnabled()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(7, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setForeground(new Color(-4473925));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        mainPanel.add(gameBoard1, new GridConstraints(2, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, 400), new Dimension(400, 400), new Dimension(400, 400), 0, false));
        gameBoard1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        chatPanel = new JPanel();
        chatPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(chatPanel, new GridConstraints(6, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(250, 250), new Dimension(250, 500), null, 0, false));
        chatPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), "Chat", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        scrollPane = new JScrollPane();
        chatPanel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        messages = new JTextArea();
        messages.setEditable(false);
        Font messagesFont = this.$$$getFont$$$("Roboto Light", -1, -1, messages.getFont());
        if (messagesFont != null) messages.setFont(messagesFont);
        scrollPane.setViewportView(messages);
        input = new JTextField();
        Font inputFont = this.$$$getFont$$$("Roboto Light", -1, -1, input.getFont());
        if (inputFont != null) input.setFont(inputFont);
        chatPanel.add(input, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        gameBoard2.setForeground(new Color(-14911728));
        mainPanel.add(gameBoard2, new GridConstraints(2, 2, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, 400), new Dimension(400, 400), new Dimension(400, 400), 0, false));
        gameBoard2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        playerFieldLabel = new JLabel();
        playerFieldLabel.setText("Your Field");
        mainPanel.add(playerFieldLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enemyFieldLabel = new JLabel();
        enemyFieldLabel.setText("Enemy field");
        mainPanel.add(enemyFieldLabel, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnPowerUpLineVert = new JRadioButton();
        btnPowerUpLineVert.setText("Fire Whole Line - Vertical");
        mainPanel.add(btnPowerUpLineVert, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnPowerUpLineHorizontal = new JRadioButton();
        btnPowerUpLineHorizontal.setText("Fire Whole Line - Horizontal");
        mainPanel.add(btnPowerUpLineHorizontal, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnPowerUpMaxHitDamage = new JRadioButton();
        btnPowerUpMaxHitDamage.setText("Max Hit Damage");
        mainPanel.add(btnPowerUpMaxHitDamage, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        whosTurnLabel = new JLabel();
        whosTurnLabel.setBackground(new Color(-14911728));
        Font whosTurnLabelFont = this.$$$getFont$$$(null, -1, 18, whosTurnLabel.getFont());
        if (whosTurnLabelFont != null) whosTurnLabel.setFont(whosTurnLabelFont);
        whosTurnLabel.setText("Who's turn?");
        mainPanel.add(whosTurnLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        replayGameButton = new JButton();
        replayGameButton.setEnabled(false);
        replayGameButton.setText("Replay Game?");
        mainPanel.add(replayGameButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enemyScore = new JLabel();
        enemyScore.setText("Score");
        mainPanel.add(enemyScore, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        yourScore = new JLabel();
        yourScore.setText("Score");
        mainPanel.add(yourScore, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }


    /**
     * Private class to handle buttons.
     */
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // send the chat message
            Object source = e.getSource();
            if (source == input) {
                //System.out.println(input.getText());
                String message = Player.getName() + ": ";
                message += input.getText();
                input.setText("");
                messages.append(message + "\n");
                try {
                    connection.send(message);
                } catch (Exception ex) {
                    messages.append("Failed to send\n");
                    ex.printStackTrace();
                }
            } else {
                // send the data to the other player
                if (isUserTurn) {
                    sendDataToPlayer(source);
                }
            }
        }

        /**
         * Test print function to see the chosen position to attack. Should be removed.
         *
         * @param src Button source
         */
        private void sendDataToPlayer(Object src) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (src == enemyPositions[i][j]) {
                        System.out.println("Chosen position to attack: " + i + ", " + j);
                        enemyPositions[i][j].setBackground(Color.ORANGE);
                        try {
                            connection.send(new int[]{PLAYING, i, j, powerUpVal});
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        enemyPositions[i][j].setEnabled(false);
                        isUserTurn = false;
                        setTurnLabel();
                    }
                }
            }
        }
    }

    /**
     * Private class to handle power ups
     */
    private class PowerUpHandler implements ActionListener {
        /**
         * Powerups are working but there are some bugs
         * The wrong hits are displayed sometimes
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            // need to disable powerup if double-clicked
            if (source == btnPowerUpLineVert) {
                powerUpVal = 1;
            }
            if (source == btnPowerUpLineHorizontal) {
                powerUpVal = 2;
            }
            if (source == btnPowerUpMaxHitDamage) {
                powerUpVal = 3;
            }
        }
    }

}