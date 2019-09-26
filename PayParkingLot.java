package hofer_gabriel;
/**
 * @author Gabriel Hofer
 * @description
 *• hofer_gabriel.PayParkingLot is derived from ParkingLot
 * • A paid lot also has an hourly fee for a car which is collected when the car exits or at the end of
 * the day, that should be set in the constructor.
 * • The fee can vary, and has a default of $1.00 per hour
 * • It must override the toString function to include the current money collected. It should
 * append “ Money Collected: $[totalProfit]” to the string produced by the parent class. The profit
 * should be rounded to the nearest cent and must display to 2 decimal places.
 * • Add a function called getProfit() that will return the current funds collected. Since this is
 * the funds currently collected, this will be accurate even if the cars have not left the lot yet.
 * • If markVehicleExit is given a car with an invalid id, simply ignore it as a glitch.
 * • A car in paid parking car may “pay as they go” similar to refilling a parking meter. The first 15
 * minute after entry and paying are ignored to allow time to reach the exit. This means other cars
 * can exit before they do. Therefore,
 * o Do not charge if they try to exit/pay before they came. This is a glitch like in the
 * ParkingLot class.
 * o If they try to exit
 * ▪ If it is under 15 since the entry or last pay, do not charge, and do not update
 * their last time of entry/pay.
 * ▪ If it is over 15 minutes since the entry or last pay, charge them since the last
 * time of entry/pay, and update their last time of entry/pay.
 * o If the car does not exit (the time is already updated to a later time due to another car
 * leaving), leave the car in the lot.
 * */
import java.text.DecimalFormat;
public class PayParkingLot extends ParkingLot {
    private double hourlyFee;
    private double totalProfit;

    /**
     * @description getter function returns total profit made by parking
     * lot since the last vehicle paid
     * @return returns value of totalProfit
     */
    public double getProfit() {
        return totalProfit;
    }
    /**
     * @description
     * keeps track of vehicles when they exit the parking lot.
     * Cars are marked with a -1 number in our ArrayList of cars when they leave
     * so that we know that they have left the parking lot.
     * updates the amount of money that is collected when the car leaves.
     * @param minutes - time that vehicle exited the lot
     * @param id - id of car
     * @return returns -1 if something went wrong (invalid operation)
     */
    public int markVehicleExit(int minutes, int id) {
        if(minutes<super.getMinutesEntered().get(id)) return -1; // glitch0
        if(isClosed()) {
            super.setTotalTimeClosed((minutes - super.getTimeClosed()));
            super.setTimeClosed(minutes);
        }
        if(minutes-super.getMinutesEntered().get(id)>=15) {
            if (super.getMinutesEntered().get(id) == -1) return -1;
            if (minutes - super.getLastPayTime() >= 15 || minutes < super.getLastPayTime()) {
                if(super.hasPaidBefore(id))
                    totalProfit+=(minutes-super.getLastPayTime())*hourlyFee/60.0;
                else
                    totalProfit+=(minutes - super.getMinutesEntered().get(id)) * hourlyFee / 60.0;
                super.setLastPayTime(minutes);
                super.setCarPaid(id);
            }
        }
        if(minutes<super.getPrevMinutes()) return -1; // glitch?
        super.setVehicleCount(super.getVehicleCount()-1);
        super.setPrevMinutes(minutes);
        super.setMinutesEntered(id,-1);
        return 0; // ??? still not sure what this is supposed to be
    }

    /**
     * @description default constructor for hofer_gabriel.PayParkingLot class
     * @return hofer_gabriel.PayParkingLot object
     */
    public PayParkingLot(){
        super();
        hourlyFee=(float)1.00;
    }
    /**
     * @description constructs hofer_gabriel.PayParkingLot object and sets the name and number of spaces in
     * the parking lot. A default fee of $1.00 is used since no value was specified.
     * @param name - name of parking lot
     * @param spaces - number of spaces to park in the parking lot
     */
    public PayParkingLot(String name, int spaces){
        super(name,spaces);
        hourlyFee=(float)1.00;
    }
    /**
     * @description constructs hofer_gabriel.PayParkingLot object and sets the name and number of spaces in
     * the parking lot. It also set the fee charged per hour per car.
     * @param name - name of parking lot
     * @param spaces - number of spaces in parking lot
     * @param fee - money charged per hour for each car in lot
     */
    public PayParkingLot(String name, int spaces, double fee){
        super(name,spaces);
        hourlyFee=fee;
    }
    /**
     * @description constructs hofer_gabriel.PayParkingLot object and sets the number of spaces in
     * the parking lot. It also sets the fee charged per hour per car.
     * @param spaces - number of spaces in parking lot
     * @param fee - money charged per hour for each car in lot
     */
    public PayParkingLot(int spaces, double fee){
        super(spaces);
        hourlyFee=fee;
    }
    /**
     * @description formats the numbers to have two decimal places because we are
     * reporting money values in the string. also constructs a string
     * containing how much money has been collected.
     * @return returns a string that contains money collected for the lot
     * and how full the lot is
     */
    public String toString(){
        DecimalFormat df=new DecimalFormat("0.00");
        return super.toString()+" Money collected: $"+df.format(totalProfit);
    }
}