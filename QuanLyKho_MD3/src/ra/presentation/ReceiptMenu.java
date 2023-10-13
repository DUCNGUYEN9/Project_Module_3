package ra.presentation;

import ra.business.BillBusiness;
import ra.business.ProductBusiness;
import ra.business.ReceiptBusiness;
import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.entity.Employee;
import ra.notify.Notify;

import static ra.color.Color.*;
import static ra.color.Color.RESET;
import static ra.notify.Notify.*;
import static ra.table.Table.*;

import java.util.List;
import java.util.Scanner;


public class ReceiptMenu {
    static Scanner scanner = new Scanner(System.in);
    static long totalPage;

    public static void receiptMenu() {
        boolean isExit = true;
        do {
            headerMenu(" RECEIPT MANAGEMENT ");
            bodyMenu("1. Danh sách phiếu nhập");
            bodyMenu("2. Tạo phiếu nhập");
            bodyMenu("3. Cập nhật thông tin phiếu nhập");
            bodyMenu("4. Chi tiết phiếu nhập");
            bodyMenu("5. Duyệt phiếu nhập");
            bodyMenu("6. Tìm kiếm phiếu nhập");
            footerMenu("7. Thoát", 61);
            try {
                notifyEnter("Lựa chọn của bạn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayReceipt();
                        break;
                    case 2:
                        addNewReceipt();
                        break;
                    case 3:
                        updateReceipt();
                        break;
                    case 4:
                        displayReceiptBill();
                        break;
                    case 5:
                        approveReceipt();
                        break;
                    case 6:
                        searchIdReceipt();
                        break;
                    case 7:
                        isExit = false;
                        break;
                    default:
                        notifyError("Lựa chọn chỉ trong khoảng từ 1 - 7 !");
                }
            } catch (NumberFormatException nfe) {
                notifyError(nfe.getMessage());
            }
        } while (isExit);
    }

    public static void displayReceipt() {
        displayTable(1, 10);
    }

    public static void displayReceiptBill() {
        displayTableBill(1, 10);
        notifyConfirm("Bạn có muốn thêm phiếu chi tiết để nhập hàng không ?");
        int Num = Integer.parseInt(scanner.nextLine());
        if (Num == 1) {
            addNewReceiptDetail();
        }
    }

    public static void displayTable(int offSet, int pageNumber) {
        List<Bill> billList = ReceiptBusiness.getAllReceipt(offSet, pageNumber);
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

    public static void displayTableBill(int offSet, int pageNumber) {
        List<BillDetail> billDetailList = ReceiptBusiness.getAllReceiptDetail(offSet, pageNumber);
        System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG HIỂN THỊ CHI TIẾT PHIẾU NHẬP ◯◯◯" + RESET);
        borderTable(150);

        System.out.printf("%s %-35s %-20s %-24s %-20s %-18s %-20s %s\n", starBorder(), BLUE_BB +
                "Mã phiếu chi tiết", "Mã phiếu nhập", "Mã sản phẩm", "Số lượng", "Giá(VND)",
                "Trạng thái" + RESET, starBorder());
        billDetailList.forEach(BillDetail::displayData);
        borderTable(150);
        paginationPageBill(offSet, pageNumber);
        displayPage(pageNumber);
    }

    public static void displayPage(int pageNumber) {
        notifyEnterPagination("Hãy nhập số trang bạn muốn hiển thị (0 để thoát): ");
        int pageDisplay = paginationEnterNumber(scanner);
        if (pageDisplay > 0 && pageDisplay <= totalPage) {
            displayTable(pageDisplay, pageNumber);
        } else if (pageDisplay == 0) {
        } else {
            notifyError("Vui lòng nhập số nhỏ hơn " + totalPage);
        }
    }


    public static void paginationPage(int offSet, int pageNumber) {
        int totalRecord = ReceiptBusiness.getTotalRecord();
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

    public static void paginationPageBill(int offSet, int pageNumber) {
        int totalRecord = ReceiptBusiness.getTotalRecordDetail();
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

    public static void addNewReceipt() {
        displayReceipt();
        Bill receiptNew = new Bill();
        notifyConfirm("Bạn có muốn tiếp tục tạo phiếu không ?");
        int Num = paginationEnterNumber(scanner);
        if (Num == 2) return;
        notifyEnter("Bạn muốn thêm mấy phiếu: ");
        int number = paginationEnterNumber(scanner);
        for (int i = 1; i <= number; i++) {
            System.out.printf(LAVENDER_BOLD + "Thêm phiếu thứ %d\n" + RESET, i);
            receiptNew.inputData(scanner);
            boolean result = ReceiptBusiness.addNewReceipt(receiptNew);
            if (result) {
                notifySuccess("Thêm mới thành công");
            } else {
                notifyError("Có lỗi xảy ra trong quá trình thực hiện !");
            }
        }

    }

    public static void updateReceipt() {
        displayReceipt();
        notifyEnter("Nhập mã phiếu cần cập nhật(0 để thoát): ");
        do {
            String billIdUpdate = scanner.nextLine();
            Bill billUpdate = ReceiptBusiness.getReceiptByIdStatus(billIdUpdate);
            if (billIdUpdate.equals("0")) {
                return;
            }
            if (billUpdate != null) {
                notifyConfirm("Bạn có muốn cập nhật mã code không ?");
                int Num = paginationEnterNumber(scanner);
                if (Num == 1) {
                    billUpdate.setBillCode(Bill.receiptCode(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật mã nhân viên tạo không ?");
                int Num1 = paginationEnterNumber(scanner);
                if (Num1 == 1) {
                    billUpdate.setEmployeeIdCreated(Bill.employeeIdCreated(scanner));
                }
                boolean result = ReceiptBusiness.updateReceipt(billUpdate);
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

    /*
     * */
    public static void approveReceipt() {
        displayReceipt();
        do {
            notifyConfirm("Bạn có muốn duyệt phiếu không ?");
            int Num = Integer.parseInt(scanner.nextLine());
            if (Num == 2) {
                return;
            }
            notifyWarning("Hãy chọn những phiếu chưa duyệt");
            notifyEnter("Nhập mã phiếu cần duyệt:");
            String billId = scanner.nextLine();
            String results = ReceiptBusiness.validateReceiptId(billId);
            if (results.equals("OK")) {
                Bill billUpdate = ReceiptBusiness.getReceiptByIdStatus(billId);
                if (billUpdate != null) {
                    billUpdate.setEmployeeIdAuth(Bill.employeeIdAuth(scanner));
                    boolean result = ReceiptBusiness.approveReceipt(billUpdate);
                    if (result) {
                        notifySuccess("Duyệt thành công");
                        break;
                    } else {
                        notifyError("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lại !");
                    }
                } else {
                    notifyError("Mã (" + billId + ") không tồn tại !");
                }
            } else {
                Notify.notifyError(results);
            }
        } while (true);
    }

    public static void searchIdReceipt() {
        do {
            try {
                System.out.println("Nhập vào mã phiếu nhập cần tìm: ");
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
                    notifyConfirm("Bạn có muốn duyệt phiếu không ?");
                    int Num1 = Integer.parseInt(scanner.nextLine());
                    if (Num1 == 1) {
                        approveReceipt();
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

    public static void addNewReceiptDetail() {
        displayReceipt();
        BillDetail receiptNew = new BillDetail();
        notifyWarning("Hãy chọn những phiếu chưa duyệt");
        notifyEnter("Bạn muốn thêm mấy phiếu: ");
        int number = paginationEnterNumber(scanner);
        for (int i = 1; i <= number; i++) {
            System.out.printf(LAVENDER_BOLD + "Thêm phiếu thứ %d\n" + RESET, i);
            receiptNew.inputData(scanner);
            boolean result = ReceiptBusiness.addNewReceiptDetail(receiptNew);
            if (result) {
                notifySuccess("Thêm mới thành công");
            } else {
                notifyError("Có lỗi xảy ra trong quá trình thực hiện !");
            }
        }
    }

    /*
     *
     * */
//    public static void updateReceiptDetail() {
//        displayReceipt();
//        do {
//            notifyEnter("Nhập mã phiếu cần cập nhật(0 để thoát): ");
//            int billIdUpdate = paginationEnterNumber(scanner);
//            Bill billUpdate = BillBusiness.getBillById(billIdUpdate);
//            if (billIdUpdate==0) {
//                return;
//            }
//            if (billUpdate != null) {
//                notifyConfirm("Bạn có muốn cập nhật mã code không ?");
//                int Num = paginationEnterNumber(scanner);
//                if (Num == 1) {
//                    billUpdate.setBillCode(Bill.receiptCode(scanner));
//                }
//                notifyConfirm("Bạn có muốn cập nhật mã nhân viên tạo không ?");
//                int Num1 = paginationEnterNumber(scanner);
//                if (Num1 == 1) {
//                    billUpdate.setEmployeeIdCreated(Bill.employeeIdCreated(scanner));
//                }
//                boolean result = ReceiptBusiness.updateReceipt(billUpdate);
//                if (result) {
//                    notifySuccess("Cập nhật thành công");
//                    break;
//                } else {
//                    notifyError("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lai !");
//                }
//
//            } else {
//                notifyError("Mã (" + billIdUpdate + ") không tồn tại hoặc đã được duyệt !");
//            }
//        } while (true);
//    }

}


