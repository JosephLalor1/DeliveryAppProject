import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class OrderPanel extends JPanel
    {
        private JPanel orderCardContainer = new JPanel();
        private JScrollPane orderCard = new JScrollPane(orderCardContainer);
        private int numOrders = DBOperations.count("orders");
        private ResultSet rs = DBOperations.dbResults("orders");
        private int row = 0;

        public OrderPanel() throws SQLException
            {
                orderCardContainer.setLayout(new GridLayout(numOrders, 1));               
                
                orderCard.getVerticalScrollBar().setUnitIncrement(16);
                
                this.setLayout(new GridBagLayout());
                
                do
                    {
                    try {
                        row = rs.getInt("orderid");
                        orderCardContainer.add(new RestaurantAd(row, this));
                    } catch (java.sql.SQLException ex) {
                        System.getLogger(OrderPanel.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                    }
                while(rs.next());
            }
    }
