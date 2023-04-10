package model;
public class Billboard {
    private String brand;
    private boolean inUse;
    private int height;
    private int width;
    public Billboard(int width, int height,boolean inUse, String brand ){
        this.width = width;
        this.height = height;
        this.inUse = inUse;
        this.brand = brand;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public boolean isInUse() {
        return inUse;
    }
    public String getBrand() {
        return brand;
    }

}
