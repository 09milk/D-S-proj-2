package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import Network.User;

public class ChatRoom extends JFrame {

	private JPanel contentPane;
	
	private JTextField txtCurrentMember;
	private JTextArea txtMembers;
	private JScrollPane scroll;
	private JTextArea txtCharBar;
	private JTextField txtMessage;

    private ClientNetworkController clientNetworkController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatRoom frame = new ChatRoom();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChatRoom() {
//		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setBounds(100, 100, 537, 516);
		
		txtCurrentMember = new JTextField();
		txtCurrentMember.setEditable(false);
		txtCurrentMember.setBackground(SystemColor.menu);
		txtCurrentMember.setText("Current Member:");
		txtCurrentMember.setColumns(10);
		
		txtMembers = new JTextArea();
		txtMembers.setEditable(false);
		txtMembers.setBackground(SystemColor.menu);
		txtMembers.setForeground(SystemColor.desktop);
		txtMembers.setText("Members we have currently");
		txtMembers.setColumns(10);
		

		txtCharBar = new JTextArea();
		txtCharBar.setEditable(false);
		txtCharBar.setBackground(SystemColor.inactiveCaptionBorder);
		txtCharBar.setText("");
		txtCharBar.setColumns(10);
		scroll = new JScrollPane(txtCharBar, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			// If the key 'Enter' is pressed, then the message is sent to server 
			@Override
			public void keyPressed(KeyEvent arg0) {
			    if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
					if (! txtMessage.getText().equals("")) {
						ChatRoom.this.sendChat(txtMessage.getText());
						txtMessage.setText("");
					}
			    }
			}
		});
		txtMessage.setText("");
		txtMessage.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addMouseListener(new MouseAdapter() {
			// Same functionality as 'Enter' key pressed, if the enter button is clicked, send message to server
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (! txtMessage.getText().equals("")) {
					ChatRoom.this.sendChat(txtMessage.getText());
					txtMessage.setText("");
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(txtMembers, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(txtCurrentMember, GroupLayout.PREFERRED_SIZE, 521, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtMessage, GroupLayout.PREFERRED_SIZE, 429, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnEnter))
						.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
					.addGap(21))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(txtCurrentMember, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtMembers, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtMessage, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
						.addComponent(btnEnter, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}


	public void setCNC(ClientNetworkController CNC){
		this.clientNetworkController = CNC;
	}

	public void addChat(User user, String message) {
		this.txtCharBar.setText(this.txtCharBar.getText() + user.userName + ": " + message + "\n");
        repaint();
	}

	public void sendChat(String message) {
		this.clientNetworkController.addChat(message);
	}
	
	public void updateMemberList(ArrayList<User> memberList){
		String txt = "";
		for (User user : memberList){
			txt += user.userName + "\n";
		}
		this.txtMembers.setText(txt);
	}
}
