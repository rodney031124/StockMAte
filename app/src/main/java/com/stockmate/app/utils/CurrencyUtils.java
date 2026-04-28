package com.stockmate.app.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {
    // Using South African Rand (ZAR) based on the image provided
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "ZA"));

    public static String format(double amount) {
        return currencyFormatter.format(amount);
    }

    public static double parse(String amount) {
        if (amount == null || amount.isEmpty()) {
            return 0.0;
        }
        try {
            // Remove currency symbols and commas
            String cleanString = amount.replaceAll("[^0-9.]", "");
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}