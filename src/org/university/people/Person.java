package org.university.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import org.university.hardware.Department;
import org.university.software.CampusCourse;
import org.university.software.OnlineCourse;

public abstract class Person implements Serializable {
	protected String name;
	protected Department department;
	protected ArrayList<Integer> schedule;
	protected ArrayList<CampusCourse> campusCourseList;
	protected ArrayList<OnlineCourse> onlineCourseList;
	protected TreeMap<Integer, CampusCourse> map;
	protected String[] week = {"Mon", "Tue", "Wed", "Thu", "Fri"};
	protected String[] slot = {"8:00am to 9:15am", "9:30am to 10:45am",
			"11:00am to 12:15pm", "12:30pm to 1:45pm",
			"2:00pm to 3:15pm", "3:30pm to 4:45pm"};

	public Person() {
		name = "unknown";
		schedule = new ArrayList<Integer>();
		campusCourseList = new ArrayList<CampusCourse>();
		onlineCourseList = new ArrayList<OnlineCourse>();
		map = new TreeMap<Integer, CampusCourse>();
	}
	// ------------------------------GETTERS, SETTERS---------------------------
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public ArrayList<Integer> getSchedule() {
		return schedule;
	}

	public ArrayList<CampusCourse> getCampusCourseList() {
		return campusCourseList;
	}

	public ArrayList<OnlineCourse> getOnlineCourseList() {
		return onlineCourseList;
	}
	// --------------------------END OF GETTERS, SETTERS--------------------------------------
	
	// ------------------------ABSTRACT: ADD COURSES-----------------------------------------
	public abstract void addCourse(CampusCourse cCourse);
	public abstract void addCourse(OnlineCourse oCourse);
	//------------------------------END OF ABSTRACT------------------------------------------
	
	
	// ---------------------------------CONCRETE FUNCTIONS-------------------------------------------
	// -------------------------SCHEDULE FUNCTIONS PRINT, DETECT CONFLICT,--------------------------- 
	// Prints the schedule by using TreeMap map[code:CampusCourse]
	public void printSchedule() {
		Set<Integer> keyList = map.keySet();	// make a Set<Integer> of key values
		// Iterate over the set of keys and grab the course belonging to it while also
		// grabbing the hours value and the days value
		for (Integer code : keyList) {	// loop through all the keys in keyList
			int value = code.intValue();	
			int hours = value % 10;				// 1s digit
			int days = (value / 100) % 10;		// 100s digit
			CampusCourse course = map.get(code);
			System.out.println(week[days - 1] + " " + slot[hours - 1] + " " +
					course.getDepartment().getDepartmentName() + course.getCourseNumber() + " " +
					course.getName());
		}
		for (OnlineCourse oCourse : onlineCourseList) {
			System.out.println(oCourse.getCourseNumber() + " " + oCourse.getName());
		}
	}
	
	public boolean detectConflict(CampusCourse cCourse) {
		boolean isTrue = false;
		for (Integer code : schedule) {
			if (cCourse.getSchedule().contains(code)) { // detect if the student has an Integer courseCode with the same courseCode as the course to be added
				isTrue = true;							
				int value = code.intValue();
				int hours = value % 10;
				int days = (value / 100) % 10;
				System.out.println(cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() +
						" course cannot be added to " + getName() + "'s Schedule. " +
						cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + " conflicts with " +
						map.get(code).getDepartment().getDepartmentName() + map.get(code).getCourseNumber() +
						". Conflicting time slot is " + week[days - 1] + " " + slot[hours - 1] + ".");
			}
		}
		return isTrue;
	}
	//  ----------------------------------------MAP STUFF----------------------------------------
	// map is used to help print out schedule 
	public void addToMap(CampusCourse cCourse) {	// Should only be called by addCourse() when there are no time conflicts
		if (!cCourse.getSchedule().isEmpty()) {
			for (Integer courseCode : cCourse.getSchedule()) { // loop through the Integers in course's: schedule ArrayList<Integer>
				map.put(courseCode, cCourse);				// make [courseCode : course] Key:Value pair
			}
		}
	}
	public void removeFromMap(CampusCourse cCourse) {
		for (Integer courseCode : cCourse.getSchedule()) {	// loop through every Integer courseCode in schedule ArrayList<Integer>
			map.remove(courseCode);							// remove key:value pair
		}
	}
	// should only be called by setCourseList()
	public void setTreeMap() {
		map = new TreeMap<Integer, CampusCourse>();
		if (!campusCourseList.isEmpty() ) {
			for (CampusCourse cCourse : campusCourseList) {
				for (Integer courseCode : cCourse.getSchedule()) {
					map.put(courseCode, cCourse);					// make new [Integer:Course] key value pair
				}
			}
		}
	}
	
	public TreeMap<Integer, CampusCourse> getMap() {
		return map;
	}
}
