package com.example.covid_19statistics.feature

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.TELEGRAM_PAGE_ID
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InfoDialog : DialogFragment() {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = MaterialAlertDialogBuilder(requireContext())

        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.info_dialog_layout, null, false)
        builder.setView(view)

        val ivClose = view.findViewById<ImageView>(R.id.iv_close)
        val ivTelegram = view.findViewById<ImageView>(R.id.iv_telegram)


        ivClose.setOnClickListener {
            dismiss()
        }

        ivTelegram.setOnClickListener {
            val uri = Uri.parse("tg://resolve?domain=${TELEGRAM_PAGE_ID}")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }



        return builder.create()

    }


}