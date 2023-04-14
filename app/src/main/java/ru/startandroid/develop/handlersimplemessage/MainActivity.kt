package ru.startandroid.develop.handlersimplemessage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    val LOG_TAG = "myLogs"
    val STATUS_NONE = 0
    val STATUS_CONNECTING = 1
    val STATUS_CONNECTED = 2
    var h: Handler? = null
    var tvStatus: TextView? = null
    var pbConnect: ProgressBar? = null
    var btnConnect: Button? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStatus = findViewById<View>(R.id.tvStatus) as TextView
        pbConnect = findViewById<View>(R.id.pbConnect) as ProgressBar
        btnConnect = findViewById<View>(R.id.btnConnect) as Button
        h = object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    STATUS_NONE -> {
                        btnConnect!!.isEnabled = true
                        tvStatus!!.text = "Not connected"
                    }
                    STATUS_CONNECTING -> {
                        btnConnect!!.isEnabled = false
                        pbConnect!!.visibility = View.VISIBLE
                        tvStatus!!.text = "Connecting"
                    }
                    STATUS_CONNECTED -> {
                        pbConnect!!.visibility = View.GONE
                        tvStatus!!.text = "Connected"
                    }
                }
            }
        }
        h!!.sendEmptyMessage(STATUS_NONE)
    }

    fun onClick(v: View?) {
        val t = Thread {
            try {
                h!!.sendEmptyMessage(STATUS_CONNECTING)
                Thread.sleep(2000)

                h!!.sendEmptyMessage(STATUS_CONNECTED)

                Thread.sleep(3000)

                h!!.sendEmptyMessage(STATUS_NONE)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        t.start()
    }
}