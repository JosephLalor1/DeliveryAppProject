import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class RestaurantPanel extends JPanel
    {
        private JPanel scrollContainer = new JPanel();
        private JScrollPane scrollFood = new JScrollPane(scrollContainer);
        public RestaurantPanel(int row) throws SQLException
            {
                ResultSet rs = DBOperations.dbResults("restaurants", row);
                String imageAddress = rs.getString("imgAddress");
                ImageIcon foodLoad = new ImageIcon (MainPanel.class.getResource(imageAddress));
                Image foodScale = foodLoad.getImage().getScaledInstance(250, 125, Image.SCALE_DEFAULT);
                ImageIcon food = new ImageIcon(foodScale);
                JLabel label = new JLabel(food);
                JLabel name = new JLabel(rs.getString("name"));
                JLabel desc = new JLabel(rs.getString("descript"));
                scrollContainer.setLayout(new GridLayout(7, 1));                               
                scrollFood.getVerticalScrollBar().setUnitIncrement(16);
                

                this.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 0.1;
                gbc.weighty = 0.4;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(label, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weightx = 0.1;
                gbc.weighty = 0.2;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(name, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.weightx = 0.1;
                gbc.weighty = 0.3;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(desc, gbc);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.weightx = 0.9;
                gbc.weighty = 1.0;
                gbc.gridwidth = 2;
                gbc.gridheight = 3;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(scrollFood, gbc);

                this.setVisible(true);
            }
    }
