package Heap;

/**
 * A class that implements a Customer for a discrete event simulation
 * @author Jonathan Chen
 * @date March 2nd, 2020
 */
public class Customer
{
    // Customer's name
    private String name;
    private int arrivalTime; // The time the customer gets in line
    private int serviceTime; // How long it takes to service the customer

    /** Create a new Customer
     * @param name Customer's name
     * @param arrival Customer's arrival time
     * @param service Time to service the customer
     */
    public Customer( String name, int arrival, int service )
    {
        this.name = name;
        arrivalTime = arrival;
        serviceTime = service;
    }

    /** Get Customer's name */
    public String getName()
    {
        return name;
    }

    /** Get arrival time */
    public int getArrivalTime()
    {
        return arrivalTime;
    }

    /** Get service time */
    public int getServiceTime()
    {
        return serviceTime;
    }
}