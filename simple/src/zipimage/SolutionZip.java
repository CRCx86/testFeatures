package zipimage;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class SolutionZip {
    public static void main(String[] args) throws IOException {

        File file = new File("\"upload\" + File.separator + \"original.jpg\"");
        Thumbnails.of(file)
                .size(160, 160)
                .outputQuality(0.5)
                .toFile("upload" + File.separator + "thumbnail.jpg");

    }
}
