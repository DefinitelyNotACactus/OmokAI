/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author david
 */
public class Piece extends JButton {
    //TODO add more options
    private static final ImageIcon ICON_1 = new ImageIcon(Piece.class.getResource("/resources/Omok.stone.0.white.1.png"));
    private static final ImageIcon ICON_2 = new ImageIcon(Piece.class.getResource("/resources/Omok.stone.1.black.1.png")); 
    
    private static final Dimension DIMENSION = new Dimension(23,23);
    
    private boolean empty;
    private Game game;
    
    private int x;
    private int y;
    private int owner;
    
    public Piece(Game game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.owner = -1;
        
        empty = false;
        
        setPreferredSize(DIMENSION);
        setMaximumSize(DIMENSION);
        setMinimumSize(DIMENSION);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addActionListener(this::pieceActionPerformed);
    }
    
    private void pieceActionPerformed(ActionEvent evt) {
        if(!empty && !game.gameFinished()) {
            setOwner();
            setIcon(owner == 0 ? ICON_1 : ICON_2);
            empty = true;
            game.searchCombo(x, y);
            repaint();
        }
    }
    
    public boolean isEmpty() {
        return empty;
    }
    
    private void setOwner() {
        owner = game.getPlayer();
    }
    
    public int getOwner() {
        return owner;
    }
}
