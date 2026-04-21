//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 
//Purpose:
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OrderPanel extends JPanel
    {
        private GridBagConstraints gbc = new GridBagConstraints();
        private TrackingPanel tPanel = new TrackingPanel();
        private JLabel trackTitle;
        private JLabel placeholder;
        public OrderPanel() throws SQLException
            {
                this.setLayout(new GridBagLayout());

                trackTitle = new JLabel("Track your order", SwingConstants.CENTER);
                trackTitle.setFont(new Font("Times New Roman", Font.PLAIN, 20));

                placeholder = new JLabel("Placeholder", SwingConstants.CENTER);

                //left order details
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 0.1;
                gbc.weighty = 0.1;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(placeholder, gbc);

                //tracking title
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.weightx = 0.1;
                gbc.weighty = 0.1;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(trackTitle, gbc);

                //tracking panel section
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(tPanel, gbc);

            }
    }
