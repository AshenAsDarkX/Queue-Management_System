import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Cashiers implements Serializable { //Serialized the object for store the data
    private int burgers = 50; //Declaring the burger count
    //Creating an Arraylist to store Food-queue objects
    private  ArrayList<FoodQueue> cashiers= new ArrayList<>(Arrays.asList(new FoodQueue(2), new FoodQueue(3), new FoodQueue(5)));

    //Getters
    public ArrayList<FoodQueue> getCashiers() {
        return cashiers;
    }//getter for cashiers used for store in file
    public int getBurgers() {//getter for burgers
        return burgers;
    } //Getter for burgers

    //Setters
    public void setCashiers(ArrayList<FoodQueue> cashiers){
        this.cashiers =cashiers;
    }//setter for cashier used for restore the data from file

    //Methods
    public void addCustomer(Customer customer){ //Method for adding a customer
        int maxEmpty=-1;
        int index=0;
        for(int i=this.cashiers.size()-1;i>=0;i--){
            if(cashiers.get(i).numOfEmpty()>=maxEmpty){
                maxEmpty=cashiers.get(i).numOfEmpty();
                index=i;
            }
        }
        if(!cashiers.get(index).isFull()){
            System.out.println("Customer is in cashier "+(index+1));
            cashiers.get(index).addCustomer(customer);
        }
        else{
            System.out.println("Warning! all queues are full.");
        }
    }
    public ArrayList<String[]> initializeVFQ(){ //method for viewing queue
        ArrayList<String[]> allCas = new ArrayList<>();
        for (FoodQueue cashier :
                cashiers) {
            allCas.add(cashier.forVfq());
        }
        return allCas;

    }
    public void viewEmpty(){ //method for view empty slots
        for (int i = 0; i < cashiers.size(); i++) {
            if (!cashiers.get(i).isFull()){
                System.out.println("Cashier "+(i+1)+" has "+cashiers.get(i).numOfEmpty()+" empty slots");
            }


            }
        if(cashiers.get(0).isFull() && cashiers.get(1).isFull() && cashiers.get(2).isFull()){
            System.out.println("All cashiers are full!!" + "");
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
                cashiers.get(cashier-1).removeCus(0);
                System.out.println("Customer served!");


            }else {
            System.out.println("Selected cashier is empty");
        }

        }catch(Exception e){
            System.out.println("please enter a valid number!");
        }
    }
    public void addBurgers(int burgers) {//Method for add burgers
        this.burgers = burgers+this.burgers ;
    }//Method for add burgers


  public void sortCashiers(){// method for sort all cashiers
      for (int i = 0; i < cashiers.size(); i++) {
          cashiers.get(i).sortCashier(i);
      }
  }
    public void showIncome(){//Method for show the income of each cashier
        for (int i = 0; i < cashiers.size(); i++) {
            int income=cashiers.get(i).getIncome();

            System.out.println("Cashier "+(i+1)+"income:"+"Rs."+income );

        }
    }
    public void saveBurgersToFile() { //method for save burger count for saving and retrieving data
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







