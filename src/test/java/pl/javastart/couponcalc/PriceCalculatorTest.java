package pl.javastart.couponcalc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {
    private PriceCalculator priceCalculator;

    @BeforeEach
    public void init() {
        priceCalculator = new PriceCalculator();
    }

    @Test
    public void shouldReturnZeroForNoProducts() {

        // when
        double result = priceCalculator.calculatePrice(null, null);

        // then
        assertThat(result).isEqualTo(0.);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupons() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result).isEqualTo(5.99);
    }

    @Test
    public void shouldReturnPriceForThreeProductsAndNoCoupons() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Twaróg", 4.99, Category.FOOD));
        products.add(new Product("Fotel", 600.99, Category.HOME));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result).isEqualTo(611.97);
    }

    @Test
    public void shouldReturnPriceForThreeProductsOneCouponWithoutCategory() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Twaróg", 4.99, Category.FOOD));
        products.add(new Product("Fotel", 600.99, Category.HOME));
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(null, 10));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(550.77);
    }

    @Test
    public void shouldReturnPriceForThreeProductsOneCouponWithCategory() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Twaróg", 4.99, Category.FOOD));
        products.add(new Product("Fotel", 600.99, Category.HOME));
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.HOME, 10));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(551.87);
    }

    @Test
    public void shouldReturnPriceForThreeProductsTwoCouponsWithCategory() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Twaróg", 4.99, Category.FOOD));
        products.add(new Product("Fotel", 600.99, Category.HOME));
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.HOME, 10));
        coupons.add(new Coupon(Category.FOOD, 10));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(551.87);
    }

    @Test
    public void shouldReturnPriceForThreeProductsOneCouponWithCategoryOneCouponWithoutCategory() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Twaróg", 4.99, Category.FOOD));
        products.add(new Product("Fotel", 600.99, Category.HOME));
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.HOME, 10));
        coupons.add(new Coupon(null, 10)); //550.77

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(550.77);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneCoupon() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(4.79);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupon() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        // when
        double result = priceCalculator.calculatePrice(products, Collections.emptyList());

        // then
        assertThat(result).isEqualTo(5.99);
    }


}