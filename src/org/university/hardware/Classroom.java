package org.university.hardware;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import org.university.software.CampusCourse;

public class Classroom implements Serializable {
	private String roomNumber;
	private ArrayList<CampusCourse> courseList;
	private ArrayList<Integer> schedule;
	private TreeMap<Integer, CampusCourse> map;
	private String[] week = {"Mon", "Tue", "Wed", "Thu", "Fri"};
	private String[] slot = {"8:00am to 9:15am", "9:30am to 10:45am",
			"11:00am to 12:15pm", "12:30pm to 1:45pm",
			"2:00pm to 3:15pm", "3:30pm to 4:45pm"};

	public Classroom() {
		roomNumber = "unknown";
		courseList = new ArrayList<CampusCourse>();
		schedule = new ArrayList<Integer>();
		map = new TreeMap<Integer, CampusCourse>();
	}
	// GETTERS AND SETTERS
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	// COURSE
	public ArrayList<CampusCourse> getCourseList() {
		return courseList;
	}
	
	public void addCourse(CampusCourse course) {
		if (!courseList.contains(course)) { 			// check if course is already in courseList
			if (!detectConflict(course)) { 				// if No timing conflict Proceed 
				courseList.add(course); 					// add to courseList ArrayList<Course>
				schedule.addAll(course.getSchedule());		// add course's 3 digit Integer codes to your own in schedule ArrayList<Integer>
				Collections.sort(schedule);					// sort your schedule low to high	
				this.addToMap(course);						// add course to TreeMap<Integer, ArrayList<Course>>
				if (course.getClassroom() != this) {
					course.setRoomAssigned(this);
				}
			}
		}
	}
	public boolean detectConflict(CampusCourse course) {
		boolean isTrue = false;
		for (Integer code : schedule) {
			if (course.getSchedule().contains(code)) { // detect if the classroom has an Integer courseCode with the same courseCode as the course to be added
				isTrue = true;							
				int value = code.intValue();
				int hours = value % 10;
				int days = (value / 100) % 10;
				//ECE107 conflicts with ECE320. Conflicting time slot Mon 9:30am to 10:45am. 
				//ECE107 course cannot be added to Harvill 101's Schedule.
				System.out.println(course.getDepartment().getDepartmentName() + course.getCourseNumber() +
						" conflicts with " + map.get(code).getDepartment().getDepartmentName() + map.get(code).getCourseNumber() + ". " +
						"Conflicting time slot " + week[days - 1] + " " + slot[hours - 1] + 
						". " + course.getDepartment().getDepartmentName() + course.getCourseNumber() +
						" course cannot be added to " + roomNumber + "'s Schedule.");
			}
		}
		return isTrue;
	}
	
	public ArrayList<Integer> getSchedule() {
		return schedule;
	}
	/*
	public void setSchedule(Integer schedule) {
		this.schedule.add(schedule);
		Collections.sort(this.schedule);
	}*/
	
	
	public void printSchedule() {
		// Iterate over the set of keys and grab the course belonging to it while also
		// grabbing the hours value and the days value
		System.out.println("The schedule for classroom " + this.getRoomNumber());
		if (!courseList.isEmpty()) {
			for (Integer code : schedule) {
				int value = code.intValue();
				int hours = value % 10;
				int days = (value / 100) % 10;
				CampusCourse course = map.get(code);
				System.out.println(week[days - 1] + " " + slot[hours - 1] + " " +
						course.getDepartment().getDepartmentName() + course.getCourseNumber() + " " +
						course.getName());
			}
		}
	}
	
	
	// MAP GETTER, ADD, REMOVE, SET
	public TreeMap<Integer, CampusCourse> getMap() {
		return map;
	}
	public void addToMap(CampusCourse course) {	// Should only be called by addCourse() when there are no time conflicts
		if (!course.getSchedule().isEmpty()) {
			for (Integer courseCode : course.getSchedule()) { // loop through the Integers in course's: schedule ArrayList<Integer>
				map.put(courseCode, course);				// make [courseCode : course] Key:Value pair
			}
		}
	}
	public void removeFromMap(CampusCourse course) {
		for (Integer courseCode : course.getSchedule()) {	// loop through every Integer courseCode in schedule ArrayList<Integer>
			map.remove(courseCode);							// remove key:value pair
		}
	}
	// should only be called by setCourseList()
	public void setTreeMap() {
		map = new TreeMap<Integer, CampusCourse>();
		if (!courseList.isEmpty() ) {
			for (CampusCourse course : courseList) {
				for (Integer courseCode : course.getSchedule()) {
					map.put(courseCode, course);					// make new [Integer:Course] key value pair
				}
			}
		}
	}
}