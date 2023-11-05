package application.mvc.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Vector;

import application.mvc.listeners.ExperimentModelEventsListener;
import application.mvc.listeners.ExperimentUIEventsListener;
import application.mvc.model.Experiment;
import application.mvc.model.Company;
import application.mvc.model.DBmySql_connection;
import application.mvc.model.Department;
import application.mvc.model.Employee;
import application.mvc.model.Role;
import application.mvc.model.SynchronizeAble;
import application.mvc.model.WorkChangeAble;
import application.mvc.view.ExperimentView;
import application.mvc.view.InterfaceExperimentView;

public class ExperimentController implements Serializable ,ExperimentModelEventsListener, ExperimentUIEventsListener {

	private InterfaceExperimentView theView;

	private Experiment theModel;

	public ExperimentController(InterfaceExperimentView theView, Experiment theModel) {

		this.theView = theView;

		this.theModel = theModel;

		this.theModel.registerListener(this);
		this.theView.registerListener(this);

		theView.onClickUploadFileNo(e -> {
			DBmySql_connection conn = new DBmySql_connection();
			conn.deleteCompany();
			theView.companyName();
			
			conn.deleteAllDepartments();
			conn.deleteAll_dep_role_employeelist_tbl();
			
			conn.deleteAll_dep_rolelist_tbl();
			conn.deleteAll_emp_roleList_tbl();
			
			
			
		});

		theView.onClickUploadFileYes(e -> {
			boolean flag = false;
			String companyName = "";
			try {
				companyName = theModel.loadOldData();
				flag = true;
			} catch (ClassNotFoundException | IOException | IllegalArgumentException| InvocationTargetException| NoSuchMethodException| SecurityException  e1) {
				theView.experimentMessage("There isn't a file to upload! ");
				e1.printStackTrace();
				flag = false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (flag) {
				theView.mainWindow(companyName);
			}
		});
	}

	@Override
	public void addCompanyToModelEvent(String companyName) {
		theView.mainWindow(companyName);
	}

	@Override
	public void addCompanyToUI(String name) {

		try {
			theModel.addCompany(name);

		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	@Override
	public void addDepartmentToModelEvent(String name, Department newDepartment) {
		theView.addDepartmentToUI(name, newDepartment);
	}

	@Override
	public void addDepartmentToUI(String name) {

		try {
			theModel.addDepartment(name, true);

		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	@Override
	public void addRoleToModelEvent(String name, Role newRole) {
		theView.addRoleToUI(name, newRole);
	}

	@Override
	public void addRoleToUI(String name, String departmentName, int startHour) {

		try {
			theModel.addRole(name, departmentName, startHour);

		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	@Override
	public void addEmployeeToModelEvent(Employee newEmployee) {
		theView.addEmployeeToUI(newEmployee);
	}

	@Override
	public void addEmployeeToUI(Employee employee, String departmentName, String roleName) {

		try {
			theModel.addEmployee(employee, departmentName, roleName, true);

		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	@Override
	public void hardCodedMaking(String name) {
		try {
			theModel.hardCoded(name);
		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	@Override
	public boolean isRoleChangeable(String department, String role) {
		return theModel.isThisRoleChangeable(department, role);

	}
	
	@Override
	public boolean isDepartmentChangeable(String department) {
		return theModel.isThisDepartmentChangeable(department);

	}

	@Override
	public String getAllData() {
		return theModel.toString();
	}

	@Override
	public Vector<String> getTheRoleInDepartment(String departmentName) {
		return theModel.getAllRolesInDepartment(departmentName);
	}

	@Override
	public void roleSynchronizeToUI(String departmetnName, String roleName, int startHour) {
		try {
			theModel.roleSynchronize(departmetnName, roleName, startHour);
		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	@Override
	public void departmentSynchronizeToUI(String departmentName, int startHour) {
		try {
			theModel.departmentSynchronize(departmentName, startHour);
		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	@Override
	public void roleWorkChangeToUI(String departmnetName, String roleName, boolean workChange) {
		try {
			theModel.roleWorkChange(departmnetName, roleName, workChange);
		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	@Override
	public void workChangeHourToUI(String department, String role, int startHour) {
		theModel.setChanegeHourByRole(department, role, startHour);
	}
	
	@Override
	public void workChangeHourByDepartmentToUI(String department, int startHour) {
		theModel.setChanegeHourByDepartment(department, startHour);
	}

	@Override
	public void departmentWorkChangeToUI(String departmentName, boolean workChange) {
		try {
			theModel.departmentWorkChange(departmentName, workChange);
		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	public void employeeWorkChangeToUI(String departmentName, String roleName, String employeeName,
			boolean workChange) {
		try {
			theModel.employeeWorkChange(departmentName, roleName, employeeName, workChange);
		} catch (Exception e) {
			theView.experimentMessage(e.getMessage());
		}
	}

	@Override
	public String employeeEfficiencyToUI() {
		theModel.employeeEfficiency();
		theModel.departmentEfficiency();
		theModel.companyEfficiency();
		return theModel.expirementResults();
	}
	
	@Override
	public void saveFileToUI() throws FileNotFoundException, IOException {
		theModel.saveToFile(theModel);
		
	}


}