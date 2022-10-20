package kr.co.apexsoft.fw.lib._common

import kr.co.apexsoft.fw.lib.utils.ValidUtil
import org.springframework.lang.Nullable
import org.springframework.util.Assert
import org.springframework.util.CollectionUtils
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import java.lang.reflect.InvocationTargetException

object ApexAssert : Assert() {
    /**
     * @param expression
     * @param message
     * @param exceptionClass
     */
    fun state(expression: Boolean, message: String, exceptionClass: Class<out RuntimeException?>) {
        if (!expression) {
            throwException(message, exceptionClass)
        }
    }

    fun isTrue(expression: Boolean, message: String, exceptionClass: Class<out RuntimeException?>) {
        if (!expression) {
            throwException(message, exceptionClass)
        }
    }

    /**
     * Assert that an object is `null`. 객체가 null이 아닌 경우 사용자가 정의한 예외를 던진다.
     */
    fun isNull(
        @Nullable `object`: Any?,
        message: String,
        exceptionClass: Class<out RuntimeException?>
    ) {
        if (`object` != null) {
            throwException(message, exceptionClass)
        }
    }

    /**
     * null인 경우 사용자 정의 예외 발생
     *
     * @param object
     * @param message
     * @param exceptionClass
     */
    fun notNull(
        @Nullable `object`: Any?,
        message: String,
        exceptionClass: Class<out RuntimeException?>
    ) {
        if (`object` == null) {
            throwException(message, exceptionClass)
        }
    }

    /**
     * 전달받은 값이 null이 거나 빈값인 경우 사용자 정의 예외 발생
     */
    fun hasLength(
        @Nullable text: String?,
        message: String,
        exceptionClass: Class<out RuntimeException?>
    ) {
        if (!StringUtils.hasLength(text)) {
            throwException(message, exceptionClass)
        }
    }

    /**
     * 전달받은 값이 null이 거나 빈값인 경우 사용자 정의 예외 발생
     */
    fun hasText(
        @Nullable text: String?,
        message: String,
        exceptionClass: Class<out RuntimeException?>
    ) {
        if (!StringUtils.hasText(text)) {
            throwException(message, exceptionClass)
        }
    }

    /**
     * 전달받은 값이 null이 거나 빈값인 경우 사용자 정의 예외 발생
     */
    fun notEmpty(
        @Nullable array: Array<Any?>?,
        message: String,
        exceptionClass: Class<out RuntimeException?>
    ) {
        if (ObjectUtils.isEmpty(array)) {
            throwException(message, exceptionClass)
        }
    }

    /**
     * 전달받은 값이 null이 거나 빈값인 경우 사용자 정의 예외 발생
     */
    fun notEmpty(
        @Nullable collection: Collection<*>?,
        message: String,
        exceptionClass: Class<out RuntimeException?>
    ) {
        if (CollectionUtils.isEmpty(collection)) {
            throwException(message, exceptionClass)
        }
    }

    /**
     * 전달받은 값이 null이 거나 빈값인 경우 사용자 정의 예외 발생
     */
    fun notEmpty(
        @Nullable map: Map<*, *>?,
        message: String,
        exceptionClass: Class<out RuntimeException?>
    ) {
        if (CollectionUtils.isEmpty(map)) {
            throwException(message, exceptionClass)
        }
    }

    /**
     * 전달받은 값이 mail 형식이 아닌경우
     */
    fun notMailPattern(
        mail: String,
        message: String,
        exceptionClass: Class<out RuntimeException?>
    ) {
        if (!ValidUtil.isMail(mail)) {
            throwException(message, exceptionClass)
        }
    }

    private fun throwException(message: String, exceptionClass: Class<out RuntimeException?>) {
        try {
            throw exceptionClass.getDeclaredConstructor(String::class.java).newInstance(message)!!
        } catch (e: InstantiationException) {
            throw AssertException("ASSERT_EXCEPTION")
        } catch (e: IllegalAccessException) {
            throw AssertException("ASSERT_EXCEPTION")
        } catch (e: InvocationTargetException) {
            throw AssertException("ASSERT_EXCEPTION")
        } catch (e: NoSuchMethodException) {
            throw AssertException("ASSERT_EXCEPTION")
        }
    }
}
