package application.mvc.model;

import java.io.Serializable;
import java.util.Vector;

public class Role implements Serializable ,WorkChangeAble, SynchronizeAble {

	private String name;

	private Vector<Employee> employeeList;
	private int startHour;
	private int endHour;
	private boolean isSynchronized;
	private boolean workChange;

	public Role(String name, int startHour) {	
		this.name = name;
		employeeList = new Vector<Employee>();
		this.startHour = startHour;
		setEndHour(startHour);
		isSynchronized = false;
		workChange = false;
	}

	public Role() {
		// TODO Auto-generated constructor stub
	}

	public boolean getWorkChange() {
		return workChange;
	}

	public void setWorkChange(boolean workChange) {
		this.workChange = workChange;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	//this method set the end hour to be accurate
	public void setEndHour(int startHour) {
		if(startHour > 14) {
			endHour = startHour - 15;
		}
		else {
		endHour = startHour + 9;
		}
	}

	public String getRoleName() {
		return name;
	}

	public void addEmployeeToRole(Employee employee) {
		employeeList.add(employee);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(Vector<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public boolean getIsSynchronized() {
		return isSynchronized;
	}

	public void setSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}


	@Override
	public void synchronize(int startHour) {
		for (int i = 0; i < employeeList.size(); i++) {
			employeeList.elementAt(i).setStartHour(startHour);
			employeeList.elementAt(i).setEndHour(startHour);
		}
		setSynchronized(true);
	}

	@Override
	public void workChange(boolean workChange) {
		this.workChange = workChange;

	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Role))
			return false;
		Role r = (Role) other;
		return r.name.equals(name);
	}

	@Override
	public String toString() {
		StringBuffer temp = new StringBuffer();
		temp.append("Role name: " + name + ", startHour=" + startHour + ", endHour=" + endHour + ", isSynchronized="
				+ isSynchronized + ", workChange=" + workChange );
		
		return temp.toString();
	}

}
