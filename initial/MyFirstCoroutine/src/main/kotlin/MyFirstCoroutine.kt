import kotlinx.coroutines.*
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis


fun main() = runBlocking {


    launch {
        logMessage("Pre>>>")
        val time = measureTimeMillis {
            logMessage(logHelloWorld())
        }
        logMessage("Time taken: $time")
    }

    logMessage("<<<Post")
    logMessage("completing main function...")

}


private suspend fun logHelloWorld(): String = withContext(Dispatchers.Default) {
    logMessage("in logHelloWorld")
    val helloMessage = helloMessage()
    val worldMessage = worldMessage()

    logMessage("leaving logHelloWorld")
    helloMessage + worldMessage
}

private suspend fun helloMessage(): String = withContext(Dispatchers.Default) {
    logMessage("in helloMessage")
    delay(1000)
    "Hello "
}

private suspend fun worldMessage(): String = withContext(Dispatchers.Default) {
    logMessage("in worldMessage")
    delay(1000)
    "World!!"
}


fun logMessage(msg: String) {
    println("Running on: [${Thread.currentThread().name}] | $msg")
}


fun CoroutineScope.logContext(id: String) {
    coroutineContext.logDetails(id)
}


fun CoroutineContext.logDetails(id: String) {
    sequenceOf(
        Job,
        ContinuationInterceptor,
        CoroutineExceptionHandler,
        CoroutineName
    )
        .mapNotNull { key -> this[key] }
        .forEach { logMessage("id: $id ${it.key} = ${it}") }
}