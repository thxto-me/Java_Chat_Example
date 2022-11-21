
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//�α��ΰ� ģ�����, ģ�� ã�� ���, �α׾ƿ� ����.
public class Tab extends JFrame  {
	private JTabbedPane tab;
	private JPanel p1, p2, p3;
	private JButton bHome, bTab1, bTab2;
	private JLabel label1, label2;
	private TextField textField1, textField2;
	private JPanel friendList;

	public Tab() {
		tab = new JTabbedPane();
		p1 = new JPanel(); 
		p1.setBackground(new Color(235,235,190));
		label1 = new JLabel("���̵� �Է�  :  ");
		textField1 = new TextField(15);
		label2 = new JLabel("��й�ȣ �Է�  :  ");
		textField2 = new TextField(15);
		textField2.setEchoChar('*');
		bHome = new JButton("�α���");
		bHome.setBackground(new Color(230,110,100));
		bHome.setForeground(new Color(255,255,255)); 
		bTab1 = new JButton("ȸ������");
		bTab1.setBackground(new Color(230,110,100));
		bTab1.setForeground(new Color(255,255,255));

		for (int i = 0; i < 2; i++) {   // �����Ǿ��� ��ư�� �׼Ǹ����ʿ� �гο� �߰�
			
			p1.add(label1);      // �α��� �г� �ȿ� �ֱ�
			p1.add(textField1);
			p1.add(label2);
			p1.add(textField2);
			p1.add(bHome);
			p1.add(bTab1);
			
			  
	
			}
		bHome.addActionListener(new LoginCheck()); // �α��� ��ư�� ��������
		bTab1.addActionListener(new ActionListener(){         // ȸ������ ��ư�� �������� 
			public void actionPerformed(ActionEvent e) {
				OpenBrowser.openURL("http://www.naver.com");  // �ӽ÷� ���̹��� ���
			}
		});
		tab.add("�α���", p1); // �ǿ� ����� �г� �߰�
		friendList=FriendListMain.getFriendListMain();
		tab.addTab("ģ�����", null, friendList);  // ģ����� �ҷ�����
		tab.addTab("ģ��ã��", null, FindPanel.getFindFriend()); // ģ���߰� �ҷ�����
		tab.setEnabledAt(1, false); // ģ����� disable
		tab.setEnabledAt(2, false); // ģ��ã�� disable
		tab.addChangeListener(new TabAction());
		textField2.addKeyListener(new LogindAction());
		//tab.addChangeListener(l);
		getContentPane().add(tab); // �����ӿ� �� �߰�
		pack();
		
		setSize(320,500);
		setVisible(true);
		setResizable(false); // false �϶� ũ�� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textField1.requestFocus();
		FrameLocation.setLocation(this);
	
		
		this.addWindowListener(new FrameListener());
	}
	public class LogindAction implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==10){ //���͸� ���� �� ������ ��ư�� ���� Ŭ��
			
				bHome.doClick();
			
			}
		
		}

		public void keyReleased(KeyEvent arg0) {}
		public void keyTyped(KeyEvent arg0) {}
		
	}
	public class TabAction implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			System.out.println(tab.getSelectedIndex());
			String[] d=getFriendList.getSearch(New_Client.getClientName());
			FriendListMain.FriendList.setListData(d);
		}
		
	}
	public class LoginCheck implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			Login log=new Login();
			String tempClientId, tempClientPassword;
			tempClientId=textField1.getText(); //Id�� Passwor�� �޾ƿ�.
			tempClientPassword=textField2.getText();
			int logCheck=0;
			logCheck=log.client_login(tempClientId, tempClientPassword);
			if(logCheck==2){ //���� �α����� �������̶�� ������ ����.
			new LoginPanel("�α��� ����",2);
			New_Client.runClient(); //������ ���� ����
			New_Client.setClientName(tempClientId); //ȸ���� ID�� ������ ���� �ѱ�
			Object evt = e.getSource();
			for (int i = 0; i < 2; i++)   
				if (evt == bHome) { // ��ư ������(ģ�����) ���� �̵�
					tab.setSelectedIndex(i);

				}
			tab.setEnabledAt(0, false); // ��ư ������ �� ù��° �α��� ȭ�� disable
			tab.setEnabledAt(1, true); // ģ����� enable
			tab.setEnabledAt(2, true); // ģ�� ã�� enable
			
			}else if(logCheck==1) //��й�ȣ�� �߸� �Է����� ���
			{
				new LoginPanel("��й�ȣ ����",1);
				System.out.println("��й�ȣ �߸��Է�");
				textField1.setText("");
				textField2.setText("");
			}else
			{
				new LoginPanel("�α��� �ߺ� ����",0);
				System.out.println("�̹� �α��εǾ� �ֽ��ϴ�.");
				textField1.setText("");
				textField2.setText("");
			}
			
		}

		
	}//Listener end
	public class FrameListener implements WindowListener{ //������ â�� �̺�Ʈ ó��

		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowClosing(WindowEvent arg0) { //���� ������ �������� Xǥ�� ������ �� �߻�  //�α׾ƿ� �κа� �����ϰ� �����ؾ���
			Map d=New_Client.getMap(); //���� ȸ���� ���ִ� �濡���� ������ ����
			Collection<client_2> client_2_values=d.values(); //�濡 ���� ó���� �ϱ����� ���� ����
			for(Iterator<client_2> it=client_2_values.iterator();it.hasNext();)
			{
				it.next().exit_bt.doClick(); //�ش� ���� ������ ��ư��������.
			}
			}//������ �󿡼� xǥ�� ������ �� ������ ��ư�� Ŭ���ϰ� �Ѵ�.
		public void windowDeactivated(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}

	
		
	}


}