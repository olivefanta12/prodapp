package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public List<Product> findAll() {
        String sql = "select * from product order by id desc";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("qty")
                );
                list.add(p);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product findById(int id) {
        String sql = "select id, name, price, qty from product where id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getInt("qty")
                    );
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("SQL Error(findById): " + e.getMessage());
        }
    }

    public int deleteById(int id) {
        String sql = "delete from product where id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int save(String name, int price, int qty) {
        String sql = "insert into product(name, price, qty) values(?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.setInt(3, qty);

            int af = pstmt.executeUpdate();
            if (af != 1) {
                throw new RuntimeException("insert failed");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

            throw new RuntimeException("generated id not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
