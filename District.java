/**
 * @program Basic Java
 * @author Gabriel Hofer
 * @class CSC-461
 * @title Programming Languages
 * @section M01
 * @date 09/26/2019
 * @description
 * You will be implementing an API for a number of parking lots in a district. You will be writing
 * three classes: ParkingLot to track the number of vehicles in an individual lot, PayParkingLot is
 * a type of ParkingLot but also tracks how much money is gathered, and District to track the
 * lots in the parking district. This MUST be done in IntelliJ with Java 11.
 * Since coding to meet a specific API is common in the workforce, in order to reuse other software
 * or hardware, you are being provided with a testing class, that must work without changes other
 * than the package name. You can add attributes and private methods, but you must implement the
 * methods (with the specified parameters) and you must stay consistent with the below
 * requirements.
 *
 * The key operations are
 *
 * markVehicleEntry – called when a vehicle enters a lot, and the requires the number of minutes
 * since the lot opened, and returns an integer ID to identify the car
 *
 * markVehicleExit – called when a vehicle exits a lot, and requires the number of minutes since
 * the lot opened, and the ID of the car that left the lot
 *
 * @language Java 11
 * @bugs no known bugs
 */
import java.util.ArrayList;
/**
 * @description
 *District.java purpose is the manage the individual ParkingLots. If any function needs more
 * than one parking lot, it is best done in District.
 * • You must be able to handle a variable number of parking lots using ONE ArrayList of parking lots
 * • The methods markVehicleEntry and markVehicleExit are in District as well, and take
 * an additional parameter in with is parking lot index of which the vehicle is entering or exiting.
 * • The method isClosed for District returns true if and only if all the parking lots are closed at
 * that time.
 * • The method closedMinutes returns the number of minutes that all of the parking lots are
 * closed at the same time. This information would be used to determine if more parking lots are
 * needed.
 * • Add a method add that takes in a Parking Lot, and returns its index.
 * • Add a method getLot which returns the ParkingLot at the given index. You may assume a
 * valid index.
 * • Add a method getVehiclesParkedInDistrict the returns the total number of parked cars
 * in the district.
 * • Add a method getTotalMoneyCollected that returns the total number money collected
 * (rounded to the nearest cent) in the district.
 * You will need to use something similar to if( lot instanceof PayParkingLot) for
 * this to work properly, since your regular and paid parking lots are in the same ArrayList. There is
 * an OOP pattern that can avoid instanceof (which is considered bad practice), but for one
 * task, this is far simpler.
 * • Add a method call isClosed that returns true is ALL parking lots are closed
 * • The value returned by toString must be a string with newline characters embedded in it. For
 * example, toString could return a string such as
 * Status for purple parking lot: 8 vehicles (CLOSED)
 * Status for gold parking lot: 20 vehicles (33.3%)
 */
public class District {
    private ArrayList<ParkingLot> parkingLots;
    private int parkingLotID;
    private int timeClosedDistrict;
    private int totalTimeClosedDistrict;
    private int prevMinutesDistrict;
    private boolean allLotsClosed;
    private int vehiclesInDistrict;
    /**
     * @description adds a parking lot to the District object
     * @param pl - parking lot object that we want to add to the district
     * @return returns the ID of the parking lot that was added
     */
    public int add(ParkingLot pl){
        parkingLots.add(pl);
        return parkingLotID++;
    }
    /**
     * @description constructor for District class
     * @return District Object
     */
    public District(){
        parkingLots=new ArrayList<ParkingLot>();
        parkingLotID=0;
        totalTimeClosedDistrict=0;
    }
    /**
     * @description iterates through all parking lots and checks whether one is open
     * @return returns true if all parking lots are closed and false otherwise
     */
    public boolean isClosed(){
        allLotsClosed=true;
        for(ParkingLot pl:parkingLots){
            if (!pl.isClosed())
                return false;}
        return true;
    }
    /**
     * @description gets the number of minutes that all parking lots have been closed
     * @return returns value of totalTimeClosedDistrict
     */
    public int getClosedMinutes(){
        return totalTimeClosedDistrict;
    }
    /**
     * @description gets the ParkingLot object with the ID passed as an argument
     * @param plID (parking lot ID): ID number for the parking lot we want
     * @return returns ParkingLot object
     */
    public ParkingLot getLot(int plID) {
        return parkingLots.get(plID);
    }
    /**
     * @description iterates through all parking lots and sums vehicles in each lot
     * @return number of vehicles in the district
     */
    public int getVehiclesParkedInDistrict() {
        vehiclesInDistrict=0;
        for(ParkingLot lot:parkingLots)
            vehiclesInDistrict+=lot.getVehiclesInLot();
        return vehiclesInDistrict;
    }
    /**
     * @description iterates through all parking lots and sums money in each lot
     * @return total money collected in district
     */
    public double getTotalMoneyCollected() {
        double money=0;
        for(ParkingLot pl:parkingLots) {
            if (pl instanceof PayParkingLot) {
                money += ((PayParkingLot) pl).getProfit();
            }
        }
        return money;
    }
    /**
     * @description insert vehicles into parking lots in a district at specific time
     * @param lotID - ID number of lot that the car is entering
     * @param minute - time when vehicles enters lot
     * @return
     */
    public int markVehicleEntry(int lotID, int minute) {
        if(minute<prevMinutesDistrict) return -1;
        prevMinutesDistrict=minute;
        if(!isClosed()) timeClosedDistrict=minute;
        parkingLotID++;
        if(isClosed()){
            totalTimeClosedDistrict+=(minute-timeClosedDistrict);
            timeClosedDistrict=minute;
        }
        return parkingLots.get(lotID).markVehicleEntry(minute);
    }
    /**
     * @description removes vehicles from parking lots in district at specific time
     * @param lotID - ID of parking lot
     * @param minute - minute that car is exiting from lot
     * @param id - id of car
     * @return -1 if something went wrong/unsuccessful, otherwise some other value
     */
    public int markVehicleExit(int lotID, int minute, int id) {
        if(minute<prevMinutesDistrict) return -1;
        prevMinutesDistrict=minute;
        if(isClosed()){
            totalTimeClosedDistrict+=(minute-timeClosedDistrict);
            timeClosedDistrict=minute;
        }
        return parkingLots.get(lotID).markVehicleExit(minute,id);
    }
    /**
     * @description prints a summary of the district - number of vehicles in each lot
     * and percentage full
     * @return string
     */
    public String toString(){
        String str="District status:\n";
        for(ParkingLot pl:parkingLots)
            str+=pl.toString()+"\n";
        return str;
    }
}