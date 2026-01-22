import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class StartFrame 
    {
        public static void startFrame()
            {
                
                ImageIcon welcome = new ImageIcon(StartFrame.class.getResource("/images/icons/Welcome!.gif"));
                JFrame starter = new JFrame("Welcome!");
                JLabel label = new JLabel(welcome);
                
                starter.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
                starter.setVisible(true);
                starter.setLocationRelativeTo(null);
                starter.add(label);
                starter.setExtendedState(JFrame.MAXIMIZED_BOTH);

                Timer timer = new Timer(1000, e -> {
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
