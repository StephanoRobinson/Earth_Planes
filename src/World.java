//import java.lang.reflect.Array;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class World {
    protected ArrayList<Aeroport> list = new ArrayList<>();
    public String filename;

    public World(String filename){
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(filename));
            String line = buffer.readLine(); //méthode readLine() de l'instance buffer => lire ligne par ligne
            while(line!=null){
                line=line.replaceAll("\"",""); //enlève les double quote
                String[] fields = line.split(","); //sépare les champs
                //filtrer les large airport
                if(fields[1].equals("large_airport")){
                    //ajouter les champs dans la list (ArrayList<Aeroport>)
                    list.add(new Aeroport(fields[5],fields[2],fields[9],Double.parseDouble(fields[12]),Double.parseDouble(fields[11])));
                }
                line = buffer.readLine();
            }
        }
        catch (Exception e){
            System.out.println("ERROR : ");
            e.printStackTrace();
        }
    }
    public Aeroport findNearestAirport(Double lat_ext, Double long_ext){
        //VARs
        Double distance_min = this.list.get(0).distance(lat_ext, long_ext); //initialisation de la valeur min temporaire
        Aeroport aeroport_min = null; //Sécurité

        //parcourt la liste objet par objet
            for (Aeroport aeroport : this.list) {
                Double distance = aeroport.distance(lat_ext, long_ext); // var temp

                //comparaison
                if (distance<=distance_min){
                    distance_min = distance; // = aeroport.distance(lat_ext, long_ext)
                    aeroport_min = aeroport;
                }
            }
        //System.out.println("Distances : "+distances);
        return aeroport_min;

    }
    public Aeroport findByCode(String IATA){
        for (Aeroport aeroport : this.list){
            if (aeroport.getIATA().equals(IATA)){
                //System.out.println("\nFound by IATA code : \n"+aeroport);
                return aeroport;
            }
        }
        return null; //si aucun aéroport n'a été trouvé
    }

    public ArrayList<Aeroport> getList(){
        return this.list;
    }
    /*public main(){

    }*/

}
