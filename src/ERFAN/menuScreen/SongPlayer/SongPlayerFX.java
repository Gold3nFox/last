package ERFAN.menuScreen.SongPlayer;//package ERFAN.menuScreen.SongPlayer;
//
//import javafx.application.Application;
//import javafx.embed.swing.JFXPanel;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.MediaView;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.File;
//import java.util.HashMap;
//
//public class SongPlayer extends Application{
//
//
//    private HashMap <String, Media> musicTable;
//    private MediaPlayer mediaPlayer;
//
//    public SongPlayer(){
//        JFXPanel jfxPanel = new JFXPanel(); // this is just to make things work ...
//        // we have a hashMap to indicate our songs
//        musicTable = new HashMap<>();
//        musicTable.put("Intro Song",new Media(new File("Resources/01 - swordland.mp3").toURI().toString()));
//
//    }
//
//    public void playSong(String songName){
//        mediaPlayer = new MediaPlayer(musicTable.get(songName));
//        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
//        mediaPlayer.play();
//    }
//
//    @Override
//    public void start(Stage primaryStage)
//    {
//
//    }
//
//    public static void main(String[] args) {
//        SongPlayer songPlayer = new SongPlayer();
//        songPlayer.playSong("Intro Song");
//    }
//}
