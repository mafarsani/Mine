
public class CreateBord {
private int m,n,numberOfMines;
private int[][]bord;

public CreateBord(int x,int y) {
	m=x;
	n=y;
	bord=new int[m][n];
	for(int i=0;i<m;i++)
		for(int j=0;j<n;j++)
			bord[i][j]=0;
	numberOfMines=m*n/5;
	
	int j,k;
	for(int i=0;i<numberOfMines;i++) {
		j=(int)(Math.random()*m);
		k=(int)(Math.random()*n);
		
		while(bord[j][k]==-1) {
			j=(int)(Math.random()*m);
			k=(int)(Math.random()*n);
	   }
		bord[j][k]=-1;
		for(int a=j-1;a<j+2;a++)
			for(int b=k-1;b<k+2;b++) {
				if(a>=m || a<0) continue;
				if(b>=n || b<0) continue;
				if(bord[a][b]==-1) continue;
				bord[a][b]++;
			}
		}
       // File file = new File("gmae.txt");
        
	}
    public int  getBord(int i,int j){
    	return bord[i][j];
     }
    public int getNumberOfMones() {
    	return numberOfMines;
    }
    public void setBord(int i,int j,int value){
        bord[i][j]=value;
    }
}
