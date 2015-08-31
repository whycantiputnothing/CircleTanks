
import javax.swing.*;
import java.awt.*;
public class GameStart{
    public static void main(String[] args){
        JFrame theGUI= new JFrame();
        theGUI.setTitle("Tanacious Circle Tanks");
         theGUI.setExtendedState(Frame.MAXIMIZED_BOTH);
         theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         Bounce panel = new Bounce(Color.blue);
         // http://forums.devx.com/showthread.php?141527-adding-KeyListener-to-JPanel-!!!!
         panel.setFocusable(true);
         panel.setBackground(Color.black);
         Container pane = theGUI.getContentPane();
         pane.add(panel);
         theGUI.setVisible(true);
         
        }
    }
