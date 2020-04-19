package com.example.kotlinpostapi.views

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.kotlinpostapi.R

class ErrorDialog<T: Any>(val callback: T) : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val builder = AlertDialog.Builder(activity)
            .setTitle("Błąd")
            .setCancelable(false)
            .setNegativeButton("Anuluj") {_, _ -> activity?.finish() }
            .setPositiveButton("Spróbuj ponownie") { _, _ -> callback}

        return builder.create()
    }
}
