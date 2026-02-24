import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JFrame;

public class MainFrame extends JFrame 
    {
        public MainFrame() throws SQLException
            {
                super("Delivery App");
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.add(new StartPanel(this), BorderLayout.CENTER);
                this.add(new MainPanel(), BorderLayout.CENTER);
                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
    }
