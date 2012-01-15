package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Auditory;
import model.CollisionException;
import model.Lesson;
import model.Specialty;
import model.Subject;
import model.Teacher;
import accessor.DataDispatcher;

public class Chooser2 extends JPanel {
	private static final long serialVersionUID = 1L;
	int ncourse = 5; // getNumber from source;
	JComboBox spetiality;
	JSpinner year = new JSpinner();
	JComboBox subject;
	JComboBox teacher;
	JComboBox lesson;
	JComboBox auditory;

	Specialty curspec; // current spetiality
	Subject cursubj;
	Teacher curteacher;
	Lesson curlesson;
	Auditory curaudit;
	List<Specialty> listofspec;
	DataDispatcher datdisp;

	private JButton setbut; // button that sets lesson to the table
	private boolean flag = false; // можно устанавливать значение в таблицу
	int coursenum = 1;
	private List<Subject> listofsubj;
	private TabbedPanel tabbedPanel; // tab,that chooser currently working with

	public Chooser2() throws Exception {
		datdisp = new DataDispatcher((byte) 2); // data from
																// database
		spetiality = new JComboBox();
		subject = new JComboBox();
		lesson = new JComboBox();
		teacher = new JComboBox();
		auditory = new JComboBox();

		spetiality.addItem(new String("----------не выбрано----------"));
		teacher.addItem(new String("----------не выбрано----------"));
		teacher.setEnabled(false);
		subject.addItem(new String("----------не выбрано----------"));
		subject.setEnabled(false);
		lesson.addItem(new String("----------не выбрано----------"));
		lesson.setEnabled(false);
		auditory.addItem(new String("----------не выбрано----------"));
		auditory.setEnabled(false);

		listofspec = datdisp.getSpecialties();

		// here we add all spetialities to combobox,and if it's length >16 we
		// add only first 16 charapters
		for (int i = 0; i < listofspec.size(); i++)
			spetiality
					.addItem((Object) ((listofspec.get(i).getName().length() > 16) ? (listofspec
							.get(i).getName().substring(0, 15) + "..")
							: listofspec.get(i).getName()));

		year.setModel(new SpinnerNumberModel(1, 1, ncourse, 1)); // spinner
																	// parametrs
																	// at the
																	// begining:
		// chosen:1,minValue:1,maxValue:number of courses,step:1
		year.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (spetiality.getSelectedIndex() != 0) { // if spetiality is
															// chosen
					coursenum = (Integer) year.getValue(); // current course we
															// are working with
					listofsubj = curspec.getYears().get(coursenum - 1)
							.getSubjects(); // fill subjects
					subject.removeAllItems();
					subject.addItem(new String("----------не выбрано---------"));
					listofsubj = curspec.getYears().get(coursenum - 1)
							.getSubjects(); // update list of subjects

					for (int i = 0; i < listofsubj.size(); i++) {
						subject.addItem((Object) ((listofsubj.get(i).getName()
								.length() > 16) ? (listofsubj.get(i).getName()
								.substring(0, 15) + "..") : listofsubj.get(i)
								.getName())); // add them to the combobox
					}
				}
				subject.setEnabled(true); // turn on newt row and disable all
											// other
				lesson.setEnabled(false);
				teacher.setEnabled(false);
				auditory.setEnabled(false);
			}
		});

		initboxes(); // Add listeners to the comboboxes
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		layout.setHgap(100000); // this is made for placing elemets verticaly
		this.setLayout(layout);

		setbut = new JButton("set");
		setbut.setEnabled(false);
		setbut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				set();
			}
		});
		JPanel empty = new JPanel(); // empty places between comboboxes
		empty.setBackground(Color.gray);
		JPanel empty1 = new JPanel();
		empty1.setBackground(Color.gray);
		JPanel empty2 = new JPanel();
		empty2.setBackground(Color.gray);
		JPanel empty3 = new JPanel();
		empty3.setBackground(Color.gray);
		JPanel empty4 = new JPanel();
		empty4.setBackground(Color.gray);
		JPanel empty5 = new JPanel();
		empty5.setBackground(Color.gray);

		this.setBackground(Color.gray);
		this.add(new JLabel("—пециальность"));
		this.add(spetiality);
		this.add(empty);
		this.add(new JLabel(" урс"));
		this.add(year);
		this.add(empty1);
		this.add(new JLabel("ѕредмет"));
		this.add(subject);
		this.add(empty2);
		this.add(new JLabel("ѕара"));
		this.add(lesson);
		this.add(empty3);
		this.add(new JLabel("ѕреподаватель"));
		this.add(teacher);
		this.add(empty4);
		this.add(new JLabel("јудитори€"));
		this.add(auditory);
		this.add(empty5);
		this.add(setbut);
		this.setVisible(true);
		year.setVisible(true);
	}

	public void set() { // add element to the table
		if (flag) {
			tabbedPanel.addLessonToSelectedCell(curlesson);
		} else
			System.out.println("not enough data");
	}

	void initboxes() {
		subject.setEnabled(false);
		teacher.setEnabled(false);
		lesson.setEnabled(false);
		auditory.setEnabled(false);

		spetiality.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				flag = false;
				setbut.setEnabled(false);

				if (spetiality.getSelectedIndex() != 0) { // if spetiality is
															// chosen
					curspec = listofspec.get(spetiality.getSelectedIndex() - 1); // -1
																					// because
																					// of
																					// we
																					// added
																					// ----не
																					// выбрано------
					ncourse = curspec.getYears().size();
					year.setModel(new SpinnerNumberModel(coursenum, 1, ncourse,
							1)); // look higher
					listofsubj = curspec.getYears().get(coursenum - 1)
							.getSubjects(); // filling list of subjects
					subject.removeAllItems();
					subject.addItem(new String("----------не выбрано---------"));
					listofsubj = curspec.getYears().get(coursenum - 1)
							.getSubjects();
					for (int i = 0; i < listofsubj.size(); i++) {
						// if (!listofsubj.get(i).isFinished())
						subject.addItem((Object) ((listofsubj.get(i).getName()
								.length() > 16) ? (listofsubj.get(i).getName()
								.substring(0, 15) + "..") : listofsubj.get(i)
								.getName()));
					}
					subject.setEnabled(true);
					lesson.setEnabled(false);
					teacher.setEnabled(false);
					auditory.setEnabled(false);
				} else {
					subject.setEnabled(false);
					lesson.setEnabled(false);
					teacher.setEnabled(false);
					auditory.setEnabled(false);
				}
			}
		});
		subject.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (subject.getSelectedIndex() != 0) {
					flag = false;
					setbut.setEnabled(false);
					lesson.removeAllItems();
					lesson.addItem(new String("----------не выбрано----------"));
					if ((subject.getSelectedIndex() != 0)
							&& ((subject.getSelectedIndex() != -1))) {
						cursubj = listofsubj.get(subject.getSelectedIndex() - 1);
						List<Lesson> lesonarr = cursubj.getLessons();
						for (int i = 0; i < lesonarr.size(); i++)
							lesson.addItem((Object) ((lesonarr.get(i)
									.toString().length() > 16) ? (lesonarr
									.get(i).toString() // listof lessons
									.substring(0, 15) + "..") : lesonarr.get(i)
									.toString()));
					}
					lesson.setEnabled(true);
					teacher.setEnabled(false);
					auditory.setEnabled(false);
				} else {
					lesson.setEnabled(false);
					teacher.setEnabled(false);
					auditory.setEnabled(false);
				}
			}
		});

		lesson.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (lesson.getSelectedIndex() != 0)
					teacher.removeAllItems();
				teacher.addItem(new String("----------не выбрано----------"));
				if (lesson.getSelectedIndex() > 0) {
					curlesson = cursubj.getLessons().get(
							lesson.getSelectedIndex() - 1);
					List<Teacher> teacherarr = curlesson.getPossibleTeachers();
					for (int i = 0; i < teacherarr.size(); i++)
						teacher.addItem((Object) ((teacherarr.get(i).toString()
								.length() > 16) ? (teacherarr.get(i).toString()
								.substring(0, 15) + "..") : teacherarr.get(i)
								.toString()));
					flag = true;
					setbut.setEnabled(true);
					teacher.setEnabled(true);
				} else
					teacher.setEnabled(false);
			}
		});
		
		teacher.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (teacher.getSelectedIndex()>0)
				{
					curteacher=curlesson.getPossibleTeachers().get(teacher.getSelectedIndex()-1);
					try {
						curlesson.setTeacher(curteacher);
					} catch (CollisionException e) {
						//предоставить возм-ть выбрать:сдвинуть старое,уступить старому,игнор,пропустить но напомнить позже
						e.printStackTrace();
					}
					JDialog jd = new JDialog();
					jd.setUndecorated(true);
					jd.setSize(230, 200);
					jd.setLocation(600, 250);
					
					jd.add( new ColiisionDispatcher(jd,new CollisionException(curteacher,curlesson ),tabbedPanel.getParentFrame()));//предоставить возм-ть выбрать:сдвинуть старое,уступить старому,игнор,пропустить но напомнить позже
					jd.setVisible(true);
					
				}
			}
		});

	}

	// void update (){}
	public static void main(String[] args) throws Exception {
		JFrame fr = new JFrame();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Chooser2 ch = new Chooser2();
		fr.add(ch);
		ch.setSize(800, 600);
		fr.setSize(new Dimension(200, 600));
		fr.setVisible(true);
		//ch.setSpetiality(2);
		//ch.setYear(7);
		ch.setLesson(ch.datdisp.getSpecialties().get(0).getYears().get(0).getSubjects().get(0).getLessons().get(0));
		
	}

	public void connect(TabbedPanel tabbedPanel2) { // it is for making
													// connection between
													// chooser and tabbed panel
		this.tabbedPanel = tabbedPanel2;
	}

	public void setSpetiality(int index) {
		spetiality.setSelectedIndex(index + 1);
	}

	public void setYear(int year) {
		this.year.setValue((Integer) year);
	}

	private void setSubject(int index) {
		subject.setSelectedIndex(index+1);
	}

	public void setLesson(Lesson lesson) throws SQLException {
		List<Specialty> listofspec;
					listofspec=datdisp.getSpecialties();
		int spec=listofspec.indexOf(lesson.getParent().getParent().getParent().getSpecialty());
		Specialty curspec = listofspec.get(spec);
		
		setSpetiality(spec);
		int curyear=lesson.getParent().getParent().getParent().getYear();
		setYear(curyear);
		setSubject(curspec.getYears().get(curyear-1).getSubjects().indexOf(lesson.getParent().getParent()));
		this.lesson
				.setSelectedItem(((Object) ((lesson.toString().length() > 16) ?
						(lesson.toString().substring(0, 15) + "..") :
						lesson.toString())));
		
	}
	public Specialty getCurspec() {
		return curspec;
	}

	public Subject getCursubj() {
		return cursubj;
	}

	public Teacher getCurteacher() {
		return curteacher;
	}

	public Lesson getCurlesson() {
		return curlesson;
	}

	public Auditory getCuraudit() {
		return curaudit;
	}

	public int getCoursenum() {
		return coursenum;
	}
}