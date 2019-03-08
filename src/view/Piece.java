/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.Player;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

/**
 *
 * @author david
 */
public class Piece extends JButton {
    private static final Dimension DIMENSION = new Dimension(23,23);
    
    private Game game;
    
    private int x;
    private int y;
    private Player owner;
    
    public Piece(Game game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.owner = null;
        
        initComponents();
    }
    
    private void initComponents() {
        setPreferredSize(DIMENSION);
        setMaximumSize(DIMENSION);
        setMinimumSize(DIMENSION);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addActionListener(this::pieceActionPerformed);
    }
    
    private void pieceActionPerformed(ActionEvent evt) {
        if(isEmpty() && !game.gameFinished()) {
            setOwner();
            setIcon(owner.getIconState(game.getAnimationState()));
            game.processTurn(x, y);
            repaint();
        }
    }
    
    public boolean isEmpty() {
        return (owner == null);
    }
    
    private void setOwner() {
        owner = game.getCurrentPlayer();
    }
    
    public Player getOwner() {
        return owner;
    }
}
