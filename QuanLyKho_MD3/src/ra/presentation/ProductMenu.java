package ra.presentation;

import ra.business.EmployeeBusiness;
import ra.business.ProductBusiness;
import ra.color.Color;
import ra.entity.Employee;
import ra.entity.Product;
import ra.notify.Notify;

import static ra.color.Color.*;
import static ra.color.Color.RESET;
import static ra.table.Table.*;
import static ra.notify.Notify.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;


public class ProductMenu {
    static Scanner scanner = new Scanner(System.in);
    static long totalPage;

    public static void productMenu() {
        boolean isExit = true;
        do {
            headerMenu(" PRODUCT MANAGEMENT");
            bodyMenu("1. Danh sách sản phẩm");
            bodyMenu("2. Thêm mới sản phẩm");
            bodyMenu("3. Cập nhật sản phẩm");
            bodyMenu("4. Tìm kiếm sản phẩm");
            bodyMenu("5. Cập nhật trạng thái sản phẩm");
            footerMenu("6. Thoát", 61);
            try {
                notifyEnter("Lựa chọn của bạn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayProduct();
                        break;
                    case 2:
                        addNewProduct();
                        break;
                    case 3:
                        updateProduct();
                        break;
                    case 4:
                        searchNameProduct();
                        break;
                    case 5:
                        updateStatusProduct();
                        break;
                    case 6:
                        isExit = false;
                        break;
                    default:
                        notifyError("Lựa chọn chỉ trong khoảng từ 1 - 6 !");
                }
            } catch (NumberFormatException nfe) {
                notifyError(nfe.getMessage());
            }
        } while (isExit);
    }

    public static void displayProduct() {
        displayTable(1, 10);
    }

    public static void displayTable(int offSet, int pageNumber) {
        List<Product> productList = ProductBusiness.getAllProduct(offSet, pageNumber);
        System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ BẢNG HIỂN THỊ SẢN PHẨM ◯◯◯" + RESET);
        borderTable(150);
        System.out.printf("%s %-20s %-22s %-20s %-15s %-20s %-19s %-20s %s\n", starBorder(), BLUE_BB +
                        "Mã sản phẩm", "Tên sản phẩm", "Nhà sản xuất", "Ngày tạo",
                "Lô chứa sản phẩm", "Số lượng sản phẩm", "Trạng thái" + RESET, starBorder());
        productList.forEach(Product::displayData);
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
        int totalRecord = ProductBusiness.getTotalRecord();
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

    public static void addNewProduct() {
        displayProduct();
        Product productNew = new Product();
        notifyEnter("Bạn muốn thêm mấy sản phẩm: ");
        int number = paginationEnterNumber(scanner);
        for (int i = 1; i <= number; i++) {
            System.out.printf(LAVENDER_BOLD +"Thêm sản phẩm thứ %d\n"+RESET,i );
            productNew.inputData(scanner);
            boolean result = ProductBusiness.addNewProduct(productNew);
            if (result) {
                notifySuccess("Thêm mới thành công");
            } else {
                notifyError("Có lỗi xảy ra trong quá trình thực hiện !");
            }
        }
    }

    public static void updateProduct() {
        displayProduct();
        do {
            notifyEnter("Nhập mã sản phẩm cần cập nhật:");
            String productIdUpdate = scanner.nextLine();
            Product productUpdate = ProductBusiness.getProductById(productIdUpdate);
            if (productUpdate != null) {
                notifyConfirm("Bạn có muốn cập nhật tên sản phẩm không ?");
                int Num = paginationEnterNumber(scanner);
                if (Num == 1) {
                    productUpdate.setProductName(Product.productName(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật nhà sản xuất không ?");
                int Num1 = paginationEnterNumber(scanner);
                if (Num1 == 1) {
                    productUpdate.setManufacture(Product.manufacture(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật lô sản phẩm không ?");
                int Num2 = paginationEnterNumber(scanner);
                if (Num2 == 1) {
                    productUpdate.setBatch(Product.batch(scanner));
                }
                notifyConfirm("Bạn có muốn cập nhật trạng thái không ?");
                int Num3 = paginationEnterNumber(scanner);
                if (Num3 == 1) {
                    productUpdate.setProductStatus(Product.productStatus(scanner));
                }
                boolean result = ProductBusiness.updateProduct(productUpdate);
                if (result) {
                    notifySuccess("Cập nhật thành công");
                    break;
                } else {
                    notifyError("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lai !");
                }
            } else {
                notifyError("Mã (" + productIdUpdate + ") không tồn tại, Vui lòng nhập lại !");
            }
        } while (true);
    }

    public static void searchNameProduct() {
        notifyEnter("Nhập vào tên sản phẩm cần tìm: ");
        String NameSearch = scanner.nextLine();
        Product checkNull = ProductBusiness.checkProductByName(NameSearch);
        if (checkNull != null) {
            List<Product> productList = ProductBusiness.searchNameProduct(NameSearch);
            System.out.printf("%-50s %s\n", "", CYAN_BB + "◯◯◯ KẾT QUẢ TÌM KIẾM THEO TÊN ◯◯◯" + RESET);
            borderTable(150);
            System.out.printf("%s %-20s %-22s %-20s %-15s %-20s %-19s %-20s %s\n", starBorder(), BLUE_BB +
                            "Mã sản phẩm", "Tên sản phẩm", "Nhà sản xuất", "Ngày tạo",
                    "Lô chứa sản phẩm", "Số lượng sản phẩm", "Trạng thái" + RESET, starBorder());
            productList.forEach(Product::displayData);
            borderTable(150);
        } else {
            notifyError("Không có sản phẩm " + NameSearch);
        }
    }

    public static void updateStatusProduct() {
        displayProduct();
        notifyEnter("Nhập mã sản phẩm cần cập nhật trạng thái:");
        String productIdUpdate = scanner.nextLine();
        Product productUpdate = ProductBusiness.getProductById(productIdUpdate);
        if (productUpdate != null) {
            productUpdate.setProductStatus(Product.productStatus(scanner));
            //Thực hiện cập nhật
            boolean result = ProductBusiness.updateProduct(productUpdate);
            if (result) {
                notifySuccess("Cập nhật thành công");
            } else {
                notifyError("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lai !");
            }
        } else {
            //Mã sach không tồn tại trong CSDL
            notifyError("Mã (" + productIdUpdate + ") không tồn tại !");
        }
    }
}
