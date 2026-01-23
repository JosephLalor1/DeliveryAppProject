import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class FoodAd extends JButton {
    private String name;
    private String desc;
    private ImageIcon image;

    public FoodAd(ResultSet results) throws SQLException
        {
            super(results.getString("name") + "\n" + results.getString("descript"));
            String imageAddress = results.getString("imgAddress");
            ImageIcon foodLoad = new ImageIcon (FoodAd.class.getResource(imageAddress));
            Image foodScale = foodLoad.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT);
            ImageIcon food = new ImageIcon(foodScale);
            this.setIcon(food);
            this.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setIconTextGap(10);
            this.setVisible(true);
            this.setMaximumSize(new Dimension(2000, 200));
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

