/**
 * @author Gabriel Hofer
 * @description ParkingLot class keeps track of cars that enter and exit
 * the parking lot. The parking lot may be given a meaningful name to
 * or otherwise be set to "test" by default. Cars are given vehicleIDs
 * when they enter the lot. Each lot is either opened or closed at all times.
 * vehicleCount is a variable that stores the number of vehicles in the lot.
 * totalTimeClosed records the amount of time that the lot has been closed
 * since the last time that a car has exited the lot.
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
     * @author Gabriel Hofer
     * @description
     * @return
     */
    public int getClosedMinutes() { return totalTimeClosed; }
    /**
     * @author Gabriel Hofer
     * @description returns the duration of time that the lot has been closed
     * @return returns the duration of time that the lot has been closed
     */
    public String getName(){ return this.parkingLotName; }
    /**
     * @author Gabriel Hofer
     * @description returns name of parking lot
     * @return return name of parking lot
     */
    public int getVehiclesInLot(){ return vehicleCount; }
    /**
     * @author Gabriel Hofer
     * @description returns number of vehicles in the lot
     * @return returns number of vehicles in the lot
     */
    public ArrayList<Integer> getMinutesEntered(){ return minutesEntered; }
    /**
     * @author Gabriel Hofer
     * @description returns ArrayList object containing the minute at which
     * each car entered the lot.
     * @return an ArrayList containing the minute that each car entered the lot
     */
    public int getPrevMinutes(){ return prevMinutes; }
    /**
     * @author Gabriel Hofer
     * @description returns value of prevMinutes
     * @return returns value of prevMinutes
     */
    public int getTimeClosed(){ return timeClosed; }
    /**
     * @author Gabriel Hofer
     * @description time Closed keeps track of when the parking lot closed
     * @return returns time that parking lot closed
     */
    public int getVehicleCount(){ return vehicleCount; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public int getLastPayTime(){ return lastPayTime; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public boolean hasPaidBefore(int id){
        if(paidCarID.containsKey(id)) return true;
        return false;
    }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public boolean isClosed() { return (100*(float)getVehiclesInLot()/(float)spacesInLot)>=CLOSED_THRESHOLD; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
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
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
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
     * @author Gabriel Hofer
     * @description constructs ParkingLot object with default name of "test"
     * and 0 spaces for cars
     * @return returns ParkingLot object
     */
    public ParkingLot() {
        this("test", 0);
    }
    /**
     * @author Gabriel Hofer
     * @description constructs ParkingLot object with number of spaces passed as argument
     * @param spaces - number of spaces that we want the parking lot to have
     * @return returns ParkingLot object
     */
    public ParkingLot(int spaces) {
        this("test", spaces);
    }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
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
     * @author Gabriel Hofer
     * @description calls toString function and prints the string that is returned
     * from toString
     * @param pl - parking lot
     */
    public void println(ParkingLot pl){ System.out.println(this.toString()); }
    /**
     * @author Gabriel Hofer
     * @description set the time that the car entered the lot.
     * the id of the car is the index in the ArrayList that maps to the
     * minute the car entered the lot.
     * @param id - id of car
     * @param time - time that car entered the lot
     */
    public void setMinutesEntered(int id, int time){ minutesEntered.set(id,time); }
    /**
     * @author Gabriel Hofer
     * @description lastPayTime keeps track of when the last car paid a fee so that
     * we can know when the next car can pay (after 15 minutes).
     * @param time - set the time that the last vehicle payed
     */
    public void setLastPayTime(int time){ lastPayTime=time; }
    /**
     * @author Gabriel Hofer
     * @description updates the current time based on the last car that started heading
     * toward the exit.
     * @param min - current time
     */
    public void setPrevMinutes(int min){ prevMinutes=min; }
    /**
     * @author Gabriel Hofer
     * @description used to update the amount of time that the lot has been closed
     * @param time - time replaces old value by assigning it to totalTimeClosed.
     */
    public void setTotalTimeClosed(int time){
        totalTimeClosed=time;
    }
    /**
     * @author Gabriel Hofer
     * @description sets the time that the lot closed
     * @param time - time that the lot closed
     */
    public void setTimeClosed(int time){
        timeClosed=time;
    }
    /**
     * @author Gabriel Hofer
     * @description updates the number of vehicles in the parking lot
     * @param cnt - number of vehicles in parking lot
     */
    public void setVehicleCount(int cnt){ vehicleCount=cnt; }
    /**
     * @author Gabriel Hofer
     * @description keeps track of the IDs of cars that have paid their fee before
     * @param id - id of car that just paid
     */
    public void setCarPaid(int id){
        paidCarID.put(id,1);
    }
    /**
     * @author Gabriel Hofer
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