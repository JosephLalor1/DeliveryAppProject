
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class RestaurantPanel extends JPanel
    {
        private JPanel scrollContainer = new JPanel();
        private JScrollPane scrollFood = new JScrollPane(scrollContainer);
        public RestaurantPanel(int row) throws SQLException
            {
                ResultSet rstResults = DBOperations.dbResults("restaurants", row);

                String imgAddress = rstResults.getString("imgAddress");
                ImageIcon foodLoad = new ImageIcon (RestaurantAd.class.getResource(imgAddress));
                Image foodScale = foodLoad.getImage().getScaledInstance(250, 125, Image.SCALE_DEFAULT);
                ImageIcon food = new ImageIcon(foodScale);
                JLabel imgLabel = new JLabel(food);

                JLabel name = new JLabel(rstResults.getString("name"), SwingConstants.CENTER);
                name.setFont(new Font("Times New Roman", Font.PLAIN, 20));

                JLabel desc = new JLabel(rstResults.getString("descript"), SwingConstants.CENTER);
                desc.setAlignmentY(SwingConstants.TOP);
                
                scrollContainer.setLayout(new GridLayout(7, 1));                               
                scrollFood.getVerticalScrollBar().setUnitIncrement(16);
                

                this.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();

                //image section
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 0.1;
                gbc.weighty = 0.1;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(imgLabel, gbc);

                //name section
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weightx = 0.1;
                gbc.weighty = 0.1;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(name, gbc);

                //description section
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.weightx = 0.1;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(desc, gbc);

                //food items section
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.weightx = 0.9;
                gbc.weighty = 1.0;
                gbc.gridwidth = 2;
                gbc.gridheight = 3;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(scrollFood, gbc);

                this.setVisible(true);
                ResultSet foodResults = DBOperations.dbResults("menuItems", row);
                do
                    {
                        scrollContainer.add(new FoodAd(foodResults));
                    }
                while(foodResults.next());
            }
    }
