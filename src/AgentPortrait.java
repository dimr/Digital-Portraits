import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import toxi.geom.Vec2D;

/**
 * Created by dimitris on 11/9/15.
 */
public class AgentPortrait extends Portrait {
    private PImage face;
    PGraphics result;
    private float tileWidth,tileHeight;


    public AgentPortrait(PApplet pa, PImage img, boolean useColor) {
        super(pa, img, useColor);
        assert (this.getPImage() != null) : "OK PIMAGE";
        this.face=this.getPImage();

        this.result = this.pa().createGraphics(this.getPImage().width*5,5*this.getPImage().height);
        this.tileWidth = result.width / (float) this.face.width;
        this.tileHeight = result.height / (float) this.face.height;
        this.face.loadPixels();
    }

    @Override
    public PGraphics generatePortrtait() {
        Vec2D pos = new Vec2D();
        result.beginDraw();
        result.background(0);
        for (int x = 0; x < this.face.width-1; x+=2) {
            for (int y = 0; y < this.face.height-1; y+=2) {
                float posX = tileWidth * x;
                float posY = tileHeight * y;
                pos.set(posX, posY);
                int col = this.face.pixels[y * this.face.width + x];
                int greyscale = this.pa().round((float) (this.pa().red(col) * 0.222 + this.pa().green(col) * 0.707 + this.pa().blue(col) * 0.071));
                //fill(greyscale);
                //ellipse(posX,posY,3,3);
              //  if (greyscale < 200) {
//                    textSize(map(greyscale, 0, 255, 4, 14));
//                    textAlign(CENTER);
//                    text(greyscale, posX, posY);
                    Vec2D t1 = pos.add(2, 2);
                    Vec2D t2 = pos.sub(2,2);
                    Vec2D t3 = t1.getPerpendicular();
                    result.noFill();

                    result.stroke(col);
                    result.beginShape();
                    result.curveVertex(pos.x(), pos.y());
                    result.curveVertex(t1.x(), t1.y());
                    result.curveVertex(t2.x(), t2.y());
                    result.curveVertex(t3.x(), t3.y());
                    result.endShape();
                //}
            }

        }
        return this.result;
    }
}
