package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class GUI {
	
	private static int border=4;
	private static int dWidth=1000;
	private static int dHeight=600;
	private static int pWidth=192;
	
	private static int decorH=37;
	private static int bottomH=27;
	private static int scrWidth=23;
	
	private static JFrame frame;
	
	public static Chooser chooser;
	public static TabbedPanel tabbedPanel;

	public static void main(String[] args) throws Exception {
		frame = new JFrame("Расписание");
		List<Image> icons = new Vector<Image>();
		icons.add(new ImageIcon("1326540803_clock.png").getImage());
		icons.add(new ImageIcon("1326541107_clock.png").getImage());
		frame.setIconImages(icons);

		frame.setSize(new Dimension(dWidth , dHeight-bottomH));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chooser = new Chooser();
		tabbedPanel = new TabbedPanel(frame);
		frame.setLayout(null);
		frame.add(chooser);
		frame.add(tabbedPanel);
		
		frame.setMinimumSize(new Dimension(1155, 500));
		
		frame.addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent arg0) {
			}
			public void componentResized(ComponentEvent arg0) {
				dWidth = arg0.getComponent().getWidth();
				dHeight= arg0.getComponent().getHeight();
				chooser.setBounds(border, border, pWidth, dHeight-decorH-border*2);
				tabbedPanel.setBounds(pWidth+3*border, border, dWidth-(pWidth+2*border), dHeight-decorH-border*2);
				for (int i=0; i<tabbedPanel.getComponentCount(); i++)
					tabbedPanel.getComponent(i).setBounds(0, 0, dWidth-(pWidth+2*border+scrWidth), dHeight-decorH-border*2);
			}
			public void componentMoved(ComponentEvent arg0) {
			}
			public void componentHidden(ComponentEvent arg0) {
			}
		});
		frame.setForeground(new Color(194,198,205));
		frame.setBackground(new Color(194,198,205));
		frame.setVisible(true);
		        
	}
}
