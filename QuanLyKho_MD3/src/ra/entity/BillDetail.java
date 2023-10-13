package ra.entity;

import ra.business.ReceiptBusiness;
import ra.notify.Notify;
import ra.presentation.ProductMenu;

import java.util.Scanner;

import static ra.table.Table.starBorder;

public class BillDetail {
    private long billDetailId;
    private long billId;
    private String productId;
    private int quantity;
    private float price;

    public BillDetail() {
    }

    public BillDetail(long billDetailId, long billId, String productId, int quantity, float price) {
        this.billDetailId = billDetailId;
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void inputData(Scanner scanner) {
        this.billId = billId(scanner);
        this.productId = productId(scanner);
        this.quantity = quantity(scanner);
        this.price = price(scanner);
    }

    public void inputDataBill(Scanner scanner) {
        this.billId = billIdDetail(scanner);
        this.productId = productId(scanner);
        //validate
        this.quantity = quantityDetail(scanner);
        this.price = price(scanner);
    }

    public static long billId(Scanner scanner) {
        System.out.print("Nhập mã phiếu: ");
        do {
            long billId = Long.parseLong(scanner.nextLine());
            if (billId > 0) {
                String result = ReceiptBusiness.validateReceiptStatus(billId);
                // validate exists
                String result1 = ReceiptBusiness.validateReceiptIdExists(billId);
                if (result.equals("OK") && result1.equals("OK")) {
                    Notify.notifyOk();
                    return billId;
                } else if (result1.equals("no exists")) {
                    Notify.notifyError("Mã phiếu bạn nhập không tồn tại, Vui lòng chọn phiếu khác !");

                } else {
                    Notify.notifyError(result);
                }
            } else {
                Notify.notifyError("Mã code không được bỏ trống và nhiều hơn 5 kí tự !");
            }
        } while (true);
    }

    public static long billIdDetail(Scanner scanner) {
        System.out.print("Nhập mã phiếu: ");
        do {
            long billId = Long.parseLong(scanner.nextLine());
            if (billId > 0) {
                String result = ReceiptBusiness.validateBillStatus(billId);
                // validate exists
                String result1 = ReceiptBusiness.validateBillIdExists(billId);
                if (result.equals("OK") && result1.equals("OK")) {
                    Notify.notifyOk();
                    return billId;
                } else if (result1.equals("no exists")) {
                    Notify.notifyError("Mã phiếu bạn nhập không tồn tại, Vui lòng chọn phiếu khác !");

                } else {
                    Notify.notifyError(result);
                }
            } else {
                Notify.notifyError("Mã code không được bỏ trống và nhiều hơn 5 kí tự !");
            }
        } while (true);
    }

    static String getProductIdQuantity = null;

    public static String productId(Scanner scanner) {
        ProductMenu.displayProduct();
        System.out.print("Nhập mã sản phẩm: ");
        do {
            String productId = scanner.nextLine();
            if (productId.trim().length() < 1 || productId.trim().length() > 5) {
                Notify.notifyError("Mã sản phẩm không được bỏ trống và nhiều hơn 5 kí tự !");
            } else {
                String result = ReceiptBusiness.validateProductStatus(productId);
                String result1 = ReceiptBusiness.validateProductIdExists(productId);
                if (result.equals("OK") && result1.equals("OK")) {
                    Notify.notifyOk();
                    getProductIdQuantity = productId;
                    return productId;
                } else if (result1.equals("no exists")) {
                    Notify.notifyError("Mã sản phẩm bạn nhập không tồn tại, Vui lòng nhập mã khác !");

                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static int quantityDetail(Scanner scanner) {
        System.out.print("Nhập số lượng: ");
        do {
            try {
                int price = Integer.parseInt(scanner.nextLine());
                if (price < 1) {
                    Notify.notifyError("số lượng không được bỏ trống !");
                } else {
                    String result = ReceiptBusiness.validateQuantity(price, getProductIdQuantity);
                    if (result.equals("OK")) {
                        Notify.notifyOk();
                        return price;
                    } else {
                        Notify.notifyError(result);
                    }
                }
            } catch (NumberFormatException n) {
                Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");
            }
        } while (true);
    }

    public static int quantity(Scanner scanner) {
        System.out.print("Nhập số lượng: ");
        do {
            try {
                int price = Integer.parseInt(scanner.nextLine());
                if (price < 1) {
                    Notify.notifyError("số lượng không được bỏ trống !");
                } else {
                    Notify.notifyOk();
                    return price;
                }
            } catch (NumberFormatException n) {
                Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");
            }
        } while (true);
    }

    public static float price(Scanner scanner) {
        System.out.print("Nhập giá: ");
        do {
            try {
                float price = Float.parseFloat(scanner.nextLine());
                if (price < 1) {
                    Notify.notifyError("Giá không được bỏ trống !");
                } else {
                    Notify.notifyOk();
                    return price;
                }
            } catch (NumberFormatException n) {
                Notify.notifyError("Lỗi xử lý dữ liệu vui lòng liên hệ hệ thống !");

            }
        } while (true);
    }

    public void displayData() {
        String confirm = ReceiptBusiness.validateBillDetailApproved(this.billId);
        System.out.printf("%s %-28d %-20d %-24s %-20d %-18.3f %-16s %s\n", starBorder(),
                this.billDetailId, this.billId, this.productId, this.quantity, this.price, confirm, starBorder());
    }
}
