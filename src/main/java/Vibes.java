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
}
