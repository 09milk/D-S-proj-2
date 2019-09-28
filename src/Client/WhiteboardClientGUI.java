package Client;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;

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

    public JMenu mnStyle;
    public JMenuItem mntmColor;
    public JMenuItem mntmFont;


    public WhiteboardClientGUI() {
        initializeGUI();
        initializeGroupLayout();
        initializeMenuBar(mainFrame);
    }

    public void startGUI() {
        mainFrame.setVisible(true);
        drawingPanel.requestFocusInWindow();
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
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

            }
        });*/

        btnPencil = new JButton();
        btnPencil.setIcon(new ImageIcon(WhiteboardClientGUI.class.getResource("/Client/icons/icons8-pencil-24.png")));

        btnEraser = new JButton();
        btnEraser.setIcon(new ImageIcon(WhiteboardClientGUI.class.getResource("/Client/icons/icons8-eraser-24.png")));

        drawingPanel = new DrawingPanel();
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.setDoubleBuffered(true);
        drawingPanel.setFocusable(true);


        jSlider = new JSlider();
        jSlider.setPaintTicks(true);
        jSlider.setValue(ClientConstants.DEFAULT_SIZE);
        jSlider.setMaximum(ClientConstants.SLIDER_MAX);
        jSlider.setMinimum(ClientConstants.SLIDER_MIN);
        jSlider.setOrientation(SwingConstants.VERTICAL);

        btnCircle = new JButton("");
        btnCircle.setIcon(new ImageIcon(WhiteboardClientGUI.class.getResource("/Client/icons/icons8-filled-circle-24.png")));

        btnText = new JButton();
        btnText.setIcon(new ImageIcon(WhiteboardClientGUI.class.getResource("/Client/icons/icons8-text-24.png")));

        btnCurrentMember = new JButton("Current member(--)");
    }

    /**********************************************************************************************************
     * group layout, generated by gui editor
     */
    private void initializeGroupLayout() {
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

    /**********************************************************************************************************/


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


        mnStyle = new JMenu("Style");
        menuBar.add(mnStyle);

        mntmColor = new JMenuItem("Color");
        mnStyle.add(mntmColor);
		/*
		mntmFont = new JMenuItem("Font");
		mnStyle.add(mntmFont);*/

    }
}