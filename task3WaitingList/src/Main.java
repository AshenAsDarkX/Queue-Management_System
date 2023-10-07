import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {

        Cashiers cashiers = new Cashiers();//Initializing  cashier object

        while (true) { //this while loops until user exits the program!
            if (10 >= cashiers.getBurgers()) {
                System.out.println("**** WARNING! LOW ON BURGER STOCK ****"); //Warning message when the burger stock reaches below 10
            }
            System.out.println("\n");// Repeating the main menu for be user-friendly
            System.out.println("""
                    Welcome to Foodies Fave Food center
                        100 or VFQ: View all Queues
                        101 or VEQ: View all Empty Queues
                        102 or ACQ: Add customer to a Queue
                        103 or RCQ: Remove a customer from a Queue
                        104 or PCQ: Remove a served customer.
                        105 or VCS: View Customers Sorted in alphabetical order
                        106 or SPD: Store Program Data into file
                        107 or LPD: Load Program Data from file
                        108 or STK: View Remaining burgers Stock
                        109 or AFS: Add burgers to Stock
                        110 or IFQ: View income of each cashier
                        999 or EXT: Exit the Program
                    """);
            Scanner sc = new Scanner(System.in);//Scanner to get inputs from user
            System.out.print("What do you want to do? Enter your command : "); //Getting user inputs and check weather inputs are correct
            String command = sc.nextLine();
            switch ((command.toUpperCase())) {
                case "100":
                case "VFQ":
                    VFQ(cashiers);
                    break;
                case "101":
                case "VEQ":

                    VEQ(cashiers);

                    break;
                case "102":
                case "ACQ":
                    ACQ(cashiers);
                    break;

                case "103":
                case "RCQ":
                    cashiers.removeCustomer();
                    break;

                case "104":
                case "PCQ":
                    cashiers.serveCus();
                    break;
                case "105":
                case "VCS":
                    cashiers.sortCashiers();
                    break;

                case "106":
                case "SPD":
                    saveToFile(cashiers);
                    cashiers.saveBurgersToFile();

                    break;
                case "107":
                case "LPD":
                    getfileForWaiting();
                    ArrayList<Customer> retrievedWaiting = getfileForWaiting();
                    if (retrievedWaiting!= null){
                        cashiers.setWaitingList(retrievedWaiting);
                    }
                    getFileForCashiers(); //https://youtu.be/pBS_I88JMXw(reading and writing java objects to a file)
                    ArrayList<FoodQueue> retrievedCashiers = getFileForCashiers();
                    if(retrievedCashiers != null){
                        cashiers.setCashiers(retrievedCashiers);
                        System.out.println("Data restored successfully!");
                    }
                    cashiers.loadBurgersFromFile();
                    break;

                case "108":
                case "STK":
                    System.out.println(cashiers.getBurgers() + " Burgers remaining!"); //Showing the remaining burger count to the user
                    break;

                case "109":
                case "AFS":
                    try {
                        System.out.print("Enter the burger amount : ");
                        int addBurgers = sc.nextInt();
                        if (cashiers.getBurgers() >= 50) {
                            System.out.println("Stock is full");
                        } else if ((cashiers.getBurgers() + addBurgers) > 50) {
                            System.out.println("The total burger stock cannot exceed 50.");
                        } else {
                            cashiers.addBurgers(addBurgers);

                            System.out.println("You have added " + addBurgers + " burgers to the stock...");  //Adding burgers to stock
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a number!  ");
                    }
                    break;
                case "110":
                case "IFQ":
                    cashiers.showIncome();
                    break;

                case "999":
                case "EXT":
                    System.out.println("Are you sure you want to terminate the program !(Y/N)");  //Exiting the program
                    String terminate = sc.nextLine();
                    if (terminate.equalsIgnoreCase("Y")) {
                        System.out.println("Now the program will be terminated!");
                        System.exit(0);
                        //https://blog.gitnux.com/code/java-exit-program/#:~:text=To%20exit%20a%20program%20in,error%20or%20an%20exceptional%20condition.&text=In%20this%20example%2C%20after%20printing,the%20program%20will%20exit%20immediately.
                    } else if (terminate.equalsIgnoreCase("N")) {
                        break;
                    } else {
                        System.out.println("please enter a valid input!");

                    }
                default:
                    System.out.println("Please enter a valid input!");
                    break;
            }


        }
    }
    static void VFQ(Cashiers cashiers) {  //View all cashiers
        System.out.println("""
                ***********
                * CASHIER *
                ***********""");
        ArrayList<String[]> allCas = cashiers.initializeVFQ();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    if (allCas.get(j)[i] == (null)) {
                        System.out.print("X" + "    ");
                    } else {
                        System.out.print("0" + "    ");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.print("     ");
                }

            }
            System.out.println("\n");
        }
        System.out.println("0=Occupied   X=Not Occupied");
        System.out.println("---------------------------");
    }

    public static void VEQ(Cashiers cashiers) {  //View all empty cashiers
        cashiers.viewEmpty();
    }

    public static void ACQ(Cashiers cashiers) {  //Add Customer to a queue
        Scanner sc = new Scanner(System.in);
        String firstName;
        String lastName;
        int reqBurgers;
        while (true) {
            System.out.print("Enter customer first name : ");
            firstName = sc.nextLine();
            if (firstName.length() != 0) {
                break;
            } else {
                System.out.println("WARNING!! First name cannot be empty\n");
            }
        }
        while (true) {
            System.out.print("Enter customer last name : ");
            lastName = sc.nextLine();
            if (lastName.length() != 0) {
                break;
            } else {
                System.out.println("WARNING!! Last name cannot be empty\n");
            }
        }
        while (true) {
            try {
                System.out.print("Enter the amount of burger required : ");
                reqBurgers = sc.nextInt();
                if (reqBurgers != 0) {
                    break;
                } else {
                    System.out.println("WARNING!! Burger count cannot be zero\n");
                }

            } catch (Exception e) {
                System.out.println("Burger count should be a number!");
                sc.nextLine();

            }

        }
//        System.out.println(firstName + lastName);
//        System.out.println(reqBurgers);(for debugging purpose)
        Customer customer = new Customer(firstName, lastName, reqBurgers);
        cashiers.addCustomer(customer);
    }
    public static void saveToFile(Cashiers cashiers) {//Modified method for store data into a file(https://www.geeksforgeeks.org/fileoutputstream-in-java/)
        String fileNameC = "cashiers.txt"; // Specify the filename or path
        String fileNameW ="waiting.txt";
        try {
            FileOutputStream fOutCashiers = new FileOutputStream(fileNameC);
            ObjectOutputStream oOutCashiers =new ObjectOutputStream(fOutCashiers);
            oOutCashiers.writeObject(cashiers.getCashiers());
            oOutCashiers.close();
            fOutCashiers.close();

            FileOutputStream fOutWaiting = new FileOutputStream(fileNameW);
            ObjectOutputStream oOutWaiting =new ObjectOutputStream(fOutWaiting);
            oOutWaiting.writeObject(cashiers.getWaitingList());
            oOutWaiting.close();
            fOutWaiting.close();
            System.out.println("Data has been saved!");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static ArrayList<FoodQueue> getFileForCashiers() {//Modified method for get data from a file(https://www.geeksforgeeks.org/fileoutputstream-in-java/)
        ArrayList<FoodQueue> cashiers=null;
        String fileName = "cashiers.txt";
        try{
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            cashiers = (ArrayList<FoodQueue>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return cashiers;
    }
    public static ArrayList<Customer> getfileForWaiting() {//Modified method for get data from a file(https://www.geeksforgeeks.org/fileoutputstream-in-java/)
        ArrayList<Customer> waitingList=null;
        String fileName = "waiting.txt";
        try{
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            waitingList = (ArrayList<Customer>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return waitingList;
    }
}
//https://www.candidjava.com/collections/sort-arraylist-in-java-without-using-sort-method/
//https://interviewsansar.com/how-to-print-arraylist-elements-in-java/(printing arraylist)
