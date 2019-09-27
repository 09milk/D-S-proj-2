package Client;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class WhiteboardGUI {

	private JFrame frame;
	
	private String currCommand = "null";
	private int thickness = 5;
	private int old_x;
	private int old_y;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WhiteboardGUI window = new WhiteboardGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	   
	/**
	 * Create the application.
	 */
	public WhiteboardGUI() {
		initialize();
	}
	
	public BufferedImage createImage(JPanel panel) {

	    int w = panel.getWidth();
	    int h = panel.getHeight();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    panel.paint(g);
	    g.dispose();
	    return bi;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 748, 555);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnPencil = new JButton("");
		btnPencil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				currCommand = "pencil";
			}
		});
		
		btnPencil.setIcon(new ImageIcon(WhiteboardGUI.class.getResource("/icons/icons8-pencil-24.png")));
		
		JButton btnEraser = new JButton("");
		btnEraser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currCommand = "eraser";
			}
		});
		btnEraser.setIcon(new ImageIcon(WhiteboardGUI.class.getResource("/icons/icons8-eraser-24.png")));
		
		JPanel panel = new JPanel();
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				panel.setDoubleBuffered(true);
				Graphics2D g = (Graphics2D) panel.getGraphics();
				g.setColor(Color.BLACK);
				int x = arg0.getX();
				int y = arg0.getY();

				switch (currCommand) {
					case "eraser":
						g.setColor(Color.WHITE);
					case "pencil":
						g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND ,BasicStroke.JOIN_ROUND));
						g.drawLine(x, y, old_x, old_y);
						old_x = x;
						old_y = y;
						break;
					default:
						
				}
			}
		});

		panel.addMouseListener(new MouseAdapter() {
			// mouse listener for the canvas
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Graphics2D g = (Graphics2D) panel.getGraphics();

				switch (currCommand) {
					case "circle":
						g.fillOval(arg0.getX() - thickness/2, arg0.getY() - thickness/2, thickness, thickness);
						break;
					case "text":
						g.drawString("ILOVEU", arg0.getX(), arg0.getY());
						break;
					default:
						System.out.println("mouse clicked: " + arg0);
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("mouse released: " + e);

			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				old_x = arg0.getX();
				old_y = arg0.getY();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				switch (currCommand) {
					case "pencil":
						panel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						break;
					case "eraser":
						break;
					case "text":
						panel.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
						break;
					default:
						panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setCursor(Cursor.getDefaultCursor());
			}
		});
		panel.setBackground(Color.WHITE);
		
		JSlider slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setToolTipText("tool's thickness");
		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// thickness slider
				thickness = slider.getValue();
			}
		});
		slider.setValue(5);
		slider.setMaximum(30);
		slider.setMinimum(1);
		slider.setOrientation(SwingConstants.VERTICAL);
		
		JButton btnCircle = new JButton("");
		btnCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currCommand = "circle";
			}
		});
		btnCircle.setIcon(new ImageIcon(WhiteboardGUI.class.getResource("/icons/icons8-filled-circle-24.png")));
		
		JButton btnText = new JButton("");
		btnText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currCommand = "text";
			}
		});
		btnText.setIcon(new ImageIcon(WhiteboardGUI.class.getResource("/icons/icons8-text-24.png")));
		
		JButton btnCurrentMember = new JButton("Current member(3)");
		btnCurrentMember.setText("current member(4)");

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnCurrentMember, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnText, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(5))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(slider, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnPencil, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnEraser, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnCircle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 661, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnText, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPencil)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEraser)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCircle, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCurrentMember))
		);
		
		
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.WHITE);
		panel.add(canvas);
		frame.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnColor = new JMenu("Color");
		menuBar.add(mnColor);
	}
}
