package org.bxkr.vkarticle.ui

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import org.bxkr.vkarticle.R
import org.bxkr.vkarticle.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.authWebView.settings.javaScriptEnabled = true
        // binding.authWebView.loadUrl("https://id.vk.com/auth?app_id=7913379&v=1.46.0&redirect_uri=https%3A%2F%2Fvk.com%2Flogin&uuid=2ubzhpMqDP001CCloJw0v&action=eyJuYW1lIjoibm9fcGFzc3dvcmRfZmxvdyIsInBhcmFtcyI6eyJ0eXBlIjoic2lnbl9pbiJ9fQ%3D%3D")
        binding.authWebView.loadUrl("https://m.vk.com")
        binding.authWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (url == "https://m.vk.com/feed") {
                    val webViewAnimation = binding.authWebView.animate().alpha(0f)
                    val appBarAnimation = binding.appBar.animate().alpha(0f)
                    webViewAnimation.duration = 700
                    appBarAnimation.duration = 700
                    appBarAnimation.setListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animator: Animator?) {}
                        override fun onAnimationCancel(animator: Animator?) {}
                        override fun onAnimationRepeat(animator: Animator?) {}

                        override fun onAnimationEnd(animator: Animator?) {
                            binding.appBar.visibility = View.GONE
                            binding.authWebView.visibility = View.GONE
                            binding.loadedCongrats.visibility = View.VISIBLE
                            val loadedAnimation = binding.loadedCongrats.animate().alpha(1f)
                            loadedAnimation.duration = 3000
                            loadedAnimation.setListener(object : Animator.AnimatorListener {
                                override fun onAnimationStart(animator: Animator?) {}
                                override fun onAnimationCancel(animator: Animator?) {}
                                override fun onAnimationRepeat(animator: Animator?) {}

                                override fun onAnimationEnd(animator: Animator?) {
                                    val cookies = CookieManager.getInstance().getCookie(url)
                                    val sharedPref = this@LoginActivity.getSharedPreferences(
                                        getString(R.string.preference_auth), Context.MODE_PRIVATE) ?: return
                                    with(sharedPref.edit()) {
                                        putString(getString(R.string.preference_auth_cookies), cookies)
                                        putBoolean(getString(R.string.preference_auth_is_logged_in), true)
                                        commit()
                                    }
                                    val gotoMain = Intent(this@LoginActivity, MainActivity::class.java)
                                    gotoMain.putExtra(getString(R.string.extra_logged_in), true)
                                    startActivity(gotoMain)
                                    finish()
                                }
                            })
                        }
                    })
                }
            }
        }
    }
}