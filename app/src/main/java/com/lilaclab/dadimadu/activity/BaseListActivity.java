package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.os.Bundle;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.database.AppDatabase;

public abstract class BaseListActivity
extends AppCompatActivity {
    protected AppDatabase db;
    protected LinearLayout listContainer;

    protected abstract String title();

    protected abstract String subtitle();

    protected abstract String actionText();

    protected abstract void onAction();

    protected abstract void loadItems();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_simple_list);
        this.db = AppDatabase.getInstance((Context)this);
        ((TextView)this.findViewById(R.id.txtTitle)).setText((CharSequence)this.title());
        ((TextView)this.findViewById(R.id.txtBannerTitle)).setText((CharSequence)this.title());
        ((TextView)this.findViewById(R.id.txtBannerSubtitle)).setText((CharSequence)this.subtitle());
        ((TextView)this.findViewById(R.id.btnAction)).setText((CharSequence)this.actionText());
        this.listContainer = (LinearLayout)this.findViewById(R.id.listContainer);
        this.findViewById(R.id.btnBack).setOnClickListener(v -> this.finish());
        this.findViewById(R.id.btnAction).setOnClickListener(v -> this.onAction());
    }

    protected void onResume() {
        super.onResume();
        this.loadItems();
    }

    protected TextView addCard(String title, String body) {
        TextView card = new TextView((Context)this);
        String text = title + "\n\n" + body;
        SpannableString styled = new SpannableString(text);
        styled.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        card.setText(styled);
        card.setTextColor(this.getResources().getColor(R.color.text_main));
        card.setTextSize(14.0f);
        card.setLineSpacing((float)this.dp(4), 1.0f);
        card.setBackgroundResource(R.drawable.bg_card);
        card.setPadding(this.dp(16), this.dp(14), this.dp(16), this.dp(14));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 0, 0, this.dp(12));
        this.listContainer.addView((View)card, (ViewGroup.LayoutParams)params);
        return card;
    }

    protected TextView label(String text) {
        TextView label = new TextView((Context)this);
        label.setText((CharSequence)text);
        label.setTextColor(this.getResources().getColor(R.color.text_main));
        label.setTextSize(12.0f);
        label.setTypeface(null, 1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 0, 0, this.dp(6));
        label.setLayoutParams((ViewGroup.LayoutParams)params);
        return label;
    }

    protected void showEmpty(String message) {
        this.listContainer.removeAllViews();
        TextView view = this.addCard("Belum ada data", message);
        view.setTextColor(this.getResources().getColor(R.color.text_muted));
    }

    protected EditText input(String hint) {
        EditText editText = new EditText((Context)this);
        editText.setHint((CharSequence)hint);
        editText.setBackgroundResource(R.drawable.bg_input);
        editText.setTextColor(this.getResources().getColor(R.color.input_text));
        editText.setHintTextColor(this.getResources().getColor(R.color.text_muted));
        editText.setTextSize(14.0f);
        editText.setPadding(this.dp(14), 0, this.dp(14), 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, this.dp(50));
        params.setMargins(0, 0, 0, this.dp(10));
        editText.setLayoutParams((ViewGroup.LayoutParams)params);
        return editText;
    }

    protected LinearLayout dialogBody() {
        LinearLayout body = new LinearLayout((Context)this);
        body.setOrientation(1);
        body.setBackgroundResource(R.drawable.bg_dialog_body);
        body.setPadding(this.dp(16), this.dp(12), this.dp(16), this.dp(4));
        return body;
    }

    protected int dp(int value) {
        return (int)((float)value * this.getResources().getDisplayMetrics().density);
    }
}
