package util;

import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.RemoteVersion;
import org.to2mbn.jmccc.mcdownloader.RemoteVersionList;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.tasks.ResultProcessor;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Version;

import java.util.HashMap;
import java.util.Map;

public class DownloadMinecraft {
    public MinecraftDownloader downloader = MinecraftDownloaderBuilder.create().build();
    public void getDownloadList(){
        downloader.fetchRemoteVersionList(new CallbackAdapter<RemoteVersionList>() {
            @Override
            public void done(RemoteVersionList result) {
                Map<String, RemoteVersion> versionMap = result.getVersions();
                versionMap.forEach((k,v)->{
                    if(k.matches("\\d+\\.\\d+(\\.\\d+)?")){
                        System.out.println(k);
                    }
                });
            }
        });
    }
    public void download(String filePath,String version,CallbackAdapter<Version> callbackAdapter){
        MinecraftDirectory dir = new MinecraftDirectory(filePath+"/.minecraft");
        downloader.downloadIncrementally(dir, version,callbackAdapter );
    }
}
