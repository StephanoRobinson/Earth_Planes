import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME; //formatter de datetime

public class JsonFlightFiller {
    //attrib
    private ArrayList<Flight> flightList = new ArrayList<>();
    int lentgh_array = flightList.size;

    //constructor
    public JsonFlightFiller(String jsonString, World w) {
        try {
            InputStream is = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8)); //buffer en byte
            JsonReader rdr = Json.createReader(is); //Lecteur JSON
            JsonObject obj = rdr.readObject(); //Objet JSON
            JsonArray results = obj.getJsonArray("data"); //obtenir le champ data du fichier json
            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                try {
                    //prendre info de chaque vol
                    /*
                    * this.airLineCode = airLineCode;
                    * this.airLineName = airLineName;
                    * this.departureTime = departureTime;
                    * this.aeroportDeparture = aeroportDeparture;
                    * this.arrivalTime = arrivalTime;
                    * this.aeroportArrival = aeroportArrival;
                    * this.number = number;
                    * */
                    //String date = result.getJsonString("flight_date").getString(); //une valeur
                    //airline, flight , departure et arrival sont des objets
                    //String airLineCode = result.getJsonObject("airline").getString("iata");
                    //String airLineName = result.getJsonObject("airline").getString("name");
                    String airLineCode = (result.containsKey("airline"))?result.getJsonObject("airline").getString("iata"):"";
                    String airLineName = (result.containsKey("airline"))?result.getJsonObject("airline").getString("name"):"";
                    int number = Integer.parseInt(result.getJsonObject("flight").getString("number"));

                    //depart
                    Aeroport aeroportDeparture = w.findByCode(result.getJsonObject("departure").getString("iata"));
                    //String departureTimeActual = result.getJsonObject("departure").getString("actual");
                    String departureTimeActual = result.getJsonObject("departure").getString("scheduled");
                    LocalDateTime departureTime = (Objects.equals(departureTimeActual, "null"))?null:LocalDateTime.parse(departureTimeActual, ISO_OFFSET_DATE_TIME);

                    //arrivée
                    Aeroport aeroportArrival = w.findByCode(result.getJsonObject("arrival").getString("iata"));
                    //String arrivalTimeActual = result.getJsonObject("arrival").getString("actual");
                    String arrivalTimeActual = result.getJsonObject("arrival").getString("scheduled");
                    LocalDateTime arrivalTime = (Objects.equals(arrivalTimeActual, "null"))?null:LocalDateTime.parse(arrivalTimeActual, ISO_OFFSET_DATE_TIME);

                    //créer un objet Flight avec ces infos
                    Flight flight = new Flight(airLineCode, airLineName,departureTime,aeroportDeparture,arrivalTime,aeroportArrival,number);
                    //ajouter à flightList
                    flightList.add(flight);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //methods
    public ArrayList<Flight> getFlightList() {
        return flightList;
    }

    public void displayFlight(){
        for (Flight flight : this.getFlightList()) System.out.println(flight);

    }

    /*public static void main(String[] args) {
        try { //manipulation de fichier => Exception => try/catch
            World world = new World("./data/airport-codes_no_comma.csv");
            BufferedReader br = new BufferedReader(new FileReader("./data/JsonOrly.txt"));
            String test = br.readLine(); //le buffer au dessus nous permet de lire ligne par ligne via cette méthode
            JsonFlightFiller jSonFlightFiller = new JsonFlightFiller(test, world);
            jSonFlightFiller.displayFlight();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}


