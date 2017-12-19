package com.app.lyasin.starwarsapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.app.lyasin.starwarsapp.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_qr_code.setOnClickListener { startActivity<QrCodeActivity>() }
    }
}
