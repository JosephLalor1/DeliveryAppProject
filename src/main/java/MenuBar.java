//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 
//Purpose:
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuBar extends JPanel
    {
        private JButton exitButton = new JButton("Exit");
        private JButton homeButton = new JButton("Home");
        private JButton orderButton = new JButton("My Orders");
        public MenuBar(MainPanel mainPanel) 
            {
                exitButton.addActionListener(e -> exit());
                orderButton.addActionListener(e -> {
                    try 
                        {
                            mainPanel.openOrderPanel();
                            RestaurantPanel.deselectAllAds();
                        } 
                    catch (SQLException e1) 
                        {
                            e1.printStackTrace();
                        }
                });                
                homeButton.addActionListener(e -> {
                    try 
                        {
                            if (mainPanel != null)
                                {
                                    mainPanel.openHomePanel();
                                    RestaurantPanel.deselectAllAds();
                                }
                        } 
                    catch (SQLException e1) 
                        {
                            e1.printStackTrace();
                        }
                });
                this.setLayout(new GridLayout(1, 4));
                add(homeButton);
                add(orderButton);
                add(exitButton);

            }
        public static void exit()
            {
                DBOperations.close();
                System.exit(0);
            }
    }
