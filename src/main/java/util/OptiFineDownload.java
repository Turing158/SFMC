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
import java.util.List;

public class OptiFineDownload {

    private OptiFine optiFine;
    private final String api = "https://bmclapi2.bangbang93.com/optifine";
    public OptiFineDownload() {

    }

    public List<OptiFine> getOptiFineVersions(String MinecraftVersion) throws IOException{
        String link = api+"/"+MinecraftVersion;
        String jsonStr = HttpRequest.GET(link);
        ObjectMapper mapper = new ObjectMapper();
        List<OptiFine> optiFines = mapper.readValue(jsonStr, List.class);
        Collections.reverse(optiFines);
        return optiFines;
    }

    public static void main(String[] args) throws IOException {
        OptiFineDownload optiFineDownload = new OptiFineDownload();
        System.out.println(optiFineDownload.getOptiFineVersions("1.12").toString());
//        List<String> optiFineVersion = optiFineDownload.getOptiFineVersions("1.12");
//        optiFineDownload.download("OptiFine_1.10.2_HD_U_C1.jar");


    }

//    public void download(String optiFineVersion) throws IOException {
//        String link = "https://optifine.cn/download/"+optiFineVersion;
//        URL url = new URL(link);
//        System.out.println(url.);
//    }
}
