import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class New_Server {
	// Ŭ���̾�Ʈ���Լ� ���� �����͸� ó���ϰ� ���� �ϱ� ���� Ŭ���� ���� �߿��� ����
	// �α���, �α׾ƿ�, ģ���ʴ�, ��ȭ��, ��ȭ�� ���� ��� ���� ������ ����.
	/**
	 * @param args
	 */
	public LogClass log = new LogClass();
	private final static int PORT_NO = 52273;
	private ServerSocket listener; // ���� ���� ����
	// =================================�ٲ�� �κ�
	Map<Integer, String[]> client_room_list = new HashMap<>(); // �����
	Map<String, Connection> client_all = new HashMap<>(); // ��ü ����
	// =================================�ٲ�� �κ�

	public Integer SERVER_GIVE_NUMBER = 1; // ������� �ʿ��� ���ȣ

	New_Server() throws IOException {
		listener = new ServerSocket(PORT_NO);// �ʱ�ȭ

		System.out.println("ON PORT : " + PORT_NO);
	}

	// ��ü�� ���鶧 �⺻���� �ʱ�ȭ ����
	void runServer() {
		try {
			while (true) // ���ѷ����� �ܺο��� �����ϴ� ������ ������ ����
			{
				Socket socket = listener.accept(); // �ܺο��� ������ �����Ҷ����� �����
				System.out.println("accept connection");

				Connection con = new Connection(socket); // ������ ���ڷ� �ѱ�

				con.start(); // ������ ��ü Thread�� ���� ��Ŵ

			} // while end
		} catch (Exception e) {
			System.out.println("IO ERROR ::::: " + e.getMessage());
			return;
		} finally {
		} // catch end
	}

	////////////////////////////////////////////////////////
	private class Connection extends Thread // Thread�� ��ӹ���
	{
		private volatile BufferedReader br;
		private volatile PrintWriter pw;
		private String clientName; // ������ ȸ���̸�
		private String client_ip; // ������ ȸ���� ip
		private InetAddress client_inet; // ip��� ���� ��ü ����

		Connection(Socket s) throws IOException {

			// client_inet=s.getInetAddress();
			// client_ip=client_inet.getHostAddress(); //ip ������
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream(), true);
		}

		public void run() {
			String line = null;

			try {

				while ((line = br.readLine()) != null) { // ������ ���涧���� ���� ��Ŵ �׸��� line �迭�� �Է¹��� �޽����� ����
					// ==============================================�α� �Է�
					log.log_out(line);// �α� ��� �����
					String[] division = line.split("#"); // Ŭ���̾�Ʈ ������ ���� �޽����� ��ɺ��� �����ϱ� ����
					int divisionNum = Integer.parseInt(division[0]);
					if (divisionNum == 52273) {
						switch (division[1]) {
						case "!login": // ========================================�α���
							clientName = br.readLine();// Ŭ���̾�Ʈ�� ID�� �޾ƿ�
							log.log_out(clientName + "�α���");// �α� ��� �����
							System.out.println("�α��� ����");
							// log.log_out(clientName+"�α���");//�α� ��� �����
							synchronized (client_all) {
								client_all.put(this.getClientName(), this); // ȸ���� ID�� Ű�� �׸��� �ش� ȸ���� �����带 Value�� ����
								System.out.println("����" + client_all.get(this.clientName).getClientName());

							}
							break;
						case "!logout": // =======================================�α׾ƿ�
							System.out.println(clientName + ":" + "LoGOUT"); // �ᱹ Ŭ���̾�Ʈ�� ������ ��ü�ϸ� �޽����� ����
							log.log_out(clientName + "�α׾ƿ�");// �α� ��� �����
							// log.log_out(clientName+"�α׾ƿ�");
							synchronized (client_all) {
								Logout out = new Logout();
								out.client_logout(this.getClientName()); // �����ͺ��̽��� �α׾ƿ��� ����
								client_all.remove(this.getClientName()); // ȸ�� �������� �����Ϳ��� ����

							} // synchronized end
							break;
						}// switch end
					} else if (divisionNum == 52274)// ����ڰ� �������� �ʴ��� //============================================�ʴ� ���
					{
						synchronized (SERVER_GIVE_NUMBER) {
							synchronized (client_all) {

								if (!client_all.containsKey(division[1])) { // Ű���� ã�Ƽ� �ִ� ��� True�̹Ƿ� !���� �� ���� �Ͽ� ������ ���
																			// True�� ����
									send(division[1] + "���� ȸ���� �����ϴ�.");
									continue;
								} // if end
							} // sync end
							Connection inviteUser = client_all.get(division[1]);

							synchronized (client_room_list) {

								// ���࿡ ù��°�� ���� ���̸� false�� ���� //���ο� ���� �������� ��� �߻�
								String[] roomUser = { this.clientName, division[1] }; // �ʴ��� ������ �ʴ� ���� ������ �Ѵ� ���ڹ迭�� �Է�
								client_room_list.put(SERVER_GIVE_NUMBER, roomUser);
								String tempStr = SERVER_GIVE_NUMBER + "#!invite"; // ������ �� ���ȣ�� �ʴ��ߴٴ°� �˸��� ���� �ʴ����
																					// Ŭ���̾�Ʈ���� �޽����� ���� ����
								this.send(tempStr); // ��ȭ ��û�� ��û�� ����� ��ȭâ�����ϱ� ������
								inviteUser.send(tempStr);
								sendClientsList(SERVER_GIVE_NUMBER);
								SERVER_GIVE_NUMBER++; // �����ϳ� ���������� ++
							} // sync end
						} // SERVER_GIVE_NUMBER End
					} // else if end
					else if (divisionNum == 52275)// 52275#id#���ȣ //�� ���� �̹� ���� �����Ǿ� �ְ� 2���� �ִ� ���¿��� �ٸ� ����� �ҷ��� ��쿡 �߻�
					{
						synchronized (client_all) {

							if (!client_all.containsKey(division[1])) { // Ű���� ã�Ƽ� �ִ� ��� True�̹Ƿ� !���� �� ���� �Ͽ� ������ ���
																		// True�� ����
								send(division[1] + "���� ȸ���� �����ϴ�.");
								continue;
							} // if end
						} // sync end
						Connection inviteUser = client_all.get(division[1]);// ������ ȸ���� ��ü(Thread)�� ����
						synchronized (client_room_list) {
							int tempDivision = Integer.parseInt(division[2]); // �ش���ȣ ����
							System.out.println("�ʴ��� ���ȣ = " + tempDivision);
							if (client_room_list.containsKey(tempDivision))// �ش���ȣ�� �����ϴ��� Ȯ��
							{ // �̹� ������ ���� �ִ� ��쿡 Ture
								String[] tempUsers;

								tempUsers = client_room_list.get(tempDivision); // �ش���� ��ȭ����� �ҷ���

								StringBuilder sb = new StringBuilder();
								for (int i = 0; i < tempUsers.length; i++) {

									if (!tempUsers[i].equals("")) {
										sb.append(tempUsers[i]);// �ش���ȣ�� ����Ʈ�� id#id#���� ����
										sb.append("#");
									}
								}
								sb.append(division[1]); // �������� �ʴ���� ����� ID�� �ǵڿ� ���� id1#id2#�ʴ���� id
								sb.append("#");

								String[] sbTemp = sb.toString().split("#"); // �׸��� �ٽ� #������ ��� �迭�θ���
								client_room_list.put(tempDivision, sbTemp); // �ش���� ����Ʈ�� ����

								String tempStr = tempDivision + "#!invite"; // ������ �� ���ȣ�� �ʴ��ߴٴ°� �˸��� ���� �ʴ���� Ŭ���̾�Ʈ����
																			// �޽����� ���� ����
								inviteUser.send(tempStr); // �ʴ���� ������� �ʴ�޽����� ����.

								sendClientsList(tempDivision); // �ش���� ��ȭ����� ����
							} // if end
						}
					} else { // ================================================================================�ش�
								// �濡�Ը� �޽����� ����.
						if (division[1].equals("!exit"))// ==================================���� ��������� ���ȣ#!exit
						{
							synchronized (client_room_list) {
								String[] updateStr = client_room_list.get(divisionNum);
								StringBuilder sb = new StringBuilder(); // String�� �ٸ��� �޸� ������ �ڵ� ������ String�� ����ϱ����
								for (int i = 0; i < updateStr.length; i++) {
									if (!updateStr[i].equals(this.clientName)) // �ڽ��� ID�� ������ �ε����� ã�Ƽ� ��ĭ���� ä��
									{
										sb.append(updateStr[i]);
										sb.append("#");
									} // if end
								} // for end

								client_room_list.put(divisionNum, sb.toString().split("#"));
								sendClientsList(divisionNum); // �� ��ȭ��� �ٽ� ������Ʈ
							} // sync end
						} // !exit end
						else {// =================================�ش� ���ȣ�� �����ؼ� �ش� ���ȣ���Ը� �޽����� ���� ��Ŵ
							broadcast(line, divisionNum);
							System.out.println(line);
						} // broadcast else end
					} // else end
				}
			} catch (Exception e) {
				System.out.println("ERROR 100: " + e.getMessage()); // �� ���������� ���α׷��� ���� ���.
				synchronized (client_all) {
					log.log_out(clientName + "�α׾ƿ�");// �α� ��� �����
					Logout out = new Logout();
					out.client_logout(this.getClientName()); // �����ͺ��̽��� �α׾ƿ��� ����
					client_all.remove(this.getClientName()); // ȸ�� �������� �����Ϳ��� ����

				} // synchronized end
			} finally { // �ƾ� ���α׷��� �� ���
				// logout �κп��� ��������.

			} // finally end

		}

		public String getClientName() // �ش� ��ü�� ȸ���̸� ��ȯ
		{
			return this.clientName;
		}

		private void broadcast(String msg, int roomNum) // �ش� �濡�� �ִ� Ŭ���̾�Ʈ���Ը� �޽��� ����
		{
			System.out.println("broadcast msg : " + msg);
			synchronized (client_room_list) {
				String[] tempUsers = client_room_list.get(roomNum);
				synchronized (client_all) {
					for (int i = 0; i < tempUsers.length; i++) {
						if (!tempUsers[i].equals("")) {
							Connection tempUser = client_all.get(tempUsers[i]);
							tempUser.send(msg);
						}
					}
				} // sync end
			} // synchronized end
		}// broadcast end

		private void send(String msg) // �ܼ��� ���Ͽ� ����� ��Ʈ������ ���ڿ��� ����
		{
			pw.println(msg);
		}// send ENd

		private void sendClientsList(int inRoomNum) // �ش� ���� ģ������� ������Ʈ ��Ű�� ���� �޼ҵ�
		{
			StringBuilder sb = new StringBuilder(); // String�� �ٸ��� �޸� ������ �ڵ� ������ String�� ����ϱ����

			synchronized (client_room_list) {
				String[] temp_room_list = client_room_list.get(inRoomNum);

				for (int i = 0; i < temp_room_list.length; i++) {
					if (!(temp_room_list[i] == null))
						sb.append(temp_room_list[i]);
					sb.append("#");
				}
				broadcast("!" + inRoomNum + "#" + sb.toString(), inRoomNum); // ���ڿ��� ���� (Ŭ���̾�Ʈ �������� ģ�� ����� ����� ����� �˾ƾߵ�.
				// !���ȣ#id1#id2#
				// !10#id1#id2#
			} // sync end

		}

	}// Connection End

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Server Runningsdsd");
			new New_Server().runServer(); // ���� ����

		} catch (Exception e) {
			System.out.println("Socket ERROR");
		}

	}

}
