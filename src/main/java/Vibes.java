//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 21/01/2026
//Purpose: Sets appearance of window and defines fade to colour change for hover animation
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Vibes {
    public static void setFeel(){
        try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());         
            } 
            catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
    public static Timer fadeTo(JComponent component, Color from, Color to, float speed) 
        {
            // Get current running timer for this component and stop it
            Object existing = component.getClientProperty("fadeTimer");
            if (existing instanceof Timer t && t.isRunning()) t.stop();
            
            float[] progress = {0f};
            Timer fadeTimer = new Timer(10, null);
            fadeTimer.addActionListener(e -> {
                progress[0] += speed;
                if (progress[0] >= 1f) {
                    progress[0] = 1f;
                    fadeTimer.stop();
                }
                int r = (int) (from.getRed()   + (to.getRed()   - from.getRed())   * progress[0]);
                int g = (int) (from.getGreen() + (to.getGreen() - from.getGreen()) * progress[0]);
                int b = (int) (from.getBlue()  + (to.getBlue()  - from.getBlue())  * progress[0]);
                component.setBackground(new Color(r, g, b));
            });
            component.putClientProperty("fadeTimer", fadeTimer);
            fadeTimer.start();
            return fadeTimer;
        }

    // Convenience overload with a default speed
    public static Timer fadeTo(JComponent component, Color from, Color to) 
        {
            return fadeTo(component, from, to, 0.08f);
        }
}
