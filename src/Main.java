import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
public class Main {
    static Scanner scanner = new Scanner(System.in);


    static String[] products = {"Хлеб", "Яблоки", "Молоко"};
    static int[] prices = {100, 200, 300};
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        Settings settings = new Settings(new File("shop.xml"));

        File loadFile = new File(settings.fileNameLoad);
        File saveFile = new File(settings.fileNameSave);
        File logFile = new File(settings.fileNameLog);

        Basket basket = createBasket(loadFile,settings.Load, settings.formatLoad);
        ClientLog saveLog = new ClientLog();


        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }



        while (true) {
            System.out.println("Выберите товар и количество или введите 'end'");
            String line = scanner.nextLine();
            if ("end".equals(line)) {
                if (settings.Log){
                    saveLog.exportAsCSV(logFile);
                }
                break;
            }

            String[] parts = line.split(" ");
            int productNum = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNum, productCount);
            if (settings.Log){
                saveLog.log(productNum,productCount);
            }
            if (settings.Save){
                switch (settings.formatSave){
                    case "json" -> basket.saveJson(saveFile);
                    case "txt" -> basket.saveTxt(saveFile);

                }
            }
        }
        basket.printCart();


    }

    private static Basket createBasket(File loadFile, boolean isLoad, String formatLoad) {
        Basket basket;
        if(isLoad && loadFile.exists()){
            basket = switch (formatLoad){
                case "json" -> Basket.loadFromJsonFile(loadFile);
                case "txt" -> Basket.loadFromTxtFile(loadFile);
                default -> new Basket(products, prices);
            };
        }
        else{
            basket = new Basket(products, prices);
        }
        return basket;
    }
}
