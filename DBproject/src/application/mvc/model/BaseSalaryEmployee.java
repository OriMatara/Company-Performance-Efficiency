package application.mvc.model;

import java.io.Serializable;

public class BaseSalaryEmployee extends Employee implements Serializable {

	protected int baseSalary;

	public BaseSalaryEmployee(String name, int age, String role, String department, prefrences prefrence,
			int preferStartHour, int baseSalary) {
		super(name, age, role, department, prefrence, preferStartHour);
		this.baseSalary = baseSalary;
	}

	public int getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof BaseSalaryEmployee))
			return false;
		if (!(super.equals(other)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "\n base salary: " + baseSalary;
	}

}
