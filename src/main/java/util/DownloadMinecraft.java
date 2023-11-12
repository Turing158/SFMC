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
import java.util.concurrent.Future;

public class DownloadMinecraft {
    MinecraftDownloader downloader = MinecraftDownloaderBuilder.create().build();
    Future<Version> downloading;
    public void download(String filePath,String version,CallbackAdapter<Version> callbackAdapter){
        MinecraftDirectory dir = new MinecraftDirectory(filePath+"/.minecraft");
        downloading = downloader.downloadIncrementally(dir, version,callbackAdapter);

    }
    public void shutdown(){
        downloader.shutdown();
    }
    public void cancel(){
        downloading.cancel(true);
    }
}
