@file:Suppress("ktlint:standard:max-line-length", "ktlint:standard:argument-list-wrapping")

package app.immich.kmp.di.platform.components

import android.accounts.AccountManager
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.AppOpsManager
import android.app.DownloadManager
import android.app.KeyguardManager
import android.app.NotificationManager
import android.app.SearchManager
import android.app.UiModeManager
import android.app.WallpaperManager
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.content.pm.ShortcutManager
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.midi.MidiManager
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.BatteryManager
import android.os.DropBoxManager
import android.os.HardwarePropertiesManager
import android.os.PowerManager
import android.os.UserManager
import android.os.Vibrator
import android.os.health.SystemHealthManager
import android.os.storage.StorageManager
import android.print.PrintManager
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class AndroidSystemServiceComponent(
  @Component val androidComponent: AndroidComponent,
) {
  private val context = androidComponent.context

  @Provides fun accessibilityManager(): AccessibilityManager =
    context.getSystemService(AccessibilityManager::class.java)

  @Provides fun accountManager(): AccountManager = context.getSystemService(AccountManager::class.java)
  @Provides fun activityManager(): ActivityManager = context.getSystemService(ActivityManager::class.java)
  @Provides fun alarmManager(): AlarmManager = context.getSystemService(AlarmManager::class.java)
  @Provides fun appOpsManager(): AppOpsManager = context.getSystemService(AppOpsManager::class.java)
  @Provides fun appWidgetManager(): AppWidgetManager = context.getSystemService(AppWidgetManager::class.java)
  @Provides fun audioManager(): AudioManager = context.getSystemService(AudioManager::class.java)
  @Provides fun batteryManager(): BatteryManager = context.getSystemService(BatteryManager::class.java)
  @Provides fun bluetoothManager(): BluetoothManager = context.getSystemService(BluetoothManager::class.java)
  @Provides fun cameraManager(): CameraManager = context.getSystemService(CameraManager::class.java)
  @Provides fun captioningManager(): CaptioningManager = context.getSystemService(CaptioningManager::class.java)
  @Provides fun clipboardManager(): ClipboardManager = context.getSystemService(ClipboardManager::class.java)
  @Provides fun connectivityManager(): ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)
  @Provides fun consumerIrManager(): ConsumerIrManager = context.getSystemService(ConsumerIrManager::class.java)
  @Provides fun devicePolicyManager(): DevicePolicyManager = context.getSystemService(DevicePolicyManager::class.java)
  @Provides fun displayManager(): DisplayManager = context.getSystemService(DisplayManager::class.java)
  @Provides fun downloadManager(): DownloadManager = context.getSystemService(DownloadManager::class.java)
  @Provides fun dropBoxManager(): DropBoxManager = context.getSystemService(DropBoxManager::class.java)
  @Provides fun carrierConfigManager(): CarrierConfigManager =
    context.getSystemService(CarrierConfigManager::class.java)

  @Provides fun hardwarePropertiesManager(): HardwarePropertiesManager =
    context.getSystemService(HardwarePropertiesManager::class.java)

  @Provides fun inputManager(): InputManager = context.getSystemService(InputManager::class.java)
  @Provides fun inputMethodManager(): InputMethodManager = context.getSystemService(InputMethodManager::class.java)
  @Provides fun jobScheduler(): JobScheduler = context.getSystemService(JobScheduler::class.java)
  @Provides fun keyguardManager(): KeyguardManager = context.getSystemService(KeyguardManager::class.java)
  @Provides fun launcherApps(): LauncherApps = context.getSystemService(LauncherApps::class.java)
  @Provides fun layoutInflater(): LayoutInflater = context.getSystemService(LayoutInflater::class.java)
  @Provides fun locationManager(): LocationManager = context.getSystemService(LocationManager::class.java)
  @Provides fun midiManager(): MidiManager = context.getSystemService(MidiManager::class.java)
  @Provides fun mediaProjectionManager(): MediaProjectionManager =
    context.getSystemService(MediaProjectionManager::class.java)

  @Provides fun mediaRouter(): MediaRouter = context.getSystemService(MediaRouter::class.java)
  @Provides fun mediaSessionManager(): MediaSessionManager = context.getSystemService(MediaSessionManager::class.java)
  @Provides fun networkStatsManager(): NetworkStatsManager = context.getSystemService(NetworkStatsManager::class.java)
  @Provides fun nfcManager(): NfcManager = context.getSystemService(NfcManager::class.java)
  @Provides fun notificationManager(): NotificationManager = context.getSystemService(NotificationManager::class.java)
  @Provides fun nsdManager(): NsdManager = context.getSystemService(NsdManager::class.java)
  @Provides fun powerManager(): PowerManager = context.getSystemService(PowerManager::class.java)
  @Provides fun printManager(): PrintManager = context.getSystemService(PrintManager::class.java)
  @Provides fun restrictionsManager(): RestrictionsManager = context.getSystemService(RestrictionsManager::class.java)
  @Provides fun searchManager(): SearchManager = context.getSystemService(SearchManager::class.java)
  @Provides fun sensorManager(): SensorManager = context.getSystemService(SensorManager::class.java)
  @Provides fun shortcutManager(): ShortcutManager = context.getSystemService(ShortcutManager::class.java)
  @Provides fun storageManager(): StorageManager = context.getSystemService(StorageManager::class.java)
  @Provides fun subscriptionManager(): SubscriptionManager = context.getSystemService(SubscriptionManager::class.java)
  @Provides fun systemHealthManager(): SystemHealthManager = context.getSystemService(SystemHealthManager::class.java)
  @Provides fun telecomManager(): TelecomManager = context.getSystemService(TelecomManager::class.java)
  @Provides fun telephonyManager(): TelephonyManager = context.getSystemService(TelephonyManager::class.java)
  @Provides fun textServicesManager(): TextServicesManager = context.getSystemService(TextServicesManager::class.java)
  @Provides fun tvInputManager(): TvInputManager = context.getSystemService(TvInputManager::class.java)
  @Provides fun uiModeManager(): UiModeManager = context.getSystemService(UiModeManager::class.java)
  @Provides fun usageStatsManager(): UsageStatsManager = context.getSystemService(UsageStatsManager::class.java)
  @Provides fun usbManager(): UsbManager = context.getSystemService(UsbManager::class.java)
  @Provides fun userManager(): UserManager = context.getSystemService(UserManager::class.java)
  @Provides fun vibrator(): Vibrator = context.getSystemService(Vibrator::class.java)
  @Provides fun wallpaperManager(): WallpaperManager = context.getSystemService(WallpaperManager::class.java)
  @Provides fun wifiP2pManager(): WifiP2pManager = context.getSystemService(WifiP2pManager::class.java)
  @Provides fun wifiManager(): WifiManager = context.getSystemService(WifiManager::class.java)
  @Provides fun windowManager(): WindowManager = context.getSystemService(WindowManager::class.java)

  companion object
}
