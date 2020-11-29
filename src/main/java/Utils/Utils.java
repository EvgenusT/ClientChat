package Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Utils {

    public static String dateTimeCreate() {
        Date data = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String datetime = df.format(data);
        return datetime;
    }

//    public static void playSound(String nameFile) {
//        try {
//
//            //(getClass().getResource("FXML/sample.fxml"));
//            Media hit = new Media(new File(nameFile));
//            MediaPlayer mediaPlayer = new MediaPlayer(hit);
//            mediaPlayer.play();
//
//        } catch (Exception e) {
//            System.out.println("Файл звука не найден");
//            e.printStackTrace();
//        }
//    }

    public static void playSound(URL sound) {
        try {
            System.out.println(sound);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception var4) {
            System.out.println("Файл звука не найден");
            var4.printStackTrace();
        }
    }
}
