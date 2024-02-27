import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class WestminsterShoppingManagerTest {
    private WestminsterShoppingManager test;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Before
    public void setUp() throws Exception {
        test = new WestminsterShoppingManager();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws Exception {
        test = null;
        System.setOut(System.out);
    }

    @Test
    public void addProduct() {

        Electronics e2 = new Electronics("Sony", 1.5, "P12345", 50.0, "Test Electronics", 10);
        WestminsterShoppingManager.productList.add(e2);


        assertEquals("P12345", e2.getProductID());
        assertEquals("Sony", e2.getBrand());
        assertEquals("Test Electronics", e2.getPName());
        assertEquals(1.50, e2.getWPeriod(), 0.001);
        assertEquals(50.0, e2.getPrice(), 0.001);
        assertEquals(10, e2.getAvailQty());

    }

    @Test
    public void deleteProduct() {
        Electronics e2 = new Electronics("Sony", 1.5, "P12345", 50.0, "Test Electronics", 10);
        WestminsterShoppingManager.productList.add(e2);

        // Mock user input for testing
        String mockInput = "P12345\n";
        System.setIn(new ByteArrayInputStream(mockInput.getBytes()));

        test.deleteProduct();
        ArrayList<Product> productList = WestminsterShoppingManager.productList;
        assertEquals(0, productList.size());
        assertTrue(outContent.toString().contains("You have successfully removed the product with product ID-P12345"));
    }

    @Test
    public void printProductList() {
//        Electronics electronics = new Electronics("Sony", 1.5, "P12345", 50.0, "Test Electronics", 10);
        Clothing clothing = new Clothing("Blue", "M", "P17890", 25.0, "Test Clothing", 55);
//        WestminsterShoppingManager.productList.add(electronics);
        WestminsterShoppingManager.productList.add(clothing);

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        test.printProductList();
        String printedOutput = outputStream.toString().trim();

        assertTrue(printedOutput.contains("Product ID: P17890\n" +
                "Product Name: Test Clothing\n" +
                "No of Items: 55\n" +
                "Price per each item: $25.0\n" +
                "Product Type: Clothing\n" +
                "Product Size: M\n" +
                "Product Color: Blue"));

    }


    @Test
    public void readFile() {
    }

    @Test
    public void saveToFile() {
    }
}