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
     * @param
     * @return
     */
    public int getClosedMinutes() { return totalTimeClosed; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public String getName(){ return this.parkingLotName; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public int getVehiclesInLot(){ return vehicleCount; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public ArrayList<Integer> getMinutesEntered(){ return minutesEntered; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public int getPrevMinutes(){ return prevMinutes; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public int getTimeClosed(){ return timeClosed; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
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
    public boolean isClosed() { return (100*(float)getVehiclesInLot()/(float)spacesInLot)>=CLOSED_THRESHOLD; }
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
     * @description
     * @param
     * @return
     */
    public ParkingLot(){ this("test",0); }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public ParkingLot(int spaces) { this("test",spaces); }
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
     * @description
     * @param
     * @return
     */
    public void println(ParkingLot pl){ System.out.println(this.toString()); }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public void setMinutesEntered(int id, int time){ minutesEntered.set(id,time); }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public void setLastPayTime(int time){ lastPayTime=time; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public void setPrevMinutes(int min){ prevMinutes=min; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public void setTotalTimeClosed(int time){
        totalTimeClosed=time;
    }

    public void setTimeClosed(int time){
        timeClosed=time;
    }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public void setVehicleCount(int cnt){ vehicleCount=cnt; }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
     */
    public void setCarPaid(int id){
        paidCarID.put(id,1);
    }
    /**
     * @author Gabriel Hofer
     * @description
     * @param
     * @return
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