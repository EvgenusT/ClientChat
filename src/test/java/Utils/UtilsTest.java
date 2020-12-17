//package Utils;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//import java.net.URL;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//public class UtilsTest {
//
//    Utils utilsTest;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        utilsTest = new Utils();
//    }
//
//    @Test
//    public void dateTimeCreateTest() throws Exception {
//        Date data = new Date();
//        DateFormat df = new SimpleDateFormat("HH:mm:ss");
//        String datetime = df.format(data);
//
//        assertEquals(utilsTest.dateTimeCreate(), datetime);
//
//    }
//
////    @Test
////    public void playSoundTest() throws Exception {
////        URL in = Utils.class.getResource("/sound/send.wav");
////        String out = "";
////        assertEquals(utilsTest.playSound(in), "alex");
////    }
//
//}
