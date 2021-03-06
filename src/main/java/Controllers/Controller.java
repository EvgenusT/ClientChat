package Controllers;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DataUser.Users;
import Utils.DBConnect;
import Utils.Utils;
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
    public TextField mytextChat;

    @FXML
    public TextArea myWindowText;

    @FXML
    public TextArea id_listUsers;

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

    URL inS = Utils.class.getResource("/sound/send.wav");
    URL outS = Utils.class.getResource("/sound/out.wav");

    List<Map<String, String>> myList = new LinkedList();

    Set<String> userNameList = new HashSet<>();

    static Controller controller = null;

    public Controller() throws UnsupportedEncodingException {
        controller = this;
    }

    public static void stop() {
        controller.downService();
    }

    String nameUser = Users.getLogin();

    public void onChat() throws IOException, InvocationTargetException {

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
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
            this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8"));
            (new ReadMsg()).start();


        } catch (IOException var3) {

        }
    }

    public void pressMyButtonCreateNik(ActionEvent actionEvent) throws IOException, InvocationTargetException, SQLException, ClassNotFoundException {
        onChat();
        StringJoiner myTextOut = new StringJoiner("\n");
        String out = myTextOut.add("(" + Utils.dateTimeCreate() + ") - " + nameUser + ":\t" + "ПОДКЛЮЧЕН К ЧАТУ !").toString();
        this.out.write(out + "\n");
        this.out.flush();
        DBConnect.changeOfStatusOn(nameUser);
    }

    public void pressMyButtonSend(ActionEvent actionEvent) throws UnsupportedAudioFileException, IOException {


        textMessage = mytextChat.getText();
        if (!textMessage.isEmpty()) {
            StringJoiner myTextOut = new StringJoiner("\n");

            String out = myTextOut.add("(" + Utils.dateTimeCreate() + ") - " + nameUser + ":\t" + textMessage).toString();
            this.out.write(out + "\n");
            this.out.flush();
            this.mytextChat.clear();
        }
    }
    public void pressMyButtonCleare(ActionEvent actionEvent) throws IOException {
        this.myList.clear();
        this.myWindowText.clear();
    }

    public void pressMyButtonOffChat(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        this.myWindowText.setText( nameUser + ":\t ты отключен от чата");
        this.downService();

    }

    private void downService() {
        try {

            if (!this.socket.isClosed()) {

                this.out.write("StoP+-+");
                this.out.flush();
                this.out.close();
                this.in.close();
                this.socket.close();
                DBConnect.changeOfStatusOff(nameUser);
            }
        } catch (IOException var2) {
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class ReadMsg extends Thread {
        private ReadMsg() {
        }
        private void addUserList(String name) throws SQLException, ClassNotFoundException {

            userNameList = DBConnect.getStatusList();
            userNameList.add(name);

            StringJoiner nameList = new StringJoiner("\n");
            for (String e : userNameList) {
                nameList.add(e);
            }
            Controller.this.id_listUsers.setText(nameList.toString());
        }

        public void run() {
            try {
                while (true) {
                    String myText = Controller.this.in.readLine();
                    StringJoiner textOut = new StringJoiner("\n");
                    Map<String, String> messageClient = new HashMap();
                    messageClient.put(nameUser, myText);

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

                    if (!resOut.equals(nameUser)) {
                        Utils.playSound(inS);
                    } else {
                        Utils.playSound(outS);
                    }
                    Controller.this.myWindowText.setText(textOut.toString());

                    addUserList(resOut);
                }

            } catch (IOException var8) {
                Controller.this.downService();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}




