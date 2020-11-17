# Networking-Demo
Networking Demo App

<p align="left">  
it is a demo application based on modern Android application stacks and MVVM architecture.
</p>
</br>

<p align="left">
<img src="/preview/preview_icon.png" width="30%"/>
</p>

## Download
Go to the [Releases](https://github.com/Mukhash/dodo/releases) to download the lastest APK.

<img src="/preview/dodo_preview.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 26
- [Kotlin](https://kotlinlang.org/) based + [RxJava](https://github.com/ReactiveX/RxJava) for asynchronous.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
- Networking
  - [Retrofit2](https://square.github.io/retrofit/) - a type-safe HTTP client for Android and Java
- DI
  - [Dagger2](https://github.com/google/dagger) - a fast dependency injector for Java and Android
- Unit testing
  - [Mockito](https://github.com/mockito/mockito) - a popular mocking framework for Java
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components recyclerView.

- What's next
  - Saving states of UI
