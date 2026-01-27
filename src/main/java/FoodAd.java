import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.*;

import java.awt.Color;

import java.awt.GridLayout;

import java.awt.Font;

public class FoodAd extends JButton {
    private String name;
    private String desc;
    private ImageIcon image;

    public FoodAd(ResultSet results) throws SQLException
        {
            this.setLayout(new GridLayout(1, 4));
            String imageAddress = results.getString("imgAddress");
            ImageIcon foodLoad = new ImageIcon (FoodAd.class.getResource(imageAddress));
            Image foodScale = foodLoad.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT);
            ImageIcon food = new ImageIcon(foodScale);

            JLabel name = new JLabel(results.getString("name"), SwingConstants.CENTER);
            name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            

            JLabel desc = new JLabel(results.getString("descript"), SwingConstants.CENTER);
            desc.setAlignmentY(SwingConstants.TOP);
            

            this.add(name);
            this.add(desc);
            this.setIcon(food);
            this.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setIconTextGap(10);
            this.setVisible(true);
            this.setMaximumSize(new Dimension(2000, 200));

            
            this.addActionListener(new ActionListener() {
                int i = 0;
                if(i == 0)
                    {
                        this.setOpaque(true);         
                        this.setForeground(Color.RED);
                        name.setOpaque(true);
                        name.setBackground(Color.RED);
                        desc.setOpaque(true);
                        desc.setBackground(Color.RED);
                        i = (i + 1) % 2;
                    }
                else
                    {
                        this.setOpaque(false);         
                        this.setForeground(Color.WHITE);
                        name.setOpaque(false);
                        name.setBackground(Color.WHITE);
                        desc.setOpaque(false);
                        desc.setBackground(Color.WHITE);
                        i = (i + 1) % 2;
                    }  
                    
            });
        }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

