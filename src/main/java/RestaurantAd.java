//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 21/01/2026 
//Purpose: Restaurant ad button
import java.awt.Dimension;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class RestaurantAd extends JButton {
    private ResultSet results;
    private ResultSet foodResults;
    private String imageAddress;
    private ImageIcon foodLoad;
    private Image foodScale;
    private ImageIcon food;

    public RestaurantAd(int row, MainPanel mainPanel) throws SQLException
        {
            results = DBOperations.dbResults("restaurants", row);
            imageAddress = results.getString("imgAddress");
            foodLoad = new ImageIcon (RestaurantAd.class.getResource(imageAddress));
            foodScale = foodLoad.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT);
            food = new ImageIcon(foodScale);
            this.setIcon(food);

            this.setText(results.getString("name") + results.getString("descript"));
            this.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setIconTextGap(10);
            this.setVisible(true);
            this.setMaximumSize(new Dimension(2000, 200));
            this.addActionListener(e -> {
                try {
                    mainPanel.openRestaurantPanel(results.getString("name"));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
        }
    public RestaurantAd(int row, OrderPanel orderPanel) throws SQLException
        {
            results = DBOperations.dbResults("orders");
            foodResults = DBOperations.dbResults("restaurants", results.getInt("menuid"));
            imageAddress = foodResults.getString("imgAddress");
            foodLoad = new ImageIcon (RestaurantAd.class.getResource(imageAddress));
            foodScale = foodLoad.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT);
            food = new ImageIcon(foodScale);
            this.setIcon(food);

            this.setText(foodResults.getString("name") + foodResults.getString("descript"));
            this.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setIconTextGap(10);
            this.setVisible(true);
            this.setMaximumSize(new Dimension(2000, 200));
        }
}
