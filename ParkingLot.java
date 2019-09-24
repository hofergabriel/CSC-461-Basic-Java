import java.util.ArrayList;
import java.text.DecimalFormat;
public class ParkingLot {
    // variables
    private String parkingLotName;
    private int vehicleID;
    private int spacesInLot;
    //private Hashtable<Integer,Integer> idToEntryTime;
    private ArrayList<Integer> minutesEntered;
    private int timeClosed; // last time that the lot was closed
    private int totalTimeClosed; // total duration of time that lot was closed
    public static final int CLOSED_THRESHOLD=80;
    private int prevMinutes;
    private int vehicleCount;
    private int lastPayTime;

    // constructors
    public ParkingLot(){
        minutesEntered=new ArrayList<Integer>();
        this.parkingLotName="test";
        totalTimeClosed=0;
        vehicleID=0;
        vehicleCount=0;
        lastPayTime=-16;
    }
    public ParkingLot(int spaces) {
        minutesEntered=new ArrayList<Integer>();
        this.parkingLotName="test";
        this.spacesInLot=spaces;
        totalTimeClosed=0;
        vehicleID=0;
        vehicleCount=0;
        lastPayTime=-16;
    }
    public ParkingLot(String name, int spaces) {
        minutesEntered=new ArrayList<Integer>();
        this.parkingLotName=name;
        this.spacesInLot=spaces;
        totalTimeClosed=0;
        vehicleID=0;
        vehicleCount=0;
        lastPayTime=-16;
    }

    // member functions
    public int markVehicleEntry(int minutes) {
        if(vehicleCount>=spacesInLot) return -1;
        if(minutes<prevMinutes) return -1;
        prevMinutes=minutes;
        if(!isClosed()) timeClosed=minutes;
        minutesEntered.add(minutes);
        vehicleCount++;
        System.out.println("markvehicleentry, vehicleCount = "+vehicleCount);
        //vehicleID+=1;
        if(isClosed()) { totalTimeClosed+=(minutes-timeClosed); timeClosed=minutes; }
        return vehicleID++;
    }
    public int markVehicleExit(int minutes, int id) {
        if(minutes<prevMinutes) return -1;
        prevMinutes=minutes;
        if(isClosed()) { totalTimeClosed+=(minutes-timeClosed); timeClosed=minutes; }
        if(id>=vehicleCount) return -1;
        //minutesEntered.remove(id);
        vehicleCount--;
        return 0; // ??? still not sure what this is supposed to be
    }

    public int getClosedMinutes() { return totalTimeClosed; }
    public String toString() {
        String result="Status for "+parkingLotName+" parking lot: "+getVehiclesInLot()+" vehicles (";
        if(isClosed()){ result=result+"CLOSED)"; }
        else {
            DecimalFormat df=new DecimalFormat("##.#%");
            result=result+df.format((float) getVehiclesInLot() / (float) spacesInLot) + ")";
        }
        return result;
    }

    public String getName(){ return this.parkingLotName; }
    public boolean isClosed() {
        return (100*(float)getVehiclesInLot()/(float)spacesInLot)>=CLOSED_THRESHOLD;
    }
    public int getVehiclesInLot(){ return vehicleCount; }
    public ArrayList<Integer> getMinutesEntered(){
        return minutesEntered;
    }
    public int getPrevMinutes(){
        return prevMinutes;
    }
    public void setPrevMinutes(int min){
        prevMinutes=min;
    }
    public int getTimeClosed(){
        return timeClosed;
    }
    public void setTotalTimeClosed(int time){
        totalTimeClosed=time;
    }
    public void setTimeClosed(int time){
        timeClosed=time;
    }
    public void setVehicleCount(int cnt){ vehicleCount=cnt; }
    public int getVehicleCount(){ return vehicleCount; }
    public void println(ParkingLot pl){ System.out.println(this.toString()); }
    public void setLastPayTime(int time){ lastPayTime=time; }
    public int getLastPayTime(){ return lastPayTime; }



}