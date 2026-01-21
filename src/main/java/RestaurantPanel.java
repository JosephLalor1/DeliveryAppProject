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
public class RestaurantPanel extends JPanel
    {
        public RestaurantPanel(int row) throws SQLException
            {
                ResultSet rs = DBOperations.dbResults("restaurants", row);
                String imageAddress = rs.getString("imgAddress");
                ImageIcon foodLoad = new ImageIcon (MainPanel.class.getResource(imageAddress));
                Image foodScale = foodLoad.getImage().getScaledInstance(1200, 600, Image.SCALE_DEFAULT);
                ImageIcon food = new ImageIcon(foodScale);
                JLabel label = new JLabel(food);
                this.add(label);
            }
    }
