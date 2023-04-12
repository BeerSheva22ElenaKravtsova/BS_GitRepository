package telran.git.project;

import java.util.List;

import telran.employees.Company;
import telran.employees.Employee;
import telran.net.NetworkClient;

public class NetworkCompany implements Company {
	private static final long serialVersionUID = 1L;
	NetworkClient nwClient;

	public NetworkCompany(NetworkClient nwClient) {
		this.nwClient = nwClient;
	}

	@Override
	public boolean addEmployee(Employee empl) {
		return nwClient.send("addEmployee", empl);
	}

	@Override
	public Employee removeEmployee(long id) {
		return nwClient.send("removeEmployee", id);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return nwClient.send("getAllEmployees", null);
	}

	@Override
	public List<Employee> getEmployeesByMonthBirth(int month) {
		return nwClient.send("getEmployeesByMonthBirth", month);
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		return nwClient.send("getEmployeesBySalary", new Integer[] { salaryFrom, salaryTo });
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return nwClient.send("getEmployeesByDepartment", department);
	}

	@Override
	public Employee getEmployee(long id) {
		return nwClient.send("getEmployee", id);
	}

	@Override
	public void save(String pathName) {
		nwClient.send("save", pathName);
	}

	@Override
	public void restore(String pathName) {
		nwClient.send("restore", pathName);
	}
}
