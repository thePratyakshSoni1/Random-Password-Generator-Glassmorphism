package com.smartphone_codes.randompasswordgenerator

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){

    companion object {
        const val PASSKEY:String="PASSWORD_KEY"
    }

    val alphsbets: String = "abcdefghijklmnopqrstuvwxyz"
    val digits: String = "0123456789"
    val specialChars: String = "$#@!%&*,"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RandomPasswordGenerator)
        setContentView(R.layout.activity_main)

        //handling status bar since our minSDK is 24 so we don't have to check
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT


        val passTXT: TextView = findViewById(R.id.textView2)
        val clearBtn: Button = findViewById(R.id.clearBtn)
        clearBtn.visibility = View.GONE

        if(savedInstanceState != null){
            passTXT.text=savedInstanceState.getString(PASSKEY, null)
            if(passTXT.text==""){
                clearBtn.visibility=View.GONE
            }else{
                clearBtn.visibility=View.VISIBLE

            }
        }



        clearBtn.setOnClickListener {
            passTXT.text = null
            clearBtn.visibility = View.GONE
        }


        findViewById<Button>(R.id.button).setOnClickListener {

            val password: String = generatePassword().joinToString("")
            passTXT.text = password
            clearBtn.visibility = View.VISIBLE
        }

        findViewById<Button>(R.id.copy_btn).setOnClickListener {
            copyText(passTXT.text.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val passTXT: TextView = findViewById(R.id.textView2)
        super.onSaveInstanceState(outState)
        if(passTXT.text!=null){
            outState.putString(PASSKEY,passTXT.text.toString())
        }
    }

    fun generatePassword(): List<String> {
        var password = mutableListOf<String>()
        password.add(specialChars.random().toString())
        password.add(alphsbets.random().uppercaseChar().toString())
        for (n in 1..3) {
            password.add(digits.random().toString())
            password.add(alphsbets.random().toString())
        }
        return password.shuffled()
    }

    fun copyText(txt: String) {
        val ClipManager: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipdata: ClipData = ClipData.newPlainText("Password", txt)
        ClipManager.setPrimaryClip(clipdata)
        Toast.makeText(this, "Password Copied ✅", Toast.LENGTH_SHORT).show()

    }

    fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win: Window = activity.window
        val winParams: WindowManager.LayoutParams = win.getAttributes()
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.setAttributes(winParams)
    }

}