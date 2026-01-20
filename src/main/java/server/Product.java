package server;

public class Product {
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;

public Product(Integer id, String name, Integer price, Integer qty){
    this.id = id;
    this.name = name;
    this.price = price;
    this.qty = qty;
}

    @Override
    public String toString() {
        return "상품 : " +
                "id=" + id +
                ", 상품명='" + name + '\'' +
                ", 상품가격=" + price +
                ", 상품갯수=" + qty ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
