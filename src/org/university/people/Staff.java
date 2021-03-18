package org.university.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;

import org.university.hardware.Department;
import org.university.software.*;

public class Staff extends Employee implements Serializable {

	private double payRate;
	private double hoursWorked;
	private int tuitionFee;
	
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
	
	public Staff() {
		name = "unknown";
		schedule = new ArrayList<Integer>();
		campusCourseList = new ArrayList<CampusCourse>();
		onlineCourseList = new ArrayList<OnlineCourse>();
		map = new TreeMap<Integer, CampusCourse>();
		payRate = 0.;
		hoursWorked = 0.;
		tuitionFee = 0;
	}
	
	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}
	public void setMonthlyHours(double monthlyHours) {
		this.hoursWorked = monthlyHours;
	}
	public int getTuitionFee() {
		return tuitionFee;
	}
	//-----------------------------------------------ADD DROP COURSE---------------------------------------------------------
	public void addCourse(CampusCourse cCourse) {
		if (!onlineCourseList.isEmpty()) {
			System.out.println(onlineCourseList.get(0).getDepartment().getDepartmentName() + onlineCourseList.get(0).getCourseNumber() + 
					" is removed from " + name + "'s schedule(Staff can only take one class at a time). " +
					cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + 
					" has been added to " + name + "'s Schedule.");
		}
		else if (!campusCourseList.isEmpty()) {
			int key = map.firstKey();
			this.removeFromMap(map.get(key));
			System.out.println(campusCourseList.get(0).getDepartment().getDepartmentName() + campusCourseList.get(0).getCourseNumber() + 
					" is removed from " + name + "'s schedule(Staff can only take one class at a time). " +
					cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + 
					" has been added to " + name + "'s Schedule.");
		}

		campusCourseList = new ArrayList<CampusCourse>();
		onlineCourseList = new ArrayList<OnlineCourse>();
		campusCourseList.add(cCourse);
		cCourse.addStudentToRoster(this);
		tuitionFee = cCourse.getCreditUnits() * 300;
		addToMap(cCourse);
		Collections.sort(schedule);				// sort schedule
	}
	
	public void addCourse(OnlineCourse oCourse) {
		if (!onlineCourseList.isEmpty()) {
			System.out.println(onlineCourseList.get(0).getDepartment().getDepartmentName() + onlineCourseList.get(0).getCourseNumber() + 
					" is removed from " + name + "'s schedule(Staff can only take one class at a time). " +
					oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + 
					" has been added to " + name + "'s Schedule.");
		}
		else if (!campusCourseList.isEmpty()) {
			int key = map.firstKey();
			this.removeFromMap(map.get(key));
			System.out.println(campusCourseList.get(0).getDepartment().getDepartmentName() + campusCourseList.get(0).getCourseNumber() + 
					" is removed from " + name + "'s schedule(Staff can only take one class at a time). " +
					oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + 
					" has been added to " + name + "'s Schedule.");
		}

		campusCourseList = new ArrayList<CampusCourse>();
		onlineCourseList = new ArrayList<OnlineCourse>();
		onlineCourseList.add(oCourse);
		oCourse.addStudentToRoster(this);
		if (oCourse.getCreditUnits() == 3)
			tuitionFee = 2000;
		else
			tuitionFee = 3000;
	}
	//---------------------------------------END OF ADD DROP COURSE--------------------------------------------
	
	public double earns() {
		return payRate * hoursWorked;
	}
	public void raise(double percent) {
		payRate += payRate * (percent / 100.);
	}

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
