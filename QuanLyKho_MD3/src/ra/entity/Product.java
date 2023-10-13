package ra.entity;


import ra.business.ProductBusiness;
import ra.notify.Notify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;

import static ra.notify.Notify.*;
import static ra.table.Table.starBorder;

public class Product {
    private String productId;
    private String productName;
    private String manufacture;
    private Date created;
    /*
     * java.sql.Date created = rs.getDate(?)
     * new Date(created.getTime())
     *
     * --
     * callSt.getDate(?,new java.sql.Date(st.getCreated().getTime()))
     * */
    private short batch;
    private int quantity;
    private boolean productStatus;

    public Product() {
    }

    public Product(String productId, String productName, String manufacture,
                   Date created, short batch, int quantity, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.manufacture = manufacture;
        this.created = created;
        this.batch = batch;
        this.quantity = quantity;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public short getBatch() {
        return batch;
    }

    public void setBatch(short batch) {
        this.batch = batch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public void inputData(Scanner scanner) {
        this.productId = productId(scanner);
        this.productName = productName(scanner);
        this.manufacture = manufacture(scanner);
        this.batch = batch(scanner);
        this.productStatus = productStatus(scanner);
    }

    public static String productId(Scanner scanner) {
        System.out.print("Nhập mã sản phẩm(5 kí tự): ");
        do {
            String productId = scanner.nextLine();
            if (productId.trim().length() != 5) {
                Notify.notifyError("Mã sản phẩm phải có 5 kí tự !");
            } else {
                String result = ProductBusiness.validateProductId(productId);
                if (result.equals("OK")) {
                    Notify.notifyOk();
                    return productId;
                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static String productName(Scanner scanner) {
        System.out.print("Nhập tên sản phẩm: ");
        do {
            String productName = scanner.nextLine();
            if (productName.trim().length() < 1 || productName.trim().length() > 100) {
                Notify.notifyError("Tên sản phẩm không được bỏ trống !");
            } else {
                String result = ProductBusiness.validateProductName(productName);
                if (result.equals("OK")) {
                    Notify.notifyOk();
                    return productName;
                } else {
                    Notify.notifyError(result);
                }
            }
        } while (true);
    }

    public static String manufacture(Scanner scanner) {
        System.out.print("Nhập nhà sản xuất: ");
        do {
            String manufacture = scanner.nextLine();
            if (manufacture.trim().length() < 1 || manufacture.trim().length() > 100) {
                Notify.notifyError("Tên nhà sản xuất không được bỏ trống !");
            } else {
                Notify.notifyOk();
                return manufacture;
            }
        } while (true);
    }

    public static short batch(Scanner scanner) {
        System.out.print("Nhập lô chứa sản phẩm: ");
        do {
            try {
                short batch = Short.parseShort(scanner.nextLine());
                if (batch > 0) {
                    Notify.notifyOk();
                    return batch;
                } else {
                    Notify.notifyError("Lô chứa sản phẩm không được bỏ trống !");
                }
            } catch (NumberFormatException nfe) {
                Notify.notifyError(nfe.getMessage());
            }
        } while (true);
    }

    public static int quantity(Scanner scanner) {
        System.out.print("Nhập số lượng sản phẩm: ");
        do {
            try {
                int quantity = Integer.parseInt(scanner.nextLine());
                if (quantity > 0) {
                    Notify.notifyOk();
                    return quantity;
                } else {
                    Notify.notifyError("Số lượng sản phẩm không được bỏ trống !");
                }
            } catch (NumberFormatException nfe) {
                Notify.notifyError(nfe.getMessage());
            }
        } while (true);
    }

    public static boolean productStatus(Scanner scanner) {
        System.out.print("Nhập trạng thái(1-HĐ / 0-KHĐ)): ");
        do {
            try {
                int num = Integer.parseInt(scanner.nextLine());
                if (num == 1) {
                    return true;
                } else if (num == 0) {
                    return false;
                } else {
                    Notify.notifyError("Vui lòng chỉ nhập (1-HĐ / 0-KHĐ) !");

                }
            } catch (NumberFormatException nfe) {
                Notify.notifyError(nfe.getMessage());
            }
        } while (true);
    }

    public void displayData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("%s %-13s %-22s %-20s %-15s %-20d %-19d %-16s %s\n", starBorder(),
                this.productId, this.productName, this.manufacture, sdf.format(this.created),
                this.batch, this.quantity, this.productStatus ? "Hoạt Động" : "Không Hoạt Động", starBorder());
    }
}
