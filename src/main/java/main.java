import Controller.Frame;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.OptiFine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class main {
    public static void main(String[] args) throws IOException {
        Frame.launch(Frame.class,args);
    }

}
