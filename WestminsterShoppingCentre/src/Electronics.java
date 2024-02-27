public class Electronics extends Product  {
    private String brand;
    private double wPeriod;
    //wPeriod- Warranty PERIOD

    public Electronics(String brand, double wPeriod,String productID,double price,String pName,int availQty) {
        super(productID,pName,availQty,price);
        this.brand = brand;
        this.wPeriod = wPeriod;

    }

    public String getBrand(){
        return this.brand;
    }

    public double getWPeriod(){
        return this.wPeriod;
    }

    public void setBrand(String brand){
        this.brand=brand;
    }
    public void setWPeriod(double wPeriod){
        this.wPeriod=wPeriod;
    }
    @Override
    public String getProductType(){
        return "Electronics";
    }

    @Override
    public String toString() {
        return "\nProduct ID: "+getProductID()+
                "\nProduct Name: "+getPName()+
                "\nNo of Items: "+getAvailQty()+
                "\nPrice per each: $"+getPrice()+
                "\nProduct Type: Electronics"+
                "\nProduct brand: "+this.brand+
                "\nProduct warranty: "+this.wPeriod+"\n";
    }
}
