package ra.presentation;

import ra.business.EmployeeBusiness;
import ra.entity.Employee;
import ra.entity.Product;
import ra.notify.Notify;

import static ra.color.Color.*;
import static ra.notify.Notify.*;
import static ra.table.Table.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {
    static Scanner scanner = new Scanner(System.in);
    static long totalPage;

    public static void employeeMenu() {
        boolean isExit = true;
        do {
            headerMenu("EMPLOYEE MANAGEMENT");
            bodyMenu("1. Danh sách nhân viên");
            bodyMenu("2. Thêm mới nhân viên");
            bodyMenu("3. Cập nhật thông tin nhân viên");
            bodyMenu("4. Cập nhật trạng thái nhân viên");
            bodyMenu("5. Tìm kiếm nhân viên");
            footerMenu("6. Thoát", 61);
            try {
                notifyEnter("Lựa chọn của bạn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayEmployee();
                        break;
                    case 2:
                        addNewEmployee();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        updateStatusEmployee();
                        break;
                    case 5:
                        searchEmployee();
                        break;
                    case 6:
                        isExit = false;
                        break;
                    default:
                        notifyError("Lựa chọn chỉ trong khoảng từ 1 - 6 !");
                }
            } catch (NumberFormatException nfe) {
                notifyError("Vui lòng nhập số nguyên từ 1 - 6 !");
            }
        } while (isExit);
    }

    public static void displayEmployee() {
        displayTable(1, 10);
    }

    public static void displayTable(int offSet, int pageNumber) {
        List<Employee> employeeList = EmployeeBusiness.getAllEmployee(offSet, pageNumber);
        System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG HIỂN THỊ NHÂN VIÊN ◯◯◯" + RESET);
        borderTable(150);
        System.out.printf("%s %-22s %-20s %-15s %-20s %-15s %-20s %-24s %s\n", starBorder(), BLUE_BB +
                        "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Email", "Số điện thoại",
                "Địa chỉ", "Trạng thái" + RESET, starBorder());
        employeeList.forEach(Employee::displayData);
        borderTable(150);
        paginationPage(offSet, pageNumber);
        displayPage(pageNumber);
    }

    public static void displayPage(int pageNumber) {
        notifyEnterPagination("Hãy nhập số trang bạn muốn hiển thị (0 để thoát): ");
        int pageDisplay = paginationEnterNumber(scanner);
        if (pageDisplay > 0 && pageDisplay <= totalPage) {
            displayTable(pageDisplay, pageNumber);
        } else if (pageDisplay == 0) {
        } else {
            notifyError("Vui lòng nhập số nhỏ hơn hoặc bằng " + totalPage);

        }
    }

    public static void paginationPage(int offSet, int pageNumber) {
        int totalRecord = EmployeeBusiness.getTotalRecord();
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


    public static void addNewEmployee() {
        displayEmployee();
        Employee employeeNew = new Employee();
        notifyEnter("Bạn muốn thêm mấy nhân viên: ");
        int number = paginationEnterNumber(scanner);
        for (int i = 1; i <= number; i++) {
            System.out.printf(LAVENDER_BOLD + "Thêm sản phẩm thứ %d\n" + RESET, i);
            employeeNew.inputData(scanner);
            boolean result = EmployeeBusiness.addNewEmployee(employeeNew);
            if (result) {
                notifySuccess("Thêm mới thành công");
            } else {
                notifyError("Có lỗi xảy ra trong quá trình thực hiện !");
            }
        }
    }

    public static void updateEmployee() {
        displayEmployee();
        do {
            notifyEnter("Nhập mã nhân viên cần cập nhật:");
            String employeeIdUpdate = scanner.nextLine();
            Employee employeeUpdate = EmployeeBusiness.getEmployeeById(employeeIdUpdate);
            if (employeeUpdate != null) {
                notifyConfirm("Bạn có muốn cập nhật tên nhân viên không ?");
                int Num = paginationEnterNumber(scanner);
                if (Num == 1) {
                    employeeUpdate.setEmployeeName(Employee.employeeName(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật ngày sinh không ?");
                int Num1 = paginationEnterNumber(scanner);
                if (Num1 == 1) {
                    System.out.print("Nhập ngày sinh(ngày/tháng/năm): ");
                    String str = scanner.nextLine();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        employeeUpdate.setBirthDate(sdf.parse(str));
                        Notify.notifyOk();
                    } catch (ParseException e) {
                        Notify.notifyError(e.getMessage());
                    }
                }
                notifyConfirm("Bạn có muốn cập nhật email không ?");
                int Num2 = paginationEnterNumber(scanner);
                if (Num2 == 1) {
                    employeeUpdate.setEmail(Employee.employeeEmail(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật số điện thoại  không ?");
                int Num3 = paginationEnterNumber(scanner);
                if (Num3 == 1) {
                    employeeUpdate.setPhone(Employee.employeePhone(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật tên địa chỉ không ?");
                int Num4 = paginationEnterNumber(scanner);
                if (Num4 == 1) {
                    employeeUpdate.setAddress(Employee.employeeAddress(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật trạng thái không ?");
                int Num5 = paginationEnterNumber(scanner);
                if (Num5 == 1) {
                    employeeUpdate.setEmployeeStatus(Employee.employeeStatus(scanner));
                }
                //Thực hiện cập nhật
                boolean result = EmployeeBusiness.updateEmployee(employeeUpdate);
                if (result) {
                    notifySuccess("Cập nhật thành công");
                    break;
                } else {
                    notifyError("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lai !");
                }

            } else {
                //Mã sach không tồn tại trong CSDL
                notifyError("Mã (" + employeeIdUpdate + ") không tồn tại !");
            }
        } while (true);
    }

    public static void updateStatusEmployee() {
        displayEmployee();
        notifyEnter("Nhập mã nhân viên cần cập nhật trạng thái:");
        String employeeIdUpdate = scanner.nextLine();
        Employee employeeUpdate = EmployeeBusiness.getEmployeeById(employeeIdUpdate);
        if (employeeUpdate != null) {
            employeeUpdate.setEmployeeStatus(Employee.employeeStatus(scanner));
            //Thực hiện cập nhật
            boolean result = EmployeeBusiness.updateEmployee(employeeUpdate);
            if (result) {
                notifySuccess("Cập nhật thành công");
            } else {
                notifyError("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lai !");
            }
        } else {
            //Mã sach không tồn tại trong CSDL
            notifyError("Mã (" + employeeIdUpdate + ") không tồn tại !");
        }
    }

    public static void searchEmployee() {
        boolean exit = true;
        do {
            notifyEnter("Hãy chọn số bạn muốn tìm kiếm (1-Mã NV 2-Tên NV): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        searchIdEmp();
                        exit = false;
                        break;
                    case 2:
                        searchName();
                        exit = false;
                        break;
                    default:
                        notifyError("Vui lòng nhập 1 hoặc 2 !");
                }

            } catch (NumberFormatException nfe) {
                notifyError("Vui lòng nhập số nguyên 1 hoặc 2 !");
            }
        } while (exit);
    }

    public static void searchName() {
        notifyEnter("Nhập vào tên nhân viên cần tìm: ");
        String NameSearch = scanner.nextLine();
        Employee checkNull = EmployeeBusiness.checkEmployeeByName(NameSearch);
        if (checkNull != null) {
            List<Employee> employeeList = EmployeeBusiness.searchNameEmployee(NameSearch);
            System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ KẾT QUẢ TÌM KIẾM THEO TÊN ◯◯◯" + RESET);
            borderTable(150);
            System.out.printf("%s %-22s %-20s %-15s %-20s %-15s %-20s %-24s %s\n", starBorder(), BLUE_BB +
                            "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Email", "Số điện thoại",
                    "Địa chỉ", "Trạng thái" + RESET, starBorder());
            employeeList.forEach(Employee::displayData);
            borderTable(150);
        } else {
            notifyError("Không có nhân viên " + NameSearch);
        }
    }

    public static void searchIdEmp() {
        notifyEnter("Nhập vào mã nhân viên cần tìm: ");
        String NameSearch = scanner.nextLine();
        Employee checkNull = EmployeeBusiness.checkEmployeeById(NameSearch);
        if (checkNull != null) {
            List<Employee> employeeList = EmployeeBusiness.searchIdEmployee(NameSearch);
            System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ KẾT QUẢ TÌM KIẾM THEO MÃ NHÂN VIÊN ◯◯◯" + RESET);
            borderTable(150);
            System.out.printf("%s %-22s %-20s %-15s %-20s %-15s %-20s %-24s %s\n", starBorder(), BLUE_BB +
                            "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Email", "Số điện thoại",
                    "Địa chỉ", "Trạng thái" + RESET, starBorder());
            employeeList.forEach(Employee::displayData);
            borderTable(150);
        } else {
            notifyError("Không mã nhân viên " + NameSearch);
        }
    }
}
