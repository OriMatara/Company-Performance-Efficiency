package application.mvc.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import application.mvc.model.Company;
import application.mvc.model.Department;
import application.mvc.model.Employee;
import application.mvc.model.Experiment;
import application.mvc.model.Role;

public interface ExperimentModelEventsListener {

	public void addCompanyToModelEvent(String name);

	void addEmployeeToModelEvent(Employee newEmployee);

	void addDepartmentToModelEvent(String name, Department newDepartment);

	void addRoleToModelEvent(String name, Role newRole);

}
