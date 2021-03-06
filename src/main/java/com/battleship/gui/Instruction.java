package com.battleship.gui;

import com.battleship.utils.BSConfigFile;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instruction {
    public Instruction() {
        JFrame frame = new JFrame("How to Play");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("<html>" + "Two players get connected through open port over internet or locally hosted port, a board is generated x by y square where players are given a set number of ships of random length. Placement of ships in any part inside the given board of their choice. Once both sides are ready, whoever is lucky to get the direct shot is able to make another hit, if the player misses, the next turn is their opponent. A hit or misses on the opponent board will mark the square, making it unavailable to be hit again. With that, the game ended when one of the players no longer had any ship alive." + "</html>\"");
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout());
        frame.setSize(300, 500);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.add(label);
        frame.setVisible(true);
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }
}
