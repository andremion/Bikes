<img alt="Icon" src="app/src/main/res/mipmap-xxhdpi/ic_launcher.png?raw=true" align="left" hspace="1" vspace="1">

# Bikes
A sample of Unidirectional Data Flow usage.

</br>

[![License Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=true)](http://www.apache.org/licenses/LICENSE-2.0)
![minSdkVersion 16](https://img.shields.io/badge/minSdkVersion-16-red.svg?style=true)
![compileSdkVersion 29](https://img.shields.io/badge/compileSdkVersion-29-yellow.svg?style=true)

<img alt='Sample' src="https://raw.githubusercontent.com/andremion/Bikes/master/sample.png"></br>

## The sample
Shows a map with markers in cities that have some bike sharing service.
When you click in any of those cities, the app will show all the bike sharing stations with current status of bike availability.
The app will initially try to position in the current user city obtained from location services. 

## The architecture

The abstract class [UdfViewModel] is a [ViewModel] subclass that delegates its [UdfDispatcher] implementation to [UdfDispatcherImpl] in order to handle all the Unidirectional Data Flow.

Each [UdfViewModel] should have one instance of [UdfDispatcherImpl] using specific [UdfProcessor] and [UdfReducer] implementations that basically do:

- Processes each `Action` in [UdfProcessor] emitting a `Result` or `ViewEffect`. `(Action => Result / ViewEffect)`
  - It receives an `Action`, process some operation (fetching/saving from/to network or database) and returns an [LiveData] of `Result` that will be used by [UdfReducer].
  - Additionally it may trigger a `ViewEffect` that means that it should be emitted but not kept as state such as: Showing a [Snackbar], Navigating to another screen, etc.
 
- Reduce the current cached `ViewState` taking `Result` in consideration by [UdfReducer]. `(Current ViewState + Result => New ViewState)`
  - It receives a `Result` from [UdfProcessor] and takes the current `ViewState` to produce another `ViewState` that will be cached.
  
## API
Bike data are fetched from [CityBikes] API, a project that provides bike sharing data for apps, research and projects to use.

## References

- [Unidirectional User Interface architectures](https://staltz.com/unidirectional-user-interface-architectures.html)
- [Unidirectional data flow on Android using Kotlin](https://proandroiddev.com/unidirectional-data-flow-on-android-the-blog-post-part-1-cadcf88c72f5)
- [MVI on Android with LiveData Coroutines](https://proandroiddev.com/mvi-on-android-with-livedata-coroutines-d2172bc7f775)
- [Easy Coroutines in Android: viewModelScope](https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471)
- [Use Kotlin coroutines with Architecture components](https://developer.android.com/topic/libraries/architecture/coroutines)
- [Async code on Kotlin: coroutines VS RxJava](https://www.codemotion.com/magazine/async-code-on-kotlin-coroutines-vs-rxjava-3532)
- [Select Current Place and Show Details on a Map](https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial)
- https://github.com/oldergod/android-architecture/blob/todo-mvi-rxjava-kotlin/app/src/main/java/com/example/android/architecture/blueprints/todoapp/tasks/TasksViewModel.kt
- https://github.com/TheSNAKY/Lives/blob/master/lives/src/main/java/com/snakydesign/livedataextensions/Transforming.kt

## License

    Copyright 2019 André Mion

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[UdfViewModel]: app/src/main/java/com/andremion/bikes/udf/UnidirectionalDataFlow.kt#L5
[UdfDispatcher]: app/src/main/java/com/andremion/bikes/udf/UnidirectionalDataFlow.kt#43
[UdfProcessor]: app/src/main/java/com/andremion/bikes/udf/UnidirectionalDataFlow.kt#L9
[UdfReducer]: app/src/main/java/com/andremion/bikes/udf/UnidirectionalDataFlow.kt#L19
[UdfDispatcherImpl]: app/src/main/java/com/andremion/bikes/udf/UnidirectionalDataFlow.kt#21
[ViewModel]: https://developer.android.com/topic/libraries/architecture/viewmodel
[LiveData]: https://developer.android.com/topic/libraries/architecture/livedata
[Snackbar]: https://developer.android.com/reference/android/support/design/widget/Snackbar
[CityBikes]: https://citybik.es
