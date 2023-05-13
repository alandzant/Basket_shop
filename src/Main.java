import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static File file = new File("basket.json");
    public static void main(String[] args) throws IOException {



        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        Basket basket = null;
        if (file.exists()){
            basket = Basket.loadFromJsonFile(file);
        }
        else{
            basket = new Basket(products, prices);
        }

        ClientLog saveLog = new ClientLog();
        while (true) {
            System.out.println("Выберите товар и количество или введите 'end'");
            String line = scanner.nextLine();
            if ("end".equals(line)) {
                saveLog.exportAsCSV(new File("log.csv"));
                break;
            }
            String[] parts = line.split(" ");
            int productNum = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNum, productCount);
            saveLog.log(productNum,productCount);
            basket.saveJson(file);
        }
        basket.printCart();
    }


}
