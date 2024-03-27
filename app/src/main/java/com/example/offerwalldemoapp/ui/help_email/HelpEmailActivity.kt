package com.example.offerwalldemoapp.ui.help_email

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import com.example.offerwalldemoapp.databinding.ActivityHelpEmailBinding
import com.example.offerwalldemoapp.utility.showToast

class HelpEmailActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHelpEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHelpEmailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupListeners()
    }

    private fun setupListeners() {
        with(mBinding) {
            btnBack.setOnClickListener {
                finish()
            }
            btnSendMail.setOnClickListener {
                val recipient = "support@pubscale.com"
                val subject = etSubject.text.toString().trim()
                if (subject.isBlank()) {
                    showToast("Subject cannot be blank.")
                    return@setOnClickListener
                }
                val appLink = etAppLink.text.toString().trim()
                if (appLink.isBlank()) {
                    showToast("App link cannot be blank.")
                    return@setOnClickListener
                }
                if (!URLUtil.isValidUrl(appLink)) {
                    showToast("App link is not a valid URL.")
                    return@setOnClickListener
                }
                val message = etMessage.text.toString().trim()
                if (message.isBlank()) {
                    showToast("Message cannot be blank.")
                    return@setOnClickListener
                }
                val finalSubject =
                    StringBuilder().append("[PUBSCALE DEMO APP] - ").append(subject).toString()
                val finalMessage =
                    StringBuilder().append(message).append("\n\n").append("App Link: $appLink")
                        .toString()
                sendEmail(recipient, finalSubject, finalMessage)
            }
        }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_TEXT, message)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Send email..."))
        }
    }
}