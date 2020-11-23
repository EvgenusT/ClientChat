import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.sound.sampled.UnsupportedAudioFileException;

public class Controller {

    String localAddress = "Local connect"; // это IP-адрес компьютера, где исполняется наша серверная программа.
    String address = "Network connected"; // это IP-адрес компьютера, где исполняется наша серверная программа.

    ObservableList<String> localizationList = FXCollections.observableArrayList(localAddress, address);
    String loc = "127.0.0.1";
    String add = "zf5bank.ddns.net";
    @FXML
    public TextField myNik;
    @FXML
    public TextField mytextChat;

    @FXML
    public TextArea myWindowText;
    @FXML
    public ChoiceBox switchLocalization;

    @FXML
    public void initialize() {
        switchLocalization.setValue("Local connect");
        switchLocalization.setItems(localizationList);
    }

    String textMessage = "";


    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    public static final int PORT = 18080;

    String outSound = "src\\main\\resources\\sound\\out.wav";
    String inSound = "src\\main\\resources\\sound\\send.wav";
    String nikname = "";

    List<Map<String, String>> myList = new LinkedList();

    public Controller() throws UnsupportedEncodingException {
    }

    public void OnChat() throws IOException, InvocationTargetException {
        try {
            String adresRes = switchLocalization.getValue().toString();
            if (!adresRes.isEmpty() && adresRes.equals(localAddress)) {
                this.socket = new Socket(loc, PORT);
            } else if (!adresRes.isEmpty() && adresRes.equals(address)) {
                this.socket = new Socket(add, PORT);
            }

        } catch (IOException var4) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ошибка подключения к сокету");
            alert.showAndWait();
        }
        try {
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

            (new ReadMsg()).start();

            if (!this.nikname.isEmpty()) {
                this.myWindowText.setText("(" + Utils.dateTimeCreate() + ") - " + " пользователь: " + this.nikname + " -\t  подключен к чату");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ник не создан");
                alert.showAndWait();
            }
        } catch (IOException var3) {

        }
    }

    public void pressMyButtonCreateNik(ActionEvent actionEvent) throws IOException, InvocationTargetException {
        this.nikname = this.myNik.getText();
        OnChat();
        if (!this.nikname.isEmpty()) {
            this.out.write("(" + Utils.dateTimeCreate() + ") - " + nikname + ":\t подключен к чату\n");
            this.out.flush();
        }
    }

    public void pressMyButtonSend(ActionEvent actionEvent) throws UnsupportedAudioFileException, IOException {

        textMessage = mytextChat.getText();

        if (!textMessage.isEmpty()) {
            StringJoiner myTextOut = new StringJoiner("\n");
            this.nikname = this.myNik.getText();

            myTextOut.add("(" + Utils.dateTimeCreate() + ") - " + this.nikname + ":\t" + textMessage);
            if (!this.nikname.isEmpty()) {
                this.out.write(myTextOut + "\n");
                this.out.flush();
                this.mytextChat.clear();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Невозможно отправить сообщение, ник не задан");
                alert.showAndWait();
            }
        }
    }

    public void pressMyButtonCleare(ActionEvent actionEvent) throws IOException {
        this.myList.clear();
        this.myWindowText.clear();
    }

    public void pressMyButtonOffChat(ActionEvent actionEvent) throws IOException {
        this.out.write("StoP+-+");
        this.out.flush();
        this.downService();
        this.myWindowText.setText("(" + Utils.dateTimeCreate() + ") - " + this.nikname + ":\t ты отключен от чата");
    }

    private void downService() {
        try {
            if (!this.socket.isClosed()) {
                this.out.close();
                this.in.close();
                this.socket.close();
            }
        } catch (IOException var2) {
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

                    // разбор строки для определения входящего либо ичсходящего сообщения, для выбора звука
                    Matcher matcher = Pattern.compile("\\-(?:([^\\-]+))\\:").matcher(myText);

                    String res;
                    for (res = ""; matcher.find(); res = matcher.group()) {
                    }

                    String resOut = res.substring(2, res.length() - 1);
                    if (!resOut.equals(Controller.this.myNik.getText())) {

                        Utils.playSound(Controller.this.inSound);
                    } else {
                        Utils.playSound(Controller.this.outSound);
                    }

                    //отправка сформированного сообщения в поле вывода чата
                    Controller.this.myWindowText.setText(textOut.toString());
                }
            } catch (IOException var8) {
                Controller.this.downService();
            }
        }
    }
}

