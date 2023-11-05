package application.mvc.model;

import java.io.Serializable;


public class BasePlusPercentageSalaryEmployee extends Employee implements Serializable {

	private int basePlusPercentageSalary;

	public BasePlusPercentageSalaryEmployee(String name, int age, String role, String department, prefrences prefrence,
			int preferStartHour, int basePlusPercentageSalary) {
		super(name, age, role, department, prefrence, preferStartHour);
		this.basePlusPercentageSalary = basePlusPercentageSalary;
	}

	public int getBasePlusPercentageSalary() {
		return basePlusPercentageSalary;
	}

	public void setBasePlusPercentageSalary(int basePlusPercentageSalary) {
		this.basePlusPercentageSalary = basePlusPercentageSalary;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof BasePlusPercentageSalaryEmployee))
			return false;
		if (!(super.equals(other)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "\n base plus percentage salary: " + basePlusPercentageSalary;
	}



	

}
