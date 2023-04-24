import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Basket implements Serializable{
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
        System.out.println("Список покупок");
        for (int i = 0; i<goods.length; i++){
            if(quantities[i]>0){
                int currentPrice = prices[i]*quantities[i];
                totalPrice += currentPrice;
                System.out.printf("%15s%4d p/шт%4d шт%6d p%n", goods[i], prices[i], quantities[i], currentPrice);
            }
        }
        System.out.printf("%dр", totalPrice);
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

    public void saveBin(File file){
        try(ObjectOutputStream strOut = new ObjectOutputStream(new FileOutputStream(file))) {
            strOut.writeObject(this);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBinFile(File file){
        Basket basket = null;
        try(ObjectInputStream strInput =  new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) strInput.readObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
