import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    public static ArrayList<Product> productList;

    public WestminsterShoppingManager() {
        productList = new ArrayList<>();
    }


    public void menu() {
        readFile("Products.ser");
        boolean menuLoop = true;
        while (menuLoop) {
            try {
                System.out.println("\n---------------------Menu---------------------");
                Scanner obj = new Scanner(System.in);
                System.out.println("1) Add a product to the system" +
                        "\n2) Delete a product from the system" +
                        "\n3) Get a list of all the products" +
                        "\n4) Save the list of products into a file." +
                        "\n5) Open GUI" +
                        "\n6) Exit from menu" +
                        "\n----------------------------------------------\nPlease select any option:");
                int ans = obj.nextInt();
                switch (ans) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        deleteProduct();
                        break;
                    case 3:
                        printProductList();
                        break;
                    case 4:
                        saveToFile("Products.ser");
                        break;
                    case 5:
                        GUI g = new GUI(productList);
                        g.gui();
                        break;
                    case 6:
                        menuLoop = false;
                        break;
                    default:
                        System.out.println("Invalid input.Please select an option between 1 and 6");

                }
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a number.");
            }

        }
    }


    public void addProduct() {
        boolean loopChecker = true;
        String type;
        while (loopChecker) {
            if (productList.size() <= 50) {
                Scanner obj = new Scanner(System.in);
                System.out.println("Please enter the product type(E=>Electronics/C=>Clothing):");
                type = obj.nextLine();
                if (type.equals("E") || type.equals("C")) {
                    String productID = IDvalidator();
                    String productN = validate("Please enter the product name:");
                    double price = validate("Please enter price of the product: $", 0.0);
                    int quantity = validate("Please enter the quantity:", 0);
                    if (type.equals("E")) {
                        String brand = validate("Please enter the brand of the product:");
                        double wPeriod = validate("Please enter the warranty period:", 0.0);
                        Electronics e = new Electronics(brand, wPeriod, productID, price, productN, quantity);
                        productList.add(e);
                    } else {
                        String colour = validate("Please enter the colour of the clothing item:");
                        String size = validate("Please enter the size of the clothing item:");
                        Clothing c = new Clothing(colour, size, productID, price, productN, quantity);
                        productList.add(c);
                    }
                    System.out.println("The product has been successfully added to the system.");
                    System.out.println("PLEASE SAVE YOUR FILE!");
                    break;
                } else {
                    System.out.println("Invalid input.Please enter the character E or C( E => Electronics/C => Clothing):");

                }

            } else {
                System.out.println("You have reached your maximum limit of 50 products.Please delete a product if you want to add a new product.");

            }
        }
    }

    public void deleteProduct() {
        boolean flag = true;
        if (productList.isEmpty()) {
            System.out.println("No items have been added to the system yet.");
        } else {
            Scanner obj = new Scanner(System.in);
            System.out.println("Please enter the product ID of the product that needs to be deleted:");
            String ans = obj.nextLine();
            for (Product product : productList) {
                if (product.getProductID().equals(ans)) {
                    productList.remove(product);
                    System.out.println("You have successfully removed the product with product ID-" + product.getProductID()
                            + "\n\nCategory:" + product.getProductType()
                            + "\nProduct Name:" + product.getPName()
                            + "\n\nThere are " + productList.size() + " products remaining in the system."
                            + "\n\nPLEASE SAVE THIS FILE!");
                    flag = false;
                    break;
                }
            }
            if (flag) {
                System.out.println("The ID you entered does not exist.");
            }
        }
    }

    public void printProductList() {
        Collections.sort(productList, Comparator.comparing(Product::getProductID));
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    public void readFile(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<Product> savedProductList = (ArrayList<Product>) inputStream.readObject();
            inputStream.close();
            productList.addAll(savedProductList);
            System.out.println("Product list loaded from file: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("A file will be created to save product details.");
        }
    }

    public void saveToFile(String filename) {
        try {
            FileOutputStream fileStream = new FileOutputStream(filename);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileStream);
            outputStream.writeObject(productList);
            outputStream.flush();
            outputStream.close();
            System.out.println("The data was saved successfully");
        } catch (Exception e) {
            System.out.println("An error occurred, please try again later.");
        }

    }


    /*Methods used for the validation of various inputs are given below*/


    //Method to valid the Product ID.
    private String IDvalidator() {
        String productId;
        boolean validator;
        String expectedFormat = "P\\d{5}";
        while (true) {
            Scanner obj = new Scanner(System.in);
            System.out.println("Please enter the product ID(eg-PXXXXX):");
            productId = obj.nextLine();
            if (productId.matches(expectedFormat)) {
                validator = true;
                for (Product product : productList) {
                    if (productId.equals(product.getProductID())) {
                        System.out.println("The product ID is already in use.");
                        validator = false;  // Set validator to false if product ID is found
                        break;
                    }
                }
                if (validator) {
                    break;
                }
            } else {
                System.out.println("Invalid format. Please enter the ID in the format PXXXXX.");
            }
        }
        return productId;
    }

    //Overloaded the validate method thrice
    private double validate(String msg, double value) {
        boolean loop = true;
        while (loop) {
            try {
                Scanner obj = new Scanner(System.in);
                System.out.println(msg);
                value = obj.nextDouble();
                if (value < 0) {
                    System.out.println("You have entered a negative number.Please enter a positive number.");

                } else {
                    loop = false;

                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a numerical value.");

            }
        }
        return value;
    }

    //Used to validate integer values.
    private int validate(String msg, int value) {
        boolean loop = true;
        while (loop) {
            try {
                Scanner obj = new Scanner(System.in);
                System.out.println(msg);
                value = obj.nextInt();
//                if !(containsSpace(value)) {
                if (value < 0) {
                    System.out.println("You have entered a negative number.Please enter a positive number.");
                } else {
                    loop = false;

                }
//                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a numerical value.");
            }
        }
        return value;
    }

    //Used to validate string inputs.
    private String validate(String msg) {
        boolean loop = true;
        String input = null;
        while (loop) {
            Scanner obj = new Scanner(System.in);
            System.out.println(msg);
            input = obj.nextLine();
            if (isValidString(input)) {
                break;
            } else {
                System.out.println("Invalid input, please try again.");
            }
        }
        return input;
    }

    //Used to make sure that the string values don't contain just numbers.
    private boolean isValidString(String input) {
        return input.matches(".*[a-zA-Z].*");
    }
}
