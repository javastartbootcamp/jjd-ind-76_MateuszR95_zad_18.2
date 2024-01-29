package pl.javastart.couponcalc;

import java.util.ArrayList;
import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        double totalPrice = 0;

        if (products == null) {
            totalPrice = 0;
        } else if (coupons == null) {
            return getProductsTotalPriceWithoutCoupon(products);
        } else {
            Coupon bestCoupon = getBestCoupon(coupons, products);
            totalPrice = calculateTotalPriceWithCoupon(products, bestCoupon);
        }

        return Math.round(totalPrice * 100.0) / 100.0;
    }

    private static double getProductsTotalPriceWithoutCoupon(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    private double calculateTotalPriceWithCoupon(List<Product> products, Coupon coupon) {
        if (coupon.getCategory() == null) {
            return getProductsTotalPriceWithoutCoupon(products) * (1 - (coupon.getDiscountValueInPercents() / 100.0));
        } else {
            Category category = coupon.getCategory();
            List<Product> productsByCategory = getProductsByCategory(products, category);
            List<Product> productsNotInCategory = getProductsNotInCategory(products, productsByCategory);
            return calculateTotalPriceForProductsByCategory(productsByCategory, coupon)
                    + getProductsTotalPriceWithoutCoupon(productsNotInCategory);
        }
    }

    public List<Product> getProductsByCategory(List<Product> products, Category category) {
        return products.stream()
                .filter(product -> product.getCategory() == category)
                .toList();
    }

    public List<Product> getProductsNotInCategory(List<Product> products, List<Product> productsByCategory) {
        List<Product> productsNotInCategory = new ArrayList<>(products);
        productsNotInCategory.removeAll(productsByCategory);
        return productsNotInCategory;
    }

    public double calculateTotalPriceForProductsByCategory(List<Product> productsByCategory, Coupon coupon) {
        double totalPrice = 0;
        for (Product product : productsByCategory) {
            totalPrice += product.getPrice() * (1 - (coupon.getDiscountValueInPercents() / 100.0));
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }

    public Coupon getBestCoupon(List<Coupon> coupons, List<Product> products) {
        double minPriceWithCoupon = Double.MAX_VALUE;
        Coupon bestCoupon = null;
        for (Coupon coupon : coupons) {
            double totalPriceWithCoupon = calculateTotalPriceWithCoupon(products, coupon);
            if (totalPriceWithCoupon < minPriceWithCoupon) {
                minPriceWithCoupon = totalPriceWithCoupon;
                bestCoupon = coupon;
            }
        }
        return bestCoupon;
    }
}

