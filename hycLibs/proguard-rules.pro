
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

##okgo
#-dontwarn com.lzy.okgo.**
#-keep class com.lzy.okgo.**{*;}
#
##okrx
#-dontwarn com.lzy.okrx.**
#-keep class com.lzy.okrx.**{*;}
#
##okserver
#-dontwarn com.lzy.okserver.**
#-keep class com.lzy.okserver.**{*;}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}