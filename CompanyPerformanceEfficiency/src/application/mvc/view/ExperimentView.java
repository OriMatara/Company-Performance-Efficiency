package application.mvc.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.Vector;

import application.mvc.listeners.ExperimentUIEventsListener;
import application.mvc.model.BasePlusPercentageSalaryEmployee;
import application.mvc.model.BaseSalaryEmployee;
import application.mvc.model.Company;
import application.mvc.model.DBmySql_connection;

import application.mvc.model.Department;
import application.mvc.model.Employee;
import application.mvc.model.Experiment;
import application.mvc.model.Employee.prefrences;
import application.mvc.model.HourSalaryEmployee;

import application.mvc.model.Role;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExperimentView implements Serializable, InterfaceExperimentView {

	// Vectors & Combo box
	private Vector<ExperimentUIEventsListener> allListeners = new Vector<ExperimentUIEventsListener>();
	private ComboBox<String> cmbAllDepartment = new ComboBox<String>();
	private ComboBox<String> cmbAllRoles = new ComboBox<String>();

	// List view
	private ListView listViewDepartment = new ListView();
	private ListView listViewRole = new ListView();
	private ListView listViewEmployee = new ListView();
	private ListView listOfAllResults = new ListView();
	private ListView listViewDataBase = new ListView();
	// Enums
	prefrences tempPrefrence;

	// VBox
	private VBox layOutScene = new VBox();

	// Checkbox
	private CheckBox cbDepartmentWorkChange;
	private CheckBox cbDepartmentIsSynschronized;
	private ComboBox<String> cbDepartmentCombo;
	private ComboBox<String> cbRoleCombo;

	// Stage
	private Stage firstWindow;
	private Stage departmenWindow;
	private Stage companyNameWindow;

	// Label
	private Label lblCompanyName = new Label("");
	private Label lblName;
	private Label lblWelcome;
	private Label lblMenu;

	// TextFiels
	private TextField tfDepartmentName;
	private TextField tfName;

	// Button
	private Button btnAddRole;
	private Button btnAddEmployee;
	private Button btnSubmit;
	private Button btnAddDepartment;
	private Button btnOk;
	private Button btnUploadFileYes;
	private Button btnUploadFileNo;
	private Button btnShowDepartmentsAndRoles;
	private Button btnChangeWorkMethodByRole;
	private Button btnChangeWorkMethodByDepartments;
	private Button btnShowExperimentResults;
	private Button btnSubmitAddDepartment;
	private Button btnShowAllEmployees;
	private Button btnDeletCompany;
	private Button btnSaveAndExit;

	public ExperimentView(Stage theStage) {

		GridPane gpRoot = new GridPane();
		
		firstWindow = theStage;
		companyNameWindow = theStage;

		lblWelcome = new Label("Welcome! ");
		lblWelcome.setStyle(
				"-fx-font-family: TRON; -fx-font-size: 14; -fx-font-style: italic; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0 0 2 0;");

		lblName = new Label("Please Enter the Company name: ");
		tfName = new TextField();

		btnAddRole = new Button("Add Role");
		btnAddEmployee = new Button("Add Employee");
		btnAddDepartment = new Button("Add Department");
		btnShowDepartmentsAndRoles = new Button("Show Departments And Roles");
		btnShowAllEmployees = new Button("Show All Employees");
		btnChangeWorkMethodByRole = new Button("Change Work Method By Role");
		btnChangeWorkMethodByDepartments = new Button("Change Work Method By Departments");
		btnShowExperimentResults = new Button("Show Experiment Results");
		btnDeletCompany = new Button("delete Company from DB");
		btnSaveAndExit = new Button("Save And Exit");
		lblMenu = new Label("do you want to upload a file information?");
		lblMenu.setStyle(
				"-fx-font-family: TRON; -fx-font-size: 14; -fx-font-style: italic; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0 0 2 0;");

		btnUploadFileYes = new Button("Yes");
		btnUploadFileNo = new Button("No");

		btnOk = new Button("OK");

		gpRoot.add(lblMenu, 0, 0);
		gpRoot.add(btnUploadFileNo, 2, 3);
		gpRoot.add(btnUploadFileYes, 1, 3);

		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);

		theStage.setScene(new Scene(gpRoot, 450, 150));
		theStage.show();

	}

	@Override
	public void companyName() {

		GridPane gpRoot = new GridPane();

		btnOk = new Button("OK");

		btnOk.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {

				String companyName = tfName.getText();

				for (int i = 0; i < allListeners.size(); i++) {

					if (tfName.getText().isEmpty()) {
						experimentMessage("you most give a name to the Company ");

					} else {
						allListeners.elementAt(i).addCompanyToUI(companyName);
						allListeners.elementAt(i).hardCodedMaking(companyName);

					}
				}
			}
		});

		gpRoot.add(btnOk, 2, 3);

		gpRoot.add(lblWelcome, 0, 1);
		gpRoot.add(lblName, 0, 2);
		gpRoot.add(tfName, 0, 3);

		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);

		companyNameWindow.setScene(new Scene(gpRoot, 300, 150));
		companyNameWindow.show();

	}

	@Override
	public void registerListener(ExperimentUIEventsListener newListener) {
		allListeners.add(newListener);
	}

	@Override
	public void mainWindow(String companyName) {

		GridPane gpRoot = new GridPane();

		lblWelcome = new Label("Welcome " + companyName + "!");
		lblWelcome.setStyle(
				"-fx-font-family: TRON; -fx-font-size: 14; -fx-font-style: italic; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0 0 2 0;");

		btnAddDepartment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {

				GridPane gpRoot = new GridPane();

				Stage departmentWindow = new Stage();

				Label lblDepartmentName = new Label("Department Name: ");

				Label lblDepartmentTimeSynchronized = new Label(
						"Enter start hour for the synchronized department between 0-23: ");

				TextField tfDepartmentName = new TextField();

				CheckBox cbDepartmentIsSynschronized = new CheckBox("Department is Synschronized");
				CheckBox cbDepartmentWorkChange = new CheckBox("Department can change work ");

				TextField tfDepartmentStartHour = new TextField();

				tfDepartmentStartHour.setDisable(true);

				cbDepartmentIsSynschronized.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent action) {

						if (cbDepartmentIsSynschronized.isSelected()) {
							tfDepartmentStartHour.setDisable(false);
						} else {
							tfDepartmentStartHour.setDisable(true);
						}

					}
				});

				tfDepartmentName.setPromptText("name");

				Button btnSubmitAddDepartment = new Button("SUBMIT");

				btnSubmitAddDepartment.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent action) {
						departmentWindow.setAlwaysOnTop(false);
						int startHour = 0;
						String departmentName = tfDepartmentName.getText();
						for (int i = 0; i < allListeners.size(); i++) {

							if (tfDepartmentName.getText().isEmpty()) {
								experimentMessage("You must give a start hour between 0-23!");
								departmentWindow.setAlwaysOnTop(true);
							} else if (cbDepartmentWorkChange.isSelected() == false
									&& cbDepartmentIsSynschronized.isSelected() == false) {
								allListeners.elementAt(i).addDepartmentToUI(departmentName);
								departmentWindow.close();
								messageAlert("The Department successfully added! ");
							} else {
								if (cbDepartmentWorkChange.isSelected() && cbDepartmentIsSynschronized.isSelected()) {
									if (tfDepartmentStartHour.getText().isEmpty()) {
										experimentMessage("You must give a start hour between 0-23!");
										departmentWindow.setAlwaysOnTop(true);
									} else {
										try {
											startHour = Integer.parseInt(tfDepartmentStartHour.getText());

										} catch (NumberFormatException e) {
											experimentMessage("You must give a start hour between 0-23!");
											departmentWindow.setAlwaysOnTop(true);
											break;
										}
										if (startHour >= 0 && startHour <= 23) {
											allListeners.elementAt(i).addDepartmentToUI(departmentName);
											allListeners.elementAt(i).departmentSynchronizeToUI(departmentName,
													startHour);
											allListeners.elementAt(i).departmentWorkChangeToUI(departmentName, true);
											departmentWindow.close();
											messageAlert("The Department successfully added! ");
										} else {
											experimentMessage("You must give a start hour between 0-23!");
											departmentWindow.setAlwaysOnTop(true);
										}
									}
								} else if (cbDepartmentIsSynschronized.isSelected()
										&& cbDepartmentWorkChange.isSelected() == false) {
									if (tfDepartmentStartHour.getText().isEmpty()) {
										experimentMessage("You must give a start hour between 0-23!");
										departmentWindow.setAlwaysOnTop(true);
									} else {
										try {
											startHour = Integer.parseInt(tfDepartmentStartHour.getText());
										} catch (NumberFormatException e) {
											experimentMessage("You must give a start hour between 0-23!");
											departmentWindow.setAlwaysOnTop(true);
											break;
										}
										if (startHour >= 0 && startHour <= 23) {
											allListeners.elementAt(i).addDepartmentToUI(departmentName);
											allListeners.elementAt(i).departmentSynchronizeToUI(departmentName,
													startHour);
											departmentWindow.close();
											messageAlert("The Department successfully added! ");
										} else {
											experimentMessage("You must give a start hour between 0-23!");
											departmentWindow.setAlwaysOnTop(true);
										}
									}
								} else {
									allListeners.elementAt(i).addDepartmentToUI(departmentName);
									allListeners.elementAt(i).departmentWorkChangeToUI(departmentName, true);
									departmentWindow.close();
									messageAlert("The Department successfully added! ");
								}
							}
						}
					}
				});

				gpRoot.add(lblDepartmentName, 0, 0);

				gpRoot.add(tfDepartmentName, 0, 1);

				gpRoot.add(cbDepartmentIsSynschronized, 0, 2);

				gpRoot.add(lblDepartmentTimeSynchronized, 0, 3);

				gpRoot.add(tfDepartmentStartHour, 0, 4);
				gpRoot.add(cbDepartmentWorkChange, 0, 5);

				gpRoot.add(btnSubmitAddDepartment, 1, 6);

				gpRoot.setPadding(new Insets(10));
				gpRoot.setHgap(15);
				gpRoot.setVgap(10);

				departmentWindow.setTitle("Add Department ");
				departmentWindow.setAlwaysOnTop(false);
				departmentWindow.setScene(new Scene(gpRoot, 450, 250));
				departmentWindow.show();

			}
		});

		btnAddRole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {

				GridPane gpRoot = new GridPane();

				Stage roleWindow = new Stage();

				Label lblRoleName = new Label("Role Name: ");

				Label lblRoleDepartment = new Label("In Department: ");

				Label lblRoleStartHour = new Label("Enter start hour for role between 0-23: ");

				TextField tfRoleName = new TextField();

				TextField tfStartHour = new TextField();

				CheckBox cbRoleIsSynschronized = new CheckBox("Role is Synschronized");
				CheckBox cbRoleWorkChange = new CheckBox("Role can change work ");

				tfRoleName.setPromptText("name");

				Button btnSubmitAddRole = new Button("SUBMIT");

				btnSubmitAddRole.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent action) {
						roleWindow.setAlwaysOnTop(false);
						int startHour = 0;
						String roleName = tfRoleName.getText();

						String departmentName = cmbAllDepartment.getValue();

						for (int i = 0; i < allListeners.size(); i++) {

							if (tfRoleName.getText().isEmpty()) {
								experimentMessage("you most give a name to the role ");
								roleWindow.setAlwaysOnTop(true);
							} else if (tfStartHour.getText().isEmpty()) {
								experimentMessage("You must give a start hour between 0-23!");
								roleWindow.setAlwaysOnTop(true);
							} else if (cmbAllDepartment.getSelectionModel().getSelectedItem() == null) {
								experimentMessage("You must choose department!");
								roleWindow.setAlwaysOnTop(true);
							} else {
								try {
									startHour = Integer.parseInt(tfStartHour.getText());

								} catch (NumberFormatException e) {
									experimentMessage("You must give a start hour between 0-23!");
									roleWindow.setAlwaysOnTop(true);
									break;
								}
								if (startHour >= 0 && startHour <= 23) {
									allListeners.elementAt(i).addRoleToUI(roleName, departmentName, startHour);
									if (cbRoleWorkChange.isSelected()) {

										allListeners.elementAt(i).roleWorkChangeToUI(departmentName, roleName, true);

									}
									if (cbRoleIsSynschronized.isSelected()) {
										allListeners.elementAt(i).roleSynchronizeToUI(departmentName, roleName,
												startHour);

									}
									roleWindow.close();
									messageAlert("The role successfully added! ");
								} else {
									experimentMessage("You must give a start hour between 0-23!");
									roleWindow.setAlwaysOnTop(true);
								}

							}
						}
					}
				});

				gpRoot.add(lblRoleName, 0, 0);

				gpRoot.add(lblRoleDepartment, 1, 0);

				gpRoot.add(cmbAllDepartment, 1, 1);

				gpRoot.add(tfRoleName, 0, 1);

				gpRoot.add(lblRoleStartHour, 0, 2);

				gpRoot.add(tfStartHour, 0, 3);

				gpRoot.add(cbRoleIsSynschronized, 0, 4);

				gpRoot.add(cbRoleWorkChange, 0, 5);
				gpRoot.add(btnSubmitAddRole, 1, 6);

				gpRoot.setPadding(new Insets(10));
				gpRoot.setHgap(15);
				gpRoot.setVgap(10);

				roleWindow.setTitle("Add Role ");
				roleWindow.setAlwaysOnTop(false);
				roleWindow.setScene(new Scene(gpRoot, 400, 250));
				roleWindow.show();

			}
		});

		btnAddEmployee.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {

				GridPane gpRoot = new GridPane();

				Stage employeeWindow = new Stage();

				Label lblEmployeeName = new Label("Employee name: ");

				TextField tfEmployeeName = new TextField("");

				Label lblEmployeeAge = new Label("Employee age: ");

				TextField tfEmployeeAge = new TextField("");

				Label lblDepartment = new Label("In which department are you: ");

				Label lblRole = new Label("In which role are you: ");

				ToggleGroup tgSalaryKind = new ToggleGroup();

				Label lblSalaryKind = new Label("What is your salary kind: ");

				RadioButton rbHourlySalary = new RadioButton("Hourly salary ");
				RadioButton rbBaseSalary = new RadioButton("Base salary");
				RadioButton rbBasePlusPercentage = new RadioButton("Base plus percentage");

				TextField tfSalary = new TextField();

				tfSalary.setDisable(true);

				Label lblPrefrence = new Label("Witch prefrence do you prefere: ");

				rbHourlySalary.setToggleGroup(tgSalaryKind);
				rbBaseSalary.setToggleGroup(tgSalaryKind);
				rbBasePlusPercentage.setToggleGroup(tgSalaryKind);

				cmbAllRoles.getItems().addAll();

				cmbAllDepartment.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (cmbAllDepartment.getValue() != null) {
							cmbAllRoles.getItems().clear();
							cmbAllRoles.getItems().addAll(getRolesInDepartment(cmbAllDepartment.getValue()));
						}
					}
				});

				tgSalaryKind.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
					@Override
					public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {

						tfSalary.setDisable(false);
					}
				});

				ToggleGroup tgPrefrence = new ToggleGroup();

				RadioButton rbEarly = new RadioButton("Early- (0-7)");
				RadioButton rbLate = new RadioButton("Late- (9-23)");
				RadioButton rbStayHome = new RadioButton("Stay home- (0-23)");
				RadioButton rbNoChange = new RadioButton("No change- (8)");

				rbEarly.setToggleGroup(tgPrefrence);
				rbLate.setToggleGroup(tgPrefrence);
				rbStayHome.setToggleGroup(tgPrefrence);
				rbNoChange.setToggleGroup(tgPrefrence);

				Label lblStartHour = new Label("What time you want to start?");

				Label lblMonthSalary = new Label("Month salary: ");

				TextField tfStart = new TextField();

				tfStart.setDisable(true);

				CheckBox cbEmployeeIsSynschronized = new CheckBox("Employee is synschronized");
				CheckBox cbEmployeeWorkChange = new CheckBox("Employee can change work ");

				tgPrefrence.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
					@Override
					public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {

						if (tgPrefrence.getSelectedToggle() == rbEarly) {
							tempPrefrence = Employee.prefrences.EARLY;
							tfStart.setDisable(false);

						} else if (tgPrefrence.getSelectedToggle() == rbLate) {
							tempPrefrence = Employee.prefrences.LATE;
							tfStart.setDisable(false);

						} else if (tgPrefrence.getSelectedToggle() == rbStayHome) {
							tempPrefrence = Employee.prefrences.WORK_FROM_HOME;
							tfStart.setDisable(false);

						} else {
							tempPrefrence = Employee.prefrences.NO_CHANGE;
							tfStart.setDisable(true);
							tfStart.setText("8");
						}
					}
				});

				tfEmployeeName.setPromptText("name");

				Button btnSubmitAddEmployee = new Button("SUBMIT");

				btnSubmitAddEmployee.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent action) {
						employeeWindow.setAlwaysOnTop(false);
						Employee tempEmployee = null;
						int tempAge = 0;
						int tempSalary = 0;
						int tempStartHour = 0;

						for (int i = 0; i < allListeners.size(); i++) {

							if (tfEmployeeName.getText().isEmpty()) {
								experimentMessage("you most give a name to the employee ");
								employeeWindow.setAlwaysOnTop(true);
							} else if (tfEmployeeAge.getText().isEmpty()) {
								experimentMessage("you most give an age to the employee");
								employeeWindow.setAlwaysOnTop(true);
							} else if (tfEmployeeAge.getText().isEmpty() == false) {
								try {
									tempAge = Integer.parseInt(tfEmployeeAge.getText());

								} catch (NumberFormatException e) {
									experimentMessage("You must give a real age");
									employeeWindow.setAlwaysOnTop(true);
									break;
								}
								if (tempAge > 0) {
									if (cmbAllDepartment.getSelectionModel().getSelectedItem() == null) {
										experimentMessage("You must choose department!");
										employeeWindow.setAlwaysOnTop(true);
									} else if (cmbAllRoles.getSelectionModel().getSelectedItem() == null) {
										experimentMessage("You must choose role!");
										employeeWindow.setAlwaysOnTop(true);
									} else if (tgSalaryKind.getSelectedToggle() == null) {
										experimentMessage("You must choose a salary kind!");
										employeeWindow.setAlwaysOnTop(true);
									} else if (tfSalary.getText().isEmpty()) {
										experimentMessage("you most give an salary to the employee");
										employeeWindow.setAlwaysOnTop(true);
									} else {
										try {
											tempSalary = Integer.parseInt(tfSalary.getText());

										} catch (NumberFormatException e) {
											experimentMessage("You must give a real salary");
											employeeWindow.setAlwaysOnTop(true);
											break;
										}
										if (tempSalary > 0) {
											if (tgPrefrence.getSelectedToggle() == null) {
												experimentMessage("You must choose a preference!");
												employeeWindow.setAlwaysOnTop(true);
											} else {
												if (tgPrefrence.getSelectedToggle().equals(rbEarly)) {
													if (tfStart.getText().isEmpty()) {
														experimentMessage("you most give a start hour");
														employeeWindow.setAlwaysOnTop(true);
													} else {
														try {
															tempStartHour = Integer.parseInt(tfStart.getText());

														} catch (NumberFormatException e) {
															experimentMessage("You must give a real start hour");
															employeeWindow.setAlwaysOnTop(true);
															break;
														}
														if (tempStartHour >= 0 && tempStartHour <= 7) {
															if (tgSalaryKind.getSelectedToggle() == rbBaseSalary) {
																tempEmployee = new BaseSalaryEmployee(
																		tfEmployeeName.getText(), tempAge,
																		cmbAllRoles.getValue(),
																		cmbAllDepartment.getValue(), tempPrefrence,
																		tempStartHour, tempSalary);

															} else if (tgSalaryKind
																	.getSelectedToggle() == rbHourlySalary) {
																tempEmployee = new HourSalaryEmployee(
																		tfEmployeeName.getText(), tempAge,
																		cmbAllRoles.getValue(),
																		cmbAllDepartment.getValue(), tempPrefrence,
																		tempStartHour, tempSalary);
															}

															else if (tgSalaryKind
																	.getSelectedToggle() == rbBasePlusPercentage) {
																tempEmployee = new BasePlusPercentageSalaryEmployee(
																		tfEmployeeName.getText(), tempAge,
																		cmbAllRoles.getValue(),
																		cmbAllDepartment.getValue(), tempPrefrence,
																		tempStartHour, tempSalary);
															}

															allListeners.elementAt(i).addEmployeeToUI(tempEmployee,
																	cmbAllDepartment.getValue(),
																	cmbAllRoles.getValue());

															if (cbEmployeeWorkChange.isSelected()) {
																allListeners.elementAt(i).employeeWorkChangeToUI(
																		cmbAllDepartment.getValue(),
																		cmbAllRoles.getValue(),
																		tfEmployeeName.getText(), true);
															}

															employeeWindow.close();
															messageAlert("The Employee successfully added!");

														} else {
															experimentMessage(
																	"You must give a start hour between 0-7!");
															employeeWindow.setAlwaysOnTop(true);
														}
													}
												} else if (tgPrefrence.getSelectedToggle().equals(rbLate)) {
													if (tfStart.getText().isEmpty()) {
														experimentMessage("you most give a start hour");
														employeeWindow.setAlwaysOnTop(true);
													} else {
														try {
															tempStartHour = Integer.parseInt(tfStart.getText());

														} catch (NumberFormatException e) {
															experimentMessage("You must give a real start hour");
															employeeWindow.setAlwaysOnTop(true);
															break;
														}
														if (tempStartHour >= 9 && tempStartHour <= 23) {
															if (tgSalaryKind.getSelectedToggle() == rbBaseSalary) {
																tempEmployee = new BaseSalaryEmployee(
																		tfEmployeeName.getText(), tempAge,
																		cmbAllRoles.getValue(),
																		cmbAllDepartment.getValue(), tempPrefrence,
																		tempStartHour, tempSalary);

															} else if (tgSalaryKind
																	.getSelectedToggle() == rbHourlySalary) {
																tempEmployee = new HourSalaryEmployee(
																		tfEmployeeName.getText(), tempAge,
																		cmbAllRoles.getValue(),
																		cmbAllDepartment.getValue(), tempPrefrence,
																		tempStartHour, tempSalary);
															}

															else if (tgSalaryKind
																	.getSelectedToggle() == rbBasePlusPercentage) {
																tempEmployee = new BasePlusPercentageSalaryEmployee(
																		tfEmployeeName.getText(), tempAge,
																		cmbAllRoles.getValue(),
																		cmbAllDepartment.getValue(), tempPrefrence,
																		tempStartHour, tempSalary);
															}

															allListeners.elementAt(i).addEmployeeToUI(tempEmployee,
																	cmbAllDepartment.getValue(),
																	cmbAllRoles.getValue());

															if (cbEmployeeWorkChange.isSelected()) {
																allListeners.elementAt(i).employeeWorkChangeToUI(
																		cmbAllDepartment.getValue(),
																		cmbAllRoles.getValue(),
																		tfEmployeeName.getText(), true);
															}

															employeeWindow.close();
															messageAlert("The Employee successfully added!");

														} else {
															experimentMessage(
																	"You must give a start hour between 9-23!");
															employeeWindow.setAlwaysOnTop(true);
														}
													}
												} else if (tgPrefrence.getSelectedToggle().equals(rbStayHome)) {
													if (tfStart.getText().isEmpty()) {
														experimentMessage("you most give a start hour");
														employeeWindow.setAlwaysOnTop(true);
													} else {
														try {
															tempStartHour = Integer.parseInt(tfStart.getText());

														} catch (NumberFormatException e) {
															experimentMessage("You must give a real start hour");
															employeeWindow.setAlwaysOnTop(true);
															break;
														}
														if (tempStartHour >= 0 && tempStartHour <= 23) {
															if (tgSalaryKind.getSelectedToggle() == rbBaseSalary) {
																tempEmployee = new BaseSalaryEmployee(
																		tfEmployeeName.getText(), tempAge,
																		cmbAllRoles.getValue(),
																		cmbAllDepartment.getValue(), tempPrefrence,
																		tempStartHour, tempSalary);

															} else if (tgSalaryKind
																	.getSelectedToggle() == rbHourlySalary) {
																tempEmployee = new HourSalaryEmployee(
																		tfEmployeeName.getText(), tempAge,
																		cmbAllRoles.getValue(),
																		cmbAllDepartment.getValue(), tempPrefrence,
																		tempStartHour, tempSalary);
															}

															else if (tgSalaryKind
																	.getSelectedToggle() == rbBasePlusPercentage) {
																tempEmployee = new BasePlusPercentageSalaryEmployee(
																		tfEmployeeName.getText(), tempAge,
																		cmbAllRoles.getValue(),
																		cmbAllDepartment.getValue(), tempPrefrence,
																		tempStartHour, tempSalary);
															}

															allListeners.elementAt(i).addEmployeeToUI(tempEmployee,
																	cmbAllDepartment.getValue(),
																	cmbAllRoles.getValue());

															if (cbEmployeeWorkChange.isSelected()) {
																allListeners.elementAt(i).employeeWorkChangeToUI(
																		cmbAllDepartment.getValue(),
																		cmbAllRoles.getValue(),
																		tfEmployeeName.getText(), true);
															}

															employeeWindow.close();
															messageAlert("The Employee successfully added!");

														} else {
															experimentMessage(
																	"You must give a start hour between 0-23!");
															employeeWindow.setAlwaysOnTop(true);
														}
													}
												} else {
													tempStartHour = 8;
													if (tgSalaryKind.getSelectedToggle() == rbBaseSalary) {
														tempEmployee = new BaseSalaryEmployee(tfEmployeeName.getText(),
																tempAge, cmbAllRoles.getValue(),
																cmbAllDepartment.getValue(), tempPrefrence,
																tempStartHour, tempSalary);

													} else if (tgSalaryKind.getSelectedToggle() == rbHourlySalary) {
														tempEmployee = new HourSalaryEmployee(tfEmployeeName.getText(),
																tempAge, cmbAllRoles.getValue(),
																cmbAllDepartment.getValue(), tempPrefrence,
																tempStartHour, tempSalary);
													}

													else if (tgSalaryKind.getSelectedToggle() == rbBasePlusPercentage) {
														tempEmployee = new BasePlusPercentageSalaryEmployee(
																tfEmployeeName.getText(), tempAge,
																cmbAllRoles.getValue(), cmbAllDepartment.getValue(),
																tempPrefrence, tempStartHour, tempSalary);
													}

													allListeners.elementAt(i).addEmployeeToUI(tempEmployee,
															cmbAllDepartment.getValue(), cmbAllRoles.getValue());

													if (cbEmployeeWorkChange.isSelected()) {
														allListeners.elementAt(i).employeeWorkChangeToUI(
																cmbAllDepartment.getValue(), cmbAllRoles.getValue(),
																tfEmployeeName.getText(), true);
													}

													employeeWindow.close();
													messageAlert("The Employee successfully added!");

												}

											}
										} else {
											experimentMessage("You must give a real salary");
											employeeWindow.setAlwaysOnTop(true);
										}
									}

								} else {
									experimentMessage("You must give a real age");
									employeeWindow.setAlwaysOnTop(true);
								}
							}
						}

					}
				});

				gpRoot.add(lblEmployeeName, 0, 0);

				gpRoot.add(tfEmployeeName, 0, 1);

				gpRoot.add(lblEmployeeAge, 0, 2);

				gpRoot.add(tfEmployeeAge, 0, 3);

				gpRoot.add(lblDepartment, 0, 4);

				gpRoot.add(cmbAllDepartment, 0, 5);

				gpRoot.add(lblRole, 1, 4);

				gpRoot.add(cmbAllRoles, 1, 5);

				gpRoot.add(lblSalaryKind, 0, 6);

				gpRoot.add(rbHourlySalary, 0, 7);

				gpRoot.add(rbBaseSalary, 0, 8);

				gpRoot.add(rbBasePlusPercentage, 0, 9);

				gpRoot.add(lblMonthSalary, 0, 10);

				gpRoot.add(tfSalary, 0, 11);

				gpRoot.add(lblPrefrence, 0, 12);

				gpRoot.add(rbEarly, 0, 13);

				gpRoot.add(rbLate, 0, 14);

				gpRoot.add(rbStayHome, 0, 15);

				gpRoot.add(rbNoChange, 0, 16);

				gpRoot.add(lblStartHour, 0, 17);

				gpRoot.add(tfStart, 0, 18);

				gpRoot.add(cbEmployeeIsSynschronized, 0, 19);
				gpRoot.add(cbEmployeeWorkChange, 0, 20);

				gpRoot.add(btnSubmitAddEmployee, 1, 20);

				gpRoot.setPadding(new Insets(10));
				gpRoot.setHgap(15);
				gpRoot.setVgap(10);

				employeeWindow.setTitle("Add Empoloyee ");
				employeeWindow.setAlwaysOnTop(false);
				employeeWindow.setScene(new Scene(gpRoot, 400, 680));
				employeeWindow.show();

			}
		});

		btnChangeWorkMethodByRole.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				GridPane gpRoot = new GridPane();

				Label lblChooseDepartment = new Label("Choose department: ");

				Label lblChooseRole = new Label("Choose role: ");

				TextField tfChangeWorkHour = new TextField();

				Label lblChangeWorkHour = new Label("What time you want to start? (choose between 0-23) ");

				tfChangeWorkHour.setDisable(true);

				Button btnSubmitChangeWorkMethodByRole = new Button("SUBMIT");

				Label lblCantChnageRole = new Label("The working method cant change in this role! ");

				lblCantChnageRole.setStyle(" -fx-font-style: italic; -fx-text-fill:red; -fx-font-weight: bold;");

				Stage changeWorkWindow = new Stage();

				cmbAllRoles.getItems().addAll();

				cmbAllDepartment.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (cmbAllDepartment.getValue() != null) {
							cmbAllRoles.getItems().clear();
							cmbAllRoles.getItems().addAll(getRolesInDepartment(cmbAllDepartment.getValue()));
						}
					}
				});

				cmbAllRoles.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						for (int i = 0; i < allListeners.size(); i++) {
							if (allListeners.elementAt(i).isRoleChangeable(cmbAllDepartment.getValue(),
									cmbAllRoles.getValue()) == false) {
								gpRoot.add(lblCantChnageRole, 1, 3);
								tfChangeWorkHour.setDisable(true);

							} else if (cmbAllRoles.getValue() == null) {
								tfChangeWorkHour.setDisable(true);
								gpRoot.add(lblCantChnageRole, 1, 3);

							} else {
								tfChangeWorkHour.setDisable(false);
								gpRoot.getChildren().remove(lblCantChnageRole);

							}
						}
					}
				});

				btnSubmitChangeWorkMethodByRole.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						changeWorkWindow.setAlwaysOnTop(false);
						int getStartHour = 0;

						for (int i = 0; i < allListeners.size(); i++) {
							if (cmbAllDepartment.getSelectionModel().getSelectedItem() == null) {
								experimentMessage("You must choose department!");
								changeWorkWindow.setAlwaysOnTop(true);
							} else if (cmbAllRoles.getSelectionModel().getSelectedItem() == null) {
								experimentMessage("You must choose role!");
								changeWorkWindow.setAlwaysOnTop(true);
							} else if (tfChangeWorkHour.getText().isEmpty()) {
								messageAlert("You most enter a start hour!");
								changeWorkWindow.setAlwaysOnTop(true);
							} else {
								try {
									getStartHour = Integer.parseInt(tfChangeWorkHour.getText());

								} catch (NumberFormatException e) {
									experimentMessage("You must give a real start hour!");
									changeWorkWindow.setAlwaysOnTop(true);
									break;
								}
								if (getStartHour >= 0 && getStartHour <= 23) {
									allListeners.elementAt(i).workChangeHourToUI(cmbAllDepartment.getValue(),
											cmbAllRoles.getValue(), getStartHour);
									changeWorkWindow.close();
									messageAlert("The Role hour successfully changed! ");
								} else {
									experimentMessage("You must give a start hour between 0-23!");
									changeWorkWindow.setAlwaysOnTop(true);
								}
							}
						}
					}

				});

				gpRoot.add(lblChooseDepartment, 0, 0);
				gpRoot.add(cmbAllDepartment, 0, 1);
				gpRoot.add(lblChooseRole, 0, 2);
				gpRoot.add(cmbAllRoles, 0, 3);
				gpRoot.add(lblChangeWorkHour, 0, 4);
				gpRoot.add(tfChangeWorkHour, 0, 5);
				gpRoot.add(btnSubmitChangeWorkMethodByRole, 1, 6);

				gpRoot.setPadding(new Insets(10));
				gpRoot.setHgap(15);
				gpRoot.setVgap(10);

				changeWorkWindow.setTitle("Change work method by role ");
				changeWorkWindow.setAlwaysOnTop(false);
				changeWorkWindow.setScene(new Scene(gpRoot, 470, 250));
				changeWorkWindow.show();
			}
		});

		btnChangeWorkMethodByDepartments.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				GridPane gpRoot = new GridPane();

				Label lblChooseDepartment = new Label("Choose department: ");

				TextField tfChangeWorkHour = new TextField();

				Label lblChangeWorkHour = new Label("What time you want to start? (choose between 0-23) ");

				tfChangeWorkHour.setDisable(true);

				Button btnSubmitChangeWorkMethodByDepartment = new Button("SUBMIT");

				Label lblCantChnageDepartment = new Label("The working method cant change in this department! ");

				lblCantChnageDepartment.setStyle(" -fx-font-style: italic; -fx-text-fill:red; -fx-font-weight: bold;");

				Stage changeWorkWindow = new Stage();

				cmbAllDepartment.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						for (int i = 0; i < allListeners.size(); i++) {
							if (allListeners.elementAt(i)
									.isDepartmentChangeable(cmbAllDepartment.getValue()) == false) {
								gpRoot.add(lblCantChnageDepartment, 1, 1);
								tfChangeWorkHour.setDisable(true);

							} else if (cmbAllDepartment.getValue() == null) {
								tfChangeWorkHour.setDisable(true);
								gpRoot.add(lblCantChnageDepartment, 1, 1);

							} else {
								tfChangeWorkHour.setDisable(false);
								gpRoot.getChildren().remove(lblCantChnageDepartment);

							}
						}
					}
				});

				btnSubmitChangeWorkMethodByDepartment.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						changeWorkWindow.setAlwaysOnTop(false);
						int getStartHour = 0;

						for (int i = 0; i < allListeners.size(); i++) {
							if (cmbAllDepartment.getSelectionModel().getSelectedItem() == null) {
								experimentMessage("You must choose department!");
								changeWorkWindow.setAlwaysOnTop(true);
							}

							else if (tfChangeWorkHour.getText().isEmpty()) {
								messageAlert("You most enter a hour!");
								changeWorkWindow.setAlwaysOnTop(true);
							} else {
								try {
									getStartHour = Integer.parseInt(tfChangeWorkHour.getText());

								} catch (NumberFormatException e) {
									experimentMessage("You must give a real start hour!");
									changeWorkWindow.setAlwaysOnTop(true);
									break;
								}
								if (getStartHour >= 0 && getStartHour <= 23) {
									allListeners.elementAt(i)
											.workChangeHourByDepartmentToUI(cmbAllDepartment.getValue(), getStartHour);
									changeWorkWindow.close();
									messageAlert("The department hour successfully changed! ");
								} else {
									experimentMessage("You must give a start hour between 0-23!");
									changeWorkWindow.setAlwaysOnTop(true);
								}
							}

						}

					}

				});

				gpRoot.add(lblChooseDepartment, 0, 0);
				gpRoot.add(cmbAllDepartment, 0, 1);
				gpRoot.add(lblChangeWorkHour, 0, 2);
				gpRoot.add(tfChangeWorkHour, 0, 3);
				gpRoot.add(btnSubmitChangeWorkMethodByDepartment, 1, 4);

				gpRoot.setPadding(new Insets(10));
				gpRoot.setHgap(15);
				gpRoot.setVgap(10);

				changeWorkWindow.setTitle("Change work method by department ");
				changeWorkWindow.setAlwaysOnTop(false);
				changeWorkWindow.setScene(new Scene(gpRoot, 510, 230));
				changeWorkWindow.show();
			}
		});

		btnShowExperimentResults.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Stage newStage = new Stage();

				ScrollPane scrollResultsPane = new ScrollPane();

				for (int i = 0; i < allListeners.size(); i++) {
					scrollResultsPane.setContent(new Text(allListeners.elementAt(i).employeeEfficiencyToUI()));
				}

				newStage.setTitle("Experiment Results ");
				newStage.setAlwaysOnTop(false);
				newStage.setScene(new Scene(scrollResultsPane, 300, 500));
				scrollResultsPane.setPadding(new Insets(10));
				newStage.show();
			}
		});

		btnShowDepartmentsAndRoles.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				showLayout(listViewDepartment, "Departments and Roles");
			}
		});

		btnShowAllEmployees.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				showLayout(listViewEmployee, "Employees");
			}
		});

//		btnDeletCompany.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent arg0) {
//				messageAlert("You decided to delete company from database, good by! ");
//				DBmySql_connection db = new DBmySql_connection();
//				db.deleteCompany();
//				
//				
//			
//			}});
		
		
		btnSaveAndExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				messageAlert("You decided to exit the experiment, good by! ");
				for (int i = 0; i < allListeners.size(); i++) {
					try {
						allListeners.elementAt(i).saveFileToUI();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				firstWindow.close();
			}

		});
		gpRoot.add(lblWelcome, 0, 0);
		gpRoot.add(btnAddDepartment, 0, 3);
		gpRoot.add(btnAddRole, 0, 4);
		gpRoot.add(btnAddEmployee, 0, 5);
		gpRoot.add(btnShowDepartmentsAndRoles, 0, 6);
		gpRoot.add(btnShowAllEmployees, 0, 7);
		gpRoot.add(btnChangeWorkMethodByRole, 0, 8);
		gpRoot.add(btnChangeWorkMethodByDepartments, 0, 9);
		gpRoot.add(btnShowExperimentResults, 0, 10);
		//gpRoot.add(btnDeletCompany, 0, 11);
		gpRoot.add(btnSaveAndExit, 0, 11);
	
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);

		firstWindow.setAlwaysOnTop(false);
		firstWindow.setScene(new Scene(gpRoot, 300, 450));
		firstWindow.show();

	}

	@Override
	public void messageAlert(String msg) {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);

		alert.initStyle(StageStyle.UTILITY);
		alert.initOwner(firstWindow);
		alert.setTitle("Message");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();

	}

	@Override
	public void onClickUploadFileYes(EventHandler<ActionEvent> e) {
		btnUploadFileYes.setOnAction(e);
	}

	@Override
	public void onClickUploadFileNo(EventHandler<ActionEvent> e) {
			btnUploadFileNo.setOnAction(e);
		
		
	
	}

	@Override
	public void addDepartmentToUI(String name, Department newDepartment) {
		cmbAllDepartment.getItems().add(name);
		listViewDepartment.getItems().add(newDepartment);
	}

	@Override
	public void addRoleToUI(String name, Role newRole) {
		cmbAllRoles.getItems().add(name);
		listViewRole.getItems().add(newRole);
	}

	@Override
	public void addEmployeeToUI(Employee employee) {
		listViewEmployee.getItems().add(employee);
	}

	public void experimentMessage(String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);

		alert.initStyle(StageStyle.UTILITY);
		alert.initOwner(firstWindow);
		alert.setTitle("Message");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();

	}


	@Override
	public String getData() {
		for (ExperimentUIEventsListener l : allListeners) {
			listViewDepartment.getItems().add(l.getAllData());
			return l.getAllData();
		}
		return "";
	}
	@Override
	public void showLayout(ListView listView, String Title) {

		Stage layOut = new Stage();

		layOut.setTitle(Title);

		VBox layOutScene = new VBox(10);

		layOutScene.setPadding(new Insets(10));

		layOutScene.getChildren().addAll(listView);

		Scene scene = new Scene(layOutScene, 1000, 500);
		layOut.setScene(scene);
		layOut.show();
	}

	@Override
	public Vector<String> getRolesInDepartment(String departmentName) {
		for (ExperimentUIEventsListener l : allListeners) {
			return l.getTheRoleInDepartment(departmentName);
		}
		return null;
	}

}
