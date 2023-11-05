package application.mvc.model;

import java.io.Serializable;
import java.util.Vector;

public class Company implements Serializable{

	private String name;
	private Vector<Department> departmentList;
	private double porfitOrLoss;

	public Company(String name) {

		this.name = name;
		departmentList = new Vector<Department>();
		porfitOrLoss = 0;
	}

	public Company() {
	
		
	}

	public double getPorfitOrLoss() {
		return porfitOrLoss;
	}

	public void setPorfitOrLoss(double porfitOrLoss) {
		this.porfitOrLoss = porfitOrLoss;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(Vector<Department> departmentList) {
		this.departmentList = departmentList;
	}

}