package Client;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WhiteboardClientGUI {

    public JFrame mainFrame;
	public JButton btnText;
    public JButton btnPencil;
    public JButton btnEraser;
	public JButton btnCircle;

    public DrawingPanel drawingPanel;
    public JSlider jSlider;
    public JButton btnCurrentMember;


    public JMenuBar menuBar;

    public JMenu mnFile;
    public JMenuItem mntmNew;
    public JMenuItem mntmOpen;
    public JMenuItem mntmSave;
    public JMenuItem mntmSaveAs;
    public JMenuItem mntmExit;

    public JMenu mnColor;


    private String currCommand;
    private int thickness = 5;
    private int old_x;
    private int old_y;


    public WhiteboardClientGUI() {
        initializeGUI();
		initializeGroupLayout();
        initializeMenuBar(mainFrame);
    }

    public void startGUI() {
        mainFrame.setVisible(true);
    }

    private void initializeGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Unable to get Java's default ui settings for this system");
		}

		JFrame.setDefaultLookAndFeelDecorated(true);
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 748, 555);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

            }
        });*/

		btnPencil = new JButton();
		btnPencil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				currCommand = "pencil";
			}
		});
		btnPencil.setIcon(new ImageIcon(WhiteboardClientGUI.class.getResource("/Client/icons/icons8-pencil-24.png")));

		btnEraser = new JButton();
		btnEraser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currCommand = "eraser";
			}
		});
		btnEraser.setIcon(new ImageIcon(WhiteboardClientGUI.class.getResource("/Client/icons/icons8-eraser-24.png")));

		drawingPanel = new DrawingPanel();
		drawingPanel.setBackground(Color.WHITE);
		drawingPanel.setDoubleBuffered(true);
		/*
		drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				Graphics2D g = (Graphics2D) drawingPanel.getGraphics();
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

		drawingPanel.addMouseListener(new MouseAdapter() {
			// mouse listener for the canvas
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Graphics2D g = (Graphics2D) drawingPanel.getGraphics();

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
						drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						break;
					case "eraser":
						break;
					case "text":
						drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
						break;
					default:
						drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				drawingPanel.setCursor(Cursor.getDefaultCursor());
			}
		});

		*/

		jSlider = new JSlider();
		jSlider.setPaintTicks(true);
		jSlider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// thickness jSlider
				thickness = jSlider.getValue();
			}
		});
		jSlider.setValue(10);
		jSlider.setMaximum(50);
		jSlider.setMinimum(1);
		jSlider.setOrientation(SwingConstants.VERTICAL);

		btnCircle = new JButton("");
		btnCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currCommand = "circle";
			}
		});
		btnCircle.setIcon(new ImageIcon(WhiteboardClientGUI.class.getResource("/Client/icons/icons8-filled-circle-24.png")));

		btnText = new JButton();
		btnText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currCommand = "text";
			}
		});
		btnText.setIcon(new ImageIcon(WhiteboardClientGUI.class.getResource("/Client/icons/icons8-text-24.png")));

		btnCurrentMember = new JButton("Current member(--)");
	}

	/**********************************************************************************************************
	 * group layout, generated by gui editor
	 */
	private void initializeGroupLayout(){
        GroupLayout groupLayout = new GroupLayout(mainFrame.getContentPane());
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
                                                                .addComponent(jSlider, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                                                .addPreferredGap(ComponentPlacement.RELATED))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                                                        .addComponent(btnPencil, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(btnEraser, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(btnCircle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(ComponentPlacement.RELATED)))
                                                .addComponent(drawingPanel, GroupLayout.PREFERRED_SIZE, 661, GroupLayout.PREFERRED_SIZE)))
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
                                                .addComponent(jSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(drawingPanel, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnCurrentMember))
        );
        mainFrame.getContentPane().setLayout(groupLayout);
    }

    private void initializeMenuBar(JFrame jFrame) {
        menuBar = new JMenuBar();
        jFrame.setJMenuBar(menuBar);

        mnFile = new JMenu("File");
        menuBar.add(mnFile);

        mntmNew = new JMenuItem("New");
        mnFile.add(mntmNew);

        mntmOpen = new JMenuItem("Open");
        mnFile.add(mntmOpen);

        mntmSave = new JMenuItem("Save");
        mnFile.add(mntmSave);

        mntmSaveAs = new JMenuItem("Save As");
        mnFile.add(mntmSaveAs);

        mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);

        mnColor = new JMenu("Color");
        menuBar.add(mnColor);
    }
}
