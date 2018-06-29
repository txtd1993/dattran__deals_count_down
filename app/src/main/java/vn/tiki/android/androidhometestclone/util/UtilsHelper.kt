package vn.tiki.android.androidhometestclone.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import java.io.InputStream
import java.util.*
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.content.Intent
import vn.tiki.android.androidhometestclone.adapter.DealAdapter
import vn.tiki.android.androidhometestclone.data.api.response.Deal


class UtilsHelper {
    companion object {
        fun getBitmapFromAssets(fileName: String, context: Context): Bitmap? {
            val assetManager = context.assets
            var inputStream: InputStream? = null
            try {
                inputStream = assetManager.open(fileName)
                return BitmapFactory.decodeStream(inputStream)

            } catch (e: Exception) {
                e.printStackTrace()

            } finally {
                inputStream?.close()
            }


            return null
        }

        fun getWidthOfScreen(context: Activity): Int {
            val displayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

        fun getDeltaTimeByMilliSeconds(endDate: Date): Long {
            return endDate.time - Calendar.getInstance().timeInMillis
        }

    }
}