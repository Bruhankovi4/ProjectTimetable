package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.CollisionException;

@SuppressWarnings("serial")
public class ColiisionDispatcher extends JPanel {
	CollisionException exception; 
	JList collisions;
	JButton okay;
	JDialog dialog;
	
public ColiisionDispatcher(final JDialog dial,CollisionException e,final JFrame parentframe ) {
	super();
	parentframe.setEnabled(false);
	this.dialog=dial;
	okay = new JButton("Ok");
	okay.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			int code=collisions.getSelectedIndex();
		/*	if(code==0){
				exception.ignore();
			}
			else if(code==1){
				exception.skip();
			}
			else if(code==2){
				exception.moveOld();
			}
			else if(code==3){
				exception.okey();
			}*/
			parentframe.setEnabled(true);
			dial.dispose();
		}
	});
	this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	this.setVisible(true);
	this.setLayout(new FlowLayout());
	
	e = exception;
	String[] strings = {
					new String("Я знаю что делаю"),
					new String("Я потом исправлю"),
					new String("Сдвинуть старое"),
					new String("Отменить добавление")
	};
	collisions= new JList(strings);
	collisions.setSelectedIndex(3);
	this.add(new JLabel(new String("<html>Применение указзаных <br> изменений приведет к накладке: <br>"/*+exception.toString()*/+"<br></html>")));
	this.add(collisions);
	//JPanel temp = new JPanel();
	this.add(okay);
	okay.setSize(40, 20);
	//this.add(temp);
	this.setBackground(Color.pink);
	okay.setBackground(Color.ORANGE);
	collisions.setBackground(Color.cyan);
	
}
}
