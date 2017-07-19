package foreignCheckers;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Point;
import java.io.File;

/**
 * ������
 */
public class Util {
	private static Point[][] points = ChessBoard.points;
	private AudioClip audioClip;
	static boolean win = false;
	static boolean mute =true;
	
	@SuppressWarnings("deprecation")
	public Util(){
		try {
			File file = new File("audio\\beep.au");
			audioClip = Applet.newAudioClip(file.toURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��������
	public void play() {
		if (mute) {
			audioClip.play();
		}
	}
	
	/**
	 * Ѱ�Ҷ��������ĸ������Point
	 */
	public static Point NorthWest(Point point) {
		int x = 0;
		int y = 0;
		for(int i = 1;i < 9;i++){
			for (int j = 1; j < 9; j++) {
				if (points[i][j].equals(point)) {
					x = i;
					y = j;
				}
			}
		}
		if (x-1>0 && y-1>0) {
			return points[x-1][y-1];
		}
		return null;
	}
	
	public static Point NorthEast(Point point){
		int x =0;
		int y = 0;
		for(int i=1;i<9;i++){
			for(int j=1;j<9;j++){
				if (points[i][j].equals(point)) {
					x = i;
					y = j;
				}
			}
		}
		if (x+1<9 && y-1>0) {
			return points[x+1][y-1];
		}
		return null;
	}
	
	public static Point SouthWest(Point point){
		int x = 0;
		int y = 0;
		for(int i =1;i<9;i++){
			for(int j=1;j<9;j++){
				if (points[i][j].equals(point)) {
					x = i;
					y =j;
				}
			}
		}
		if (x-1>0 && y+1<9) {
			return points[x-1][y+1];
		}
		return null;
	}
	
	public static Point SouthEast(Point point){
		int x = 0;
		int y = 0;
		for(int i=1;i<9;i++){
			for(int j=1;j<9;j++){
				if (points[i][j].equals(point)) {
					x = i;
					y = j;
				}
			}
		}
		if (x+1<9 && y+1 <9) {
			return points[x+1][y+1];
		}
		return null;
	}

	
	/**
	 * �Ƿ��п��ԳԵ���
	 */
	public static boolean eat(Chess monster,ChessState chessState){
		Chess[] red = chessState.red;
		Chess[] white = chessState.white;
		return eat(monster, red, white);
	}
	
	public static boolean eat(Chess monster, Chess[] red, Chess[] white){
		int x = 0, y = 0;
		if (monster == null) {
			return false;
		}
		for (int i = 1; i < 9; i++) {
			for(int j = 1; j < 9; j++){
				if (points[i][j].equals(monster.getLocation())) {
					x = i;
					y = j;
				}
			}
		}
		if (monster.getColor().equals("red")) {
			for(int t=0;t<12;t++ ){ //���monster���ĸ����Ƿ��п��ԳԵİ���
				if (x-2>0&&y-2>0&&monster.isKing()&&white[t].isVisible()&&white[t].getLocation()
						.equals(points[x-1][y-1])&&hasChess(red, white, points[x-2][y-2]).equals("none")) {
					return true;
				}
				if(x-2>0&&y+2<9&&white[t].getLocation().equals(points[x-1][y+1])&&white[t].isVisible()&&
						hasChess(red, white, points[x-2][y+2]).equals("none")){
					return true;
				}
				if (x+2<9&&y-2>0&&monster.isKing()&&white[t].isVisible()&&white[t].getLocation()
						.equals(points[x+1][y-1])&&hasChess(red, white, points[x+2][y-2]).equals("none")) {
					return true;
				}
				if (x+2<9&&y+2<9&&white[t].isVisible()&&white[t].getLocation().equals(points[x+1][y+1])&&
						hasChess(red, white, points[x+2][y+2]).equals("none")) {
					return true;
				}
			}
			return false;
		}else {
			for(int t=0;t<12;t++ ){ //���monster���ĸ����Ƿ��п��ԳԵĺ���
				if (x-2>0&&y-2>0&&red[t].isVisible()&&red[t].getLocation().equals(points[x-1][y-1])
						&&hasChess(red, white, points[x-2][y-2]).equals("none")) {
					return true;
				}
				if(x-2>0&&y+2<9&&monster.isKing()&&red[t].getLocation().equals(points[x-1][y+1])&&
						red[t].isVisible()&&hasChess(red, white, points[x-2][y+2]).equals("none")){
					return true;
				}
				if (x+2<9&&y-2>0&&red[t].isVisible()&&red[t].getLocation().equals(points[x+1][y-1])
						&&hasChess(red, white, points[x+2][y-2]).equals("none")) {
					return true;
				}
				if (x+2<9&&y+2<9&&monster.isKing()&&red[t].isVisible()&&red[t].getLocation()
						.equals(points[x+1][y+1])&&hasChess(red, white, points[x+2][y+2]).equals("none")) {
					return true;
				}
			}
			return false;
		}
		
		
	}
	
	/**
	 * ���õ��Ƿ������ӣ�ʲô��ɫ����
	 */
	public static String hasChess(Chess[] red, Chess[] white,Point point){
		for(int i=0;i<12;i++){
			if (red[i].isVisible()&&red[i].getLocation().equals(point)) {
				return "red";
			}
			if (white[i].isVisible()&&white[i].getLocation().equals(point)) {
				return "white";
			}
		}
		return "none";
	}
	
	public static String hasChess(ChessState chessState,Point point){
		Chess[] red = chessState.red;
		Chess[] white = chessState.white;
		return hasChess(red, white,point);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
