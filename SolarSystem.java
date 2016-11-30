/* This is the main class for the Solar System itself
* it holds and array of planets, with the sun at index 0.
* up to five more planets can be created.
*
* this class is instantiated by the SolarSytemGUI.
* run that file to control the others.
*/

import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;

import javafx.scene.SubScene;
import javafx.scene.Group;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;

public class SolarSystem {

    private final double SIZE = 300;
    
    private SubScene subscene;
    private Group g;
    
    private Rotate rx = new Rotate(0, Rotate.X_AXIS);
    private Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    private Planet[] planets = new Planet[6];  // holds 5 planets at index 1-5, and Sun at index 0.
    
    //SolarSystem constructor. Automatically creates Sun with radius of 25
    public SolarSystem (){
    	g = new Group();
    	g.getTransforms().addAll(rx, ry);
    	subscene = new SubScene(g, 1200, 500, true, SceneAntialiasing.BALANCED);
    	subscene.setFill(Color.BLACK);

    	rx.setAngle(90);
    	ry.setAngle(25);
    	
    	PerspectiveCamera camera = new PerspectiveCamera();
        camera.setFarClip(1000);
        camera.setTranslateZ(-250);
        subscene.setCamera(camera);
    	
        createPlanet("80", "0", "YELLOW", "0", "Sun");
        
        subscene.setStyle("-fx-background-image: url('" + "milky-way-galaxy.jpg" + "'); " +
                   "-fx-background-position: center center; " +
                   "-fx-background-repeat: stretch;");
        
     //   Image bg = new Image("milky-way-galaxy.jpg");
     //   pane.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, null, null)));
    }
    
    //CreatePlanet method called by Actionlistener in GUI.
    public void createPlanet(String radius, String orbit, String color, String speed, String name){
    	
    	int index = Integer.parseInt(orbit);
    	//Creates planet in planets array at index of 'orbit'
        planets[index] = new Planet(radius, orbit, color, speed, name);
        
        if(index == 0)
        {  	
        	planets[index].getPlanetBody().setTranslateX(subscene.widthProperty().divide(2).getValue());
        	planets[index].getPlanetBody().setTranslateY(subscene.heightProperty().divide(2).getValue());
        }
        else
        {
        	planets[index].getOrbit().centerXProperty().bind(subscene.widthProperty().divide(2));
        	planets[index].getOrbit().centerYProperty().bind(subscene.heightProperty().divide(2));
        }
        
        //Adds planet to SolarSystem's pane.
        g.getChildren().addAll(planets[index].getPlanetBody(), planets[index].getOrbit());
        
        if(index != 0)
        	planets[index].getAnimation().play(); //Starts animation
    }
    
    public SubScene getSubScene() { return subscene; }
    
    public Planet[] getPlanets() { return planets; } 
}
