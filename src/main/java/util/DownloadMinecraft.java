package util;

import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.tasks.ResultProcessor;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Version;

public class DownloadMinecraft {
    public void download(String filePath,String version,CallbackAdapter<Version> callbackAdapter){
        MinecraftDirectory dir = new MinecraftDirectory(filePath+"/.minecraft");
        MinecraftDownloader downloader = MinecraftDownloaderBuilder.create().build();
        downloader.downloadIncrementally(dir, version,callbackAdapter );

    }
}
