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

    // constructors
    public ParkingLot(){
        minutesEntered=new ArrayList<Integer>();
        this.parkingLotName="test";
        totalTimeClosed=0;
        vehicleID=0;
    }
    public ParkingLot(int spaces) {
        minutesEntered=new ArrayList<Integer>();
        this.parkingLotName="test";
        this.spacesInLot=spaces;
        totalTimeClosed=0;
        vehicleID=0;
    }
    public ParkingLot(String name, int spaces) {
        minutesEntered=new ArrayList<Integer>();
        this.parkingLotName=name;
        this.spacesInLot=spaces;
        totalTimeClosed=0;
        vehicleID=0;
    }

    // member functions
    public int markVehicleEntry(int minutes) {
        if(minutesEntered.size()==spacesInLot) return -1;
        if(minutes<prevMinutes) return -1;
        prevMinutes=minutes;
        if(!isClosed()) timeClosed=minutes;
        minutesEntered.add(minutes);
        vehicleID+=1;
        if(isClosed()) { totalTimeClosed+=(minutes-timeClosed); timeClosed=minutes; }
//                System.out.println(minutesEntered.toString()+"\t\tclosed mins: "+getClosedMinutes());
//                System.out.println("\t\ttime closed: "+getClosedMinutes());
        return vehicleID-1;
    }
    public int markVehicleExit(int minutes, int id) {
        // check if vehicle id is actually in lot
        if(minutes<prevMinutes) return -1;
        prevMinutes=minutes;
        if(isClosed()) { totalTimeClosed+=(minutes-timeClosed); timeClosed=minutes; }
//                System.out.println(minutesEntered.toString()+"\t\tclosed mins: "+getClosedMinutes());
//                System.out.println("\t\ttime closed: "+getClosedMinutes());
        if(id>=minutesEntered.size()) return -1;
        minutesEntered.remove(id);
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
    public int getVehiclesInLot(){ return minutesEntered.size(); }
    public void println(ParkingLot pl){ System.out.println(this.toString()); }
}
