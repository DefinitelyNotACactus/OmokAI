/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.util;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 *
 * @author david
 */
public class ComboBoxRenderer extends JLabel implements ListCellRenderer {

    public static ImageIcon icons[];
    
    public ComboBoxRenderer() {
        loadIcons();
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setPreferredSize(new Dimension(25,25));
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focused) {
        int selectedIndex = (Integer) value;
        
        if(selected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setIcon(icons[selectedIndex]);
        return this;
    }
    
    private void loadIcons() {
        icons = new ImageIcon[7];
        icons[0] = new ImageIcon(getClass().getResource("/resources/Omok.stone.0.white.1.png"));
        icons[1] = new ImageIcon(getClass().getResource("/resources/Omok.stone.1.black.1.png"));
        icons[2] = new ImageIcon(getClass().getResource("/resources/Omok.stone.2.black.1.png"));
        icons[3] = new ImageIcon(getClass().getResource("/resources/Omok.stone.6.white.1.png"));
        icons[4] = new ImageIcon(getClass().getResource("/resources/Omok.stone.6.black.1.png"));
        icons[5] = new ImageIcon(getClass().getResource("/resources/Omok.stone.7.black.1.png"));
        icons[6] = new ImageIcon(getClass().getResource("/resources/Omok.stone.9.white.1.png"));
    }
}
