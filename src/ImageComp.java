import javax.swing.*;

import java.awt.Graphics;
import java.awt.Image;

public class ImageComp extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private int x,y,w,h;
	public int  color,value;
	
	public ImageComp(int x, int y,int w,int h,String fileName) {
		image=new ImageIcon(fileName).getImage();
		this.x=0;
		this.y=0;
		this.w=w;
		this.h=h;
	    this.setLayout(null);
	    this.setBounds(0,0, w, h);
		this.setLocation(x, y);
		System.out.println("=====");
	}
	void refresh(String fileName) {
		image=new ImageIcon(fileName).getImage();	
	}
	protected void paintComponent(Graphics G) {
			super.paintComponent(G);
			if(image!=null)
				G.drawImage(image,x,y,w,h,this);
			else System.out.println("null");
			System.out.println("=====");
		}
}
