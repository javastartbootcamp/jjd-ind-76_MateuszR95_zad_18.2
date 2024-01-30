package pl.javastart.couponcalc;

import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        double totalPrice = 0;

        if (products == null || products.isEmpty()) {
            totalPrice = 0;
        } else if (coupons == null || coupons.isEmpty()) {
            return sumProductsPrice(products);
        } else {
            totalPrice = getTotalPriceForMostFavorableCoupon(coupons, products);
        }

        return Math.round(totalPrice * 100.0) / 100.0;
    }

    private static double sumProductsPrice(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    private double getTotalPriceForMostFavorableCoupon(List<Coupon> coupons, List<Product> products) {
        double minPriceWithCoupon = Double.MAX_VALUE;
        for (Coupon coupon : coupons) {
            double totalPriceWithCoupon = calculateTotalPriceWithCoupon(products, coupon);
            if (totalPriceWithCoupon < minPriceWithCoupon) {
                minPriceWithCoupon = totalPriceWithCoupon;
            }
        }
        return minPriceWithCoupon;
    }

    private double calculateTotalPriceWithCoupon(List<Product> products, Coupon coupon) {
        if (coupon.getCategory() == null) {
            return sumProductsPrice(products) * (1 - (coupon.getDiscountValueInPercents() / 100.0));
        } else {
            Category category = coupon.getCategory();
            List<Product> productsByCategory = getProductsByCategory(products, category);
            List<Product> productsNotInCategory = getProductsNotInCategory(products, category);
            return calculateTotalPriceForProductsByCategory(productsByCategory, coupon.getDiscountValueInPercents())
                    + sumProductsPrice(productsNotInCategory);
        }
    }

    private List<Product> getProductsByCategory(List<Product> products, Category category) {
        return products.stream()
                .filter(product -> product.getCategory() == category)
                .toList();
    }

    private List<Product> getProductsNotInCategory(List<Product> products, Category category) {
        return products.stream()
                .filter(product -> product.getCategory() != category)
                .toList();
    }

    private double calculateTotalPriceForProductsByCategory(List<Product> productsByCategory, int discountValue) {
        double totalPrice = 0;
        for (Product product : productsByCategory) {
            totalPrice += product.getPrice() * (1 - (discountValue / 100.0));
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }

}

