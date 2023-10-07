import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Cashiers implements Serializable {//Serialized the object for store the data
    private int burgers = 50;//Declaring the burger count

    private ArrayList<Customer> waitingList=new ArrayList<Customer>(); //Creating an arraylist for the waiting-list
    //Creating an Arraylist to store Food-queue objects
    private ArrayList<FoodQueue> cashiers= new ArrayList<FoodQueue>(Arrays.asList(new FoodQueue(2),new FoodQueue(3),new FoodQueue(5)));


    //getters
    public int getBurgers() {//Getter for burgers
        return burgers;
    }
    public ArrayList<Customer> getWaitingList() {
        return waitingList;
    }
    public ArrayList<FoodQueue> getCashiers() {
        return cashiers;
    }//getter for cashiers used for store in file


    //Setters
    public void setWaitingList(ArrayList<Customer> waitingList) {
        this.waitingList = waitingList;
    }
    public void setCashiers(ArrayList<FoodQueue> cashiers){
        this.cashiers =cashiers;
    }//setter for cashier used for restore the data from file

    //Methods
    public void addCustomer(Customer customer){//Method for adding a customer
        if(!(cashiers.get(0).isFull() && cashiers.get(1).isFull() && cashiers.get(2).isFull())){
            int maxEmpty=-1;
            int index=0;
            for(int i=this.cashiers.size()-1;i>=0;i--){
                if(cashiers.get(i).numOfEmpty()>=maxEmpty){
                    maxEmpty=cashiers.get(i).numOfEmpty();
                    index=i;
                }
            }
            if(!cashiers.get(index).isFull()){
                System.out.println("Customer is in cashier"+(index+1));
                cashiers.get(index).addCustomer(customer);
            }
            else{
                System.out.println("Warning! all queues are full.");
            }

        }
        else{
            waitingList.add(customer);
            System.out.println("Customer is in waiting list");
        }

    }
    public ArrayList<String[]> initializeVFQ(){//method for viewing queue
        ArrayList<String[]> allCas = new ArrayList<>();
        for (FoodQueue cashier :
                cashiers) {
            allCas.add(cashier.forVfq());
        }
        return allCas;

    }
    public void viewEmpty(){//method for view empty slots
        for (int i = 0; i < cashiers.size(); i++) {
            if (!cashiers.get(i).isFull()){
                System.out.println("Cashier "+(i+1)+" has "+cashiers.get(i).numOfEmpty()+" empty slots");
            }
            else{
                System.out.println("No empty queues");
            }

        }
    }
    public void removeCustomer(){//Method for remove a customer from specific location
        Scanner sc=new Scanner(System.in);
        System.out.print("What is customer's cashier? ");
        try{
            int cashier = sc.nextInt();
            if (!cashiers.get(cashier-1).isEmpty()){
                cashiers.get(cashier-1).showCus();
                try{
                    System.out.print("Enter the index of your customer :");
                    int index=sc.nextInt();
                    cashiers.get(cashier-1).removeCus(index-1);
                    System.out.println("Customer removed!");
                    if (!waitingList.isEmpty()){
                        Customer newCustomer=waitingList.get(0);
                        waitingList.remove(0);
                        cashiers.get(cashier-1).addCustomer(newCustomer);
                        System.out.println("New customer added from the waiting list");
                    }

                }catch (Exception e){
                    System.out.print("Please enter valid customer index!");
                }
            }else {
                System.out.println("Selected cashier is empty");
            }


        }catch (Exception e){
            System.out.println("please enter a valid number!");
        }
    }
    public void serveCus(){//Same method used as above but removes the first index
        Scanner sc=new Scanner(System.in);
        System.out.print("What is customer's cashier? ");
        try{
            int cashier = sc.nextInt();
            if (!cashiers.get(cashier-1).isEmpty()){
                int numOfBurgers=cashiers.get(cashier-1).getCus(0).getReqBurgers();
                cashiers.get(cashier-1).calIncome(numOfBurgers);
                burgers=burgers-numOfBurgers;
                System.out.println(cashiers.get(cashier-1).getCus(0).getFirstName()+" is served with "+cashiers.get(cashier-1).getCus(0).getReqBurgers()+" burgers!");
                cashiers.get(cashier-1).removeCus(0);
                if (!waitingList.isEmpty()){
                    Customer newCustomer=waitingList.get(0);
                    waitingList.remove(0);
                    cashiers.get(cashier-1).addCustomer(newCustomer);
                    System.out.println("New customer added from the waiting list!");
                }
            }else {
                System.out.println("No customer to serve");
            }

        }catch(Exception e){
            System.out.println("please enter a valid number!");
        }
    }

    public void addBurgers(int burgers) {//Method for add burgers
        this.burgers = burgers+this.burgers ;
    }


    public void sortCashiers(){// method for sort all cashiers
        for (int i = 0; i < cashiers.size(); i++) {
            cashiers.get(i).sortCashier(i);
        }
    }

    public void showIncome(){//Method for show the income of each cashier
        for (int i = 0; i < cashiers.size(); i++) {
                int income=cashiers.get(i).getIncome();

               System.out.println("Cashier "+(i+1)+"income:"+"Rs." +income );

       }
    }
    public void saveBurgersToFile() {//method for save burger count for saving and retrieving data
        String fileName="burgers.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(Integer.toString(burgers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBurgersFromFile() {//method for get burger count for saving and retrieving data
        String fileName="burgers.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            burgers = Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}







