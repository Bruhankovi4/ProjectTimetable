package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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

public class Chooser extends JPanel {
	private static final long serialVersionUID = 1L;
	int ncourse = 5; // getNumber from source;
	JComboBox spetiality;
	JSpinner year = new JSpinner();
	JComboBox subject;
	JComboBox teacher;
	JComboBox lesson;
	JComboBox auditory;

	Specialty curSpec; // current spetiality
	Subject curSubj;
	Teacher curTeacher;
	Lesson curLesson;
	Auditory curAudit;
	List<Specialty> listOfSpec;
	DataDispatcher dataDisp;

	JButton setbut; // button that sets lesson to the table
	private boolean flag = false; // можно устанавливать значение в таблицу
	int courseNum = 1;
	private List<Subject> listofsubj;

	public Chooser() throws Exception {
		dataDisp = new DataDispatcher((byte) 2); // data from
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

		listOfSpec = dataDisp.getSpecialties();

		// here we add all spetialities to combobox,and if it's length >16 we
		// add only first 16 charapters
		for (int i = 0; i < listOfSpec.size(); i++)
			spetiality.addItem((Object) listOfSpec.get(i));

		year.setModel(new SpinnerNumberModel(1, 1, ncourse, 1)); // spinner
																	// parametrs
																	// at the
																	// begining:
		// chosen:1,minValue:1,maxValue:number of courses,step:1
		year.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (spetiality.getSelectedIndex() > 0) { // if spetiality is
															// chosen
					courseNum = (Integer) year.getValue(); // current course we
															// are working with
					listofsubj = curSpec.getYears().get(courseNum - 1)
							.getSubjects(); // fill subjects
					subject.removeAllItems();
					subject.addItem(new String("----------не выбрано---------"));
					listofsubj = curSpec.getYears().get(courseNum - 1)
							.getSubjects(); // update list of subjects

					for (int i = 0; i < listofsubj.size(); i++) {
						subject.addItem((Object) listofsubj.get(i));
					}
					subject.setEnabled(true); // turn on newt row and disable
												// all other
				} else {
					subject.setEnabled(false);
					curSubj = null;
				}
				lesson.setEnabled(false);
				teacher.setEnabled(false);
				auditory.setEnabled(false);
				curLesson = null;
				curTeacher = null;
				curAudit = null;
			}
		});

		initboxes(); // Add listeners to the comboboxes
		addRenderer();
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		layout.setHgap(100000); // this is made for placing elemets verticaly
		this.setLayout(layout);

		setbut = new JButton("ƒобавить >>");
		setbut.setEnabled(false);
		setbut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				set();
				// lesson.update(lesson.getGraphics());
				lesson.repaint();
				subject.repaint();
				// subject.update(subject.getGraphics());
				spetiality.repaint();
				// spetiality.update(spetiality.getGraphics());
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
		GUI.tabbedPanel.addLessonToSelectedCell(curLesson);
		GUI.tabbedPanel.repaint();
		lesson.setSelectedIndex(0);
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
					curSpec = (Specialty) (spetiality.getSelectedItem());
					GUI.tabbedPanel.switchToTab(spetiality.getSelectedIndex() - 1);
					ncourse = curSpec.getYears().size();
					year.setModel(new SpinnerNumberModel(courseNum, 1, ncourse,
							1)); // look higher
					listofsubj = curSpec.getYears().get(courseNum - 1)
							.getSubjects(); // filling list of subjects
					subject.removeAllItems();
					subject.addItem(new String("----------не выбрано---------"));

					for (int i = 0; i < listofsubj.size(); i++) {
						// if (!listofsubj.get(i).isFinished())
						subject.addItem((Object) (listofsubj.get(i)));

					}
					subject.setEnabled(true);
				} else {
					subject.setEnabled(false);
					curSubj = null;
					curSpec = null;
				}
				lesson.setEnabled(false);
				teacher.setEnabled(false);
				auditory.setEnabled(false);
				curAudit = null;
				curTeacher = null;
				curLesson = null;
				GUI.tabbedPanel.repaint();
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
					if (subject.getSelectedIndex() > 0) {
						curSubj = (Subject) subject.getSelectedItem();
						List<Lesson> lesonarr = curSubj.getLessons();
						for (int i = 0; i < lesonarr.size(); i++)
							lesson.addItem((Object) lesonarr.get(i));
					}
					lesson.setEnabled(true);
				} else {
					lesson.setEnabled(false);
					curSubj = null;
					curLesson = null;
				}
				teacher.setEnabled(false);
				auditory.setEnabled(false);
				curAudit = null;
				curTeacher = null;
				GUI.tabbedPanel.repaint();
			}
		});

		lesson.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (lesson.getSelectedIndex() != 0)
					teacher.removeAllItems();
				teacher.addItem(new String("----------не выбрано----------"));
				if (lesson.getSelectedIndex() > 0) {
					if (curLesson != (Lesson) lesson.getSelectedItem()) {
						curLesson = (Lesson) lesson.getSelectedItem();
						setbut.setEnabled(false);
					}

					// curLesson =(Lesson) lesson.getSelectedItem();
					List<Teacher> teacherarr = curLesson.getPossibleTeachers();
					for (int i = 0; i < teacherarr.size(); i++)
						teacher.addItem((Object) teacherarr.get(i));
					flag = true;
					setbut.setEnabled(true);
					teacher.setEnabled(true);
					if (curLesson.isFinished())
						setbut.setEnabled(false);
					else
						setbut.setEnabled(true);
				} else {
					teacher.setEnabled(false);
					curLesson = null;
					setbut.setEnabled(false);
				}
				curAudit = null;
				curTeacher = null;
				GUI.tabbedPanel.repaint();
			}
		});

		teacher.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (teacher.getSelectedIndex() > 0) {
					curTeacher = (Teacher) teacher.getSelectedItem();
					try {
						curLesson.setTeacher(curTeacher);
					} catch (CollisionException e) {
						// предоставить возм-ть выбрать:сдвинуть старое,уступить
						// старому,игнор,пропустить но напомнить позже
						// e.printStackTrace();
						e.skip(); // be careful: forever skip()
					}
					/*
					 * JDialog jd = new JDialog(); jd.setUndecorated(true);
					 * jd.setSize(230, 200); jd.setLocation(600, 250);
					 * 
					 * jd.add( new ColiisionDispatcher(jd,new
					 * CollisionException(curTeacher,curLesson
					 * ),GUI.tabbedPanel.getParentFrame()));//предоставить
					 * возм-ть выбрать:сдвинуть старое,уступить
					 * старому,игнор,пропустить но напомнить позже
					 * jd.setVisible(true);
					 */

				}
				GUI.tabbedPanel.repaint();
			}
		});

	}

	// void update (){}
	/*
	 * public static void main(String[] args) throws Exception { JFrame fr = new
	 * JFrame(); fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); Chooser ch =
	 * new Chooser(); fr.add(ch); ch.setSize(800, 600); fr.setSize(new
	 * Dimension(200, 600)); fr.setVisible(true); //ch.setSpetiality(2);
	 * //ch.setYear(7);
	 * ch.setLesson(ch.datdisp.getSpecialties().get(0).getYears(
	 * ).get(0).getSubjects().get(0).getLessons().get(0));
	 * 
	 * }
	 */
	private void addRenderer() {
		spetiality.setRenderer(new ComboboxRenderer(spetiality.getRenderer()));
		subject.setRenderer(new ComboboxRenderer(subject.getRenderer()));
		lesson.setRenderer(new ComboboxRenderer(lesson.getRenderer()));
	}

	public void setSpetiality(int index) {
		spetiality.setSelectedIndex(index + 1);
	}

	public void setYear(int year) {
		this.year.setValue((Integer) year);
	}

	private void setSubject(int index) {
		subject.setSelectedIndex(index + 1);
	}

	public void setLesson(Lesson lesson) throws SQLException {
		List<Specialty> listofspec;
		listofspec = dataDisp.getSpecialties();
		int spec = listofspec.indexOf(lesson.getParent().getParent()
				.getParent().getSpecialty());
		Specialty curspec = listofspec.get(spec);

		setSpetiality(spec);
		int curyear = lesson.getParent().getParent().getParent().getYear();
		setYear(curyear);
		setSubject(curspec.getYears().get(curyear - 1).getSubjects()
				.indexOf(lesson.getParent().getParent()));
		this.lesson.setSelectedItem(lesson);
	}

	public Specialty getCurSpec() {
		return curSpec;
	}

	public Subject getCurSubj() {
		return curSubj;
	}

	public Teacher getCurTeacher() {
		return curTeacher;
	}

	public Lesson getCurLesson() {
		return curLesson;
	}

	public Auditory getCurAudit() {
		return curAudit;
	}

	public int getCourseNum() {
		return courseNum;
	}
}