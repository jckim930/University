package org.university.people;

import java.io.Serializable;
import java.util.Collections;

import org.university.hardware.Department;
import org.university.software.CampusCourse;
import org.university.software.OnlineCourse;

public class Student extends Person implements Serializable {
	private int completedUnits;
	private int requiredCredits;
	private int tuitionFee;
	private int enrolledCredits;
	private int enrolledCampusCredits;
	private int enrolledOnlineCredits;

	public Student() {
		department = new Department();
		completedUnits = 0;
		requiredCredits = 0;
		tuitionFee = 0;
		enrolledCredits = 0;
		enrolledCampusCredits = 0;
		enrolledOnlineCredits = 0;
	}

	// ------------------------------GETTERS, SETTERS--------------------------

	public int getCompletedUnits() {
		return completedUnits;
	}
	public void setCompletedUnits(int completedUnits) {
		this.completedUnits = completedUnits;
	}

	public int getRequiredCredits() {
		return requiredCredits;
	}
	public void setRequiredCredits(int requiredCredits) {
		this.requiredCredits = requiredCredits;
	}
	
	public int getEnrolledCredits() {
		return enrolledCredits;
	}
	public int getEnrolledCampusCredits() {
		return enrolledCampusCredits;
	}
	public int getEnrolledOnlineCredits() {
		return enrolledOnlineCredits;
	}
	
	public int getTuitionFee() {
		return tuitionFee;
	}


	// --------------------------END OF GETTERS, SETTERS--------------------------------------
	
	//----------------------------IMPLEMENTING ABSTRACT ADD COURSES------------------------------
	public void addCourse(CampusCourse cCourse) {
		if (cCourse.availableTo(this)) {				// check if this course is available to the Student
			if (!campusCourseList.contains(cCourse)) { // check if course is already in courseList
				if (!detectConflict(cCourse)) { // if No timing conflict Proceed 
					campusCourseList.add(cCourse); 					// add to courseList ArrayList<Course>
					enrolledCredits += cCourse.getCreditUnits();
					enrolledCampusCredits += cCourse.getCreditUnits();
					tuitionFee += (300 * cCourse.getCreditUnits());
					schedule.addAll(cCourse.getSchedule());		// add course's 3 digit Integer codes to your own in schedule ArrayList<Integer>
					Collections.sort(schedule);					// sort your schedule low to high	
					this.addToMap(cCourse);							// add course to TreeMap<Integer, ArrayList<Course>>
					
					if (!cCourse.getStudentRoster().contains(this)) { // if course's roster doesn't already have you
						cCourse.addStudentToRoster(this);					// call course's addStudent fnc with this Student
					}
				}
			}
		}
		else {
			System.out.println(name + " can't add Campus Course " + cCourse.getDepartment().getDepartmentName() + 
					cCourse.getCourseNumber() + " " + cCourse.getName() + 
					". Because this Campus course has enough student.");
		}
	}
	
	public void addCourse(OnlineCourse oCourse) {
		if (!onlineCourseList.contains(oCourse)) {
			if (oCourse.availableTo(this)) {
				onlineCourseList.add(oCourse);
				oCourse.addStudentToRoster(this);
				enrolledCredits += oCourse.getCreditUnits();
				enrolledOnlineCredits += oCourse.getCreditUnits();
				if (oCourse.getCreditUnits() == 3) 
					tuitionFee += 2000;
				else
					tuitionFee += 3000;
			}
			else {
				System.out.println("Student " + name + " has only " + enrolledCampusCredits + " on campus credits enrolled. "
						+ "Should have at least 6 credits registered before registering online courses.");
				System.out.println(name + " can't add online Course " + oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + " " +oCourse.getName() +
						". Because this student doesn't have enough Campus course credit.");
			}
		}
		else {
			System.out.println("Student " + name + " has only " + enrolledCampusCredits + 
					" campus credits enrolled. Should have at least 6 credits registered before registering online courses.");
			System.out.println(name + " can't add online Course " + oCourse.getDepartment().getDepartmentName() + 
					oCourse.getCourseNumber() + " " + oCourse.getName() + 
					". Because this student doesn't have enough Campus course credit.");
		}
	}
	
	public void dropCourse(CampusCourse cCourse) {
		if (campusCourseList.contains(cCourse)) {
			boolean cCreditsLessThan6 = (enrolledCampusCredits - cCourse.getCreditUnits()) < 6;
			if (!onlineCourseList.isEmpty() && cCreditsLessThan6) {
				System.out.println(name + " can't drop this CampusCourse, because this student "
						+ "doesn't have enough campus course credit to hold the online course");
			}
			else {	// Successful drop cCourse
					campusCourseList.remove(cCourse);			// remove from courseList Array<Course>
					enrolledCredits -= cCourse.getCreditUnits();
					enrolledCampusCredits -= cCourse.getCreditUnits();
					tuitionFee -= (300 * cCourse.getCreditUnits());
					removeFromMap(cCourse);				// call fnc to remove map[courseCode:Course] pairs
					removeFromSchedule(cCourse);
					cCourse.dropStudentFromRoster(this);
			} 
		}
		else {	// if the course isn't in the student's courseList ArrayList<Course>
			System.out.println("The course " + cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() +
					" could not be dropped because " + getName() + " is not enrolled in " + 
					cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() +".");
		}
	}
	
	public void dropCourse(OnlineCourse oCourse) {
		if (onlineCourseList.contains(oCourse)) {
			onlineCourseList.remove(oCourse);
			enrolledCredits -= oCourse.getCreditUnits();
			enrolledOnlineCredits -= oCourse.getCreditUnits();
			oCourse.dropStudentFromRoster(this);
			if (oCourse.getCreditUnits() == 3)
				tuitionFee -= 2000;
			else
				tuitionFee -= 3000;
		}
		else {	// if the course isn't in the student's courseList ArrayList<Course>
			System.out.println("The course " + oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() +
					" could not be dropped because " + getName() + " is not enrolled in " + 
					oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() +".");
		}
	}
	
	public void removeFromSchedule(CampusCourse cCourse) {
		for (Integer courseCode : cCourse.getSchedule()) {	// loop through every Integer courseCode in schedule ArrayList<Integer>
			schedule.remove(courseCode);						// remove key:value pair
		}
	}
	


	// ----------------------------------------REQUIRED CREDITS-------------------------------------------------
	public int requiredToGraduate() {
		return (requiredCredits - completedUnits - enrolledCredits);
	}
}
