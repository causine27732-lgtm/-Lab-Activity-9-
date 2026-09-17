import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentGateway gateway = new PaymentGateway();

        // Sample payments
        gateway.add(new GCashPayment(1001, "Ana", 1500.00, "0917-555-0134"));
        gateway.add(new MayaPayment(1002, "Jerome", 899.50, "jerome@liceo.edu.ph"));
        gateway.add(new CashPayment(1003, "Liza", 250.00));

        while (true) {
            System.out.println();
            System.out.println("================================");
            System.out.println("          LICEO PAY");
            System.out.println("================================");
            System.out.println("1. Make Payment");
            System.out.println("2. Show All Receipts");
            System.out.println("3. Find Payment");
            System.out.println("4. Show Total Collected");
            System.out.println("5. Refund All Refundable");
            System.out.println("6. Show Service Fees");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (choice == 0) {
                System.out.println("Thank you for using LICEO PAY.");
                break;
            }

            switch (choice) {

                case 1:
                    System.out.println();
                    System.out.println("1. GCash");
                    System.out.println("2. Maya");
                    System.out.println("3. Cash");
                    System.out.print("Choose payment method: ");

                    int method;

                    try {
                        method = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                        break;
                    }

                    System.out.print("Enter ID: ");
                    int id;

                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid ID.");
                        break;
                    }

                    System.out.print("Enter payer name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter amount: ");
                    double amount;

                    try {
                        amount = Double.parseDouble(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid amount.");
                        break;
                    }

                    Payment payment = null;

                    if (method == 1) {
                        System.out.print("Enter mobile number: ");
                        String mobile = scanner.nextLine();

                        payment = new GCashPayment(id, name, amount, mobile);

                    } else if (method == 2) {
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();

                        payment = new MayaPayment(id, name, amount, email);

                    } else if (method == 3) {
                        payment = new CashPayment(id, name, amount);

                    } else {
                        System.out.println("Invalid payment method.");
                        break;
                    }

                    gateway.add(payment);

                    System.out.println();
                    payment.printReceipt();
                    payment.printThankYou();
                    break;

                case 2:
                    System.out.println();
                    System.out.println("All Payment Receipts:");
                    gateway.processAll();
                    break;

                case 3:
                    System.out.print("Enter payment ID: ");

                    try {
                        int searchId = Integer.parseInt(scanner.nextLine());
                        Payment found = gateway.findById(searchId);

                        if (found != null) {
                            System.out.println("Payment found:");
                            found.printReceipt();
                            found.printThankYou();
                        } else {
                            System.out.println("Payment not found.");
                        }

                    } catch (Exception e) {
                        System.out.println("Invalid ID.");
                    }

                    break;

                case 4:
                    System.out.printf("Total collected: PHP %.2f%n",
                            gateway.totalCollected());
                    break;

                case 5:
                    System.out.println("Refunding every payment that can be refunded:");
                    gateway.refundAll();
                    break;

                case 6:
                    System.out.println("Service fees (the two serviceFee methods):");
                    gateway.showServiceFees();
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}