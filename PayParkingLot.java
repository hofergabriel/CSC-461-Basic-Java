/**
 * @author Gabriel Hofer
 * @description PayParkingLot is a subclass of the ParkingLot class.
 * PayParkingLot class keeps track of cars that enter and exit
 * parking lots that change a fee. The variable hourlyFee stores the
 * fee that the lot requires cars to pay to park there. The variable totalProfit
 * stores the total amount of money paid.
 */


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
     * @description default constructor for PayParkingLot class
     * @return PayParkingLot object
     */
    public PayParkingLot(){
        super();
        hourlyFee=(float)1.00;
    }
    /**
     * @description constructs PayParkingLot object and sets the name and number of spaces in
     * the parking lot. A default fee of $1.00 is used since no value was specified.
     * @param name - name of parking lot
     * @param spaces - number of spaces to park in the parking lot
     */
    public PayParkingLot(String name, int spaces){
        super(name,spaces);
        hourlyFee=(float)1.00;
    }
    /**
     * @description constructs PayParkingLot object and sets the name and number of spaces in
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
     * @description constructs PayParkingLot object and sets the number of spaces in
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