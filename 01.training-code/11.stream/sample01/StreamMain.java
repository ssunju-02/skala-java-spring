import java.util.ArrayList;
import java.util.List;

public class StreamMain {

    static List<Product> products = new ArrayList<>();

    static void setProduct() {
        products.add(new Product(1, true, "fashion", 50000));
        products.add(new Product(2, true, "fashion", 38000));
        products.add(new Product(3, true, "it", 250000));
        products.add(new Product(4, true, "it", 85000));
        products.add(new Product(5, true, "it", 99000));
        products.add(new Product(6, true, "it", 75000));
        products.add(new Product(7, true, "furniture", 350000));
        products.add(new Product(8, false, "furniture", 210000));
        products.add(new Product(9, false, "furniture", 58000));
        products.add(new Product(10, false, "it", 120000));
    }

    public static void main(String[] args) {
        setProduct();

        // 판매 중이고 십만원 이하인 상품 개수 (일반 for문)
        int count = 0;
        for (Product p : products) {
            if (p.isUsable()) {
                if (p.getPrice() <= 100000) {
                    count++;
                }
            }
        }
        System.out.println("판매 중, 10만원 이하 상품 개수(for문): " + count);

        // 같은 조건을 Stream API로 처리
        long count2 = products.stream()
                .filter(Product::isUsable)
                .filter(p -> p.getPrice() <= 100000)
                .count();
        System.out.println("판매 중, 10만원 이하 상품 개수(Stream): " + count2);

        // 실습: usable == true인 모든 product의 price 합을 Stream API로 계산
        int totalPrice = products.stream()
                .filter(Product::isUsable)
                .mapToInt(Product::getPrice)
                .sum();
        System.out.println("판매 중인 상품의 가격 합계: " + totalPrice);
    }
}
