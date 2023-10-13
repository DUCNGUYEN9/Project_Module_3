package ra.table;

import static ra.color.Color.*;

public class Table {
    public static void borderStar(int number) {
        for (int i = 0; i < number; i++) {
            System.out.print(ORANGE_BOLD + "⁕" + RESET);
        }
    }

    public static void borderHyphen(int number) {
        for (int i = 0; i < number; i++) {
            System.out.print(ORANGE_BOLD + "-" + RESET);
        }
    }

    public static void borderCircle(int number) {
        for (int i = 0; i < number; i++) {
            System.out.print(PURPLE_BB + "◯" + RESET);
        }
    }

    public static void headerMenu(String mes) {
        System.out.printf("%s %s %s\n", PURPLE_BB + "⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜", PINK_BOLD + mes +
                RESET, PURPLE_BB + "⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜");
    }

    public static void bodyMenu(String mes) {
        System.out.printf("%-7s %-60s %s\n", "⁜", CYAN_BB + mes + RESET, PURPLE_BB + "⁜");
    }

    public static void footerMenu(String mes,int number) {
        System.out.printf("%-7s %-60s %s\n", "⁜", CYAN_BB + mes + RESET, PURPLE_BB + "⁜" + RESET);
        borderCircle(number);
        System.out.println();
    }
    public static void borderTable(int number) {
        for (int i = 0; i < number; i++) {
            System.out.print(PURPLE_BB + "⊹" + RESET);
        }
        System.out.println();
    }
    public static String starBorder() {
        return PURPLE_BB + "⊹" + RESET;
    }
    /**
     * System.out.printf("%s %s %s\n",PURPLE_BB + "⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜", PINK_BOLD + "WAREHOUSE MANAGEMENT" +
     *                     RESET, PURPLE_BB + "⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜");
     *             System.out.printf("%-7s %-60s %s\n", "⁜", CYAN_BB + "1. Quản lý sản phẩm" + RESET, PURPLE_BB + "⁜");
     *             System.out.printf("%-7s %-60s %s\n", "⁜", CYAN_BB + "2. Quản lý nhân viên" + RESET, PURPLE_BB + "⁜");
     *             System.out.printf("%-7s %-60s %s\n", "⁜", CYAN_BB + "3. Quản lý tài khoản" + RESET, PURPLE_BB + "⁜");
     *             System.out.printf("%-7s %-60s %s\n", "⁜", CYAN_BB + "4. Quản lý phiếu nhập" + RESET, PURPLE_BB + "⁜");
     *             System.out.printf("%-7s %-60s %s\n", "⁜", CYAN_BB + "5. Quản lý phiếu xuất" + RESET, PURPLE_BB + "⁜");
     *             System.out.printf("%-7s %-60s %s\n", "⁜", CYAN_BB + "6. Quản lý báo cáo" + RESET, PURPLE_BB + "⁜");
     *             System.out.printf("%-7s %-60s %s\n", "⁜", CYAN_BB + "7. Thoát" + RESET, PURPLE_BB + "⁜"+ RESET);
     *             Table.borderCircle(61);
     *             System.out.println();
     */
}
