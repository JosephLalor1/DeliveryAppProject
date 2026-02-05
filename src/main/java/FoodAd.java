import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class FoodAd extends JButton implements ActionListener {
    private JLabel name;
    private JLabel desc;
    private ImageIcon food;
    private Image foodScale;
    private String imageAddress;
    private ImageIcon foodLoad;
    private int numClicks = 0;
    private int id = 0;
    private RestaurantPanel superPanel;
    private JLabel foodLabel;


    public FoodAd(ResultSet results, RestaurantPanel panel) throws SQLException
        {
            this.setLayout(new GridLayout(1, 4));
            this.setUI(new BasicButtonUI());
            this.setBackground(Color.WHITE);

            Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1, true);
            this.setBorder(defaultBorder);

            imageAddress = results.getString("imgAddress");
            foodLoad = new ImageIcon (FoodAd.class.getResource(imageAddress));
            foodScale = foodLoad.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT);
            food = new ImageIcon(foodScale);

            id = results.getInt("menuid");
            superPanel = panel;

            foodLabel = new JLabel(food);

            name = new JLabel(results.getString("name"), SwingConstants.CENTER);
            name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            
            desc = new JLabel(results.getString("descript"), SwingConstants.CENTER);
            desc.setAlignmentY(SwingConstants.TOP);
            
            this.add(foodLabel);
            this.add(name);
            this.add(desc);
            
            this.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setVisible(true);
            this.setMaximumSize(new Dimension(2000, 200));
            this.addActionListener(this);
        }
    //making the button select/unselect
    public void actionPerformed(ActionEvent e) 
        {
            if (numClicks == 0)
                {
                    this.setBackground(Color.LIGHT_GRAY);
                    numClicks = (numClicks + 1) % 2;
                    superPanel.addToSelect(id);
                }
            else
                {
                    this.setBackground(Color.WHITE);
                    numClicks = (numClicks + 1) % 2;
                    superPanel.deleteFromSelect(id);
                }
        }

}

