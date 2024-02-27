public class Clothing extends Product {
    private String color;
    private String size;

    public Clothing(String color,String size,String productID,double price,String pName,int availQty) {
        super(productID,pName,availQty,price);
        this.size=size;
        this.color=color;
    }

    public String getColor() {
        return color;
    }

    public String getSize(){
        return size;
    }

    public void setColor(String color){
        this.color=color;
    }

    public void setSize(String size){
        this.size=size;
    }

    @Override
    public String getProductType(){
        return "Clothing";
    }

    @Override
    public String toString() {
        return "\nProduct ID: "+getProductID()+
                "\nProduct Name: "+getPName()+
                "\nNo of Items: "+getAvailQty()+
                "\nPrice per each item: $"+getPrice()+
                "\nProduct Type: Clothing"+
                "\nProduct Size: "+this.size+
                "\nProduct Color: "+this.color+"\n";
    }

}
