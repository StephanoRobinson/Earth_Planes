public class Aeroport {
    private String IATA, country, name;
    private Double latitude, longitude;

    public Aeroport(String country, String name, String IATA, Double latitude, Double longitude){
        this.country = country;
        this.name = name;
        this.IATA = IATA;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getIATA(){
        return IATA;
    }
    public Double getLatitude(){
        return latitude;
    }
    public Double getLongitude(){
        return longitude;
    }

    @Override
    public String toString(){
        return "Nom : "+name+"\nLongitude : "+longitude+"\nLatitude : "+latitude+"\nCode IATA : "+IATA;
    }
}
