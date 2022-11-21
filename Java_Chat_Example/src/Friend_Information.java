import java.awt.Label;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;


public class Friend_Information { //�� Ŭ���� ��뿡 ���� ���� ���
	public static void friend_Info(String friendID){

		String Friend_Id = null;
		String Friend_Name = null;
		String Friend_Email = null;
		String Friend_Phone = null;

	  
		try {	        		  
			Connection con =DriverManager.getConnection("jdbc:mysql://"+New_Client.DBIP+":3306/TingTalk","root","dnfxmfk8");
			
			PreparedStatement ps1 = null;
			ResultSet rs1 = null;
			String sql1 = "select * from client_list where client_id=?";

			ps1 = con.prepareStatement(sql1);
			ps1.setString(1, friendID); // ����Ʈ�� ���õ� ģ���� ID(�Ǵ� �̸�)
			rs1 = ps1.executeQuery();
	
			while (rs1.next()) {
				
				Friend_Id = rs1.getString("client_ID");
				Friend_Name = rs1.getString("client_name");
				Friend_Email = rs1.getString("client_email");
				Friend_Phone = rs1.getString("client_phone");
			}	 
			con.close();
		}
	  
		catch(SQLException sqex){
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}	        
	  
	  JFrame a = new JFrame();
//	  BorderLayout f = new BorderLayout();
	  
	  Label ShowFrdName = new Label(" �̸� : "+ Friend_Name);
	  Label ShowFrdEmail = new Label(" E-mail : "+ Friend_Email);
	  Label ShowFrdPhone = new Label(" ����ó : "+ Friend_Phone);
	  Label ShowFrdID = new Label(" ID : "+ Friend_Id);
	  Label ShowFrdInfo = new Label("< ģ�� ����  >");
	  
	  a.setLayout(null);
	  
	  ShowFrdName.setBounds(30,50,100,30);
	  ShowFrdID.setBounds(30,100,100,30);
	  ShowFrdEmail.setBounds(30,150,200,30);
	  ShowFrdPhone.setBounds(30,200,200,30);
	  ShowFrdInfo.setBounds(30,10,100,30);
	  
	  a.add(ShowFrdName);
	  a.add(ShowFrdID);
	  a.add(ShowFrdEmail);
	  a.add(ShowFrdPhone);
	  a.add(ShowFrdInfo);
	  a.setResizable(false); // false �϶� ũ�� ����
	  a.setVisible(true);
	  a.setSize(300,300);
	  FrameLocation.setLocation(a);
	  a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 


		
	}
}
