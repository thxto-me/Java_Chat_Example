import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.Arrays;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.LayerUI;



public class client_2 extends JFrame { //ä�����η��� ��ȭâ�� ���� Ŭ����
	private int choiceRadio=0; //���� ��ư�� ����� �������� �˱�����.
	private int roomNumber = 0; // �� �� �����Ǵ� ���ȣ. �渶�� ���ȣ�� �ο��ǰ� �ٸ�.
	String[] chattingList=new String[10]; //�ش���� ��ȭ��� ����
	public static String str = ""; // ����Ǵ� �޽���
	// =================================================������ ����
	JButton send_bt, exit_bt; // ������ ��ư // ������ ��ư
	public JTextArea out_ar; // ä�� �޽��� ���â
	JTextField in_tf; // ����ڰ� �������� �ϴ� ���ڿ�
	JPanel west_p, center_p, south_p, east_p;
	public JList list; // ģ�� ����Ʈ �� ��ȭ��� ����Ʈ
	JRadioButton firstButton, secondButton;
	JPopupMenu pm; //�˾��޴� ����
	JPopupMenu pm2; //�˾��޴� ���� ��ȭ��� ������ ��
	LayerUI<JPanel> layuerUI;// ���α׷��� �߰� �ϱ�
	JScrollPane qScroller; //out_ar�� ��ũ��
	int scrollValue=0;
	public client_2() {

		
		/////////////////////////////////////////////////////////// ���� �г� �۾�
        west_p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        west_p.setBackground(new Color(235,235,190));

        //************************************************************TextArea ����
        west_p.add(out_ar = new JTextArea(15,15));
        //**************************************************************
        qScroller = new JScrollPane(out_ar);  // ��ũ�� ����
        out_ar.setFocusable(false);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		west_p.add(qScroller);
        this.add(west_p, BorderLayout.WEST);
        
        /////////////////////////////////////////////////////////// ������ �г� �۾�
        east_p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        east_p.setBackground(new Color(235,235,190));
        east_p.add(list = new JList(),"Center");
        list.setFixedCellWidth(130);
        //******************************************************list ����
        list.setFixedCellHeight(20);
        //******************************************************
        JScrollPane qScroller1 = new JScrollPane(list);  // ��ũ�� ����
		qScroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pm = new JPopupMenu(); //�˾��޴� ����
		pm2 = new JPopupMenu(); //��ȭ��� ������ ��
	    JMenuItem Info = new JMenuItem("������");      //�˾��޴� ������ ����
	    JMenuItem Info2 = new JMenuItem("������");  //�ϳ����� �˾����� ���ϱ� ������ 2���� ����
	    JMenuItem FrInvite = new JMenuItem("��ȭ�ʴ�");   
	    JMenuItem addFriend=new JMenuItem("ģ���߰�");
	    pm.add(Info); //�˾��޴��� ������ �߰�
	    pm.add(FrInvite);
	    pm2.add(Info2);
	    pm2.add(addFriend);
	    list.addMouseListener(new ListAction());
	    FrInvite.addActionListener(new PopAction());
	    Info.addActionListener(new PopAction());
	    Info2.addActionListener(new PopAction());
	    addFriend.addActionListener(new PopAction());
		east_p.add(qScroller1);
		 east_p.add(secondButton = new JRadioButton("��ȭ���"),true);
		east_p.add(firstButton = new JRadioButton("ģ�����"));
		secondButton.setBackground(new Color(235,235,190));
		firstButton.setBackground(new Color(235,235,190));
        //*********************************************************���� ��ư �������гη� �̵�
        ButtonGroup bg = new ButtonGroup(); 
		bg.add(firstButton);
		bg.add(secondButton);

		firstButton.addActionListener(new MyAction());
		secondButton.addActionListener(new MyAction());
        this.add(east_p, BorderLayout.CENTER);
        //******************************************************************************
        
        //////////////////////////////////////////////////////////// �Ʒ��� �г� �۾�
        south_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south_p.setBackground(new Color(235,235,190));
       
        south_p.add(in_tf = new JTextField(16));
        south_p.add(send_bt = new JButton("������"));
        south_p.add(exit_bt = new JButton("�泪����"));
        send_bt.setBackground(new Color(230,110,100));
        exit_bt.setBackground(new Color(230,110,100));
		send_bt.addActionListener(new SendButtonListener());
		exit_bt.addActionListener(new ExitButtonListener());
		in_tf.addKeyListener(new textFieldAction());
		
        this.add(south_p, BorderLayout.SOUTH);
 
        //****************************************************** ��üũ�� ����
        setSize(380,350);
        setResizable(false); // false �϶� ũ�� ����
        //***************************************************************        
        setVisible(true);
        FrameLocation.setLocation(this);
        in_tf.requestFocus();
    	this.addWindowListener(new FrameListener()); //�������� X�� ������ �� �̺�Ʈó��
 
    	
	}
	public class FrameListener implements WindowListener{ //������ â�� �̺�Ʈ ó�� ������ ��� ���.

		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowClosing(WindowEvent arg0) {
			
			exit_bt.doClick();
			}//������ �󿡼� xǥ�� ������ �� ������ ��ư�� Ŭ���ϰ� �Ѵ�.
		public void windowDeactivated(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}

	
		
	}

	public void setRoomNumber(int num) // ���ȣ ����
	{

		this.roomNumber = num;

	}

	public int getRoomNumber() // ���ȣ ��ȯ
	{
		return this.roomNumber;
	}

	public class textFieldAction implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==10){ //���͸� ���� �� ������ ��ư�� ���� Ŭ��
			
				send_bt.doClick();
			
			}
		
		}

		public void keyReleased(KeyEvent arg0) {}
		public void keyTyped(KeyEvent arg0) {}
		
	}
	public class PopAction implements ActionListener //����Ʈ���� ������ Ŭ�� �� �˾� â������ �̺�Ʈ ����
													//ģ���ʴ� ��ɰ� ģ�� ������ ������
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("��ȭ�ʴ�")&& choiceRadio==1) //ģ������� ������ ��ȭ�ʴ븦 ������ ��쿡 �̺�Ʈ �߻�
			{
				//String[] fr=null;
				//fr=getFriendList.getSearch(New_Client.getClientName()); //ģ�� ����� �ҷ���
				
				//int selectIndex=list.getSelectedIndex();//����Ʈ���� ������ �ε����� �ҷ���
				
				//String selectFr=fr[selectIndex]; //ģ������Ʈ�� ������ ����� ID�� �ҷ���
				
				
				//String[] tt= (String[]) list.getSelectedValues();
				
				//Arrays.toString(tt);
				
				String selectFr=(String) list.getSelectedValue(); //��ȭ�濡 �ʴ��� ģ�� ID
				if(!(selectFr==null)){ //���� ������ ģ���� Null�ϰ��� �� ID�� ��ġ ���� �������
				String inviteFr="52275#"+selectFr+"#"+getRoomNumber(); //ģ���ʴ� 52275#ģ��ID#���ȣ
				New_Client.pw.println(inviteFr); //�ʴ��� ģ������ �޽����� ����
				System.out.println(selectFr+"���� �ʴ���");
				System.out.println("ģ���ʴ�Ŭ��");
				}
				
			}else if(e.getActionCommand().equals("������")) //�������� Ŭ�� //ģ����Ͽ����� ��ȭ��Ͽ����� 
			{
				
				String selectString=(String) list.getSelectedValue(); //����Ʈ���� ������ �� ����
				if(!(selectString==null))
				Friend_Information.friend_Info(selectString);
			}else if(e.getActionCommand().equals("ģ���߰�"))
			{
				String selectString=(String) list.getSelectedValue(); //����Ʈ���� ������ �� ����
				if(!(selectString==null)&&!(selectString.equals(New_Client.getClientName())))
				AddFriend.addFriend(New_Client.getClientName(), selectString); //�� ID�� ģ�� �߰��� ID�� �Է��մϴ�.
			}
			
		}
	}
	public class ListAction implements MouseListener{ //����Ʈ�� ���� �̺�Ʈ 

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

	          if (arg0.getButton() == arg0.BUTTON3) //���콺 ������ ��ư�� �����̺�Ʈ
	          {
	             JList  c =  (JList) arg0.getComponent();
	             int x = arg0.getX();
	             int y = arg0.getY();
	            if(!list.isSelectionEmpty()&& list.locationToIndex(arg0.getPoint()) == list.getSelectedIndex())
	            {  
	                 int count = c.getModel().getSize();
	                 int cal = count * 18;
	                 if(y <= cal)
	                 {
	                	 if(choiceRadio==1)//ģ�� ����� ������ ��
	                	 {
	                	 pm.show(list, x, y); //�˾��� ������ ��ġ���� ������
	                	 }//choice end
	                	 else if(choiceRadio==0){
	                		 pm2.show(list,x,y);
	                	 }
	                 }
	            }
	          }    
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {} 
	}
	public class MyAction implements ActionListener { // =====================//����Ʈ�� ���� �̺�Ʈ ���� Ŭ����

														// ���� ��ư Ŭ���� ����Ʈ�� ģ�����
														// ���� �̱���
		public void actionPerformed(ActionEvent e) {
			//list.add(FriendListMain.getFriendListMain());
			if(e.getActionCommand().equals("ģ�����")) //ģ������� ���� ��ư�� ������ �� 
			{
				String[] fr=null;
				fr=getFriendList.getSearch(New_Client.getClientName());
				choiceRadio=1;//ģ������� ����
				try{
				list.setListData(fr);
				}catch(NullPointerException e1)
				{
					System.out.println("ģ���� ����..");
				}
			
			}else //��ȭ����� ������ �� 
			{
				//String[] tempChatList=New_Client.getChatList();
				list.setListData(getChatList());
				choiceRadio=0;//��ȭ����� ����
				System.out.println("��ȭ��� ����");
			
			}
		}
		

	}
	public void setChatList(String[] chatList)
	{
		 System.arraycopy(chatList, 0, this.chattingList, 0,chatList.length); //��ȭ����� ������.
		 secondButton.doClick(); //��ȭ����� ������Ʈ �Ǿ��� ������ �ڵ����� ��ȭ������� �̵���Ŵ
		 list.setListData(chatList); //����Ʈ�� ������Ʈ��
	}
	public String[] getChatList()
	{
		return this.chattingList;
	}
	public class SendButtonListener implements ActionListener { // ===================������
																// ��ư
		public void actionPerformed(ActionEvent event) {
			try {
				if (!in_tf.getText().isEmpty()) {
					//==========================================�ڵ� ��ũ�� �۾�
					
					JScrollBar scrollBar = qScroller.getVerticalScrollBar();
			
					scrollBar.setValue(scrollBar.getMaximum());
					scrollValue=scrollValue+10;
					//==========================================
					// pw.println( out_ar.getText());
					String sendMsg = getRoomNumber() + "#"
							+ New_Client.getClientName() + ": "
							+ in_tf.getText(); //���ȣ#ID:�����޽���
					New_Client.pw.println(sendMsg); // ���� �ƿ�ǲ��Ʈ���� �ҷ��ͼ� ��.
					in_tf.setText("");
				}
				in_tf.hasFocus(); // ��Ŀ�� �̵�.
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public class ExitButtonListener implements ActionListener { // ======================������
																// ��ư
		public void actionPerformed(ActionEvent event) {
			try {
				System.out.println("���� �� ������");
				String exitStr = getRoomNumber() + "#!exit";
				Map<Integer, client_2> tempMap = New_Client.getMap();
				tempMap.remove(getRoomNumber());
				New_Client.pw.println(exitStr);
				dispose(); // �ش� �����Ӹ� ����

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/*
	 * public static void main(String[] args) throws IOException { //client_1
	 * Chat = new client_1();
	 * 
	 * }
	 */

}
