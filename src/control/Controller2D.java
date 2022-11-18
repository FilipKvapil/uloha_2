package control;

import fill.ScanCut;
import fill.ScanLine;
import fill.SeedFiller;
import model.Point;
import model.Polygon;
import rasterize.*;
import view.Panel;

import java.awt.*;
import java.awt.event.*;

public class Controller2D implements Controller {

    private final Panel panel;

    private int x,y;
    private FilledLineRasterizer lineRasterizer;
    private final Polygon polygon = new Polygon();
    private final Polygon polygon2 = new Polygon();
    private PolygonRasterizer polygonRasterizer;
    private SeedFiller seedFiller;

    public Controller2D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRaster());
        initListeners(panel);
    }

    public void initObjects(Raster raster) {
        lineRasterizer = new FilledLineRasterizer(raster);
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);
        seedFiller = new SeedFiller(raster);
        polygon2.addPoint(new Point(0,0));
        polygon2.addPoint(new Point(300,300));
        polygon2.addPoint(new Point(0,300));
        hardClear();
     }

    @Override
    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {

           /* //Stisknutí tlačítka myši
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && polygon.getCount() == 0)
                    polygon.addPoint(new Point(e.getX(), e.getY()));
            }*/
            //Uvolnění tlačítka myši
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    polygon.addPoint(new Point(e.getX(),e.getY()));
                    drawPolygon(polygon);
                    if (polygon.getCount() == 2){
                        drawLine(polygon.getPoint(polygon.getCount()-2),new Point(e.getX(),e.getY()));
                    }
                }
                //seedFiller
                if (e.getButton() == MouseEvent.BUTTON3){
                    seedFiller.fill(e.getX(), e.getY(), Color.GREEN.getRGB(),panel.getRaster().getPixel(e.getX(), e.getY()));
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            //Stisknutí tlačítka myši a přetažení
            @Override
            public void mouseDragged(MouseEvent e) {
                if (polygon.getCount()<=1 && (e.getButton() == MouseEvent.BUTTON1)){
                    if(polygon.getCount()==0) polygon.addPoint(new Point(e.getX(), e.getY()));
                    drawLine(polygon.getPoint(0),new Point(e.getX(),e.getY()));
                } else if(e.getButton() == MouseEvent.BUTTON1){
                    Polygon polygon1 = new Polygon();
                    polygon1.setList(polygon.getList());
                    polygon1.addPoint(new Point(e.getX(),e.getY()));
                    hardClear();
                    polygonRasterizer.drawPolygon(polygon1);
                    panel.repaint();
                }
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // na klávesu C vymazat plátno
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    hardClear();
                }
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize();
                initObjects(panel.getRaster());
            }
        });
    }


    private void drawPolygon (Polygon polygon){
        hardClear();
        polygonRasterizer.drawPolygon(polygon);
        Polygon newPolygon = new ScanCut().cut(polygon2,polygon);
        new ScanLine(newPolygon,lineRasterizer).fill();
        panel.repaint();
    }
    private void drawLine (Point p1, Point p2){
        hardClear();
        lineRasterizer.drawLine(p1,p2);
        panel.repaint();
    }
    private void hardClear() {
        panel.clear();
        lineRasterizer.setColor(Color.BLUE.getRGB());
        new ScanLine(polygon2,lineRasterizer).fill();
        lineRasterizer.setColor(Color.RED.getRGB());
        panel.repaint();
    }

}
