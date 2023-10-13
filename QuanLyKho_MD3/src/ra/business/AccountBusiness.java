package ra.business;

import ra.entity.Account;
import ra.notify.Notify;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountBusiness {
    public static List<Account> getAllAccount(int offSet, int pageNumber) {
        List<Account> accountList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_account(?,?)}");
            callSt.setInt(1,offSet);
            callSt.setInt(2,pageNumber);
            ResultSet rs = callSt.executeQuery();
            accountList = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("acc_password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmployeeId(rs.getString("emp_id"));
                account.setAccountStatus(rs.getBoolean("acc_status"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return accountList;
    }
    public static int getTotalRecord() {
        int total = 0 ;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_total_account(?)}");
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
    public static boolean addNewAccount(Account account) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call insert_account(?,?,?,?,?)}");
            callSt.setString(1, account.getUserName());
            callSt.setString(2, account.getPassword());
            callSt.setBoolean(3, account.isPermission());
            callSt.setString(4, account.getEmployeeId());
            callSt.setBoolean(5, account.isAccountStatus());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
    public static boolean updateStatusAccount(Account account) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_status_account(?,?)}");
            //set dữ liệu cho các tham số vào của procedure
            callSt.setInt(1, account.getAccountId());
            callSt.setBoolean(2, account.isAccountStatus());
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
    public static Account getAccountById(int Id) {
        Account account = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_exists_id_account(?)}");
            //set giá trị tham số vào
            callSt.setInt(1,Id);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            //Lấy dữ liệu rs đẩy vào đối tượng book trả về
            while (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("acc_password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmployeeId(rs.getString("emp_id"));
                account.setAccountStatus(rs.getBoolean("acc_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return account;
    }
    public static List<Account> searchUserNameAccount(String userName) {
        List<Account> accountList = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_account_username(?)}");
            callSt.setString(1, userName);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            accountList = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("acc_password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmployeeId(rs.getString("emp_id"));
                account.setAccountStatus(rs.getBoolean("acc_status"));
                accountList.add(account);
            }
        } catch (Exception ex) {
            Notify.notifyError(ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return accountList;
    }
    public static Account checkAccountByUserName(String userName) {
        Account account = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_account_username(?)}");
            //set giá trị tham số vào
            callSt.setString(1, userName);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("acc_password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmployeeId(rs.getString("emp_id"));
                account.setAccountStatus(rs.getBoolean("acc_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return account;
    }
    public static Account checkAccountByIdEmployeeStatus(int Id) {
        Account account = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_status_acc_emp(?)}");
            //set giá trị tham số vào
            callSt.setInt(1,Id);
            //Thực hiện gọi procedure
            ResultSet rs = callSt.executeQuery();
            //Lấy dữ liệu rs đẩy vào đối tượng book trả về
            while (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("acc_password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmployeeId(rs.getString("emp_id"));
                account.setAccountStatus(rs.getBoolean("acc_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return account;
    }
    /*
    *
    * */
    public static String validateUserName(String text){
        return ProductBusiness.validate(text,"{call validate_user_name(?,?)}");
    }
    public static String validateAccEmployeeId(String text){
        return ProductBusiness.validate(text,"{call validate_acc_emp_id(?,?)}");
    }
}
