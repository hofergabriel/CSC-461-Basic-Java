import java.text.DecimalFormat;
public class PayParkingLot extends ParkingLot {
    private double hourlyFee;
    private double totalProfit;
    // constructors
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
    public int markVehicleExit(int minutes, int id) {
        System.out.println("ONE");
        if(minutes<super.getMinutesEntered().get(id)) return -1; // glitch0
        System.out.println("TWO");
        if(isClosed()) {
            super.setTotalTimeClosed((minutes - super.getTimeClosed()));
            super.setTimeClosed(minutes);
        }
        if(minutes-super.getMinutesEntered().get(id)>=15) {
            System.out.println("THREE");
            if(super.getMinutesEntered().get(id)==-1) return -1;
            System.out.println("FOUR");
            if (minutes - super.getLastPayTime()>=15||minutes<super.getLastPayTime()){
                System.out.println("FIVE");
                System.out.println("MINUTES = "+minutes);
                System.out.println("entered = "+super.getMinutesEntered().get(id));
                System.out.println("total profit = "+totalProfit);
                totalProfit += (minutes - super.getMinutesEntered().get(id)) * hourlyFee / 60;
                super.setLastPayTime(minutes);
            }
        }
        System.out.println("proft : "+totalProfit);
        if(minutes<super.getPrevMinutes()) return -1; // glitch?
        System.out.println("SIX");
        super.setVehicleCount(super.getVehicleCount()-1);
        System.out.println("vehicleCount : "+super.getVehiclesInLot());
        super.setPrevMinutes(minutes);
        super.getMinutesEntered().set(id,-1);
        return 0; // ??? still not sure what this is supposed to be
    }

    public double getProfit() {
        return totalProfit;
    }
}