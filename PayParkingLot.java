import java.text.DecimalFormat;
public class PayParkingLot extends ParkingLot {
    private double hourlyFee;
    private double totalProfit;

    public PayParkingLot(){
        super();
        hourlyFee=(float)1.00;
    }
    public PayParkingLot(String name, int spaces){
        super();
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
        if(minutes<super.getPrevMinutes()) return -1;
        super.setPrevMinutes(minutes);
        if(isClosed()) { super.setTotalTimeClosed((minutes-super.getTimeClosed())); super.setTimeClosed(minutes); }
        if(id>=super.getMinutesEntered().size()) return -1;

        System.out.println("id : "+id+" exited and totalProfit : "+totalProfit);
        if(minutes-super.getMinutesEntered().get(id)>=15)
            totalProfit=(minutes-super.getMinutesEntered().get(id))*hourlyFee/60;
        super.getMinutesEntered().remove(id);
        return 0; // ??? still not sure what this is supposed to be
    }

    public double getProfit() {
        int profit=0;
        for(Integer i:super.getMinutesEntered())
            if(i-super.getPrevMinutes()>=15) {
                System.out.println("i : "+i+" super.getprevmins : "+super.getPrevMinutes());
                profit+=(super.getPrevMinutes() - i) * hourlyFee/60;
            }
        System.out.println("\t\tcurrent profit : "+totalProfit);
        return totalProfit+profit;
    }
}
