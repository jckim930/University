package org.university.software;

import java.io.Serializable;
import java.util.ArrayList;

import org.university.hardware.Department;
import org.university.people.Person;
import org.university.people.Professor;
import org.university.people.Student;

public abstract class Course implements Serializable {
	
	protected String name;
	protected int courseNumber;
	protected Department department;
	protected Professor professor;
	protected ArrayList<Person> studentRoster;
	protected int creditUnits;

	protected String[] week = {"Mon", "Tue", "Wed", "Thu", "Fri"};
	protected String[] slot = {"8:00am to 9:15am", "9:30am to 10:45am",
			"11:00am to 12:15pm", "12:30pm to 1:45pm",
			"2:00pm to 3:15pm", "3:30pm to 4:45pm"};
	
	public Course() {
		name = "unknown";
		courseNumber = 0;
		department = new Department();
		professor = new Professor();
		studentRoster = new ArrayList<Person>();
		creditUnits = 0;
	}
	
	//----------------------------GETTERS SETTERS---------------------------------------------
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(int courseNumber) {
		this.courseNumber = courseNumber;
	}

	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public ArrayList<Person> getStudentRoster() {
		return studentRoster;
	}
	
	public int getCreditUnits() {
		return creditUnits;
	}
	public void setCreditUnits(int creditUnits) {
		this.creditUnits = creditUnits;
	}
	
	//---------------------------------END OF GETTERS SETTERS-----------------------------------
	
	public void addStudentToRoster(Person student) {
		if (!studentRoster.contains(student)) {	// check if student is already in studentRoster ArrayList<Student>
				studentRoster.add(student);			// add student to the studentRoster
		}
	}
	
	public void dropStudentFromRoster(Person student) {
		if (studentRoster.contains(student)) {
			studentRoster.remove(student);
		}
	}
	
	public void printRoster() {
		if (!studentRoster.isEmpty()) {
			for (Person student : studentRoster) {
				System.out.println(student.getName());
			}
		}
		System.out.println();
	}
	//--------------------------ABSTRACT---------------------------
	public abstract boolean availableTo(Student aStudent);
}
