/**
 * @program CSC-461 Basic Java
 * @author Gabriel Hofer
 * @date 9/26/2019
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
 * District uses an ArrayList to store multiple parking lots.
 * It also Uses a Hashtable to stoe
 */
public class District {
    private ArrayList<ParkingLot> parkingLots;
    private int parkingLotID;
    private int timeClosedDistrict;
    private int totalTimeClosedDistrict;
    private int prevMinutesDistrict;
    private boolean allLotsClosed;
    private int vehiclesInDistrict;
    public int add(ParkingLot pl){
        parkingLots.add(pl);
        return parkingLotID++;
    }
    public District(){
        parkingLots=new ArrayList<ParkingLot>();
        parkingLotID=0;
        totalTimeClosedDistrict=0;
    }
    public boolean isClosed(){
        allLotsClosed=true;
        for(ParkingLot pl:parkingLots){
            if (!pl.isClosed())
                return false;}
        return true;
    }
    public int getClosedMinutes(){
        return totalTimeClosedDistrict;
    }
    public ParkingLot getLot(int plID) {
        return parkingLots.get(plID);
    }
    public int getVehiclesParkedInDistrict() {
        vehiclesInDistrict=0;
        for(ParkingLot lot:parkingLots)
            vehiclesInDistrict+=lot.getVehiclesInLot();
        return vehiclesInDistrict;
    }
    public double getTotalMoneyCollected() {
        double money=0;
        for(ParkingLot pl:parkingLots) {
            if (pl instanceof PayParkingLot) {
                money += ((PayParkingLot) pl).getProfit();
            }
        }
        return money;
    }
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
    public int markVehicleExit(int lotID, int minute, int id) {
        if(minute<prevMinutesDistrict) return -1;
        prevMinutesDistrict=minute;
        if(isClosed()){
            totalTimeClosedDistrict+=(minute-timeClosedDistrict);
            timeClosedDistrict=minute;
        }
        return parkingLots.get(lotID).markVehicleExit(minute,id);
    }
    public String toString(){
        String str="District status:\n";
        for(ParkingLot pl:parkingLots)
            str+=pl.toString()+"\n";
        return str;
    }
}