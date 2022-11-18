package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    public List<Point> points;

    public Polygon() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public int getCount() {
        return points.size();
    }

    public List<Point> getList() {
        return new ArrayList<>(points);
    }

    public List<Edge> getEdge() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            if (i != points.size() - 1) {
                edges.add(new Edge(getPoint(i), getPoint(i + 1)));
            } else {
                edges.add(new Edge(getPoint(i), getPoint(0)));
            }
        }
        return edges;
    }

    public List<Line> getLines() {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            if (i != points.size() - 1) {
                lines.add(new Line(getPoint(i).getX(), getPoint(i).getY(), getPoint(i + 1).getX(), getPoint(i + 1).getY()));
            } else {
                lines.add(new Line(getPoint(i).getX(), getPoint(i).getY(), getPoint(0).getX(), getPoint(0).getY()));
            }
        }
        return lines;
    }

    public void setList(List<Point> points) {
        this.points = points;
    }

    public void setPoint(int index, Point p) {
        points.set(index, p);
    }

    public void clear() {
        points.clear();
    }
}
