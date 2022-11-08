package fill;

import model.Edge;
import model.Point;
import model.Polygon;
import rasterize.Raster;
import rasterize.RasterBufferedImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanFiller {
    private final Raster raster;

    public ScanFiller(Raster raster) {
        this.raster = raster;
    }

    public void scanLine(Polygon polygon) {
        // init seznamu hran
        List<Edge> edges = new ArrayList<>();

        // projdu pointy a vytvořím z nich hrany
        for (int i = 0; i < polygon.getCount(); i++) {
            int nextIndex = (i + 1) % polygon.getCount();
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint(nextIndex);
            Edge edge = new Edge(p1,p2);
            // Pokud je horizontální, ignoruju
            if (edge.isHorizontal())
                continue;
            // Přidám hranu do seznamu
            edges.add(edge);
        }

        // Najít yMin, yMax
        int yMin = polygon.getPoint(0).getY();
        int yMax = yMin;
        for (Point p : polygon.getList()) {
            if (yMin>p.getY())
                yMin=p.getY();
            if (yMax<p.getY())
                yMax=p.getY();
        }

        // Pro Y od yMin po yMax
        for (int y = yMin; y <= yMax; y++) {
            List<Integer> pruseciky = new ArrayList<>();
                for(Edge h:edges){
                    if(h.isIntersection(y))
                        return;
                    pruseciky.add(h.getIntersection(y));
                }
            Collections.sort(pruseciky);
                int i = 1;
                while (i <= pruseciky.size()){
                    for (int x = pruseciky.get(i-1);x <= pruseciky.get(i);x++){
                        raster.setPixel(x,y, Color.RED.getRGB());
                    }
                    i+=2;
                }
        }

    }
}
