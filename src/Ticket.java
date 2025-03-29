// import statements
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String row;
    private int seat;
    private int price;
    private Person person;

    //constructor
    public Ticket(String row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }
    // All setters and getters
    public void setRow(String row) {
        this.row = row;
    }

    public String getRow() {
        return row.toUpperCase();
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getSeat() {
        return seat;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    // method for display ticket and passenger information
    public String printTicket() {
        return("Ticket information ," +
                "\n\t\tRow letter  : " + getRow() +
                "\n\t\tseat number : " + getSeat() +
                "\n\t\tPrice       : $" + getPrice() +
                getPerson().printPerson());

    }
    // method for file handling
    public void save(boolean filedel) {
        try {
            File file = new File(getRow() + getSeat() + ".txt");

            if (file.createNewFile()) {
                // if file created display as created and write details in the file
                System.out.println( file.getName() + " file created.");
                FileWriter writer = new FileWriter(file.getName());
                writer.write(printTicket());
                writer.close();
            }
            else if (file.exists()) {
                // if file already exists display as file exists and check file should delete or not
                System.out.println(file.getName() + " file is already exists.");
                if (filedel) {
                    // delete the file
                    System.out.println(file.getName() + " file deleted.");
                    file.delete();
                }
                else {
                    // file already exists and rewrite file details.
                    System.out.println(file.getName() + " file updated with new details.");
                    FileWriter writer = new FileWriter(file.getName());
                    writer.write(printTicket());
                    writer.close();
                }

            }
            else {
                System.out.println("Error while creating the " + file.getName() + " file.");
            }
        }catch (IOException IOE) {
            System.out.println("ERROR in file.");
        }
    }
}
