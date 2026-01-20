package server;

import java.util.List;

public class ProductService implements ProductServiceInterface{
    private final ProductRepository repo = new ProductRepository();

    @Override
    public int 상품등록(String name, int price, int qty) {
        return repo.save(name, price, qty);
    }

    @Override
    public List<Product> 상품목록() {
        return repo.findAll();
    }

    @Override
    public Product 상품상세(int id) {
        Product p = repo.findById(id);
        if (p == null){
            throw new RuntimeException("id not found");
        }
        return p;
    }

    @Override
    public void 상품삭제(int id) {
        int result = repo.deleteById(id);
        if (result != 1) {
            throw new RuntimeException("id not found");
        }
    }
}
