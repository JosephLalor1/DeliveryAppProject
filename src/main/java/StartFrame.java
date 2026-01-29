import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class StartFrame 
    {
        private ImageIcon welcome = new ImageIcon(StartFrame.class.getResource("/images/icons/Welcome!.gif"));
        private JFrame starter = new JFrame("Welcome!");
        private JLabel label = new JLabel(welcome);
        private Timer timer;

        public StartFrame()
            {
                starter.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
                starter.setVisible(true);
                starter.setLocationRelativeTo(null);
                starter.add(label);
                starter.setExtendedState(JFrame.MAXIMIZED_BOTH);

                timer = new Timer(1000, e -> {
                    starter.dispose();
                    DBOperations.Connect();
                    try 
                        {
                            new MainFrame();
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
