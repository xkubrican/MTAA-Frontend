package com.example.yourslovakiafrontend.ui.profile

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.yourslovakiafrontend.R
import java.util.Locale

class ProfileFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        val changeLanguageButton = findViewById<Button>(R.id.button_change_language)
        changeLanguageButton.setOnClickListener { showLanguagePopupMenu(changeLanguageButton) }
    }

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

                R.id.english -> {
                    setLocale("en")
                    true
                }

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