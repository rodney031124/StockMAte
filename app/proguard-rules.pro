# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the Android SDK tools/proguard/proguard-android.txt

-dontwarn com.google.firebase.**
-keep class com.stockmate.app.** { *; }
-keepattributes Signature
-keepattributes *Annotation*