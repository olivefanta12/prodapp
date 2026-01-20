package server;

import java.util.List;

public interface ProductServiceInterface {
    //public abstract 생략가능
    int 상품등록(String name, int price, int qty);
    List<Product> 상품목록();
    Product 상품상세(int id);
    void 상품삭제(int id);
}
