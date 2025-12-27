package flipline.ui;

import java.util.List;

public class MenuPrinter {

    public static void printMenu(String title, String subtitle, List<String> options) {
        int width = calculateWidth(title, subtitle, options);

        printTop(width);
        printCentered(title, width);
        printCentered(subtitle, width);
        printSeparator(width);

        for (String option : options) {
            printLeft(option, width);
        }

        printBottom(width);
    }

    private static int calculateWidth(String title, String subtitle, List<String> options) {
        int max = Math.max(title.length(), subtitle.length());
        for (String option : options) {
            max = Math.max(max, option.length());
        }
        return max + 4; // padding
    }

    private static void printTop(int width) {
        System.out.println("╔" + "═".repeat(width) + "╗");
    }

    private static void printBottom(int width) {
        System.out.println("╚" + "═".repeat(width) + "╝");
    }

    private static void printSeparator(int width) {
        System.out.println("╠" + "═".repeat(width) + "╣");
    }

    private static void printCentered(String text, int width) {
        int padding = width - text.length();
        int left = padding / 2;
        int right = padding - left;
        System.out.println("║" + " ".repeat(left) + text + " ".repeat(right) + "║");
    }

    private static void printLeft(String text, int width) {
        int padding = width - text.length();
        System.out.println("║ " + text + " ".repeat(padding - 1) + "║");
    }
}
