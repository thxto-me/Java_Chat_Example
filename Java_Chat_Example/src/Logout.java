import java.sql.Connection;
import java.sql.DriverManager;


public class Logout {
	//����ڰ� �������̰ų� �� ���������� �α׾ƿ��� ���� ��� DB�� �α׾ƿ��� �˸��� ���� Ŭ����
	/**
	 * @param args
	 */
	public static void client_logout(String client_id) {
		// TODO Auto-generated method stub
		 Connection conn;
		 boolean LogCheck=false;
	        try{
	            conn=DriverManager.getConnection("jdbc:mysql://"+New_Client.DBIP+":3306/TingTalk","root","dnfxmfk8");
	            //Mysql�� �⺻ ��Ʈ�� 3306
	            System.out.println("---------------------");
	            System.out.println("�α� �ƿ� DB���� ����");
	            
	            
	            
	            String sql;
	            sql="update login_check set log='logout' where client_id=?;";
	            java.sql.PreparedStatement pstmt=conn.prepareStatement(sql); //�� �˻��� ��ȭ�ϴ� ���� �˻��ϱ� ���� PreparedStatement Ŭ����
	          
	            
	                //�α׾ƿ��� �α���üũ�� ������Ʈ ��Ŵ.
		            pstmt=conn.prepareStatement(sql); //�� �˻��� ��ȭ�ϴ� ���� �˻��ϱ� ���� PreparedStatement Ŭ����
		            pstmt.setString(1,client_id); //�������� ��ȭ�ϴ� ���� ����  ���� �����ϴ� ���� �����̸� setInt(index,����) �̷� ������ �ϸ��.
		            pstmt.executeUpdate();
	               
	                
	            System.out.println("---------------------");
	            conn.close(); //���� ����
	        }
	      catch(Exception e )
	      {
	           System.out.println("DB���� ���� "+e);
	      }
	        
	 
	
	}

}
