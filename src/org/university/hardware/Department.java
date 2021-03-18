package org.university.hardware;


import java.io.Serializable;
import java.util.ArrayList;

import org.university.people.Professor;
import org.university.people.Staff;
import org.university.people.Student;
import org.university.people.*;
import org.university.software.CampusCourse;
import org.university.software.OnlineCourse;

public class Department implements Serializable {
	private String departmentName;
	private ArrayList<CampusCourse> campusCourseList;
	private ArrayList<OnlineCourse> onlineCourseList;
	private ArrayList<Student> studentList;
	private ArrayList<Staff> staffList;
	private ArrayList<Professor> professorList;
	
	public Department() {
		departmentName = "unknown";
		campusCourseList = new ArrayList<CampusCourse>();
		onlineCourseList = new ArrayList<OnlineCourse>();
		studentList = new ArrayList<Student>();
		professorList = new ArrayList<Professor>();
		staffList = new ArrayList<Staff>();
	}
	
	// GETTERS AND SETTERS
	// DEPARTMENT NAME
	public void setDepartmentName(String name) {
		departmentName = name;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	
	public ArrayList<CampusCourse> getCampusCourseList() {
		return campusCourseList;
	}
	public ArrayList<OnlineCourse> getOnlineCourseList() {
		return onlineCourseList;
	}
	
	//----------------END OF GETTERS AND SETTERS----------------------
	
	// STUDENT
	public void addStudent(Student student) {
		if (!studentList.contains(student)) {
			if (student.getDepartment() != this) {
				student.setDepartment(this);
			}
			studentList.add(student);
		}
	}
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	public void printStudentList() {
		if (!studentList.isEmpty()) {
			for (Student student : studentList)
				System.out.println(student.getName());
		}
	}
	
	// COURSE
	public void addCourse(CampusCourse cCourse) {
		if (!campusCourseList.contains(cCourse)) {
			if (cCourse.getDepartment() != this) {
				cCourse.setDepartment(this);
			}
			campusCourseList.add(cCourse);
		}
	}
	public void addCourse(OnlineCourse oCourse) {
		if (!onlineCourseList.contains(oCourse)) {
			if (oCourse.getDepartment() != this) {
				oCourse.setDepartment(this);
			}
			onlineCourseList.add(oCourse);
		}
	}
	
	public void printCourseList() {
		if (!campusCourseList.isEmpty()) {
			for (CampusCourse course : campusCourseList)
				System.out.println(departmentName + course.getCourseNumber() + 
						" " + course.getName());
		}
		if (!onlineCourseList.isEmpty()) {
			for (OnlineCourse course : onlineCourseList)
				System.out.println(departmentName + course.getCourseNumber() +
						" " + course.getName());
		}
	}
	
	// PROFESSOR
	public ArrayList<Professor> getProfessorList() {
		return professorList;
	}
	public void addProfessor(Professor professor) {
		if (!professorList.contains(professor))
			professorList.add(professor);
	}
	public void printProfessorList() {
		if (!professorList.isEmpty()) {
			for (Professor professor : professorList)
				System.out.println(professor.getName());
		}
	}
	
	// STAFF
	public ArrayList<Staff> getStaffList() {
		return staffList;
	}
	public void addStaff(Staff staff) {
		if (!staffList.contains(staff))
			staffList.add(staff);
	}
	public void printStaffList() {
		if (!staffList.isEmpty()) {
			for (Staff staff : staffList) 
				System.out.println(staff.getName());
		}
	}
	
	public void printAllSchedules() {
		System.out.println();
		System.out.println("Printing Professor Schedules:");
		System.out.println();
		if (!professorList.isEmpty()) {
			for (Professor professor : professorList) {
				System.out.println("The Schedule for Prof. " + professor.getName() + ":");
				professor.printSchedule();
				System.out.println();
			}
		} else 
			System.out.println();
		
		System.out.println("Printing Student Schedules:");
		System.out.println();
		if (!studentList.isEmpty()) {
			for (Student student : studentList) {
				System.out.println("The schedule for Student " + student.getName() + ":");
				student.printSchedule();
				System.out.println();
			}
		} else
			System.out.println();
			
		System.out.println("Printing Staff Schedules:");
		System.out.println();
		if (!staffList.isEmpty()) {
			for (Staff staff : staffList) {
				System.out.println("The schedule for Employee " + staff.getName() + ":");
				staff.printSchedule();
				System.out.println();
			}
		} else
			System.out.println();
	}
	
	public void printStaffEarnings() {
		if (!staffList.isEmpty()) {
			for (Staff staff : staffList) {
				System.out.println("Staff: " + staff.getName() + " earns " + staff.earns() + " this month");
			}
			System.out.println();
		}
	}
	
	public void printAllRosters() {
		if (!campusCourseList.isEmpty()) {
			for (CampusCourse cCourse : campusCourseList) {
				System.out.println("The roster for course " + cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber());
				cCourse.printRoster();
			}
		}
		/*
		if (!onlineCourseList.isEmpty()) {
			for (OnlineCourse oCourse : onlineCourseList) {
				System.out.println("The roster for course " + oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber());
				oCourse.printRoster();
			}
		}*/
		
	}
}
