package app.bootcamp.com.todoapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.telephony.TelephonyManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sign

class Splash : AppCompatActivity() {
    val PERMISSION_READ_STATE = 1000
    val auth =FirebaseAuth.getInstance()
    var deviceId = System.currentTimeMillis().toString();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        if(auth.currentUser != null){
            launchMain()
        }else{
            signIn()
        }

    }

    private fun signIn(){
        auth.signInAnonymously().addOnCompleteListener({
            if (it.isSuccessful){
                launchMain()
            }else{
               getImei()
            }
        })
    }

    private fun launchMain(){
        Handler().postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("imei",deviceId)
            startActivity(intent)
            finish()
        },2000);
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_READ_STATE -> {
                getImei()
                return
            }
        }
    }

    private fun getImei(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.READ_PHONE_STATE), PERMISSION_READ_STATE);
        }else{
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                deviceId = telephonyManager.imei
            } else {
                deviceId = telephonyManager.deviceId
            }
            launchMain()
        }
    }
}
