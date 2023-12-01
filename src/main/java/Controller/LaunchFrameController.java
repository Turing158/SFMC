package Controller;

import Launch.LaunchMC;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.to2mbn.jmccc.launch.ProcessListener;
import util.EffectAnimation;

public class LaunchFrameController {
    @FXML
    public Label loadUser;
    @FXML
    public Label user;
    @FXML
    public Label waitLaunch;
    EffectAnimation effect = new EffectAnimation();
    LaunchMC launchMC = new LaunchMC();
    @FXML
    public Button cancel;


    @FXML
    public void initialize(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                effect.fadeEmergeVanish(0.1,true,loadUser);
                if(LaunchMC.playerFunc.equals("microsoft")){
                    user.setText("微软登录 -> 用户名: "+LaunchMC.authInfo.getUsername());
                }
                else {
                    user.setText("离线登录 -> 用户名: "+LaunchMC.username);
                }
                Thread.sleep(200);
                effect.fadeEmergeVanish(0.2,true,user);
                Thread.sleep(200);
                effect.fadeEmergeVanish(0.2,true,waitLaunch);
                start();
                return null;
            }
        };
        new Thread(task).start();
    }

    public void start(){
        launchMC.start(new ProcessListener() {
            boolean started = false;

            @Override
            public void onLog(String s) {
                System.out.println(s);
                if(!started){
                    started = true;
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            if(started){
                                Thread.sleep(1000);
                                closePage();
                            }
                            return null;
                        }
                    };
                    new Thread(task).start();
                }

            }

            @Override
            public void onErrorLog(String s) {
                System.out.println(s);
            }

            @Override
            public void onExit(int i) {
                System.out.println(i);
                if(i == 1){
                    System.out.println("！启动失败！");
                }
            }
        });
    }

    public void cancel(){
        LaunchMC.destroy();
        closePage();
    }
    public void closePage(){
        effect.fadeEmergeVanish(0.2,false,cancel.getParent().getParent().getParent());
        effect.switchPage(cancel.getParent().getParent(),0.2,25,425,false).play();
    }
}
