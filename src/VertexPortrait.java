import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import toxi.geom.Vec2D;

import java.util.ArrayList;

/**
 * Created by dimitris on 11/8/15.
 */
public class VertexPortrait extends Portrait {


    public VertexPortrait(PApplet pa, PImage img, boolean useColor) {
        super(pa, img, useColor);


    }

    @Override
    public PGraphics generatePortrtait() {
        assert (this.getPImage() != null) : "OK PIMAGE";
        PImage face = this.getPImage();
        PApplet pa = this.pa();
        face.loadPixels();
        PGraphics result = pa.createGraphics(this.getPImage().width * 5, 5 * this.getPImage().height,pa.P3D);
        System.out.println(result.width + " " + result.height);

        result.beginDraw();
        result.background(255);

        float tileWidth = result.width / (float) face.width;
        float tileHeight = result.height / (float) face.height;
//        int [] color = new int [this.getDimensions()];
//
//        for (int p = 0; p < color.length; p++) {
//            color[p]=face.pixels[p];
//        }

        int[][] colors = new int[face.height][face.width];
        ArrayList<ArrayList<Vec2D>> test = new ArrayList<ArrayList<Vec2D>>();
        for (int y = 0; y < face.height; y++) {
            ArrayList<Vec2D> temp= new ArrayList<Vec2D>();
           // test.add(temp);
            for (int x = 0; x < face.width; x++) {
                colors[y][x] = face.pixels[y * face.width + x];
                float posX=tileWidth*x;
                float posY=tileHeight*y;
                Vec2D pos = new Vec2D(posX,posY);
                temp.add(pos);
            }
            test.add(temp);
        }

        System.out.println("-----" + face.width + " " + face.height + " " + test.get(0).size());

        result.pushMatrix();
        result.translate(0,0,-500);
        result.rotateX(pa.radians(40));
        for (int y = 0; y < face.height; y++) {

            result.beginShape();
            result.vertex(test.get(y).get(0).x(), test.get(y).get(0).y());

            for (int x = 0; x < face.width; x++) {
                int c=colors[y][x];
                float greyscale = ((float) (pa.red(c) + pa.green(c)  + pa.blue(c)));
                result.stroke(c);
               // result.strokeWeight(2);
                Vec2D p = new Vec2D(test.get(y).get(x).x(),test.get(y).get(x).y());
               // float yy=p.jitter((float).4).y();
                result.vertex(p.x(), p.y(),pa.map(greyscale,0,255,5,30));
            }
          ;
            result.vertex(test.get(y).get(face.width - 1).x(), test.get(y).get(face.width - 1).y());

            result.endShape();
        }
        result.popMatrix();


        result.endDraw();



//        result.noFill();
//        result.beginShape();
//        for (int y = 0; y < face.height; y++) {
//            int tempX = 0;
//            float tempPosX = tileWidth * tempX;
//            float posY = tileHeight * y;
//            result.curveVertex(tempPosX, posY);
//            while (tempX < face.width) {
//                int c = face.pixels[y * face.width + tempX];
//                int greyscale = pa.round((float) (pa.red(c) * 0.222 + pa.green(c) * 0.707 + pa.blue(c) * 0.071));
//                float posX = tileWidth * tempX++;
//                //result.fill(greyscale);
//                result.pushStyle();
//                result.stroke(greyscale);
//                pos.set(posX, posY);
////                result.ellipse(pos.x(), pos.y(), 3, 3);
//                result.curveVertex(pos.x(), pos.y());
//                result.popStyle();
////                result.pushStyle();
////                result.noStroke();
////                result.fill(c);
////                result.ellipse(pos.x(),pos.y(),4,4);
////                result.popStyle();
//            }
//            result.curveVertex(tempPosX, posY);
//
//        }
//        result.endShape();
//
//        result.endDraw();

        return result;
    }
}
