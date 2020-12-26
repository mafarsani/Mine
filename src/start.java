import java.awt.Image;
import javax.swing.*;
import java.io.FileNotFoundException;
public class start {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
       GameBord B=new GameBord(20,20);
       B.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //B.setLocationRelativeTo(null);
       B.setTitle("Dorsa, Lets do it");
       B.setSize(625,700);
       B.setResizable(false);
       Image icon = new ImageIcon("Dor.jpg").getImage(); 
       B.setIconImage(icon);
       B.setVisible(true);
	}

}
