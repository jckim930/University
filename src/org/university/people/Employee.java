package org.university.people;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import org.university.hardware.Department;
import org.university.software.CampusCourse;
import org.university.software.OnlineCourse;

public abstract class Employee extends Person {
	
	public abstract double earns();
	public abstract void raise(double percent);
	
}
