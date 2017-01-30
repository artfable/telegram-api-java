# Telegram Bot Api (Version: 0.1.1)
Simple wrapper for using telegram api with java ([Telegram Api](https://core.telegram.org/bots/api)). Bot API version: 2.3.1

Requirements:
For compilation: 
* Java 8
* Gradle 3.*

*Note! [Kotlin 1.0.6](http://kotlinlang.org/) used as well. However, you can don't care about it, if you don't want to modify.*

For use: (All provided by [Spring Framework](https://spring.io/))
* EnableAsync (Spring)
* Configure Slf4j
* Create RestTemplate bean (spring-web)
* Create TaskExecutor bean