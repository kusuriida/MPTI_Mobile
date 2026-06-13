package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.os.Bundle;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.model.DetailTransaksiItem;
import com.lilaclab.dadimadu.model.TransaksiListItem;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.List;

public class DetailTransaksiActivity
extends AppCompatActivity {
    private TextView txtInvoice;
    private TextView txtInfo;
    private TextView txtTotal;
    private LinearLayout detailContainer;
    private AppDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail_transaksi);
        this.db = AppDatabase.getInstance((Context)this);
        this.txtInvoice = (TextView)this.findViewById(R.id.txtInvoice);
        this.txtInfo = (TextView)this.findViewById(R.id.txtInfo);
        this.txtTotal = (TextView)this.findViewById(R.id.txtTotal);
        this.detailContainer = (LinearLayout)this.findViewById(R.id.detailContainer);
        this.findViewById(R.id.btnBack).setOnClickListener(v -> this.finish());
        String idTransaksi = this.getIntent().getStringExtra("id_transaksi");
        this.loadDetail(idTransaksi);
    }

    private void loadDetail(String idTransaksi) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            TransaksiListItem transaksi = this.db.transaksiDao().getListItemById(idTransaksi);
            List details = this.db.transaksiDao().getDetailItems(idTransaksi);
            this.runOnUiThread(() -> {
                if (transaksi == null) {
                    this.txtInvoice.setText((CharSequence)"Transaksi tidak ditemukan.");
                    return;
                }
                this.txtInvoice.setText((CharSequence)transaksi.idTransaksi);
                this.txtInfo.setText((CharSequence)(transaksi.namaPelanggan + " \u2022 " + FormatHelper.tanggalIndo((String)transaksi.tanggal) + " \u2022 " + transaksi.statusBayar + " \u2022 " + transaksi.metodeBayar));
                this.txtTotal.setText((CharSequence)("Total " + FormatHelper.rupiah((double)transaksi.grandtotal)));
                this.renderDetails(details);
            });
        });
    }

    private void renderDetails(List<DetailTransaksiItem> details) {
        this.detailContainer.removeAllViews();
        for (DetailTransaksiItem item : details) {
            TextView view = new TextView((Context)this);
            String title = item.namaProduk + " " + item.ukuranKemasan;
            String text = title + "\n" + item.qty + " x " + FormatHelper.rupiah((double)item.hargaSaatTransaksi) + " = " + FormatHelper.rupiah((double)item.subtotal);
            SpannableString styled = new SpannableString(text);
            styled.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.setText((CharSequence)styled);
            view.setTextColor(this.getResources().getColor(R.color.text_main));
            view.setTextSize(13.0f);
            view.setMinHeight(this.dp(68));
            view.setPadding(this.dp(14), this.dp(12), this.dp(14), this.dp(12));
            view.setLineSpacing((float)this.dp(2), 1.0f);
            view.setBackgroundResource(R.drawable.bg_cream_card);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
            params.setMargins(0, 0, 0, 8);
            this.detailContainer.addView((View)view, (ViewGroup.LayoutParams)params);
        }
    }

    private int dp(int value) {
        return (int)((float)value * this.getResources().getDisplayMetrics().density);
    }
}
