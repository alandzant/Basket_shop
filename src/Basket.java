import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

import static java.lang.System.out;

public class Basket {
    private static final long serialVersionUID  = 1L;
    private String[] goods;
    private int[] prices;
    private int[] quantities;

    public Basket(){

    }

    public Basket(String[] goods, int[] prices){
        this.goods = goods;
        this.prices = prices;
        this.quantities = new int[goods.length];

    }

    public void addToCart( int productNum, int amount) {
        quantities[productNum] += amount;
    }

    public void printCart(){
        int totalPrice = 0;
        out.println("Список покупок");
        for (int i = 0; i<goods.length; i++){
            if(quantities[i]>0){
                int currentPrice = prices[i]*quantities[i];
                totalPrice += currentPrice;
                out.printf("%15s%4d p/шт%4d шт%6d p%n", goods[i], prices[i], quantities[i], currentPrice);
            }
        }
        out.printf("%dр", totalPrice);
    }



    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String good : goods) {
                out.print(good + " ");
            }
            out.println();

            for (int quantity : quantities) {
                out.print(quantity + " ");
            }
            out.println();

            for (int price : prices) {
                out.print(price + " ");
            }
        }
    }

    public static Basket loadFromTxtFile(File textFile){
        Basket basket = new Basket();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String goodsFil = bufferedReader.readLine();
            String quantitiesFil = bufferedReader.readLine();
            String pricesFil = bufferedReader.readLine();

            basket.goods = goodsFil.split(" ");
            basket.quantities = Arrays.stream(quantitiesFil.split(" ")).map(Integer:: parseInt).mapToInt(Integer::intValue).toArray();
            basket.prices = Arrays.stream(pricesFil.split(" ")).map(Integer:: parseInt).mapToInt(Integer::intValue).toArray();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return basket;

    }





    public void saveJson(File jsonFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(jsonFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            writer.print(json);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromJsonFile(File jsonFile){
        Basket basket = null;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine())!=null){
                builder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(), Basket.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return basket;

    }
}

