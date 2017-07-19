package foreignCheckers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Robot���ô�alpha beta��֦�ļ���Сֵ�㷨������״̬������
 * ����һ���������̣߳�һֱ�ڼ��������Ƿ��Ѿ��߹��壬����ǣ��Ϳ�ʼ��������������
 */
public class Robot extends Thread{
	private static int DEPTH = 3; //�������.Ĭ���Ǽ򵥵�ˮƽ
	private static final int MAX = 99999;
	private static final int MIN = -99999;
	private ChessState state;
	private static ArrayList<ChessState> NextStates; //��ǰ״̬���п��ܵ��߷�
	private JPanel chessBoard;
	private Util util = new Util();
	public static boolean eaten = false;
	
	public Robot(){
		start(); //����robot�߳�
	}

	public void setState(ChessState state){
		this.state = state;
	}
	
	public void run(){
		while(true){
			synchronized(this){
				try{
					//��ʱ���������壬�ȴ�
					wait();
				}
				catch(InterruptedException ie){
					throw new RuntimeException(ie);
				}	
			}
			try{
				//�Ե�һ�ᣬ��������̫��
				Thread.sleep(100);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//�ȴ���������robot���ˣ���ʼ����
			alphaBetaSearch(state);
		}
	}


	private void alphaBetaSearch(ChessState state){
		
		state.depth = 0;
		NextStates = null;

		//��Ϊ�˴��ǵ������壬����Ҫȡ����״̬�����ֵ,maxValue()��ѵ�һ���״̬���浽NextStates��
		int value = maxValue(state,MIN,MAX);

		//��������������,�����Ӯ��
		if(NextStates==null){ 
			JOptionPane.showMessageDialog(null, "��Ӯ�ˣ�����");	
			Util.win = true;
			return ;
		}

		//��NextStates����Ѱ����ѵ��߷�,ͨ���Ƚ�Ȩֵ
		ChessState s = null;
		ChessState nextState = null;
		for(Iterator<ChessState> it = NextStates.iterator();it.hasNext();){
			s = (ChessState)it.next();
			if(s.value==value){
				nextState = s;
				break;
			}
		}

		//����û�����ˣ�������жϻ����б�Ҫ�ģ���ȻNextStates.iterator()һ������쳣
		if(nextState==null){
			JOptionPane.showMessageDialog(null, "��Ӯ�ˣ�����");
			Util.win = true;
			return ;
		}
	
		ArrayList<ChessState> steps = findPath(new ChessState(ChessState.preRed,ChessState.preWhite,false),nextState);
		System.out.println("findPath  after");
		ChessState st = null;
		for(Iterator<ChessState> it = steps.iterator();it.hasNext();){
			System.out.println("Iterator  repaint()");
			st = (ChessState)it.next();
			ChessBoard.red = st.red;
			ChessBoard.white = st.white;
			chessBoard.repaint();
			util.play();
			checkOver(st); //����Ƿ������
			try{
				Thread.sleep(200); //����֮����һ��
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		ChessBoard.turn = true;
		ChessBoard.chessState = st;
		System.out.println("ChessBoard.turn   "+ChessBoard.turn);
	}
	
	
	private int maxValue(ChessState state,int a,int b){
		//���������ײ㣬�������ۺ����õ���Ȩֵ
		if(state.depth==DEPTH) 
			return state.getValue();
		//�Ȱ�state��value��Ϊ��С����Ϊ����Ҫ��һ�����ֵ	
		state.value = MIN;
		//ȡ���ֵʱ��Ӧ���ӵ�״̬
		ArrayList<ChessState> list = state.nextStatesOfRed();

		//�����һ���״̬�������ʱ����
		if(state.depth==0)
			NextStates = list;

		ChessState s = null;
		int minValue = MIN;
		for(Iterator<ChessState> it = list.iterator();it.hasNext();){
			s = (ChessState)it.next();
			s.depth = state.depth + 1; //��ȼ�һ

			int temp = minValue(s,a,b);//��һ��ȡ��Сֵ
			if(minValue < temp)
				minValue = temp;

			//minValue����һ��״̬����С�ģ�����state��value��Ƚϣ�ȡ���
			state.value = state.value>minValue ? state.value : minValue;

			//state��value��betaֵ�󣬴�ʱû��Ҫ������ȥ�����Լ�֦��
			if(state.value>=b)
				return state.value;

			//���state��value��alphaֵ�󣬸���alphaֵ
			a = a>state.value ? a : state.value;
		}
		return state.value;
	}


	private int minValue(ChessState state,int a,int b){
		if(state.depth==DEPTH)
			return state.getValue();
		state.value = MAX;
		ArrayList<ChessState> list = state.nextStatesOfWhite();
		ChessState s = null;
		int maxValue = MAX;
		for(Iterator<ChessState> it = list.iterator();it.hasNext();){
			s = (ChessState)it.next();
			s.depth = state.depth + 1;

			int temp = maxValue(s,a,b);
			if(maxValue > temp)
				maxValue = temp;
			state.value = state.value<maxValue ? state.value : maxValue;
			if(state.value<=a)
				return state.value;
			b = b<state.value ? b : state.value;
		}
		return state.value;
	}
	
	/**
	 * Ѱ�ҵ�ǰ״̬����һ״̬֮���·������Ϊ�����������ӵ������
	 * �÷������ص���һ�����飬���������е��м�״̬������״̬
	 */
	private ArrayList<ChessState> findPath(ChessState from, ChessState to){
		System.out.println("findPath");
		ArrayList<ChessState> list = new ArrayList<>();
		Chess oriRed[] = from.red;
		Chess oriwhite[] = from.white;
		Chess currentwhite[] = to.white;
		Chess currentRed[] = to.red;
		Chess movedRed = null;

		boolean eaten = false;
		for(int i=0;i<12;i++){
			if(!oriRed[i].getLocation().equals(currentRed[i].getLocation()))
				movedRed = oriRed[i];
			if(oriwhite[i].isVisible()&& !currentwhite[i].isVisible())
				eaten = true;
		}
		Point start = movedRed.getLocation();

		if(eaten)
			while(!from.equals(to)){
				if(Util.NorthWest(start)!=null && Util.NorthWest( Util.NorthWest(start))!=null && Util.hasChess(from, 
						Util.NorthWest(start)).equals("white") && Util.hasChess(to,Util.NorthWest(start)).equals("none") )
				{
					System.out.println("NorthWest");
					from.getWhite( Util.NorthWest(start)).setVisible(false);
					from.getRed(start).setLocation(Util.NorthWest( Util.NorthWest(start)));
					list.add(from.copy());
					start = Util.NorthWest( Util.NorthWest(start));
				}
				else if(Util.NorthEast(start)!=null && Util.NorthEast( Util.NorthEast(start))!=null && Util.hasChess(from, 
						Util.NorthEast(start)).equals("white") && Util.hasChess(to,Util.NorthEast(start)).equals("none") )
				{
					System.out.println("NorthEast");
					from.getWhite( Util.NorthEast(start)).setVisible(false);
					from.getRed(start).setLocation(Util.NorthEast( Util.NorthEast(start)));
					list.add(from.copy());
					start = Util.NorthEast( Util.NorthEast(start));
				}
				else if(Util.SouthWest(start)!=null && Util.SouthWest( Util.SouthWest(start))!=null && Util.hasChess(from, 
						Util.SouthWest(start)).equals("white") && Util.hasChess(to,Util.SouthWest(start)).equals("none") )
				{
					System.out.println("SouthWest");
					from.getWhite(Util.SouthWest(start)).setVisible(false);
					from.getRed(start).setLocation(Util.SouthWest( Util.SouthWest(start)));
					list.add(from.copy());
					start = Util.SouthWest( Util.SouthWest(start));
					System.out.println("SouthWest  add");
				}
				else if(Util.SouthEast(start)!=null && Util.SouthEast( Util.SouthEast(start))!=null && Util.hasChess(from, 
						Util.SouthEast(start)).equals("white") && Util.hasChess(to,Util.SouthEast(start)).equals("none") )
				{
					System.out.println("SouthEast");
					from.getWhite( Util.SouthEast(start)).setVisible(false);
					from.getRed(start).setLocation(Util.SouthEast( Util.SouthEast(start)));
					list.add(from.copy());
					start = Util.SouthEast( Util.SouthEast(start));
				}
			}

		else
			list.add(to);
		return list;
	}
	

	//���������Ϊrobot����֮��ˢ�������õ�
	public void setBorad(JPanel jp){
		chessBoard = jp;
	}
	
	//�����Ѷ�
	public void setLevel(int index){
		if(index==1)
			DEPTH = 3;
		else if(index==2)
			DEPTH = 6;
		else if(index==3)
			DEPTH = 9;
	}

	//����Ƿ�һ��û����
	private void checkOver(ChessState state){
		Chess[] red = state.red;
		Chess[] white = state.white;
		for(int i=0;i<12;i++){
			if(red[i].isVisible())
				break;
			if(i==11){
				JOptionPane.showMessageDialog(null, "��Ӯ�ˣ�����");
				return ;
			}
		}
		for(int i=0;i<12;i++){
			if(white[i].isVisible())
				break;
			if(i==11){
				JOptionPane.showMessageDialog(null, "�����ˣ�����");
				return ;
			}
		}
	}
}
	
	
	
	
