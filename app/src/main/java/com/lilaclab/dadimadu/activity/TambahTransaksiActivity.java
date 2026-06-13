package com.lilaclab.dadimadu.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;

import com.lilaclab.dadimadu.adapter.ProdukPilihAdapter;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.DetailTransaksi;
import com.lilaclab.dadimadu.database.entity.Pelanggan;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.database.entity.Transaksi;
import com.lilaclab.dadimadu.utils.FormatHelper;
import com.lilaclab.dadimadu.utils.IdGenerator;
import com.lilaclab.dadimadu.utils.SessionManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TambahTransaksiActivity
extends AppCompatActivity {
    private AutoCompleteTextView edtNamaPelanggan;
    private EditText edtNoHp;
    private EditText edtOngkir;
    private EditText edtTanggal;
    private Spinner spinnerStatusBayar;
    private Spinner spinnerMetodeBayar;
    private TextView txtSubtotal;
    private TextView txtGrandTotal;
    private TextView btnSimpan;
    private ProgressBar progressSimpan;
    private ProdukPilihAdapter produkAdapter;
    private AppDatabase db;
    private SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tambah_transaksi);
        this.db = AppDatabase.getInstance((Context)this);
        this.session = new SessionManager((Context)this);
        this.bindViews();
        this.setupSpinners();
        this.setupProdukList();
        this.loadInitialData();
    }

    private void bindViews() {
        this.edtNamaPelanggan = (AutoCompleteTextView)this.findViewById(R.id.edtNamaPelanggan);
        this.edtNoHp = (EditText)this.findViewById(R.id.edtNoHp);
        this.edtOngkir = (EditText)this.findViewById(R.id.edtOngkir);
        this.edtTanggal = (EditText)this.findViewById(R.id.edtTanggal);
        this.spinnerStatusBayar = (Spinner)this.findViewById(R.id.spinnerStatusBayar);
        this.spinnerMetodeBayar = (Spinner)this.findViewById(R.id.spinnerMetodeBayar);
        this.txtSubtotal = (TextView)this.findViewById(R.id.txtSubtotal);
        this.txtGrandTotal = (TextView)this.findViewById(R.id.txtGrandTotal);
        this.btnSimpan = (TextView)this.findViewById(R.id.btnSimpan);
        this.progressSimpan = (ProgressBar)this.findViewById(R.id.progressSimpan);
        this.edtTanggal.setText((CharSequence)FormatHelper.today());
        this.findViewById(R.id.btnBack).setOnClickListener(v -> this.finish());
        this.btnSimpan.setOnClickListener(v -> this.simpanTransaksi());
        this.edtOngkir.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                TambahTransaksiActivity.this.updateTotal();
            }
        });
        this.edtTanggal.setOnClickListener(v -> this.showDatePicker());
    }

    private void setupSpinners() {
        ArrayAdapter statusAdapter = new ArrayAdapter((Context)this, R.layout.item_spinner_text, (Object[])new String[]{"Lunas", "Belum Lunas", "DP"});
        statusAdapter.setDropDownViewResource(R.layout.item_spinner_text);
        this.spinnerStatusBayar.setAdapter((SpinnerAdapter)statusAdapter);
        ArrayAdapter metodeAdapter = new ArrayAdapter((Context)this, R.layout.item_spinner_text, (Object[])new String[]{"Tunai", "Transfer Bank", "COD", "QRIS"});
        metodeAdapter.setDropDownViewResource(R.layout.item_spinner_text);
        this.spinnerMetodeBayar.setAdapter((SpinnerAdapter)metodeAdapter);
    }

    private void setupProdukList() {
        this.produkAdapter = new ProdukPilihAdapter(() -> this.updateTotal());
        RecyclerView rvProduk = (RecyclerView)this.findViewById(R.id.rvProduk);
        rvProduk.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager((Context)this));
        rvProduk.setAdapter((RecyclerView.Adapter)this.produkAdapter);
    }

    private void loadInitialData() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List products = this.db.produkDao().getAll();
            List<Pelanggan> pelanggan = this.db.pelangganDao().getAll();
            ArrayList<String> names = new ArrayList<String>();
            for (Pelanggan item : pelanggan) {
                names.add(item.namaPelanggan);
            }
            this.runOnUiThread(() -> {
                this.produkAdapter.setItems(products);
                ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>((Context)this, android.R.layout.simple_dropdown_item_1line, names);
                this.edtNamaPelanggan.setAdapter(nameAdapter);
                this.edtNamaPelanggan.setThreshold(1);
                this.updateTotal();
            });
        });
    }

    private void updateTotal() {
        double subtotal = this.produkAdapter == null ? 0.0 : this.produkAdapter.getSubtotal();
        double ongkir = FormatHelper.parseMoney((String)(this.edtOngkir == null ? "0" : this.edtOngkir.getText().toString()));
        this.txtSubtotal.setText((CharSequence)("Subtotal produk: " + FormatHelper.rupiah((double)subtotal)));
        this.txtGrandTotal.setText((CharSequence)("Grand total: " + FormatHelper.rupiah((double)(subtotal + ongkir))));
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog((Context)this, (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            this.edtTanggal.setText((CharSequence)date);
        }, calendar.get(1), calendar.get(2), calendar.get(5)).show();
    }

    private void simpanTransaksi() {
        List<ProdukPilihAdapter.SelectedItem> selectedItems = this.produkAdapter.getSelectedItems();
        if (selectedItems.isEmpty()) {
            Toast.makeText((Context)this, (CharSequence)"Pilih minimal satu produk.", (int)0).show();
            return;
        }
        String tanggal = this.edtTanggal.getText().toString().trim();
        if (tanggal.isEmpty()) {
            Toast.makeText((Context)this, (CharSequence)"Tanggal wajib diisi.", (int)0).show();
            return;
        }
        this.btnSimpan.setEnabled(false);
        this.progressSimpan.setVisibility(0);
        SaveResult result = new SaveResult(null);
        String namaPelanggan = this.edtNamaPelanggan.getText().toString().trim();
        String noHp = this.edtNoHp.getText().toString().trim();
        double ongkir = FormatHelper.parseMoney((String)this.edtOngkir.getText().toString());
        String statusBayar = this.spinnerStatusBayar.getSelectedItem().toString();
        String metodeBayar = this.spinnerMetodeBayar.getSelectedItem().toString();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                this.db.runInTransaction(() -> {
                    Integer idPelanggan = null;
                    if (!namaPelanggan.isEmpty()) {
                        Pelanggan pelanggan = this.db.pelangganDao().findByName(namaPelanggan);
                        if (pelanggan == null) {
                            long id2 = this.db.pelangganDao().insert(new Pelanggan(namaPelanggan, noHp, ""));
                            idPelanggan = (int)id2;
                        } else {
                            idPelanggan = pelanggan.idPelanggan;
                        }
                    }
                    String invoice = IdGenerator.invoiceId((AppDatabase)this.db, (String)tanggal);
                    double subtotal = 0.0;
                    ArrayList<DetailTransaksi> details = new ArrayList<DetailTransaksi>();
                    for (ProdukPilihAdapter.SelectedItem item : selectedItems) {
                        Produk produk = this.db.produkDao().getById(item.produk.idProduk);
                        if (produk == null) {
                            throw new IllegalStateException("Produk tidak ditemukan.");
                        }
                        if (produk.stok < item.qty) {
                            throw new IllegalStateException("Stok " + produk.namaProduk + " tidak cukup.");
                        }
                        DetailTransaksi detail = new DetailTransaksi();
                        detail.idTransaksi = invoice;
                        detail.idProduk = produk.idProduk;
                        detail.qty = item.qty;
                        detail.hargaSaatTransaksi = produk.hargaJual;
                        detail.subtotal = produk.hargaJual * (double)item.qty;
                        subtotal += detail.subtotal;
                        details.add(detail);
                    }
                    Transaksi transaksi = new Transaksi();
                    transaksi.idTransaksi = invoice;
                    transaksi.idPelanggan = idPelanggan;
                    transaksi.idUser = this.session.getUserId() == 0 ? 1 : this.session.getUserId();
                    transaksi.tanggal = tanggal;
                    transaksi.ongkir = ongkir;
                    transaksi.metodeBayar = metodeBayar;
                    transaksi.statusBayar = statusBayar;
                    transaksi.subtotal = subtotal;
                    transaksi.grandtotal = subtotal + ongkir;
                    transaksi.createAt = System.currentTimeMillis();
                    this.db.transaksiDao().insert(transaksi);
                    this.db.transaksiDao().insertDetails(details);
                    for (DetailTransaksi detail : details) {
                        int changed = this.db.produkDao().reduceStock(detail.qty, detail.idProduk);
                        if (changed != 0) continue;
                        throw new IllegalStateException("Stok produk berubah, coba lagi.");
                    }
                    result.invoice = invoice;
                });
            }
            catch (Exception e) {
                result.error = e.getMessage();
            }
            this.runOnUiThread(() -> {
                this.btnSimpan.setEnabled(true);
                this.progressSimpan.setVisibility(8);
                if (result.error != null) {
                    Toast.makeText((Context)this, (CharSequence)result.error, (int)1).show();
                    return;
                }
                Toast.makeText((Context)this, (CharSequence)("Transaksi " + result.invoice + " tersimpan."), (int)0).show();
                this.finish();
            });
        });
    }

    private static class SaveResult {
        String invoice;
        String error;
        SaveResult(String invoice) { this.invoice = invoice; }
    }
}