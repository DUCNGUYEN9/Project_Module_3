package ra.notify;

import ra.color.Color;

import java.util.Scanner;

public class Notify {
    /**
     * notify a error message
     * @param message enter a message
     */
    public static void notifyError(String message) {
        System.out.println();
        System.out.println(Color.RED_BOLD + message + Color.RESET);
        System.out.println();
    }

    /**
     * notify a message success
     * @param message get message
     */
    public static void notifySuccess(String message) {
        System.out.println(Color.GREEN_BB + message + " ✓" + Color.RESET);
    }

    /**
     * notify ok
     */
    public static void notifyOk() {
        System.out.println(Color.GREEN_BB + "OK ✓" + Color.RESET);
    }

    /**
     * notify enter info
     * @param message get message
     */
    public static void notifyEnter(String message) {
        System.out.print(Color.WHITE_BB + message + Color.RESET);
    }
    /**
     *
     * @param message enter a message
     */
    public static void notifyEnterPagination(String message) {
        System.out.print(Color.ORANGE_BOLD + message + Color.RESET);
    }
    /**
     * warning notify
     * @param message enter a message
     */
    public static void notifyWarning(String message) {
        System.out.println(Color.YELLOW_BB + message + " ⚠️" + Color.RESET);
    }

    /**
     * confirm a notify
     * @param message enter a message
     */
    public static void notifyConfirm(String message) {
        System.out.println(Color.BLUE_BB
                + message + Color.RESET);
        System.out.printf("\t%-20s %s\n", Color.GREEN_BACKGROUND + " 1.Có " + Color.RESET,
                Color.RED_BACKGROUND + " 2.Không " + Color.RESET);

    }

    /**
     *
     * @param scanner enter a number pagination
     * @return number
     */
    public static int paginationEnterNumber(Scanner scanner) {
        int number;
        do {
            try {
                number = Integer.parseInt(scanner.nextLine());
                return number;
            } catch (NumberFormatException n) {
                notifyError("Vui lòng nhập số nguyên dương !");
            }

        } while (true);
    }
}
