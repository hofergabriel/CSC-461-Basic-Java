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
    public double getProfit() {
        return totalProfit;
    }
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
    public PayParkingLot(){
        super();
        hourlyFee=(float)1.00;
    }
    public PayParkingLot(String name, int spaces){
        super(name,spaces);
        hourlyFee=(float)1.00;
    }
    public PayParkingLot(String name, int spaces, double fee){
        super(name,spaces);
        hourlyFee=fee;
    }
    public PayParkingLot(int spaces, double fee){
        hourlyFee=fee;
    }
    public String toString(){
        DecimalFormat df=new DecimalFormat("0.00");
        return super.toString()+" Money collected: $"+df.format(totalProfit);
    }
}