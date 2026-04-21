//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 21/01/2026 
//Purpose: Splash welcome screen
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanel extends JPanel 
    {
        private ImageIcon welcome = new ImageIcon(StartPanel.class.getResource("/images/icons/Welcome!.gif"));
        private JLabel label = new JLabel(welcome);

        public StartPanel() 
            {
                this.setVisible(true);
                this.add(label);
            }
    }