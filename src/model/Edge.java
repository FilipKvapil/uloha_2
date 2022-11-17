package model;

public class Edge{
    private final int x1, y1, x2, y2;
    private float k, q;

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        calculate();
    }
    public Edge(Point p1,Point p2) {
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
        calculate();
    }
    private void calculate (){
        if (!isHorizontal()) {
            this.k = (x2 - x1) / (float)(y2 - y1);
            this.q = x1 - k * y1;
        }
    }
    public boolean isHorizontal() {
        return y1 == y2;
    }

    public boolean inside (Point p){
        final Point v1 = new Point(x2 - x1, y2 - y1);
        final Point n1 = new Point(-v1.y, v1.x);
        final Point v2 = new Point(p.x - x1, p.y - y1);
        return n1.x * v2.x + n1.y * v2.y < 0.0;
    }
    public Point intersection(final Point v1, final Point v2) {
        final int px = ((v1.x * v2.y - v1.y * v2.x) * (x1 - x2) - (x1 * y2 - y1 * x2) * (v1.x - v2.x)) / ((v1.x - v2.x) * (y1 - y2) - (x1 - x2) * (v1.y - v2.y));
        final int py = ((v1.x * v2.y - v1.y * v2.x) * (y1 - y2) - (x1 * y2 - y1 * x2) * (v1.y - v2.y)) / ((v1.x - v2.x) * (y1 - y2) - (x1 - x2) * (v1.y - v2.y));
        return new Point(px, py);
    }

}
