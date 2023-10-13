package ra.business;

import ra.entity.Employee;
import ra.notify.Notify;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeBusiness {
    public static List<Employee> getAllEmployee(int offSet, int pageNumber) {
        List<Employee> employeeList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_employee(?,?)}");
            callSt.setInt(1, offSet);
            callSt.setInt(2, pageNumber);
            ResultSet rs = callSt.executeQuery();
            employeeList = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                java.sql.Date date = rs.getDate("birth_of_date");
                employee.setBirthDate(new Date(date.getTime()));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeStatus(rs.getShort("emp_status"));
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return employeeList;
    }

    public static int getTotalRecord() {
        int total = 0;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_total_employee(?)}");
            callSt.registerOutParameter(1, Types.INTEGER);
            callSt.execute();
            total = callSt.getInt(1);
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return total;
    }

    public static boolean addNewEmployee(Employee employee) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call insert_employee(?,?,?,?,?,?,?)}");
            callSt.setString(1, employee.getEmployeeId());
            callSt.setString(2, employee.getEmployeeName());
            callSt.setDate(3, new java.sql.Date(employee.getBirthDate().getTime()));
            callSt.setString(4, employee.getEmail());
            callSt.setString(5, employee.getPhone());
            callSt.setString(6, employee.getAddress());
            callSt.setShort(7, employee.getEmployeeStatus());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static Employee getEmployeeById(String employeeId) {

        Employee employee = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_exists_id(?)}");
            //set giá trị tham số vào
            callSt.setString(1, employeeId);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            //Lấy dữ liệu rs đẩy vào đối tượng book trả về
            while (rs.next()) {
                employee = new Employee();
                employee.setEmployeeId(rs.getString("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                employee.setBirthDate(new java.sql.Date(rs.getDate("birth_of_date").getTime()));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeStatus(rs.getShort("emp_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return employee;
    }

    public static Employee checkEmployeeByName(String employeeName) {
        Employee employee = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_employee_name(?)}");
            //set giá trị tham số vào
            callSt.setString(1, employeeName);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                employee = new Employee();
                employee.setEmployeeId(rs.getString("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                employee.setBirthDate(new java.sql.Date(rs.getDate("birth_of_date").getTime()));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeStatus(rs.getShort("emp_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return employee;
    }

    public static Employee checkEmployeeById(String employeeName) {
        Employee employee = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_employee_id(?)}");
            //set giá trị tham số vào
            callSt.setString(1, employeeName);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                employee = new Employee();
                employee.setEmployeeId(rs.getString("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                employee.setBirthDate(new java.sql.Date(rs.getDate("birth_of_date").getTime()));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeStatus(rs.getShort("emp_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return employee;
    }

    public static boolean updateEmployee(Employee employee) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_status_employee(?,?,?,?,?,?,?)}");
            //set dữ liệu cho các tham số vào của procedure
            callSt.setString(1, employee.getEmployeeId());
            callSt.setString(2, employee.getEmployeeName());
            callSt.setDate(3,new java.sql.Date(employee.getBirthDate().getTime()));
            callSt.setString(4, employee.getEmail());
            callSt.setString(5, employee.getPhone());
            callSt.setString(6, employee.getAddress());
            callSt.setShort(7, employee.getEmployeeStatus());
            //Thực hiện gọi procedure
            callSt.executeUpdate();
            result = true;
        } catch (Exception ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static List<Employee> searchNameEmployee(String name) {
        List<Employee> employeeList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_employee_name(?)}");
            callSt.setString(1, name);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            employeeList = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                employee.setBirthDate(new java.sql.Date(rs.getDate("birth_of_date").getTime()));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeStatus(rs.getShort("emp_status"));
                employeeList.add(employee);
            }
        } catch (Exception ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return employeeList;
    }

    public static List<Employee> searchIdEmployee(String name) {
        List<Employee> employeeList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_employee_id(?)}");
            callSt.setString(1, name);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            employeeList = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                employee.setBirthDate(new java.sql.Date(rs.getDate("birth_of_date").getTime()));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeStatus(rs.getShort("emp_status"));
                employeeList.add(employee);
            }
        } catch (Exception ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return employeeList;
    }
    /*

    */
    public static String validateEmployeeId(String text){
        return ProductBusiness.validate(text,"{call validate_employee_id(?,?)}");
    }
}
