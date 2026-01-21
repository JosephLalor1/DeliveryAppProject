import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class RestaurantAd extends JButton {
    private String name;
    private String desc;
    private ImageIcon image;
    public RestaurantAd(String name, String desc, ImageIcon image)
        {
            super(name + "\n" + desc, image);
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
