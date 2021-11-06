package com.battleship.gui;

import com.battleship.game.playerpack.Player;
import com.battleship.networking.NetworkConnection;
import com.battleship.utils.BSConfigFile;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class Window {

    /**
     *
     */
    private static final long serialVersionUID = 4453499308378636423L;
    private JFrame frame;
    private JPanel panel;
    private JButton b_start_host;
    private JButton b_start_join;
    private JButton b_exit;
    private JButton b_settings;
    private JLabel gameName;

    private final EventHandler eventHandler = new EventHandler();

    protected String ip;
    protected int port;
    private NetworkConnection connection;

    /**
     * Constructor of the Window class
     */
    public Window() {
        initUI();
    }

    /**
     * Initializes the User Interface
     */
    private void initUI() {

        frame = new JFrame("BattleShip Game");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b_exit.addActionListener(eventHandler);
        b_start_host.addActionListener(eventHandler);
        b_start_join.addActionListener(eventHandler);
        b_settings.addActionListener(eventHandler);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.initPlayer();
    }

    private void initPlayer() {
        Player.setName(BSConfigFile.readProperties("Name"));
        Player.setAvatar(BSConfigFile.readProperties("Avatar_Path"));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panel.getFont()), null));
        b_start_host = new JButton();
        b_start_host.setIcon(new ImageIcon(getClass().getResource("/server.png")));
        b_start_host.setText("Host Game");
        b_start_host.setToolTipText("Host a game");
        panel.add(b_start_host, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        b_start_join = new JButton();
        b_start_join.setIcon(new ImageIcon(getClass().getResource("/link.png")));
        b_start_join.setText("Join Game");
        b_start_join.setToolTipText("Join a game");
        panel.add(b_start_join, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        b_settings = new JButton();
        b_settings.setIcon(new ImageIcon(getClass().getResource("/settings.png")));
        b_settings.setText("Settings");
        b_settings.setToolTipText("Open the settings");
        panel.add(b_settings, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        b_exit = new JButton();
        b_exit.setIcon(new ImageIcon(getClass().getResource("/logout.png")));
        b_exit.setText("Exit");
        b_exit.setToolTipText("Exit the application");
        panel.add(b_exit, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel.add(spacer1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        gameName = new JLabel();
        gameName.setIcon(new ImageIcon(getClass().getResource("/ship.png")));
        gameName.setText("BattleShip game v.0.0.1");
        panel.add(gameName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return panel;
    }

    /**
     * Private class to handle events
     */
    private class EventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == b_exit) {
                System.exit(0);
            } else if (source == b_start_host) {
                SwingUtilities.invokeLater(ServerDialog::new);
                Player.setHost(true);
            } else if (source == b_start_join) {
                SwingUtilities.invokeLater(ClientDialog::new);
            } else if (source == b_settings) {
                SwingUtilities.invokeLater(Settings::new);
            }
        }
    }
}