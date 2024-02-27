public interface ShoppingManager {
    void menu();

    void addProduct();
    void deleteProduct();
    void printProductList();

    void readFile(String filename);

    void saveToFile(String filename);

}
