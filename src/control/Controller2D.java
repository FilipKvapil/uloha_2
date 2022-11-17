package control;

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
        polygonRasterizer.drawPolygon(polygon);
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
                    hardClear();
                    if(polygon.getCount()>2) new ScanLine(polygon, lineRasterizer).run();
                    polygonRasterizer.drawPolygon(polygon);
                    if (polygon.getCount() == 2){
                        hardClear();
                        lineRasterizer.drawLine(polygon.getPoint(polygon.getCount()-2),new Point(e.getX(),e.getY()));
                    }
                    panel.repaint();
                }
                //seedFiller
                if (e.getButton() == MouseEvent.BUTTON3 &&polygon.getCount() >= 3){
                    System.out.println("ahoj");
                    seedFiller.fill(e.getX(), e.getY(), Color.RED.getRGB(),Color.BLACK.getRGB());
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            //Stisknutí tlačítka myši a přetažení
            @Override
            public void mouseDragged(MouseEvent e) {
                if (polygon.getCount()==1){
                    hardClear();
                    lineRasterizer.drawLine(polygon.getPoint(0),new Point(e.getX(),e.getY()));
                    panel.repaint();
                } else {
                    hardClear();
                    Polygon polygon1 = new Polygon();
                    polygon1.setList(polygon.getList());
                    polygon1.addPoint(new Point(e.getX(),e.getY()));
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

    private void draw (){
        panel.clear();
        polygonRasterizer.drawPolygon(polygon);
        panel.repaint();
    }
    private void update() {
//        panel.clear();

    }

    private void hardClear() {
        panel.clear();
    }

}
