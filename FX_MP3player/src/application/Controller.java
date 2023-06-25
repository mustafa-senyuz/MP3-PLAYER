package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

  

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
 
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Controller implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mp3Player;

    @FXML
    private Label songName;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button playButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private ComboBox<String> speedComboBox;

    @FXML
    private Slider volumeSider;
    
    private Media media;
    private MediaPlayer player;    
    
    private File directory;
    private File[] files;
    
    private ArrayList<File> songs;
    private int songNumber;
    private int[] speed = {25 , 50 , 75 , 100 , 125 , 150 , 175 , 200};

    private Timer t;
    private TimerTask task;
    private boolean running;
    
    
    
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		 
    	songs = new ArrayList<File>();
    	directory = new File("music");
    	files = directory.listFiles();
    	
    	if (files !=null) {
    		
    		for (File file : files) {
				songs.add(file);
				System.out.println(file);
			}
			
		}
    	
    	media = new Media(songs.get(songNumber).toURI().toString());
    	player = new MediaPlayer(media);
    	
    	songName.setText(songs.get(songNumber).getName());
		
    	for (int i = 0; i < speed.length; i++) {
			speedComboBox.getItems().addAll(Integer.toString(speed[i])+"%");
		}
    	
    	volumeSider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
			 
				 player.setVolume(volumeSider.getValue()*0.01);
				
			}
		});
    	
    	
    	progressBar.setStyle("-fx-accent: #00FF00;");
    	
	}
  
    public void ChangeSpeed( ) {
//    		player.setRate(Integer.parseInt(speedComboBox.getValue())*0.01);
    	
    		if (speedComboBox.getValue() == null) {
				player.setRate(1);
			}
    		else {
    		player.setRate(Integer.parseInt(speedComboBox.getValue().substring(0 , speedComboBox.getValue().length()-1))*0.01);
    			}
    		}

   
    public void PauseSong( ) {
    		
    	    cancelTimer() ;
    		player.pause();
    }

 
    public void PlaySong( ) {
    		
    	    beginTimer();
    		ChangeSpeed();
    		player.setVolume(volumeSider.getValue()*0.01);
    		player.play();
    }

 
    public void PlaynextSong( ) {
    			if (songNumber<songs.size()-1) {
					
    				songNumber++;
    				player.stop();
    				
    				if (running) {
    					cancelTimer();
    				}
    				
    				media = new Media(songs.get(songNumber).toURI().toString());
    		    	player = new MediaPlayer(media);
    		    	
    		    	songName.setText(songs.get(songNumber).getName());
    		    	
    		    	PlaySong();   				
    				
				} else {
					songNumber = 0;
    				player.stop();
    				
    				media = new Media(songs.get(songNumber).toURI().toString());
    		    	player = new MediaPlayer(media);
    		    	
    		    	songName.setText(songs.get(songNumber).getName());
    		    	
    		    	PlaySong(); 
				}		
    } 

    
    public void PreviousSong(   ) { 
    	
    	if (songNumber > 0) {
			
			songNumber-- ;
			player.stop();
			
			if (running) {
				cancelTimer();
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
	    	player = new MediaPlayer(media);
	    	
	    	songName.setText(songs.get(songNumber).getName());
	    	
	    	PlaySong();   				
			
		} else {
			songNumber = songs.size()-1;
			player.stop();
			
			if (running) {
				cancelTimer();
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
	    	player = new MediaPlayer(media);
	    	
	    	songName.setText(songs.get(songNumber).getName());
	    	
	    	PlaySong(); 
		}

    }

    
    		public void ResetSong(   ) {
    			progressBar.setProgress(0);
    			player.seek(Duration.millis(0.00));
    		}
    
    public void beginTimer() {
    	t = new Timer();
    	
    	task = new TimerTask() {
			
			@Override
			public void run() {
				 
				running = true;
				double current = player.getCurrentTime().toSeconds();
				double end = media.getDuration().toSeconds();
				System.out.println("%"+(current/end)*100+" passed ");
				progressBar.setProgress(current/end);
				
				if (current/end == 1) {
					
					cancelTimer();
					
				}
				
				
				
			}
		};
		
		t.scheduleAtFixedRate(task, 1000 , 1000) ;
    }
    
			public void cancelTimer() {
						running = false;
						t.cancel();
		
					}


	

 
}
