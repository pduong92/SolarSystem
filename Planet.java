import static java.lang.Integer.parseInt;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class Planet {
    
    private Circle orbit;
    private Sphere planetBody;
    private int speed;
    private double radius;
    private final double SIZE = 500; // just a standardized reference
    
    private Animation animation;
    
    private String name;
    private String colorStr;
    private String sizeStr;
    private String speedStr;
    private String orbitStr;
    
    public Planet (String sizeInput, String orbitInput, String colorInput, String speedInput, String nameInput){
    	
    	this.name = nameInput;
    	this.colorStr = colorInput;
    	this.sizeStr = sizeInput;
    	this.speedStr = speedInput;
    	this.orbitStr = orbitInput;
    	
    	int radius;
        switch (sizeInput){
            case "Small": radius = 15;
                break;
            case "Medium": radius = 30;
                break;
            case "Large": radius = 45;
                break;
            default:
            	radius = Integer.parseInt(sizeInput);
        }
        
        Color color = Color.YELLOW;
        switch (colorInput){
            case "RED" : color = Color.RED;
                break;
            case "BLUE" : color = Color.BLUE;
                break;
            case "GREEN" : color = Color.GREEN;
                break;
            case "ORANGE" : color = Color.ORANGE;
                break;
            case "PURPLE" : color = Color.PURPLE;
                break;
            case "YELLOW" : color = Color.YELLOW;

        }
            
        int speed;
        switch (speedInput){
            case "Slow" : speed = 9;
                break;
            case "Medium" : speed = 7;
                break;
            case "Fast" : speed = 5;
                break;
            default:
            	speed = 10;
        }
                
        double orbitData = (Double.parseDouble(orbitInput))*0.5;
    	
    	
    	
        this.orbit = createCircle(orbitData * SIZE);
        planetBody = new Sphere(radius);
        this.radius = radius;
        planetBody.setMaterial(new PhongMaterial(color));
        this.speed = speed;
        
        animation = new ParallelTransition(
            createTransition(this.orbit, this.planetBody, Duration.seconds(speed)));
    }
 
    
    // Creates orbital path
    private Circle createCircle(double orbitSize) {
            Circle c = new Circle(orbitSize / 2);
            c.setFill(Color.TRANSPARENT);
            c.setStroke(Color.TRANSPARENT);
            return c;
    }
    
    // Defines planetary orbit
    private Transition createTransition(Shape path, Shape3D node, Duration duration) {
            PathTransition t = new PathTransition(duration, path, node);
            t.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            t.setCycleCount(Timeline.INDEFINITE);
            t.setInterpolator(Interpolator.LINEAR);
            return t;
        }
    
    public Circle getOrbit(){ return orbit; }
    
    public Sphere getPlanetBody(){ return planetBody; }
    
    public int getSpeed(){ return speed; }
    
    public double getRadius() {return radius; }
    
    public String getName() {return name;}
    
    public String getColorStr() { return colorStr;}
    
    public String getSizeStr() { return sizeStr;}
    
    public String getSpeedStr() { return speedStr;}
    
    public String getOrbitStr() { return orbitStr;}
    
    public Animation getAnimation() { return animation; }
    
}