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
public class Piece extends JButton implements Cloneable {
    private static final Dimension DIMENSION = new Dimension(23,23);
    
    private Game game;
    private Player owner;
    private int x;
    private int y;
    
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
        if(isEmpty() && !game.gameFinished() && !game.isAiTurn()) {
            setOwner(game.getCurrentPlayer()); // The ai will never call this method with a false parameter
            setIcon(owner.getIconState(game.getAnimationState()));
            game.processTurn(x, y);
            repaint();
        }
    }
    
    public boolean isEmpty() {
        return (owner == null);
    }
    
    public void setOwner(Player newOwner) {
        owner = newOwner;
    }
    
    public Player getOwner() {
        return owner;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
