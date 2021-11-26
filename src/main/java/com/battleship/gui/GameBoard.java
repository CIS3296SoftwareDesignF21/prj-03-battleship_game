package com.battleship.gui;

import com.battleship.game.colorpack.ColorPack;
import com.battleship.game.playerpack.Player;
import com.battleship.game.playerpack.PlayerData;
import com.battleship.game.shippack.Ship;
import com.battleship.networking.Client;
import com.battleship.networking.NetworkConnection;
import com.battleship.networking.Server;
import com.battleship.game.powerup.PowerUp;
import com.battleship.utils.BSConfigFile;
import com.battleship.utils.SoundEffects;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
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
    private NetworkConnection connection;
    private final ButtonHandler buttonHandler = new ButtonHandler();
    private final PowerUpHandler powerUpHandler = new PowerUpHandler();
    private final ButtonGroup powerUpButtonGroup = new ButtonGroup();
    private final int SHIP_HIT = 1;
    private final int GAME_WON = 2;
    private int powerUpVal = 0;
    private int port = 0;
    private String ip;
    private JFrame frame;
    private JLabel playerFieldLabel;
    private JLabel enemyFieldLabel;
    private JRadioButton btnPowerUpLineVert;
    private JRadioButton btnPowerUpLineHorizontal;
    private JRadioButton btnPowerUpMaxHitDamage;
    private JLabel whosTurnLabel;
    private JButton replayGameButton;
    private String enemyName = "Enemy Player";
    private JLabel enemyScore;
    private JLabel yourScore;
    private JLabel yourPreviousScore;
    private JOptionPane optionPane;
    private int yourCurrentScore = 0;
    private int enemyCurrentScore = 0;
    private boolean isUserDataSet = false;
    private boolean isReplay = false;
    private boolean isServer;
    private boolean isUserTurn = Player.isHost();

    /**
     *  GameBoard Constructor
     *  This is the main GUI window for the game
     */
    public GameBoard(int port, String ip, boolean isServer) {
        frame = new JFrame("Battleship Game");
        $$$setupUI$$$();
        input.addActionListener(buttonHandler);
        replayGameButton.addActionListener(buttonHandler);
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
        this.ip = ip;
        this.port = port;
        this.isServer = isServer;
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
        setUpPreviousScore();
        setScoreLabel();
        for (Ship shipToPlace : ShipPlanner.board.field.values()) {
            int[] h_c = shipToPlace.getHeadCoordinates();
            int length = shipToPlace.getLength();
            // draw the ship on the board
            if (shipToPlace.isVertical()) {
                for (int i = 0; i < length; i++) {
                    playerPositions[h_c[0]][h_c[1] + i].setBackground(ColorPack.playerTurnColor);
                    playerPositions[h_c[0]][h_c[1] + i].setEnabled(true);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    playerPositions[h_c[0] + i][h_c[1]].setBackground(ColorPack.playerTurnColor);
                    playerPositions[h_c[0] + i][h_c[1]].setEnabled(true);
                }
            }
        }
    }

    /**
     *  Set the previous score that is stored in the config file
     */
    private void setUpPreviousScore() {
        yourPreviousScore.setText("Previous Score: " + BSConfigFile.readProperties("Score"));
    }

    /**
     *  Check if the user has a new high score and update it if they do
     */
    private void changePreviousScore() {
        int previousHighest = Integer.parseInt(BSConfigFile.readProperties("Score"));
        if (yourCurrentScore >= previousHighest) {
            BSConfigFile.updateScoring(yourCurrentScore + "");
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

    /**
     * Setup the enemy board with the corresponding buttons
     */
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

    /**
     * Set this players data
     */
    private void setUserElements() {
        playerFieldLabel.setText(Player.getName() + "'s field");
        //playerFieldLabel.setIcon(Player.getAvatar());
    }

    /**
     * Set which player has the turn
     */
    private void setTurnLabel() {
        whosTurnLabel.setText(isUserTurn ? "Your Turn" : enemyName + "'s Turn");
        whosTurnLabel.setForeground(isUserTurn ? Color.GREEN : Color.RED);
    }

    /**
     *  Set the score of the current game
     */
    private void setScoreLabel() {
        yourScore.setText("Your Score: " + yourCurrentScore);
        enemyScore.setText("Enemy Score: " + enemyCurrentScore);
    }

    /**
     * Called if the Player is the server
     */
    public void createServer() {
        connection = new Server(data -> SwingUtilities.invokeLater(() -> handleData(data)), port);
        try {
            connection.startConnection();
            waitForClientConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  This is called if the player is the host (really a TCP server)
     *  It will block, for 5 minutes, and wait for another player (client) to join the game
     */
    private void waitForClientConnection() {
        JLabel label = new JLabel("Waiting for client to connect");
        label.setHorizontalAlignment(JLabel.CENTER);
        long startTime = System.currentTimeMillis();
        while (true) {
            boolean val;
            JOptionPane.showMessageDialog(null, label, "Please wait...", JOptionPane.PLAIN_MESSAGE);
            connection.lock.lock();
            try {
                val = connection.clientConnected;
                connection.lock.unlock();
                // once this bool turns true, a client has connected
                if (val) {
                    JLabel label2 = new JLabel("Client Connected");
                    JOptionPane.showMessageDialog(null, label2, "Connection success!", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                // this is only getting called when the above code is done
                // maybe but in another thread?
                long endTimeSeconds = (System.currentTimeMillis() - startTime) / 1000;
                System.out.println("Elapsed waiting time " + endTimeSeconds);
                // 5 min wait
                if (endTimeSeconds >= 300) {
                    JLabel label2 = new JLabel("Error");
                    JOptionPane.showMessageDialog(null, label2, "Timeout - No client ever connected!", JOptionPane.PLAIN_MESSAGE);
                    frame.dispose();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called if the Player is the client
     */
    public void createClient() {
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

    /**
     * Send this player's data to the other player
     * This is called first once both players connect
     */
    public void sendUserData() {
        try {
            connection.send(new PlayerData(Player.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the data sent from the other player
     * This is the callback function that is called in the
     * NetworkConnection thread
     */
    private void handleData(Object data) {
        setTurnLabel();
        if (data instanceof String) {
            // check the string input from the other player
            checkInputFromPlayer((String) data);
        } else if (data instanceof PlayerData) {
            // got the player data
            setPlayerData(data);
        } else {
            // got game data
            handleGameData((int[]) data);
        }
    }

    /**
     * Check the string data that's been received
     */
    private void checkInputFromPlayer(String data) {
        // check if we got replay message
        if (data.equals("replay")) {
            isReplay = true;
            messages.append(enemyName + " would like to play again!\n");
            messages.append("Type 'yes' to replay match, 'no' to quit\n");
        }
        // check if we got the ack message
        else if (isReplay && (data.equals(enemyName + ": yes") || data.equals(enemyName + ": no"))) {
            handleReplay(data);
        }
        // got a chat message
        else {
            messages.append(data.toString() + "\n");
        }
    }

    /**
     * Check if the ack message from the other player
     * has been set to replay the game
     */
    private void handleReplay(String data) {
        if (data.equals(enemyName + ": yes")) {
            try {
                connection.closeConnection();
                replayGameWait();
                SwingUtilities.invokeLater(() -> new ShipPlanner(isServer, port, ip));
                frame.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (data.equals(enemyName + ": no")) {
            frame.dispose();
        }
    }

    /**
     * Set the enemy players data
     */
    private void setPlayerData(Object data) {
        if (!isUserDataSet) {
            PlayerData enemy = (PlayerData) data;
            if (enemy.getName().length() > 0) enemyName = enemy.getName();
            enemyFieldLabel.setText(enemy.getName() + "'s field");
            //enemyFieldLabel.setIcon(enemy.getAvatar());
            sendUserData();
            isUserDataSet = true;
        }
    }

    /**
     * Check what game data has been sent and see if there are any hits
     */
    private void handleGameData(int[] posToAttack) {
        if (posToAttack[0] == SHIP_HIT && posToAttack[3] != 0) {
            // the other play got hit, so update their board
            updateHitWithPowerUp(posToAttack);
            disableChosenPowerUp(posToAttack[3]);
            SoundEffects.playBoomDynamite(this);
        } else if (posToAttack[0] == SHIP_HIT) { // The enemy sends back the int array with a 1 in first position to signal that he has been hit
            yourCurrentScore++;
            enemyPositions[posToAttack[1]][posToAttack[2]].setBackground(ColorPack.enemyHitColor);
            isUserTurn = true;
            setScoreLabel();
            setTurnLabel();
            SoundEffects.playBoom(this);
        } else if (posToAttack[0] == GAME_WON) {
            isReplay = true;
            showWinMessage(posToAttack);
            changePreviousScore();
        } else {
            checkForPlayerHit(posToAttack);
        }
    }

    /**
     * Check if the position to attack sent by the enemy is an enabled
     * location on this players board
     */
    private void checkForPlayerHit(int[] posToAttack) {
        // check if this player got hit
        if (playerPositions[posToAttack[1]][posToAttack[2]].isEnabled()) {
            enemyCurrentScore++;
            updatePlayerBoard(posToAttack);
            setScoreLabel();
        } else {
            // not hit, it's this players turn now
            isUserTurn = true;
            setTurnLabel();
        }
    }

    /**
     * This player won, show the winning message
     */
    private void showWinMessage(int[] posToAttack) {
        enemyPositions[posToAttack[1]][posToAttack[2]].setBackground(ColorPack.enemyHitColor);
        if (posToAttack[3] != 0) {
            PowerUp.handlePowerUp(enemyPositions, posToAttack, ColorPack.enemyHitColor);
        }
        SoundEffects.playWinning(this);
        JOptionPane.showMessageDialog(frame, "You won!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        replayGameButton.setEnabled(true);
        replayGameButton.setVisible(true);
    }

    /**
     * This player got hit by the enemy, update this players field
     * Will also check if the enemy had any powerups enabled
     */
    private void updatePlayerBoard(int[] posToAttack) {
        playerPositions[posToAttack[1]][posToAttack[2]].setBackground(ColorPack.playerHitColor);
        playerPositions[posToAttack[1]][posToAttack[2]].setEnabled(false);
        PowerUp.handlePowerUp(playerPositions, posToAttack, ColorPack.playerHitColor);
        checkForWinAndSendData(posToAttack);
    }

    /**
     * This player got hit by the enemy
     * Check if the game is over and send the data back to the other player
     */
    private void checkForWinAndSendData(int[] posToAttack) {
        try {
            if (hasPlayerWin()) {
                playerPositions[posToAttack[1]][posToAttack[2]].setBackground(ColorPack.playerHitColor);
                if (posToAttack[3] != 0) {
                    PowerUp.handlePowerUp(playerPositions, posToAttack, ColorPack.playerHitColor);
                }
                connection.send(new int[]{GAME_WON, posToAttack[1], posToAttack[2], posToAttack[3]});
                SoundEffects.playLosing(this);
                JOptionPane.showMessageDialog(frame, "You lost!", "Bad news", JOptionPane.INFORMATION_MESSAGE);
                changePreviousScore();
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

    /**
     * This player got a hit on the other player with a powerup enabled
     * Set the enemys board to red and handle the powerup
     */
    private void updateHitWithPowerUp(int[] posToAttack) {
        System.out.println("Enemy got hit at pos " + posToAttack[1] + " and " + posToAttack[2]);
        yourCurrentScore++;
        enemyPositions[posToAttack[1]][posToAttack[2]].setBackground(ColorPack.enemyHitColor);
        PowerUp.handlePowerUp(enemyPositions, posToAttack, ColorPack.enemyHitColor);
        isUserTurn = true;
        setTurnLabel();
        setScoreLabel();
    }

    /**
     * Loop through all the positions on the enemy's board
     * and see if any are still enabled
     * <p>
     * If there are still positions enabled, the game is not done
     * If the player has no more enabled position, the game is over
     */
    private boolean hasPlayerWin() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (playerPositions[i][j].isEnabled()) {
                    System.out.println("Position " + i + " " + j + " is still enabled");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if this player needs to wait for the host to set their board first
     * before playing another game
     */
    private void replayGameWait() {
        if (!isServer) {
            JOptionPane.showMessageDialog(frame, "Please wait for the other player to set board", "BattleShip Replay", JOptionPane.INFORMATION_MESSAGE);
        }
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
        mainPanel.setLayout(new GridLayoutManager(7, 6, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setForeground(new Color(-4473925));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        mainPanel.add(gameBoard1, new GridConstraints(2, 0, 4, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, 400), new Dimension(400, 400), new Dimension(400, 400), 0, false));
        gameBoard1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        chatPanel = new JPanel();
        chatPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(chatPanel, new GridConstraints(6, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(250, 250), new Dimension(250, 500), null, 0, false));
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
        mainPanel.add(gameBoard2, new GridConstraints(2, 3, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, 400), new Dimension(400, 400), new Dimension(400, 400), 0, false));
        gameBoard2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        playerFieldLabel = new JLabel();
        playerFieldLabel.setText("Your Field");
        mainPanel.add(playerFieldLabel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enemyFieldLabel = new JLabel();
        enemyFieldLabel.setText("Enemy field");
        mainPanel.add(enemyFieldLabel, new GridConstraints(1, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnPowerUpLineVert = new JRadioButton();
        btnPowerUpLineVert.setText("Fire Whole Line - Vertical");
        mainPanel.add(btnPowerUpLineVert, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnPowerUpLineHorizontal = new JRadioButton();
        btnPowerUpLineHorizontal.setText("Fire Whole Line - Horizontal");
        mainPanel.add(btnPowerUpLineHorizontal, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnPowerUpMaxHitDamage = new JRadioButton();
        btnPowerUpMaxHitDamage.setText("Max Hit Damage");
        mainPanel.add(btnPowerUpMaxHitDamage, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        whosTurnLabel = new JLabel();
        whosTurnLabel.setBackground(new Color(-14911728));
        Font whosTurnLabelFont = this.$$$getFont$$$(null, -1, 18, whosTurnLabel.getFont());
        if (whosTurnLabelFont != null) whosTurnLabel.setFont(whosTurnLabelFont);
        whosTurnLabel.setText("Who's turn?");
        mainPanel.add(whosTurnLabel, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        replayGameButton = new JButton();
        replayGameButton.setEnabled(false);
        replayGameButton.setText("Replay Game?");
        mainPanel.add(replayGameButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enemyScore = new JLabel();
        enemyScore.setText("Score");
        mainPanel.add(enemyScore, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        yourScore = new JLabel();
        yourScore.setText("Score");
        mainPanel.add(yourScore, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        yourPreviousScore = new JLabel();
        yourPreviousScore.setText("Previous Score");
        mainPanel.add(yourPreviousScore, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
     * Private class that handles all game buttons for the main BattleShip Window
     * Whenever a gamegrid button is clicked, this listener is called
     */
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == input) {
                handleChatArea();
            }
            else if (source == replayGameButton) {
                handleReplayInput();
            }
            else {
                // send the data to the other player whenever a user clicks a square
                if (isUserTurn) {
                    sendDataToPlayer(source);
                }
            }
        }

        /**
         * Handle the input area for chat messages
         */
        private void handleChatArea() {
            String message = Player.getName() + ": ";
            message += input.getText();
            input.setText("");
            messages.append(message + "\n");
            try {
                // send the message to the other player
                connection.send(message);
                if (isReplay && message.equals(Player.getName() + ": yes")) {
                    // this is the ack message from the other player for the replay
                    connection.closeConnection();
                    replayGameWait();   // check if this player needs to wait for host
                    SwingUtilities.invokeLater(() -> new ShipPlanner(isServer, port, ip));
                    frame.dispose();
                }
            } catch (Exception ex) {
                messages.append("Failed to send\n");
                ex.printStackTrace();
            }
        }

        /**
         *  Send the replay request to the other player
         *  This will set the isReplay flag so when the ack message
         *  comes back, we know to restart the game
         */
        private void handleReplayInput() {
            try {
                // the user clicked the replay button
                // ask the other user if they want to play again
                isReplay = true;
                connection.send("replay");
            } catch (Exception ex) {
                messages.append("Failed to send replay\n");
                ex.printStackTrace();
            }
        }

        /**
         * Send the position to attack to the other player
         * This will trigger the handleData() method on the other player's side
         * and then they will tell us if we got a hit
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
                        // disable the button that was just clicked
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
     * <p>
     * Will be called everytime one of the buttons are clicked,
     * but will only activate if the player gets a hit.
     * Only one of each powerup can be used per game
     */
    private class PowerUpHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == btnPowerUpLineVert) {
                powerUpVal = PowerUp.VERTICAL;
            }
            if (source == btnPowerUpLineHorizontal) {
                powerUpVal = PowerUp.HORIZONTAL;
            }
            if (source == btnPowerUpMaxHitDamage) {
                powerUpVal = PowerUp.MAXDAMAGE;
            }
        }
    }

}