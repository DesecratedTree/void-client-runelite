# Balanced: obfuscate default-package (project) classes, leave packaged libs intact.

-dontoptimize
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,SourceFile,LineNumberTable,Exceptions
-renamesourcefileattribute SourceFile

# Entry points
-keep public class Loader {
    public static void main(java.lang.String[]);
}

# Applet lifecycle on any default-package subclass
-keep class * extends java.applet.Applet {
    public void init();
    public void start();
    public void stop();
    public void destroy();
    <init>();
}

# Native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Guice injection targets in project classes
-keepclasseswithmembers class * {
    @com.google.inject.Inject <init>(...);
}
-keepclassmembers class * {
    @com.google.inject.Inject *;
}

# All packaged classes — treat as untouchable libs
-keep class **.** { *; }
-keepclassmembers class **.** { *; }

# Silence unresolved references inside third-party jars
-dontwarn **
