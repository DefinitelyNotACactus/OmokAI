package view;

import data.Mordekai;
import data.Player;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import view.util.ComboBoxRenderer;

/**
 *
 * @author david
 */
public class ModeSelect extends JPanel {
    
    public enum ModeEnum {
        UNDEFINED(-1), AI_ONLY(0), PLAYER_AI(1), PLAYER_PLAYER(2);
        
        private final int code;
        
        private ModeEnum(int code) {
            this.code = code;
        }
        
        public int getModeCode() {
            return code;
        }
    }
    
    private ComboBoxRenderer renderer;
    private Integer array[];
    
    /** Stores the game mode.
     * Possible values:
     * -1: N/A.
     * 0: Player vs. AI.
     * 1: Player vs. Player.
     */
    private ModeEnum mode;
    private Game game;
    private Player player1;
    private Player player2;
    
    public ModeSelect(Game game) {
        this.game = game;
        
        renderer = new ComboBoxRenderer();
        array = new Integer[8];
        for(int i = 0; i < 8; i++) {
            array[i] = i;
        }
        mode = ModeEnum.UNDEFINED;
        
        initComponents();
        add(modePanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createPlayer1Panel = new javax.swing.JPanel();
        player1NameTextField = new javax.swing.JTextField();
        player1PieceComboBox = new javax.swing.JComboBox(array);
        btPlayer1Ready = new javax.swing.JButton();
        createPlayer2Panel = new javax.swing.JPanel();
        player2NameTextField = new javax.swing.JTextField();
        player2PieceComboBox = new javax.swing.JComboBox(array);
        btPlayer2Ready = new javax.swing.JButton();
        modePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        btPvai = new javax.swing.JButton();
        btPvp = new javax.swing.JButton();
        btAiRoyale = new javax.swing.JButton();

        createPlayer1Panel.setBackground(new java.awt.Color(0, 0, 0));
        createPlayer1Panel.setMinimumSize(new java.awt.Dimension(160, 160));
        createPlayer1Panel.setSize(new java.awt.Dimension(160, 160));
        createPlayer1Panel.setLayout(new java.awt.GridLayout(0, 1, 5, 5));

        player1NameTextField.setBackground(new java.awt.Color(0, 0, 0));
        player1NameTextField.setForeground(new java.awt.Color(255, 255, 255));
        player1NameTextField.setText("Player 1");
        createPlayer1Panel.add(player1NameTextField);

        player1PieceComboBox.setBackground(new java.awt.Color(0, 0, 0));
        player1PieceComboBox.setForeground(new java.awt.Color(255, 255, 255));
        player1PieceComboBox.setSelectedIndex(0);
        player1PieceComboBox.setRenderer(renderer);
        createPlayer1Panel.add(player1PieceComboBox);

        btPlayer1Ready.setBackground(new java.awt.Color(0, 0, 0));
        btPlayer1Ready.setForeground(new java.awt.Color(255, 255, 255));
        btPlayer1Ready.setText("Ready");
        btPlayer1Ready.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPlayer1ReadyActionPerformed(evt);
            }
        });
        createPlayer1Panel.add(btPlayer1Ready);

        createPlayer2Panel.setBackground(new java.awt.Color(255, 255, 255));
        createPlayer2Panel.setMinimumSize(new java.awt.Dimension(160, 160));
        createPlayer2Panel.setSize(new java.awt.Dimension(160, 160));
        createPlayer2Panel.setLayout(new java.awt.GridLayout(0, 1, 5, 5));

        player2NameTextField.setText("Player 2");
        createPlayer2Panel.add(player2NameTextField);

        player2PieceComboBox.setBackground(new java.awt.Color(255, 255, 255));
        player2PieceComboBox.setSelectedIndex(1);
        player2PieceComboBox.setRenderer(renderer);
        createPlayer2Panel.add(player2PieceComboBox);

        btPlayer2Ready.setBackground(new java.awt.Color(255, 255, 255));
        btPlayer2Ready.setText("Ready");
        btPlayer2Ready.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPlayer2ReadyActionPerformed(evt);
            }
        });
        createPlayer2Panel.add(btPlayer2Ready);

        modePanel.setBackground(new java.awt.Color(255, 255, 255));
        modePanel.setMinimumSize(new java.awt.Dimension(160, 160));
        modePanel.setLayout(new java.awt.GridLayout(0, 1, 5, 5));

        titleLabel.setBackground(new java.awt.Color(255, 255, 255));
        titleLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        titleLabel.setText("OmokAI");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        modePanel.add(titleLabel);

        btPvai.setBackground(new java.awt.Color(255, 255, 255));
        btPvai.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        btPvai.setText("Player vs. AI");
        btPvai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPvaiActionPerformed(evt);
            }
        });
        modePanel.add(btPvai);

        btPvp.setBackground(new java.awt.Color(255, 255, 255));
        btPvp.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        btPvp.setText("Player vs. Player");
        btPvp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPvpActionPerformed(evt);
            }
        });
        modePanel.add(btPvp);

        btAiRoyale.setBackground(new java.awt.Color(255, 255, 255));
        btAiRoyale.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        btAiRoyale.setText("AI vs. AI");
        btAiRoyale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAiRoyaleActionPerformed(evt);
            }
        });
        modePanel.add(btAiRoyale);

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
    }// </editor-fold>//GEN-END:initComponents

    private void btPvaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPvaiActionPerformed
        mode = ModeEnum.PLAYER_AI;
        remove(modePanel);
        add(createPlayer1Panel);
        revalidate();
    }//GEN-LAST:event_btPvaiActionPerformed

    private void btPvpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPvpActionPerformed
        mode = ModeEnum.PLAYER_PLAYER;
        remove(modePanel);
        add(createPlayer1Panel);
        revalidate();
    }//GEN-LAST:event_btPvpActionPerformed

    private void btPlayer1ReadyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPlayer1ReadyActionPerformed
        if((mode == ModeEnum.PLAYER_AI && player2PieceComboBox.getSelectedItem() != player1PieceComboBox.getSelectedItem()) || mode == ModeEnum.PLAYER_PLAYER) {
            player1 = new Player(player1NameTextField.getText(), (Integer) player1PieceComboBox.getSelectedItem());
            if(mode == ModeEnum.PLAYER_PLAYER) {
                remove(createPlayer1Panel);
                add(createPlayer2Panel);
            } else {
                player2 = new Mordekai(game, 3);
                game.modeSelected(mode, player1, player2);
                setVisible(false);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Piece", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btPlayer1ReadyActionPerformed

    private void btPlayer2ReadyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPlayer2ReadyActionPerformed
        if(player2PieceComboBox.getSelectedItem() != player1PieceComboBox.getSelectedItem()) {
            player2 = new Player(player2NameTextField.getText(), (Integer) player2PieceComboBox.getSelectedItem());
            game.modeSelected(mode, player1, player2);
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Pieces must be different!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btPlayer2ReadyActionPerformed

    private void btAiRoyaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAiRoyaleActionPerformed
        mode = ModeEnum.AI_ONLY;
        player1 = new Mordekai(game, 4);
        player1.setName("Mordekai 1");
        player1.changeIcon(0);
        player2 = new Mordekai(game, 3);
        player2.setName("Mordekai 2");
        game.modeSelected(mode, player1, player2);
        setVisible(false);
    }//GEN-LAST:event_btAiRoyaleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAiRoyale;
    private javax.swing.JButton btPlayer1Ready;
    private javax.swing.JButton btPlayer2Ready;
    private javax.swing.JButton btPvai;
    private javax.swing.JButton btPvp;
    private javax.swing.JPanel createPlayer1Panel;
    private javax.swing.JPanel createPlayer2Panel;
    private javax.swing.JPanel modePanel;
    private javax.swing.JTextField player1NameTextField;
    private javax.swing.JComboBox<String> player1PieceComboBox;
    private javax.swing.JTextField player2NameTextField;
    private javax.swing.JComboBox<String> player2PieceComboBox;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
