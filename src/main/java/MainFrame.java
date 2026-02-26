import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class MainFrame extends JFrame 
    {
        private MainPanel mainPanel;
        public MainFrame() throws SQLException
            {
                super("Delivery App");
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.add(new StartPanel(this), BorderLayout.CENTER);
                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setExtendedState(JFrame.MAXIMIZED_BOTH);
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        DBOperations.Connect();
                        mainPanel = new MainPanel();
                        return null;
                    }
                }.execute();
            }
        public void openMainPanel() throws SQLException
            {
                this.add(mainPanel, BorderLayout.CENTER);
                mainPanel.openHomePanel();
                revalidate();
                repaint();
            }
    }
