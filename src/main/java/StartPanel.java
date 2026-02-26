import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StartPanel extends JPanel 
    {
        private ImageIcon welcome = new ImageIcon(StartPanel.class.getResource("/images/icons/Welcome!.gif"));
        private JLabel label = new JLabel(welcome);
        private Timer timer;

        public StartPanel(MainFrame mainFrame)
            {
                this.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
                this.setVisible(true);
                this.add(label);

                timer = new Timer(1000, e -> {
                    mainFrame.remove(this);
                    try 
                        {
                            mainFrame.openMainPanel();
                        } 
                    catch (SQLException e1) 
                        {
                            e1.printStackTrace();
                        }
                });
                timer.setRepeats(false);
                timer.start();
           
            }
    }
