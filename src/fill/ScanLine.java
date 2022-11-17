package fill;

import model.Edge;
import model.Line;
import model.Polygon;
import model.Point;
import rasterize.FilledLineRasterizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine {

    private final Polygon enges;
    private final FilledLineRasterizer lineRasterizer;

    public ScanLine(Polygon enges, FilledLineRasterizer lineRasterizer) {
        this.enges = enges;
        this.lineRasterizer = lineRasterizer;
    }

    public void fill() {
        if (enges.getList().size()<=2) return;
        //deklarace a inicializace pomocného Polygonu
        Polygon pomLines = new Polygon();
        //Max,Min Y
        Integer yMin = null;
        Integer yMax = null;
        //Pro všechny Line v enges
        for (Edge edge : enges.getEdge()) {
            //vyřazení horizontálních
            if (edge.getY1() != edge.getY2()) {
                //určení horního a dolního bodu
                if (edge.getY1() > edge.getY2()) {
                    pomLines.addPoint(new Point(edge.getX1(),edge.getY1()));
                    pomLines.addPoint(new Point(edge.getX2(),edge.getY2()));
                } else {
                    pomLines.addPoint(new Point(edge.getX2(),edge.getY2()));
                    pomLines.addPoint(new Point(edge.getX1(),edge.getY1()));
                }
            }
            //Určení Max,Min Y
            if (yMin == null) yMin = edge.getY2();
            if (yMax == null) yMax = edge.getY1();
            if (yMin > edge.getY2()) yMin = edge.getY2();
            if (yMax < edge.getY1()) yMax = edge.getY1();
        }
        //Od minima po maximum vykresli Line
        try {
            for (int y = yMin + 1; y < yMax; y++) {
                List<Integer> pruseciky = new ArrayList<>();
                for (Edge edge : pomLines.getEdge()) {
                    //Je na  y na enges ?
                    if (edge.inside(y)) {
                        //vypočítej x pro y na enges
                        pruseciky.add(edge.intersection(y));
                    }
                }
                //srovnej x
                Collections.sort(pruseciky);
                //vykresli
                for (int i = 0; i < pruseciky.size(); i += 2) {
                    if (pruseciky.size() > i + 1) {
                        lineRasterizer.drawLine(new Point(pruseciky.get(i), y), new Point(pruseciky.get(i + 1), y));
                        for (Edge usecka : enges.getEdge()) {
                            lineRasterizer.drawLine(usecka.getX1(),usecka.getY1(),usecka.getX2(),usecka.getY2());
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}

