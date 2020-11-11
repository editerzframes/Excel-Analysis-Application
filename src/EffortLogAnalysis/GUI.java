package EffortLogAnalysis;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GUI {
	
	
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	/* Constants used to parameterize the graphical user interface.  We do not use a layout manager for
	   this application. Rather we manually control the location of each graphical element for exact
	   control of the look and feel. */
	private final double BUTTON_WIDTH = 60;

	// These are the application values required by the user interface
	private Label label_ApplicationTitle = new Label("Effort Log Merge and Analysis");
	private Label label_Operand1 = new Label("Source Directory");
	private TextField text_directory = new TextField();
	
	private Label label_size = new Label();
	
	private Button button_display= new Button("Display Report");
	private Button button_Go= new Button("Browse");
	private Button button_report= new Button("Generate Analysis Report");
	private Button button_exit= new Button("Exit");
	   List<File> fileList;
	   File file;
	   ListView<String> listView;
	   ObservableList<String> list;
	protected Window theStage;
	int size = 0;
	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the graphical user interface. These assignments
	 * determine the location, size, font, color, and change and event handlers for each GUI object.
	 */
	public GUI(Pane theRoot) {
		
		 listView = new ListView<>();
	       list =FXCollections.observableArrayList ();
	       final Label labelFile = new Label();
	       
	       
		// Label theScene with the name of the calculator, centered at the top of the pane
		setupLabelUI(label_ApplicationTitle, "Arial", 24, SummaryDisplay.WINDOW_WIDTH, Pos.CENTER, 0, 10);
		
		// Label the first operand just above it, left aligned
		setupLabelUI(label_Operand1, "Arial", 18, SummaryDisplay.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 10, 75);
		

		// Label the first operand just above it, left aligned
		setupLabelUI(label_size, "Arial", 18, SummaryDisplay.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 550, 128);
		
		
		// Establish the first text input operand field and when anything changes in operand 1 measured value,
		// measured value process all fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_directory, "Arial", 18, SummaryDisplay.WINDOW_WIDTH/2 
				+ 30, Pos.BASELINE_LEFT, 170, 70, true);
		text_directory.textProperty().addListener((observable, oldValue, newValue) 
				-> {setOperand1MeasuredValue(); });
		

		// Label theScene with the name of the calculator, centered at the top of the pane
				setupLabelUI(listView,   SummaryDisplay.WINDOW_WIDTH/3	+ 50, SummaryDisplay.WINDOW_WIDTH/3	+ 50, 150, 125);
				
		
		// Establish the ADD "+" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Go, "Arial", 16, BUTTON_WIDTH, Pos.BASELINE_LEFT, 
						SummaryDisplay.WINDOW_WIDTH/1.2-BUTTON_WIDTH/2, 70);
	
		button_Go.setOnAction(new EventHandler<ActionEvent>()
				{
			public void handle(ActionEvent arg0)
			{
				 FileChooser fileChooser = new FileChooser();
	               
	               //Open directory from existing directory
	               if( fileList != null ){
	                   if( !fileList.isEmpty() ){
	                       File existDirectory = fileList.get(0).getParentFile();
	                       fileChooser.setInitialDirectory(existDirectory);
	                       text_directory.setText(existDirectory.getPath());
	                   }
	               }
	              
	               //Set extension filter
	               FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Xlsx files (*.xlsx)", "*.xlsx");
	               fileChooser.getExtensionFilters().add(extFilter);
	               
	               //Show open file dialog, with primaryStage blocked.
	               
	               fileList = fileChooser.showOpenMultipleDialog(theStage);
	               
	               list.clear();
	               for(int i=0; i<fileList.size(); i++){
	                   list.add(fileList.get(i).getName());
	                  
	               }
	              
	               listView.setItems(list);
	               
	               listView.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>()
	   		    {
	   		    	public ObservableValue<Boolean> call(String item)
	   		    	{
	   		    		BooleanProperty observable = new SimpleBooleanProperty();
	   		    		observable.addListener((obs, wasSelected, isNowSelected) ->
	   		    		System.out.println("Check box for " + item + "changed from " +wasSelected + " to " + isNowSelected)
	   		    		
	   		    	);
	   		    		return observable;
	   		    		
	   		    	}
	   		    }));
	   		
	           }
				});
		VBox vbox = VBoxBuilder.create().children(button_Go).build();
		theRoot.getChildren().add(vbox);
		  vbox.getChildren().addAll(labelFile);
		  vbox.getChildren().add(listView);
		  
		 
		
		// Establish the ADD "+" button, position it, and link it to methods to accomplish its work
				setupButtonUI(button_report, "Arial", 16, BUTTON_WIDTH, Pos.BASELINE_LEFT, 
								SummaryDisplay.WINDOW_WIDTH/9-BUTTON_WIDTH/2, 500);
				button_report.setOnAction((event) -> { performGo(); });
				

				// Establish the ADD "+" button, position it, and link it to methods to accomplish its work
						setupButtonUI(button_display, "Arial", 16, BUTTON_WIDTH, Pos.BASELINE_LEFT, 
										SummaryDisplay.WINDOW_WIDTH/2-BUTTON_WIDTH/2, 500);
						button_display.setDisable(true);
						button_display.setOnAction((event) -> { displayReport(); });
						
						
				// Establish the ADD "+" button, position it, and link it to methods to accomplish its work
				setupButtonUI(button_exit, "Arial", 16, BUTTON_WIDTH, Pos.BASELINE_LEFT, 
								SummaryDisplay.WINDOW_WIDTH/1.1-BUTTON_WIDTH/2, 500);
				button_exit.setOnAction((event) -> { System.exit(0); });
				
				
				
			
		// Place all of the just-initialized GUI elements into the pane
		theRoot.getChildren().addAll(label_ApplicationTitle, label_Operand1, text_directory, 
				button_Go, button_report, button_exit, listView, button_display, label_size);

	}




	private void setupLabelUI(ListView<String> listView2,double et, double w, double i, double j) {

		listView2.setMaxHeight(et);
		listView2.setMinWidth(w);
		listView2.setMaxWidth(w);
		listView2.setLayoutX(i);
		listView2.setLayoutY(j);
	}


	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}
	
	/**********
	 * Private local method to initialize the standard fields for a button
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	

	private void setOperand1MeasuredValue() {
		performGo();
	}
	
	private void performGo() {
	
String effortlog[] = new String[14];
		
		for ( File listView:fileList) {
			
							effortlog[size] = ( listView).getName();
				size++;
			}
	label_size.setText("Number of files selected:" + size);
		new Merge(effortlog, size);
		button_display.setDisable(false);
		
			}
	private void displayReport() {
	
	  
	      File xlfile = new File("EffortLogData.xlsx");
	      if (xlfile.exists())
	      {
	       if (Desktop.isDesktopSupported())
	       {
	        try
	        {
	         Desktop.getDesktop().open(xlfile);
	        }
	        catch (IOException e)
	        {
	        
	         e.printStackTrace();
	        }
	       }
	       else
	        {
	         System.out.println("Not Supported!");
	        }
	      }
	      
	      else
	      {
	       System.out.println("File is not exists!");
	      }
	    
	     }
	class FileList {
		private final SimpleBooleanProperty selected;
		private final SimpleStringProperty name;

		public FileList(boolean id, String name) {
			this.selected = new SimpleBooleanProperty(id);
			this.name = new SimpleStringProperty(name);
		}

		public boolean getSelected() {
			return selected.get();
		}

		public void setSelected(boolean selected) {
			this.selected.set(selected);
		}

		public String getName() {
			return name.get();
		}

		public void setName(String fName) {
			name.set(fName);
		}

		public SimpleBooleanProperty selectedProperty() {
			return selected;
		}

		@Override
		public String toString() {
			return getName();
		}
	}
}
		
	
	
	
	
	
