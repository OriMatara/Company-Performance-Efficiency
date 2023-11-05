# Company Performance Efficiency Project

## Project Overview

The Company Performance Efficiency project is designed to enhance the working conditions of employees by allowing them to personalize their preferences. This software solution aims to improve employee satisfaction and, in turn, boost overall company performance.

Welcome to the Company Performance Efficiency project! This software project is designed to improve the efficiency of a company by adjusting the working conditions of its employees to better align with their personal preferences. As a software engineer, you'll play a crucial role in developing and maintaining this system, which aims to optimize the company's performance and profitability.

## Table of Contents

- [Key Features](#key-features)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Contributing](#contributing)

## Key Features

**Employee Preferences:** The system allows employees to define their preferences, including work hours and work change settings.

**Department and Role Management:** The software provides tools for managing departments and roles, enabling administrators to set working hours and other department-specific policies.

**Database Integration:** The project features a SQL database that stores employee, department, and role data, ensuring efficient data management.

**User-Friendly Interface:** A graphical user interface (GUI) simplifies interaction with the system for both employees and administrators.

**Performance Metrics:** The software collects and displays performance metrics, making it easier for management to assess the impact of personalization on productivity.

The project focuses on enabling employees to work according to their personal preferences while considering the constraints of their roles, departments, and the company's requirements. In the traditional setup, all employees start and end their workday at fixed hours. However, in this system, employees can choose from four preferences:

- **Early Birds:** Those who prefer to start and end work earlier.
- **Night Owls:** Employees who favor later working hours.
- **Status Quo:** Those who prefer not to change their existing work hours.
- **Remote Work:** Employees who wish to work from home without specific start and end times.

The key to this project's success is that work hours aligned with an employee's preference can boost their efficiency by 20%. Conversely, hours misaligned with their preference can decrease their efficiency by 20%. If an employee doesn't modify their work hours, their efficiency remains unchanged. Additionally, working from home increases efficiency by 10%.

Certain roles and departments have restrictions on changing work hours, and some require synchronized work hours for all employees. The system enables administrators to experiment with different scenarios to evaluate the potential financial impact of adjusting work hours and preferences.

## Project Structure

The project is structured using the Model-View-Controller (MVC) pattern. Here's a brief overview of the main components:

**Model (M):** The core of the application, containing classes like Experiment that represent the business logic. This layer interacts with the database.

**View (V):** The user interface elements reside here, including classes like ExperimentView that create the GUI.

**Controller (C):** Classes like ExperimentController act as intermediaries, handling user inputs and connecting the Model and View.

This project follows the Model-View-Controller (MVC) architectural pattern:

- **Model:** The core of the application. It includes classes like Experiment, which manages data and interactions with the SQL database.

- **View:** The user interface components are located here. Classes like ExperimentView are responsible for creating an intuitive graphical user interface (GUI).

- **Controller:** Intermediary components, like ExperimentController, bridge the Model and View, handling user inputs and logic implementation.

## Database Schema

The SQL database stores essential data for the project. Below is the schema with key tables:

**dep_role_employeelist_tbl:** Stores employee data, including roles, preferences, and synchronization status.

**departments_tbl:** Manages department details, such as working hours and synchronization settings.

**dep_roleList_tbl:** Represents the connection between departments and roles.

**emp_roleList_tbl:** Records the relationships between employees, roles, and employee IDs.

**dep_employeeList_tbl:** Associates departments with lists of employee IDs for efficient department management.

**company_tbl:** Contains basic information about the company, including its name.

For a detailed database schema, refer to the Database Schema section.

## Getting Started

To get started with the Company Performance Efficiency project, follow these steps:

- Clone this repository to your local machine.
- Ensure you have a compatible SQL database system installed.
- Execute the SQL scripts to create the necessary database and tables.
- Build and run the project using your preferred Java development environment.

To begin working with the Company Performance Efficiency project:

- Clone this repository to your local development environment.
- Ensure that you have an SQL database system installed and ready for use.
- Run the provided SQL scripts to create the required database and tables.
- Build and run the project using your preferred Java development environment.

## Usage

- **For administrators:** Use the GUI to manage departments, roles, and employee preferences.
- **For employees:** Customize your work conditions within the constraints set by administrators and monitor your performance metrics.

**For administrators:** Utilize the intuitive GUI to manage departments, roles, and employee preferences. Experiment with different scenarios to optimize the company's efficiency and profitability.

**For employees:** Customize your work conditions within the constraints set by administrators and monitor your performance metrics.

## Contribution

Contributions to this project are welcome. If you'd like to contribute, please follow these steps:

- Fork the repository.
- Create a new branch for your feature or bug fix.
- Make your changes and commit them.
- Submit a pull request.

We encourage contributions from developers who are interested in improving workplace efficiency and job satisfaction. To contribute to the project:

- Fork the repository to your own GitHub account.
- Create a new branch for your feature or bug fix.
- Implement your changes and commit them to the branch.
- Submit a pull request to merge your changes with the main project.
