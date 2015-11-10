import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * Created by dimitris on 11/8/15.
 */
public class TextPortrait extends Portrait {

    String inputText = "Creative Technology is an interdisciplinary and transdisciplinary field combining technology with design, art and the humanities. In its applied form appears as the synergistic fusion of technology with creative forms of expression such as fashion, architecture, advertising, media, entertainment, etc. In the spring of 2015 was held in Thessaloniki the first creative coding workshop. After completion of the course, there was the initial thought to organize an exhibition where participants would exhibit their work. Within this context the enthusiasm of team members led our thinking a step further and decided to organize a festival.\n" +
            "\n" +
            "Theta is aimed at attracting designers, architects, artists of all disciplines and levels, performers, code enthusiasts and generally those who wish to learn about the latest developments or to experiment with digital media and tools as a creative expression platform. The main categories of the festival are, but not limited to them: generative design, human-centric, interactive media, audio reactive, image / video synthesis - live visuals, physical computing. We aspire to be the first event, associated with Creative Technology in Greece, to collect a set of activities as professionals and artists talks, workshops on new technologies, interactive multimedia installations, but also to attract a multitude of creative people from a wide range of disciplines, including creative technologists, data lovers, new media artists etc. We hope that it will emerge as a commonplace for those who want to share and interact, learn, explore and participate in an insightful discussion on the placement of technology in art and culture.";
    private float betweenLettersSpace = (float)3.5;
    private PFont font;
    public TextPortrait(PApplet pa, PImage img, boolean useColor) {
        super(pa, img, useColor);
        font = this.pa().createFont("Ubuntu-Bold-48",8);
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
        result.smooth();
        float x = 0, y = 10;
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
            float fontSize = pa.map(greyscale, 0, 255, 18,12);
            float thefontSize = pa.max(fontSize, 1);
            result.fill(c);
            //result.textSize(fontSize);
            result.textFont(font,thefontSize);
            char letter = inputText.charAt(counter);
            result.text(letter, 0, 0);
            float letterWidth = pa.textWidth(letter) + betweenLettersSpace;
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
