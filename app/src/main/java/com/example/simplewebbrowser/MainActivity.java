package com.example.simplewebbrowser;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<WebView> webViews;
    private LinearLayout webViewContainer;
    private LinearLayout tabStrip;
    private EditText addressBar;
    private int currentTabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webViews = new ArrayList<>();
        webViewContainer = findViewById(R.id.webViewContainer);
        tabStrip = findViewById(R.id.tabStrip);
        addressBar = findViewById(R.id.addressBar);
        Button goButton = findViewById(R.id.goButton);
        Button newTabButton = findViewById(R.id.newTabButton);

        newTab("http://www.google.com");

        goButton.setOnClickListener(view -> loadCurrentTab());

        newTabButton.setOnClickListener(view -> newTab(null));
    }

    private void newTab(String url) {
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (currentTabIndex == webViews.indexOf(view)) {
                    addressBar.setText(url);
                    TextView tabView = (TextView) tabStrip.getChildAt(currentTabIndex);
                    tabView.setText(view.getTitle());
                }
            }
        });
        webViews.add(webView);
        currentTabIndex = webViews.size() - 1;
        switchTab(currentTabIndex);

        TextView tabView = new TextView(this);
        tabView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        tabView.setText("Tab " + (currentTabIndex + 1));
        tabView.setTextColor(Color.WHITE);
        tabView.setPadding(20, 20, 20, 20);
        tabView.setOnClickListener(view -> switchTab(tabStrip.indexOfChild(view)));
        tabStrip.addView(tabView);

        if (url != null) {
            webView.loadUrl(url);
        } else {
            webView.loadData("", "text/html", "UTF-8");
        }
    }

    private void switchTab(int index) {
        webViewContainer.removeAllViews();
        webViewContainer.addView(webViews.get(index));
        currentTabIndex = index;
        addressBar.setText(webViews.get(index).getUrl());

        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setBackgroundColor(i == index ? Color.DKGRAY : Color.TRANSPARENT);
        }
    }

    private void loadCurrentTab() {
        String url = addressBar.getText().toString().trim();
        if (!url.isEmpty()) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            webViews.get(currentTabIndex).loadUrl(url);
            addressBar.setText(url);
        }
    }

    @Override
    public void onBackPressed() {
        if (webViews.get(currentTabIndex).canGoBack()) {
            webViews.get(currentTabIndex).goBack();
        } else {
            super.onBackPressed();
        }
    }
}
