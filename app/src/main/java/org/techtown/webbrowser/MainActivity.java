package org.techtown.webbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    boolean isPageOpen = false;

    Animation translateBottomAnim, translateTopAnim;

    LinearLayout page;
    Button openButton;

    private WebView webView;
    private Button loadButton;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = (LinearLayout) findViewById(R.id.page);

        translateBottomAnim = AnimationUtils.loadAnimation(this, R.anim.translate_bottom);
        translateTopAnim = AnimationUtils.loadAnimation(this, R.anim.translate_top);

        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateBottomAnim.setAnimationListener(animListener);
        translateTopAnim.setAnimationListener(animListener);

        openButton = (Button) findViewById(R.id.openButton);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPageOpen) {
                    page.startAnimation(translateTopAnim);
                } else {
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateBottomAnim);
                }
            }
        });

        webView = (WebView) findViewById(R.id.webView);

        WebSettings wbSettings; //웹뷰세팅

        webView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        wbSettings = webView.getSettings(); //세부 세팅 등록
        wbSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        wbSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        wbSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        wbSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        wbSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        wbSettings.setSupportZoom(false); // 화면 줌 허용 여부
        wbSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        wbSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        wbSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        wbSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        webView.setWebChromeClient(new WebChromeClient());

        final EditText urlEditText = (EditText) findViewById(R.id.urlEditText);

        loadButton = (Button) findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlInput = urlEditText.getText().toString();
//                webView.loadUrl("https://" + urlInput);
                webView.loadUrl("http://www.naver.com");

                page.startAnimation(translateTopAnim);
            }
        });
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (isPageOpen) {
                page.setVisibility(View.INVISIBLE);

                openButton.setText("Open");
                isPageOpen = false;
            } else {
                openButton.setText("Close");
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
