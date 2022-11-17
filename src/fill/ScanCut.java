package fill;

import model.Edge;
import model.Point;
import model.Polygon;

public class ScanCut {

    // polygon který budeme ořezávat a šablona
    public Polygon cut (Polygon polygon, Polygon clipPolygon){
        Polygon newPolygon = polygon;
        Point p1 = clipPolygon.getList().get(clipPolygon.getList().size() - 1);
        //prokaždý bod v clipPointu udělej
        for (final Point p2 : clipPolygon.getList()) {
            polygon = this.cutEdge(polygon, new Edge(p1, p2));
            newPolygon = polygon;
            p1 = p2;
        }
        return newPolygon;
    }
    private Polygon cutEdge (Polygon polygon, Edge edge){
        Polygon out = new Polygon();
        Point v1 = polygon.getList().get(polygon.getCount()-1);
        for (final Point v2 : polygon.getList()) {
            if (edge.inside(v2)) {
                if (!edge.inside(v1)) {
                    out.addPoint(edge.intersection(v1, v2));
                }
                out.addPoint(v2);
            }
            else if (edge.inside(v1)) {
                out.addPoint(edge.intersection(v1, v2));
            }
            v1 = v2;
        }
        return out;
    }
}
