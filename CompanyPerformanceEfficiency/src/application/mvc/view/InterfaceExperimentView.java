package application.mvc.view;

import java.util.Vector;

import application.mvc.listeners.ExperimentUIEventsListener;
import application.mvc.model.Company;
import application.mvc.model.Department;
import application.mvc.model.Employee;
import application.mvc.model.Role;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public interface InterfaceExperimentView {

	void registerListener(ExperimentUIEventsListener newListener);

	void mainWindow(String companyName);

	void experimentMessage(String str);

	void addDepartmentToUI(String name, Department newDepartment);

	void addRoleToUI(String name, Role newRole);

	Vector<String> getRolesInDepartment(String departmentName);

	void addEmployeeToUI(Employee employee);

	void companyName();

	void onClickUploadFileNo(EventHandler<ActionEvent> e);

	void onClickUploadFileYes(EventHandler<ActionEvent> e);
	
	void messageAlert(String msg);

	String getData();

	void showLayout(ListView listView, String Title); 

}
