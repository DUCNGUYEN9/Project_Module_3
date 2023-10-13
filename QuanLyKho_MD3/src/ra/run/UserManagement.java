package ra.run;

import ra.business.BillBusiness;
import ra.business.ReceiptBusiness;
import ra.business.UserBusiness;
import ra.entity.Bill;
import ra.notify.Notify;
import ra.presentation.BillMenu;
import ra.presentation.ReceiptMenu;

import java.util.List;
import java.util.Scanner;

import static ra.color.Color.*;
import static ra.color.Color.RESET;
import static ra.notify.Notify.*;
import static ra.table.Table.*;

public class UserManagement {
    static Scanner scanner = new Scanner(System.in);
    static long totalPage;
    static short saveNumber;

    public static void userManagement() {
        boolean noExit = true;
        do {
            headerMenu("WAREHOUSE MANAGEMENT");
            bodyMenu("1. Danh sách phiếu nhập theo trạng thái");
            bodyMenu("2. Tạo phiếu nhập");
            bodyMenu("3. Cập nhật phiếu nhập");
            bodyMenu("4. Tìm kiếm phiếu nhập");
            bodyMenu("5. Danh sách phiếu xuất theo trạng thái");
            bodyMenu("6. Tạo phiếu xuất");
            bodyMenu("7. Cập nhật phiếu xuất");
            bodyMenu("8. Tìm kiếm phiếu xuất");
            footerMenu("9. Thoát", 61);

            try {
                Notify.notifyEnter("Lựa chọn của bạn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        userReceiptStatus();
                        break;
                    case 2:
                        addNewReceipt();
                        break;
                    case 3:
                        updateReceipt();
                        break;
                    case 4:
                        searchIdReceipt();
                        break;
                    case 5:
                        userBillStatus();
                        break;
                    case 6:
                        addNewBillDetail();
                        break;
                    case 7:
                        updateBill();
                        break;
                    case 8:
                        searchIdBill();
                        break;
                    case 9:
                        noExit = false;
                        break;
                    default:
                        Notify.notifyError("Lựa chọn chỉ trong khoảng từ 1 - 9 !");
                }
            } catch (NumberFormatException nfe) {
                Notify.notifyError("Vui lòng chỉ nhập số nguyên dương !");
            }
        } while (noExit);
    }

    public static void userReceiptStatus() {
        short number = Bill.receiptStatus(scanner);
        saveNumber = number;
        displayTable(1, 5, number);
    }

    /**
     * create and display table data
     */

    public static void displayTable(int offSet, int pageNumber, short number) {
        List<Bill> billList = UserBusiness.getUserReceiptStatus(offSet, pageNumber, number);
        System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG HIỂN THỊ PHIẾU NHẬP ◯◯◯" + RESET);
        borderTable(150);
        System.out.printf("%s %-18s %-10s %-15s %-20s %-15s %-20s %-17s %-20s %s\n", starBorder(), BLUE_BB +
                        "Mã phiếu", "Mã code", "Loại phiếu", "Mã nhân viên tạo", "Ngày tạo", "Mã nhân viên duyệt",
                "Ngày duyệt", "Trạng thái" + RESET, starBorder());
        billList.forEach(Bill::displayData);
        borderTable(150);
        paginationPage(offSet, pageNumber);
        displayPage(pageNumber);
    }

    public static void displayPage(int pageNumber) {
        short number = saveNumber;
        notifyEnterPagination("Hãy nhập số trang bạn muốn hiển thị (0 để thoát): ");
        int pageDisplay = paginationEnterNumber(scanner);
        if (pageDisplay > 0 && pageDisplay <= totalPage) {
            displayTable(pageDisplay, pageNumber, number);
        } else if (pageDisplay == 0) {
        } else {
            notifyError("Vui lòng nhập số nguyên dương và nhỏ hơn " + totalPage);
        }

    }


    public static void paginationPage(int offSet, int pageNumber) {
        short number = saveNumber;
        int totalRecord = UserBusiness.getTotalRecordReceipt(number);
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

    public static void userBillStatus() {
        short number = Bill.receiptStatus(scanner);
        saveNumber = number;
        displayTableBill(1, 5, number);
    }

    public static void displayTableBill(int offSet, int pageNumber, short number) {
        List<Bill> billList = UserBusiness.getUserBillStatus(offSet, pageNumber, number);
        System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG HIỂN THỊ PHIẾU NHẬP ◯◯◯" + RESET);
        borderTable(150);
        System.out.printf("%s %-18s %-10s %-15s %-20s %-15s %-20s %-17s %-20s %s\n", starBorder(), BLUE_BB +
                        "Mã phiếu", "Mã code", "Loại phiếu", "Mã nhân viên tạo", "Ngày tạo", "Mã nhân viên duyệt",
                "Ngày duyệt", "Trạng thái" + RESET, starBorder());
        billList.forEach(Bill::displayData);
        borderTable(150);
        paginationPageBill(offSet, pageNumber);
        displayPageBill(pageNumber);
    }

    public static void paginationPageBill(int offSet, int pageNumber) {
        short number = saveNumber;
        int totalRecord = UserBusiness.getTotalRecordBill(number);
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

    public static void displayPageBill(int pageNumber) {
        short number = saveNumber;
        notifyEnterPagination("Hãy nhập số trang bạn muốn hiển thị (0 để thoát): ");
        int pageDisplay = paginationEnterNumber(scanner);
        if (pageDisplay > 0 && pageDisplay <= totalPage) {
            displayTableBill(pageDisplay, pageNumber, number);
        } else if (pageDisplay == 0) {
        } else {
            notifyError("Vui lòng nhập số nguyên dương và nhỏ hơn " + totalPage);
        }
    }

    /*
     *
     * */
    public static void addNewReceipt() {
        ReceiptMenu.displayReceipt();
        notifyConfirm("Bạn có muốn tiếp tục tạo phiếu không ?");
        int Num = paginationEnterNumber(scanner);
        if (Num == 2) return;
        Bill receiptNew = new Bill();
        notifyEnter("Bạn muốn thêm mấy phiếu: ");
        int number = paginationEnterNumber(scanner);
        for (int i = 1; i <= number; i++) {
            System.out.printf(LAVENDER_BOLD + "Thêm phiếu thứ %d\n" + RESET, i);
            receiptNew.inputDataUser(scanner);
            boolean result = ReceiptBusiness.addNewReceipt(receiptNew);
            if (result) {
                notifySuccess("Thêm mới thành công");
            } else {
                notifyError("Có lỗi xảy ra trong quá trình thực hiện !");
            }
        }
    }

    public static void addNewBillDetail() {
        BillMenu.displayBillDetail();
        notifyConfirm("Bạn có muốn tiếp tục tạo phiếu không ?");
        int Num = paginationEnterNumber(scanner);
        if (Num == 2) return;
        Bill billNew = new Bill();
        notifyEnter("Bạn muốn thêm mấy phiếu: ");
        int number = paginationEnterNumber(scanner);
        for (int i = 1; i <= number; i++) {
            System.out.printf(LAVENDER_BOLD + "Thêm phiếu thứ %d\n" + RESET, i);
            billNew.inputDataUser(scanner);
            boolean result = BillBusiness.addNewBillDetail(billNew);
            if (result) {
                notifySuccess("Thêm mới thành công");
            } else {
                notifyError("Có lỗi xảy ra trong quá trình thực hiện !");
            }
        }
    }

    public static void searchIdBill() {
        do {
            try {
                notifyEnter("Nhập vào mã phiếu xuất cần tìm: ");
                int id = Integer.parseInt(scanner.nextLine());
                Bill checkNull = BillBusiness.getBillById(id);
                if (checkNull != null) {
                    List<Bill> billList = BillBusiness.searchIdBill(id);
                    System.out.printf("%-50s %s\n", "", CYAN_BB +
                            "◯◯◯ KẾT QUẢ TÌM KIẾM THEO MÃ PHIẾU XUẤT ◯◯◯" + RESET);
                    borderTable(150);
                    System.out.printf("%s %-18s %-10s %-15s %-20s %-15s %-20s %-17s %-20s %s\n",
                            starBorder(), BLUE_BB + "Mã phiếu", "Mã code", "Loại phiếu", "Mã nhân viên tạo",
                            "Ngày tạo", "Mã nhân viên duyệt", "Ngày duyệt", "Trạng thái" + RESET, starBorder());
                    billList.forEach(Bill::displayData);
                    borderTable(150);
                    notifyConfirm("Bạn có muốn cập nhật phiếu không ?");
                    int Num = Integer.parseInt(scanner.nextLine());
                    if (Num == 1) {
                        BillMenu.updateBill();
                    }

                    break;
                } else {
                    notifyError("Không có mã phiếu " + id);
                }
            } catch (NumberFormatException n) {
                Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

            }
        } while (true);
    }

    public static void updateReceipt() {
        ReceiptMenu.displayReceipt();
        notifyEnter("Nhập mã phiếu cần cập nhật(0 để thoát): ");
        do {
            String billIdUpdate = scanner.nextLine();
            Bill billUpdate = ReceiptBusiness.getReceiptByIdStatus(billIdUpdate);
            if (billIdUpdate.equals("0")) {
                return;
            }
            if (billUpdate != null) {
                try {
                    notifyConfirm("Bạn có muốn cập nhật mã code không ?");
                    int Num = Integer.parseInt(scanner.nextLine());
                    if (Num == 1) {
                        billUpdate.setBillCode(Bill.receiptCode(scanner));
                    }
                    notifyConfirm("Bạn có muốn cập nhật mã nhân viên tạo không(chỉ user) ?");
                    int Num1 = Integer.parseInt(scanner.nextLine());
                    if (Num1 == 1) {
                        billUpdate.setEmployeeIdCreated(Bill.employeeIdCreatedUser(scanner));
                    }
                    notifyConfirm("Bạn có muốn cập nhật trạng thái không ?");
                    int Num2 = Integer.parseInt(scanner.nextLine());
                    if (Num2 == 1) {
                        billUpdate.setBillStatus(Bill.receiptStatusUpdate(scanner));
                    }
                    boolean result = ReceiptBusiness.updateReceipt(billUpdate);
                    if (result) {
                        notifySuccess("Cập nhật thành công");
                        break;
                    } else {
                        notifyError("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lai !");
                    }
                } catch (NumberFormatException n) {
                    Notify.notifyError("Vui lòng nhập số nguyên dương !");
                }
            } else {
                notifyError("Mã (" + billIdUpdate + ") không tồn tại hoặc đã được duyệt !");
            }
        } while (true);
    }

    public static void searchIdReceipt() {
        do {
            try {
                notifyEnter("Nhập vào mã phiếu nhập cần tìm: ");
                int id = Integer.parseInt(scanner.nextLine());
                Bill checkNull = ReceiptBusiness.getReceiptById(id);
                if (checkNull != null) {
                    List<Bill> billList = ReceiptBusiness.searchIdReceipt(id);
                    System.out.printf("%-50s %s\n", "", CYAN_BB +
                            "◯◯◯ KẾT QUẢ TÌM KIẾM THEO MÃ PHIẾU NHẬP ◯◯◯" + RESET);
                    borderTable(150);
                    System.out.printf("%s %-18s %-10s %-15s %-20s %-15s %-20s %-17s %-20s %s\n",
                            starBorder(), BLUE_BB + "Mã phiếu", "Mã code", "Loại phiếu", "Mã nhân viên tạo",
                            "Ngày tạo", "Mã nhân viên duyệt", "Ngày duyệt", "Trạng thái" + RESET, starBorder());
                    billList.forEach(Bill::displayData);
                    borderTable(150);
                    notifyConfirm("Bạn có muốn cập nhật phiếu không ?");
                    int Num = Integer.parseInt(scanner.nextLine());
                    if (Num == 1) {
                        updateReceipt();
                    }

                    break;
                } else {
                    notifyError("Không có mã phiếu " + id);
                }
            } catch (NumberFormatException n) {
                Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");
            }
        } while (true);
    }

    public static void updateBill() {
        BillMenu.displayBillDetail();
        notifyEnter("Nhập mã phiếu xuất cần cập nhật(0 để thoát):");
        do {
            String billIdUpdate = scanner.nextLine();
            Bill billUpdate = BillBusiness.getBillByIdStatus(billIdUpdate);
            if (billIdUpdate.equals("0")) {
                return;
            }
            if (billUpdate != null) {
                notifyConfirm("Bạn có muốn cập nhật mã code không ?");
                int Num = Integer.parseInt(scanner.nextLine());
                if (Num == 1) {
                    billUpdate.setBillCode(Bill.receiptCode(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật mã nhân viên tạo không(chỉ user) ?");
                int Num1 = Integer.parseInt(scanner.nextLine());
                if (Num1 == 1) {
                    billUpdate.setEmployeeIdCreated(Bill.employeeIdCreatedUser(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật trạng thái không ?");
                int Num2 = Integer.parseInt(scanner.nextLine());
                if (Num2 == 1) {
                    billUpdate.setBillStatus(Bill.receiptStatusUpdate(scanner));
                }
                boolean result = BillBusiness.updateBill(billUpdate);
                if (result) {
                    notifySuccess("Cập nhật thành công");
                    break;
                } else {
                    notifyError("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lai !");
                }
            } else {
                notifyError("Mã (" + billIdUpdate + ") không tồn tại hoặc đã được duyệt !");

            }
        } while (true);
    }
}
