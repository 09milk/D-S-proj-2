import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JEditorPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.Color;

import static java.awt.Frame.CROSSHAIR_CURSOR;
import static java.awt.Frame.TEXT_CURSOR;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Whiteboard {

	private JFrame frame;
	
	private String currCommand = "null";
	private int thickness = 5;
	private int old_x;
	private int old_y;
	private Paint currPaint;
	   
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Whiteboard window = new Whiteboard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//   public void paint(Graphics g) {
//      Graphics2D g2 = (Graphics2D) g;
//      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//         RenderingHints.VALUE_ANTIALIAS_ON);
//      Font font = new Font("Serif", Font.PLAIN, 24);
//      g2.setFont(font);
//      g2.drawString("Welcome to TutorialsPoint", 50, 70); 
//   }
	   
	/**
	 * Create the application.
	 */
	public Whiteboard() {
		initialize();
	}
	
	public BufferedImage createImage(JPanel panel) {
	   
	    return null;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 783, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnPencil = new JButton("");
		btnPencil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				currCommand = "pencil";
			}
		});
		
		btnPencil.setIcon(new ImageIcon(Whiteboard.class.getResource("/icons/icons8-pencil-24.png")));
		
		JButton btnEraser = new JButton("");
		btnEraser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currCommand = "eraser";
			}
		});
		btnEraser.setIcon(new ImageIcon(Whiteboard.class.getResource("/icons/icons8-eraser-24.png")));
		
		JPanel panel = new JPanel();
		BufferedImage bi = new BufferedImage(660, 465, BufferedImage.TYPE_INT_RGB);
		Graphics2D g_bi = (Graphics2D) bi.getGraphics();
		g_bi.setColor(Color.WHITE);
		g_bi.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
//				panel.setDoubleBuffered(true);
				Graphics2D g = (Graphics2D) panel.getGraphics();
		        Graphics2D g_bi = (Graphics2D) bi.getGraphics();
				g.setColor(Color.BLACK);
				g_bi.setColor(Color.BLACK);
				int x = arg0.getX();
				int y = arg0.getY();
				switch (currCommand) {
					case "pencil":
						g.setStroke(new BasicStroke(thickness/2, BasicStroke.CAP_ROUND ,BasicStroke.JOIN_ROUND));
						g.drawLine(x, y, old_x, old_y);
						g_bi.setStroke(new BasicStroke(thickness/2, BasicStroke.CAP_ROUND ,BasicStroke.JOIN_ROUND));
						g_bi.drawLine(x, y, old_x, old_y);
						old_x = x;
						old_y = y;
						break;
					case "eraser":
						g.setColor(Color.WHITE);
						g_bi.setColor(Color.WHITE);
						g.fillOval(x, y, thickness, thickness);
						g_bi.fillOval(x, y, thickness, thickness);
						break;
					default:
						
				}
				panel.paintComponents(g);
				
			}
		});

		panel.addMouseListener(new MouseAdapter() {
			// mouse listener for the canvas
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Graphics2D g = (Graphics2D) panel.getGraphics();
				Graphics2D g_bi = (Graphics2D) bi.getGraphics();
				g.setColor(Color.BLACK);
				g_bi.setColor(Color.BLACK);
				switch (currCommand) {
					case "circle":
						Ellipse2D.Double circle = new Ellipse2D.Double((arg0.getX()-thickness*3/2), (arg0.getY()-thickness*3/2), thickness*3, thickness*3);
						g.fill(circle);
						g_bi.fill(circle);
						break;
					case "text":
						g.drawString("ILOVEU", arg0.getX(), arg0.getY());
						g_bi.drawString("ILOVEU", arg0.getX(), arg0.getY());
						break;
					default:
						System.out.println("mouse clicked: " + arg0);
				}
				///////
				panel.paintComponents(g);
				
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
						frame.setCursor(frame.CROSSHAIR_CURSOR);
						break;
					case "eraser":
						break;
					case "text":
						frame.setCursor(frame.TEXT_CURSOR);
						break;
					default:
						frame.setCursor(frame.DEFAULT_CURSOR);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(frame.DEFAULT_CURSOR);
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
		btnCircle.setIcon(new ImageIcon(Whiteboard.class.getResource("/icons/icons8-filled-circle-24.png")));
		
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
		btnText.setIcon(new ImageIcon(Whiteboard.class.getResource("/icons/icons8-text-24.png")));
		
		JButton btnCurrentMember = new JButton("Current member(3)");
		btnCurrentMember.setText("current member(4)");

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnCurrentMember, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(slider, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnCircle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
										.addComponent(btnEraser, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
										.addComponent(btnPencil, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
										.addComponent(btnText, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
									.addGap(5)))
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 660, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnText, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPencil)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEraser)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCircle, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE))
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
			public void mousePressed(MouseEvent e) {
				System.out.println("save btn pressed");
			    try {
					File outputfile = new File("saved.png");
					ImageIO.write(bi, "png", outputfile);
					System.out.println("image saved!");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnColor = new JMenu("Color");
		menuBar.add(mnColor);
		
//		frame.pack();
	}
}
