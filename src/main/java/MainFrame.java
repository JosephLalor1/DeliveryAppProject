import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.sql.SQLException;

public class MainFrame extends JFrame 
    {
        public MainFrame() throws SQLException
            {
                super("Delivery App");
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.add(new MainPanel(), BorderLayout.CENTER);
                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
    }
