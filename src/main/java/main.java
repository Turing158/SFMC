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
        URL url = new URL("https://optifine.cn/api");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int code = connection.getResponseCode();
        if(code == HttpURLConnection.HTTP_OK){
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder sb = new StringBuilder();
            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            System.out.println(sb);
            ObjectMapper mapper = new ObjectMapper();
            mapper.readValue(sb.toString(),OptiFine.class);
        }

        Frame.launch(Frame.class,args);
    }

}
