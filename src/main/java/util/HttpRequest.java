package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.OptiFine;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class HttpRequest {

    public static String GET(String link){
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                inputStreamReader.close();
                inputStream.close();
                connection.disconnect();
                return sb.toString();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void download(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int code = connection.getResponseCode();
        if(code == HttpURLConnection.HTTP_OK){
            InputStream inputStream = connection.getInputStream();
            File Dir = new File("E:\\Game\\Minecraft\\.minecraft");
            File file = new File(Dir,"OptiFine_1.10.2_HD_U_C1.jar");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = readInputStream(inputStream);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            inputStream.close();
            connection.disconnect();
        }

    }
    private static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4 * 1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
