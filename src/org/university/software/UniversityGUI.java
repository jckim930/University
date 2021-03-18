package org.university.software;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

import javax.swing.*;

import org.university.hardware.Department;
import org.university.people.*;

/* Demo by Lahiru Ariyananda and Peter Hall
   Note :  this is very simple program  used as a  demo on  Serilizing and GUIs. 
   It does not test for faults or errors (  which needs to be implemented) 
 */



public class UniversityGUI extends JFrame 
{ 
	public University uni1;
	private JMenuBar menuBar;		//the horizontal container
	private JMenu fileMenu;
	private JMenu adminMenu;		
	private JMenu studentMenu;
	//-------------------------------------
	private JMenuItem fileSave;
	private JMenuItem fileLoad;
	private JMenuItem fileExit;
	
	private JMenuItem studentAddCourse;
	private JMenuItem studentDropCourse;
	private JMenuItem studentPrintSchedule;
	
	private JMenuItem adminPrintAll;
	//-----------------------------------
	//private JRadioButton radio2;
	//private JPanel test;
	
	
	public UniversityGUI(String windowTitle, University uni) 
	{
		super(windowTitle);

		uni1 = new University(uni);
		uni1 = uni;
		setSize(300, 150);
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(new JLabel("<HTML><center>Welcome to the University." +
				"<BR>Choose an action from the above menus.</center></HTML>"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildGUI();	
		setVisible(true);
	}
	
	public University getUniversity() {
		return uni1;
	}
	
	public void buildGUI() 
	{
		menuBar = new JMenuBar();
     	
		// Employee Student Menu
		
		fileMenu = new JMenu("File");
		studentMenu = new JMenu("Students");
		adminMenu = new JMenu("Administrators");
		//----------------------------------------------FILE------------------------------------------------
		fileSave = new JMenuItem("Save");
		fileLoad = new JMenuItem("Load");
		fileExit = new JMenuItem("Exit");
		
		fileSave.addActionListener(new MenuListener());
		fileLoad.addActionListener(new MenuListener());
		fileExit.addActionListener(new MenuListener());
		
		fileMenu.add(fileSave);
		fileMenu.add(fileLoad);
		fileMenu.add(fileExit);
		
		//---------------------------------------------STUDENT-------------------------------------------------
		studentAddCourse = new JMenuItem("Add Course");
		studentDropCourse = new JMenuItem("Drop Course");
		studentPrintSchedule = new JMenuItem("Print Schedule");
		
		studentAddCourse.addActionListener(new MenuListener());
		studentDropCourse.addActionListener(new MenuListener());
		studentPrintSchedule.addActionListener(new MenuListener());
		
		studentMenu.add(studentAddCourse);
		studentMenu.add(studentDropCourse);
		studentMenu.add(studentPrintSchedule);
		
		//----------------------------------------------ADMINISTRATORS-----------------------------------
		adminPrintAll = new JMenuItem("Print All Info");

		adminPrintAll.addActionListener(new MenuListener());		//The innerclass is implementing ActionListener, so it can take action when the JMenuItem is clicked.
	    
	    adminMenu.add(adminPrintAll);
		
		menuBar.add(fileMenu);
	    menuBar.add(adminMenu);
	    menuBar.add(studentMenu);
	    
		setJMenuBar(menuBar);
		
	}
	/*
	private class ButtonListener implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent e) {
			//JOptionPane.showMessageDialog(null, "button 2 pressed", "button pressed", JOptionPane.PLAIN_MESSAGE);
			JRadioButton source = (JRadioButton)(e.getSource());
			System.out.println(source);
			if (source.equals(radio2)) {
				JOptionPane.showMessageDialog(null, "button 2 pressed", "button pressed", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}*/
	

	
	private class MenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) //this is the method MenuListener must implement, as it comes from the ActionListener interface.
		{
			JMenuItem source = (JMenuItem)(e.getSource());
			
			if (source.equals(fileSave))
			{
				handleFileSave();
			}
			else if (source.equals(fileLoad))
			{
				handleFileLoad();
			} 
			else if (source.equals(fileExit)) {
				handleFileExit();
			}
			
			else if(source.equals(studentAddCourse))
			{
				handleStudentAddCourse();	
			}
			else if(source.equals(studentDropCourse))
			{
				handleStudentDropCourse();
			}
			else if (source.equals(studentPrintSchedule)) {
				handleStudentPrintSchedule();
			}
			
			else if (source.equals(adminPrintAll)) {
				handleAdminPrintAll();
			}
		}
		
		public void handleFileSave() 
		{
			University.saveData(uni1);
		}
		
		public void handleFileLoad()
		{
			uni1 = University.loadData();
		}
		
		public void handleFileExit() {
			System.exit(0);
		}
		
		public void handleAdminPrintAll() {
			if ( uni1 != null) {  
				JTextArea textArea = new JTextArea(40, 70); 
				PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
				System.setOut(printStream);
				System.setErr(printStream);
				textArea.setEditable(false);
				
				JFrame window = new JFrame("ScrollPane");
				window.setSize(800, 800);
				window.setLayout(new FlowLayout());
				window.setVisible(true); 
				
				JScrollPane scrollPane = new JScrollPane(textArea);
				scrollPane.setSize(45, 90);
				window.add(scrollPane);
				uni1.printAll();
			} 
			else {
				JOptionPane.showMessageDialog(null, 
					"No University", 
					"Error", 
					JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		public void handleStudentAddCourse() {
			if ( uni1 != null) {

				JPanel window = new JPanel();
				window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
				
				JLabel studentLabel = new JLabel("Student Name:", SwingConstants.CENTER);
				JTextField student = new JTextField();
				JLabel deptLabel = new JLabel("Department:", SwingConstants.CENTER);
				JTextField dept = new JTextField("");
				JLabel courseLabel = new JLabel("Course #:", SwingConstants.CENTER);
				JTextField course = new JTextField("");
				
				window.add(studentLabel);
				window.add(student);
				window.add(deptLabel);
				window.add(dept);
				window.add(courseLabel);
				window.add(course);
				window.setVisible(true);
				
				int input = JOptionPane.showConfirmDialog(null, window, "Add Course", JOptionPane.OK_CANCEL_OPTION);
				
				if (input == 0) {
					String studentName = student.getText();
					String deptName = dept.getText();
					String courseNum = course.getText();
					Student studentObj = null;
					Department deptObj = null;
					CampusCourse cCourseObj = null;
					OnlineCourse oCourseObj = null;
					
					studentObj = getStudent(studentName);
					deptObj = getDepartment(deptName);
					String courseType;
					
					if (studentObj != null) {
						if (deptObj != null) {
							courseType = isCourse(courseNum, deptObj);
							JTextArea textArea = new JTextArea(40, 70); 
							PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
							System.setOut(printStream);
							System.setErr(printStream);
							textArea.setEditable(false);
							String message;
							String courseName = "";
							
							if (!courseType.equals("none")) {
								if (courseType.equals("campus")) {
									studentObj.addCourse(getCampusCourse(courseNum, deptObj));
									courseName = getCampusCourse(courseNum, deptObj).getName();
								}
								else if (courseType.equals("online")) {
									studentObj.addCourse(getOnlineCourse(courseNum, deptObj));
									courseName = getOnlineCourse(courseNum, deptObj).getName();
								}
								message = textArea.getText();
								if (!message.isBlank())
									JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.PLAIN_MESSAGE);
								else 
									JOptionPane.showMessageDialog(null, "Success you have added " + courseName, "Success", JOptionPane.PLAIN_MESSAGE);
							}
							else { // courseType = "none"
								handleCourseNotExist(courseNum, deptName);
							}
						}
						else { // deptObj == null
							handleDeptNotExist(deptName);
						}
					}
					else { // studentObj == null
						handleStudentNotExistAdd(studentName);
					}
				}	 
			} 
			else { // clicked on ok
				JOptionPane.showMessageDialog(null, 
					"No University", 
					"Error", 
					JOptionPane.PLAIN_MESSAGE);
			}
		}
		

		
		public void handleStudentPrintSchedule() {
			if ( uni1 != null) {
				String studentName;
				studentName = JOptionPane.showInputDialog(null, "Student Name:", "Print Student Schedule", JOptionPane.PLAIN_MESSAGE);
				Student student;
				student = getStudent(studentName);
				if (studentName != null) {
					if (student == null)
						handleStudentNotExistPrint(studentName);
					else {
						JTextArea textArea = new JTextArea(30, 40); 
						PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
						System.setOut(printStream);
						System.setErr(printStream);
						textArea.setEditable(false);
						student.printSchedule();
						String n = textArea.getText();
						JOptionPane.showMessageDialog(null, n, "Student " + student.getName() + "'s Schedule", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
		
		public void handleStudentNotExistAdd(String name) {
			JOptionPane.showMessageDialog(null, "Student \"" + name + "\" doesn't exist.", "Error adding student to course", JOptionPane.PLAIN_MESSAGE);
		}
		
		public void handleStudentNotExistPrint(String name) {
			JOptionPane.showMessageDialog(null, "Student \"" + name + "\" doesn't exist.", "Error printing student schedule", JOptionPane.PLAIN_MESSAGE);
		}
		
		public boolean isStudent(String name) {
			for (Department d : uni1.getDepartmentList()) {
				for (Student s : d.getStudentList()) {
					if (s.getName().equals(name)) {
						return true;
					}
				}
			}
			return false;
		}
		
		public boolean isDept(String name) {
			for (Department d : uni1.getDepartmentList()) {
				if (d.getDepartmentName().equals(name))
					return true;
			}
			return false;
		}
		
		public Student getStudent(String name) {
			Student retStudent = null;
			for (Department d : uni1.getDepartmentList()) {
				for (Student s : d.getStudentList()) {
					if (s.getName().equals(name)) {
						return s;
					}
				}
			}
			return retStudent;
		}
		
		public Department getDepartment(String name) {
			Department retDept = null;
			for (Department d : uni1.getDepartmentList()) {
				if (d.getDepartmentName().equals(name))
					retDept = d;
			}
			return retDept;
		}
		
		public String isCourse(String courseNum, Department dept) {
			for (CampusCourse c : dept.getCampusCourseList()) {
				if (Integer.toString(c.getCourseNumber()).equals(courseNum)) {
					return "campus";
				}
			}
			for (OnlineCourse o : dept.getOnlineCourseList()) {
				if (Integer.toString(o.getCourseNumber()).equals(courseNum)) {
					return "online";
				}
			}
			return "none";
		}
		public CampusCourse getCampusCourse(String courseNum, Department dept) {
			CampusCourse retCourse = null;
			for (CampusCourse c : dept.getCampusCourseList()) {
				if (Integer.toString(c.getCourseNumber()).equals(courseNum)) {
					return c;
				}
			}
			return retCourse;
		}
		
		public OnlineCourse getOnlineCourse(String courseNum, Department dept) {
			OnlineCourse retCourse = null;
			for (OnlineCourse o : dept.getOnlineCourseList()) {
				if (Integer.toString(o.getCourseNumber()).equals(courseNum)) {
					return o;
				}
			}
			return retCourse;
		}
		
		public void handleCourseNotExist(String courseNum, String deptName) {
			JOptionPane.showMessageDialog(null, "Course: " + deptName + courseNum + " doesn't exist."
					, "Error: Non-existent Course", JOptionPane.PLAIN_MESSAGE);
		}
		
		public void handleDeptNotExist(String deptName) {
			JOptionPane.showMessageDialog(null, "Deptartment: " + deptName + " doesn't exist."
					, "Error: Non-existent Department", JOptionPane.PLAIN_MESSAGE);
		}
		
		public void handleStudentDropCourse() {
			if ( uni1 != null) {

				JPanel window = new JPanel();
				window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
				
				JLabel studentLabel = new JLabel("Student Name:", SwingConstants.CENTER);
				JTextField student = new JTextField();
				JLabel deptLabel = new JLabel("Department:", SwingConstants.CENTER);
				JTextField dept = new JTextField("");
				JLabel courseLabel = new JLabel("Course #:", SwingConstants.CENTER);
				JTextField course = new JTextField("");
				
				window.add(studentLabel);
				window.add(student);
				window.add(deptLabel);
				window.add(dept);
				window.add(courseLabel);
				window.add(course);
				
				window.setVisible(true);
				
				int input = JOptionPane.showConfirmDialog(null, window, "Drop Course", JOptionPane.OK_CANCEL_OPTION);
				
				if (input == 0) {
					String studentName = student.getText();
					String deptName = dept.getText();
					String courseNum = course.getText();
					Student studentObj = null;
					Department deptObj = null;
					CampusCourse cCourseObj = null;
					OnlineCourse oCourseObj = null;
					
					studentObj = getStudent(studentName);
					deptObj = getDepartment(deptName);
					String courseType;
					
					if (studentObj != null) {
						if (deptObj != null) {
							courseType = isCourse(courseNum, deptObj);
							JTextArea textArea = new JTextArea(40, 70); 
							PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
							System.setOut(printStream);
							System.setErr(printStream);
							textArea.setEditable(false);
							String message;
							String courseName = "";
							
							if (!courseType.equals("none")) {
								if (courseType.equals("campus")) {
									studentObj.dropCourse(getCampusCourse(courseNum, deptObj));
									courseName = getCampusCourse(courseNum, deptObj).getName();
								}
								else if (courseType.equals("online")) {
									studentObj.dropCourse(getOnlineCourse(courseNum, deptObj));
									courseName = getOnlineCourse(courseNum, deptObj).getName();
								}
								message = textArea.getText();
								if (!message.isBlank())
									JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.PLAIN_MESSAGE);
								else 
									JOptionPane.showMessageDialog(null, "Success you have Dropped " + courseName, "Success", JOptionPane.PLAIN_MESSAGE);
							}
							else { // courseType = "none"
								handleCourseNotExist(courseNum, deptName);
							}
						}
						else { // deptObj == null
							handleDeptNotExist(deptName);
						}
					}
					else { // studentObj == null
						handleStudentNotExistAdd(studentName);
					}
				}	 
			} 
			else { // clicked on ok
				JOptionPane.showMessageDialog(null, 
					"No University", 
					"Error", 
					JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	

	
}
