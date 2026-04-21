//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 21/04/2026
//Purpose: display active order details.
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OrderDetailsPanel extends JPanel
    {
        private GridBagConstraints gbc = new GridBagConstraints();
        private JLabel title;
        private JLabel orderIdLabel;
        private List<JLabel> foodLabels = new ArrayList<>();

        public OrderDetailsPanel()
            {
                this.setLayout(new GridBagLayout());
                refresh();
            }

        // Rebuilds the panel based on the current active order, or shows a "no orders" message.
        public void refresh()
            {
                this.removeAll();
                foodLabels.clear();

                int orderId = -1;
                List<String> foodNames = new ArrayList<>();

                try
                    {
                        PreparedStatement pstat = DBOperations.connection.prepareStatement(
                            "SELECT o.orderid, m.name " +
                            "FROM orders o JOIN menuItems m ON o.menuid = m.menuid " +
                            "WHERE o.delivered = 0");
                        ResultSet rs = pstat.executeQuery();
                        while (rs.next())
                            {
                                if (orderId == -1) orderId = rs.getInt("orderid");
                                foodNames.add(rs.getString("name"));
                            }
                        rs.close();
                        pstat.close();
                    }
                catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                if (orderId == -1)
                    {
                        // No active order
                        JLabel none = new JLabel("No active orders right now", SwingConstants.CENTER);
                        none.setFont(new Font("Times New Roman", Font.PLAIN, 20));

                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.weightx = 1.0;
                        gbc.weighty = 1.0;
                        gbc.gridwidth = 1;
                        gbc.gridheight = 1;
                        gbc.fill = GridBagConstraints.BOTH;
                        this.add(none, gbc);
                    }
                else
                    {
                        // Title
                        title = new JLabel("Order Details", SwingConstants.CENTER);
                        title.setFont(new Font("Times New Roman", Font.BOLD, 22));

                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.weightx = 1.0;
                        gbc.weighty = 0.1;
                        gbc.gridwidth = 1;
                        gbc.gridheight = 1;
                        gbc.fill = GridBagConstraints.BOTH;
                        this.add(title, gbc);

                        // Order ID
                        orderIdLabel = new JLabel("Order #" + orderId, SwingConstants.CENTER);
                        orderIdLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));

                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        gbc.weightx = 1.0;
                        gbc.weighty = 0.1;
                        gbc.gridwidth = 1;
                        gbc.gridheight = 1;
                        gbc.fill = GridBagConstraints.BOTH;
                        this.add(orderIdLabel, gbc);

                        // Food items — one label per line
                        int gridY = 2;
                        for (String foodName : foodNames)
                            {
                                JLabel foodLabel = new JLabel("• " + foodName, SwingConstants.CENTER);
                                foodLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                                foodLabels.add(foodLabel);

                                gbc.gridx = 0;
                                gbc.gridy = gridY++;
                                gbc.weightx = 1.0;
                                gbc.weighty = 0.1;
                                gbc.gridwidth = 1;
                                gbc.gridheight = 1;
                                gbc.fill = GridBagConstraints.BOTH;
                                this.add(foodLabel, gbc);
                            }
                    }

                this.revalidate();
                this.repaint();
            }
    }