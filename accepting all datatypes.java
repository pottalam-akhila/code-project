import java.util.InputMismatchException;
import java.util.Scanner;
public class JavaTest {
    public static int getIntInput(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("ERROR! Please enter a valid integer.");
                sc.next();
            }
        }
    }
    public static Number getNumberInput(Scanner sc, String prompt, String dataType) {
        while (true) {
            try {
                System.out.print(prompt);
                switch (dataType.toLowerCase()) {
                    case "byte":
                        return sc.nextByte();
                    case "short":
                        return sc.nextShort();
                    case "int":
                        return sc.nextInt();
                    case "long":
                        return sc.nextLong();
                    case "float":
                        return sc.nextFloat();
                    case "double":
                        return sc.nextDouble();
                    default:
                        throw new IllegalArgumentException("Unsupported data type");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR! Please enter a valid " + dataType + ".");
                sc.next();
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select data type:");
        System.out.println("1. byte\n2.short3.int\n4.long\n5.float\n6.double\n");
        int dataTypeChoice;
        while (true) {
            dataTypeChoice = getIntInput(sc, "Enter choice (1-6): ");
            if (dataTypeChoice >= 1 && dataTypeChoice <= 6) {
                break;
            } else {
                System.out.println("Invalid choice! Please select from 1 to 6.");
            }
        }
        String dataType = switch (dataTypeChoice) {
            case 1 -> "byte";
            case 2 -> "short";
            case 3 -> "int";
            case 4 -> "long";
            case 5 -> "float";
            case 6 -> "double";
            default -> "int";
        };
        System.out.println("\nSelect operation:");
        System.out.println("1. add\n2.sub\n3.mul\n4.div\n");
        int choice;
        while (true) {
            choice = getIntInput(sc, "Enter operation choice (1-4): ");
            if (choice >= 1 && choice <= 4) {
                break;
            } else {
                System.out.println("Invalid choice! Please select from 1 to 4.");
            }
        }
        Number num1 = getNumberInput(sc, "Enter number 1: ", dataType);
        Number num2;
        while (true) {
            num2 = getNumberInput(sc, "Enter number 2: ", dataType);
            if (choice == 4) {
                if (isZero(num2)) {
                    System.out.println("ERROR! Division by zero is not allowed. Please enter a non-zero number.");
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        System.out.print("Result: ");
        if (dataType.equals("float") || dataType.equals("double")) {
            // Perform floating-point operations
            double dnum1 = num1.doubleValue();
            double dnum2 = num2.doubleValue();
            switch (choice) {
                case 1 -> System.out.println(dnum1 + dnum2);
                case 2 -> System.out.println(dnum1 - dnum2);
                case 3 -> System.out.println(dnum1 * dnum2);
                case 4 -> System.out.println(dnum1 / dnum2);
            }
        } else {
            long lnum1 = num1.longValue();
            long lnum2 = num2.longValue();
            switch (choice) {
                case 1 -> System.out.println(lnum1 + lnum2);
                case 2 -> System.out.println(lnum1 - lnum2);
                case 3 -> System.out.println(lnum1 * lnum2);
                case 4 -> System.out.println(lnum1 / lnum2);
            }
        }
        sc.close();
    }
    private static boolean isZero(Number num) {
        if (num instanceof Float || num instanceof Double) {
            return num.doubleValue() == 0.0;
        } else {
            return num.longValue() == 0L;
        }
    }
}
