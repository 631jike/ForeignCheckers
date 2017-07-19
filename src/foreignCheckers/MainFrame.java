package foreignCheckers;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;





/**
 * ������,��Ҫ������ť
 */
public class MainFrame extends JFrame implements ActionListener{ 

	private static final long serialVersionUID = 1L;
	private JMenuBar mainMenu = new JMenuBar();
	private JMenu gameMenu = new JMenu("������Ϸ");
	private JMenu levelMenu = new JMenu("��Ϸ�ȼ�");
	private JMenu soundMenu = new JMenu("��Ϸ��Ч");
	private JMenu changeBgMenu = new JMenu("���̱���");
	private JMenuItem itemStart = new JMenuItem("��Ϸ��ʼ");
	private JMenuItem itemRegular = new JMenuItem("��Ϸ����");
	private JMenuItem itemEasy = new JMenuItem("��");
	private JMenuItem itemNormal = new JMenuItem("һ��");
	private JMenuItem itemHard = new JMenuItem("����");
	private JMenuItem itemSoundOn = new JMenuItem("����");
	private JMenuItem itemSoundOff = new JMenuItem("�ر�");
	private JMenuItem itemChangeBg = new JMenuItem("�������̱���");
	private ImageIcon selectedIcon;
	ChessBoard chessBoard;
	Robot robot;
	
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new MainFrame();  
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public MainFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setBounds(450,170,486,530);
		this.setTitle("��������");
		init();
	}

	private void init(){
		URL iconUrl = this.getClass().getResource("/selected.png");
		selectedIcon = new ImageIcon(iconUrl);
		robot = new Robot();
		chessBoard = new ChessBoard(robot);
		
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		gameMenu.setFont(new Font("Dialog", 0, 13));
		levelMenu.setFont(new Font("Dialog", 0, 13));
		soundMenu.setFont(new Font("Dialog", 0, 13));
		itemStart.setFont(new Font("Dialog", 0, 12));
		itemRegular.setFont(new Font("Dialog", 0, 12));
		itemEasy.setFont(new Font("Dialog", 0, 12));
		itemEasy.setIcon(selectedIcon);
		itemNormal.setFont(new Font("Dialog", 0, 12));
		itemHard.setFont(new Font("Dialog", 0, 12));
		itemSoundOn.setFont(new Font("Dialog", 0, 12));
		itemSoundOn.setIcon(selectedIcon);
		itemSoundOff.setFont(new Font("Dialog", 0, 12));
		itemChangeBg.setFont(new Font("Dialog", 0, 12));
		//��ϲ˵���
		gameMenu.add(itemStart);
		gameMenu.add(itemRegular);
		levelMenu.add(itemEasy);
		levelMenu.add(itemNormal);
		levelMenu.add(itemHard);
		soundMenu.add(itemSoundOn);
		soundMenu.add(itemSoundOff);
		changeBgMenu.add(itemChangeBg);
		mainMenu.add(gameMenu);
		mainMenu.add(levelMenu);
		mainMenu.add(soundMenu);
		mainMenu.add(changeBgMenu);
		
//		mainMenu.setBounds(0, 0, 480, 40);
		this.setJMenuBar(mainMenu);
//		this.add(mainMenu);
//		chessBoard.setLocation(0,35);
//		chessBoard.setBounds(0,40,480,480);
//		container.add(chessBoard);
//		chessBoard.setBounds(0,20,480,500);
//		chessBoard.setVisible(false);
		this.add(chessBoard);
		
		//����¼�����
		itemStart.addActionListener(this);
		itemRegular.addActionListener(this);
		itemEasy.addActionListener(this);
		itemNormal.addActionListener(this);
		itemHard.addActionListener(this);
		itemSoundOn.addActionListener(this);
		itemSoundOff.addActionListener(this);
		itemChangeBg.addActionListener(this);

    	setVisible(true);
    	setResizable(false);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object == itemStart) {
			Util.win = false;
			chessBoard.init();
			chessBoard.setVisible(true);
			chessBoard.repaint();
		}else if (object == itemRegular) {
			String regular = "�������\n1��˫���������ߡ���δ������������ֻ�������Ͻǻ����Ͻ�������ռ�ݵĸ���б��һ��\n2������ʱ���з������ӱ�����"
					+ "�ڼ������ӵ����Ͻǻ����Ͻǵĸ��ӣ����Ҹõз����ӵ�\n    ��Ӧ�����Ͻǻ����ϽǱ���û�����ӡ�\n3�������ӵ��˵��ߣ����Ϳ��ԡ�������"
					+ "����������ƶ���\n4����һ�����ӿ��Գ��壬������ԡ����ӿ������ԡ�����˵����һֻ���ӳԹ��з�����\n    �Ӻ������µ�λ�������"
					+ "�Եз�����һЩ�з����ӣ��������ٳԣ�ֱ���޷��ٳ�Ϊֹ��\n5����һ�����û�����߻��������Ӿ�����ȥ�����䡣\n6������ϷΪ�˻���ս��"
					+ "��Ϸ���Ϊ���ӡ�";
			JOptionPane.showMessageDialog(null,regular,"��Ϸ����",JOptionPane.INFORMATION_MESSAGE); 
		}else if (object == itemEasy) {
			clearLevelIcon(itemEasy);
			robot.setLevel(1);
		}else if (object == itemNormal) {
			clearLevelIcon(itemNormal);
			robot.setLevel(2);
		}else if (object == itemHard) {
			clearLevelIcon(itemHard);
			robot.setLevel(3);
		}else if (object == itemSoundOn) {
			clearSoundIcon(itemSoundOn);
			Util.mute = true;
		}else if (object == itemSoundOff) {
			clearSoundIcon(itemSoundOff);
			Util.mute = false;
		}else if (object == itemChangeBg) {
			chessBoard.changeBg();
		}
		
	}
	
	private void clearLevelIcon(JMenuItem jMenuItem){
		itemEasy.setIcon(null);
		itemNormal.setIcon(null);
		itemHard.setIcon(null);
		jMenuItem.setIcon(selectedIcon);
	}
	
	private void clearSoundIcon(JMenuItem jMenuItem){
		itemSoundOn.setIcon(null);
		itemSoundOff.setIcon(null);
		jMenuItem.setIcon(selectedIcon);
	}
	
	
	
	
	

}
