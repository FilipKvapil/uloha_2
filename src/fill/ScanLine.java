package fill;

import model.Line;
import model.Polygon;
import model.Point;
import rasterize.FilledLineRasterizer;
import rasterize.Raster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine {

    private final Polygon lines;
    private final FilledLineRasterizer lineRasterizer;

    public ScanLine(Polygon lines, FilledLineRasterizer lineRasterizer) {
        this.lines = lines;
        this.lineRasterizer = lineRasterizer;
    }

    public void run() {
        //deklarace a inicializace pomocného Polygonu
        Polygon pomLines = new Polygon();
        //Max,Min Y
        Integer yMin = null;
        Integer yMax = null;
        //Pro všechny Line v lines
        for (Line line : lines.getLines()) {
            //vyřazení horizontálních
            if (line.getY1() != line.getY2()) {
                //určení horního a dolního bodu
                if (line.getY1() > line.getY2()) {
                    pomLines.addPoint(new Point(line.getX1(),line.getY1()));
                    pomLines.addPoint(new Point(line.getX2(),line.getY2()));
                } else {
                    pomLines.addPoint(new Point(line.getX2(),line.getY2()));
                    pomLines.addPoint(new Point(line.getX1(),line.getY1()));
                }
            }
            //Určení Max,Min Y
            if (yMin == null) yMin = line.getY2();
            if (yMax == null) yMax = line.getY1();
            if (yMin > line.getY2()) yMin = line.getY2();
            if (yMax < line.getY1()) yMax = line.getY1();
        }
        //Od minima po maximum vykresli Line
        try {
            for (int y = yMin + 1; y < yMax; y++) {
                List<Integer> pruseciky = new ArrayList<>();
                for (Line line : pomLines.getLines()) {
                    //Je na  y na lines ?
                    if (y <= line.getY1() && y >= line.getY2() && line.getY1() != line.getY2() && y <= (line.getY1() - 1) && y >= line.getY2()) {
                        //vypočítej x pro y na lines
                        float k = (line.getX1() - line.getX2()) / (float) (line.getY1() - line.getY2());
                        float x = (line.getX1() - (line.getY1() - y) * k);
                        pruseciky.add(Math.round(x));
                    }
                }
                //srovnej x
                Collections.sort(pruseciky);
                //vykresli
                for (int i = 0; i < pruseciky.size(); i += 2) {
                    if (pruseciky.size() > i + 1) {
                        lineRasterizer.drawLine(new Point(pruseciky.get(i), y), new Point(pruseciky.get(i + 1), y));
                        for (Line usecka : lines.getLines()) {
                            lineRasterizer.drawLine(usecka);
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}

