package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class ChatRoom extends JFrame {

	private JPanel contentPane;
	
	private JTextField txtCurrentMember;
	private JTextField txtMembers;
	private JTextField txtCharBar;
	private JTextField txtMessage;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setBounds(100, 100, 537, 502);
		
		txtCurrentMember = new JTextField();
		txtCurrentMember.setEditable(false);
		txtCurrentMember.setBackground(SystemColor.menu);
		txtCurrentMember.setText("Current Member:");
		txtCurrentMember.setColumns(10);
		
		txtMembers = new JTextField();
		txtMembers.setEditable(false);
		txtMembers.setBackground(SystemColor.menu);
		txtMembers.setForeground(SystemColor.desktop);
		txtMembers.setText("Members we have currently");
		txtMembers.setColumns(10);
		
		txtCharBar = new JTextField();
		txtCharBar.setBackground(SystemColor.inactiveCaptionBorder);
		txtCharBar.setEditable(false);
		// Override the setText so that it could show the top-10 (10 should be good I guess) messages from server
		txtCharBar.setText("Char bar - all the communications will show here:");
		txtCharBar.setColumns(10);
		
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			// If the key 'Enter' is pressed, then the message is sent to server 
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		txtMessage.setText("Message");
		txtMessage.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addMouseListener(new MouseAdapter() {
			// Same functionality as 'Enter' key pressed, if the enter button is clicked, send message to server
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(txtMembers, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(txtCurrentMember, GroupLayout.PREFERRED_SIZE, 521, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(txtMessage, GroupLayout.PREFERRED_SIZE, 429, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnEnter))
						.addComponent(txtCharBar, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(txtCurrentMember, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtMembers, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtCharBar, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtMessage, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
						.addComponent(btnEnter, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}

}
