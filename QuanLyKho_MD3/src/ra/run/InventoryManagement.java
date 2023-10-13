package ra.run;
import ra.color.Color;
import ra.entity.Account;
import ra.notify.Notify;
import ra.util.ConnectionDB;
import java.sql.*;
import java.util.Scanner;

import static ra.notify.Notify.*;

public class InventoryManagement {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean noExit = true;
        do {
            System.out.println(Color.ORANGE_BOLD + "WELCOME SYSTEM MANAGEMENT PRODUCT 🫡" + Color.RESET);
            System.out.println();
            notifyConfirm("Bạn có muốn đăng nhập vào hệ thống không 🤖 ?");
            int exitNum = Integer.parseInt(scanner.nextLine());
            if (exitNum == 2) {
                noExit = false;
            } else {
                boolean exists = true;
                do {
                    try {
                        notifyEnter("Nhập Tên tài khoản: ");
                        String userName = scanner.nextLine();
                        String check = checkUserName(userName);
                        int checkNumber;
                        if (check.equals("0")) {
                            checkNumber = 0;
                        } else if (check.equals("1")) {
                            checkNumber = 1;
                        } else {
                            checkNumber = 3;
                        }
                        switch (checkNumber) {
                            case 0:
                               passAdmin();
                                break;
                            case 1:
                               passUser();
                                break;
                        }
                        exists = false;
                    } catch (Exception e) {
                        notifyError("Tên tài khoản này không tồn tại hoặc đã bị khóa,Vui lòng nhập lại !");
                    }
                } while (exists);
            }
        } while (noExit);

    }

    /**
     * check username
     * @param userName enter userName
     * @return results
     */
    public static String checkUserName(String userName) {
        String num = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call login_user_name(?,?)}");
            callSt.setString(1, userName);
            callSt.registerOutParameter(2, Types.VARCHAR);
            callSt.execute();
            num = callSt.getString(2);
        } catch (SQLException e) {
            Notify.notifyError(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return num;
    }

    /**
     * check password
     * @param pass enter password
     * @return result
     */
    public static Account checkPassword(String pass) {
        Account account = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call login_password(?)}");
            //set giá trị tham số vào
            callSt.setString(1, pass);
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

    /**
     *  admin of password
     */
    public static void passAdmin(){
        do {
            notifyEnter("Nhập mật khẩu: ");
            String passAd = scanner.nextLine();
            Account account = checkPassword(passAd);
            if (account != null) {
                notifySuccess("Đăng nhập tài khoản Admin thành công");
                System.out.println();
                AdminManagement.adminManagement();
                break;
            } else {
                notifyError("Mật khẩu sai, Vui lòng nhập lại !");
            }
        } while (true);
    }

    /**
     * user of password
     */
    public static void passUser(){
        do {
            notifyEnter("Nhập mật khẩu: ");
            String passUser = scanner.nextLine();
            Account account = checkPassword(passUser);
            if (account != null) {
                notifySuccess("Đăng nhập tài khoản User thành công");
                System.out.println();
                UserManagement.userManagement();
                break;
            } else {
                notifyError("Mật khẩu sai, Vui lòng nhập lại !");
            }
        } while (true);
    }

}
