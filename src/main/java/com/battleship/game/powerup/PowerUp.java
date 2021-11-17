package com.battleship.game.powerup;

import javax.swing.*;
import java.awt.*;

public class PowerUp {

    /**
     *  PowerUp constants
     */
    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;
    public static final int MAXDAMAGE = 3;

    /**
     *  Figure out which powerup is being used and call the corresponding method
     */
    public static void handlePowerUp(JButton[][] positions, int[] posToAttack, Color color) {
        if (posToAttack[3] == VERTICAL) {
            vertPowerUp(positions, posToAttack[2], color);
        }
        if (posToAttack[3] == HORIZONTAL) {
            horizontalPowerUp(positions, posToAttack[1], color);
        }
        if (posToAttack[3] == MAXDAMAGE) {
            handleMaxDamage(positions, posToAttack[1], posToAttack[2], color);
        }
    }

    /**
     *  Check the surronding areas for the max damage powerup
     */
    public static void handleMaxDamage(JButton[][] positions, int row, int col, Color color) {
        if (row - 1 >= 0) {
            positions[row - 1][col].setBackground(color);
            positions[row - 1][col].setEnabled(false);
        }
        if (row + 1 <= 9) {
            positions[row + 1][col].setBackground(color);
            positions[row + 1][col].setEnabled(false);
        }
        if (col - 1 >= 0) {
            positions[row][col - 1].setBackground(color);
            positions[row][col - 1].setEnabled(false);
        }
        if (col + 1 <= 9) {
            positions[row][col + 1].setBackground(color);
            positions[row][col + 1].setEnabled(false);
        }
    }

    /**
     *  Display the vertical powerup on a board
     */
    public static void vertPowerUp(JButton[][] positions, int col, Color color) {
        for (int i = 0; i < 10; ++i) {
            // vert line
            positions[i][col].setBackground(color);
            positions[i][col].setEnabled(false);
        }
    }

    /**
     *  Display the horizontal powerup on a board
     */
    public static void horizontalPowerUp(JButton[][] positions, int row, Color color) {
        for (int i = 0; i < 10; ++i) {
            // horizontal line powerup
            positions[row][i].setBackground(color);
            positions[row][i].setEnabled(false);
        }
    }

}
