import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String addr;
    private int port;
    public static final int PORT = 18080;

    //ссылки на контроллеры
    private ControllerLogin children;

    @FXML
    public TextArea login;
    @FXML
    public TextArea password;

    String loginClient;
    String passwordClient;


    String outSound = "src\\main\\resources\\sound\\out.wav";
    String inSound = "src\\main\\resources\\sound\\send.wav";
    String nikname = "";

    List<Map<String, String>> myList = new LinkedList();


    //получаем имя компьютера
    public static final String compName = System.getenv("COMPUTERNAME");
    public static final String compName2 = "zf5bank.ddns.net";

    public Controller() {
    }

//   public void  getControllerLogin() throws IOException {
//
//       FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/login.fxml"));
//       Parent root = loader.load();
//       ControllerLogin conLog = loader.<>getController();
//       conLog.setLoginClient();
//       conLog.setPasswordClient();
//   }

    public void findAction(ActionEvent actionEvent) {
        try {
            Stage stageFind = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXML/login.fxml"));
            Parent root = loader.load();
            stageFind.setScene(new Scene(root));
            stageFind.show();

            children = loader.getController(); //получаем контроллер окна login.fxml

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void initialize(URL location, ResourceBundle resources) {
    }

    public void pressMyButtonCreateNik(ActionEvent actionEvent) throws IOException, InvocationTargetException {
        OnChat();
        this.nikname = this.myNik.getText();
        if (!this.nikname.isEmpty()) {
            this.out.write("(" + this.dateTimeCreate() + ") - " + nikname + ":\t подключен к чату\n");
            this.out.flush();

        }
    }

    public void pressMyButtonSend(ActionEvent actionEvent) throws UnsupportedAudioFileException, IOException {

        String textMessage = this.mytextChat.getText();
        if (!textMessage.isEmpty()) {
            StringJoiner myTextOut = new StringJoiner("\n");
            this.nikname = this.myNik.getText();

            myTextOut.add("(" + this.dateTimeCreate() + ") - " + this.nikname + ":\t" + textMessage);
            if (!this.nikname.isEmpty()) {
                this.out.write(myTextOut + "\n");
                this.out.flush();
                this.mytextChat.clear();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Невозможно отправить сообщение, ник не задан");
                alert.showAndWait();
            }
        }
    }

    public void pressMyButtonCleare(ActionEvent actionEvent) throws IOException {
        this.myList.clear();
        this.myWindowText.clear();
    }

    public void OnChat() throws IOException, InvocationTargetException {
        try {
//
                this.socket = new Socket(compName2, PORT);
//

        } catch (IOException var4) {
            System.err.println("(" + this.dateTimeCreate() + ") - Ошибка подключения к сокету");
        }
        try {
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            (new ReadMsg()).start();
            if (!this.nikname.isEmpty()) {
            this.myWindowText.setText("(" + this.dateTimeCreate() + ") - " + " пользователь: " + this.nikname + " -\t  подключен к чату");
            } else {
//                System.err.println("(" + this.dateTimeCreate() + ") - Ник не создан");
//                this.myWindowText.setText("(" + this.dateTimeCreate() + ") - " + "Ник не создан");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ник не создан");
                alert.showAndWait();
            }
        } catch (IOException var3) {
            this.downService();
        }
    }

    private void downService() {
        try {
            if (!this.socket.isClosed()) {
                this.socket.close();
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

