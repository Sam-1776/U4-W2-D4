package samuelesimeone.classi;

public class Customer {
    public Long id;
    public String name;
    public Integer tier;

    public Customer(Long id, String name, Integer tier) {
        this.id = id;
        this.name = name;
        this.tier = tier;
    }

    public String getName() {
        return name;
    }

    public Integer getTier() {
        return tier;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tier=" + tier +
                '}';
    }
}
