# Flux Architecture on Android using Rx 2

I've modified the [Luis G. Valle](http://lgvalle.xyz/) Flux Android example in order to use Rx 2 instead on Otto. In this case, the `store` keep the state of the TODO list and the view (`TodoActivity`) is subscribed to the state changes. The `store` is subscribed to the actions of the view via the `dispatcher`.

I've added too [Dagger 2](https://github.com/Babelia13/android-flux-todo-app/commit/3f204af2a31e6189c3b89b0043efab961eb531dc) for dependency injection and [espresso tests](https://github.com/Babelia13/android-flux-todo-app/blob/master/app/src/androidTest/java/lgvalle/com/fluxtodo/ApplicationTest.java) for all the app features.

## Flux Architecture Graph

![flux-graph-complete]

## Sample source code

**[https://github.com/lgvalle/android-flux-todo-app][android-app]**


## Further Reading:
* [Facebook Flux Overview][flux-arch]
* [Testing Flux Applications](https://facebook.github.io/flux/docs/testing-flux-applications.html#content)
* [Flux architecture Step by Step](http://blogs.atlassian.com/2014/08/flux-architecture-step-by-step/)
* [Async Requests and Flux](http://www.code-experience.com/async-requests-with-react-js-and-flux-revisited/)
* [Flux and Android](http://armueller.github.io/android/2015/03/29/flux-and-android.html)
* [Rx Java](http://www.vogella.com/tutorials/RxJava/article.html)
* [Dagger 2](https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2)
* [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/index.html)
* [Butter Knife](http://jakewharton.github.io/butterknife/)



[flux-arch]: https://facebook.github.io/flux/docs/overview.html

[flux-graph-complete]: https://raw.githubusercontent.com/lgvalle/lgvalle.github.io/master/public/images/flux-graph-complete.png

[android-app]: https://github.com/lgvalle/android-flux-todo-app

