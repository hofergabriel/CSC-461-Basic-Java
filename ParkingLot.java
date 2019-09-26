package hofer_gabriel;
/**
 * @author Gabriel Hofer
 * @description
 *It is expected that time never goes backwards in this class. If markVehicleEntry or
 * markVehicleExit is called with a time that is before some other call, assume the sensor
 * glitched and ignore the event, and return -1 to signify a failure. Otherwise update the current
 * time to the given time.
 * • Parking lots have names. To simplify testing, ParkingLot defaults the name to "test" when
 * no color is specified.
 * • The method getVehiclesInLot returns the number of vehicles in the lot at any one time.
 * • The method isClosed returns whether or not a lot is closed. It will return true is the number of
 * vehicles is at 80% or above of the number of spaces in the lot. When it reaches this point, an
 * electronic sign goes on saying the lot is closed so drivers do not waste fuel circling the lot to find
 * one of the few remaining spaces. Drivers can ignore this sign and continue to enter, up to the
 * capacity. After capacity is reached, markVehicleEntry should return -1 to signify a failure
 * • Create a class constant CLOSED_THRESHOLD for the 80% threshold. Use this constant in your
 * code so that it would be easy to support changes in the policy.
 * • The method getCosedMinutes returns the number of minutes during which the lot is closed
 * (as defined above). You can assume this method will never need to update the closed minutes
 * while the lot is closed; that is, enough vehicles will have exited that the closed sign has turned
 * off, reopening the lot to new drivers. This is a simplification, otherwise this function would need
 * the current time.
 * • Add a toString method. This method is to return a string of the form "Status for [name]
 * parking lot: [x] vehicles ([p])" where [name] is filled in by the name, [x] by the number of
 * vehicles currently in the lot, and [p] by the percentage of the lot that is occupied. The
 * percentage may have up to 1 decimal place shown, only if needed. If the percentage is at or
 * above the threshold, display "CLOSED" for the percentage inside.
 * Example:
 * Status for pink parking lot: 17 vehicles (68.2%)
 * Status for blue parking lot: 28 vehicles (CLOSED)
 * Status for gray parking lot: 2 vehicles (20%)
 */
import java.util.ArrayList;
import java.util.Hashtable;
import java.text.DecimalFormat;
public class ParkingLot {
    private String parkingLotName;
    private int vehicleID;
    private int spacesInLot;
    private ArrayList<Integer> minutesEntered;
    private int timeClosed; // last time that the lot was closed
    private int totalTimeClosed; // total duration of time that lot was closed
    public static final int CLOSED_THRESHOLD=80;
    private int prevMinutes;
    private int vehicleCount;
    private int lastPayTime;
    private Hashtable<Integer,Integer> paidCarID;
    /**
     * @description returns the duration of time that the lot has been closed
     * @return returns the duration of time that the lot has been closed
     */
    public int getClosedMinutes() { return totalTimeClosed; }
    /**
     * @description returns name of parking lot
     * @return return name of parking lot
     */
    public String getName(){ return this.parkingLotName; }
    /**
     * @description returns number of vehicles in the lot
     * @return returns number of vehicles in the lot
     */
    public int getVehiclesInLot(){ return vehicleCount; }
    /**
     * @description returns ArrayList object containing the minute at which
     * each car entered the lot.
     * @return an ArrayList containing the minute that each car entered the lot
     */
    public ArrayList<Integer> getMinutesEntered(){ return minutesEntered; }
    /**
     * @description returns value of prevMinutes
     * @return returns value of prevMinutes
     */
    public int getPrevMinutes(){ return prevMinutes; }
    /**
     * @description time Closed keeps track of when the parking lot closed
     * @return returns time that parking lot closed
     */
    public int getTimeClosed(){ return timeClosed; }
    /**
     * @description returns number of vehicles in parking lot
     * @return returns vehicleCount
     */
    public int getVehicleCount(){ return vehicleCount; }
    /**
     * @description returns lastPayTime
     * @return returns lastPayTime
     */
    public int getLastPayTime(){ return lastPayTime; }
    /**
     * @description used to calculate how much to change cars and keep track of
     * whether a car has paid already.
     * @param id - ID of car
     * @return returns true if car has paid before and false otherwise
     */
    public boolean hasPaidBefore(int id){
        if(paidCarID.containsKey(id)) return true;
        return false;
    }
    /**
     * @description used to determine whether to turn off the sign.
     * The parking lot is marked as "CLOSED" when an arbitrary number of cars fill the lot.
     * However, cars are still permitted to enter the lot until all of the spaces
     * are taken/filled.
     * @return boolean value: true if the number of vehicles in the lot is greater than or equal to the
     * threshold.
     */
    public boolean isClosed() {
        return (100 * (float) getVehiclesInLot() / (float) spacesInLot) >= CLOSED_THRESHOLD;
    }
    /**
     * @description
     * inserts cars that enter a parking lot into an ArrayList which keeps track
     * of when cars enter the parking lot. This ArrayList also keeps track of
     * which cars have exited the parking lot.
     * vehicleCount is incremented when a car enters the lot.
     * the amount of time that the lot has been
     * @param minutes - time at which car enters the parking lot
     * @return returns the ID number of the car entering the parking lot
     */
    public int markVehicleEntry(int minutes) {
        if(vehicleCount>=spacesInLot) return -1;
        if(minutes<prevMinutes) return -1;
        prevMinutes=minutes;
        if(!isClosed()) timeClosed=minutes;
        minutesEntered.add(minutes);
        vehicleCount++;
        if(isClosed()) { totalTimeClosed+=(minutes-timeClosed); timeClosed=minutes; }
        return vehicleID++;
    }
    /**
     * @description
     * remove car from parking lot by
     * decrementing the number of cars in the parking lot
     * @param minutes - time that car leaves the lot
     * @param id - ID of car that leaves the lot
     * @return returns -1 if something went wrong and 0 otherwise
     */
    public int markVehicleExit(int minutes, int id) {
        if(minutes<prevMinutes) return -1;
        prevMinutes=minutes;
        if(isClosed()) { totalTimeClosed+=(minutes-timeClosed); timeClosed=minutes; }
        if(id>=vehicleCount) return -1;
        vehicleCount--;
        return 0; // ??? still not sure what this is supposed to be
    }
    /**
     * @description constructs ParkingLot object with default name of "test"
     * and 0 spaces for cars
     * @return returns ParkingLot object
     */
    public ParkingLot() {
        this("test", 0);
    }
    /**
     * @description constructs ParkingLot object with number of spaces passed as argument
     * @param spaces - number of spaces that we want the parking lot to have
     * @return returns ParkingLot object
     */
    public ParkingLot(int spaces) {
        this("test", spaces);
    }
    /**
     * @description creates a ParkingLot object. initializes variables/fields
     * @param name - name of parking lot
     * @param spaces  - number of spaces in the parking lot
     * @return ParkingLot object
     */
    public ParkingLot(String name, int spaces) {
        minutesEntered=new ArrayList<Integer>();
        this.parkingLotName=name;
        this.spacesInLot=spaces;
        totalTimeClosed=0;
        vehicleID=0;
        vehicleCount=0;
        lastPayTime=-16;
        paidCarID=new Hashtable<Integer,Integer>();
    }
    /**
     * @description calls toString function and prints the string that is returned
     * from toString
     * @param pl - parking lot
     */
    public void println(ParkingLot pl){ System.out.println(this.toString()); }
    /**
     * @description set the time that the car entered the lot.
     * the id of the car is the index in the ArrayList that maps to the
     * minute the car entered the lot.
     * @param id - id of car
     * @param time - time that car entered the lot
     */
    public void setMinutesEntered(int id, int time){ minutesEntered.set(id,time); }
    /**
     * @description lastPayTime keeps track of when the last car paid a fee so that
     * we can know when the next car can pay (after 15 minutes).
     * @param time - set the time that the last vehicle payed
     */
    public void setLastPayTime(int time){ lastPayTime=time; }
    /**
     * @description updates the current time based on the last car that started heading
     * toward the exit.
     * @param min - current time
     */
    public void setPrevMinutes(int min){ prevMinutes=min; }
    /**
     * @description used to update the amount of time that the lot has been closed
     * @param time - time replaces old value by assigning it to totalTimeClosed.
     */
    public void setTotalTimeClosed(int time){
        totalTimeClosed=time;
    }
    /**
     * @description sets the time that the lot closed
     * @param time - time that the lot closed
     */
    public void setTimeClosed(int time){
        timeClosed=time;
    }
    /**
     * @description updates the number of vehicles in the parking lot
     * @param cnt - number of vehicles in parking lot
     */
    public void setVehicleCount(int cnt){ vehicleCount=cnt; }
    /**
     * @description keeps track of the IDs of cars that have paid their fee before
     * @param id - id of car that just paid
     */
    public void setCarPaid(int id){
        paidCarID.put(id,1);
    }
    /**
     * @description reports the number of vehicles in the parking lot.
     * Also, it tells us whether the lot is closed or not. If the lot isn't closed,
     * the percentage of vehicles that have been taking is reported.
     * @return string reporting the number of vehicles in the parking lot and the corresponding percentage value
     */
    public String toString() {
        String result="Status for "+parkingLotName+" parking lot: "+getVehiclesInLot()+" vehicles (";
        if(isClosed()){ result=result+"CLOSED)"; }
        else {
            DecimalFormat df=new DecimalFormat("##.#%");
            result=result+df.format((float) getVehiclesInLot() / (float) spacesInLot) + ")";
        }
        return result;
    }
}