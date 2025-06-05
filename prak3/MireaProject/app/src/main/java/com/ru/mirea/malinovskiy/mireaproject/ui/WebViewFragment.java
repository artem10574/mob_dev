package com.ru.mirea.malinovskiy.mireaproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebSettings;

import androidx.fragment.app.Fragment;

import com.ru.mirea.malinovskiy.mireaproject.R;

public class WebViewFragment extends Fragment {

    private WebView webView;
    private EditText editUrl;
    private Button btnGo;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);

        editUrl = view.findViewById(R.id.editUrl);
        btnGo = view.findViewById(R.id.btnGo);
        webView = view.findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("https://www.google.com");

        btnGo.setOnClickListener(v -> {
            String url = editUrl.getText().toString();
            if (!url.isEmpty()) {
                webView.loadUrl(url);
            } else {
                Toast.makeText(getActivity(), "Введите URL", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}