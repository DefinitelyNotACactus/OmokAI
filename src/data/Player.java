/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import javax.swing.ImageIcon;
import view.util.ComboBoxRenderer;

/**
 *
 * @author david
 */
public class Player implements Serializable { //we may want to serialize this later
    private static final long serialVersionUID = 13L;
    
    private final String name;
    private final ImageIcon icon;
    private final int iconId;
    
    private int wins;
    private int ties;
    private int losses;
    
    public Player(String name, int iconId) {
        this.name = name;
        this.iconId = iconId;
        icon = ComboBoxRenderer.icons[iconId][0];
        
        wins = 0;
        ties = 0;
        losses = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public ImageIcon getIcon() {
        return icon;
    }
    
    public int getIconId() {
        return iconId;
    }
    
    public int getWins() {
        return wins;
    }
    
    public void addWin() {
        wins++;
    }
    
    public int getTies() {
        return ties;
    }
    
    public void addTie() {
        ties++;
    }
    
    public int getLosses() {
        return losses;
    }
    
    public void addLoss() {
        losses++;
    }
}
