import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;

public class Earth extends Group {
    //Attribs
    public PhongMaterial phongMaterial = new PhongMaterial();
    public Sphere earthSphere = new Sphere(300);
    public Sphere aeroportSphere = null;
    public Sphere flightSphere = null;
    public Rotate rotateY = new Rotate();

    private ArrayList<Thread> threadArrayList = new ArrayList<>();

    public ArrayList<Thread> getThreadArrayList() {
        return threadArrayList;
    }

    //constructor
    public Earth() {
        phongMaterial.setDiffuseMap(new Image("D:\\srobinson\\1.SCHOOL\\JAVA_COURS\\TP\\TP_JAVA_221215\\data\\earth_lights_4800.png"));
        earthSphere.setMaterial(phongMaterial);
        this.getChildren().add(earthSphere);
        this.getTransforms().add(rotateY);

        Thread thread = new Thread(){
            public void run(){
                AnimationTimer animationTimer = new AnimationTimer() {
                    @Override
                    public void handle(long time) {
                        //System.out.println("Valeur de time : " + time);
                        rotateY.setAxis(new Point3D(0,1,0)); //définition de l'axe : ici c'est l'axe Y
                        double angle = time*24*Math.pow(10,-9); //pour un tour en 15s
                        //System.out.println("Angle : "+angle);
                        rotateY.setAngle(angle); //degree
                    }
                };
                animationTimer.start();
            }
        };
        thread.start();
        threadArrayList.add(thread);

    }

    public Sphere createAeroportSphere(Aeroport aeroport, Color color){
        if (aeroport==null){return null;}
        //Création d'une sphere
        aeroportSphere = new Sphere(2);
        //Application de la couleur via phongMaterial
        PhongMaterial phongMaterialColor = new PhongMaterial();

        //lat et long pour rotation de la sphere sur X et Y
        double latitude = Math.toRadians(aeroport.getLatitude()-13); //theta
        double longitude = Math.toRadians(aeroport.getLongitude()); //phi

        //application du material
        phongMaterialColor.setDiffuseColor(color);
        aeroportSphere.setMaterial(phongMaterialColor);

        //translation sur les axes X, Y et Z
        Translate translate = new Translate();
        double x = 300*Math.cos(latitude)*Math.sin(longitude);
        double y = -300*Math.sin(latitude);
        double z = -300*Math.cos(latitude)*Math.cos(longitude);
        translate.setZ(z);
        translate.setY(y);
        translate.setX(x);

        //application des translations et rotations
        aeroportSphere.getTransforms().add(translate);

        return aeroportSphere;
    }

    public void displayRedSphere(Aeroport aeroport){
        this.getChildren().add(createAeroportSphere(aeroport, Color.RED));
    }

    public void displayYellowSphere(ArrayList<Flight> flightList){
        for(Flight flight : flightList){
            if(flight!=null){
                if(flight.getAeroportDeparture()!=null){
                    this.getChildren().add(createAeroportSphere(flight.getAeroportDeparture(),Color.YELLOW));
                }
            }
        }
    }

}
