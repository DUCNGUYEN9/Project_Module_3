package ra.presentation;

import ra.business.EmployeeBusiness;
import ra.business.ReportBusiness;
import ra.entity.Bill;
import ra.entity.Employee;
import ra.notify.Notify;
import ra.util.ConnectionDB;

import static ra.color.Color.*;
import static ra.color.Color.RESET;
import static ra.notify.Notify.*;
import static ra.table.Table.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ReportMenu {
    static Scanner scanner = new Scanner(System.in);

    public static void reportMenu() {
        boolean isExit = true;
        do {
            headerMenu(" REPORT MANAGEMENT ");
            bodyMenu("1. Thống kê chi phí theo ngày, tháng, năm");
            bodyMenu("2. Thống kê chi phí theo khoảng thời gian");
            bodyMenu("3. Thống kê doanh thu theo ngày, tháng, năm");
            bodyMenu("4. Thống kê doanh thu theo khoảng thời gian");
            bodyMenu("5. Thống kê số nhân viên theo từng trạng thái");
            bodyMenu("6. Thống kê sp nhập nhiều nhất theo thời gian");
            bodyMenu("7. Thống kê sp nhập ít nhất theo thời gian");
            bodyMenu("8. Thống kê sp xuất nhiều nhất theo thời gian");
            bodyMenu("9. Thống kê sp xuất ít nhất theo thời gian");
            footerMenu("10. Thoát", 61);
            try {
                notifyEnter("Lựa chọn của bạn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        statisticsExpense();
                        break;
                    case 2:
                        statisticsExpenseTime();
                        break;
                    case 3:
                        statisticsProfit();
                        break;
                    case 4:
                        statisticsProfitTime();
                        break;
                    case 5:
                        ReportBusiness.statisticsEmployee();
                        break;
                    case 6:
                        statisticsImportMany();
                        break;
                    case 7:
                        statisticsImportLeast();
                        break;
                    case 8:
                        statisticsExportMany();
                        break;
                    case 9:
                        statisticsExportLeast();
                        break;
                    case 10:
                        isExit = false;
                        break;
                    default:
                        notifyError("Lựa chọn chỉ trong khoảng từ 1 - 10 !");
                }
            } catch (NumberFormatException nfe) {
                notifyError("Vui lòng nhập số nguyên dương !");
            }
        } while (isExit);
    }

    public static void statisticsExpense() {
        notifyEnterPagination("Nhập vào ngày, tháng, năm cần Thống kê chi phí (VD: 01-01-2023): ");
        do {
            String str = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                // Chuyển đổi chuỗi thành đối tượng java.util.Date
                Date date = sdf.parse(str);
                // Gọi hàm getStatisticsExpense với đối tượng Date
                float expense = ReportBusiness.getStatisticsExpense(date);
                System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG THỐNG KÊ ◯◯◯" + RESET);
                borderTable(150);
                System.out.printf("%-30s %-52s %-71s %s\n", starBorder(), BLUE_BB +
                        "Ngày-Tháng-Năm", "Tổng chi phí(VND)" + RESET, starBorder());
                System.out.printf("%-30s %-45s %-67.3f %s\n", starBorder(), str, expense, starBorder());
                borderTable(150);
                break;
            } catch (ParseException e) {
                notifyError("Định dạng ngày không hợp lệ. Sử dụng định dạng dd-MM-yyyy.");
            }
        } while (true);
    }

    public static void statisticsExpenseTime() {
        notifyEnterPagination("Nhập vào ngày, tháng, năm cần Thống kê chi phí\n");
        do {
            notifyEnter("Nhập vào ngày, tháng, năm thứ 1 (VD: 01-01-2023): ");
            String str1 = scanner.nextLine();
            notifyEnter("Nhập vào ngày, tháng, năm thứ 2 (VD: 01-01-2023): ");
            String str2 = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                // Chuyển đổi chuỗi thành đối tượng java.util.Date
                Date date1 = sdf.parse(str1);
                Date date2 = sdf.parse(str2);
                // Gọi hàm getStatisticsExpense với đối tượng Date
                float expense = ReportBusiness.getStatisticsExpenseTime(date1, date2);
                System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG THỐNG KÊ  ◯◯◯" + RESET);
                borderTable(150);
                System.out.printf("%-30s %-36s %-36s %-50s %s\n", starBorder(), BLUE_BB +
                        "Từ Ngày-Tháng-Năm", "Đến Ngày-Tháng-Năm", "Tổng chi phí(VND)" + RESET, starBorder());
                System.out.printf("%-30s %-29s %-36s %-46.3f %s\n",
                        starBorder(), str1, str2, expense, starBorder());
                borderTable(150);
                break;
            } catch (ParseException e) {
                notifyError("Định dạng ngày không hợp lệ. Sử dụng định dạng dd-MM-yyyy.");
            }
        } while (true);
    }

    public static void statisticsProfit() {
        notifyEnterPagination("Nhập vào ngày, tháng, năm cần Thống kê doanh thu (VD: 01-01-2023): ");
        do {
            String str = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                // Chuyển đổi chuỗi thành đối tượng java.util.Date
                Date date = sdf.parse(str);
                // Gọi hàm getStatisticsExpense với đối tượng Date
                float expense = ReportBusiness.getStatisticsProfit(date);
                System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG THỐNG KÊ ◯◯◯" + RESET);
                borderTable(150);
                System.out.printf("%-30s %-52s %-71s %s\n", starBorder(), BLUE_BB +
                        "Ngày-Tháng-Năm", "Tổng doanh thu(VND)" + RESET, starBorder());
                System.out.printf("%-30s %-45s %-67.3f %s\n", starBorder(), str, expense, starBorder());
                borderTable(150);
                break;
            } catch (ParseException e) {
                notifyError("Định dạng ngày không hợp lệ. Sử dụng định dạng dd-MM-yyyy.");
            }
        } while (true);
    }

    public static void statisticsProfitTime() {
        notifyEnterPagination("Nhập vào ngày, tháng, năm cần Thống kê doanh thu\n");
        do {
            notifyEnter("Nhập vào ngày, tháng, năm thứ 1 (VD: 01-01-2023): ");
            String str1 = scanner.nextLine();
            notifyEnter("Nhập vào ngày, tháng, năm thứ 2 (VD: 01-01-2023): ");
            String str2 = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                // Chuyển đổi chuỗi thành đối tượng java.util.Date
                Date date1 = sdf.parse(str1);
                Date date2 = sdf.parse(str2);
                // Gọi hàm getStatisticsExpense với đối tượng Date
                float expense = ReportBusiness.getStatisticsProfitTime(date1, date2);
                System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG THỐNG KÊ  ◯◯◯" + RESET);
                borderTable(150);
                System.out.printf("%-30s %-36s %-36s %-50s %s\n", starBorder(), BLUE_BB +
                        "Từ Ngày-Tháng-Năm", "Đến Ngày-Tháng-Năm", "Tổng doanh thu(VND)" + RESET, starBorder());
                System.out.printf("%-30s %-29s %-36s %-46.3f %s\n", starBorder(), str1, str2, expense, starBorder());
                borderTable(150);
                break;
            } catch (ParseException e) {
                notifyError("Định dạng ngày không hợp lệ. Sử dụng định dạng dd-MM-yyyy.");
            }
        } while (true);
    }

    public static void statisticsImportMany() {
        notifyEnterPagination("Nhập vào ngày, tháng, năm cần Thống kê sản phẩm nhập nhiều nhất\n");
        do {
            notifyEnter("Nhập vào ngày, tháng, năm thứ 1 (VD: 01-01-2023): ");
            String str1 = scanner.nextLine();
            notifyEnter("Nhập vào ngày, tháng, năm thứ 2 (VD: 01-01-2023): ");
            String str2 = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                // Chuyển đổi chuỗi thành đối tượng java.util.Date
                Date date1 = sdf.parse(str1);
                Date date2 = sdf.parse(str2);
                // Gọi hàm getStatisticsExpense với đối tượng Date
                String name = ReportBusiness.getStatisticsImportMany(date1, date2);
                System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG THỐNG KÊ  ◯◯◯" + RESET);
                borderTable(150);
                System.out.printf("%-30s %-36s %-36s %-50s %s\n", starBorder(), BLUE_BB +
                        "Từ Ngày-Tháng-Năm", "Đến Ngày-Tháng-Năm", "Sản phẩm nhập nhiều nhất" + RESET, starBorder());
                System.out.printf("%-30s %-29s %-36s %-46s %s\n", starBorder(), str1, str2, name, starBorder());
                borderTable(150);
                break;
            } catch (ParseException e) {
                notifyError("Định dạng ngày không hợp lệ. Sử dụng định dạng dd-MM-yyyy.");
            }
        } while (true);
    }

    public static void statisticsImportLeast() {
        notifyEnterPagination("Nhập vào ngày, tháng, năm cần Thống kê sản phẩm nhập ít nhất\n");
        do {
            notifyEnter("Nhập vào ngày, tháng, năm thứ 1 (VD: 01-01-2023): ");
            String str1 = scanner.nextLine();
            notifyEnter("Nhập vào ngày, tháng, năm thứ 2 (VD: 01-01-2023): ");
            String str2 = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                // Chuyển đổi chuỗi thành đối tượng java.util.Date
                Date date1 = sdf.parse(str1);
                Date date2 = sdf.parse(str2);
                // Gọi hàm getStatisticsExpense với đối tượng Date
                String name = ReportBusiness.getStatisticsImportLeast(date1, date2);
                System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG THỐNG KÊ ◯◯◯" + RESET);
                borderTable(150);
                System.out.printf("%-30s %-36s %-36s %-50s %s\n", starBorder(), BLUE_BB +
                        "Từ Ngày-Tháng-Năm", "Đến Ngày-Tháng-Năm", "Sản phẩm nhập ít nhất" + RESET, starBorder());
                System.out.printf("%-30s %-29s %-36s %-46s %s\n", starBorder(), str1, str2, name, starBorder());
                borderTable(150);
                break;
            } catch (ParseException e) {
                notifyError("Định dạng ngày không hợp lệ. Sử dụng định dạng dd-MM-yyyy.");
            }
        } while (true);
    }

    public static void statisticsExportMany() {
        notifyEnterPagination("Nhập vào ngày, tháng, năm cần Thống kê sản phẩm xuất nhiều nhất\n");
        do {
            notifyEnter("Nhập vào ngày, tháng, năm thứ 1 (VD: 01-01-2023): ");
            String str1 = scanner.nextLine();
            notifyEnter("Nhập vào ngày, tháng, năm thứ 2 (VD: 01-01-2023): ");
            String str2 = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                // Chuyển đổi chuỗi thành đối tượng java.util.Date
                Date date1 = sdf.parse(str1);
                Date date2 = sdf.parse(str2);
                // Gọi hàm getStatisticsExpense với đối tượng Date
                String name = ReportBusiness.getStatisticsExportMany(date1, date2);
                System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG THỐNG KÊ  ◯◯◯" + RESET);
                borderTable(150);
                System.out.printf("%-30s %-36s %-36s %-50s %s\n", starBorder(), BLUE_BB +
                        "Từ Ngày-Tháng-Năm", "Đến Ngày-Tháng-Năm", "Sản phẩm xuất nhiều nhất" + RESET, starBorder());
                System.out.printf("%-30s %-29s %-36s %-46s %s\n", starBorder(), str1, str2, name, starBorder());
                borderTable(150);
                break;
            } catch (ParseException e) {
                notifyError("Định dạng ngày không hợp lệ. Sử dụng định dạng dd-MM-yyyy.");
            }
        } while (true);
    }

    public static void statisticsExportLeast() {
        notifyEnterPagination("Nhập vào ngày, tháng, năm cần Thống kê sản phẩm xuất ít nhất\n");
        do {
            notifyEnter("Nhập vào ngày, tháng, năm thứ 1 (VD: 01-01-2023): ");
            String str1 = scanner.nextLine();
            notifyEnter("Nhập vào ngày, tháng, năm thứ 2 (VD: 01-01-2023): ");
            String str2 = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                // Chuyển đổi chuỗi thành đối tượng java.util.Date
                Date date1 = sdf.parse(str1);
                Date date2 = sdf.parse(str2);
                // Gọi hàm getStatisticsExpense với đối tượng Date
                String name = ReportBusiness.getStatisticsExportLeast(date1, date2);
                System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG THỐNG KÊ  ◯◯◯" + RESET);
                borderTable(150);
                System.out.printf("%-30s %-36s %-36s %-50s %s\n", starBorder(), BLUE_BB +
                        "Từ Ngày-Tháng-Năm", "Đến Ngày-Tháng-Năm", "Sản phẩm xuất ít nhất" + RESET, starBorder());
                System.out.printf("%-30s %-29s %-36s %-46s %s\n", starBorder(), str1, str2, name, starBorder());
                borderTable(150);
                break;
            } catch (ParseException e) {
                notifyError("Định dạng ngày không hợp lệ. Sử dụng định dạng dd-MM-yyyy.");
            }
        }while (true);
    }

}
