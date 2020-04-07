package com.dazo66.test;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GifEncoderTest {

        public static final int SIZE = 200;

        public static void main(String[] args) throws IOException {
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            encoder.start("res/out.gif");
            encoder.setTransparent(Color.WHITE);
            encoder.setRepeat(0);
            encoder.setDelay(30);

            BufferedImage img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g2d = img.createGraphics();
            for (int i = 0; i < 100; i++) {
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, SIZE, SIZE);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(0, i, 100, 100);

                encoder.addFrame(img);
            }

            g2d.dispose();
            encoder.finish();
        }
}