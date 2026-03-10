import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class MainPanel extends JPanel
    {
        private final String HOMEPAGE = "Home page";
        private final String ORDERSPAGE = "Orders page";
        private CardLayout cl = new CardLayout();
        private JPanel cards = new JPanel(cl);
        private JPanel menuCardContainer = new JPanel();
        private JScrollPane menuCard = new JScrollPane(menuCardContainer);
        private OrderPanel orderPanel = new OrderPanel(); 
        private int numAds = DBOperations.count("restaurants");
        private GridBagConstraints gbc = new GridBagConstraints();
        private MenuBar menuBar;
        private ResultSet rs = DBOperations.dbResults("restaurants");
        private int row = 0;

        public void openHomePanel() throws SQLException
            {
                cl.show(cards, HOMEPAGE);
            }
        public void openOrderPanel() throws SQLException
            {
                cl.show(cards, ORDERSPAGE);
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
                menuCardContainer.setLayout(new GridLayout(numAds, 1));               
                
                menuCard.getVerticalScrollBar().setUnitIncrement(16);

                cards.add(menuCard, HOMEPAGE);
                cards.add(orderPanel, ORDERSPAGE);
                
                this.setLayout(new GridBagLayout());

                menuBar = new MenuBar(this);
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
                
                do
                    {
                        row = rs.getInt("restaurantid");
                        menuCardContainer.add(new RestaurantAd(row, this));
                        cards.add(new RestaurantPanel(rs.getInt("restaurantid")), rs.getString("name"));
                    }
                while(rs.next());
            }
    }
