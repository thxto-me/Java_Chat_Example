import java.sql.Connection;
import java.sql.DriverManager;


public class Login {
  //Tab���� �α��� �� ��� ID�� Passwor�� Ȯ�ΰ� �ߺ��α��� ���� Ȯ��
	/**
	 * @param args
	 */
	public static int client_login(String client_id, String client_password) {
		// TODO Auto-generated method stub
		 Connection conn;
		 final int WRONGPASSWORD=1; //�߸��� ��й�ȣ�� ���
		 final int OVERLAPLOGIN=0; //�ߺ� �α��� �� ���
		 final int SUCCESSLOGIN=2; //�������� �α��� ���
		int LogCheck=0;
	        try{
	            conn=DriverManager.getConnection("jdbc:mysql://"+New_Client.DBIP+":3306/TingTalk","root","dnfxmfk8");
	            //Mysql�� �⺻ ��Ʈ�� 3306
	            System.out.println("---------------------");
	            System.out.println("ģ�� ã�� DB���� ����");
	            
	            String sql="select log from login_check where client_id=?"; //����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����
	            java.sql.PreparedStatement pstmt=conn.prepareStatement(sql); //�� �˻��� ��ȭ�ϴ� ���� �˻��ϱ� ���� PreparedStatement Ŭ����
	            pstmt.setString(1, client_id); 
	            java.sql.ResultSet result=pstmt.executeQuery();
	            String logCh = null;
	            while(result.next())
                {
	            	logCh=result.getString("log"); //�α��� üũ ���� Ȯ��  //�� �ߺ� �α��� �˻�
                }
	        
	            if(logCh.equals("logout")){
	            sql="select * from client_list where client_id=? and client_password=?"; //����ǥ���� �������� ��ȭ�ϴ� ���� �ֱ� ����
	            pstmt=conn.prepareStatement(sql); //�� �˻��� ��ȭ�ϴ� ���� �˻��ϱ� ���� PreparedStatement Ŭ����
	            pstmt.setString(1, client_id); //�������� ��ȭ�ϴ� ���� ����  ���� �����ϴ� ���� �����̸� setInt(index,����) �̷� ������ �ϸ��.
	            pstmt.setString(2, client_password);
	            result=pstmt.executeQuery();
	        
	    
	            String data[]=new String[4];
	            String line = "";
	        
	            
	                //result.beforeFirst();
	                while(result.next())
	                {
	                    data[0]=result.getString("client_id");
	                    
	                    data[1]=result.getString("client_name");
	                    
	                    data[2]=result.getString("client_email");
	                    
	                    data[3]=result.getString("client_phone");
	                    
	                    if(data[0].equals(client_id)) //ID�� �˻��� ID�� �����Ҷ�
	                    	
	                    {
	                    	LogCheck=SUCCESSLOGIN;
	                    	break;
	                    }
	                }
	                //�α��ν� �α���üũ�� ������Ʈ ��Ŵ.
	                if(LogCheck==SUCCESSLOGIN){
	                sql="update login_check set log='login' where client_id=?;";
		            pstmt=conn.prepareStatement(sql); //�� �˻��� ��ȭ�ϴ� ���� �˻��ϱ� ���� PreparedStatement Ŭ����
		     
		            pstmt.setString(1,client_id); //�������� ��ȭ�ϴ� ���� ����  ���� �����ϴ� ���� �����̸� setInt(index,����) �̷� ������ �ϸ��.
		            pstmt.executeUpdate();
	                }else{
                    	LogCheck=WRONGPASSWORD; //��й�ȣ�� �߸� �Է��� ���.
                    	
                    }
	        }//�ߺ��˻� üũ
	            System.out.println("---------------------");
	            conn.close(); //���� ����
	        }
	      catch(Exception e )
	      {
	           System.out.println("DB���� ���� "+e);
	      }
	        
	 
	 return LogCheck;   
	}

}
