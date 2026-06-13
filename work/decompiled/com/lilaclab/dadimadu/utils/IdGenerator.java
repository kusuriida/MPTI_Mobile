/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lilaclab.dadimadu.database.AppDatabase
 *  com.lilaclab.dadimadu.utils.IdGenerator
 */
package com.lilaclab.dadimadu.utils;

import com.lilaclab.dadimadu.database.AppDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 * Exception performing whole class analysis ignored.
 */
public class IdGenerator {
    public static String invoiceId(AppDatabase db, String tanggal) {
        String datePart = IdGenerator.toInvoiceDate((String)tanggal);
        String prefix = "INV" + datePart + "DM";
        int next = db.transaksiDao().countByPrefix(prefix) + 1;
        return prefix + String.format(Locale.US, "%03d", next);
    }

    private static String toInvoiceDate(String tanggal) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(tanggal);
            if (date != null) {
                return new SimpleDateFormat("ddMMyyyy", Locale.US).format(date);
            }
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        return new SimpleDateFormat("ddMMyyyy", Locale.US).format(new Date());
    }
}

