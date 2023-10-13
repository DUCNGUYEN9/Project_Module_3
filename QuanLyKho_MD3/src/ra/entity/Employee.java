package ra.entity;

import ra.business.EmployeeBusiness;
import ra.notify.Notify;

import static ra.table.Table.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Employee {
    private String employeeId;
    private String employeeName;
    private Date birthDate;
    private String email;
    private String phone;
    private String address;
    private short employeeStatus;

    public Employee() {
    }

    public Employee(String employeeId, String employeeName, Date birthDate,
                    String email, String phone, String address, short employeeStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public short getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(short employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public void inputData(Scanner scanner) {
        this.employeeId = employeeId(scanner);
        this.employeeName = employeeName(scanner);
        System.out.print("Nhập ngày sinh(ngày/tháng/năm): ");
        do {
            String str = scanner.nextLine();
            if (str.trim().length() > 1) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    this.birthDate = sdf.parse(str);
                    Notify.notifyOk();
                    break;
                } catch (ParseException e) {
                    Notify.notifyError("Lỗi khi nhập ngày sinh!");
                }
            } else {
                Notify.notifyError("Vui lòng không để trống hãy nhập lại !");
            }
        } while (true);
        this.email = employeeEmail(scanner);
        this.phone = employeePhone(scanner);
        this.address = employeeAddress(scanner);
        this.employeeStatus = employeeStatus(scanner);
    }

    public static String employeeId(Scanner scanner) {
        System.out.print("Nhập mã nhân viên(5 kí tự): ");
        do {
            String employeeId = scanner.nextLine();
            if (employeeId.trim().length() < 1 || employeeId.trim().length() > 5) {
                Notify.notifyError("mã nhân viên không được bỏ trống và nhiều hơn 5 kí tự !");
            } else {
                String result = EmployeeBusiness.validateEmployeeId(employeeId);
                if (result.equals("OK")) {
                    Notify.notifyOk();
                    return employeeId;
                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static String employeeName(Scanner scanner) {
        System.out.print("Nhập tên nhân viên: ");
        do {

            String employeeName = scanner.nextLine();
            if (employeeName.trim().length() < 1 || employeeName.trim().length() > 100) {
                Notify.notifyError("Tên nhân viên không được bỏ trống !");
            } else {
                Notify.notifyOk();
                return employeeName;
            }
        } while (true);
    }

    public static String employeeEmail(Scanner scanner) {
        System.out.print("Nhập Email: ");
        do {
            String email = scanner.nextLine();
            String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            if (email.trim().length() < 1) {
                Notify.notifyError("Email không được bỏ trống !");
            } else if (!Pattern.matches(emailRegex, email)) {
                Notify.notifyError("Email không đúng định dạng, Vui lòng nhập lại !");
            } else {
                Notify.notifyOk();
                return email;
            }
        } while (true);
    }

    public static String employeePhone(Scanner scanner) {
        System.out.print("Nhập số điện thoại: ");
        do {
            String phone = scanner.nextLine();
            if (phone.trim().length() < 10 || phone.trim().length() > 12) {
                Notify.notifyError("số điện thoại phải từ (10 - 12) số !");
            } else {
                Notify.notifyOk();
                return phone;
            }
        } while (true);
    }

    public static String employeeAddress(Scanner scanner) {
        System.out.print("Nhập địa chỉ: ");
        do {
            String address = scanner.nextLine();
            if (address.trim().length() < 1) {
                Notify.notifyError("địa chỉ không được bỏ trống !");
            } else {
                Notify.notifyOk();
                return address;
            }
        } while (true);
    }

    public static short employeeStatus(Scanner scanner) {
        System.out.print("Nhập trạng thái(0-Hoạt động 1-Nghỉ chế độ 2-Nghỉ việc): ");
        do {
            try {
                short status = Short.parseShort(scanner.nextLine());
                if (status == 0 || status == 1 || status == 2) {
                    Notify.notifyOk();
                    return status;
                } else {
                    Notify.notifyError("trạng thái chỉ được nhập 0 hoặc 1 hoặc 2 !");
                }
            } catch (NumberFormatException nfe) {
                Notify.notifyError("Vui lòng nhập sô nguyên dương !");
            }
        } while (true);
    }


    public void displayData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String empStatus = null;
        if (this.employeeStatus == 0) {
            empStatus = "Hoạt động";
        } else if (this.employeeStatus == 1) {
            empStatus = "Nghỉ chế độ";
        } else {
            empStatus = "Nghỉ việc";
        }
        System.out.printf("%s %-15s %-20s %-15s %-20s %-15s %-20s %-20s %s\n",
                starBorder(), this.employeeId, this.employeeName, sdf.format(this.birthDate), this.email,
                this.phone, this.address, empStatus, starBorder());
    }
}
