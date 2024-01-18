package samuelesimeone.classi;

import samuelesimeone.categorie.Category;

public class Product {
    public Long id;
    public String name;
    public Category category;
    public Double price;

    public Product(Long id, String name, Category category, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                '}';
    }
}
