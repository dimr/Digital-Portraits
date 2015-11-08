import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import toxi.geom.Vec2D;

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
        PGraphics result = pa.createGraphics(this.getPImage().width * 5, 5 * this.getPImage().height);
        System.out.println(result.width + " " + result.height);

        result.beginDraw();
        result.background(255);

        Vec2D pos = new Vec2D();
        float tileWidth = result.width / (float) face.width;
        float tileHeight = result.height / (float) face.height;
        //result.noStroke();
        for (int y = 0; y < face.height; y++) {
            int tempX = 0;
            while (tempX < face.width) {
                int c = face.pixels[y * face.width + tempX];
                float posX = tileWidth * tempX++;
                float posY = tileHeight * y;
                result.fill(c);
                result.noStroke();
                pos.set(posX, posY);
                result.ellipse(pos.x(), pos.y(), 3, 3);
            }

        }

        result.endDraw();

        return result;
    }
}
