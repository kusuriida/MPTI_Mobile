package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.os.Bundle;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.model.PenjualanProdukItem;
import com.lilaclab.dadimadu.model.StockReportItem;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LaporanActivity
extends AppCompatActivity {
    private static final String[] MONTH_NAMES = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    private AppDatabase db;
    private Spinner spinnerBulan;
    private Spinner spinnerTahun;
    private TextView txtPeriode;
    private TextView txtRingkasan;
    private PieChart chartKeuangan;
    private LinearLayout reportContainer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_laporan);
        this.db = AppDatabase.getInstance((Context)this);
        this.spinnerBulan = (Spinner)this.findViewById(R.id.spinnerBulan);
        this.spinnerTahun = (Spinner)this.findViewById(R.id.spinnerTahun);
        this.txtPeriode = (TextView)this.findViewById(R.id.txtPeriode);
        this.txtRingkasan = (TextView)this.findViewById(R.id.txtRingkasan);
        this.chartKeuangan = (PieChart)this.findViewById(R.id.chartKeuangan);
        this.reportContainer = (LinearLayout)this.findViewById(R.id.reportContainer);
        this.findViewById(R.id.btnBack).setOnClickListener(v -> this.finish());
        this.findViewById(R.id.btnTampilkanLaporan).setOnClickListener(v -> this.loadReport());
        this.setupPeriodSelector();
        this.loadReport();
    }

    private void setupPeriodSelector() {
        ArrayAdapter monthAdapter = new ArrayAdapter((Context)this, R.layout.item_spinner_text, (Object[])MONTH_NAMES);
        monthAdapter.setDropDownViewResource(R.layout.item_spinner_text);
        this.spinnerBulan.setAdapter((SpinnerAdapter)monthAdapter);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(1);
        int currentMonth = calendar.get(2);
        ArrayList<String> years = new ArrayList<String>();
        for (int year = currentYear + 2; year >= currentYear - 5; --year) {
            years.add(String.valueOf(year));
        }
        ArrayAdapter yearAdapter = new ArrayAdapter((Context)this, R.layout.item_spinner_text, years);
        yearAdapter.setDropDownViewResource(R.layout.item_spinner_text);
        this.spinnerTahun.setAdapter((SpinnerAdapter)yearAdapter);
        this.spinnerBulan.setSelection(currentMonth);
        this.spinnerTahun.setSelection(years.indexOf(String.valueOf(currentYear)));
    }

    private void loadReport() {
        String bulan = this.getSelectedPeriod();
        String periodeLabel = this.getSelectedPeriodLabel();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            double pemasukan = this.db.transaksiDao().sumPemasukanBulan(bulan);
            double pengeluaran = this.db.pengeluaranDao().sumPengeluaranBulan(bulan);
            List stok = this.db.produkDao().getStockReport();
            List penjualan = this.db.produkDao().getPenjualanProduk(bulan);
            this.runOnUiThread(() -> {
                this.txtPeriode.setText((CharSequence)("Periode: " + periodeLabel));
                this.txtRingkasan.setText((CharSequence)String.format(Locale.US, "%-12s %s\n%-12s %s\n%-12s %s", "Pemasukan", FormatHelper.rupiah((double)pemasukan), "Pengeluaran", FormatHelper.rupiah((double)pengeluaran), "Laba/Rugi", FormatHelper.rupiah((double)(pemasukan - pengeluaran))));
                this.renderChart(pemasukan, pengeluaran);
                this.renderReports(stok, penjualan, periodeLabel);
            });
        });
    }

    private String getSelectedPeriod() {
        int month = this.spinnerBulan.getSelectedItemPosition() + 1;
        String year = this.spinnerTahun.getSelectedItem() == null ? String.valueOf(Calendar.getInstance().get(1)) : this.spinnerTahun.getSelectedItem().toString();
        return String.format(Locale.US, "%s-%02d", year, month);
    }

    private String getSelectedPeriodLabel() {
        String month = this.spinnerBulan.getSelectedItem() == null ? MONTH_NAMES[Calendar.getInstance().get(2)] : this.spinnerBulan.getSelectedItem().toString();
        String year = this.spinnerTahun.getSelectedItem() == null ? String.valueOf(Calendar.getInstance().get(1)) : this.spinnerTahun.getSelectedItem().toString();
        return month + " " + year;
    }

    private void renderChart(double pemasukan, double pengeluaran) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        int pemasukanColor = this.getResources().getColor(R.color.amber);
        int pengeluaranColor = this.getResources().getColor(R.color.danger);
        int emptyColor = this.getResources().getColor(R.color.border);
        double total = pemasukan + pengeluaran;
        if (total <= 0.0) {
            entries.add(new PieEntry(1.0f, "Belum ada data"));
        } else {
            if (pemasukan > 0.0) {
                entries.add(new PieEntry((float)pemasukan, "Pemasukan"));
            }
            if (pengeluaran > 0.0) {
                entries.add(new PieEntry((float)pengeluaran, "Pengeluaran"));
            }
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        if (total <= 0.0) {
            dataSet.setColor(emptyColor);
        } else if (pemasukan > 0.0 && pengeluaran > 0.0) {
            dataSet.setColors(new int[]{pemasukanColor, pengeluaranColor});
        } else if (pemasukan > 0.0) {
            dataSet.setColor(pemasukanColor);
        } else {
            dataSet.setColor(pengeluaranColor);
        }
        dataSet.setSliceSpace(3.0f);
        dataSet.setSelectionShift(6.0f);
        dataSet.setValueLinePart1Length(0.35f);
        dataSet.setValueLinePart2Length(0.25f);
        PieData data = new PieData((IPieDataSet)dataSet);
        data.setValueFormatter((ValueFormatter)new PercentFormatter(this.chartKeuangan));
        data.setValueTextColor(this.getResources().getColor(R.color.white));
        data.setValueTextSize(12.0f);
        double pemasukanPercent = total <= 0.0 ? 0.0 : pemasukan / total * 100.0;
        this.chartKeuangan.setData(data);
        this.chartKeuangan.setUsePercentValues(true);
        this.chartKeuangan.getDescription().setEnabled(false);
        this.chartKeuangan.getLegend().setEnabled(true);
        this.chartKeuangan.getLegend().setTextColor(this.getResources().getColor(R.color.text_muted));
        this.chartKeuangan.getLegend().setOrientation(com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL);
        this.chartKeuangan.getLegend().setHorizontalAlignment(com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER);
        this.chartKeuangan.getLegend().setVerticalAlignment(com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM);
        this.chartKeuangan.getLegend().setTextSize(13f);
        this.chartKeuangan.setDrawEntryLabels(false);
        this.chartKeuangan.setHoleRadius(62.0f);
        this.chartKeuangan.setTransparentCircleRadius(68.0f);
        this.chartKeuangan.setTransparentCircleColor(this.getResources().getColor(R.color.cream));
        this.chartKeuangan.setCenterText((CharSequence)(total <= 0.0 ? "0%\nDATA" : String.format(Locale.US, "%.0f%%\nPEMASUKAN", pemasukanPercent)));
        this.chartKeuangan.setCenterTextColor(this.getResources().getColor(R.color.text_main));
        this.chartKeuangan.setCenterTextSize(15.0f);
        this.chartKeuangan.setNoDataText("Belum ada data laporan");
        this.chartKeuangan.invalidate();
    }

    private void renderReports(List<StockReportItem> stok, List<PenjualanProdukItem> penjualan, String periodeLabel) {
        this.reportContainer.removeAllViews();
        this.addSectionTitle("Laporan Stok Produk");
        for (StockReportItem item : stok) {
            this.addCard(item.namaProduk, "Kemasan  : " + item.ukuranKemasan + "\nStok     : " + item.stok + "\nMin stok : " + item.minimumStok + "\nNilai    : " + FormatHelper.rupiah((double)item.nilaiStok));
        }
        this.addSectionTitle("Laporan Penjualan Produk");
        boolean hasSales = false;
        for (PenjualanProdukItem item : penjualan) {
            if (item.totalQty <= 0) continue;
            hasSales = true;
            this.addCard(item.namaProduk, "Terjual : " + item.totalQty + " pcs\nTotal   : " + FormatHelper.rupiah((double)item.totalPenjualan));
        }
        if (!hasSales) {
            this.addCard("Belum ada penjualan", "Transaksi periode " + periodeLabel + " belum tercatat.");
        }
    }

    private void addSectionTitle(String title) {
        TextView view = new TextView((Context)this);
        view.setText((CharSequence)title);
        view.setTextColor(this.getResources().getColor(R.color.text_main));
        view.setTextSize(17.0f);
        view.setTypeface(null, 1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, this.dp(6), 0, this.dp(10));
        this.reportContainer.addView((View)view, (ViewGroup.LayoutParams)params);
    }

    private void addCard(String title, String body) {
        TextView card = new TextView((Context)this);
        String text = title + "\n\n" + body;
        SpannableString styled = new SpannableString(text);
        styled.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        card.setText((CharSequence)styled);
        card.setTextColor(this.getResources().getColor(R.color.text_main));
        card.setTextSize(14.0f);
        card.setBackgroundResource(R.drawable.bg_card);
        card.setPadding(this.dp(16), this.dp(14), this.dp(16), this.dp(14));
        card.setLineSpacing((float)this.dp(4), 1.0f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 0, 0, this.dp(12));
        this.reportContainer.addView((View)card, (ViewGroup.LayoutParams)params);
    }

    private int dp(int value) {
        return (int)((float)value * this.getResources().getDisplayMetrics().density);
    }
}
