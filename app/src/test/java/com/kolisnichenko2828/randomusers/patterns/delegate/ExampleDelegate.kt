package com.kolisnichenko2828.randomusers.patterns.delegate

import org.junit.Test

private interface ExceptionHandler {
    fun catchException(e: Exception): String?
}
private interface SomeLogger {
    fun log(event: String): String
}

private class ExceptionHandlerImpl : ExceptionHandler {
    override fun catchException(e: Exception): String? {
        return e.localizedMessage
    }
}

private class SomeLoggerImpl : SomeLogger{
    override fun log(event: String): String {
        return "Event logged: event"
    }
}

private class ExampleDelegate(
    logger: SomeLogger = SomeLoggerImpl(),
    exceptionHandler: ExceptionHandler = ExceptionHandlerImpl()
): SomeLogger by logger, ExceptionHandler by exceptionHandler {
    fun doSomeWork(): String {
        return "Some work"
    }
}

class DelegateTest {
    @Test
    fun main() {
        val delegate = ExampleDelegate()
        val log = delegate.log("Some event")
        println(log)
        val error = delegate.catchException(Exception("Some exception"))
        println(error)
        val someResult = delegate.doSomeWork()
        println(someResult)
    }
}