import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class GameBord extends JFrame implements Constants{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private class GameButton extends JButton {
		/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	public int value,state;
	public GameButton(String s) {
			state=FREE;
			super.setText(s);
		}
	  }
    private JMenuBar jmb = new JMenuBar();
    private JMenuItem Open,Save,New;
	private int N_Mines,N_Remain,N_Predict;
	private Color[] color=new Color[5];
    private Color btColor=new Color(150,255,255);
	
    private CreateBord bord;
	private JPanel panel;
	private JLabel LMines,LRemain,LPredict;
	private JLabel[] lb;
	private GameButton[] bt;
    JPanel p1=new JPanel();
    private Font f;
    private File file;
    private FileWriter fr ;
    private BufferedWriter br;
    private PrintWriter pr;
	private int m,n;
	public GameBord (int m,int n){
        p1.setLayout(null);
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(New=new JMenuItem("New"));
        fileMenu.add(Open=new JMenuItem("Open"));
        fileMenu.add(Save=new JMenuItem("Save"));
        Save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
                saveBord();
            }
            
        });
        New.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                runGame(true);
             }
            
        }); 
         Open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                openBord();
             }
            
        });               
       
        jmb.add(fileMenu);
        this.setJMenuBar(jmb);
        color[0]=new Color(0, 0, 255);//1
        color[1]=new Color(0, 125, 0);//2
        color[2]=new Color(255,0,0);//3
        color[3]=new Color(125,0,0);//4
        color[4]=new Color(50,0,0);//5
        
        this.m=m;
        this.n=n;
        runGame(true);
	}
        private void deleteBord(){
                    f=null;
                    panel.setVisible(false);
                    for(int i=0;i<m*n;i++){
                        bt[i].setVisible(false);
                        bt[i]=null;
                        lb[i].setVisible(false);
                        lb[i]=null;
                    }
                    bt=null;
                    lb=null;
                    panel=null;
                    LMines.setVisible(false);
                    LRemain.setVisible(false);
                    LPredict.setVisible(false);
                    LMines=LRemain=LPredict=null;
                    

        }
        private void runGame(boolean mod)  {
               
                if(panel!=null){
                    deleteBord();                    
                }
               
                f=new Font("Arial",Font.BOLD,16);
                N_Remain=m*n;
                N_Predict=0;
                file = new File("gmae.txt");
                try{
                    pr=new PrintWriter(file);
                }catch (IOException e) {
                	e.printStackTrace();
                }
                finally{
                    if(pr!=null)
                        pr.close();
                }
               
                if (mod) bord=new CreateBord(m,n);
                	panel=new JPanel();
                panel.setVisible(true);
                panel.setEnabled(true);
                
 		N_Mines=bord.getNumberOfMones();
		LMines=new JLabel();
		LMines.setText("Mines: "+Integer.toString(N_Mines));
		LMines.setForeground(Color.RED);
		LMines.setBounds(10, 600, 150, 30);

		LRemain=new JLabel();
		LRemain.setText("Remain: "+Integer.toString(N_Remain));
		LRemain.setForeground(Color.GREEN);
		LRemain.setBounds(300, 600, 150, 30);

		LPredict=new JLabel();
		LPredict.setText("Predicted: "+Integer.toString(N_Predict));
		LPredict.setForeground(Color.BLUE);
		LPredict.setBounds(500, 600, 150, 30);

		bt=new GameButton[m*n];
		lb=new JLabel[m*n];
		panel.setLayout(null);
		int i,j,k;
		for(k=0;k<m*n;k++) {
			lb[k]=new JLabel();
			i=(k/n);
			j=(k%n);
			bt[k]=new GameButton("");
			bt[k].value=bord.getBord(i, j);
                        lb[k].setFont(f);
			if(bt[k].value!=0){
                            switch(bt[k].value){
                                case 1:lb[k].setForeground(color[0]);break;
                                case 2:lb[k].setForeground(color[1]);break;
                                case 3:lb[k].setForeground(color[2]);break;
                                case 4:lb[k].setForeground(color[3]);break;
                                default:lb[k].setForeground(color[4]);
                            }
                            lb[k].setText("  "+Integer.toString(bt[k].value));
                        }
			bt[k].setBackground(btColor);
		
                        
                }
		for(k=0;k<m*n;k++) {
			i=(k/n)*30;
			j=(k%n)*30;
			bt[k].setBounds(j, i, 30, 30);
			lb[k].setBounds(j, i, 30, 30);
			lb[k].setBorder(BorderFactory.createLineBorder(Color.black));
			lb[k].setVisible(false);
			bt[k].addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
					int j;
			    	for(j=0;j<m*n;j++)
						if(e.getSource()==bt[j])
							break;
					switch(e.getButton()) {
					      case 1: 
					    	  if(bt[j].state==FREE) {
						    	     if(bt[j].value==-1) {
						    	    	 //lb[j].setBackground(Color.RED);
						    	    	 f=new Font("Arial",Font.BOLD,20);
                                         lb[j].setForeground(new Color(150,0,150));
                                         lb[j].setText("  *");
						    	    	 lb[j].setFont(f);
						    	    	 for(int ll=0;ll<n*m;ll++) {
                                             bt[ll].state=CLEAR;
						    	    		 bt[ll].setVisible(false);
						    	    		 lb[ll].setVisible(true);
						    	    	 }
                                         writeToFile(j,1,"clear");   
                                         writeToFile(j,1,"Boom");
						    	    	
                                         break;
						    	     }
						    	     if(bt[j].value==0){
                                       clearScreen(j);
                                     }
						    	     else {
						    	    	 N_Remain--;
						    	    	 LRemain.setText("Remains: "+Integer.toString(N_Remain));
						    	    	 bt[j].setVisible(false);
						    	    	 lb[j].setVisible(true);
                                         bt[j].state=CLEAR;
                                         writeToFile(j,1,"clear");
									    if(N_Remain==N_Mines) {
	                                       writeToFile(j,1,"Congratulate"); 
									       Congratulate();
									     }
						    	    	 
						    	     }
					           }
					    	   break;
					      case 3: 
					    	    if(bt[j].state==FREE) {
					    	    	bt[j].setBackground(Color.red);
					    	    	bt[j].state=PREDICTED;
					    	    	bt[j].setEnabled(false);
					    	    	N_Predict++;
					    	    	LPredict.setText("Predicted: "+Integer.toString(N_Predict));
                                    writeToFile(j,3,"addPredict");
					    	    }
					    	    else if(bt[j].state==1) {
					    	    	bt[j].setBackground(btColor);
					    	    	bt[j].state=FREE;
					    	    	bt[j].setEnabled(true);
					    	    	N_Predict--;
					    	    	LPredict.setText("Predicted: "+Integer.toString(N_Predict));
					    	    	writeToFile(j,3,"removePredict");
					    	    	
					    	    }
					    	    break;
					      case 2: 
					    	  if(bt[j].state==FREE) {
					    	        bt[j].setBackground(Color.GRAY);
					    	        bt[j].state=DOUPTED;
					    	        bt[j].setEnabled(false);
                                    writeToFile(j,2,"addDoubt");
					          }
					    	    else if(bt[j].state==DOUPTED) {
					    	    	bt[j].setBackground(btColor);
					    	    	bt[j].state=FREE;
					    	    	bt[j].setEnabled(true);
					    	    	writeToFile(j,2,"removeDoubt");
					    	    }
					    	    break;
					    	  
					}
				}
			});
			panel.add(lb[k]);
			panel.add(bt[k]);
			panel.add(LMines);
			panel.add(LRemain);
			panel.add(LPredict);
		}
		add(panel);
                i=(int)(m*n*Math.random());
               while(bt[i].value!=0)
                   i=(int)(m*n*Math.random());
               bt[i].setBackground(Color.YELLOW);
        }
	private int clearScreen(int k) {
		//String string = JOptionPane.showInputDialog(null, Integer.toString(k),
				//Integer.toString(k), JOptionPane.QUESTION_MESSAGE);
		int i,j,g,h,l;
		if(bt[k].state!=0) return -1;
		if(!bt[k].isVisible()) return -1;
		if(!panel.isEnabled())return -1;
		lb[k].setVisible(true);
		bt[k].setVisible(false);
                bt[k].state=CLEAR;
                writeToFile(k,1,"clear");
		N_Remain--;
		LRemain.setText(Integer.toString(N_Remain));
		i=(k/n);
		j=(k%n);
                
		if(N_Remain==N_Mines) {
            writeToFile(k,1,"Congratulate"); 
			Congratulate();
		}
		
		
		for(g=-1;g<2;g++)
			for(h=-1;h<2;h++)
				if(i+g>-1 && i+g<this.m && j+h >-1  && j+h<this.n && (g!=0 || h!=0)) {
					
                    l=(i+g)*this.n+j+h;
					if(bt[l].value==0) {
                      clearScreen(l);
					}
					if(bt[l].isVisible()) {
						lb[l].setVisible(true);
						bt[l].setVisible(false);
                        bt[l].state=CLEAR; 
						N_Remain--;
						LRemain.setText(Integer.toString(N_Remain));
                        writeToFile(l,1,"clear");
						if(N_Remain==N_Mines) {
                            writeToFile(k,1,"Congratulate"); 
							Congratulate();
						}
					}
			}
		return 0;
		
	}
	private void Congratulate() {
		panel.add(new ImageComp(100,100,300,300,"win.jpg"));
		/*panel.setVisible(false);
        panel.setEnabled(false);
		
		p1.add(new ImageComp(0,0,getWidth(),getHeight(),"win.jpg"));
		p1.addMouseListener(new MouseAdapter() {
			public void  mouseClicked(MouseEvent e) {
				if (e.getButton()==1) {
					p1.setVisible(false);
					panel.setVisible(true);
				}
			}
		});*/
                
	}
        private void writeToFile(int l, int mousButton, String message){
                try {
                    fr = new FileWriter(file, true);
                    br = new BufferedWriter(fr);
                    pr = new PrintWriter(br);
                    int i=(l/n);
                    int j=(l%n);
                    pr.println(l+" "+i+" "+j+" "+mousButton+"  "+message);
                }
                catch (IOException e) {
			e.printStackTrace();
		}
                finally {
                    try{
                        if(pr!=null)
                            pr.close();
                        if(br!=null)
                            br.close();
                        if(fr!=null)
                            fr.close();
                        }
                    catch (IOException e) {
			e.printStackTrace();
                    }

                 }
            
        }
     private int saveBord(){
        FileDialog d = new FileDialog(new JFrame(), "save", FileDialog.SAVE);
        d.setVisible(true);
        File saveFile = new File(d.getDirectory() + d.getFile()); 
        if(d.getDirectory()==null) return 0;
        FileOutputStream fos=null;
        BufferedOutputStream bos=null;
        DataOutputStream dos=null;
       
        
        
        int[][] bordCondition=new int[m][2*n];
        for(int i=0;i<m;i++)
            for(int j=0;j<n;j++)
                bordCondition[i][j]=bord.getBord(i, j);
        int k,l;
        for(int i=0;i<m*n;i++){
 		k=(i/n);
		l=(i%n)+n;
                bordCondition[k][l]=bt[i].state;
        }
        try{
             fos=new FileOutputStream(saveFile);
             bos=new BufferedOutputStream(fos);
             dos=new DataOutputStream(bos);
            System.out.println("save File ..");
            for(int i=0;i<m;i++){
                for(int j=0;j<2*n;j++){
                    dos.writeInt(bordCondition[i][j]);
                   //System.out.print(bordCondition[i][j]+" ");
                }
                 //System.out.println();
            }
           
        
            System.out.println("Finished");
        }catch(IOException e) {
            e.printStackTrace();
        }
        finally{
            try{
                if(dos!=null)
                   dos.close();
                if(bos!=null)
                   bos.close();
                 if(fos!=null)
                   fos.close();

            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
     }
      private int openBord(){
        FileDialog d = new FileDialog(new JFrame(), "Save", FileDialog.LOAD);
        d.setVisible(true);
        if(d.getDirectory()==null) return 0;
        File saveFile = new File(d.getDirectory() + d.getFile()); 
        FileInputStream fos=null;
        BufferedInputStream bos=null;
        DataInputStream dos=null;
       
        
        
        int[][] bordCondition=new int[m][2*n];
        try{
            fos=new FileInputStream(saveFile);
            bos=new BufferedInputStream(fos);
            dos=new DataInputStream(bos);
            
            for(int i=0;i<m;i++){
                for(int j=0;j<2*n;j++){
                   bordCondition[i][j]= dos.readInt();
                }
             }
            for(int i=0;i<m;i++)
                for(int j=0;j<n;j++){
                    bord.setBord(i, j, bordCondition[i][j]);
                }
            
        }catch(IOException e) {
            e.printStackTrace();
        }
        finally{
            try{
                if(dos!=null)
                   dos.close();
                if(bos!=null)
                   bos.close();
                 if(fos!=null)
                   fos.close();

            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        int l,k;
         deleteBord();
         runGame(false);
         for(int i=0;i<m;i++)
            for(int j=n;j<2*n;j++){
                l=j-n; 
                k=i*n+l;
                switch(bordCondition[i][j]){
                    case CLEAR:
                            bt[k].setVisible(false);
                            lb[k].setVisible(true);
                            bt[k].state=CLEAR;
                            N_Remain--;
                    break;
                    case PREDICTED:
                        bt[k].setBackground(Color.RED);
                        bt[k].state=PREDICTED;
                        N_Predict++;
                    break;
                     case DOUPTED:
                        bt[k].setBackground(Color.GRAY);
                        bt[k].state=DOUPTED;
                        
                     break;
                 }
                LPredict.setText("Predicted: "+Integer.toString(N_Predict));
                LRemain.setText("Remain: "+Integer.toString(N_Remain));
            }
                    
       
        return 1;
     }    
 }
