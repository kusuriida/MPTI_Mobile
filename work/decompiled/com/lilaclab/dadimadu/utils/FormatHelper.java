/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lilaclab.dadimadu.utils.FormatHelper
 */
package com.lilaclab.dadimadu.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatHelper {
    private static final Locale ID_LOCALE = new Locale("in", "ID");
    private static final SimpleDateFormat DB_DATE = new SimpleDateFormat("yyyy-MM-dd", ID_LOCALE);
    private static final SimpleDateFormat READABLE_DATE = new SimpleDateFormat("dd MMM yyyy", ID_LOCALE);
    private static final SimpleDateFormat MONTH = new SimpleDateFormat("yyyy-MM", ID_LOCALE);

    public static String rupiah(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(ID_LOCALE);
        formatter.setMaximumFractionDigits(0);
        formatter.setMinimumFractionDigits(0);
        return formatter.format(value).replace("Rp", "Rp ");
    }

    public static String today() {
        return DB_DATE.format(new Date());
    }

    public static String currentMonth() {
        return MONTH.format(new Date());
    }

    public static String tanggalIndo(String dbDate) {
        try {
            Date date = DB_DATE.parse(dbDate);
            return date == null ? dbDate : READABLE_DATE.format(date);
        }
        catch (ParseException e) {
            return dbDate;
        }
    }

    public static double parseMoney(String value) {
        if (value == null) {
            return 0.0;
        }
        String clean = value.replace("Rp", "").replace(".", "").replace(",", ".").trim();
        if (clean.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(clean);
        }
        catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static int parseInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value.trim());
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }
}

