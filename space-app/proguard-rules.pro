# Core
-keep class com.hdeva.space.core.net.dto.** { *; }
-keep class com.hdeva.space.core.net.converter.** { *; }
-keep class com.hdeva.space.core.model.** { *; }

# Support library
-keep class android.support.** { *; }

# Views
-keep class com.hdeva.space.ui.widget.** { *; }
-keep class com.google.android.gms.ads.** { *; }
-keep class com.aurelhubert.ahbottomnavigation.** { *; }
-keep class com.github.chrisbanes.** { *; }
-keep class com.google.android.exoplayer2.ui.** { *; }
-keep class com.crystal.crystalrangeseekbar.widgets.** { *; }

# Picker dialogs
-keep class com.codetroopers.betterpickers.** { *; }

# Miscellaneous
-dontwarn sun.misc.Unsafe
-dontwarn com.google.errorprone.**
-dontwarn java.lang.invoke.**
-dontwarn org.codehaus.mojo.**
