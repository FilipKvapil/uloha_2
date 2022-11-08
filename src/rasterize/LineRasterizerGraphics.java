package rasterize;


import model.Point;

import java.awt.*;

public class LineRasterizerGraphics extends LineRasterizer {


    public LineRasterizerGraphics(Raster raster) {
        super(raster);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        Graphics g = ((RasterBufferedImage)raster).getImg().getGraphics();
        g.setColor(this.color);
        g.drawLine(x1, y1, x2, y2);
    }
    public void drawLine(Point p1, Point p2) {
        Graphics g = ((RasterBufferedImage)raster).getImg().getGraphics();
        g.setColor(this.color);
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    public void drawLine(int x1, int y1, int x2, int y2,Color color) {
        Graphics g = ((RasterBufferedImage)raster).getImg().getGraphics();
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }
    public void drawLine(Point p1, Point p2,Color color) {
        Graphics g = ((RasterBufferedImage)raster).getImg().getGraphics();
        g.setColor(color);
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
}
