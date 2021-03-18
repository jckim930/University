package org.university.people;

import java.io.Serializable;
import java.util.Collections;

import org.university.software.CampusCourse;
import org.university.software.OnlineCourse;

public class Professor extends Employee implements Serializable {
	private double salary;
	
	public Professor() {
		salary = 0.;
	}
	//-------------------------------------GETTERS SETTERS---------------------------------------------------
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public double getSalary() {
		return salary;
	}
	//-------------------------------------END OF GETTERS SETTERS------------------------------------------------
	
	// -------------------------------ADD DROP COURSES----------------------------
	public void addCourse(CampusCourse cCourse) {
		if (!cCourse.getProfessor().getName().equals("unknown")) {	// if a professor is already assigned to the course
			System.out.println("The professor " + name + " cannot be assigned to this campus course because professor " +
					cCourse.getProfessor().getName() + " is already assigned to the course " +
					cCourse.getName() + ".");
		}
		else if (!detectConflict(cCourse)) {			// if no conflict
			campusCourseList.add(cCourse);					// add course to courseList ArrayList<Course>
			schedule.addAll(cCourse.getSchedule());	// add course's schedule ArrayList<Integer> to Professor's own
			cCourse.setProfessor(this);				// set the course's professor to this
			addToMap(cCourse);
			Collections.sort(schedule);				// sort schedule
		}
	}
	
	public void addCourse(OnlineCourse oCourse) {
		if (!oCourse.getProfessor().getName().equals("unknown")) {	// if a professor is already assigned to the course
			System.out.println("The professor cannot be assigned to this online course because professor " +
					oCourse.getProfessor().getName() + " is already assigned to the online course " +
					oCourse.getName() + ".");
		}
		else {
			onlineCourseList.add(oCourse);
			oCourse.setProfessor(this);
		}
	}
	
	public void dropCourse(CampusCourse cCourse) {
		if (campusCourseList.contains(cCourse)) {
			campusCourseList.remove(cCourse);			// remove from courseList Array<Course>
			removeFromMap(cCourse);				// call fnc to remove map[courseCode:Course] pairs
			removeFromSchedule(cCourse);
			cCourse.setProfessor(new Professor());
		} else {								// if the course isn't in the Professor's courseList ArrayList<Course>
			System.out.println("The course " + cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() +
					" could not be dropped because " + getName() + " is not teaching " + 
					cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() +".");
		}
	}
	
	public void dropCourse(OnlineCourse oCourse) {
		if (onlineCourseList.contains(oCourse)) {
			onlineCourseList.remove(oCourse);			// remove from courseList Array<Course>
			oCourse.setProfessor(new Professor());
		} else {								// if the course isn't in the Professor's courseList ArrayList<Course>
			System.out.println("The course " + oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() +
					" could not be dropped because " + getName() + " is not teaching " + 
					oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() +".");
		}
	}
	
	public void removeFromSchedule(CampusCourse cCourse) {
		for (Integer courseCode : cCourse.getSchedule()) {	// loop through every Integer courseCode in schedule ArrayList<Integer>
			schedule.remove(courseCode);						// remove key:value pair
		}
	}
	//-------------------------------END OF ADD DROP COURSES-----------------------------

	public void raise(double percent) {
		salary += salary * (percent / 100);
	}
	
	public double earns() {
		return salary/26;
	}
}
