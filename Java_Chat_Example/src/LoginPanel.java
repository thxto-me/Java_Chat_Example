import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;

public class LoginPanel extends JFrame { //�α����� ���� ��� �������� ���� ��й�ȣ�� Ʋ���� ��� �ߺ��α����� ���� ���� ������ ��
	// �� �� ������ �޽����� ����ڿ��� �˸��� ���� GUI
	Button checkBtn;
	int check;

	public LoginPanel(String title, int check) {

		super(title);
		setLayout(new FlowLayout());
		this.check = check;
		init(check);
	}

	public void init(int i) {
		Label lbl1;
		checkBtn = new Button("Ȯ��");
		if (i == 0) {
			lbl1 = new Label("�̹� �α��� �Ǿ��ֽ��ϴ�.");
		} else if (i == 1) {
			lbl1 = new Label("��й�ȣ�� �߸� �Է���ϴ�.");
		} else {
			lbl1 = new Label("�α��� �Ǿ����ϴ�.");
		}
		checkBtn.addActionListener(new ExitButtonListener());
		this.add(lbl1);
		this.add(checkBtn);

		setSize(280, 100);
		setVisible(true);
		setResizable(false); // false �϶� ũ�� ����
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		FrameLocation.setLocation(this);
	}

	public class ExitButtonListener implements ActionListener { // ======================������
		// ��ư
		public void actionPerformed(ActionEvent event) {
		
				dispose(); // �ش� �����Ӹ� ����

		}
	}
}
