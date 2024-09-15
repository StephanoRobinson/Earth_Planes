public class Aeroport {
    //attribs
    private final String IATA, country, name;
    private final Double latitude, longitude;

    public Aeroport(String country, String name, String IATA, Double latitude, Double longitude){
        this.country = country;
        this.name = name;
        this.IATA = IATA;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //getters
    public String getIATA(){
        return IATA;
    }
    public String getCountry() {
        return country;
    }
    public Double getLatitude(){
        return latitude;
    }
    public Double getLongitude(){
        return longitude;
    }

    public Double distance(Double lat_ext, Double long_ext){
        return Math.pow(this.latitude-lat_ext,2)+Math.pow((this.longitude-long_ext)*Math.cos((this.latitude+lat_ext)/2),2);
    }

    @Override
    public String toString(){
        return "Nom : "+name+"\nPays : "+country+"\nLongitude : "+longitude+"\nLatitude : "+latitude+"\nCode IATA : "+IATA;
    }

}
