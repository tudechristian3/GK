# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\user\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
##-----------------AMAZON-------------------------
## Class names are needed in reflection
-keepnames class com.amazonaws.**
-keepnames class com.amazon.**
## Request handlers defined in request.handlers
-keep class com.amazonaws.services.**.*Handler
## The following are referenced but aren't required to run
-dontwarn com.fasterxml.jackson.**
-dontwarn org.apache.commons.logging.**
## Android 6.0 release removes support for the Apache HTTP client
-dontwarn org.apache.http.**
## The SDK has several references of Apache HTTP client
-dontwarn com.amazonaws.http.**
-dontwarn com.amazonaws.metrics.**
#
##-----------------TwitterHandle-------------------
-dontwarn oauth.**
-dontwarn com.android.auth.TwitterHandle.**

-keep class oauth.** { *; }
-keep class com.android.auth.TwitterHandle.** { *; }
#
#
##------------Picasso-------------------
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
#
#
##----------------Retrofit-----------------
## Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
## EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod
#
## Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
#
## Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
#
## Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#
## Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
#
## Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
#
## Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
#
## With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
## and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

-repackageclasses