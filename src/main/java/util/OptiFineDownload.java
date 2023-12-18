package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.OptiFine;
import entity.OptiFineFiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OptiFineDownload {
    private OptiFine optiFine;
    public OptiFineDownload() throws IOException {
        URL url = new URL("https://optifine.cn/api");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            optiFine = mapper.readValue(sb.toString(), OptiFine.class);
        }
    }
    public List<String> getOptiFineVersions(String MinecraftVersion) {
        List<String> optiFineVersions = new ArrayList<>();
        for (int i = 0; i < optiFine.getFiles().size(); i++) {
            OptiFineFiles optiFineFiles = optiFine.getFiles().get(i);
            if(optiFineFiles.getVersion().equals(MinecraftVersion)){
                optiFineVersions.add(optiFineFiles.getName());
            }
        }
        Collections.reverse(optiFineVersions);
        return optiFineVersions;
    }

    public static void main(String[] args) throws IOException {
        OptiFineDownload optiFineDownload = new OptiFineDownload();
        List<String> optiFineVersion = optiFineDownload.getOptiFineVersions("1.12");
    }
}
