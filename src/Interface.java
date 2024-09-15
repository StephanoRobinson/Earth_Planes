import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Interface extends Application {

    Translate translate = new Translate();
    private double mousePosX;
    private double mousePosY;
    private ArrayList<Thread> threadArrayList = new ArrayList<>();

    public ArrayList<Thread> getThreadArrayList() {
        return threadArrayList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello world");
        World world = new World("./data/airport-codes_no_comma.csv");
        //Group root = new Group();
        Earth earth = new Earth();
        //Pane pane = new Pane(root); //old : root
        Scene ihm = new Scene(earth, 600, 400,true);

        //Camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        //positions initales
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1); //proche de la sphere
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);
        ihm.setCamera(camera);

        for(Thread thread : earth.getThreadArrayList()){
            try{thread.join();}catch (Exception e){e.printStackTrace();}
        }

        Thread thread1 = new Thread(() -> {
            //1
            ihm.addEventHandler(MouseEvent.ANY, event -> {
                //Si clic avec la souris, obtenir la position (x,y) du clic
                if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    mousePosX = event.getSceneX();
                    mousePosY = event.getSceneY();
                    System.out.println("Clicked on : ("+ mousePosX+ ", "+ mousePosY+")");
                }
                //si maintien du clic, translation sur l'axe Z
                if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    translate.setZ((event.getSceneY()-mousePosY)*0.01); //*0.01 afin de ralentir la cadence de zoom et d'avoir plus de pas
                    camera.getTransforms().add(translate);
                }
            });

            //2
            //Obtenir les coordonnée (x,y) sur la map
            ihm.addEventHandler(MouseEvent.ANY, event -> {
                //Si clic droit avec la souris
                if (event.getButton()== MouseButton.SECONDARY && event.getEventType()==MouseEvent.MOUSE_CLICKED) {
                    PickResult pickResult = event.getPickResult(); //permet de récupérer le point 2D du clic de la souris
                    if(pickResult.getIntersectedNode() != null) {
                        /*Aeroports*/
                        //point d'intersection
                        Point2D point2D = pickResult.getIntersectedTexCoord();
                        //Conversion en long, lat
                        //atan en radian -> degree
                        double latitude=2*Math.toDegrees(Math.atan(Math.exp((0.5-point2D.getY())/0.2678))-(Math.PI/4));
                        double longitude = 360*(point2D.getX()-0.5);
                        //nearest
                        Aeroport nearestAeroport = world.findNearestAirport(latitude,longitude);
                        earth.displayRedSphere(nearestAeroport);
                        System.out.println("\nPosition : ("+longitude+", "+latitude+")");
                        System.out.println("Aeroport le plus proche : \n"+nearestAeroport.toString());

                    }
                }
            });
        });
        thread1.start();
        threadArrayList.add(thread1);

        Thread thread2 = new Thread(() -> ihm.addEventHandler(MouseEvent.ANY, event -> {
            if(event.getButton()==MouseButton.SECONDARY && event.getEventType()==MouseEvent.MOUSE_CLICKED){
                /*Flights*/
                try{
                    //GET from aviationstack.com, result in String
                    String url = "http://api.aviationstack.com/v1/flights?access_key=3bd5a187954f6a2bb79e1a32387c0283";
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response);

                    //convert to json and display yellow sphere
                    JsonFlightFiller jsonFlightFiller = new JsonFlightFiller(response.body(), world);
                    jsonFlightFiller.displayFlight();
                    earth.displayYellowSphere(jsonFlightFiller.getFlightList());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }));
        thread2.start();
        threadArrayList.add(thread2);


        primaryStage.setScene(ihm);
        primaryStage.show();

    }
    public static void main(String[] args) {

        Interface interface1 = new Interface();
        for(Thread thread : interface1.getThreadArrayList()){
            thread.start();
            try{
                thread.join();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        launch(args);
    }
}