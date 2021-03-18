package org.university.software;

import java.io.Serializable;

import org.university.people.Student;

public class OnlineCourse extends Course implements Serializable {
	
	public boolean availableTo(Student aStudent) {
		if (aStudent.getEnrolledCampusCredits() < 6) 
			return false;
		return true;
	}
}
