import java.sql.Connection;
import java.sql.DriverManager;

public class AddFriend { // ��ȭâ���� ģ�� �߰��ϱ� ���� Ŭ����

	static public void addFriend(String client_id, String friend_id) {

		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/TingTalk", "root",
					"dnfxmfk8");
			// Mysql�� �⺻ ��Ʈ�� 3306
			System.out.println("---------------------");
			System.out.println("ģ�� �߰��ϱ� DB���� ����");
			String sql = "INSERT INTO client_friend_list (client_id,friend_id) SELECT ?,? FROM DUAL WHERE NOT EXISTS (SELECT * FROM client_friend_list WHERE client_id=? and friend_id=?)";
			// �ߺ��� �Է��� �������� �����Դϴ�.
			// String sql="insert into client_friend_list values(?,?)"; //����ǥ���� �������� ��ȭ�ϴ� ����
			// �ֱ� ����
			java.sql.PreparedStatement pstmt = conn.prepareStatement(sql); // �� �˻��� ��ȭ�ϴ� ���� �˻��ϱ� ���� PreparedStatement
																			// Ŭ����

			pstmt.setString(1, client_id); // �������� ��ȭ�ϴ� ���� ���� ���� �����ϴ� ���� �����̸� setInt(index,����) �̷� ������ �ϸ��.
			pstmt.setString(2, friend_id);
			pstmt.setString(3, client_id);
			pstmt.setString(4, friend_id);
			pstmt.executeUpdate();

			System.out.println("---------------------");

			conn.close();
		} catch (Exception e) {
			System.out.println("DB���� ���� " + e);
		}
	}
}
