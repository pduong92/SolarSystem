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

import javafx.scene.SubScene;
import javafx.scene.Group;

public class SolarSystemGUI extends Application{
	
    private String[] sizes = {"Small", "Medium", "Large"};
    private String[] colors = {"RED", "BLUE", "GREEN", "ORANGE", "PURPLE"};
    private String[] speeds = {"Slow", "Medium", "Fast"};
    private String[] positions = {"1", "2", "3", "4", "5"};
	
    //Variable for our margin spacing
    private Insets spacing = new Insets(10, 50, 10, 50);
    
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
    
    /***Variables for out Right Pane***/
    //Grid for our Data Table
    private GridPane dataTable = new GridPane();
    
    //Array to store labels/values for the data table
    private Label[][] data = new Label[5][5];
    
    //Column Constraint for the data table
    ColumnConstraints colSpacing = new ColumnConstraints(75);
    
    //private SolarSystem sol = new SolarSystem(750,500);
    private SolarSystem sol = new SolarSystem();
    
    private SubScene GUIscene;
    
    
    //Method to Display all planet data on the table grid
    public void displayDataTable() {
    	int count = 0;
    	Planet index;
    	Planet[] planets = sol.getPlanets();
    	for(int i = 1; i < planets.length; i++)
    	{
    		index = planets[i];
    		if(index != null)
    		{
    			data[count][0].setText(index.getName());
    			data[count][1].setText(index.getColorStr());
    			data[count][2].setText(index.getSizeStr());
    			data[count][3].setText(index.getSpeedStr());
    			data[count][4].setText(index.getOrbitStr()); 
    			
    			count++;
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
  		
  		dataTable.getColumnConstraints().addAll(colSpacing, colSpacing, colSpacing, colSpacing, colSpacing);
  		
  		for(int i = 0; i < data.length; i++)
  		{
  			for(int j = 0; j < data.length; j++)
  			{
  				data[i][j] = new Label();
  				dataTable.add(data[i][j], j, i+1);
  			}
  		}
  			
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
//	FlowPane leftPane = createLeftPane(); 
//	FlowPane rightPane = createRightPane();
	
	HBox bottomPane = new HBox();
	bottomPane.getChildren().addAll(createLeftPane(), createRightPane());
	
	GUIscene = new SubScene(bottomPane, 1200, 200);
	
	BorderPane fullPane = new BorderPane();
	fullPane.setCenter(sol.getSubScene());
	fullPane.setBottom(GUIscene);
	
        
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