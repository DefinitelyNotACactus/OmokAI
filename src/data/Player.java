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
    
    private String name;
    private ImageIcon icon;
    private int iconId;
    
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
    
    public void setName(String newName) {
        name = newName;
    }
    
    public String getName() {
        return name;
    }
    
    public void changeIcon(int newIconId) {
        iconId = newIconId;
        icon = ComboBoxRenderer.icons[iconId][0];
    }
    
    public ImageIcon getIcon() {
        return icon;
    }
    
    public ImageIcon getIconState(int state) {
        return ComboBoxRenderer.icons[iconId][state];
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
