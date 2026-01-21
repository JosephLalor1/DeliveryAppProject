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


public class MainFrame extends JFrame
    {

        private MainPanel mp = new MainPanel();

        public MainFrame()
            {
                super("Delivery App");
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.add(mp, BorderLayout.CENTER);
                this.setLocationRelativeTo(null);
                this.pack();
                this.setVisible(true);
                this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
    }
