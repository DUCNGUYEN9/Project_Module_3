package ra.entity;

import ra.business.ReceiptBusiness;
import ra.notify.Notify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static ra.color.Color.RESET;
import static ra.table.Table.starBorder;

public class Bill {
    private long billId;
    private String billCode;
    private boolean billType;
    private String employeeIdCreated;
    private Date created;
    private String employeeIdAuth;
    private Date authDate;
    private short billStatus;
    private String authDateNull;

    public Bill() {
    }

    public Bill(long billId, String billCode, boolean billType,
                String employeeIdCreated, Date created, String employeeIdAuth, Date authDate, short billStatus) {
        this.billId = billId;
        this.billCode = billCode;
        this.billType = billType;
        this.employeeIdCreated = employeeIdCreated;
        this.created = created;
        this.employeeIdAuth = employeeIdAuth;
        this.authDate = authDate;
        this.billStatus = billStatus;
    }

    public String getAuthDateNull() {
        return authDateNull;
    }

    public void setAuthDateNull(String authDateNull) {
        this.authDateNull = authDateNull;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public boolean isBillType() {
        return billType;
    }

    public void setBillType(boolean billType) {
        this.billType = billType;
    }

    public String getEmployeeIdCreated() {
        return employeeIdCreated;
    }

    public void setEmployeeIdCreated(String employeeIdCreated) {
        this.employeeIdCreated = employeeIdCreated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEmployeeIdAuth() {
        return employeeIdAuth;
    }

    public void setEmployeeIdAuth(String employeeIdAuth) {
        this.employeeIdAuth = employeeIdAuth;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public short getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(short billStatus) {
        this.billStatus = billStatus;
    }

    public void inputData(Scanner scanner) {
        this.billCode = receiptCode(scanner);
        this.employeeIdCreated = employeeIdCreated(scanner);
    }

    public void inputDataUser(Scanner scanner) {
        this.billCode = receiptCode(scanner);
        this.employeeIdCreated = employeeIdCreatedUser(scanner);
    }

    public static String receiptCode(Scanner scanner) {
        System.out.print("Nhập mã code: ");
        do {
            String receiptCode = scanner.nextLine();
            if (receiptCode.trim().length() < 1 || receiptCode.trim().length() > 5) {
                Notify.notifyError("Mã code không được bỏ trống và nhiều hơn 5 kí tự !");
            } else {
                String result = ReceiptBusiness.validateReceiptCode(receiptCode);
                if (result.equals("OK")) {
                    Notify.notifyOk();
                    return receiptCode;
                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static String employeeIdCreatedUser(Scanner scanner) {
        System.out.print("Nhập mã nhân viên: ");
        do {
            String employeeIdCreated = scanner.nextLine();
            if (employeeIdCreated.trim().length() < 1 || employeeIdCreated.trim().length() > 5) {
                Notify.notifyError("mã nhân viên không được bỏ trống và nhiều hơn 5 kí tự !");
            } else {
                String result = ReceiptBusiness.validateAccIdUser(employeeIdCreated);
                if (result.equals("OK")) {
                    Notify.notifyOk();
                    return employeeIdCreated;
                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static String employeeIdCreated(Scanner scanner) {
        System.out.print("Nhập mã nhân viên: ");
        do {
            String employeeIdCreated = scanner.nextLine();
            if (employeeIdCreated.trim().length() < 1 || employeeIdCreated.trim().length() > 5) {
                Notify.notifyError("mã nhân viên không được bỏ trống và nhiều hơn 5 kí tự !");
            } else {
                String result = ReceiptBusiness.validateAccId(employeeIdCreated);
                if (result.equals("OK")) {
                    Notify.notifyOk();
                    return employeeIdCreated;
                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static void createDate(Scanner scanner) {
        System.out.print("Nhập ngày tạo(ngày/tháng/năm): ");
        String dateCreate = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date created = sdf.parse(dateCreate);
        } catch (ParseException e) {
            Notify.notifyError(e.getMessage());
        }
        System.out.print("Nhập ngày duyệt(ngày/tháng/năm): ");
        String dateAuth = scanner.nextLine();
        try {
            Date authDate = sdf.parse(dateAuth);
        } catch (ParseException e) {
            Notify.notifyError(e.getMessage());
        }
    }

    public static String employeeIdAuth(Scanner scanner) {
        System.out.print("Nhập mã nhân viên duyệt: ");
        do {
            String employeeIdAuth = scanner.nextLine();
            if (employeeIdAuth.trim().length() != 5) {
                Notify.notifyError("mã nhân viên không được bỏ trống và nhiều hơn 5 kí tự !");
            } else {
                String result = ReceiptBusiness.validateAccId(employeeIdAuth);
                if (result.equals("OK")) {
                    Notify.notifyOk();
                    return employeeIdAuth;
                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static short receiptStatus(Scanner scanner) {
        System.out.print("Nhập trạng thái(0-Tạo 1-Hủy 2-Duyệt): ");
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
                Notify.notifyError(nfe.getMessage());
            }
        } while (true);
    }

    public static short receiptStatusUpdate(Scanner scanner) {
        System.out.print("Nhập trạng thái(0-Tạo 1-Hủy): ");
        do {
            try {
                short status = Short.parseShort(scanner.nextLine());
                if (status == 0 || status == 1) {
                    Notify.notifyOk();
                    return status;
                } else {
                    Notify.notifyError("trạng thái chỉ được nhập 0 hoặc 1 !");
                }
            } catch (NumberFormatException nfe) {
                Notify.notifyError(nfe.getMessage());
            }
        } while (true);
    }

    public void displayData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String statusBill = null;
        if (this.billStatus == 0) {
            statusBill = "Tạo";
        } else if (this.billStatus == 1) {
            statusBill = "Hủy";
        } else {
            statusBill = "Duyệt";
        }
        if (this.authDate != null) {
            System.out.printf("%s %-11d %-10s %-15s %-20s %-15s %-20s %-17s %-20s %s\n", starBorder(),
                    this.billId, this.billCode, this.billType ? "Phiếu nhập" : "Phiếu xuất",
                    this.employeeIdCreated, sdf.format(this.created), this.employeeIdAuth,
                    sdf.format(this.authDate), statusBill + RESET, starBorder());
        } else {
            System.out.printf("%s %-11d %-10s %-15s %-20s %-15s %-20s %-17s %-20s %s\n", starBorder(),
                    this.billId, this.billCode, this.billType ? "Phiếu nhập" : "Phiếu xuất",
                    this.employeeIdCreated, sdf.format(this.created), getAuthDateNull(),
                    getAuthDateNull(), statusBill + RESET, starBorder());
        }
    }
}
