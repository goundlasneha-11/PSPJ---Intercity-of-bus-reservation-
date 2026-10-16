import java.io.IOException;
import java.nio.file.*;
import java.util.*;

// CO4: interface
interface ReservationActions {
    boolean bookSeat(int seat, String name);
    boolean cancelSeat(int seat);
}

// CO4: parent class
class Trip {
    String route;
    String date;

    Trip(String route, String date) {
        this.route = route;
        this.date = date;
    }

    void display() {
        System.out.println(route + " on " + date);
    }
}

// CO4: inheritance + overriding
class BusTrip extends Trip implements ReservationActions {
    boolean[] booked = new boolean[11];                    // CO3: 1D array (seats 1-10)
    HashMap<Integer, String> passengers = new HashMap<>(); // CO6: seat -> passenger

    BusTrip(String route, String date) {
        super(route, date);
    }

    @Override
    void display() {
        System.out.println(route + " on " + date + " | Free seats: " + countFree(1));
    }

    // CO3: recursion
    int countFree(int seat) {
        if (seat > 10) return 0;
        return (booked[seat] ? 0 : 1) + countFree(seat + 1);
    }

    void showSeats() {
        for (int i = 1; i <= 10; i++) {                    // CO2: for loop
            System.out.print(booked[i] ? "[XX] " : "[" + (i < 10 ? "0" : "") + i + "] ");
            if (i % 5 == 0) System.out.println();
        }
    }

    public boolean bookSeat(int seat, String name) {
        if (seat < 1 || seat > 10) {
            System.out.println("Invalid seat.");
            return false;
        }
        if (booked[seat]) {                                // CO2: if-else
            System.out.println("Seat already booked!");
            return false;
        }
        booked[seat] = true;
        passengers.put(seat, name);
        return true;
    }

    public boolean cancelSeat(int seat) {
        if (seat < 1 || seat > 10 || !booked[seat]) {
            System.out.println("No booking found for that seat.");
            return false;
        }
        booked[seat] = false;
        passengers.remove(seat);
        return true;
    }
}

public class IntercityBusReservation {
    static Path file = Paths.get("bookings.txt");          // CO5: Path

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);               // CO1: Scanner

        ArrayList<BusTrip> trips = new ArrayList<>();      // CO6: ArrayList + generics
        trips.add(new BusTrip("Hyderabad - Bengaluru", "20-10-2026"));
        trips.add(new BusTrip("Hyderabad - Chennai", "21-10-2026"));
        trips.add(new BusTrip("Vijayawada - Hyderabad", "22-10-2026"));

        loadBookings(trips);

        int choice = 0;
        while (choice != 5) {                              // CO2: while loop
            System.out.println("\n--- BUS RESERVATION ---");
            System.out.println("1. Search buses");
            System.out.println("2. View seats");
            System.out.println("3. Book seat");
            System.out.println("4. Cancel seat");
            System.out.println("5. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {                              // CO2: switch
                case 1:
                    for (int i = 0; i < trips.size(); i++) {
                        System.out.print((i + 1) + ". ");
                        trips.get(i).display();
                    }
                    break;
                case 2: {
                    BusTrip t = pickTrip(sc, trips);
                    t.showSeats();
                    break;
                }
                case 3: {
                    BusTrip t = pickTrip(sc, trips);
                    t.showSeats();
                    System.out.print("Seat number: ");
                    int seat = sc.nextInt();
                    System.out.print("Passenger name: ");
                    String name = sc.next();
                    if (t.bookSeat(seat, name)) {
                        System.out.println("Seat " + seat + " booked for " + name);
                        save("BOOK," + trips.indexOf(t) + "," + seat + "," + name);
                    }
                    break;
                }
                case 4: {
                    BusTrip t = pickTrip(sc, trips);
                    System.out.print("Seat number to cancel: ");
                    int seat = sc.nextInt();
                    if (t.cancelSeat(seat)) {
                        System.out.println("Seat " + seat + " cancelled.");
                        save("CANCEL," + trips.indexOf(t) + "," + seat + ",-");
                    }
                    break;
                }
                case 5:
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static BusTrip pickTrip(Scanner sc, ArrayList<BusTrip> trips) {
        System.out.print("Trip number (1-" + trips.size() + "): ");
        int n = sc.nextInt();
        if (n < 1 || n > trips.size()) n = 1;              // default to first trip if invalid
        return trips.get(n - 1);
    }

    // CO5: append a record to the file
    static void save(String line) {
        try {
            Files.writeString(file, line + "\n",
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    // CO5: read records back when the program starts
    static void loadBookings(ArrayList<BusTrip> trips) {
        if (!Files.exists(file)) return;
        try {
            for (String line : Files.readString(file).split("\n")) {
                String[] p = line.split(",");
                if (p.length < 4) continue;
                BusTrip t = trips.get(Integer.parseInt(p[1]));
                int seat = Integer.parseInt(p[2]);
                if (p[0].equals("BOOK")) t.bookSeat(seat, p[3]);
                else t.cancelSeat(seat);
            }
        } catch (IOException e) {
            System.out.println("Could not read saved bookings.");
        }
    }
}
