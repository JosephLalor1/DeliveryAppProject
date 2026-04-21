//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 
//Purpose: A JPanel for list of foods of a restaurant
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class RestaurantPanel extends JPanel
    {
        private JPanel scrollContainer = new JPanel();
        private JScrollPane scrollFood = new JScrollPane(scrollContainer);
        private ResultSet rstResults;
        private String imgAddress;
        private ImageIcon foodLoad;
        private Image foodScale;
        private ImageIcon food;
        private JLabel imgLabel;
        private JLabel name;
        private JLabel desc;
        private GridBagConstraints gbc = new GridBagConstraints();
        private ResultSet foodResults;
        private JButton checkoutButton;
        private int[] orderSelect;
        private int selectArraySize = 0;
        private int numFoods = 0;
        private Boolean activeOrder;
        private ArrayList<FoodAd> foodAdsList = new ArrayList<>();
        private static ArrayList<RestaurantPanel> restPanels = new ArrayList<>();

        public RestaurantPanel(int row) throws SQLException
            {
                rstResults = DBOperations.dbResults("restaurants", row);

                imgAddress = rstResults.getString("imgAddress");
                BufferedImage buffered = new BufferedImage(250, 125, BufferedImage.TYPE_INT_ARGB);
                foodLoad = new ImageIcon (RestaurantAd.class.getResource(imgAddress));
                Graphics2D g2 = buffered.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(foodLoad.getImage(), 0, 0, 250, 125, null);
                g2.dispose();
                food = new ImageIcon(buffered);
                imgLabel = new JLabel(food);

                name = new JLabel(rstResults.getString("name"), SwingConstants.CENTER);
                name.setFont(new Font("Times New Roman", Font.PLAIN, 20));

                desc = new JLabel(rstResults.getString("descript"), SwingConstants.CENTER);
                desc.setAlignmentY(SwingConstants.TOP);
                
                scrollContainer.setLayout(new GridLayout(7, 1));                               
                scrollFood.getVerticalScrollBar().setUnitIncrement(16);
                
                checkoutButton = new JButton("Proceed to checkout (" + numFoods + ")");

                this.setLayout(new GridBagLayout());
                

                //image section
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 0.1;
                gbc.weighty = 0.1;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(imgLabel, gbc);

                //name section
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weightx = 0.1;
                gbc.weighty = 0.1;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(name, gbc);

                //description section
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.weightx = 0.1;
                gbc.weighty = 1.0;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(desc, gbc);

                //food items section
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.weightx = 0.9;
                gbc.weighty = 1.0;
                gbc.gridwidth = 2;
                gbc.gridheight = 3;
                gbc.fill = GridBagConstraints.BOTH;
                this.add(scrollFood, gbc);

                this.setVisible(true);
                foodResults = DBOperations.dbResults("menuItems", row);
                System.out.println("Restaurant " + row + ": foodResults = " + foodResults);

                if (foodResults != null)
                    {
                        do
                            {
                                System.out.println("  creating FoodAd, selectArraySize=" + selectArraySize);
                                FoodAd adtemp = new FoodAd(foodResults, this);
                                foodAdsList.add(adtemp);
                                scrollContainer.add(adtemp);
                                selectArraySize++;
                            }
                        while(foodResults.next());
                    }

                orderSelect = new int[selectArraySize];

                scrollContainer.add(checkoutButton);

                checkoutButton.addActionListener(e -> checkout());

                restPanels.add(this);
            }
        
        public void addToSelect(int menuId)
            {
                int i = 0;

                while (menuId < orderSelect[i])
                    {
                        i++;
                    }

                for (int j = numFoods - 1; j > i; j--)
                    {
                        orderSelect[j] = orderSelect[j - 1];
                    }

                orderSelect[i] = menuId;
                numFoods++;
                checkoutButton.setText("Proceed to checkout (" + numFoods + ")");
            }
        public void deleteFromSelect(int menuId)
            {
                int i = 0;
                
                while (menuId < orderSelect[i])
                    {
                        i++;
                    }

                for (int j = i; j < numFoods - 1; j++)
                    {
                        orderSelect[j] = orderSelect[j + 1];
                    }
                numFoods--;      
                checkoutButton.setText("Proceed to checkout (" + numFoods + ")");
            }
        public void checkout()
            {
                activeOrder = DBOperations.checkOrder();
                if (activeOrder)
                    {
                        new JOptionPane().showMessageDialog(checkoutButton, "There is already an active order! Please wait for it to be delivered before placing another order");
                    }
                else
                    {
                        int choice = new JOptionPane().showConfirmDialog(checkoutButton, "Are you sure?");
                        if(choice == JOptionPane.YES_OPTION && !activeOrder)
                            {
                                for (int i = 0; i < numFoods; i++)
                                    {
                                        DBOperations.InsertOrder(orderSelect[i], 1, 1, false);
                                    }
                                new JOptionPane().showMessageDialog(checkoutButton, "Order placed! Go to My Orders to track");
                                OrderPanel.tPanel.loadActiveOrder();
                                OrderPanel.detailsPanel.refresh();
                                deselectAll();
                            }
                    }
            }
        public void deselectAll()
            {
                for(FoodAd tempAd : foodAdsList)
                    {
                        tempAd.deselect();
                    }
                numFoods = 0;
                checkoutButton.setText("Proceed to checkout (" + numFoods + ")");
            }
        public static void deselectAllAds()
            {
                for(RestaurantPanel tempPanel : restPanels)
                    {
                        tempPanel.deselectAll();
                    }
            }
    }
