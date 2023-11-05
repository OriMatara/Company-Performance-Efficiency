package application.mvc.model;

import java.io.Serializable;

public class HourSalaryEmployee extends Employee implements Serializable {

	private int salary;

	public HourSalaryEmployee(String name, int age, String role, String department, prefrences prefrence,
			int preferStartHour, int salary) {
		super(name, age, role, department, prefrence, preferStartHour);
		this.salary = salary;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof HourSalaryEmployee))
			return false;
		if (!(super.equals(other)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "\n hour salary: " + salary;
	}

}
