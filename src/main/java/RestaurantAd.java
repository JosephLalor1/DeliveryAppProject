import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class RestaurantAd extends JButton {
    private String name;
    private String desc;
    private ImageIcon image;

    public RestaurantAd(ResultSet results, ImageIcon image, MainPanel mainPanel) throws SQLException
        {
            super(results.getString("name") + "\n" + results.getString("descript"), image);
            this.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setIconTextGap(10);
            this.setVisible(true);
            this.setMaximumSize(new Dimension(2000, 200));
            this.addActionListener(e -> {
                try {
                    mainPanel.openRestaurantPanel(results.getString("name"));
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
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
