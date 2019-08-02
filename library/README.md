# Xposed Ktx
Xposed kotlin extension.

[![](https://jitpack.io/v/ikws4/xposed-ktx.svg)](https://jitpack.io/#ikws4/xposed-ktx)

## Gradle Dependency
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
      ...
      maven { url 'https://jitpack.io' }
   }
}
```
Add the dependency
```gradle
dependencies {
    implementation 'com.github.ikws4:xposed-ktx:latest-version'
}
```

## Hook method
Use className(String) to hook method.
```kotlin
val className = "android.app.Activity"
className.hookMethod(classLoder, "onCreate", Bundle::class.java,
    beforeHookedMethod = { param ->
        // Do something
    },
    afterHookedMethod = { param ->
        // Do something
    })
    
// if you just use afterHookedMethod, you can do this

Activity::class.java.hookMethod("onCreate", Bundle::class.java) { param ->
     // Do something
}
```
Use class to hook method, then you can use 'this' to get Activity instance.
```kotlin
Activity::class.java.hookMethod("onCreate", Bundle::class.java, 
    beforeHookedMethod = { param ->
        // for example
        Toast.makeText(this, "I'm a toast", Toast.LENGHT_SHORT).show()
    },
    afterHookedMethod = { param ->
        // Do something
    })
```

## Replace method
```kotlin
Activity::class.java.replaceMethod("onCreate", Bundle::class.java){ param ->
      // `this` is the Activity instance
}

// or

val className = "android.app.Activity"
className.replaceMethod(classLoder, "onCreate", Bundle::class.java){ param ->
      // Do something
}
```

## Hook constructor
```kotlin
TextView::class.java.hookConstructor(Context::class.java,
     beforeHookedMethod = { param ->
          // Do something
          // 'this' is the TextView instance
     },
     afterHookedMethod = { param ->
         // Do something
     })

// or

val className = "android.widget.TextView"
className.hookConstructor(classLoader,Context::class.java,
     beforeHookedMethod = { param ->
         // Do something
     },
     afterHookedMethod = { param ->
         // Do something
     })
```

## Replace constructor
```kotlin
TextView::class.java.replaceConstructor(Context::class.java) { param ->
     // 'this' is the TextView instance
     // Do something
}

// or

val className = "android.widget.TextView"
className.replaceConstructor(classLoader,Context::class.java) { param ->
     // Do something
}
```