package ra.entity;

import ra.business.AccountBusiness;
import ra.business.ProductBusiness;
import ra.notify.Notify;

import static ra.table.Table.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Account {
    private int accountId;
    private String userName;
    private String password;
    private boolean permission;
    private String employeeId;
    private boolean accountStatus;

    public Account() {
    }

    public Account(int accountId, String userName, String password,
                   boolean permission, String employeeId, boolean accountStatus) {
        this.accountId = accountId;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.employeeId = employeeId;
        this.accountStatus = accountStatus;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void inputData(Scanner scanner) {
        this.userName = userName(scanner);
        this.password = password(scanner);
        this.permission = permission(scanner);
        this.employeeId = employeeId(scanner);
        this.accountStatus = status(scanner);

    }

    public static String userName(Scanner scanner) {
        System.out.print("Nhập tên tài khoản: ");
        do {
            String userName = scanner.nextLine();
            if (userName.trim().length() < 1) {
                Notify.notifyError("mã nhân viên không được bỏ trống !");
            } else {
                String result = AccountBusiness.validateUserName(userName);
                if (result.equals("OK")) {
                    Notify.notifyOk();
                    return userName;
                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static String password(Scanner scanner) {
        System.out.print("Nhập mật khẩu: ");
        do {
            String password = scanner.nextLine();
            if (password.trim().length() < 1) {
                Notify.notifyError("mã nhân viên không được bỏ trống !");
            } else {
                Notify.notifyOk();
                return password;
            }
        } while (true);
    }

    public static boolean permission(Scanner scanner) {
        System.out.print("Nhập quyền tài khoản(0-admin 1-user): ");
        do {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number == 0) {
                    Notify.notifyOk();
                    return false;
                } else if (number == 1) {
                    Notify.notifyOk();
                    return true;
                } else {
                    Notify.notifyError("Quyền tài khoản chỉ nhập 0 hoặc 1 !");
                }
            } catch (NumberFormatException n) {
                Notify.notifyError("Lỗi khi nhập kí tự, vui lòng nhập lại!");
            }
        } while (true);
    }

    public static String employeeIdAccountNew(Scanner scanner) {
        System.out.print("Nhập mã nhân viên(5 kí tự): ");
        do {
            String employeeId = scanner.nextLine();
            if (employeeId.trim().length() < 1 || employeeId.trim().length() > 5) {
                Notify.notifyError("mã nhân viên không được bỏ trống và nhiều hơn 5 kí tự !");
            } else {
                Notify.notifyOk();
                return employeeId;
            }
        } while (true);
    }

    public static String employeeId(Scanner scanner) {
        System.out.print("Nhập mã nhân viên(5 kí tự): ");
        do {
            String employeeId = scanner.nextLine();
            if (employeeId.trim().length() < 1 || employeeId.trim().length() > 5) {
                Notify.notifyError("mã nhân viên không được bỏ trống và nhiều hơn 5 kí tự !");
            } else {
                String result = AccountBusiness.validateAccEmployeeId(employeeId);
                if (result.equals("OK")) {
                    Notify.notifyOk();
                    return employeeId;
                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static boolean status(Scanner scanner) {
        System.out.print("Nhập trạng thái(0-Block 1-Active): ");
        do {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number == 0) {
                    Notify.notifyOk();
                    return false;
                } else if (number == 1) {
                    Notify.notifyOk();
                    return true;
                } else {
                    Notify.notifyError("Trạng thái chỉ nhập 0 hoặc 1 !");
                }
            } catch (NumberFormatException n) {
                Notify.notifyError("Lỗi khi nhập kí tự, vui lòng nhập lại!");
            }
        } while (true);
    }

    public void displayData() {
        String per;
        String status;
        if (this.permission) {
            per = "user";
        } else {
            per = "admin";

        }
        if (this.accountStatus) {
            status = "active";
        } else {
            status = "block";

        }
        System.out.printf("%s %-15d %-28s %-27s %-20s %-15s %-21s %s\n", starBorder(),
                this.accountId, this.userName, this.password, per, this.employeeId, status, starBorder());
    }
}
