import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.Serializable;

public class FoodQueue implements Serializable {
    private int income;//Declaring variable for calculate income

    private ArrayList<Customer> queue= new ArrayList<Customer>(); //Initializing Arraylist for cashier
    private int queueLength; //Declaring a variable for length of a cashier

    //Getter
    public int getIncome() {
        return income;
    }

    public FoodQueue(int queueLength) {
        this.queueLength = queueLength;
    }

    public void addCustomer(Customer customer) { //Method for add the customer object to the queue arraylist
        this.queue.add(customer);

    }
     public int numOfEmpty(){ //Method for count the empty slots in the queue
        return queueLength-queue.size();
    }
    public boolean isFull(){ //Method for check weather queue is full or not
        if(queue.size()>=queueLength){
            return true;
        }
        else {
            return false;
        }

    }
    public String[] forVfq(){ //Method for viewing the queues using '*' and '0' symbols
        String[] cashier=new String[queueLength];
        for (int i=0;i<queueLength;i++){
                try{
                    if (queue.get(i)!=null){
                        cashier[i]="X";
                    }
                }catch (Exception e){

                }

        }
        return cashier;
    }
    public void showCus(){ //Method for show the customer to the user
        System.out.printf("%5s | %15s | %15s | %15s\n","index", "First Name", "Last Name", "No. of burgers");
        for (int i = 0; i <queue.size(); i++) {
            System.out.printf("%5s | %15s | %15s | %15s\n",(i+1),queue.get(i).getFirstName(),queue.get(i).getLastName(),queue.get(i).getReqBurgers());

        }
    }
    public void removeCus(int index){
        queue.remove(index);
    }//Method for remove a customer from the queue
    public boolean isEmpty(){ //Method for check weather the queue is empty or not
        if(queue.size()==0){
            return true;
        }
        else {
            return false;
        }
    }
    public void calIncome(int noOfBurgers){//Method for calculate the income of the queue
        income=income+(650 * noOfBurgers);
    }
    public Customer getCus(int index){ //Method for get a specific customer from a queue
        return queue.get(index);

    }



    public void sortCashier(int index) { //Method for sort the cashier
            ArrayList<Customer> tempQueue = new ArrayList<Customer>();
            for (int j = 0; j < queue.size(); j++) {
                tempQueue.add(queue.get(j));
            }
            Collections.sort(tempQueue, Comparator.comparing(Customer::getFirstName));
            if (tempQueue.size() > 0) {
                System.out.println("-----------------------Cashier"+(index+1)+" [SORTED] -------------------------");
                System.out.printf("%15s | %15s | %15s\n", "First Name", "Last Name", "No.of burgers");
                System.out.println("-----------------------------------------------------------------");

                for (Customer customer:tempQueue) {
                    System.out.printf("%15s | %15s | %15s\n", customer.getFirstName(), customer.getLastName(), customer.getReqBurgers());

                }
                System.out.println();
            }


    }


}
//----Other used resources----
//https://www.tutorialspoint.com/3-ways-to-initialize-an-object-in-Java