import java.util.ArrayList;
import java.util.Hashtable;
public class District {
    private ArrayList<ParkingLot> parkingLots;
    private Hashtable<Integer, ParkingLot> IDToParkingLot;
    private int parkingLotID;
    private int timeClosedDistrict;
    private int totalTimeClosedDistrict;
    private int prevMinutesDistrict;
    private boolean allLotsClosed;
    private int vehiclesInDistrict;
    public District(){
        parkingLots=new ArrayList<ParkingLot>();
        IDToParkingLot=new Hashtable<Integer, ParkingLot>();
        parkingLotID=0;
        totalTimeClosedDistrict=0;
    }
    public int markVehicleEntry(int lotID, int minute) {
        // check if all parking lots are closed
        if(minute<prevMinutesDistrict) return -1;
        prevMinutesDistrict=minute;
        if(!isClosed()) timeClosedDistrict=minute;
        parkingLotID++;
        if(isClosed()){
            totalTimeClosedDistrict+=(minute-timeClosedDistrict);
            timeClosedDistrict=minute;
        }
        return IDToParkingLot.get(lotID).markVehicleEntry(minute);
    }
    public int markVehicleExit(int lotID, int minute, int id) {
        if(minute<prevMinutesDistrict) return -1;
        prevMinutesDistrict=minute;
        if(isClosed()){
            totalTimeClosedDistrict+=(minute-timeClosedDistrict);
            timeClosedDistrict=minute;
        }
        return IDToParkingLot.get(lotID).markVehicleExit(minute,id);
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
    public int add(ParkingLot pl){
        parkingLots.add(pl);
        IDToParkingLot.put(parkingLotID,pl);
        return parkingLotID++;
    }
    public ParkingLot getLot(int something) {
        return IDToParkingLot.get(something);
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
    public double getlotprofit(int id){
        return ((PayParkingLot) IDToParkingLot.get(id)).getProfit();
    }
    public String toString(){
        String str="District status:\n";
        for(ParkingLot pl:parkingLots)
            str+=pl.toString()+"\n";
        return str;
    }
}