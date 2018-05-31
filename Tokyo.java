import java.awt.*;
import java.awt.event.*;
public class Tokyo extends Frame{
	MyThread thread =null;
	int x,y,move,x1[],y1[],x2[],y2[],x3[],y3[],width;
	TokyoTower tower;
	Button resumeButton,suspendButton;
	boolean threadSuspended=false;

	Tokyo(){
		setLayout(new FlowLayout());
		setTitle("Animation");
		setSize(1100,1000);
		setBackground(Color.cyan);
		x=500;
		y=-400;
		move=10;
		width=getSize().width;
		tower= new TokyoTower();
		tower.setXY(x,y);
		tower.setXY2();
		tower.setColor(Color.red);
		tower.setMaxWidth(width);
		tower.setSpeed(move);
		setVisible(true);
		addWindowListener(new WinAdapter());
		resumeButton=new Button("Resume");
		suspendButton=new Button("Suspend");
		add(resumeButton);
		add(suspendButton);
		resumeButton.addActionListener(new ActionAdp1());
		suspendButton.addActionListener(new ActionAdp2());
	}
	void start(){
		if (thread==null){
			thread=new MyThread();
			thread.start();
		}
	}
	public void paint(Graphics g){
		tower.paint(g);
	}
	class ActionAdp1 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			threadSuspended=false;
			thread.myResume();
		}
	}
	class ActionAdp2 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			threadSuspended=true;
		}
	}
	class MyThread extends Thread{
		public void run(){
			Thread thisThread=Thread.currentThread();
			while(thread==thisThread){
				while(threadSuspended){
					synchronized(this){
						try{
							wait();
						}catch(InterruptedException e){
						}
					}
				}
				repaint();
				tower.nextPlace();
				try{
					thread.sleep(50);
				}catch(InterruptedException e){
				}
			}
		}
		public synchronized void myResume(){
			notify();
		}
	}
	class WinAdapter extends WindowAdapter{
		public void windowClosing(WindowEvent we){
			System.exit(0);
		}
	}
	public static void main (String args[]){
		Tokyo anim=new Tokyo();
		anim.start();
	}
}
class TokyoTower{
	int x;
	int y;
	int x1[];
	int y1[];
	int x2[];
	int y2[];
	int x3[];
	int y3[];
	int move;
	Color color=Color.black;
	double time =0.0;
	double maxWidth=0.0;


	TokyoTower(){
	}
	public void setXY(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void setXY2(){
		int x1[]={x,x+250,x+150,x+50,x-50,x-150,x-250};
		int y1[]={y-350,y+350,y+350,y+100,y+100,y+350,y+350};
		int x2[]={x-200,x+200,x+200,x-200};
		int y2[]={y,y,y+100,y+100};
		int x3[]={x-100,x+100,x+100,x-100};
		int y3[]={y-200,y-200,y-100,y-100};
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		this.x3=x3;
		this.y3=y3;
	}
	public void setColor(Color color){
		this.color=color;
	}
	public void setMaxWidth(int maxWidth){
		this.maxWidth=maxWidth;
	}
	public void setSpeed(int move){
		this.move=move;
	}
	public void paint(Graphics g){
		g.setColor(color);
		g.fillPolygon(x1,y1,7);
		g.setColor(color.gray);
		g.fillPolygon(x2,y2,4);
		g.fillPolygon(x3,y3,4);
		g.setColor(color);
		if(time>0.1){g.drawLine(10,825,90,825);}
		if(time>0.2){g.drawLine(50,825,50,975);}
		if(time>0.3){g.drawOval(110,825,80,150);}
		if(time>0.4){g.drawLine(210,825,210,975);}
		if(time>0.5){g.drawLine(290,825,210,900);}
		if(time>0.6){g.drawLine(210,900,290,975);}
		if(time>0.7){g.drawLine(310,825,350,870);}
		if(time>0.8){g.drawLine(390,825,350,870);}
		if(time>0.9){g.drawLine(350,870,350,975);}
		if(time>1.0){g.drawOval(410,825,80,150);}
		if(time>1.1){g.drawLine(560,825,640,825);}
		if(time>1.2){g.drawLine(600,825,600,970);}
		if(time>1.3){g.drawOval(660,825,80,150);}
	    	if(time>1.4){g.drawLine(760,825,780,970);}
		if(time>1.5){g.drawLine(780,970,800,900);}
		if(time>1.6){g.drawLine(800,900,820,970);}
		if(time>1.7){g.drawLine(820,970,840,825);}
		if(time>1.8){g.drawLine(860,825,940,825);}
		if(time>1.9){g.drawLine(860,825,860,975);}
		if(time>2.0){g.drawLine(860,900,940,900);}
		if(time>2.1){g.drawLine(860,975,940,975);}
		if(time>2.2){g.drawLine(960,825,960,975);}
		if(time>2.3){g.drawLine(960,825,1040,825);}
		if(time>2.4){g.drawLine(1040,825,1040,900);}
		if(time>2.5){g.drawLine(1040,900,960,900);}
		if(time>2.6){g.drawLine(960,900,1040,975);}
	}
	public void nextPlace(){
		if(y<=400){
			setXY(x,y+move);
			time+=0.05;
		}else{
			if(x<maxWidth+500){
				setXY(x+move,y);
				time+=0.05;
			}else{
				setXY(500,-400);
				time =0;
			}
		}
		setXY2();
	}
}
