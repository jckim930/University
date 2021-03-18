package org.university.software;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.university.hardware.Classroom;
import org.university.hardware.Department;
import org.university.people.Professor;
import org.university.people.Staff;
import org.university.people.Student;

public class University implements Serializable {
	private String name;
	private ArrayList<Student> studentList;
	private ArrayList<Professor> professorList;
	private ArrayList<Staff> staffList;
	public ArrayList<Department> departmentList;
	public ArrayList<Course> courseList;
	public ArrayList<Classroom> classroomList;
	
	
	public University() {
		name = "unknown";
		studentList = new ArrayList<Student>();
		professorList = new ArrayList<Professor>();
		staffList = new ArrayList<Staff>();
		departmentList = new ArrayList<Department>();
		courseList = new ArrayList<Course>();
		classroomList = new ArrayList<Classroom>();		
	}
	
	University(University uni) {
		name = uni.name;
		studentList = uni.getStudentList();
		professorList = uni.professorList;
		staffList = uni.staffList;
		departmentList = uni.getDepartmentList();
		courseList = uni.courseList;
		classroomList = uni.classroomList;
	}
	
	public ArrayList<Department> getDepartmentList() {
		return departmentList;
	}
	
	// GETTERS AND SETTERS
	// NAME
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	// ----------------------END OF GETTERS SETTERS--------------------------------------
	
	// STUDENT STUFF
	public void addStudent(Student student) {
		if (!studentList.contains(student)) {
			studentList.add(student);
		}
	}
	public void printStudentList() {
		if (!departmentList.isEmpty()) {
			for (Department department : departmentList) {
				for (Student student : department.getStudentList()) {
					System.out.println(student.getName());
				}
			}
		}
	}
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	
	// PROFESSOR
	public void addProfessor(Professor professor) {
		if (!professorList.contains(professor)) {
			professorList.add(professor);
		}
	}
	public void printProfessorList() {
		if (!departmentList.isEmpty()) {
			for (Department department : departmentList) {
				System.out.println("The professor list for department " + department.getDepartmentName());
				for (Professor professor : department.getProfessorList())
					System.out.println(professor.getName());
				System.out.println();
			}
		}
	}
	
	// STAFF
	public void addStaff(Staff staff) {
		if (!staffList.contains(staff))
			staffList.add(staff);
	}
	public void printStaffList() {
		if (!departmentList.isEmpty()) {
			for (Department department : departmentList) {
				for (Staff staff : department.getStaffList())
					System.out.println(staff.getName());
			}
		}
	}
	
	// DEPARTMENT
	public void addDepartment(Department department) {
		if (!departmentList.contains(department)) {
			departmentList.add(department);
			for (Student student : department.getStudentList()) {
				if (!studentList.contains(student)) {
					studentList.add(student);
				}
			}
		}
	}
	public void printDepartmentList() {
		if (!departmentList.isEmpty()) {
			for (Department department : departmentList) {
				System.out.println(department.getDepartmentName());
			}
		}
	}
	
	// COURSE
	public void addCourse(Course course) {
		courseList.add(course);
	}
	public void printCourseList() {
		for (Department department : departmentList) {
			System.out.println("The course list for department " + department.getDepartmentName());
			for (CampusCourse course : department.getCampusCourseList()) {
				System.out.println(department.getDepartmentName() + course.getCourseNumber()+ " " + course.getName());
			}
			for (OnlineCourse course : department.getOnlineCourseList()) {
				System.out.println(department.getDepartmentName() + course.getCourseNumber()+ " " + course.getName());
			}
			System.out.println();
		}
	}
	
	public void printClassroomList() {
		if (!classroomList.isEmpty()) {
			for (Classroom classroom : classroomList) {
				System.out.println(classroom.getRoomNumber());
			}
		}
	}
	
	public void printClassroomSchedule() {
		if (!classroomList.isEmpty()) {
			for (Classroom classroom : classroomList) {
				classroom.printSchedule();
				System.out.println();
			}
		}
	}
	
	public void printDepartmentSchedules() {
		if (!departmentList.isEmpty()) {
			
			for (Department department : departmentList) {
				System.out.println("Department " + department.getDepartmentName());
				department.printAllSchedules();
				//System.out.println();
			}
		} 
	}
	
	public void printDepartmentEarnings() {
		if (!departmentList.isEmpty()) {
			for (Department department : departmentList) {
				department.printStaffEarnings();
			}
		} 
	}
	
	public void printDepartmentRosters() {
		if (!departmentList.isEmpty()) {
			for (Department department : departmentList) {
				department.printAllRosters();
			}
		} 
	}
	
	public void printAll() {
		System.out.println();
		System.out.println("List of departments:");
		printDepartmentList();
		System.out.println();
		
		System.out.println("Classroom list:");
		printClassroomList();
		System.out.println();
		
		printProfessorList();
		
		printCourseList();
		
		printClassroomSchedule();
		
		printAllDepartmentInfo();
		
	}
	
	void printAllDepartmentInfo() {
		if (!departmentList.isEmpty()) {
			for (Department department : departmentList) {
				System.out.println("Department " + department.getDepartmentName());
				department.printAllSchedules();
				department.printStaffEarnings();
				System.out.println("The rosters for courses offered by " + department.getDepartmentName());
				System.out.println();
				department.printAllRosters();
			}
		} 
	}
	
	public static void saveData(University u) {
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut = null;
		
		try {
			fileOut = new FileOutputStream("university.ser");
			objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(u);
			objOut.close();
			fileOut.close();
			
		}
		catch(IOException i) {
			i.printStackTrace();
		}
	}
	public static University loadData() {
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		
		University uni = null;
		
		try {
			fileIn = new FileInputStream("university.ser");
			objIn = new ObjectInputStream(fileIn);
			
			uni = (University) objIn.readObject();
			fileIn.close();
			objIn.close();
		}
		catch (IOException i) {
			i.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return uni;
	}
}
