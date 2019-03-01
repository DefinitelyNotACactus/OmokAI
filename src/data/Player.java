/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import javax.swing.ImageIcon;

/**
 *
 * @author david
 */
public class Player {
    private final String name;
    private final ImageIcon icon;
    
    public Player(String name, ImageIcon icon) {
        this.name = name;
        this.icon = icon;
    }
    
    public String getName() {
        return name;
    }
    
    public ImageIcon getIcon() {
        return icon;
    }
}
