import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuBar extends JPanel
    {
        public MenuBar(MainPanel mainPanel) 
            {
                JButton exitButton = new JButton("Exit");
                exitButton.addActionListener(e -> exit());
                JButton homeButton = new JButton("Home");
                homeButton.addActionListener(e -> {
                    try 
                        {
                            mainPanel.openHomePanel();
                        } 
                    catch (SQLException e1) 
                        {
                            e1.printStackTrace();
                        }
                });
                this.setLayout(new GridLayout(1, 4));
                add(homeButton);
                add(new JButton("My Orders"));
                add(new JButton("My Account"));
                add(exitButton);

            }
        public static void exit()
            {
                DBOperations.close();
                System.exit(0);
            }
    }
