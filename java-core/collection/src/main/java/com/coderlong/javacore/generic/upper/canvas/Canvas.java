package com.coderlong.javacore.generic.upper.canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * 设定类型通配符上限
 * @author Long Qiong
 * @create 2019/3/27
 */
public class Canvas {
    public void drawAll(List<? extends Sharpe> sharpeList){
        sharpeList.forEach(s ->{
            s.draw(this);
        });
    }

    public static void main(String[] args) {
        Canvas canvas = new Canvas();
        List<Circle> circleList = new ArrayList<>();
        canvas.drawAll(circleList);

        List<Rectangle> rectangleList = new ArrayList<>();
        canvas.drawAll(rectangleList);

        List<Sharpe> sharpeList = new ArrayList<>();
        sharpeList.add(new Circle());
        sharpeList.add(new Rectangle());
        canvas.drawAll(sharpeList);
    }

}
