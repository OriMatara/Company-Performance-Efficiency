package application.mvc.model;
import java.sql.*;
import java.util.Vector;

import application.mvc.listeners.ExperimentModelEventsListener;
import application.mvc.model.Department;
import application.mvc.model.Employee.prefrences;


public class DBmySql_connection {
	private Connection conn;
	
	// Constructor - connect DBmySql
		public DBmySql_connection() {
		

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String dbUrl = "jdbc:mysql://localhost:3306/companyDB";
				String password = " ";
				conn = DriverManager.getConnection(dbUrl, "root",password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	   
		// close DB
		public void closeDB() {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		//dep_role_employeelist_tbl
				public void deleteAll_dep_role_employeelist_tbl() {
					try {
						PreparedStatement stmt = conn.prepareStatement("DELETE FROM dep_role_employeelist_tbl");
						stmt.executeUpdate();
						stmt.close();

					} catch (Exception e) {
			
					}
				}
		
		//dep_rolelist_tbl
		public void deleteAll_dep_rolelist_tbl() {
			try {
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM dep_rolelist_tbl");
				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
			
			}
		}
		
		public void deleteAll_emp_roleList_tbl() {
			try {
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM emp_roleList_tbl");
				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				
			}
		}
		public void deleteAll_dep_employeelist_tbl() {
			try {
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM dep_employeelist_tbl");
				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				
			}
		}
		
		public void deleteAllDepartments() {
			try {
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM companydb.departmrnts_tbl");
				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
			
			}
		}
		
		public void deleteCompany() {
			try {
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM company_tbl");
				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
			
			}
		}
		
		public void addCompanyDB(Company comp) {

			 try {
		    	
				 String queryC ="INSERT INTO company_tbl(name)" +"VALUES (?)";
 
		    	  PreparedStatement pstmt = conn.prepareStatement(queryC);
		    	  pstmt = conn.prepareStatement(queryC);
		    	  pstmt.setString (1, comp.getName());
		    	
		    	  pstmt.executeUpdate();  
		    		pstmt.close();
		      
		    	  
		     
		     }catch (Exception e) {
				e.printStackTrace();
					}
			
			
		}
		public Company getCompanyDB() throws ClassNotFoundException, SQLException{
			Company theCompany = new Company();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT* FROM company_tbl");
		
			while (rs.next()) {
				String name = rs.getString("name");
				theCompany.setName(name);
				
		
			
			}
	
			stmt.close();
			rs.close();
			return theCompany;

		}


		public void addDepartmentDB(Department dep,String nameDep, boolean flag) {
			try {
				
				if(flag == false) {
					return;
				}
				int startH=dep.getStartHour();
				int endH=dep.getEndHour();
			
				String name = dep.getName();
				String synch=null;
				if(dep.getIsSynchronized()==false)
				{
					 synch ="false";
				}
				if(dep.getIsSynchronized()==true)
				{
					 synch ="true";
				}
				
				String workChange= "false";
				
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO departmrnts_tbl VALUES(?,?,?,?,?)");
				stmt.setString(1, name);
				stmt.setInt(2, startH);
				stmt.setInt(3, endH);
				stmt.setString(4, synch);
				stmt.setString(5, workChange);
				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void department_Synchronize(String departmentName ,int startH, int endH){
				try {

					  String synchronizeStr="true";

				      String query = "UPDATE departmrnts_tbl SET start_hour = ?, end_hour = ?, synch = ? where name = ?";
				      PreparedStatement preparedStmt = conn.prepareStatement(query);
				      preparedStmt.setInt   (1, startH);
				      preparedStmt.setInt   (2, endH);
				      preparedStmt.setString   (3, synchronizeStr);
				      preparedStmt.setString(4, departmentName);

				      // execute the java preparedstatement
				      preparedStmt.executeUpdate();
				      
				      preparedStmt.close();
				} catch (SQLException e) {
					//e.printStackTrace();
				}
		}
		
		public void role_department_Synchronize(String departmentName, String roleName ,int startH, int endH, boolean synchronize){
			
				try {
					
					  String synchronizeStr="true";

				      String query2 = "UPDATE dep_roleList_tbl SET isSynchronize = ? where name_department = ?";
				      PreparedStatement preparedStmt2 = conn.prepareStatement(query2);

				      preparedStmt2.setString   (1, synchronizeStr);
				      preparedStmt2.setString(2, departmentName);

				      // execute the java preparedstatement
				      preparedStmt2.executeUpdate();
				      
				      preparedStmt2.close();

				} catch (SQLException e) {
				
				}
		}
		
		
		
	
				public void updateEmployeeWorkChange(String departName,String nameRole,Employee emp) {
					
					try {
					      String workchangeStr = "true";

					      String query1 = "UPDATE dep_role_employeelist_tbl SET workChange = ? where EmployeeID = ?";
					      PreparedStatement preparedStmt1 = conn.prepareStatement(query1);

					      preparedStmt1.setString   (1, workchangeStr);
					      preparedStmt1.setInt(2, emp.getSerialNumber());

				
					      preparedStmt1.executeUpdate();
					      
					      preparedStmt1.close();
					      
						

					} catch (SQLException e) {
						
					}
				}
		
		
		
		// Change Work Method By Role
		public void updateRoleWorkChange(String departName,String nameRole,boolean workChange) {
			
			try {
			      String workchangeStr = "true";

			      String query2 = "UPDATE dep_roleList_tbl SET workChange = ? where name_department = ?";
			      PreparedStatement preparedStmt2 = conn.prepareStatement(query2);

			      preparedStmt2.setString   (1, workchangeStr);
			      preparedStmt2.setString(2, departName);

			      // execute the java preparedstatement
			      preparedStmt2.executeUpdate();
			      
			      preparedStmt2.close();
				

			} catch (SQLException e) {
				
			}
		}
		
		

	
		
		public void department_WorkChange(String departmentName ){
			try {

				String workchangeStr="true";

			      String query = "UPDATE departmrnts_tbl SET workChange = ? where name = ?";
			      PreparedStatement preparedStmt = conn.prepareStatement(query);
			      preparedStmt.setString   (1, workchangeStr);
			      preparedStmt.setString(2, departmentName);

			      // execute the java preparedstatement
			      preparedStmt.executeUpdate();
			      preparedStmt.close();
			      
			} catch (SQLException e) {
				
			}
	}
		
		public Vector<Department> getDepartments() {
			try {
				Vector<Department> departments = new Vector<Department>();

				Statement stmt = conn.createStatement();
				String query = "SELECT * From departmrnts_tbl";
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					Department d;
					String name = rs.getString(1); 
					int start_hour = rs.getInt(2);
					int end_hour=rs.getInt(3);	
					String synch = rs.getString(4);
					String workChange = rs.getString(5);
					
					boolean isSynchronized = false;
					
					if(synch.equals("true"))
					{
						isSynchronized = true ;
					}
					
					boolean isWorkChange = false;
					
					if(workChange.equals("true"))
					{
						isWorkChange = true ;
					}
					
					
					
					d=new Department(name,start_hour,end_hour, isSynchronized, isWorkChange);
					Vector<Role> roleListDB = getRolListOfDepartment(name);
					Vector<Employee> employeeListDB = getEmpListOfDepartment( d, name);
					d.setRoleList(roleListDB);
					d.setEmployeeList(employeeListDB);
					departments.add(d);
				}
				stmt.close();
				rs.close();
				
				
		return departments;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return null;
		}

		// employees Queries

		public Vector<Employee> getEmployees() {
			try {

				Vector<Employee> employees = new Vector<Employee>();
				Statement stmt = conn.createStatement();
				String query = "SELECT * From employees_tbl";
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					
					prefrences prefrence= prefrences.EARLY;
					
					Employee em = new Employee(rs.getString(2), rs.getInt(3), rs.getString(4),rs.getString(5),prefrence,rs.getInt(7));
					employees.add(em);
				}
				stmt.close();
				rs.close();
				
				
			return employees;
			} catch (Exception e) {
			
			}
		
			return null;
		}
		
	
		
		// add employee to dep_role_employeelist_tbl by Employee object
		public void addEmployeeToDepartmentToRoleDB(Employee emp, boolean flag) {
			
			try {
				if(flag == false) {
					return;
				}
				
			int  empSerialNumber=emp.getSerialNumber();
			String empName=emp.getName();
			int age=emp.getAge();
			String departmentName = emp.getDepartment();
			String roleName = emp.getRole();
			String preferenceEmp=emp.getPrefrence().name();
			int preferStartHour=emp.getPreferStartHour();
			int preferEndHour=emp.getPreferEndHour();
			int realStartHour=emp.getStartHour();
			int realEndHour=emp.getEndHour();
			String synch=null;
			if(emp.getSynchronized()==false)
			{
				 synch ="false";
			}
			if(emp.getSynchronized()==true)
			{
				 synch ="true";
			}
			
			String workChange= "false";
			String empKind =null;

			int salary=0;
		
			if(emp.getClass().equals(BasePlusPercentageSalaryEmployee.class)) {
				empKind = "BasePlusPercentageSalaryEmployee";
				salary = ((BasePlusPercentageSalaryEmployee)emp).getBasePlusPercentageSalary();
			}
			if(emp.getClass().equals(BaseSalaryEmployee.class)) {
				empKind = "BaseSalaryEmployee";
				salary = ((BaseSalaryEmployee)emp).getBaseSalary();
			}
			if(emp.getClass().equals(HourSalaryEmployee.class)) {
				empKind = "HourSalaryEmployee";
				salary = ((HourSalaryEmployee)emp).getSalary();
			}
	
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO dep_role_employeelist_tbl VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1,empSerialNumber);
			stmt.setString(2, empName);
			stmt.setInt(3, age);
			stmt.setString(4, roleName);
			stmt.setString(5, departmentName);
			stmt.setString(6, preferenceEmp);
			stmt.setInt(7, preferStartHour);
			stmt.setInt(8, preferEndHour);
			stmt.setInt(9, realStartHour);
			stmt.setInt(10, realEndHour);
			stmt.setString(11, synch);
			stmt.setString(12, workChange);
			stmt.setString(13, empKind);
			stmt.setInt(14, salary);
			
			
			
			stmt.executeUpdate();
			stmt.close();
			} catch (Exception e) {
				
			}
		
			
		}
		

		
		public void addRoleToDepartmentRoleList(Role role ,String departmentName) {
			try {
		
				String roleName = role.getRoleName();
				int startH=role.getStartHour();
				int endH=role.getEndHour();
				String synch=null;
				
				if(role.getIsSynchronized()==false)
				{
					 synch ="false";
				}
				if(role.getIsSynchronized()==true)
				{
					 synch ="true";
				}
				
				String workChange= "false";
				
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO dep_roleList_tbl VALUES (?,?,?,?,?,?)");
			
			stmt.setString(1, departmentName);
			stmt.setString(2, roleName);
			stmt.setInt(3, startH);
			stmt.setInt(4, endH);
			stmt.setString(5, synch);
			stmt.setString(6, workChange);
			
			stmt.executeUpdate();
			stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public Vector<Employee> getEmployeeListOfDepartmentAndRoll(String departmentName) {

			try {
				Vector<Employee> employees = new Vector<Employee>();

			
				Statement stmt = conn.createStatement();
				String query = String.format("SELECT * FROM dep_role_employeelist_tbl WHERE Emp_Department = '%s';", departmentName);
			
				ResultSet rs = stmt.executeQuery(query);
			

				while (rs.next()) {
					
					int EmployeeID = rs.getInt(1);
					String name = rs.getString(2);
					int age = rs.getInt(3);
					String Emp_Role=rs.getString(4);
					String Emp_Department=rs.getString(5);
					String prefrenceStr=rs.getString(6);
					int prefer_Start_Hour = rs.getInt(7);
					int prefer_End_Hour = rs.getInt(8);
					int real_Start_Hour = rs.getInt(9);
					int real_End_Hour = rs.getInt(10);

					String isSynchronize=rs.getString(11);
					String workChange = rs.getString(12);
					
					String emp_Kind = rs.getString(13);
					int salary = rs.getInt(14);
					
					prefrences pref = prefrences.EARLY;
					
					if(prefrenceStr.equals("LATE") )
					{
						pref = prefrences.LATE;
					}
					
					if(prefrenceStr.equals("NO_CHANGE") )
					{
						pref = prefrences.NO_CHANGE;
					}
					
					if(prefrenceStr.equals("WORK_FROM_HOME") )
					{
						pref = prefrences.WORK_FROM_HOME;
					}
	
					
					if(emp_Kind.equals("BasePlusPercentageSalaryEmployee")) {
						BasePlusPercentageSalaryEmployee emp = new BasePlusPercentageSalaryEmployee(name, age, Emp_Role, Emp_Department, pref, prefer_Start_Hour,salary);
						
						emp.setStartHour(real_Start_Hour);
						emp.setEndHour(real_Start_Hour);
						
						
						if(isSynchronize.equals("false") )
						{
							emp.setSynchronized(false);
						}
						
						if(isSynchronize.equals("true"))
						{
							emp.setSynchronized(true);
						}
						if(workChange.equals("false") )
						{
							emp.setWorkChange(false);
						}
						
						if(workChange.equals("true"))
						{
							emp.setWorkChange(true);
						}
						
						employees.add(emp);
					}
					if(emp_Kind.equals("BaseSalaryEmployee")) {
						BaseSalaryEmployee emp = new BaseSalaryEmployee(name, age, Emp_Role, Emp_Department, pref, prefer_Start_Hour,salary);
						
						emp.setStartHour(real_Start_Hour);
						emp.setEndHour(real_Start_Hour);
						
						
						if(isSynchronize.equals("false") )
						{
							emp.setSynchronized(false);
						}
						
						if(isSynchronize.equals("true"))
						{
							emp.setSynchronized(true);
						}
						if(workChange.equals("false") )
						{
							emp.setWorkChange(false);
						}
						
						if(workChange.equals("true"))
						{
							emp.setWorkChange(true);
						}
						
						employees.add(emp);
					}
					if(emp_Kind.equals("HourSalaryEmployee")) {
						HourSalaryEmployee emp = new HourSalaryEmployee(name, age, Emp_Role, Emp_Department, pref, prefer_Start_Hour,salary);
						
						emp.setStartHour(real_Start_Hour);
						emp.setEndHour(real_Start_Hour);
						
						if(isSynchronize.equals("false") )
						{
							emp.setSynchronized(false);
						}
						
						if(isSynchronize.equals("true"))
						{
							emp.setSynchronized(true);
						}
						if(workChange.equals("false") )
						{
							emp.setWorkChange(false);
						}
						
						if(workChange.equals("true"))
						{
							emp.setWorkChange(true);
						}
						
						employees.add(emp);
					}
					
					

					
				}
				stmt.close();
				rs.close();
				
				
		return employees;
		
			} catch (Exception e) {
			
			}
			
		return null;
		}
		
		public Vector<Role> getRolListOfDepartment(String departmentName) {
			try {
				Vector<Role> roles = new Vector<Role>();

				Statement stmt = conn.createStatement();
				String query = String.format("SELECT * FROM dep_roleList_tbl WHERE name_department = '%s';", departmentName);
				
				ResultSet rs = stmt.executeQuery(query);
			
				while (rs.next()) {
					
				
					String name_department=rs.getString(1);
					String name_role=rs.getString(2);
			
					int start_hour = rs.getInt(3);
					Role r=new Role(name_role, start_hour);
					
					int end_hour=rs.getInt(4);
					r.setEndHour(end_hour);
					String isSynchronize=rs.getString(5);
					String workChange = rs.getString(6);
					
					if(isSynchronize.equals("false") )
					{
						r.setSynchronized(false);
					}
					
					if(isSynchronize.equals("true"))
					{
						r.setSynchronized(true);
					}
					if(workChange.equals("false") )
					{
						r.setWorkChange(false);
					}
					
					if(workChange.equals("true"))
					{
						r.setWorkChange(true);
					}
					
					
					roles.add(r);
				}
				stmt.close();
				rs.close();
				
				
		return roles;
			} catch (Exception e) {
				
			}
			
		return null;
		}
		public Vector<Employee> getEmpListOfDepartment(Department d, String departmentName) {
			try {
				Vector<Employee> employees = new Vector<Employee>();

		
			
				PreparedStatement	stmt= conn.prepareStatement("SELECT  emplyee_id FROM dep_employeeList_tbl ");
					ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					int empID=rs.getInt(1);
					Employee e=d.getEmployeeById(empID);
					employees.add(e);
				}
				stmt.close();
				rs.close();
				
				
		return employees;
			} catch (Exception e) {
				
			}
			
		return null;
		}
		
		public void addEmployeeToDepartmentEmployeeList(String departmentName,int  empSerialNumber) {
			try {
		
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO dep_employeeList_tbl VALUES (?,?)");
			
			stmt.setString(1, departmentName);
			stmt.setInt(2, empSerialNumber);
			
			stmt.executeUpdate();
			stmt.close();
			} catch (Exception e) {
			
			}
		
			
		}
		public void addEmployeeToRoleEmployeeList(String employeeName,int  empSerialNumber, String  roleName) {
			try {
		
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO emp_rolelist_tbl VALUES (?,?,?)");
			
			stmt.setString(1, employeeName);
			stmt.setInt(2, empSerialNumber);
			stmt.setString(3, roleName);
			
			stmt.executeUpdate();
			stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			
		}
		
		
	
}
