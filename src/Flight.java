import java.time.LocalDateTime;

public class Flight {
    //attribs
    private final String airLineCode;
    private final String airLineName;
    private final LocalDateTime departureTime;
    private final Aeroport aeroportDeparture;
    private final LocalDateTime arrivalTime;
    private final Aeroport aeroportArrival;
    private final int number;

    //constructor
    public Flight(String airLineCode, String airLineName, LocalDateTime departureTime, Aeroport aeroportDeparture, LocalDateTime arrivalTime, Aeroport aeroportArrival, int number) {
        this.airLineCode = airLineCode;
        this.airLineName = airLineName;
        this.departureTime = departureTime;
        this.aeroportDeparture = aeroportDeparture;
        this.arrivalTime = arrivalTime;
        this.aeroportArrival = aeroportArrival;
        this.number = number;
    }

    //getters
    public String getAirLineCode() {
        return airLineCode;
    }

    public String getAirLineName() {
        return airLineName;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public Aeroport getAeroportDeparture() {
        return aeroportDeparture;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Aeroport getAeroportArrival() {
        return aeroportArrival;
    }

    public int getNumber() {
        return number;
    }

    public String toString(){
        String out = "\nVol : "+this.getAirLineCode()+", "+this.getAirLineName()+", "+this.getNumber()+
                "\nDépart : \n{"+this.getAeroportDeparture()+", \nheure : "+this.getDepartureTime()+"}"+
                "\nArrivée : \n{"+this.getAeroportArrival()+", \nheure : "+this.getArrivalTime()+"}";
        return out;
    }
}
