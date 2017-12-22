package com.app.lyasin.starwarsapp.activity

import android.os.Bundle
import com.app.lyasin.starwarsapp.R
import com.google.zxing.Result
import io.vrinda.kotlinpermissions.PermissionCallBack
import io.vrinda.kotlinpermissions.PermissionsActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.startActivity


class QrCodeActivity : PermissionsActivity() , ZXingScannerView.ResultHandler  {

    private var scannerView: ZXingScannerView? = null

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        checkPermission()
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
    }

    private fun checkPermission(){
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), object : PermissionCallBack {
                override fun permissionGranted() {
                    super.permissionGranted()
                   // Log.v("Camera permissions", "Granted")
                }

                override fun permissionDenied() {
                    super.permissionDenied()
                 //   Log.v("Camera permissions", "Denied")
                    finish()
                }
            })

    }

    public override fun onResume() {
        super.onResume()
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        startActivity<CharacterActivity>(getString(R.string.from_qrcode) to rawResult.text)

    }

}