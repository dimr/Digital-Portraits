import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * Created by dimitris on 11/8/15.
 */
public class TextPortrait extends Portrait {

    String inputText = "Ihr naht euch wieder, schwankende Gestalten, Die früh sich einst dem trüben Blick gezeigt. Versuch ich wohl, euch diesmal festzuhalten? Fühl ich mein Herz noch jenem Wahn geneigt? Ihr drängt euch zu! nun gut, so mögt ihr walten, Wie ihr aus Dunst und Nebel um mich steigt; Mein Busen fühlt sich jugendlich erschüttert Vom Zauberhauch, der euren Zug umwittert. Ihr bringt mit euch die Bilder froher Tage, Und manche liebe Schatten steigen auf; Gleich einer alten, halbverklungnen Sage Kommt erste Lieb und Freundschaft mit herauf; Der Schmerz wird neu, es wiederholt die Klage.";


    public TextPortrait(PApplet pa, PImage img, boolean useColor) {
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
        float x = 0, y = 0;
        int counter = 0;
        System.out.println("starting....");
        int greyscale = 0;
        while (y < result.height) {
            int imgX = (int) pa.map(x, 0, result.width, 0, face.width);
            int imgY = (int) pa.map(y, 0, result.height, 0, face.height);
            int c = face.pixels[imgY * face.width + imgX];
            greyscale = pa.round((float) (pa.red(c) * 0.222 + pa.green(c) * 0.707 + pa.blue(c) * 0.071));
            result.pushMatrix();
            result.translate(x, y);
            float fontSize = pa.map(greyscale, 0, 255, 8, 20);
            fontSize = pa.max(fontSize, 1);
            char letter = inputText.charAt(counter);
            result.fill(c);
            result.textSize(fontSize);
            result.text(letter, 0, 0);
            float letterWidth = pa.textWidth(letter) + (float) .5;
            x = x + letterWidth;
            result.popMatrix();

            if (x + letterWidth >= result.width) {
                x = 0;
                y = y + 12;
            }

            counter++;
            if (counter > inputText.length() - 1)
                counter = 0;
        }
        result.endDraw();
        System.out.println("END....");
        return result;
    }
}
