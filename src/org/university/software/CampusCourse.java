package org.university.software;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.university.hardware.Classroom;
import org.university.people.Student;

public class CampusCourse extends Course implements Serializable{
	private int maxCourseLimit;
	private ArrayList<Integer> schedule;
	private Classroom classroom;
	
	public CampusCourse() {
		maxCourseLimit = 0;
		schedule = new ArrayList<Integer>();
		classroom = new Classroom();
	}
	//------------------------------GETTERS AND SETTERS----------------------
	public int getMaxCourseLimit() {
		return maxCourseLimit;
	}
	public void setMaxCourseLimit(int maxCourseLimit) {
		this.maxCourseLimit = maxCourseLimit;
	}
	public ArrayList<Integer> getSchedule() {
		return schedule;
	}

	public Classroom getClassroom() {
		return classroom;
	}
	public void setRoomAssigned(Classroom classroom) {
		if (!classroom.detectConflict(this)) {
			this.classroom = classroom;
			classroom.addCourse(this);
		}
			
	}
	
	//-------------------------------END OF GETTERS AND SETTERS--------------------------

	public boolean availableTo(Student aStudent) {
		if (studentRoster.size() < maxCourseLimit)
			return true;
		return false;
	}
	
	
	// SCHEDULE
	public void setSchedule(Integer schedule) {
		this.schedule.add(schedule);
		Collections.sort(this.schedule);
	}

	public void printSchedule() {
		for (Integer timeSlot : schedule) {
				int value = timeSlot.intValue();
				int hours = value % 10;
				int days = (value / 100) % 10;
				System.out.println(week[days - 1] + " " + slot[hours - 1] + " " + classroom.getRoomNumber());
		}
	}	
	
}
