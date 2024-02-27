import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class GUI extends WestminsterShoppingManager{
    JButton button1;
    JButton button2;
    String textString;
    ArrayList<Product> products;

    //When the add button is selected after clicking on a product, it gets added to this arrayList.
    static ArrayList<Product> productsT;
    static Font boldFont = new Font("Arial", Font.BOLD, 13);

    public GUI(ArrayList<Product> products) {
        this.products = products;
        this.productsT = new ArrayList<>();
    }

    public void gui() {
        JFrame gui = new JFrame("Westminster Shopping Center");
        ShoppingCartFrame handler = new ShoppingCartFrame();
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        p1.add(new JLabel("Select Product Category:"));

        JComboBox<String> type = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        p1.add(type);
        button1 = new JButton("Shopping Cart");
        button1.addActionListener(handler);
        p1.add(button1);
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.CENTER));

        DefaultTableModel table1 = new DefaultTableModel();
        JTable info = new JTable(table1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the cells uneditable
            }
        };

        table1.addColumn("Product ID");
        table1.addColumn("Name");
        table1.addColumn("Category");
        table1.addColumn("Price");
        table1.addColumn("Info");

        info.setRowHeight(50);

        // Populate the table initially
        updateTable(table1, "All");

        info.getTableHeader().setFont(boldFont);

        JPanel p3 = new JPanel(new GridLayout(1, 1, 10, 10));
        JTextArea tf1 = new JTextArea();
        tf1.setEditable(false);
        tf1.setFont(new Font("Arial", Font.PLAIN, 12));
        p3.add(tf1);

        type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = type.getSelectedItem().toString();
                updateTable(table1, selectedCategory);
            }
        });


        info.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        info.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = info.getSelectedRow();
                    if (selectedRow != -1) {
                        Object productID = info.getValueAt(selectedRow, 0);
                        textString = String.valueOf(productID);
                        updateProductDetails(tf1);
                    }
                }
            }
        });

        p2.add(new JScrollPane(info));

        JPanel p5 = new JPanel(new BorderLayout());
        p5.add(p2, BorderLayout.NORTH);
        p5.add(p3, BorderLayout.CENTER);

        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        button2 = new JButton("ADD ");
        p4.add(button2);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = info.getSelectedRow();
                if (selectedRow != -1) {
                    Object productID = info.getValueAt(selectedRow, 0);
                    for (Product product : products) {
                        if (product.getProductID().equals(productID)) {
                            productsT.add(product);
//                            addToCart(product);

                            break; // Exit the loop once the product is added
                        }
                    }
                }
                if(textString!=null){
                    for (Product product : products) {
                        if(product.getProductID().equals(textString)){
                            int quantity = product.getAvailQty();
                            product.setAvailQty(quantity - 1);
                        }

                    }
                    updateProductDetails(tf1);
                }
            }
        });

        gui.add(p1, BorderLayout.NORTH);
        gui.add(p4, BorderLayout.SOUTH);
        gui.add(p5, BorderLayout.CENTER);

        gui.setVisible(true);
        gui.setSize(700, 700);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static class ShoppingCartFrame extends ShoppingCart implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            // Display shopping cart details
            JFrame frame1 = new JFrame("Shopping Cart");
            JPanel c1 = new JPanel();
            c1.setLayout(new FlowLayout(FlowLayout.CENTER));
            JPanel c2 = new JPanel();


            // Create a table to display shopping cart details
            DefaultTableModel cartTableModel = new DefaultTableModel();
            JTable cartTable = new JTable(cartTableModel);
            cartTableModel.addColumn("Product ");
            cartTableModel.addColumn("Quantity");
//            cartTableModel.addColumn("Category");
            cartTableModel.addColumn("Price");
            cartTable.getTableHeader().setFont(boldFont);
            double totalCost = 0;
            pList=productsT;
            // Adding data to the table with products from the pList list
            for (Product product : pList) {
                String tabledata;
                if (product instanceof Electronics){
                    Electronics e=(Electronics) product;
                    tabledata=e.getBrand()+", "+e.getWPeriod();
                }
                else{
                    Clothing c=(Clothing) product;
                    tabledata=c.getSize()+", "+c.getColor();
                }
                Object[] rowData = {product.getProductID()+" , "+product.getPName()+" , "+tabledata, 1, product.getPrice()};
                cartTableModel.addRow(rowData);
                totalCost = calculateTotalCost(product.getPrice(), totalCost);

            }
            c1.add(new JScrollPane(cartTable));
            c1.setLayout(new FlowLayout(FlowLayout.CENTER));
            cartTable.setRowHeight(50);
            JTextArea totalPriceTextArea = new JTextArea("\n\n\nTotal Price : $" + totalCost+
                    "\n\n\nDiscount : $"+totalCost*0.1+
                    "\n\n\nDiscounted Price : $"+totalCost*0.9);
//            c2.add(totalPriceTextArea, BorderLayout.CENTER);
            Font boldFont = new Font(totalPriceTextArea.getFont().getFontName(), Font.BOLD, totalPriceTextArea.getFont().getSize());
            totalPriceTextArea.setFont(boldFont);

            // Match the background color
            totalPriceTextArea.setBackground(c1.getBackground());

            c2.add(totalPriceTextArea, BorderLayout.CENTER);

            frame1.add(c1, BorderLayout.NORTH);
            frame1.add(c2, BorderLayout.CENTER);
            frame1.setVisible(true);
            frame1.setSize(700, 700);
        }
    }

    public void updateTable(DefaultTableModel tableModel, String selectedCategory) {
        // Clear the existing rows
        tableModel.setRowCount(0);

        // Sort the products based on the selected category
        sortProducts(selectedCategory);

        // Adds all the products to the table
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            String data;
            Product p = iterator.next();
            if (selectedCategory.equals("All") || selectedCategory.equals(p.getProductType())) {
                if (p instanceof Electronics){
                    Electronics e=(Electronics) p;
                    data=e.getBrand()+", "+e.getWPeriod();
                }
                else{
                    Clothing c=(Clothing) p;
                    data=c.getSize()+", "+c.getColor();
                }
                Object[] rowData = {p.getProductID(), p.getPName(), p.getProductType(), p.getPrice(),data};
                tableModel.addRow(rowData);
            }
        }
    }
    private void updateProductDetails(JTextArea tf1){
        for (Product product : products) {
            if (product.getProductID().equals(textString)) {
                if (product.getProductType().equals("Electronics")) {
                    Electronics electronicI = (Electronics) product;
                    tf1.setText("\nProduct ID : " + product.getProductID() + "\nType : " + product.getProductType() + "\nProduct Name: " + product.getPName() + "\nProduct Brand : " + electronicI.getBrand() + "\nWarranty Period : " + electronicI.getWPeriod() + "\nAvailable No of Items : " + product.getAvailQty());
                } else {
                    Clothing clothingI = (Clothing) product;
                    tf1.setText("\nProduct ID : " + product.getProductID() + "\nType : " + product.getProductType() + "\nProduct Name: " + product.getPName() + "\nProduct colour : " + clothingI.getColor() + "\nSize : " + clothingI.getSize() + "\nAvailable No of Items : " + product.getAvailQty());
                }
            }
        }
    }

    // Sorting the products based on the selected category
    private void sortProducts(String selectedCategory) {
        if (selectedCategory.equals("Clothing")) {
            Collections.sort(products, Comparator.comparing(Product::getPName));
        } else if (selectedCategory.equals("Electronics")) {
            Collections.sort(products, Comparator.comparing(Product::getPName));
        } else {
            Collections.sort(products, Comparator.comparing(Product::getProductType).thenComparing(Product::getPName));
        }
    }

    private void addToCart(Product product) {
        // Adding the selected product to the shopping cart
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(product);
    }


}