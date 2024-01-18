package samuelesimeone;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import samuelesimeone.categorie.Category;
import samuelesimeone.classi.Customer;
import samuelesimeone.classi.Order;
import samuelesimeone.classi.Product;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    static Random rdm = new Random();
    static Faker faker = new Faker(Locale.ITALY);


    static Supplier<Integer> takeCategory = () ->{
        Integer t = rdm.nextInt(1,4);
        return t;
    };
    static Supplier<Integer> takeNProduct = () ->{
        Integer n = rdm.nextInt(50);
        return n;
    };
   static Supplier<Long> takeId = ()->{
        Long id = rdm.nextLong(10000000);
        return id;
    };
   static Supplier<Double> takePrice = () ->{
     Double p = rdm.nextDouble(200.0);
     return p;
   };
   static Supplier<Customer> generateUser = () -> {
        Integer t = takeCategory.get();
        Long id = takeId.get();
        return new Customer(id,faker.name().firstName(), t);
    };

    public static void main(String[] args) {

        System.out.println("Esercizio D4");



        Integer nC = takeNProduct.get();
        LocalDate inizio = LocalDate.parse("2021-02-01");
        LocalDate fine = LocalDate.parse("2021-04-01");
        LocalDate today = LocalDate.now();
        Predicate<LocalDate> isBetween = x -> (x.isAfter(inizio)) && (x.isBefore(fine));
        Predicate<LocalDate> isAfter = x -> x.isBefore(today);

        List<Product> warehouse = new ArrayList<>();
        List<Customer> utenti = new ArrayList<>();
        List<Order> ordini = new ArrayList<>();


        generateCustomer(utenti);


        if (nC == 0){
            System.out.println("Non ci sono prodotti in magazzino");
        }else {
            System.out.println("Prodotti in magazzino: " + nC);
            for (int j = 0; j < nC; j++) {
                Integer t = takeCategory.get();
                Double p = takePrice.get();
                Long i = takeId.get();
                String str;
                if (t == 1){
                    str = faker.book().title();
                    warehouse.add(new Product(i,str, Category.books, p));
                } else if (t == 2) {
                    str = faker.commerce().productName();
                    warehouse.add(new Product(i,str,Category.baby,p));
                }else {
                    str = faker.commerce().productName();
                    warehouse.add(new Product(i,str,Category.boys,p));
                }
            }
            Integer nCO = takeNProduct.get();
            for (int i = 0; i < nCO; i++) {
                Integer t = rdm.nextInt(1,3);
                Long id = takeId.get();
                List<Product> order = new ArrayList<>();
                order.addAll(generateOrder(warehouse));
                LocalDate orderDay = generateData();
                if (isAfter.test(orderDay.plusDays(5))) {
                    ordini.add(new Order(id,"completed", orderDay, orderDay.plusDays(5), order, takeOneCustomer(utenti)));
                }else {
                    ordini.add(new Order(id,"Pending", orderDay, orderDay.plusDays(5), order, takeOneCustomer(utenti)));
                }

            }

        }

        utenti.stream().forEach(System.out::println);
        warehouse.stream().forEach(System.out::println);
        ordini.stream().forEach(System.out::println);

        System.out.println("******************************** Esercizio 1 *********************************************");
        Map<String, List<Order>> riassuntoOrderCustomer = ordini.stream().collect(Collectors.groupingBy(order -> order.getCustomer().name));
       riassuntoOrderCustomer.forEach((nome,ordine) -> System.out.println("Customer: " + nome + ", Ordini:" + ordine));

        System.out.println("******************************** Esercizio 2 *********************************************");
        riassuntoOrderCustomer.forEach((nome,ordine) -> {
            Double tot = 0.0;
            for (int i = 0; i < ordine.size(); i++) {
               tot = ordine.get(i).getTotal();
            }
            System.out.println("Customer: " + nome + ", Totale:" + tot);
        });

        System.out.println("******************************** Esercizio 3 *********************************************");
        List<Product> piùCari = warehouse.stream().sorted(Comparator.comparing(Product::getPrice).reversed()).limit(5).toList();
        piùCari.forEach(System.out::println);

        System.out.println("******************************** Esercizio 4 *********************************************");
        riassuntoOrderCustomer.forEach((nome,ordine) -> {
            Double media = 0.0;
            for (int i = 0; i < ordine.size(); i++) {
                media = ordine.get(i).products.stream().collect(Collectors.averagingDouble(Product::getPrice));
            }
            System.out.println("Customer: " + nome + ", Media:" + media);
        });


        System.out.println("******************************** Esercizio 5 *********************************************");
        Map<Category, List<Product>> elencoProd = warehouse.stream().collect(Collectors.groupingBy(product -> product.category));
        elencoProd.forEach((category, list)-> {
            Double price = 0.0;
            for (int i = 0; i < list.size(); i++) {
                price = list.get(i).price;
            }
            System.out.println("Categoria: " + category + ", Lista: " + price);
        });

        System.out.println("******************************** Esercizio 6 *********************************************");
        File file = new File("src/main/java/samuelesimeone/dbProduct.txt");
        for (int i = 0; i < warehouse.size(); i++) {
            try{
                FileUtils.writeStringToFile(file,warehouse.get(i).name + "@" + warehouse.get(i).category + "@" + warehouse.get(i).price + "#", StandardCharsets.UTF_8, true);
            }catch (IOException e ){
                System.out.println(e.getMessage());
            }
        }
//        FileUtils.deleteQuietly(file);

//        System.out.println("******************************** Esercizio 7 *********************************************");
//            try{
//                List<Product> newMeg = new ArrayList<>();
//                String contenuto = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
//                System.out.println(contenuto.length());
//                for (int a = 0; a < contenuto.length(); a++) {
//                    for (int i = 0; i < 22; i++) {
//                        System.out.println(contenuto.split("#")[i]);
//                        for (int j = 0; j < 3; j++) {
//                            System.out.println(contenuto.split("#")[i].split("@")[j]);
//                        }
//                    }
//                }
//
//            }catch (IOException e){
//                System.out.println(e.getMessage());
//            }

    }

    public static List<Customer> generateCustomer(List<Customer> x){
        for (int i = 0; i < 100; i++) {
            x.add(generateUser.get());
        }
        return x;

    }

    public static List<Product> generateOrder(List<Product> x){
        Random rdm = new Random();
        List<Product> y = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Integer nP = rdm.nextInt( x.size() -1);
            y.add(x.get(nP));
        }
        return y;
    }

    public static Customer takeOneCustomer(List<Customer> x){
        Random rdm = new Random();
        Integer nC = rdm.nextInt( x.size() - 1 );
        Customer newUser = x.get(nC);
        return newUser;
    }

    public static LocalDate generateData (){
        Random rdm = new Random();

        int year = rdm.nextInt(24) + 2000;
        int month = rdm.nextInt(12) + 1;
        int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
        int day = rdm.nextInt(maxDay) + 1;

        LocalDate randomDate = LocalDate.of(year, month, day);

        return randomDate;

    }
}
