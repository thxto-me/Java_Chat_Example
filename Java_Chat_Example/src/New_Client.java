import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollBar;

public class New_Client {
//�α��� Tab�� ���� ������ �ϱ����� Ŭ���̾�Ʈ ���α׷��� �������� Ŭ����
	private static String Client_Name; // ����� ID
	// ===============================================================���� ����
	final static String SERVER_ADDR = "127.0.0.1"; // �ڹ� ä�� ���� �ּ�
	public static final String DBIP = "localhost"; // DB ���� ������ ����
	final static int SERVER_PORT = 52273; // �ڹ� ä�ü��� ��Ʈ��ȣ
	// ===============================================================
	public static Socket socket; // ������ �����ϱ� ���� ��Ʈ
	public static PrintWriter pw; // �������� �����͸� ���� ���� Ȯ�� ��Ʈ��
	public static volatile BufferedReader br; // ������ ���� �����͸� �ޱ� ���� ��Ʈ��
	public static volatile InputStreamReader isr;
	private static Map<Integer, client_2> chat_room = new HashMap<>(); // �ټ��� ���� ȿ�������� �����ϱ� ���� �ݷ���

	/**
	 * @param args
	 * @throws IOException
	 */
	New_Client() // �⺻������ ���� �α��� GUI�� ����
	{
		new Tab();
		/*
		 * tab Ŭ�������� �����ϸ� runClient()�޼ҵ尡 ����� tabŬ�������� runClient()����.
		 * pw.println("52273#!login"); //�α������� ��� runClient(); //�α����� �㰡�� ���� �� �۵��ϴ� �Լ�
		 * pw.println("52273#!logout"); //�α׾ƿ� ���� socket.close();
		 * pw.println("Ting_Talk"); //�α��� �� ID�� �Է��ؾߵ� Client_Name="Ting_Talk";
		 * 
		 * pw.println("52274#Ting_Talk3"); //��ȭ��û
		 * 
		 */
	}

	static void runClient() {
		try {
			socket = new Socket(SERVER_ADDR, SERVER_PORT);

			isr = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(isr); // �������� �޾ƿ��� ��Ʈ��
			pw = new PrintWriter(socket.getOutputStream(), true); // ������ ������ ���� ��Ʈ��
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ���� ����
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// =======================================�α��� ������ ����
					pw.println("52273#!login"); // �α������� ���
					pw.println(Client_Name); // �α��� �� ID�� �Է��ؾߵ�

					while (true) // ���ѷ����� �ܺο��� �����ϴ� ������ ������ ����
					{
						String line = null; // ���ڿ� �ʱ�ȭ
						String[] temp = { "", "", "" }; // ���ڹ迭 �ʱ�ȭ
						try {
							line = br.readLine(); // �������� �޽����� ����
							System.out.println("�������� ���� �޽���" + line);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("������ Ŭ���̾�Ʈ���� ��Ʈ�� ����");
							e.printStackTrace();
						}

						try {
							temp = line.split("#"); // �������� ���� �޽����� �м��ϱ� ���� �ɰ�.
						} catch (Exception e) {
							System.out.println("Split ����");
							e.getMessage();
						}

						if (temp[1].equals("!invite")) // �ٸ� �������κ��� �ʴ븦 �޾��� ���
						{

							System.out.println(line);
							client_2 df2 = new client_2();
							int tempRoomNumber = Integer.parseInt(temp[0]); // �ʴ���� ��� ���ȣ�� ���� �޽����� �ɴϴ�. 10#!invite ��������
							df2.setRoomNumber(tempRoomNumber); // ���ȣ�� �ش� ��ȭâ GUI�� ���ȣ ������ �����մϴ�.
							chat_room.put(tempRoomNumber, df2); // ���� GUI�� �����ϱ� ���� ���ȣ�� GUI��ü�� �����մϴ�.

						} else if (temp[0].charAt(0) == '!')// x������ ��ȭ��� ����Ʈ�� �������� �޾ƿ� !���ȣ#id1#id2#id3
						{

							System.out.println("��ȭ����� �Ծ��~~");
							String[] users;

							users = line.substring(1).split("#");// ����ǥ�� �����ϰ� ���鹮�ڿ��� ���ڿ����� ������ !���̵�1 ���̵�2 ���̵�3
							String[] copyUsers = new String[10]; // ��ȭ���
							System.arraycopy(users, 1, copyUsers, 0, users.length - 1); // ������ �迭,���� ���� �ε���, ���ٿ�, ������
																						// ����
							// �̷��� �ϴ� ������ �������� ģ������� �޾ƿö� ���ȣ���� ���� ��� ���Ƿ� �ε��� 0�� �ڸ��� ���ߵǱ� �����̴�.
							int roomNum = Integer.parseInt(temp[0].substring(1));// �ش���ȣ�� �޾ƿ�

							client_2 temp1 = chat_room.get(roomNum);// ���ȣ�� �´� GUI�� �ҷ���
							temp1.setChatList(copyUsers); // �ش� ���� GUI�� ģ����� ����

						} // ģ����� �ҷ����� ��
						else {// �������� �޾ƿ� �ٸ�������� x���濡 ���� �޽���
							System.out.println(line);
							int roomNum = Integer.parseInt(temp[0]); // ���ȣ ����
							client_2 temp1 = chat_room.get(roomNum); // �ش� ���ȣ�� GUI ��ü ������
							temp1.out_ar.append(temp[1] + "\n"); // �ش� ���ȣ�� ä�ù��� GUI���� �����͸� ����
							JScrollBar scrollBar = temp1.qScroller.getVerticalScrollBar(); // ��ũ���� �����ϱ� ���� ��ũ�� ����
							scrollBar.setValue(scrollBar.getMaximum()); // �ִ� ��ġ�� ��ũ�ѹٸ� �ű�
						}
						System.out.println("!!!!!!!!!!!!!!!!!!!!!");
					} // while end
				}
			}).start();

		} catch (NullPointerException e) {
			System.out.println("IO ERROR ::::: " + e.getMessage());
			return;
		} // catch end
	}

	static public Map getMap() {
		return chat_room; // ���� �������� GUI���� ������ �� �ְ� �Ѱ���
	}

	static public String getClientName() {
		return Client_Name; // �α����� ID �ѷ���
	}

	static public void setClientName(String tempName) {
		Client_Name = tempName;

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		new New_Client();

		/*
		 * pw.println("52273#!logout"); //������ ���α׷��� ������� pw.println("���ȣ#�����޽���");
		 * //�Ϲ������� ���ڸ� �ش� ���ȣ���� ������ ������� pw.println("�ش���ȣ#!exit"); //�ش� ���� �������� �� ���
		 * pw.println("�ش���ȣ#!invite"); //�α��� �� ó������ ��ȭ ��û�� �޾��� ���
		 * pw.println("52274#Ting_Talk2"); //ó�� ģ������ ���� �ɾ��� ���
		 * pw.println("52275#Ting_Talk2#1"); //�̹� �θ��ִ� �濡�� �ٸ� ����� �ʴ��Ұ��
		 * 
		 */

	}

}
