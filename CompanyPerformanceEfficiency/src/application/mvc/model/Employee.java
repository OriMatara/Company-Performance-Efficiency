package application.mvc.model;

import java.io.Serializable;

public class Employee implements Serializable, WorkChangeAble, SynchronizeAble {

	protected String name;
	protected static int otomaticSerial = 1000;
	protected int serialNumber;
	protected int age;
	protected String role;
	protected String department;

	public enum prefrences {
		EARLY, LATE, NO_CHANGE, WORK_FROM_HOME
	};

	protected prefrences prefrence;
	protected int startHour;
	protected int endHour;
	protected double efficiencyTime;
	protected double porfitOrLoss;
	private boolean workChange;
	private boolean isSynchronized;
	protected int preferStartHour;
	protected int preferEndHour;
	protected int difaultStartHour;
	protected int difaultEndHour;

	public Employee(String name, int age, String role, String department, prefrences prefrence, int preferStartHour) {
		this.name = name;
		this.age = age;
		this.role = role;
		this.department = department;
		this.prefrence = prefrence;
		serialNumber = otomaticSerial++;
		startHour = 8;
		endHour = 17;
		difaultStartHour = 8;
		difaultEndHour = 17;
		efficiencyTime = 0;
		porfitOrLoss = 0;
		workChange = false;
		isSynchronized = false;
		this.preferStartHour = preferStartHour;
		setPreferEndHour(preferStartHour);
	}
//	public Employee(String name, int age, String role, String department, int preferStartHour) {
//		this.name = name;
//		this.age = age;
//		this.role = role;
//		this.department = department;
//		serialNumber = otomaticSerial++;
//		startHour = 8;
//		endHour = 17;
//		difaultStartHour = 8;
//		difaultEndHour = 17;
//		efficiencyTime = 0;
//		porfitOrLoss = 0;
//		workChange = false;
//		isSynchronized = false;
//		this.preferStartHour = preferStartHour;
//		setPreferEndHour(preferStartHour);
//	}
	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(int id, String nameVal, int ageVal, double porfitOrLossVal, int star_tHour, int end_Hour,
			double efficiency_time, String emp_Role_Name, String emp_Department_Name) {
		this.serialNumber=id;
		this.name=nameVal;
		this.age=ageVal;
		this.porfitOrLoss=porfitOrLossVal;
		this.startHour=star_tHour;
		this.endHour=end_Hour;
		this.efficiencyTime=efficiency_time;
		this.role=emp_Role_Name;
		this.department=emp_Department_Name;
		
	}

	public int getDifaultStartHour() {
		return difaultStartHour;
	}

	public void setDifaultStartHour(int difaultStartHour) {
		this.difaultStartHour = difaultStartHour;
	}

	public int getDifaultEndHour() {
		return difaultEndHour;
	}

	public void setDifaultEndHour(int difaultEndHour) {
		this.difaultEndHour = difaultEndHour;
	}

	public int getPreferStartHour() {
		return preferStartHour;
	}

	public void setPreferStartHour(int preferStartHour) {
		this.preferStartHour = preferStartHour;
	}

	public int getPreferEndHour() {
		return preferEndHour;
	}

	//this method set the end hour to be accurate
	public void setPreferEndHour(int preferStartHour) {
		if (preferStartHour > 14) {
			preferEndHour = preferStartHour - 15;
		} else {
			preferEndHour = preferStartHour + 9;
		}
	}

	public boolean getSynchronized() {
		return isSynchronized;
	}

	public void setSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public boolean getWorkChange() {
		return workChange;
	}

	public void setWorkChange(boolean workChange) {
		this.workChange = workChange;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getPorfitOrLoss() {
		return porfitOrLoss;
	}

	public void setPorfitOrLoss(double porfitOrLoss) {
		this.porfitOrLoss = porfitOrLoss;
	}

	public double getEfficiencyTime() {
		return efficiencyTime;
	}

	public void setEfficiencyTime(double efficiency) {
		this.efficiencyTime = efficiency;
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
		if (startHour > 14) {
			endHour = startHour - 15;
		} else {
			endHour = startHour + 9;
		}
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public prefrences getPrefrence() {
		return prefrence;
	}

	public void setPrefrence(prefrences prefrence) {
		this.prefrence = prefrence;
	}

	@Override
	public void workChange(boolean workChange) {
		this.workChange = workChange;
	}

	@Override
	public void synchronize(int startHour) {

		setStartHour(startHour);
		setEndHour(startHour);

		setSynchronized(true);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Employee))
			return false;
		Employee e = (Employee) other;
		return e.name.equals(name);
	}

	@Override
	public String toString() {
		return "Employee name: " + name + "\n age: " + age + ", serialNumber: " + serialNumber + ", role: " + role
				+ ", department: " + department + "\n prefrence: " + prefrence + ", real start hour: " + startHour
				+ ", real end hour: " + endHour + ",can work change: " + workChange + ", prefer start hour: "
				+ preferStartHour + ", prefer end hour: " + preferEndHour + ", difault start hour: " + difaultStartHour
				+ ", difault end hour: " + difaultEndHour;
	}


}
