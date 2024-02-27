
import java.io.Serializable;


public abstract class Product implements Serializable {
    private String productID;
    private String pName;
    private int availQty;
    private double price;

    public Product(String productID, String pName, int availQty, double price) {
        this.productID = productID;
        this.pName = pName;
        this.availQty = availQty;
        this.price = price;
    }

    public String getProductID() {
        return this.productID;
    }

    public String getPName() {
        return this.pName;
    }

    public int getAvailQty() {
        return this.availQty;
    }

    public double getPrice() {
        return this.price;
    }

    public abstract String getProductType();

    public void setAvailQty(int availQty) {
        this.availQty = availQty;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

