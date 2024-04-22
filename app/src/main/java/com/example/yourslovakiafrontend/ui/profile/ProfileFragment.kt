package com.example.yourslovakiafrontend.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yourslovakiafrontend.databinding.FragmentProfileBinding

    private fun showLanguagePopupMenu(anchorView: Button) {
        val popupMenu = PopupMenu(this, anchorView)
        popupMenu.menuInflater.inflate(R.menu.language_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.czech -> {
                    setLocale("cs")
                    true
                }

                R.id.german -> {
                    setLocale("de")
                    true
                }

    private var _binding: FragmentProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

                R.id.slovak -> {
                    setLocale("sk")
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.setLocale(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)

        // Restart the activity to apply the new locale
        recreate()
    }
}