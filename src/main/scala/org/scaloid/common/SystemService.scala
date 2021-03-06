
/*
 *
 *
 *
 *
 * Less painful Android development with Scala
 *
 * http://scaloid.org
 *
 *
 *
 *
 *
 *
 * Copyright 2013 Sung-Ho Lee
 *
 * Sung-Ho Lee licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.scaloid.common

import android.app._
import admin.DevicePolicyManager
import android.view._
import android.net._
import android.os._
import android.media._

import android.view.accessibility._
import android.accounts._
import android.view.inputmethod._
import android.location._
import android.hardware._
import android.telephony._
import android.net.wifi._
import android.content._
import java.lang.CharSequence

trait SystemService {
  @inline def accessibilityManager  (implicit context: Context): AccessibilityManager    = context.getSystemService(Context.ACCESSIBILITY_SERVICE ).asInstanceOf[AccessibilityManager]
  @inline def accountManager        (implicit context: Context): AccountManager          = context.getSystemService(Context.ACCOUNT_SERVICE       ).asInstanceOf[AccountManager]
  @inline def activityManager       (implicit context: Context): ActivityManager         = context.getSystemService(Context.ACTIVITY_SERVICE      ).asInstanceOf[ActivityManager]
  @inline def alarmManager          (implicit context: Context): AlarmManager            = context.getSystemService(Context.ALARM_SERVICE         ).asInstanceOf[AlarmManager]
  @inline def audioManager          (implicit context: Context): AudioManager            = context.getSystemService(Context.AUDIO_SERVICE         ).asInstanceOf[AudioManager]
  @inline def clipboardManager      (implicit context: Context): android.text.ClipboardManager =context.getSystemService(Context.CLIPBOARD_SERVICE).asInstanceOf[android.text.ClipboardManager]

  class RichClipboardManager(cm: android.text.ClipboardManager) {
    def text_=(txt: CharSequence) = cm.setText(txt)
    def text = cm.getText
  }

  @inline implicit def richClipboardManager(cm: android.text.ClipboardManager): RichClipboardManager = new RichClipboardManager(cm)

  @inline def connectivityManager   (implicit context: Context): ConnectivityManager    = context.getSystemService(Context.CONNECTIVITY_SERVICE   ).asInstanceOf[ConnectivityManager]
  @inline def devicePolicyManager   (implicit context: Context): DevicePolicyManager    = context.getSystemService(Context.DEVICE_POLICY_SERVICE  ).asInstanceOf[DevicePolicyManager]
  @inline def dropBoxManager        (implicit context: Context): DropBoxManager         = context.getSystemService(Context.DROPBOX_SERVICE        ).asInstanceOf[DropBoxManager]
  @inline def inputMethodManager    (implicit context: Context): InputMethodManager     = context.getSystemService(Context.INPUT_METHOD_SERVICE   ).asInstanceOf[InputMethodManager]
  @inline def keyguardManager       (implicit context: Context): KeyguardManager        = context.getSystemService(Context.KEYGUARD_SERVICE       ).asInstanceOf[KeyguardManager]
  @inline def layoutInflater        (implicit context: Context): LayoutInflater         = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE).asInstanceOf[LayoutInflater]
  @inline def locationManager       (implicit context: Context): LocationManager        = context.getSystemService(Context.LOCATION_SERVICE       ).asInstanceOf[LocationManager]
  @inline def notificationManager   (implicit context: Context): NotificationManager    = context.getSystemService(Context.NOTIFICATION_SERVICE   ).asInstanceOf[NotificationManager]
  @inline def powerManager          (implicit context: Context): PowerManager           = context.getSystemService(Context.POWER_SERVICE          ).asInstanceOf[PowerManager]
  @inline def searchManager         (implicit context: Context): SearchManager          = context.getSystemService(Context.SEARCH_SERVICE         ).asInstanceOf[SearchManager]
  @inline def sensorManager         (implicit context: Context): SensorManager          = context.getSystemService(Context.SENSOR_SERVICE         ).asInstanceOf[SensorManager]
  @inline def telephonyManager      (implicit context: Context): TelephonyManager       = context.getSystemService(Context.TELEPHONY_SERVICE      ).asInstanceOf[TelephonyManager]

  def onCallForwardingIndicatorChanged(fun: Boolean => Any)(implicit ctx: Context, reg: Registerable) {
    val callStateListener = new PhoneStateListener() {
      override def onCallForwardingIndicatorChanged(cfi: Boolean) {
        fun(cfi)
      }
    }
    reg.onRegister {
      telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR)
    }
    reg.onUnregister {
      telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
    }
  }

  def onCallStateChanged(fun: (Int, String) => Any)(implicit ctx: Context, reg: Registerable) {
    val callStateListener = new PhoneStateListener() {
      override def onCallStateChanged(state: Int, incomingNumber: String) {
        fun(state, incomingNumber)
      }
    }
    reg.onRegister {
      telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }
    reg.onUnregister {
      telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
    }
  }

  def onCellLocationChanged(fun: CellLocation => Any)(implicit ctx: Context, reg: Registerable) {
    val callStateListener = new PhoneStateListener() {
      override def onCellLocationChanged(cellLocation: CellLocation) {
        fun(cellLocation)
      }
    }
    reg.onRegister {
      telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CELL_LOCATION)
    }
    reg.onUnregister {
      telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
    }
  }

  @inline def uiModeManager         (implicit context: Context): UiModeManager          = context.getSystemService(Context.UI_MODE_SERVICE        ).asInstanceOf[UiModeManager]
  @inline def vibrator              (implicit context: Context): Vibrator               = context.getSystemService(Context.VIBRATOR_SERVICE       ).asInstanceOf[Vibrator]
  @inline def wallpaperManager      (implicit context: Context): WallpaperManager       = context.getSystemService(Context.WALLPAPER_SERVICE      ).asInstanceOf[WallpaperManager]
  @inline def wifiManager           (implicit context: Context): WifiManager            = context.getSystemService(Context.WIFI_SERVICE           ).asInstanceOf[WifiManager]
  @inline def windowManager         (implicit context: Context): WindowManager          = context.getSystemService(Context.WINDOW_SERVICE         ).asInstanceOf[WindowManager]
}