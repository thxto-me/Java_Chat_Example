import java.sql.Connection;
import java.sql.DriverManager;


public class getFriendList { //��ȭâ���� ģ������� �ҷ����� Ŭ����

	/**
	 * @param args
	 */
	public static String[] getSearch(String client_id) {
		// TODO Auto-generated method stub
		 Connection conn;
		 String[] frList=new String[20];
	        try{
	            conn=DriverManager.getConnection("jdbc:mysql://"+New_Client.DBIP+":3306/TingTalk","root","dnfxmfk8");
	            //Mysql�� �⺻ ��Ʈ�� 3306
	            System.out.println("---------------------");
	            System.out.println("ģ�� ã�� DB���� ����");

	            String sql="select client_friend_list.client_id,client_friend_list.friend_id from client_friend_list  join login_check on(client_friend_list.friend_id = login_check.client_id) where client_friend_list.client_id=? and login_check.log='login';"; 
	            //����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����
	            //�α��ε� ģ���� ����Ʈ���� ����
	            java.sql.PreparedStatement pstmt=conn.prepareStatement(sql); //�� �˻��� ��ȭ�ϴ� ���� �˻��ϱ� ���� PreparedStatement Ŭ����
	            pstmt.setString(1, client_id); //�������� ��ȭ�ϴ� ���� ����  ���� �����ϴ� ���� �����̸� setInt(index,����) �̷� ������ �ϸ��.
	            java.sql.ResultSet result=pstmt.executeQuery();
	            
	            
	            
	            String data[]=new String[2];
	            String line = "";
	        
	            int i=0;
	                //result.beforeFirst();
	                while(result.next())
	                {
	                	  	data[0]=result.getString("client_id");
		                    
		                    data[1]=result.getString("friend_id");
		                    
		                  
	                    frList[i]=data[1];
	                    i++;
	                   
	                }
	                System.out.println("---------------------");
	            
	            conn.close(); //���� ����
	        }
	      catch(Exception e )
	      {
	           System.out.println("DB���� ���� "+e);
	      }
	        
	        return frList; //ģ���������
	    
	}

}
