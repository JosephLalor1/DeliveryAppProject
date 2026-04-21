//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 
//Purpose:
import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class MainFrame extends JFrame 
    {
        private MainPanel mainPanel;
        private StartPanel startPanel;

        public MainFrame() 
            {
                super("Delivery App");
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                startPanel = new StartPanel();
                this.add(startPanel, BorderLayout.CENTER);
                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setExtendedState(JFrame.MAXIMIZED_BOTH);

                new SwingWorker<Void, Void>() 
                    {
                        @Override
                        protected Void doInBackground() throws Exception 
                            {
                                DBOperations.Connect();
                                return null;
                            }

                        @Override
                        protected void done() {
                            try {
                                get(); // this re-throws any exception from doInBackground()
                                mainPanel = new MainPanel();
                                openMainPanel();
                            } catch (Exception e) {
                                e.printStackTrace(); // now you'll actually see what's going wrong
                            }
                        }
                    }.execute();
            }

        public void openMainPanel() throws SQLException 
            {
                this.remove(startPanel);
                this.add(mainPanel, BorderLayout.CENTER);
                mainPanel.openHomePanel();
                revalidate();
                repaint();
            }
    }
