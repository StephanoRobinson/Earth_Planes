public class Main extends Interface{
    public static void main(String[] args) {
        //PREMIERS TESTS
        /*//Aeroport aeroport = new Aeroport("France","Roissy","CDG",49.00,2.51);
        //System.out.println(aeroport.toString());
        World world = new World("./data/airport-codes_no_comma.csv");
        //System.out.println(world.list.toString());

        //avec les coordonnées de Paris 48.866, 2.316
        Aeroport nearestAirport = world.findNearestAirport(48.866,2.316);
        System.out.println("L'aéroport le plus proche est : \n"+nearestAirport.toString());

        //IATA de Paris Orly
        world.findByCode("ORY");*/

        //THREADS//
        Interface interface1 = new Interface();
        for(Thread thread :interface1.getThreadArrayList()){
            thread.start();
            try{
                thread.join();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}