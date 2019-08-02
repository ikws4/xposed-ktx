package io.ikws4.library.xposedktx

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

/**
 * example for usage
 * ```
 * TextView::class.java.hookConstructor(Context::class.java,
 *      beforeHookedMethod = { param ->
 *           // Do something
 *           // 'this' is the TextView instance
 *      },
 *      afterHookedMethod = { param ->
 *          // Do something
 *      })
 * ```
 * if you just use afterHookedMethod, you can do this
 * ```
 * TextView::class.java.hookConstructor(Context::class.java) { param ->
 *      // Do something
 * }
 * ```
 * @receiver Class<T>
 * @param parameterTypes Array<out Any?>
 * @param beforeHookedMethod [@kotlin.ExtensionFunctionType] Function2<T, [@kotlin.ParameterName] MethodHookParam, Unit>
 * @param afterHookedMethod [@kotlin.ExtensionFunctionType] Function2<T, [@kotlin.ParameterName] MethodHookParam, Unit>
 * @return XC_MethodHook.Unhook
 */
@Suppress("UNCHECKED_CAST")
inline fun <T> Class<T>.hookConstructor(
    vararg parameterTypes: Any?,
    crossinline beforeHookedMethod: T.(param: XC_MethodHook.MethodHookParam) -> Unit = {},
    crossinline afterHookedMethod: T.(param: XC_MethodHook.MethodHookParam) -> Unit = {}
): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookConstructor(this, *parameterTypes, object : XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam) {
            beforeHookedMethod.invoke(param.thisObject as T, param)
        }

        override fun afterHookedMethod(param: MethodHookParam) {
            afterHookedMethod.invoke(param.thisObject as T, param)
        }
    })

}

/**
 * example for usage
 * ```
 * className.hookConstructor(classLoader,parameterType1,parameterType2,...,
 *      beforeHookedMethod = { param ->
 *          // Do something
 *      },
 *      afterHookedMethod = { param ->
 *          // Do something
 *      })
 * ```
 * @receiver String
 * @param loader ClassLoader
 * @param parameterTypes Array<out Any?>
 * @param beforeHookedMethod Function1<[@kotlin.ParameterName] MethodHookParam, Unit>
 * @param afterHookedMethod Function1<[@kotlin.ParameterName] MethodHookParam, Unit>
 * @return XC_MethodHook.Unhook
 */
inline fun String.hookConstructor(
    loader: ClassLoader, vararg parameterTypes: Any?,
    crossinline beforeHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit = {},
    crossinline afterHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit = {}
): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookConstructor(
        this,
        loader,
        *parameterTypes,
        object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                beforeHookedMethod.invoke(param)
            }

            override fun afterHookedMethod(param: MethodHookParam) {
                afterHookedMethod.invoke(param)
            }
        })
}

/**
 * example for usage
 * ```
 * TextView::class.java.replaceConstructor(Context::class.java) { param ->
 *      // 'this' is the TextView instance
 *      // Do something
 * }
 * ```
 * @receiver Class<T>
 * @param parameterTypes Array<out Any?>
 * @param replaceHookedMethod [@kotlin.ExtensionFunctionType] Function2<T, [@kotlin.ParameterName] MethodHookParam, Any?>
 * @return XC_MethodHook.Unhook
 */
@Suppress("UNCHECKED_CAST")
inline fun <T> Class<T>.replaceConstructor(
    vararg parameterTypes: Any?,
    crossinline replaceHookedMethod: T.(param: XC_MethodHook.MethodHookParam) -> Any? = {}
): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookConstructor(this, *parameterTypes,
        object : XC_MethodReplacement() {
            override fun replaceHookedMethod(param: MethodHookParam): Any? {
                return replaceHookedMethod.invoke(param.thisObject as T, param)
            }
        })
}

/**
 * example for usage
 * ```
 * className.replaceConstructor(classLoader,parameterType1,parameterType2,...) { param ->
 *      // Do something
 * }
 * ```
 * @receiver String
 * @param loader ClassLoader
 * @param parameterTypes Array<out Any?>
 * @param replaceHookedMethod Function1<[@kotlin.ParameterName] MethodHookParam, Any?>
 * @return XC_MethodHook.Unhook
 */
inline fun String.replaceConstructor(
    loader: ClassLoader, vararg parameterTypes: Any?,
    crossinline replaceHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Any? = {}
): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookConstructor(this, loader, *parameterTypes,
        object : XC_MethodReplacement() {
            override fun replaceHookedMethod(param: MethodHookParam): Any? {
                return replaceHookedMethod.invoke(param)
            }
        })
}

/**
 * example for usage:
 * ```
 * Activity::class.java.hookMethod("onCreate", Bundle::class.java,
 *      beforeHookedMethod = { param ->
 *          // `this` is activity instance
 *          // for example, you can use this.actionBar or actionBar
 *      },
 *      afterHookedMethod = { param ->
 *          // Do something
 *      })
 * ```
 * @receiver Class<*> 类
 * @param methodName String 方法名
 * @param parameterTypes Array<out Any> 方法参数类型
 * @param beforeHookedMethod [@kotlin.ExtensionFunctionType] Function2<T, [@kotlin.ParameterName] MethodHookParam, Unit>
 * @param afterHookedMethod [@kotlin.ExtensionFunctionType] Function2<T, [@kotlin.ParameterName] MethodHookParam, Unit>
 * @return XC_MethodHook.Unhook
 */
@Suppress("UNCHECKED_CAST")
inline fun <T> Class<T>.hookMethod(
    methodName: String, vararg parameterTypes: Any?,
    crossinline beforeHookedMethod: T.(param: XC_MethodHook.MethodHookParam) -> Unit = {},
    crossinline afterHookedMethod: T.(param: XC_MethodHook.MethodHookParam) -> Unit = {}
): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookMethod(
        this,
        methodName,
        *parameterTypes,
        object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                beforeHookedMethod.invoke(param.thisObject as T, param)
            }

            override fun afterHookedMethod(param: MethodHookParam) {
                afterHookedMethod.invoke(param.thisObject as T, param)
            }
        })
}

/**
 * example for usage
 * ```
 * className.hookMethod(classLoader, "methodName",
 *      beforeHookedMethod = { param ->
 *          // Do something
 *      },
 *      afterHookedMethod = { param ->
 *          // Do something
 *      })
 * ```
 * @receiver String 类名
 * @param loader ClassLoader 类加载器
 * @param methodName String 方法名
 * @param parameterTypes Array<out Any> 方法参数类型
 * @param beforeHookedMethod Function1<[@kotlin.ParameterName] MethodHookParam, Unit>
 * @param afterHookedMethod Function1<[@kotlin.ParameterName] MethodHookParam, Unit>
 * @return XC_MethodHook.Unhook
 */
inline fun String.hookMethod(
    loader: ClassLoader, methodName: String, vararg parameterTypes: Any?,
    crossinline beforeHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit = {},
    crossinline afterHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit = {}
): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookMethod(
        this,
        loader,
        methodName,
        *parameterTypes,
        object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                beforeHookedMethod.invoke(param)
            }

            override fun afterHookedMethod(param: MethodHookParam) {
                afterHookedMethod.invoke(param)
            }
        })
}

/**
 * example for usage
 * ```
 * Activity::class.java.replaceMethod("onCreate", Bundle::class.java){ param ->
 *      // `this` is the Activity instance
 * }
 * ```
 * @receiver Class<*> 类
 * @param methodName String 方法名
 * @param parameterTypes Array<out Any> 方法参数类型
 * @param replaceHookedMethod [@kotlin.ExtensionFunctionType] Function2<T, [@kotlin.ParameterName] MethodHookParam, Any?>
 * @return XC_MethodHook.Unhook
 */
@Suppress("UNCHECKED_CAST")
inline fun <T> Class<T>.replaceMethod(
    methodName: String, vararg parameterTypes: Any?,
    crossinline replaceHookedMethod: T.(param: XC_MethodHook.MethodHookParam) -> Any? = {}
): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookMethod(
        this,
        methodName,
        *parameterTypes,
        object : XC_MethodReplacement() {
            override fun replaceHookedMethod(param: MethodHookParam): Any? {
                return replaceHookedMethod.invoke(param.thisObject as T, param)
            }
        })
}

/**
 * example for usage
 * ```
 * className.replaceMethod("methodName", parameterType1, parameterType2,...){ param ->
 *      // Do something
 * }
 * ```
 * @receiver String 类名
 * @param loader ClassLoader 类加载器
 * @param methodName String 方法名
 * @param parameterTypes Array<out Any> 方法的参数类型
 * @param replaceHookedMethod Function1<[@kotlin.ParameterName] MethodHookParam, Any>
 * @return XC_MethodHook.Unhook
 */
inline fun String.replaceMethod(
    loader: ClassLoader, methodName: String, vararg parameterTypes: Any?,
    crossinline replaceHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Any? = {}
): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookMethod(
        this,
        loader,
        methodName,
        *parameterTypes,
        object : XC_MethodReplacement() {
            override fun replaceHookedMethod(param: MethodHookParam): Any? {
                return replaceHookedMethod.invoke(param)
            }
        })
}

/**
 * example for usage
 * ```
 * Toast::class.java.invokeStaticMethod("makeText", context,"I'm a toast", Toast.LENGTH_SHORT)
 *      .invokeMethod("show")
 * ```
 * @receiver Class<*> 调用类
 * @param methodName String 方法名
 * @param args Array<out Any> 方法参数
 * @return Any 方法调用后的返回值
 */
fun Class<*>.invokeStaticMethod(methodName: String, vararg args: Any): Any? {
    CharSequence::class.javaPrimitiveType
    return XposedHelpers.findMethodBestMatch(this, methodName, *args).invoke(null, *args)
}

/**
 * example for usage
 * ```
 * Toast::class.java.invokeStaticMethod("makeText",
 *      arrayOf(Context::class.java,CharSequence::class.java,Int::class.javaPrimitiveType),
 *      context,"I'm a toast", Toast.LENGTH_SHORT).invokeMethod("show")
 * ```
 * @receiver Class<*> 调用类
 * @param methodName String 方法名
 * @param parameterTypes Array<Class<*>> 方法参数类型
 * @param args Array<out Any> 方法参数
 * @return Any 方法调用后的返回值
 */
fun Class<*>.invokeStaticMethod(
    methodName: String,
    parameterTypes: Array<Class<*>>,
    vararg args: Any?
): Any? {
    return XposedHelpers.findMethodBestMatch(this, methodName, parameterTypes, *args)
        .invoke(null, *args)
}

/**
 * * example for usage
 * ```
 * textView.invokeMethod("setText", "test")
 * ```
 * @receiver Any 调用对象
 * @param clazz Class<Any> 如果无法找到方法，请使用改方法的原始类
 * @param methodName String 方法名
 * @param args Array<out Any> 方法参数
 * @return Any 方法调用后的返回值
 */
fun Any.invokeMethod(clazz: Class<*>? = null, methodName: String, vararg args: Any?): Any? {
    return XposedHelpers.findMethodBestMatch(clazz ?: this::class.java, methodName, *args)
        .invoke(this, *args)
}

/**
 * example for usage
 * ```
 * textView.invokeMethod("setText", arrayOf(CharSequence::class.java),"test")
 * ```
 * @receiver Any 调用对象
 * @param clazz Class<Any>
 * @param methodName String 方法名
 * @param parameterTypes Array<Class<*>> 方法参数类型
 * @param args Array<out Any> 方法参数
 * @return Any 方法调用后的返回值
 */
fun Any.invokeMethod(
    clazz: Class<*>? = null,
    methodName: String,
    parameterTypes: Array<Class<*>>,
    vararg args: Any?
): Any? {
    return XposedHelpers.findMethodBestMatch(
        clazz ?: this::class.java,
        methodName,
        parameterTypes,
        *args
    ).invoke(this, *args)
}

/**
 * example for usage
 * ```
 * TextView::class.java.newInstance(context)
 * ```
 * @receiver Class<*>
 * @param args Array<out Any?>
 * @return Any
 */
@Suppress("UNCHECKED_CAST")
fun Class<*>.newInstance(vararg args: Any?): Any {
    return XposedHelpers.newInstance(this, args)
}

/**
 * example for usage
 * ```
 * TextView::class.java.newInstance(arrayOf(Context::class.java), context)
 * ```
 * @receiver Class<*>
 * @param parameterTypes Array<Any>
 * @param args Array<out Any?>
 * @return Any
 */
@Suppress("UNCHECKED_CAST")
fun Class<*>.newInstance(parameterTypes: Array<Any>, vararg args: Any?): Any {
    return XposedHelpers.newInstance(this, parameterTypes, args)
}

/**
 * example for usage
 * ```
 * // getCharField()
 * textView.getCharField("testCharField")
 *
 * // setCharField()
 * textView.setCharField("testCharField", testValue)
 *
 * // getStaticCharField()
 * TextView::class.java.getStaticCharField("testStaticCharField")
 *
 * // setStaticCharField()
 * TextView::class.java.setStaticCharField("testStaticCharField", testValue)
 * ```
 */
fun Any.getCharField(fieldName: String): Char {
    return XposedHelpers.getCharField(this, fieldName)
}

fun Any.setCharField(fieldName: String, value: Char) {
    XposedHelpers.setCharField(this, fieldName, value)
}

fun Class<*>.getStaticCharField(fieldName: String): Char {
    return XposedHelpers.getStaticCharField(this, fieldName)
}

fun Class<*>.setStaticCharField(fieldName: String, value: Char) {
    XposedHelpers.setStaticCharField(this, fieldName, value)
}


/**
 * example for usage
 * ```
 * // getBooleanField()
 * textView.getBooleanField("testBooleanField")
 *
 * // setBooleanField()
 * textView.setBooleanField("testBooleanField", testValue)
 *
 * // getStaticBooleanField()
 * TextView::class.java.getStaticBooleanField("testStaticBooleanField")
 *
 * // setStaticBooleanField()
 * TextView::class.java.setStaticBooleanField("testStaticBooleanField", testValue)
 * ```
 */
fun Any.getBooleanField(fieldName: String): Boolean {
    return XposedHelpers.getBooleanField(this, fieldName)
}

fun Any.setBooleanField(fieldName: String, value: Boolean) {
    XposedHelpers.setBooleanField(this, fieldName, value)
}

fun Class<*>.getStaticBooleanField(fieldName: String): Boolean {
    return XposedHelpers.getBooleanField(this, fieldName)
}

fun Class<*>.setStaticBooleanField(fieldName: String, value: Boolean) {
    XposedHelpers.setBooleanField(this, fieldName, value)
}


/**
 * example for usage
 * ```
 * // getByteField()
 * textView.getByteField("testByteField")
 *
 * // setByteField()
 * textView.setByteField("testByteField", testValue)
 *
 * // getStaticByteField()
 * TextView::class.java.getStaticByteField("testStaticByteField")
 *
 * // setStaticByteField()
 * TextView::class.java.setStaticByteField("testStaticByteField", testValue)
 * ```
 */
fun Any.getByteField(fieldName: String): Byte {
    return XposedHelpers.getByteField(this, fieldName)
}

fun Any.setByteField(fieldName: String, value: Byte) {
    XposedHelpers.setByteField(this, fieldName, value)
}

fun Class<*>.getStaticByteField(fieldName: String): Byte {
    return XposedHelpers.getStaticByteField(this, fieldName)
}

fun Class<*>.setStaticByteField(fieldName: String, value: Byte) {
    XposedHelpers.setStaticByteField(this, fieldName, value)
}


/**
 * example for usage
 * ```
 * // getDoubleField()
 * textView.getDoubleField("testDoubleField")
 *
 * // setDoubleField()
 * textView.setDoubleField("testDoubleField", testValue)
 *
 * // getStaticDoubleField()
 * TextView::class.java.getStaticDoubleField("testStaticDoubleField")
 *
 * // setStaticDoubleField()
 * TextView::class.java.setStaticDoubleField("testStaticDoubleField", testValue)
 * ```
 */
fun Any.getDoubleField(fieldName: String): Double {
    return XposedHelpers.getDoubleField(this, fieldName)
}

fun Any.setDoubleField(fieldName: String, value: Double) {
    return XposedHelpers.setDoubleField(this, fieldName, value)
}

fun Class<*>.getStaticDoubleField(fieldName: String): Double {
    return XposedHelpers.getStaticDoubleField(this, fieldName)
}

fun Class<*>.setStaticDoubleField(fieldName: String, value: Double) {
    XposedHelpers.setDoubleField(this, fieldName, value)
}


/**
 * example for usage
 * ```
 * // getFloatField()
 * textView.getFloatField("testFloatField")
 *
 * // setFloatField()
 * textView.setFloatField("testFloatField", testValue)
 *
 * // getStaticFloatField()
 * TextView::class.java.getStaticFloatField("testStaticFloatField")
 *
 * // setStaticFloatField()
 * TextView::class.java.setStaticFloatField("testStaticFloatField", testValue)
 * ```
 */
fun Any.getFloatField(fieldName: String): Float {
    return XposedHelpers.getFloatField(this, fieldName)
}

fun Any.setFloatField(fieldName: String, value: Float) {
    XposedHelpers.setFloatField(this, fieldName, value)
}

fun Class<*>.getStaticFloatField(fieldName: String): Float {
    return XposedHelpers.getStaticFloatField(this, fieldName)
}

fun Class<*>.setStaticFloatField(fieldName: String, value: Float) {
    XposedHelpers.setStaticFloatField(this, fieldName, value)
}


/**
 * example for usage
 * ```
 * // getIntField()
 * textView.getIntField("testIntField")
 *
 * // setIntField()
 * textView.setIntField("testIntField", testValue)
 *
 * // getStaticIntField()
 * TextView::class.java.getStaticIntField("testStaticIntField")
 *
 * // setStaticIntField()
 * TextView::class.java.setStaticIntField("testStaticIntField", testValue)
 * ```
 */
fun Any.getIntField(fieldName: String): Int {
    return XposedHelpers.getIntField(this, fieldName)
}

fun Any.setIntField(fieldName: String, value: Int) {
    XposedHelpers.setIntField(this, fieldName, value)
}

fun Class<*>.getStaticIntField(fieldName: String): Int {
    return XposedHelpers.getStaticIntField(this, fieldName)
}

fun Class<*>.setStaticIntField(fieldName: String, value: Int) {
    XposedHelpers.setStaticIntField(this, fieldName, value)
}


/**
 * example for usage
 * ```
 * // getLongField()
 * textView.getLongField("testLongField")
 *
 * // setLongField()
 * textView.setLongField("testLongField", testValue)
 *
 * // getStaticLongField()
 * TextView::class.java.getStaticLongField("testStaticLongField")
 *
 * // setStaticLongField()
 * TextView::class.java.setStaticLongField("testStaticLongField", testValue)
 * ```
 */
fun Any.getLongField(fieldName: String): Long {
    return XposedHelpers.getLongField(this, fieldName)
}

fun Any.setLongField(fieldName: String, value: Long) {
    XposedHelpers.setLongField(this, fieldName, value)
}

fun Class<*>.getStaticLongField(fieldName: String): Long {
    return XposedHelpers.getStaticLongField(this, fieldName)
}

fun Class<*>.setStaticLongField(fieldName: String, value: Long) {
    XposedHelpers.setStaticLongField(this, fieldName, value)
}


/**
 * example for usage
 * ```
 * // getObjectField()
 * textView.getObjectField("testObjectField")
 *
 * // setObjectField()
 * textView.setObjectField("testObjectField", testValue)
 *
 * // getStaticObjectField()
 * TextView::class.java.getStaticObjectField("testStaticObjectField")
 *
 * // setStaticObjectField()
 * TextView::class.java.setStaticObjectField("testStaticObjectField", testValue)
 * ```
 */
fun Any.getObjectField(fieldName: String): Any {
    return XposedHelpers.getObjectField(this, fieldName)
}

fun Any.setObjectField(fieldName: String, value: Any) {
    XposedHelpers.setObjectField(this, fieldName, value)
}

fun Class<*>.getStaticObjectField(fieldName: String): Any {
    return XposedHelpers.getStaticObjectField(this, fieldName)
}

fun Class<*>.setStaticObjectField(fieldName: String, value: Any) {
    XposedHelpers.setStaticObjectField(this, fieldName, value)
}


/**
 * example for usage
 * ```
 * // getShortField()
 * textView.getShortField("testShortField")
 *
 * // setShortField()
 * textView.setShortField("testShortField", testValue)
 *
 * // getStaticShortField()
 * TextView::class.java.getStaticShortField("testStaticShortField")
 *
 * // setStaticShortField()
 * TextView::class.java.setStaticShortField("testStaticShortField", testValue)
 * ```
 */
fun Any.getShortField(fieldName: String): Short {
    return XposedHelpers.getShortField(this, fieldName)
}

fun Any.setShortField(fieldName: String, value: Short) {
    XposedHelpers.setShortField(this, fieldName, value)
}

fun Class<*>.getStaticShortField(fieldName: String): Short {
    return XposedHelpers.getStaticShortField(this, fieldName)
}

fun Class<*>.setStaticShortField(fieldName: String, value: Short) {
    XposedHelpers.setStaticShortField(this, fieldName, value)
}