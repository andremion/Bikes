package androidx.lifecycle

// https://github.com/TheSNAKY/Lives/blob/master/lives/src/main/java/com/snakydesign/livedataextensions/Transforming.kt
fun <T, R> LiveData<T>.scan(initialValue: R, accumulator: (R, T) -> R): LiveData<R> = MediatorLiveData<R>().apply {
    value = initialValue
    addSource(this@scan) { emittedValue ->
        value?.let { accumulatedValue ->
            value = accumulator(accumulatedValue, emittedValue)
        }
    }
}
