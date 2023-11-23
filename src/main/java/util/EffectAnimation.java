package util;

import com.sun.scenario.animation.SplineInterpolator;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

//自制动画库
public class EffectAnimation {
//    用于元素的淡入淡出
    public Timeline fadeEffect(Node node,double seconds,double waitTime){
        FadeTransition fadeFirst = new FadeTransition(Duration.seconds(seconds),node);
        fadeFirst.setFromValue(0);
        fadeFirst.setToValue(1);
        FadeTransition fadeFinish = new FadeTransition(Duration.seconds(seconds),node);
        fadeFinish.setFromValue(1);
        fadeFinish.setToValue(0);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),e-> {
                    node.setVisible(true);
                    node.setOpacity(0);
                    fadeFirst.play();
                }),
                new KeyFrame(Duration.seconds(waitTime),e-> {
                    fadeFinish.play();
                })
        );
        return timeline;
    }
    public void fadeEmergeVanish(double seconds, boolean emerge, Node... nodes){
        for (int i = 0; i < nodes.length; i++) {
            FadeTransition fade = new FadeTransition(Duration.seconds(seconds),nodes[i]);
            if(emerge){
                nodes[i].setVisible(true);
                fade.setFromValue(0);
                fade.setToValue(1);
            }
            else{
                fade.setFromValue(1);
                fade.setToValue(0);
                int finalI = i;
                fade.setOnFinished(event ->{
                    nodes[finalI].setVisible(false);
                });
            }
            set60fps(fade).play();
        }

    }
//    用于元素的横向移动
    public TranslateTransition moveX(Node node,double seconds,double from,double to){
        TranslateTransition translate = new TranslateTransition(Duration.seconds(seconds),node);
        translate.setFromX(from);
        translate.setToX(to);
        translate.setInterpolator(new SplineInterpolator(0.1,0.1,0.1,1));
        return translate;
    }
//    用于元素的纵向移动
    public TranslateTransition moveY(Node node,double seconds,double from,double to){
        TranslateTransition translate = new TranslateTransition(Duration.seconds(seconds),node);
        translate.setFromY(from);
        translate.setToY(to);
        translate.setInterpolator(new SplineInterpolator(0.1,0.1,0.1,1));
        return translate;
    }
//    用于元素动画设置成60帧
    public Timeline set60fps(Transition transition){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(8),e->{
            transition.play();
        }));
        return timeline;
    }
//    用于页面切换
    public Timeline switchPage(Node node,double seconds,double from,double to,boolean isShow){
        if(isShow){
            node.setVisible(true);
        }
        TranslateTransition translate = moveY(node,seconds,from,to);
        translate.setInterpolator(new SplineInterpolator(0.1,0.1,0.1,1));
        translate.setOnFinished(event -> {
            if (!isShow) {
                node.setVisible(true);
            }
        });
         return set60fps(translate);
    }
    public Transition rotate(Node node,double seconds,double from,double to){
        RotateTransition rotate = new RotateTransition(Duration.seconds(seconds),node);
        rotate.setFromAngle(from);
        rotate.setToAngle(to);
        rotate.setInterpolator(new SplineInterpolator(0.1,0.1,0.1,1));
        return rotate;
    }
    public Timeline tipsEffect(Node tipsBox,Text text, double seconds, double waitTime,String tips){
        text.setText(tips);
        tipsBox.setTranslateX(tipsBox.getParent().getLayoutBounds().getWidth()/2 - text.getLayoutBounds().getWidth()/2 - ((HBox) tipsBox).getPadding().getLeft());
        return fadeEffect(tipsBox, seconds, waitTime);
    }
//    用于停止动画
    public void stopAnimation(Transition transition){
        transition.stop();
    }
}
