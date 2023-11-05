package application.mvc.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import application.mvc.model.Employee;
import javafx.scene.control.ComboBox;

public interface ExperimentUIEventsListener {

	void addDepartmentToUI(String name);

	void addRoleToUI(String name, String departmentName, int startHour);

	void addCompanyToUI(String companyName);

	void addEmployeeToUI(Employee employee, String departmentName, String roleName);

	Vector<String> getTheRoleInDepartment(String departmentName);

	String getAllData();

	void hardCodedMaking(String companyName);

	void roleSynchronizeToUI(String departmentName, String roleName, int startHour);

	void departmentSynchronizeToUI(String departmentName, int startHour);

	void departmentWorkChangeToUI(String departmentName, boolean workChange);

	void roleWorkChangeToUI(String departmnetName, String roleName, boolean workChange);

	void employeeWorkChangeToUI(String value, String value2, String text, boolean b); 

	String employeeEfficiencyToUI();

	boolean isRoleChangeable(String value, String value2);

	void workChangeHourToUI(String cmbAllDepartment, String cmbAllRoles, int getStartHour);

	boolean isDepartmentChangeable(String value);

	void workChangeHourByDepartmentToUI(String value, int getStartHour);

	void saveFileToUI() throws FileNotFoundException, IOException;


}
