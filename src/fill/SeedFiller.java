package fill;

import rasterize.Raster;

public class SeedFiller{
    private final Raster raster;

    public SeedFiller(Raster raster) {
        this.raster = raster;
    }

    public void fill(int x, int y,int fillColor,int backColor) {
        // 1. načtu barvu z pixelu
        int pixelColor = raster.getPixel(x, y);
        // Je barva pixelu stejná jako barva pozadí?
        // Pokud ne -> končím
        if (pixelColor != backColor)
            return;

        // Pokud je -> pokračuju
        // obarvím pixel barvou fillColor
        raster.setPixel(x, y, fillColor);
        // rekurzivně zavolám metodu pro sousedy
        fill(x, y - 1,fillColor,backColor);
        fill(x, y + 1,fillColor,backColor);
        fill(x + 1, y,fillColor,backColor);
        fill(x - 1, y,fillColor,backColor);
    }
}
