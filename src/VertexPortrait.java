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
    private float tileWidth,tileHeight;
    private int colors[][];
    private final ArrayList<ArrayList<Vec2D>> test;

    public VertexPortrait(PApplet pa, PImage img, boolean useColor) {
        super(pa, img, useColor);
        this.face=this.getPImage();

        this.result = this.pa().createGraphics(this.getPImage().width*5,5*this.getPImage().height,this.pa().P3D);
        this.tileWidth = result.width / (float) this.face.width;
        this.tileHeight = result.height / (float) this.face.height;
        this.face.loadPixels();
        this.colors = new int[face.height][face.width];
        test = new ArrayList<ArrayList<Vec2D>>();
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

    }

    @Override
    public PGraphics generatePortrtait() {
        assert (this.getPImage() != null) : "OK PIMAGE";

        System.out.println(result.width + " " + result.height);

        result.beginDraw();
        result.background(255);


//        int [] color = new int [this.getDimensions()];
//
//        for (int p = 0; p < color.length; p++) {
//            color[p]=face.pixels[p];
//        }




        result.pushMatrix();
        result.translate(0, 0, -500);
        result.rotateX(this.pa().radians(40));
        for (int y = 0; y < face.height; y++) {

            result.beginShape();
            result.vertex(test.get(y).get(0).x(), test.get(y).get(0).y());

            for (int x = 0; x < face.width; x++) {
                int c=colors[y][x];
                float greyscale = ((float) (this.pa().red(c) + this.pa().green(c)  + this.pa().blue(c)));
                result.stroke(c);
               // result.strokeWeight(2);
                Vec2D p = new Vec2D(test.get(y).get(x).x(),test.get(y).get(x).y());
               // float yy=p.jitter((float).4).y();
                result.vertex(p.x(), p.y(),this.pa().map(greyscale,0,255,5,30));
            }
          ;
            result.vertex(test.get(y).get(face.width - 1).x(), test.get(y).get(face.width - 1).y());

            result.endShape();
        }
        result.popMatrix();


        result.endDraw();


        return result;
    }
}
