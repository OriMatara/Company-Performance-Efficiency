package application.mvc.model;
import java.sql.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import application.mvc.listeners.ExperimentModelEventsListener;
import application.mvc.model.Department;
import application.mvc.model.Employee.prefrences;
import java.sql.*;
public class Experiment implements Serializable {

	private Vector<ExperimentModelEventsListener> listeners;
	public Vector<Employee> employeeList;
	public Vector<Department> departmentList;
	public Vector<Role> roleList;
	public Company newCompany;
	public Experiment experiment;

	public DBmySql_connection conn;
	public Experiment() {
		employeeList = new Vector<Employee>();
		departmentList = new Vector<Department>();
		roleList = new Vector<Role>();
		listeners = new Vector<ExperimentModelEventsListener>();
		newCompany = new Company("");
		 conn=new DBmySql_connection();

	}

	public void registerListener(ExperimentModelEventsListener listener) {
		listeners.add(listener);
	}

	// Hard coded
	public void hardCoded(String name) {

//		addDepartment("Movie");
//		addDepartment("Developer");
//		addDepartment("Sport");
//
//		departmentList.elementAt(0).setWorkChange(true);
//		departmentList.elementAt(0).setSynchronized(true);
//		departmentList.elementAt(0).setStartHour(14);
//		departmentList.elementAt(0).setEndHour(14);;
//		
//		departmentList.elementAt(1).setWorkChange(false);
//		departmentList.elementAt(1).setSynchronized(false);     
//		
//		departmentList.elementAt(2).setWorkChange(true);
//
//		addRole("Director", "Movie", 8);
//		addRole("Movie Star", "Movie", 10);
//		addRole("Senior", "Developer", 5);
//		addRole("Junior", "Developer", 12);
//
//		departmentList.elementAt(0).getRoleList().elementAt(0).setWorkChange(true);
//		
//		departmentList.elementAt(0).getRoleList().elementAt(1).setWorkChange(false);
//		
//		departmentList.elementAt(1).getRoleList().elementAt(0).setWorkChange(false);
//		departmentList.elementAt(1).getRoleList().elementAt(0).setSynchronized(true);
//		
//		departmentList.elementAt(1).getRoleList().elementAt(1).setWorkChange(true);
//		departmentList.elementAt(1).getRoleList().elementAt(1).setSynchronized(true);
//
//		Employee newEmployee1 = new Employee("Eitan", 32, "Senior", "Developer", prefrences.EARLY, 4);
//		Employee newEmployee2 = new Employee("Morgan", 22, "Junior", "Developer", prefrences.NO_CHANGE, 8);
//		Employee newEmployee3 = new Employee("Dan", 30, "Movie Star", "Movie", prefrences.LATE, 15);
//		Employee newEmployee4 = new Employee("Orlando", 26, "Movie Star", "Movie", prefrences.WORK_FROM_HOME, 13);
//
//		addEmployee(newEmployee1, "Developer", "Senior");
//		addEmployee(newEmployee2, "Developer", "Junior");
//		addEmployee(newEmployee3, "Movie", "Movie Star");
//		addEmployee(newEmployee4, "Movie", "Movie Star");

	}

	// Creates a new company
	public void addCompany(String companyName) {
	
		newCompany = new Company(companyName);
		conn.addCompanyDB(newCompany);
		
		for (int i = 0; i < listeners.size(); i++) {
			listeners.elementAt(i).addCompanyToModelEvent(newCompany.getName());
			
		}
	}

	// this function add an employee to employee list in experiment, to role, and to
	// department and checks if the department is synchronized, if yes it changes
	// the real start hour for the employee to the start hour that the department
	// holds because we decided that the department is the most influencing.
	// if the department is not synchronized the real start hour for the employee is
	// the role start hour.
	// all that helps us to calculate the efficiency in the continue of the program
	public void addEmployee(Employee newEmployee, String departmentName, String roleName, boolean flag) {
		newEmployee.setEndHour(newEmployee.getStartHour());

		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(departmentName)) {

				for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {
					if (departmentList.elementAt(i).getRoleList().elementAt(j).getRoleName().equals(roleName)) {

						if (departmentList.elementAt(i).getIsSynchronized() == true) {

							newEmployee.setStartHour(departmentList.elementAt(i).getStartHour());

							newEmployee.setEndHour(departmentList.elementAt(i).getStartHour());

						} else {
							newEmployee.setStartHour(
									departmentList.elementAt(i).getRoleList().elementAt(j).getStartHour());
							newEmployee.setEndHour(departmentList.elementAt(i).getRoleList().elementAt(j).getStartHour());
						}
					}

					departmentList.elementAt(i).getRoleList().elementAt(j).addEmployeeToRole(newEmployee);
					departmentList.elementAt(i).addEmployeeToDepartment(newEmployee);
				

					
					
				}
			}

		}

		employeeList.add(newEmployee);
		conn.addEmployeeToDepartmentToRoleDB(newEmployee, flag);
		conn.addEmployeeToDepartmentEmployeeList(newEmployee.getName(),newEmployee.getSerialNumber());
		conn.addEmployeeToRoleEmployeeList(newEmployee.getName(),newEmployee.getSerialNumber(),newEmployee.getRole());
		
		for (int i = 0; i < listeners.size(); i++) {
			listeners.elementAt(i).addEmployeeToModelEvent(newEmployee);
		}

	}

	// this method calculate the efficiency and the profit or loss from all
	// employees.
	// it does calculation with the start hour that the employee prefer, with the
	// difault start hour that it is 8 o'clock and with the real start hour that
	// comes from the role or from the department if its synchronized.
	public void employeeEfficiency() {
		int temp = 0;
		double tempEfficiencyTime = 0;

		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {
				for (int k = 0; k < departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
						.size(); k++) {
					int imployeePreferHour = departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
							.elementAt(k).getPreferStartHour();
					int realStartHour = departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
							.elementAt(k).getStartHour();
					int difaultHour = departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
							.elementAt(k).getDifaultStartHour();

					if (departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
							.getPrefrence().equals(Employee.prefrences.WORK_FROM_HOME)) {
						if (difaultHour < realStartHour && realStartHour < imployeePreferHour) {
							temp = (realStartHour - difaultHour) + (realStartHour - imployeePreferHour);
							tempEfficiencyTime = temp * 0.1;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if (difaultHour < imployeePreferHour && imployeePreferHour < realStartHour) {
							temp = (imployeePreferHour - difaultHour) + (imployeePreferHour - realStartHour);
							tempEfficiencyTime = temp * 0.1;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if (imployeePreferHour < realStartHour && realStartHour < difaultHour) {
							temp = (difaultHour - realStartHour) + (imployeePreferHour - realStartHour);
							tempEfficiencyTime = temp * 0.1;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if (realStartHour < imployeePreferHour && imployeePreferHour < difaultHour) {
							temp = (difaultHour - imployeePreferHour) + (realStartHour - imployeePreferHour);
							tempEfficiencyTime = temp * 0.1;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if ((imployeePreferHour < difaultHour && difaultHour < realStartHour)
								|| (realStartHour < difaultHour && difaultHour < imployeePreferHour)) {
							temp = (Math.abs(imployeePreferHour - realStartHour)) * (-1);
							tempEfficiencyTime = temp * 0.1;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if ((difaultHour < realStartHour && realStartHour == imployeePreferHour)
								|| (difaultHour > realStartHour && realStartHour == imployeePreferHour)) {
							temp = Math.abs(imployeePreferHour - difaultHour);
							tempEfficiencyTime = temp * 0.1;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if (difaultHour == realStartHour && difaultHour == imployeePreferHour) {
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(0);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(0);
						} else {
							temp = (Math.abs(realStartHour - imployeePreferHour)) * (-1);
							tempEfficiencyTime = temp * 0.1;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						}

					} else {
						if (difaultHour < realStartHour && realStartHour < imployeePreferHour) {
							temp = (realStartHour - difaultHour) + (realStartHour - imployeePreferHour);
							tempEfficiencyTime = temp * 0.2;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if (difaultHour < imployeePreferHour && imployeePreferHour < realStartHour) {
							temp = (imployeePreferHour - difaultHour) + (imployeePreferHour - realStartHour);
							tempEfficiencyTime = temp * 0.2;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if (imployeePreferHour < realStartHour && realStartHour < difaultHour) {
							temp = (difaultHour - realStartHour) + (imployeePreferHour - realStartHour);
							tempEfficiencyTime = temp * 0.2;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if (realStartHour < imployeePreferHour && imployeePreferHour < difaultHour) {
							temp = (difaultHour - imployeePreferHour) + (realStartHour - imployeePreferHour);
							tempEfficiencyTime = temp * 0.2;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if ((imployeePreferHour < difaultHour && difaultHour < realStartHour)
								|| (realStartHour < difaultHour && difaultHour < imployeePreferHour)) {
							temp = (Math.abs(imployeePreferHour - realStartHour)) * (-1);
							tempEfficiencyTime = temp * 0.2;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if ((difaultHour < realStartHour && realStartHour == imployeePreferHour)
								|| (difaultHour > realStartHour && realStartHour == imployeePreferHour)) {
							temp = Math.abs(imployeePreferHour - difaultHour);
							tempEfficiencyTime = temp * 0.2;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						} else if (difaultHour == realStartHour && difaultHour == imployeePreferHour) {
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(0);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(0);
						} else {
							temp = (Math.abs(realStartHour - imployeePreferHour)) * (-1);
							tempEfficiencyTime = temp * 0.2;
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEfficiencyTime(tempEfficiencyTime);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setPorfitOrLoss(tempEfficiencyTime * 10);
						}

					}
				}

			}

		}

	}

	// this method calculate the efficiency of all departments
	public void departmentEfficiency() {
		int counter = 0;
		double tempProfitOrLoss = 0;

		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {

				for (int k = 0; k < departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
						.size(); k++) {
					counter = 0;

					int temp = departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
							.getSerialNumber();

					for (int f = 0; f < k; f++) {
						if (departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(f)
								.getSerialNumber() == temp) {
							counter++;
						}
					}
					if (counter == 0) {
						tempProfitOrLoss = tempProfitOrLoss + (departmentList.elementAt(i).getRoleList().elementAt(j)
								.getEmployeeList().elementAt(k).getPorfitOrLoss());
					}

				}
				break;
			}
			departmentList.elementAt(i).setPorfitOrLoss(tempProfitOrLoss);
			tempProfitOrLoss = 0;
		}
	}

	// this method calculate the efficiency of all the company
	public void companyEfficiency() {
		
		double tempProfitOrLoss = 0;

		for (int i = 0; i < departmentList.size(); i++) {
			tempProfitOrLoss = tempProfitOrLoss + (departmentList.elementAt(i).getPorfitOrLoss());
		}

		newCompany.setPorfitOrLoss(tempProfitOrLoss);
	
	}

	// this method checks if the department is changeable
	public boolean isThisDepartmentChangeable(String department) {
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(department)) {
				return departmentList.elementAt(i).getWorkChange();
			}
		}
		return true;
	}

	// this method changes the hours that department demands from employee and
	// checks if the employee can change his work and if the department is
	// synchronized, if yes the hour change in the employee too
	public void setChanegeHourByDepartment(String department, int startHour) {
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(department)) {
				departmentList.elementAt(i).setStartHour(startHour);
				departmentList.elementAt(i).setEndHour(startHour);
				for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {
					for (int k = 0; k < departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
							.size(); k++) {
						if (departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
								.getWorkChange() == true && departmentList.elementAt(i).getIsSynchronized() == true) {
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setStartHour(startHour);
							departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.setEndHour(startHour);
						}
					}
				}
			}
		}

	}

	// this method checks if the role is changeable
	public boolean isThisRoleChangeable(String department, String role) {
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(department)) {

				for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {
					if (departmentList.elementAt(i).getRoleList().elementAt(j).getName().equals(role)) {
						return departmentList.elementAt(i).getRoleList().elementAt(j).getWorkChange();
					}
				}
			}
		}
		return true;
	}

	// this method changes the hours that role demands from employee and
	// checks if the employee can change his work, if the department is not
	// synchronized and if the role is synchronized,
	// if yes the hour change in the employee too.
	public void setChanegeHourByRole(String department, String role, int startHour) {
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(department)) {
				for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {
					if (departmentList.elementAt(i).getRoleList().elementAt(j).getName().equals(role)) {
						departmentList.elementAt(i).getRoleList().elementAt(j).setStartHour(startHour);
						departmentList.elementAt(i).getRoleList().elementAt(j).setEndHour(startHour);
						for (int k = 0; k < departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
								.size(); k++) {
							if (departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.getWorkChange() == true
									&& departmentList.elementAt(i).getRoleList().elementAt(j)
											.getIsSynchronized() == true
									&& departmentList.elementAt(i).getIsSynchronized() == false) {
								departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
										.setStartHour(startHour);
								departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
										.setEndHour(startHour);
							}
						}
					}
				}
			}
		}
	}

	// this method shows the experiment results
		public String expirementResults() {

			String str1 = "";

			String str2 = "";

			String str3 = "";

			String employees = "Porfit or loss per Employee:";

			String departments = "\n Porfit or loss per Department:";

			String company = "\nPorfit or loss for Company:\n";

			int counter = 0;

			for (int i = 0; i < departmentList.size(); i++) {

				for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {

					for (int k = 0; k < departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
							.size(); k++) {
						counter = 0;

						int temp = departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
								.getSerialNumber();

						for (int f = 0; f < k; f++) {
							if (departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(f)
									.getSerialNumber() == temp) {

								counter++;
							}
						}
						if (counter == 0) {
							str1 += "\n "
									+ departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
											.getName()
									+ ": " + departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
											.elementAt(k).getPorfitOrLoss();
						}
					}
					break;
				}
				str2 += "\n " + departmentList.elementAt(i).getName() + ": "
						+ departmentList.elementAt(i).getPorfitOrLoss();

			}

			str3 = "\n " + newCompany.getName() + ": " + newCompany.getPorfitOrLoss();

			return employees + str1 + "\n" + departments + str2 + "\n" + company + str3;

		}

	// this method add department
	public void addDepartment(String name, boolean flag) {
		Department newDepartment = new Department(name);
		departmentList.add(newDepartment);
		
		for (int i = 0; i < listeners.size(); i++) {
			
			listeners.elementAt(i).addDepartmentToModelEvent(name, newDepartment);
			
		}
		
		conn.addDepartmentDB(newDepartment, name, flag);
	
	}
	

	public void departmentSynchronize(String departmentName, int startHour) {
		Department d = new Department();
		//conn.addDepartmentDB(d,departmentName);
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(departmentName)) {

				departmentList.elementAt(i).synchronize(startHour);
				departmentList.elementAt(i).setStartHour(startHour);
				departmentList.elementAt(i).setEndHour(startHour);
	
				d=departmentList.elementAt(i);
				int endH = departmentList.elementAt(i).getEndHour();

				conn.department_Synchronize( departmentName ,startHour,endH);
				
				
			
			}
		}
	}
	
	public void departmentWorkChange(String departmentName, boolean workChange) {
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(departmentName)) {
				departmentList.elementAt(i).workChange(workChange);
				
				conn.department_WorkChange( departmentName);
			}
		}
	}

	// this method add role
	@SuppressWarnings("unlikely-arg-type")
	public void addRole(String name, String departmentName, int startHour) {
		Role newRole = new Role(name, startHour);

		roleList.add(newRole);
	
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(departmentName)) {
				departmentList.elementAt(i).setRole(newRole);
				conn.addRoleToDepartmentRoleList(newRole, departmentName); // insert role to department list table in database
			}
		}

		for (int i = 0; i < listeners.size(); i++) {
			listeners.elementAt(i).addRoleToModelEvent(name, newRole);
		}

	}

	public Vector<String> getAllRolesInDepartment(String departmetnName) {
		Vector<String> allRolesNames = new Vector<String>();

		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(departmetnName)) {
				for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {
					allRolesNames.add(departmentList.elementAt(i).getRoleList().elementAt(j).getRoleName());
				}
			}
		}
		return allRolesNames;
	}

	public void roleSynchronize(String departmentName, String roleName, int startHour) {
	
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(departmentName)) {

				for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {
					if (departmentList.elementAt(i).getRoleList().elementAt(j).getName().equals(roleName)) {
						departmentList.elementAt(i).getRoleList().elementAt(j).synchronize(startHour);
						departmentList.elementAt(i).getRoleList().elementAt(j).setStartHour(startHour);
						departmentList.elementAt(i).getRoleList().elementAt(j).setEndHour(startHour);
						boolean sync = departmentList.elementAt(i).getRoleList().elementAt(j).getIsSynchronized();
						int endH=departmentList.elementAt(i).getRoleList().elementAt(j).getEndHour();
					
						conn.role_department_Synchronize(departmentName,  roleName, startHour, endH, sync );
					}
				}
			}
		}
	}


	public void roleWorkChange(String departmentName, String roleName, boolean workChange) {

		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(departmentName)) {

				for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {
					if (departmentList.elementAt(i).getRoleList().elementAt(j).getName().equals(roleName)) {
						departmentList.elementAt(i).getRoleList().elementAt(j).workChange(workChange);
						conn.updateRoleWorkChange(departmentName,roleName, workChange);
					}
				}
			}
		}
	}



	public void employeeWorkChange(String departmentName, String roleName, String employeeName, boolean workChange) {
		for (int i = 0; i < departmentList.size(); i++) {
			if (departmentList.elementAt(i).getName().equals(departmentName)) {

				for (int j = 0; j < departmentList.elementAt(i).getRoleList().size(); j++) {
					if (departmentList.elementAt(i).getRoleList().elementAt(j).equals(roleName)) {

						for (int k = 0; k < departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList()
								.size(); k++) {
							if (departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
									.equals(employeeName)) {
								departmentList.elementAt(i).getRoleList().elementAt(j).getEmployeeList().elementAt(k)
										.workChange(workChange);
								
							}

						}
					}
				}
			}
		}
		for (int i = 0; i < employeeList.size(); i++) {
			if (employeeList.elementAt(i).getDepartment().equals(departmentName)
					&& employeeList.elementAt(i).getName().equals(employeeName)) {
				employeeList.elementAt(i).workChange(workChange);
				
				conn.updateEmployeeWorkChange(departmentName,roleName, employeeList.elementAt(i));
			}
		}
	}
	
	public String loadOldData() throws IOException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException  {

		newCompany =conn.getCompanyDB();
		Vector<Department> departmentsVector = new Vector<Department>();
		departmentsVector = conn.getDepartments();	
		Vector<Role> rolesVector = new Vector<Role>();	
		Vector<Employee> employeesVector = new Vector<Employee>();

			for (int i = 0; i < departmentsVector.size(); i++) {
			addDepartment(departmentsVector.elementAt(i).getName(), false);
			Department d = new Department();
			d=departmentsVector.elementAt(i);
			departmentList.elementAt(i).setName(d.getName());
			
			rolesVector = conn.getRolListOfDepartment(d.getName());
			employeesVector = conn.getEmployeeListOfDepartmentAndRoll(d.getName());
			
			departmentList.elementAt(i).setRoleList(rolesVector);
			departmentList.elementAt(i).setEmployeeList(employeesVector);
			departmentList.elementAt(i).setStartHour((d.getStartHour()));
			departmentList.elementAt(i).setEndHour((d.getStartHour()));
			departmentList.elementAt(i).setSynchronized(d.getIsSynchronized());
			departmentList.elementAt(i).setWorkChange(d.getWorkChange());
			departmentList.elementAt(i).setPorfitOrLoss(d.getPorfitOrLoss());

		}
			
			
			for (int i = 0; i < employeesVector.size(); i++) {
			employeeList.add(employeesVector.elementAt(i));
			for (int j = 0; j < listeners.size(); j++) {
				listeners.elementAt(j).addEmployeeToModelEvent(employeesVector.elementAt(i));
			}
			
			for (int y = 0; y < departmentList.size(); y++) {
				if (departmentList.elementAt(y).getName().equals(employeesVector.elementAt(i).getDepartment())) {

					for (int j = 0; j < departmentList.elementAt(y).getRoleList().size(); j++) {
						if (departmentList.elementAt(y).getRoleList().elementAt(j).getRoleName().equals(employeesVector.elementAt(i).getRole())) {
							
							departmentList.elementAt(y).getRoleList().elementAt(j).addEmployeeToRole(employeesVector.elementAt(i));
							
						}

					}
				}

			}
			
			
			}
			
			
		return newCompany.getName();
	}
	


	
	
	// this method save data to file
	public void saveToFile(Experiment theModel) throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("data.txt"));
		
		
		outFile.writeObject(newCompany);
	outFile.writeObject(departmentList);
	outFile.writeObject(employeeList);
	
	outFile.close();

	}

	@Override
	public String toString() {
		StringBuffer temp = new StringBuffer();
		temp.append("Department List: " + "\n");
		for (int i = 0; i < departmentList.size(); i++) {
			temp.append("\n" + departmentList.elementAt(i).toString());
		}
		return temp.toString();
	}

}
