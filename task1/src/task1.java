import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class task1 {
    static int burgers = 50;

    public static void main(String[] args) {
        String[][] cashiers = new String[3][];
        //Initializing arrays for each cashier
        cashiers[0] = new String[2];
        cashiers[1] = new String[3];
        cashiers[2] = new String[5];
        
        while (true) {
            if (10>=burgers){
                System.out.println("**** WARNING! LOW ON BURGER STOCK ****"); //Warning message when the burger stock reaches below 10
            }
            System.out.println("\n");
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
                        999 or EXT: Exit the Program
                    """);
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your command : ");
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
                    RCQ(cashiers);
                    break;
                case  "104":
                case  "PCQ":
                    PCQ(cashiers);
                    break;
                case  "105":
                case  "VCS":
                    VCS(cashiers);
                    break;

                case "106" :
                case "SPD" :
                    SPD(cashiers);
                    System.out.println("data added");
                    break;
                case "107" :
                case "LPD":
                    LPD(cashiers);
                    break;

                case "108" :
                case "STK" :
                    System.out.println(burgers+" Burgers remaining!");
                    break;

                case "109":
                case "AFS":
                    try {
                        System.out.print("Enter the burger amount : ");
                        int addBurgers = sc.nextInt();
                        if (burgers >= 50) {
                            System.out.println("Stock is full");
                        } else if ((burgers+addBurgers)>50) {
                            System.out.println("The total burger stock cannot exceed 50.");
                        } else {
                            burgers = addBurgers + burgers;
                            System.out.println("You have added " + addBurgers + " burgers to the stock...");  //Adding burgers to stock
                        }
                    } catch(Exception e) {
                        System.out.println("Please enter a number!  ");
                    }break;


                case "999":
                case "EXT":
                    System.out.println("Are you sure you want to terminate the program !(Y/N)");  //Exiting the program
                    String terminate = sc.nextLine();
                    if (terminate.equalsIgnoreCase("Y")) {
                        System.out.println("Now the program will be terminated!");
                        System.exit(0);

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

    static void VFQ(String[][] cashiers) {  //View all queues
        System.out.println("""
                ***********
                * CASHIER *
                ***********""");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    if (cashiers[j][i] == (null)) {
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

    public static void VEQ(String[][] cashiers) {  //View all empty queues
        for (int i = 0; i < 3; i++) {
            System.out.println("      Cashier" + (i + 1) + "- " + countEmptyQueues(cashiers[i]) + " " + "slots empty");
        }
    }
    public static void ACQ(String[][] cashiers){  //Add customer to a queue
        Scanner sc=new Scanner(System.in);
        try {
            System.out.print("Please choose a cashier : ");
            int cashier = sc.nextInt();
            String[] selectedCashier=cashiers[cashier-1];
            if (countEmptyQueues(selectedCashier)==0){
                System.out.println("Cashier is already full!");
            }
            else{
                System.out.print("Enter the name : ");
                String name=sc.next();
                selectedCashier[selectedCashier.length-countEmptyQueues(selectedCashier)]=name;
            }
        }catch (Exception e){     //Handling errors
            System.out.println("Please enter a valid input!");
        }
    }


    public static void RCQ(String[][] cashiers) {  //Remove a customer from queue
        try {
            Scanner sc=new Scanner(System.in);
            System.out.print("Please choose a cashier : ");
            int cashier = sc.nextInt();
            System.out.println("Customers in this cashier:");
            for(int i=0;i<cashiers[cashier-1].length;i++){
                System.out.println("    "+(i+1)+cashiers[cashier-1][i]);
            }
            System.out.print("Enter the customer you want to remove :");
            int slot= sc.nextInt();
            removeCustomer(cashiers[cashier-1],slot-1);
            System.out.println("Customer removed!");
        }catch(Exception e){     //Handling errors
            System.out.println("Please enter a valid input!");
        }


    }
    private static void removeCustomer(String[] cashier, int index){  //Method for removing customer

        try{
            int numOfNull = countEmptyQueues(cashier)+1;
            for (int i = index; i < (cashier.length - countEmptyQueues(cashier)); i++) {
                //System.out.println(String.join(",",cashier));

                if (i+1 < cashier.length){
                    cashier[i] = cashier[i + 1];
                }
                if (i+numOfNull==cashier.length){
                    cashier[i]=null;
                }

            }
        }catch (Exception e){     //Handling errors
            System.out.println("Please enter a valid input!");
        }
    }
    public static void PCQ(String[][] cashiers){  //Remove served customer
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Please choose a cashier :");
            int cashier=sc.nextInt();
            if(countEmptyQueues(cashiers[cashier-1])==cashiers[cashier-1].length){
                System.out.println("Cashier is already empty!");

            }
            else{
                removeCustomer(cashiers[cashier-1],0 );
                burgers-=5;
            }
        }catch (Exception e){     //Handling errors
            System.out.println("Please enter a valid input!");
        }
    }
    public static int countEmptyQueues(String[] cashier) {  //Method for count all empty queue
            int count = 0;
            for (int i = 0; i < cashier.length; i++) {
                if (cashier[i] == null) {

                    count++;
                }
            }return count;

    }


    public static void VCS(String[][] cashiers){  //View customers in alphabatical order
        System.out.print("Cashier 1:");
        System.out.println(String.join(",",sortArray(cashiers[0])));
        System.out.print("Cashier 2:");
        System.out.println(String.join(",",sortArray(cashiers[1])));
        System.out.print("Cashier 3:");
        System.out.println(String.join(",",sortArray(cashiers[2])));


    }
    private static String[] sortArray(String[] cashiers) {   //https://www.softwaretestinghelp.com/sort-arrays-in-java/
        int loopLength = cashiers.length-countEmptyQueues(cashiers);   //Sort customers in alphabatical order
        String[] sortedPump = Arrays.copyOf(cashiers,cashiers.length);
        if (loopLength>1) {
            for (int i = 0; i < loopLength - 1; i++) {
                for (int j = i + 1; j < loopLength; j++) {
                    if (sortedPump[i].compareTo(sortedPump[j]) > 0) {
                        String temp = sortedPump[i];
                        sortedPump[i] = sortedPump[j];
                        sortedPump[j] = temp;
                    }
                }
            }
        }
        return sortedPump;
    }

    public static void SPD(String[][] cashiers)  //Add data into file
    {
        try{
            FileWriter wr = new FileWriter("Details.txt");
            wr.write( burgers+"\n");
            for (String[] cashier :
                    cashiers) {
                wr.write(String.join(",",cashier)+"\n");
            }
            wr.close();
        }catch(IOException e){     //Handling errors
            e.printStackTrace();
        }
    }
    public static void LPD(String[][] cashiers){ //Load data from file
        try{
            Path path=Path.of("Details.txt");
            ArrayList<String> content = new ArrayList<>(Files.readAllLines(path));
            burgers=Integer.parseInt(content.get(0));
            for (int i = 1; i < 4; i++) {
                String[] cashierData = content.get(i).split(",");
                String[] fixedCashierData = new String[cashierData.length];
                for (int j = 0; j < cashierData.length; j++) {
                    if (!cashierData[j].equals("null")){
                        fixedCashierData[j] = cashierData[j];
                    }

                }
                System.arraycopy(fixedCashierData,0,cashiers[i-1],0,cashiers[i-1].length);
            }
        } catch (IOException e) {    //Handling errors
            throw new RuntimeException(e);
        }
    }
}

//https://www.candidjava.com/collections/sort-arraylist-in-java-without-using-sort-method/
//https://interviewsansar.com/how-to-print-arraylist-elements-in-java/(printing arraylist)
//https://blog.gitnux.com/code/java-exit-program/#:~:text=To%20exit%20a%20program%20in,error%20or%20an%20exceptional%20condition.&text=In%20this%20example%2C%20after%20printing,the%20program%20will%20exit%20immediately.
