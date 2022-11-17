package model;

public class Edge{
    private final int x1, y1, x2, y2;
    private float k;

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
            float q = x1 - k * y1;
        }
    }
    public boolean isHorizontal() {
        return y1 == y2;
    }
    public boolean isIntersection(int y) {
       return y >= y1 && y < y2;
        //return y<=y1 && y>=y2 && y1!=y2 && y<=(y1-1);
    }

    public int getIntersection(int y) {
        //return (int) (this.k * y + this.q);
        return Math.round(x1-(y1-y)*k);
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}
