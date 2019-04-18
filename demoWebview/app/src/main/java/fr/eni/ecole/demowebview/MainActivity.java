package fr.eni.ecole.demowebview;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
        NE PAS OUBLIER LA PERMISSION INTERNET
    */

    private WebView webView;
    private final static String URL_DEMO = "http://www.eni-ecole.fr/";
    private final static String HTTP_DEMO = "<input type=\"button\" value=\"Test\" "+
                                            "onClick=\"showAndroidToast('coucou')\" /> " +
                                            "<script>" +
                                            "function showAndroidToast(text)" +
                                            "{" +
                                            "Android.showToastPersoJS(text);" +
                                            "}" +
                                            "</script>"    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.myWebView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
             public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        //webView.loadUrl(URL_DEMO);
        webView.addJavascriptInterface(new JavascriptPerso(this), "Android");
        webView.loadData(HTTP_DEMO, "text/html", "UTF-8");

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Touche retour physique ou non et si il y a un historique dans le webview
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private class JavascriptPerso{
         Context context;

         public JavascriptPerso(Context context){
             this.context = context;
         }

         @JavascriptInterface
         public void showToastPersoJS(String text){
             Toast.makeText(context,"Hello JS " + text, Toast.LENGTH_LONG).show();
         }
    }
}
