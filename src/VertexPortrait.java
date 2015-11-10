import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import toxi.geom.Vec2D;

import java.util.ArrayList;

/**
 * Created by dimitris on 11/8/15.
 */
public class VertexPortrait extends Portrait {

    private PImage face;
    //private PApplet pa;
    private PGraphics result;
    private float tileWidth, tileHeight;
    private int colors[][];
    private final ArrayList<ArrayList<Vec2D>> test;

    public VertexPortrait(PApplet pa, PImage img, boolean useColor) {
        super(pa, img, useColor);
        this.face = this.getPImage();
        this.setResult(pa.createGraphics(this.getPImage().width * 5, 5 * this.getPImage().height, pa.P3D));

        //this.setResult(this.pa().createGraphics(this.getPImage().width * 5, 5 * this.getPImage().height, this.pa().P3D));
        this.tileWidth = this.getResult().width / (float) this.face.width;
        this.tileHeight = this.getResult().height / (float) this.face.height;
        this.face.loadPixels();
        this.colors = new int[face.height][face.width];
        test = new ArrayList<ArrayList<Vec2D>>();
        for (int y = 0; y < face.height; y++) {
            ArrayList<Vec2D> temp = new ArrayList<Vec2D>();
            // test.add(temp);
            for (int x = 0; x < face.width; x++) {
                colors[y][x] = face.pixels[y * face.width + x];
                float posX = tileWidth * x;
                float posY = tileHeight * y;
                //for animated version
                Vec2D pos = new Vec2D(posX, posY).sub(this.getResult().width / 2, this.getResult().height / 2);
                //for non animated version
                Vec2D staticPos = new Vec2D(posX, posY).sub(this.getResult().width / 2, this.getResult().height / 2);
                temp.add(pos);
            }
            test.add(temp);
        }

    }

    @Override
    public PGraphics getResult() {
        return this.result;
    }

    @Override
    public void setResult(PGraphics result) {
        this.result = result;
    }

    @Override
    public PGraphics generatePortrtait() {
        assert (this.getPImage() != null) : "OK PIMAGE";
//
//      //  System.out.println(result.width + " " + result.height);
//
//        result.beginDraw();
//        result.background(255);
//
//        result.pushMatrix();
//        result.translate(0, 0, -500);
//        //result.rotateX(this.pa().radians(40));
//        for (int y = 0; y < face.height; y++) {
//
//            result.beginShape();
//            result.vertex(test.get(y).get(0).x(), test.get(y).get(0).y());
//
//            for (int x = 0; x < face.width; x++) {
//                int c = colors[y][x];
//                float greyscale = ((float) (this.pa().red(c) + this.pa().green(c) + this.pa().blue(c)));
//                result.stroke(c);
//                // result.strokeWeight(2);
//                this.s = new Vec2D(test.get(y).get(x).x(), test.get(y).get(x).y());
//                // float yy=p.jitter((float).4).y();
//                result.vertex(p.x(), p.y(), this.pa().map(greyscale, 0, 255, 5, 30));
//            }
//
//            result.vertex(test.get(y).get(face.width - 1).x(), test.get(y).get(face.width - 1).y());
//
//            result.endShape();
//        }
//        result.popMatrix();
//
//
//        result.endDraw();


        return result;

    }


    public PGraphics generatePortrtait(float factor) {
        assert (this.getPImage() != null) : "OK PIMAGE";

//        System.out.println(result.width + " " + result.height);

        this.getResult().beginDraw();
        this.getResult().background(255);
        this.getResult().noFill();
        this.getResult().pushMatrix();
        this.getResult().translate(this.getResult().width / 2, this.getResult().height / 2, -300);
        this.getResult().rotateX(this.pa().radians(40));
        this.getResult().rotateX(this.pa().radians(factor));
        this.getResult().rotateY(this.pa().radians(factor));
        // result.rotateZ(this.pa().radians(factor));

        for (int y = 0; y < face.height; y++) {

            this.getResult().beginShape();
            this.getResult().vertex(test.get(y).get(0).x(), test.get(y).get(0).y());

            for (int x = 0; x < face.width; x += 2) {
                int c = colors[y][x];
                float greyscale = ((float) (this.pa().red(c) + this.pa().green(c) + this.pa().blue(c)));
                this.getResult().stroke(c);
                // result.strokeWeight(2);
                Vec2D p = new Vec2D(test.get(y).get(x).x(), test.get(y).get(x).y());
                // float yy=p.jitter((float).4).y();
                this.getResult().vertex(p.x(), p.y(), this.pa().map(greyscale, 0, 255, 5, 30));
            }

            this.getResult().vertex(test.get(y).get(face.width - 1).x(), test.get(y).get(face.width - 1).y());

            this.getResult().endShape();
        }
        this.getResult().popMatrix();


        this.getResult().endDraw();


        return this.getResult();
    }


}
