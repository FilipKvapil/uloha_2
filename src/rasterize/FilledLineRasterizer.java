package rasterize;

import model.Line;
import model.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FilledLineRasterizer extends LineRasterizer {

    private int color;

    public FilledLineRasterizer(Raster raster) {
        super(raster);
        this.color = Color.RED.getRGB();
    }
    /*Byl použit triviální algoritmus.
     *Nevýhoda: násobení a sčítání v plovoucí řádové čárce je neefektivní
     *Výhoda: postup použitelný i pro složitější křivky
     * */

    public void drawLine(Point p1, Point p2) {

        float x1 = p1.getX();
        float y1 = p1.getY();
        float x2 = p2.getX();
        float y2 = p2.getY();
        //Určení řídící osy
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            //Výměna koncových bodů
            if (x2 < x1) {
                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            float k = (y2 - y1) / (x2 - x1);

            //Vyhreslení bodu podle Y
            for (float i = x1; i <= x2; ++i) {
                this.drawPixel((int) i, (int) y1);
                y1 += k;
            }

        } else {
            //Výměna koncových bodů
            if (y2 < y1) {
                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            float k = (x2 - x1) / (y2 - y1);
            //Vyhreslení bodu podle X
            for (float i = y1; i <= y2; ++i) {
                this.drawPixel((int) x1, (int) i);
                x1 += k;
            }
        }
    }

    public void drawLine(Line line) {

        float x1 = line.getX1();
        float y1 = line.getY1();
        float x2 = line.getX2();
        float y2 = line.getY2();
        //Určení řídící osy
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            //Výměna koncových bodů
            if (x2 < x1) {
                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            float k = (y2 - y1) / (x2 - x1);

            //Vyhreslení bodu podle Y
            for (float i = x1; i <= x2; ++i) {
                this.drawPixel((int) i, (int) y1);
                y1 += k;
            }

        } else {
            //Výměna koncových bodů
            if (y2 < y1) {
                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            float k = (x2 - x1) / (y2 - y1);
            //Vyhreslení bodu podle X
            for (float i = y1; i <= y2; ++i) {
                this.drawPixel((int) x1, (int) i);
                x1 += k;
            }
        }
    }

    public void drawLine(float x1, float y1, float x2, float y2) {

        //Určení řídící osy
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            //Výměna koncových bodů
            if (x2 < x1) {
                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            float k = (y2 - y1) / (x2 - x1);

            //Vyhreslení bodu podle Y
            for (float i = x1; i <= x2; ++i) {
                this.drawPixel((int) i, (int) y1);
                y1 += k;
            }

        } else {
            //Výměna koncových bodů
            if (y2 < y1) {
                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            float k = (x2 - x1) / (y2 - y1);
            //Vyhreslení bodu podle X
            for (float i = y1; i <= y2; ++i) {
                this.drawPixel((int) x1, (int) i);
                x1 += k;
            }
        }
    }

    private void drawPixel(final int x, final int y) {
        //Blokace vykreslení mimo plochu
        if (x >= 0 && x < this.raster.getWidth() && y >= 0 && y < this.raster.getHeight()) {
            raster.setPixel(x, y, this.color);
        }
    }

    public void setColor(final int color) {
        this.color = color;
    }

}
