import static java.lang.Integer.parseInt;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import javafx.scene.paint.Color;
import javafx.geometry.Insets;



public class SolarSystemGUI extends Application{
	
    private String[] sizes = {"Small", "Medium", "Large"};
    private String[] colors = {"RED", "BLUE", "GREEN", "ORANGE", "PURPLE"};
    private String[] speeds = {"Slow", "Medium", "Fast"};
    private String[] positions = {"1", "2", "3", "4", "5"};
	
    
    /****Variables for out Left Pane***/
    //Text Field for user to input Planet title
    private TextField tfName = new TextField("Enter Planet Name");
    //Drop-down Boxes
    private ComboBox<String> cbSize = new ComboBox<>();
    private ComboBox<String> cbColor = new ComboBox<>();
    private ComboBox<String> cbSpeed = new ComboBox<>();
    private ComboBox<String> cbPos = new ComboBox<>();
    //Button to Create a Planet
    private Button btnCreate = new Button("Create Planet");
    
    //Grid for our Data Table
    private GridPane dataTable = new GridPane();
    
    //Variable for our margin spacing
    private Insets spacing = new Insets(10, 50, 10, 50);
    
    private SolarSystem sol = new SolarSystem(750,500);

    //Method to Display all planet data on the table grid
    public void displayDataTable() {
    	Planet index;
    	for(int i = 1; i < 6; i++)
    	{
    		index = sol.planets[i];
    		System.out.println(index);
    		if(index != null)
    		{
    			dataTable.add(new Label(index.getName()), 0, i);
    			dataTable.add(new Label(index.getColorStr()), 1, i);
    			dataTable.add(new Label(index.getSizeStr()), 2, i);
    			dataTable.add(new Label(index.getSpeedStr()), 3, i);
    			dataTable.add(new Label(index.getOrbitStr()), 4, i);  			
    		}
    	}
    }
 
    //Method to Create the RightPane (Data Table)
  	private FlowPane createRightPane() {
  		FlowPane dataPane = new FlowPane();
  		dataPane.setAlignment(Pos.BASELINE_LEFT);
  		dataPane.setOrientation(Orientation.VERTICAL);
  		
  		Label lblHead = new Label("My Planets");
  	
  		dataTable.add(new Label("Name"), 0, 0);
  		dataTable.add(new Label("Color"), 1, 0);
  		dataTable.add(new Label("Size"), 2, 0);
  		dataTable.add(new Label("Speed"), 3, 0);
  		dataTable.add(new Label("Position"), 4, 0);
  		
  		ColumnConstraints colSpacing = new ColumnConstraints(75);
  		
  		dataTable.getColumnConstraints().addAll(colSpacing, colSpacing, colSpacing, colSpacing, colSpacing);
  	
  			
  		dataPane.getChildren().addAll(lblHead, dataTable);
  		dataPane.setMargin(lblHead, spacing);
  		dataPane.setMargin(dataTable, spacing);
  		
  		return dataPane;
  	}
    
    //Method to create the LeftPane (Data Pane)
    private FlowPane createLeftPane() {
        FlowPane creationPane = new FlowPane();
        creationPane.setAlignment(Pos.BASELINE_LEFT);
        creationPane.setOrientation(Orientation.VERTICAL);
	
        tfName.setEditable(true);
        tfName.setAlignment(Pos.BASELINE_LEFT);
        
        Label lbName = new Label("Add a New Planet", tfName);
        lbName.setContentDisplay(ContentDisplay.RIGHT);
	
        ObservableList<String> sizeItems = FXCollections.observableArrayList(sizes);
        cbSize.getItems().addAll(sizeItems);
        cbSize.setValue("Select a size");
	
        ObservableList<String> colorItems = FXCollections.observableArrayList(colors);
        cbColor.getItems().addAll(colorItems);
        cbColor.setValue("Select a color");
	
        ObservableList<String> speedItems = FXCollections.observableArrayList(speeds);
        cbSpeed.getItems().addAll(speedItems);
        cbSpeed.setValue("Select the speed");
	
        ObservableList<String> posItems = FXCollections.observableArrayList(positions);
        cbPos.getItems().addAll(posItems);
        cbPos.setValue("Select the position");
	
        Label rule1 = new Label("All fields are required");
        Label rule2 = new Label("The maximum numbers of planets allowed is 5.");
	
        HBox attributes = new HBox();
        attributes.setSpacing(5);
        attributes.setHgrow(cbSize, Priority.ALWAYS);
        attributes.setHgrow(cbColor, Priority.ALWAYS);
        attributes.setHgrow(cbSpeed, Priority.ALWAYS);
        attributes.setHgrow(cbPos, Priority.ALWAYS);
        attributes.getChildren().addAll(cbSize, cbColor, cbSpeed, cbPos);
	
        VBox rules = new VBox();
        rules.getChildren().addAll(rule1, rule2);
	
        HBox botContent = new HBox();
        botContent.setSpacing(75);
        botContent.getChildren().addAll(rules, btnCreate);
        
        creationPane.getChildren().addAll(lbName, attributes, botContent);
        creationPane.setMargin(lbName, spacing);
        creationPane.setMargin(attributes, spacing);
        creationPane.setMargin(botContent, spacing);
	
        return creationPane;
    }
	
	
    public void start(Stage primaryStage) {
	FlowPane leftPane = createLeftPane(); 
	FlowPane rightPane = createRightPane();
	
	HBox bottomPane = new HBox();
	bottomPane.getChildren().addAll(leftPane, rightPane);
	
	//VBox fullPane = new VBox();
	
        
        /* wound up trying to add solarsystem and leftPane to both
         fullPane, and a group I created, but couldn't figure out
         how to control layout  */
        //fullPane.getChildren().addAll(sol.pane, bottomPane);
	
	BorderPane fullPane = new BorderPane();
	fullPane.setCenter(sol.pane);
	fullPane.setBottom(bottomPane);
	//fullPane.setAlignment(bottomPane, Pos.BOTTOM_CENTER);
	
        
        // revamped the action listener to talk between GUI and SolarSystem
	btnCreate.setOnAction((e) -> {           
            
            sol.createPlanet(cbSize.getValue(), cbPos.getValue(), cbColor.getValue(), cbSpeed.getValue(), tfName.getText() ); //creates planet
          //  sol.planets[Integer.parseInt(cbPos.getValue())].animation.play(); //starts animation
            
            displayDataTable();
	});
		
    Scene scene = new Scene(fullPane, Color.WHITE);
	primaryStage.setTitle("User Interface");
	primaryStage.setScene(scene);
	primaryStage.show();
    }
	
    public static void main(String[] args) {
	Application.launch(args);
    }
}