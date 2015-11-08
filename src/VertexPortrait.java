import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import toxi.geom.Vec2D;

/**
 * Created by dimitris on 11/8/15.
 */
public class VertexPortrait extends Portrait{

    public VertexPortrait(PApplet pa, PImage img, boolean useColor) {
        super(pa, img, useColor);
    }

    @Override
    public PGraphics generatePortrtait() {
        assert (this.getPImage() != null) : "OK PIMAGE";
        PImage face = this.getPImage();
        PApplet pa = this.pa();
        face.loadPixels();
        PGraphics result = pa.createGraphics(this.getPImage().width * 5, 5 * this.getPImage().height);
        System.out.println(result.width + " " + result.height);

        result.beginDraw();
        result.background(255);

        Vec2D pos = new Vec2D();
        float tileWidth=result.width/(float)face.width;
        float tileHeight=result.height/(float)face.height;
        result.noStroke();
        for (int x = 0; x < face.width; x++) {
            for (int y = 0; y < face.height; y++) {
                int loc = x+y*face.width;
                float r = pa.red(face.pixels[loc]);
                float g=pa.green(face.pixels[loc]);
                float b=pa.blue(face.pixels[loc]);
                float posX = tileWidth * x;
                float posY = tileHeight * y;
                pos.set(posX,posY);
                float c = pa.map(r,0,255,1,4);
                result.fill(c);
                result.ellipse(pos.x(),pos.y(),c,c);
            }
            result.endDraw();

        }



        return result;
    }
}
