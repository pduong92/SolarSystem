/* This is the main class for the Solar System itself
* it holds and array of planets, with the sun at index 0.
* up to five more planets can be created.
*
* this class is instantiated by the SolarSytemGUI.
* run that file to control the others.
*/

import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;


public class SolarSystem {

    private final double SIZE = 300;
    public Pane pane;
    private Rotate rx = new Rotate(0, Rotate.X_AXIS);
    private Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    public Planet[] planets = new Planet[6];  // holds 5 planets at index 1-5, and Sun at index 0.
    
    //SolarSystem constructor. Automatically creates Sun with radius of 25
    public SolarSystem (double h, double w){
    	pane = new Pane();
    	pane.setPrefSize(h, w);
   // 	pane.getTransforms().addAll(rx,ry);
   // 	rx.setAngle(75);
   // 	ry.setAngle(85);
        createPlanet("25", "0", "YELLOW", "0", "Sun");        
    }
    
    //createPlanet method called by Actionlistener in GUI.
    public void createPlanet(String radius, String orbit, String color, String speed, String name){
    	
    	int index = Integer.parseInt(orbit);
    	//creates planet in planets array at index of 'orbit'
        planets[index] = new Planet(radius, orbit, color, speed, name);
        
        if(index == 0)
        {  	
        	//planets[index].getPlanetBody().setTranslateX(pane.widthProperty().divide(2).getValue());
        	//planets[index].getPlanetBody().setTranslateY(pane.heightProperty().divide(2).getValue());
        	
        	planets[index].getPlanetBody().setTranslateX(539.5);
        	planets[index].getPlanetBody().setTranslateY(210.5);
        }
        else
        {
        	planets[index].getOrbit().centerXProperty().bind(pane.widthProperty().divide(2));
        	planets[index].getOrbit().centerYProperty().bind(pane.heightProperty().divide(2));
        }
        //System.out.println(pane.widthProperty().divide(2).getValue());
        //System.out.println(pane.heightProperty().divide(2).getValue());
        
        // adds planet to SolarSystem's pane.
        pane.getChildren().addAll(planets[index].getPlanetBody(), planets[index].getOrbit());
        //pane.getChildren().addAll(planets[orbit].getPlanetBody(), currentOrbit);
        
        if(index != 0)
        	planets[index].animation.play(); //starts animation
    }
    
    public Pane getPane() { return pane; }
    
}
