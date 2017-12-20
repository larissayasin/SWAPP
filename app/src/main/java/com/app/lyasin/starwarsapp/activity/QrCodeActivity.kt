package com.app.lyasin.starwarsapp.activity

import android.os.Bundle
import android.util.Log
import com.app.lyasin.starwarsapp.R
import com.google.zxing.Result
import io.vrinda.kotlinpermissions.PermissionCallBack
import io.vrinda.kotlinpermissions.PermissionsActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.startActivity


class QrCodeActivity : PermissionsActivity() , ZXingScannerView.ResultHandler  {

    private var mScannerView: ZXingScannerView? = null

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        checkPermission()
        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)
    }

    private fun checkPermission(){
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), object : PermissionCallBack {
                override fun permissionGranted() {
                    super.permissionGranted()
                    Log.v("Camera permissions", "Granted")
                }

                override fun permissionDenied() {
                    super.permissionDenied()
                    Log.v("Camera permissions", "Denied")
                    finish()
                }
            })

    }

    public override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView?.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        startActivity<CharacterActivity>(getString(R.string.from_qrcode) to rawResult.text)

       // mScannerView?.resumeCameraPreview(this)
    }

}