package ra.presentation;


import ra.business.AccountBusiness;
import ra.entity.Account;

import java.util.List;
import java.util.Scanner;

import static ra.color.Color.*;
import static ra.color.Color.RESET;
import static ra.notify.Notify.*;
import static ra.table.Table.*;

public class AccountMenu {
    static long totalPage;
    static Scanner scanner = new Scanner(System.in);

    public static void accountMenu() {
        boolean isExit = true;
        do {
            headerMenu("ACCOUNT MANAGEMENT ");
            bodyMenu("1. Danh sách tài khoản");
            bodyMenu("2. Tạo tài khoản mới");
            bodyMenu("3. Cập nhật trạng thái tài khoản");
            bodyMenu("4. Tìm kiếm tài khoản");
            footerMenu("5. Thoát", 61);
            try {
                notifyEnter("Lựa chọn của bạn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayAccount();
                        break;
                    case 2:
                        addNewAccount();
                        break;
                    case 3:
                        updateStatusAccount();
                        break;
                    case 4:
                        searchUserName();
                        break;
                    case 5:
                        isExit = false;
                        break;
                    default:
                        notifyError("Lựa chọn chỉ trong khoảng từ 1 - 5 !");
                }
            } catch (NumberFormatException nfe) {
                notifyError(nfe.getMessage());
            }
        } while (isExit);
    }

    public static void displayAccount() {
        displayTable(1, 10);

    }

    public static void displayTable(int offSet, int pageNumber) {
        List<Account> accountList = AccountBusiness.getAllAccount(offSet, pageNumber);
        System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG HIỂN THỊ TÀI KHOẢN NHÂN VIÊN ◯◯◯" + RESET);
        borderTable(150);
        System.out.printf("%s %-22s %-28s %-27s %-20s %-15s %-25s %s\n", starBorder(), BLUE_BB +
                        "Mã tài khoản", "Tên tài khoản", "Mật khẩu", "Quyền tài khoản",
                "Mã nhân viên", "Trạng thái" + RESET, starBorder());
        accountList.forEach(Account::displayData);
        borderTable(150);
        paginationPage(offSet, pageNumber);
        displayPage(pageNumber);
    }

    public static void displayPage(int pageNumber) {
        try {
            notifyEnterPagination("Hãy nhập số trang bạn muốn hiển thị (0 để thoát): ");
            int pageDisplay = Integer.parseInt(scanner.nextLine());
            if (pageDisplay > 0 && pageDisplay <= totalPage) {
                displayTable(pageDisplay, pageNumber);
            } else if (pageDisplay == 0) {
            } else {
                notifyError("Vui lòng nhập số nguyên dương và nhỏ hơn " + totalPage);
            }
        } catch (NumberFormatException nfe) {
            notifyError("Vui lòng nhập số nguyên dương !");
        }
    }

    public static void paginationPage(int offSet, int pageNumber) {
        int totalRecord = AccountBusiness.getTotalRecord();
        double pages = Math.ceil((double) totalRecord / pageNumber);
        totalPage = Math.round(pages);
        System.out.printf("%-55s", " ");
        for (int i = 1; i <= totalPage; i++) {
            if (offSet == i) {
                System.out.printf("%-2s %-2s", BLUE_BACKGROUND + " " + i + " " + RESET, " ");
            } else {
                System.out.printf("%-2s %-2s", i, " ");
            }
        }
        System.out.println();
    }
    public static void addNewAccount() {
        EmployeeMenu.displayEmployee();
        Account accountNew = new Account();
        accountNew.inputData(scanner);
        boolean result = AccountBusiness.addNewAccount(accountNew);
        if (result) {
            notifySuccess("Thêm mới thành công");
        } else {
            notifyError("Có lỗi xảy ra trong quá trình thực hiện !");
        }
    }
    public static void updateStatusAccount() {
        displayAccount();
        while (true) {
            System.out.println("Nhập mã tài khoản cần cập nhật trạng thái:");
            int accountIdUpdate = Integer.parseInt(scanner.nextLine());
            Account accountUpdate = AccountBusiness.checkAccountByIdEmployeeStatus(accountIdUpdate);
            if (accountUpdate != null) {
                accountUpdate.setAccountStatus(Account.status(scanner));
                //Thực hiện cập nhật
                boolean result = AccountBusiness.updateStatusAccount(accountUpdate);
                if (result) {
                    notifySuccess("Cập nhật thành công");
                    break;
                } else {
                    notifyError("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lai !");
                }
            } else {
                //Mã sach không tồn tại trong CSDL
                notifyError("Mã (" + accountIdUpdate + ") không được phép cập nhật hoặc không tồn tại !");
            }
        }
    }
    public static void searchUserName() {
        do {
            System.out.println("Nhập vào tên tài khoản cần tìm: ");
            String NameSearch = scanner.nextLine();
            Account checkNull = AccountBusiness.checkAccountByUserName(NameSearch);
            if (checkNull != null) {
                List<Account> accountList = AccountBusiness.searchUserNameAccount(NameSearch);
                System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ KẾT QUẢ TÌM KIẾM ◯◯◯" + RESET);
                borderTable(150);
                System.out.printf("%s %-22s %-28s %-27s %-20s %-15s %-25s %s\n", starBorder(), BLUE_BB +
                                "Mã tài khoản", "Tên tài khoản", "Mật khẩu", "Quyền tài khoản",
                        "Mã nhân viên", "Trạng thái" + RESET, starBorder());
                accountList.forEach(Account::displayData);
                borderTable(150);
                break;
            } else {
                notifyError("Không có tên tài khoản " + NameSearch);
            }
        } while (true);
    }

}
