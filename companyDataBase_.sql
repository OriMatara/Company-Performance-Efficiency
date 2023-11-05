create database companyDB;
USE companyDB;


CREATE TABLE dep_role_employeelist_tbl(
EmployeeID INT NOT NULL PRIMARY KEY,
name VARCHAR(50)  NOT NULL,
age INT,
Emp_Role VARCHAR(50),
Emp_Department VARCHAR(50),
prefrence VARCHAR(50),
prefer_Start_Hour INT,
prefer_End_Hour INT,
real_Start_Hour INT,
real_End_Hour INT,
isSynchronize VARCHAR(50),
workChange VARCHAR(50),
emp_Kind VARCHAR(50),
salary INT
);

CREATE TABLE departmrnts_tbl(
 name VARCHAR(50) NOT NULL PRIMARY KEY,
 start_hour INT,
 end_hour INT,
 synch VARCHAR(50)  NOT NULL DEFAULT 'false',
 workChange varchar(50)
);

CREATE TABLE dep_roleList_tbl(
 name_department VARCHAR(50),
 name_role VARCHAR(50) NOT NULL PRIMARY KEY,
start_hour INT,
end_hour INT,
isSynchronize VARCHAR(50),
workChange VARCHAR(50)
);

CREATE TABLE emp_roleList_tbl(
name_employee  VARCHAR(50) NOT NULL  PRIMARY KEY,
employee_id INT,
emplyeeRoleName  VARCHAR(50),
CONSTRAINT `emplyeeRoleName`FOREIGN KEY (`emplyeeRoleName`)REFERENCES `companydb`.`dep_rolelist_tbl` (`name_role`),
CONSTRAINT `employee_id`FOREIGN KEY (`employee_id`)REFERENCES `companydb`.`dep_role_employeelist_tbl` (`EmployeeID`)
);



CREATE TABLE dep_employeeList_tbl(
name_department  VARCHAR(50) NOT NULL  PRIMARY KEY,
emplyee_id  INT
);


CREATE TABLE company_tbl(
name VARCHAR(50) NOT NULL PRIMARY KEY
);
