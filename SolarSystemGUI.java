
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
import javafx.scene.text.Text;

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

import javafx.scene.media.AudioClip; // music

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
    
    //Text for error message
    private Text errText = new Text();
    
    /***Variables for out Right Pane***/
    //Grid for our Data Table
    private GridPane dataTable = new GridPane();
    
    //Array to store labels/values for the data table
    private Label[][] data = new Label[5][5];
    
    //Column Constraint for the data table
    ColumnConstraints colSpacing = new ColumnConstraints(75);
    
    
    private SolarSystem sol = new SolarSystem();
    private SubScene GUIscene;
    
    //Method to validate all fields, this includes the methods to validated completeness and orbital position
    public boolean validateFields(String size, String pos, String color, String speed, String name) {
    	boolean validated = false;
    	if(incompleteFields(size, pos, color, speed, name)) {
    		if(indexIsEmpty(pos))
    			validated = true;
    		else
    			duplicateErr();
    	}
    	else
    		incompleteErr();
    	
    	return validated;		
    }
    
    //Method to set the error label with the incomplete message statement
    public void incompleteErr() {
    	errText.setText("Please complete all fields.");
    	errText.setFill(Color.RED);
    }
    //Method to check if there exists incomplete fields
    public boolean incompleteFields(String size, String pos, String color, String speed, String name) {
    	boolean valid = true;
    	String trimName = name.trim();
    	if(trimName.equals("") || trimName.equals("Enter Planet Name"))
    		valid = false;
    	else if(size.equals("Select the size"))
    		valid = false;
    	else if(color.equals("Select the color"))
    		valid = false;
    	else if(speed.equals("Select the speed"))
    		valid = false;
    	else if(pos.equals("Select the position"))
    		valid = false;
    	
    	return valid;
    }
    //Method to set error label with the duplicate message statement
    public void duplicateErr() {
    	errText.setText("Existing planet on selected orbital position.\nPlease select another orbital position.");
    	errText.setFill(Color.RED);
    }
    //Method to check if there already exists a planet at the selected orbital position
    public boolean indexIsEmpty(String pos) {
    	int index = Integer.parseInt(pos);
    	Planet[] planets = sol.getPlanets();
    	if(planets[index] == null)
    		return true;
    	else
    		return false;
    }
    //Method to reset error label, and all other user input fields back to default
    public void Reset() {
    	//errLabel.setText("");
    	errText.setText("");
    	tfName.setText("Enter Planet Name");
    	cbSize.setValue("Select the size");
    	cbColor.setValue("Select the color");
    	cbSpeed.setValue("Select the speed");
    	cbPos.setValue("Select the position");
    }
    
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
        cbSize.setValue("Select the size");
	
        ObservableList<String> colorItems = FXCollections.observableArrayList(colors);
        cbColor.getItems().addAll(colorItems);
        cbColor.setValue("Select the color");
	
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
        
        creationPane.getChildren().addAll(lbName, attributes, botContent, errText);
        creationPane.setMargin(lbName, spacing);
        creationPane.setMargin(attributes, spacing);
        creationPane.setMargin(botContent, spacing);
        creationPane.setMargin(errText, spacing);
	
        return creationPane;
    }
	
	
    public void start(Stage primaryStage) {
	
	HBox bottomPane = new HBox();
	bottomPane.getChildren().addAll(createLeftPane(), createRightPane());
	
	GUIscene = new SubScene(bottomPane, 1200, 200);
	
	BorderPane fullPane = new BorderPane();
	fullPane.setCenter(sol.getSubScene());
	fullPane.setBottom(GUIscene);
	
        
        // revamped the action listener to talk between GUI and SolarSystem
	btnCreate.setOnAction((e) -> {
		
        if(validateFields(cbSize.getValue(), cbPos.getValue(), cbColor.getValue(), cbSpeed.getValue(), tfName.getText())) {
        	sol.createPlanet(cbSize.getValue(), cbPos.getValue(), cbColor.getValue(), cbSpeed.getValue(), tfName.getText() ); //creates planet
        	displayDataTable();
        	Reset();
        }
	});
		
    Scene scene = new Scene(fullPane, Color.WHITE);
	primaryStage.setTitle("Build Your Own Solar System");
	primaryStage.setScene(scene);
	primaryStage.show();
	
	// music
    String musicFile = this.getClass().getResource("space.m4a").toExternalForm();
    AudioClip music = new AudioClip(musicFile);
    music.play();   
	
    }
	
    public static void main(String[] args) {
	Application.launch(args);
    }
}