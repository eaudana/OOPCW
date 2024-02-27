import java.util.ArrayList;

public class ShoppingCart {
    public static ArrayList<Product> pList;

    public ShoppingCart() {
        this.pList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        pList.add(product);
    }

    public void deleteProduct(Product product) {
        pList.remove(product);
    }

    public double calculateTotalCost(double price, double total) {
        total = total + price;
        return total;
    }



}
