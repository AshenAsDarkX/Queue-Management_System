import java.io.Serializable;

public class Customer implements Serializable {
    private String firstName;
    private String lastName;
    private int reqBurgers;

    public Customer(String firstName, String lastName, int reqBurgers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reqBurgers = reqBurgers;
    }

    //getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getReqBurgers() {
        return reqBurgers;
    }

}
