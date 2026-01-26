import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.sql.SQLException;


public class MainFrame extends JFrame 
    {

        private MainPanel mp = new MainPanel();

        public MainFrame() throws SQLException
            {
                super("Delivery App");
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.add(mp, BorderLayout.CENTER);
                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
    }
