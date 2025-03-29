//import statements
import java.util.InputMismatchException;
import java.util.Scanner;

public class PlaneManagement {
    //Declare and initialize arrays as global variables
    private static String[][] plane = {
            {" X ", " 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10", "11", "12", "13", "14"},
            {" A ", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0"},
            {" B ", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0"},
            {" C ", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0"},
            {" D ", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0", " 0"}
    };
    private static int[][] prices = {
            {200,200,200,200,200,150,150,150,150,180,180,180,180,180},
            {200,200,200,200,200,150,150,150,150,180,180,180},
            {200,200,200,200,200,150,150,150,150,180,180,180},
            {200,200,200,200,200,150,150,150,150,180,180,180,180,180}
    };
    private static Ticket[][] tickets_array = {
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null}
    };

    public static void main(String[] args) {
        //main method
        boolean again = true;

        System.out.println("\n\tWelcome to the Plane Management application");

        do {
            try {
                int option = menu();

                switch (option) {

                    case 1:
                        //to buy a seat
                        buy_seat();
                        break;

                    case 2:
                        // to cancel a seat
                        cancel_seat();
                        break;

                    case 3:
                        //Find first available seat
                        find_first_available();
                        break;

                    case 4:
                        //Show seating plan
                        show_setting_plan();
                        break;

                    case 5:
                        //print tickets information and total sales
                        print_ticket_info();
                        break;

                    case 6:
                        //search ticket
                        search_ticket();
                        break;

                    case 0:
                        //Quit
                        System.out.println("Thank you for using the Plane Manager. Goodbye!");
                        again = false;
                        break;

                    default:
                        System.out.println("Enter only an option number (1-6) ");
                        System.out.println("------------------------------------------------------------");
                }
            }
            catch (InputMismatchException IME) {
                System.out.println("Invalid input!......");
                System.out.println("------------------------------------------------------------");
            }
            catch (ArrayIndexOutOfBoundsException AE) {
                System.out.println("Invalid Seat number.");
                System.out.println("------------------------------------------------------------");
            }
        } while (again);

    }
    public static int menu() {
        // method for print manu
        Scanner input = new Scanner(System.in);
        System.out.println("\n*****************************************************\n*\t\t\t\t\tMenu Options\t\t\t\t\t*\n*****************************************************");
        System.out.println("\t\t1) Buy a seat");
        System.out.println("\t\t2) Cancel a seat");
        System.out.println("\t\t3) Find first available seat");
        System.out.println("\t\t4) Show seating plane");
        System.out.println("\t\t5) Print tickets information and total sales");
        System.out.println("\t\t6) Search ticket");
        System.out.println("\t\t0) Quit");
        System.out.println("*****************************************************");
        System.out.print("\nPlease select an option : "); // select an option
        return input.nextInt();
    }
    public static void buy_seat() {
        // method for buy a seat
        Scanner input = new Scanner(System.in);
        int row;
        System.out.print("Enter a Row letter : ");
        String letter = input.next();

        switch (letter.toUpperCase()) {

            case "A":
                row = 1;
                break;

            case "B" :
                row = 2;
                break;

            case "C" :
                row = 3;
                break;

            case "D":
                row = 4;
                break;

            default:
                System.out.println("Enter only valid Row letter.");
                throw new InputMismatchException(); // throw an exception - invalid row letter
        }

        System.out.print("Enter a seat number : ");
        int seat = input.nextInt();

        if (plane[row][seat].equals(" 0")) {
            plane[row][seat] = " X";
            String[] tempPerson = personInput();
            Ticket ticket = new Ticket(letter, seat, prices[row-1][seat-1], new Person(tempPerson[0],tempPerson[1],tempPerson[2])); // create a ticket object + create person object in this ticket object
            tickets_array[row-1][seat-1] = ticket; // add ticket object to the array called ticket_array
            System.out.println("\nRow " + letter.toUpperCase() +", Seat " + seat + " - booked Successfully.");
            ticket.save(false); // create a file + use false for create it
        }
        else {
            System.out.println("seat is not Available.");
        }
    }

    public static void cancel_seat() {
        //method for cancel a seat
        Scanner input = new Scanner(System.in);
        int row ;
        System.out.print("Enter a Row letter : ");
        String letter = input.next();

        switch (letter.toUpperCase()) {

            case "A":
                row = 1;
                break;

            case "B":
                row = 2;
                break;

            case "C":
                row = 3;
                break;

            case "D":
                row = 4;
                break;

            default:
                System.out.println("Enter only valid Row letter.");
                throw new InputMismatchException(); // throw an exception - invalid row letter
        }

        System.out.print("Enter a seat number : ");
        int seat = input.nextInt();

        if (plane[row][seat].equals(" X")) {
            plane[row][seat] = " 0";
            System.out.println("\nRow " + letter.toUpperCase() +", Seat " + seat + " - Successfully canceled");
            tickets_array[row-1][seat-1].save(true); // delete the text file + use true for delete.
            tickets_array[row-1][seat-1] = null;
        }
        else {
            System.out.println("seat is not booked yet.");
        }
    }
    public static void find_first_available() {
        // method for find the first available seat
        int seat_found = 0;
        for (int row = 0; (row < tickets_array.length) && (seat_found !=1) ; row++) {
            for (int seat = 0; (seat < tickets_array[row].length) && (seat_found != 1); seat++) {
                if (tickets_array[row][seat] == null) {
                    seat_found++;
                    System.out.println("Row letter  : " +  (char)(65 + row)); // 65 + 0 = A , 64 + 1 = B so on.
                    System.out.println("seat number : " + (seat+1));

                }
            }
        }
        if (seat_found == 0 ) {
            System.out.println("All seats are booked.");
        }
        System.out.println("\n-----------------------------------------------------------------------");
    }
    public static void show_setting_plan() {
        // method for Display seat plan
        System.out.print("\nSeating plan : ");
        for (String[] row : plane) {
            System.out.println("\n");
            for (String seat : row) {
                System.out.print(seat + "   ");
            }
        }
        System.out.println("\n\n------------------------------------------------------------------------------");
    }

    public static String[] personInput() {
        // method for read person details
        String[] info = new String[3];
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your first name : ");
        info[0] = input.next();
        System.out.print("Enter your surname : ");
        info[1]= input.next();

        do {
            System.out.print("Enter your Email address (must include : @gmail.com) : ");
            info[2] = input.next();
        }while (!(info[2].contains("@gmail.com"))); // check the email is valid (only except Google mails - gmail.com)

        return info;
    }
    public static void print_ticket_info() {
        // method for Display ticket and passenger information
        int total = 0;
        System.out.println("\nAll information about booked tickets ,");
        for (Ticket[] row : tickets_array ) {
            for (Ticket ticket : row) {
                if (ticket != null) {
                    //ticket.printTicket();
                    System.out.println(ticket.printTicket());
                    System.out.println("-------------------------------------------------");
                    total+= ticket.getPrice();

                }
            }
        }
        System.out.println("\nTotal sales - $" + total);
    }
    public static void search_ticket() {
        // method for search a ticket and find seat details
        Scanner input = new Scanner(System.in);
        int row;
        System.out.print("Enter Row letter : ");
        String letter = input.next();

        switch (letter.toUpperCase()) {

            case "A":
                row = 0;
                break;

            case "B":
                row = 1;
                break;

            case "C":
                row = 2;
                break;

            case "D":
                row = 3;
                break;

            default:
                System.out.println("Enter only valid Row letter.");
                throw new InputMismatchException(); // throw an exception - invalid row letter

        }
        System.out.print("Enter seat number : ");
        int seat = input.nextInt();

        if (tickets_array[row][seat-1] != null ) {
            System.out.println("\nSeat is already booked.");
            System.out.println(tickets_array[row][seat-1].printTicket());
        }
        else {
            System.out.println("\nThis seat is available. You can book this seat.");
        }
        System.out.println("-------------------------------------------------");
    }
}