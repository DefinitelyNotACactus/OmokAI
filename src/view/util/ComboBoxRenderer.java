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

    public static ImageIcon icons[][];
    
    public ComboBoxRenderer() {
        loadIcons();
        initComponents();
        
    }
    
    private void initComponents() {
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
        setIcon(icons[selectedIndex][0]);
        return this;
    }
    
    private void loadIcons() { //hardcoded
        icons = new ImageIcon[8][3];
        icons[0][0] = new ImageIcon(getClass().getResource("/resources/Omok.stone.0.white.0.png"));
        icons[0][1] = new ImageIcon(getClass().getResource("/resources/Omok.stone.0.white.1.png"));
        icons[0][2] = new ImageIcon(getClass().getResource("/resources/Omok.stone.0.white.2.png"));
        
        icons[1][0] = new ImageIcon(getClass().getResource("/resources/Omok.stone.1.black.0.png"));
        icons[1][1] = new ImageIcon(getClass().getResource("/resources/Omok.stone.1.black.1.png"));
        icons[1][2] = new ImageIcon(getClass().getResource("/resources/Omok.stone.1.black.2.png"));
        
        icons[2][0] = new ImageIcon(getClass().getResource("/resources/Omok.stone.2.black.0.png"));
        icons[2][1] = new ImageIcon(getClass().getResource("/resources/Omok.stone.2.black.1.png"));
        icons[2][2] = new ImageIcon(getClass().getResource("/resources/Omok.stone.2.black.2.png"));
        
        icons[3][0] = new ImageIcon(getClass().getResource("/resources/Omok.stone.3.white.0.png"));
        icons[3][1] = new ImageIcon(getClass().getResource("/resources/Omok.stone.3.white.1.png"));
        icons[3][2] = new ImageIcon(getClass().getResource("/resources/Omok.stone.3.white.2.png"));
        
        icons[4][0] = new ImageIcon(getClass().getResource("/resources/Omok.stone.6.white.0.png"));
        icons[4][1] = new ImageIcon(getClass().getResource("/resources/Omok.stone.6.white.1.png"));
        icons[4][2] = new ImageIcon(getClass().getResource("/resources/Omok.stone.6.white.2.png"));
        
        icons[5][0] = new ImageIcon(getClass().getResource("/resources/Omok.stone.6.black.0.png"));
        icons[5][1] = new ImageIcon(getClass().getResource("/resources/Omok.stone.6.black.1.png"));
        icons[5][2] = new ImageIcon(getClass().getResource("/resources/Omok.stone.6.black.2.png"));
        
        icons[6][0] = new ImageIcon(getClass().getResource("/resources/Omok.stone.7.black.0.png"));
        icons[6][1] = new ImageIcon(getClass().getResource("/resources/Omok.stone.7.black.1.png"));
        icons[6][2] = new ImageIcon(getClass().getResource("/resources/Omok.stone.7.black.0.png"));
        
        icons[7][0] = new ImageIcon(getClass().getResource("/resources/Omok.stone.9.white.0.png"));
        icons[7][1] = new ImageIcon(getClass().getResource("/resources/Omok.stone.9.white.1.png"));
        icons[7][2] = new ImageIcon(getClass().getResource("/resources/Omok.stone.9.white.2.png"));
    }
}
