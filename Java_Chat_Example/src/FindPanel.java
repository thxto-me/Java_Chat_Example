import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class FindPanel extends JFrame {

	static String[] MaxList = new String[100]; // �ִ� ģ�� ����Ʈ �迭
	static String Login_ID = New_Client.getClientName();
	static String Fdata[] = new String[3]; // ģ���� �� ���� ����

	static JPopupMenu pm; // �˾��޴�
	static JTextField txt1; // �ؽ�Ʈ�ʵ�
	static JButton FButton; // �˻���ư

	static Choice FChoice; // �޺��ڽ� ����
	static int findindex; // �޺��ڽ� �ε���
	static int num; // �˻���� �ε���
	int pmindex;
	static JList FriendList; // ����Ʈ ����
	static String FriendInfo; // �ؽ�Ʈ�ʵ忡 �Էµ� ���������ϴ� ����

	static JPanel getFindFriend() {
		// super("TingTalk");
		Login_ID = New_Client.getClientName();
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel FFindMain = new JPanel(new BorderLayout()); // ���� �г�, BorderLayout���� ����
		Panel Logout = new Panel(new BorderLayout()); // Logout�г� ����, BorderLayout���� ����
		Panel FFind = new Panel(); // FFind(Friend Find)�г� ����
		Panel Logoutset = new Panel(); // Logout�г� ����
		JButton LogoutBtn = new JButton("�α� �ƿ�");
		LogoutBtn.setBackground(new Color(230, 110, 100));

		FButton = new JButton("ģ��ã��"); // ��ư����
		FButton.setBackground(new Color(230, 110, 100));
		txt1 = new JTextField(10); // �ؽ�Ʈ�ʵ����
		FriendList = new JList(); // ����Ʈ����

		FChoice = new Choice(); // �޺��ڽ�����
		FChoice.add("���̵�");
		FChoice.add("�̸�");
		FChoice.add("�ڵ��� ��ȣ");
		FChoice.add("�̸���");

		FFind.add(FChoice); // North�κ� �г�
		FFind.add(txt1);
		FFind.add(FButton);

		// JLabel logo = new JLabel(new ImageIcon(getClass().getResource("1.jpg")));
		// //���� �����ܻ���
		Logoutset.add(LogoutBtn);// �α׾ƿ� UI����

		Logout.add("Center", Logoutset); // South �κ� �г�
		// Logout.add("West",logo);

		FFindMain.add("North", FFind);
		FFindMain.add("South", Logout);
		FFindMain.add("Center", FriendList);

		tabbedPane.addTab("ģ�� ã��", null, FFindMain, "Firet Panel"); // �Ǹ�
		// getContentPane().add(tabbedPane);
		ActionListener LogoutAction = new ActionListener() { // �α׾ƿ� ��ư �̺�Ʈ

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Map d = New_Client.getMap(); // ���� ȸ���� ���ִ� �濡���� ������ ����
				Collection<client_2> client_2_values = d.values(); // �濡 ���� ó���� �ϱ����� ���� ����
				for (Iterator<client_2> it = client_2_values.iterator(); it.hasNext();) {
					it.next().exit_bt.doClick(); // �ش� ���� ������ ��ư��������.
				}

				System.exit(1);

			}

		};
/////////////////////////////////////////////////////////////////////////////////////
		ActionListener actionListener = new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {

				String TempFriendList = (String) FriendList.getSelectedValue();

				findindex = FChoice.getSelectedIndex(); // �޺��ڽ�JChoic)�� ���õ� �ε��� ��ȣ ����
				num = FriendList.getSelectedIndex();

				if (actionEvent.getSource() == txt1) {
					FriendInfo = actionEvent.getActionCommand();
				} // �ؽ�Ʈ�ʵ忡�� ���͸� ������...�̺�Ʈ
				if (actionEvent.getSource() == FButton) {
					FriendInfo = txt1.getText();
				} // ��ưŬ���� �̺�Ʈ...

				////////////////// ģ�� �˻��ϴ� �κ�///////////////////
				Connection conn;
				try {
					String sql = null;
					conn = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/TingTalk", "root",
							"dnfxmfk8");

					if (findindex == 0) {
						sql = "select * from client_list where client_id like ?";
					} // ����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����
					else if (findindex == 1) {
						sql = "select * from client_list where client_name like ?";
					} // ����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����
					else if (findindex == 2) {
						sql = "select * from client_list where client_phone like ?";
					} // ����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����
					else if (findindex == 3) {
						sql = "select * from client_list where client_email like ?";
					} // ����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����

					java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%" + FriendInfo + "%"); // �ؽ�Ʈ�� �ִ� �Է��� ��~ SQL�� ������~
					java.sql.ResultSet result = pstmt.executeQuery();

					String data[] = new String[4]; // SQL�κ��� �̸� ��ȭ��ȣ �̸����� �޴� �迭
					String line = ""; // SQL�κ��͹��� ������ ������ �ϳ��� ���� ���ڿ�
					int i = 0;

					for (int j = 0; j < 100; j++) {
						MaxList[j] = ""; // MaxList�� �˻��ø��� �ʱ�ȭ

					}

					while (result.next()) {
						data[0] = result.getString("client_id"); // SQL�� ���� �̸��� �޴´�
						data[1] = result.getString("client_name"); // SQL�� ���� ��ȭ��ȣ���޴´�
						data[2] = result.getString("client_phone"); // SQL�� ���� �̸����� �޴´�
						data[3] = result.getString("client_email"); // SQL�� ���� �̸����� �޴´�

						line = ""; // line�ʱ�ȭ
						for (int j = 0; j < data.length; j++) // data[]�� �ϳ��� ���ڿ��� ����
						{

							line = line + data[j] + " ";

						}
						MaxList[i] = line;// ��ģ ����(line)�� �迭 MaxList�� �ϳ��ϳ� ����
						i++;

						FriendList.setListData(MaxList); // �˻���ģ���� ���� MaxList�迭���ִ°��� ���� ����Ʈ�� ����
					}
				}

				catch (Exception e) {
					System.out.println("DB���� ���� " + e);
				}

				// ������ �׼� �̺�Ʈ

				if (actionEvent.getActionCommand() == "������") {

					String SelFrdID = MaxList[num];
					String[] InfoCut = SelFrdID.split(" "); // ģ�����̵�

					// System.out.println(InfoCut[num+1]);
					if (!InfoCut[0].equals("")) {

						try {
							conn = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/TingTalk",
									"root", "dnfxmfk8");

							PreparedStatement ps1 = null;
							ResultSet rs1 = null;
							String sql1 = null;

							if (findindex == 0) {
								sql1 = "select * from client_list where client_id like ?";
							} // ����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����
							else if (findindex == 1) {
								sql1 = "select * from client_list where client_name like ?";
							} // ����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����
							else if (findindex == 2) {
								sql1 = "select * from client_list where client_phone like ?";
							} // ����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����
							else if (findindex == 3) {
								sql1 = "select * from client_list where client_email like ?";
							} // ����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����

							ps1 = (PreparedStatement) conn.prepareStatement(sql1);
							ps1.setString(1, InfoCut[0]); // ����Ʈ�� ���õ� ģ���� ID(�Ǵ� �̸�)
							rs1 = ps1.executeQuery();

							while (rs1.next()) {
								Fdata[0] = rs1.getString("client_name");
								Fdata[1] = rs1.getString("client_email");
								Fdata[2] = rs1.getString("client_phone");
							}
						} catch (SQLException sqex) {
							System.out.println("SQLException: " + sqex.getMessage());
							System.out.println("SQLState: " + sqex.getSQLState());
						}

						JFrame a = new JFrame();
						// BorderLayout f = new BorderLayout();

						Label ShowFrdID = new Label(" ID : " + InfoCut[0]);
						Label ShowFrdInfo = new Label("< ģ�� ����  >");
						Label ShowFrdName = new Label(" �̸� : " + Fdata[0]);
						Label ShowFrdEmail = new Label(" E-mail : " + Fdata[1]);
						Label ShowFrdPhone = new Label(" ����ó : " + Fdata[2]);

						a.setLayout(null);

						ShowFrdName.setBounds(30, 50, 100, 30);
						ShowFrdID.setBounds(30, 100, 100, 30);
						ShowFrdEmail.setBounds(30, 150, 200, 30);
						ShowFrdPhone.setBounds(30, 200, 200, 30);
						ShowFrdInfo.setBounds(30, 10, 100, 30);

						a.add(ShowFrdName);
						a.add(ShowFrdID);
						a.add(ShowFrdEmail);
						a.add(ShowFrdPhone);
						a.add(ShowFrdInfo);

						a.setVisible(true);
						a.setSize(300, 300);
						FrameLocation.setLocation(a);
					} // if end
				}

				if (actionEvent.getActionCommand() == "ģ���߰�") {

					int num = FriendList.getSelectedIndex(); // ����Ʈ���� ���õ� �ε�����ȣ

					// String SelFrdID = MaxList[num+1]; //����Ʈ �ε����� ���ڿ�(�߰��Ϸ��� ģ�� ����)
					String[] addCut = TempFriendList.split(" ");

					if ((!addCut[0].equals("")) && !addCut[0].equals(New_Client.getClientName())) { // ��ĭ�� Ŭ���ϰų� �ڽ��� ID��
																									// �߰� �� ��� ����
						// �� ���� Ŭ���� �׸��� ��ĭ�� �ƴϰų� �� ID�� ���� �ʴٸ�...
						Connection con = null;

						try {
							con = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/TingTalk",
									"root", "dnfxmfk8");
							// System.out.println("MySql ���� ����");
						} catch (SQLException e) {
							// System.out.println("���� ����");
						}

						PreparedStatement ps = null;
						String sql = "INSERT INTO client_friend_list (client_id,friend_id) SELECT ?,? FROM DUAL WHERE NOT EXISTS (SELECT * FROM client_friend_list WHERE client_id=? and friend_id=?)";

						try {
							ps = (PreparedStatement) con.prepareStatement(sql);
							String MyId = New_Client.getClientName();
							ps.setString(1, MyId); // �� �̸�
							ps.setString(2, addCut[0]); // ���õ� ģ���̸�
							ps.setString(3, MyId);
							ps.setString(4, addCut[0]);
							int n = ps.executeUpdate();

							if (n > 0) {
								System.out.println("ģ�� ����");
							} else {
								System.out.println("����");
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}

			}

		};
		// �ؽ�Ʈ�κ� �̺�Ʈ����

		txt1.addActionListener(actionListener);
		FButton.addActionListener(actionListener);
		LogoutBtn.addActionListener(LogoutAction);

		pm = new JPopupMenu(); // �˾��޴� ����
		JMenuItem Info = new JMenuItem("������"); // �˾��޴� ������ ����
		JMenuItem Fadd = new JMenuItem("ģ���߰�");

		pm.add(Info); // �˾��޴��� ������ �߰�
		pm.add(Fadd);

		Info.addActionListener(actionListener);
		Fadd.addActionListener(actionListener);

		//////////////////////////// ����Ʈ���� ��Ŭ���� �˾��޴�//////////////////////////////
		FriendList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON3) {
					JList c = (JList) e.getComponent();
					int x = e.getX();
					int y = e.getY();
					if (!FriendList.isSelectionEmpty()
							&& FriendList.locationToIndex(e.getPoint()) == FriendList.getSelectedIndex()) {
						int count = c.getModel().getSize();
						int cal = count * 18;
						if (y <= cal) {
							pm.show(FriendList, x, y);
						}
					}
				}
			}
		});
		return FFindMain;

	}

	/*
	 * public static void main(String args[]) { FindPanel Search = new FindPanel();
	 * Search.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * Search.setVisible(true); Search.setSize(500, 500); }
	 */

}