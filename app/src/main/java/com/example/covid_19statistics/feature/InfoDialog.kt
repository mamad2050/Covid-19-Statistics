package com.example.covid_19statistics.feature

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.covid_19statistics.BuildConfig
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.TELEGRAM_PAGE_ID
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Exception

class InfoDialog : DialogFragment() {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = MaterialAlertDialogBuilder(requireContext())

        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.info_dialog_layout, null, false)
        builder.setView(view)

        val ivClose = view.findViewById<ImageView>(R.id.iv_close)
        val ivTelegram = view.findViewById<ImageView>(R.id.iv_telegram)
        val btnVote = view.findViewById<MaterialButton>(R.id.btn_vote)

btnVote.setOnClickListener {
    rateApp()
}

        ivClose.setOnClickListener {
            dismiss()
        }

        ivTelegram.setOnClickListener {
            val uri = Uri.parse("tg://resolve?domain=${TELEGRAM_PAGE_ID}")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }



        return builder.create()

    }

    private fun rateApp() {
        try {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.data = Uri.parse("bazaar://details?id=${BuildConfig.APPLICATION_ID}")
            intent.setPackage("com.farsitel.bazaar")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), getString(R.string.install_bazaar_app), Toast.LENGTH_SHORT)
                .show()
        }
    }


}