package application.mvc.model;

import java.io.Serializable;
import java.util.Vector;


public class Department implements Serializable, WorkChangeAble, SynchronizeAble {

	private String name;
	private Vector<Role> roleList;
	private Vector<Employee> employeeList;
	private int startHour;
	private int endHour;
	private boolean isSynchronized;
	private boolean workChange;
	private double porfitOrLoss;

	public Department(String name) {

		this.name = name;
		roleList = new Vector<Role>();
		employeeList = new Vector<Employee>();
		startHour = 8;
		endHour = 17;
		isSynchronized = false;
		workChange = false;
		porfitOrLoss = 0;
	}

	public Department() {
		// TODO Auto-generated constructor stub
	}
    
	public Department(String nameVal, int start_hour, int end_hour, boolean isSynchronized, boolean workChange) {
		this.name=nameVal;
		roleList = new Vector<Role>();
		employeeList = new Vector<Employee>();
		this.startHour=start_hour;
		this.endHour=end_hour;
		
		this.isSynchronized = isSynchronized;
		this.workChange = workChange;
	}
	public Employee getEmployeeById(int serialNumber)
	{
		Employee theEmployee;
		for(int i=0; i < employeeList.size(); i++)	
		{
			int check = ((employeeList.get(i).getSerialNumber())-serialNumber);
			if(check==0)
			{
				theEmployee = employeeList.get(i);
				return theEmployee;
			}
		}
		return null;
	}
	public Role getRoleByName(String nameRole)
	{
		Role theRole;
		for(int i=0; i < roleList.size(); i++)	
		{
			String nameCheck=roleList.get(i).getName();
			if(nameCheck.equals(nameRole))
			{
				theRole = roleList.get(i);
				return theRole;
			}
		}
		return null;
	}
	public Vector<Employee> getEmployeeList() {
		return employeeList;
	}
	

	public void setEmployeeList(Vector<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public double getPorfitOrLoss() {
		return porfitOrLoss;
	}

	public void setPorfitOrLoss(double porfitOrLoss) {
		this.porfitOrLoss = porfitOrLoss;
	}

	public void setRoleList(Vector<Role> roleList) {
		this.roleList = roleList;
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

	public boolean getIsSynchronized() {
		return isSynchronized;
	}

	public void setSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}

	public String getName() {
		return name;
	}

	public void addEmployeeToDepartment(Employee employee) {
		employeeList.add(employee);
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Role> getRoleList() {
		return roleList;
	}

	public void setRole(Role role) {
		roleList.add(role);
	}


	@Override
	public void synchronize(int startHour) {
		for (int i = 0; i < roleList.size(); i++) {
			for (int j = 0; j < employeeList.size(); j++) {
				roleList.elementAt(i).getEmployeeList().elementAt(j).setStartHour(startHour);
				roleList.elementAt(i).getEmployeeList().elementAt(j).setEndHour(startHour);

			}
		}
		setSynchronized(true);

	}

	@Override
	public void workChange(boolean workChange) {
		this.workChange = workChange;

	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Department))
			return false;
		Department d = (Department) other;
		return d.name.equals(name);
	}

	@Override
	public String toString() {
		StringBuffer temp = new StringBuffer();
		temp.append("Department name: " + name + ", start hour=" + startHour + ", end hour="
				+ endHour + ", is synchronized=" + isSynchronized + ", can work change=" + workChange 
				+ "\n" + "role List: ");
		
		for (int i = 0; i < roleList.size(); i++) {
			temp.append("\n" + roleList.elementAt(i).toString());
		}
		
		return temp.toString();
	}

}
