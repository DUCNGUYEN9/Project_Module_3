package ra.run;
import ra.notify.Notify;
import ra.presentation.*;
import static ra.table.Table.*;
import java.util.Scanner;

public class AdminManagement {
    public static void adminManagement() {
        Scanner scanner = new Scanner(System.in);
        boolean noExit = true;
        do {
            headerMenu("WAREHOUSE MANAGEMENT");
            bodyMenu("1. Quản lý sản phẩm");
            bodyMenu("2. Quản lý nhân viên");
            bodyMenu("3. Quản lý tài khoản");
            bodyMenu("4. Quản lý phiếu nhập");
            bodyMenu("5. Quản lý phiếu xuất");
            bodyMenu("6. Quản lý báo cáo");
            footerMenu("7. Thoát",61);
            try {
                Notify.notifyEnter("Lựa chọn của bạn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        ProductMenu.productMenu();
                        break;
                    case 2:
                        EmployeeMenu.employeeMenu();
                        break;
                    case 3:
                        AccountMenu.accountMenu();
                        break;
                    case 4:
                        ReceiptMenu.receiptMenu();
                        break;
                    case 5:
                        BillMenu.billMenu();
                        break;
                    case 6:
                        ReportMenu.reportMenu();
                        break;
                    case 7:
                        noExit = false;
                        break;
                    default:
                        Notify.notifyError("Lựa chọn chỉ trong khoảng từ 1 - 7 !");
                }
            } catch (NumberFormatException nfe) {
                Notify.notifyError(nfe.getMessage());
            }
        } while (noExit);
    }
}
