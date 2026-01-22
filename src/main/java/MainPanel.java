import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


public class MainPanel extends JPanel
    {
        private final String HOMEPAGE = "Home page";
        private CardLayout cl = new CardLayout();
        private JPanel cards = new JPanel(cl);
        private JPanel card1Container = new JPanel();
        private JScrollPane card1 = new JScrollPane(card1Container);
        private int numAds = DBOperations.count("restaurants");
        private RestaurantAd[] ads = new RestaurantAd[numAds];

        public void openHomePanel() throws SQLException
            {
                cl.show(cards, HOMEPAGE);
            }
        public void openRestaurantPanel(String name) throws SQLException
            {
                cl.show(cards, name);
            }

        public void displayBox()
            {
                JOptionPane.showMessageDialog (MainPanel.this, DBOperations.Display("orders"));
            }
        public MainPanel() throws SQLException
            {                                                
                card1Container.setLayout(new GridLayout(numAds, 1));               
                
                card1.getVerticalScrollBar().setUnitIncrement(16);

                cards.add(card1, HOMEPAGE);
                
                this.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();

                MenuBar menuBar = new MenuBar(this);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1;
                gbc.weighty = 0.1;
                gbc.gridwidth = 4;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(menuBar, gbc);

                gbc.weighty = 1.0;
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 4;
                gbc.gridheight = 9;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(cards, gbc);
                this.setVisible(true);

                for(int i = 1; i <= numAds; i++)
                    {
                        ResultSet rs = DBOperations.dbResults("restaurants", i);
                        String imageAddress = rs.getString("imgAddress");
                        ImageIcon foodLoad = new ImageIcon (MainPanel.class.getResource(imageAddress));
                        Image foodScale = foodLoad.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT);
                        ImageIcon food = new ImageIcon(foodScale);
                        ads[i - 1] = new RestaurantAd(rs, food, this);
                        card1Container.add(ads[i - 1]);
                        cards.add(new RestaurantPanel(i), rs.getString("name"));
                    }

            }
    }
