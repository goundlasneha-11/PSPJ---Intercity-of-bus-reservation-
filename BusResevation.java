import java.util.Scanner;

public class BusResevation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Bus details
        String busName = "Express Travels";
        String from = "Hyderabad";
        String to = "Vijayawada";
        String date = "25-08-2026";

        // Seat status
        boolean[] seats = new boolean[10];

        int choice;

        // while loop - continue reservation process
        while (true) {

            System.out.println("\n===== INTERCITY BUS RESERVATION SYSTEM =====");
            System.out.println("1. Search Bus");
            System.out.println("2. Choose Route/Date");
            System.out.println("3. Book Seat");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View Seat Map");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            // switch - booking menu
            switch (choice) {

                case 1:
                    System.out.println("\n----- SEARCH BUS -----");
                    System.out.println("Bus Name : " + busName);
                    System.out.println("From     : " + from);
                    System.out.println("To       : " + to);
                    System.out.println("Date     : " + date);
                    System.out.println("Bus Found Successfully!");
                    break;

                case 2:
                    System.out.println("\n----- ROUTE DETAILS -----");
                    System.out.println("From : " + from);
                    System.out.println("To   : " + to);
                    System.out.println("Date : " + date);
                    System.out.println("Route Selected Successfully!");
                    break;

                case 3:
                    System.out.println("\n----- BOOK SEAT -----");

                    // for loop - display seat map
                    for (int i = 0; i < seats.length; i++) {
                        if (seats[i]) {
                            System.out.println("Seat " + (i + 1) + " : BOOKED");
                        } else {
                            System.out.println("Seat " + (i + 1) + " : AVAILABLE");
                        }
                    }

                    System.out.print("Enter seat number to book (1-10): ");
                    int seatNumber = sc.nextInt();

                    if (seatNumber >= 1 && seatNumber <= 10) {

                        // if-else - check seat availability
                        if (!seats[seatNumber - 1]) {
                            seats[seatNumber - 1] = true;
                            System.out.println("Seat " + seatNumber + " booked successfully!");
                        } else {
                            System.out.println("Sorry! Seat " + seatNumber + " is already booked.");
                        }

                    } else {
                        System.out.println("Invalid seat number!");
                    }

                    break;

                case 4:
                    System.out.println("\n----- CANCEL RESERVATION -----");

                    System.out.print("Enter seat number to cancel (1-10): ");
                    int cancelSeat = sc.nextInt();

                    if (cancelSeat >= 1 && cancelSeat <= 10) {

                        if (seats[cancelSeat - 1]) {
                            seats[cancelSeat - 1] = false;
                            System.out.println("Reservation cancelled successfully!");
                        } else {
                            System.out.println("This seat is not booked.");
                        }

                    } else {
                        System.out.println("Invalid seat number!");
                    }

                    break;

                case 5:
                    System.out.println("\n----- SEAT MAP -----");

                    // for loop
                    for (int i = 0; i < seats.length; i++) {

                        if (seats[i]) {
                            System.out.println("Seat " + (i + 1) + " : BOOKED");
                        } else {
                            System.out.println("Seat " + (i + 1) + " : AVAILABLE");
                        }
                    }

                    break;

                case 6:
                    System.out.println("\nThank you for using the Bus Reservation System!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}