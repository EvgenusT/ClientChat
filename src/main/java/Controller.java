import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Controller implements Initializable {
    @FXML
    public TextField myNik;
    @FXML
    public TextField mytextChat;
    @FXML
    public TextArea myWindowText;
    private Socket socet;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String addr;
    private int port;
    public static final int PORT = 18080;
    // String outSound = "\\sound\\out.wav";
    String outSound = "src\\main\\resources\\sound\\out.wav";
    String inSound = "src\\main\\resources\\sound\\send.wav";
    String nikname;
    List<Map<String, String>> myList = new LinkedList();

    //получаем имя компьютера
    public static final String compName = System.getenv("COMPUTERNAME");

    public Controller() {
    }

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void pressMyButtonCreateNik(ActionEvent actionEvent) throws IOException {
        this.nikname = this.myNik.getText();
        this.out.write("(" + this.dateTimeCreate() + ") - " + this.nikname + ":\t подключен к  чату\n");
        this.out.flush();
    }

    public void pressMyButtonSend(ActionEvent actionEvent) throws IOException, UnsupportedAudioFileException {
        String textMessage = this.mytextChat.getText();
        StringJoiner myTextOut = new StringJoiner("\n");
        this.nikname = this.myNik.getText();
        if (this.nikname.isEmpty()) {
            this.nikname = "Anonymous";
        }

        myTextOut.add("(" + this.dateTimeCreate() + ") - " + this.nikname + ":\t" + textMessage);
        this.out.write(myTextOut + "\n");
        this.out.flush();
        this.mytextChat.clear();
    }

    public void pressMyButtonCleare(ActionEvent actionEvent) {
        this.myList.clear();
        this.myWindowText.clear();
    }

    public void pressMyButtonOnChat(ActionEvent actionEvent) throws IOException {
        this.addr = compName;
        this.port = PORT;

        try {
            this.socet = new Socket(compName, PORT);
        } catch (IOException var4) {
            System.err.println("(" + this.dateTimeCreate() + ") - Ошибка подключения к сокету");
        }

        try {
            this.in = new BufferedReader(new InputStreamReader(this.socet.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(this.socet.getOutputStream()));
            (new Controller.ReadMsg()).start();
            this.myWindowText.setText("(" + this.dateTimeCreate() + ") - " + this.nikname + ":\t ты подключен к чату");
        } catch (IOException var3) {
            this.downService();
        }

    }

    public void pressMyButtonOffChat(ActionEvent actionEvent) {
        this.downService();
        this.myWindowText.setText("(" + this.dateTimeCreate() + ") - " + this.nikname + ":\t ты отключен от чата");
    }

    private void downService() {
        try {
            if (!this.socet.isClosed()) {
                this.socet.close();
                this.in.close();
                this.out.close();
            }
        } catch (IOException var2) {
        }

    }

    private String dateTimeCreate() {
        Date data = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String datetime = df.format(data);
        return datetime;
    }

    public void playSound(String sound) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sound));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private class ReadMsg extends Thread {
        private ReadMsg() {
        }

        public void run() {
            try {
                while (true) {
                    String myText = Controller.this.in.readLine();
                    StringJoiner textOut = new StringJoiner("\n");
                    Map<String, String> messageClient = new HashMap();
                    messageClient.put(Controller.this.nikname, myText);
                    Controller.this.myList.add(messageClient);
                    Controller.this.myList.forEach((m) -> {
                        m.forEach((key, value) -> {
                            textOut.add(value);
                        });
                    });
                    Matcher matcher = Pattern.compile("\\-(?:([^\\-]+))\\:").matcher(myText);

                    String res;
                    for (res = ""; matcher.find(); res = matcher.group()) {
                    }

                    String resOut = res.substring(2, res.length() - 1);
                    if (!resOut.equals(Controller.this.myNik.getText()) && !resOut.equals("Anonymous")) {
                        Controller.this.playSound(Controller.this.inSound);
                    } else {
                        Controller.this.playSound(Controller.this.outSound);
                    }

                    Controller.this.myWindowText.setText(textOut.toString());
                }
            } catch (IOException var8) {
                Controller.this.downService();
            }
        }
    }
}

