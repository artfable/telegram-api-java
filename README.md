# Telegram Bot Api (Version: 0.2.0)
Simple wrapper for using telegram api with java ([Telegram Api](https://core.telegram.org/bots/api)). Bot API version: 2.3.1

Requirements:
* Java 11

*Note! [Kotlin 1.3.72](http://kotlinlang.org/) used as well. However, you can don't care about it, if you don't want to modify.*

For use: (All provided by [Spring Framework](https://spring.io/))
* EnableAsync (Spring)
* Configure Slf4j
* Create RestTemplate bean (spring-web)
* Create TaskExecutor bean

For start create instance of LongPollingTelegramBot or WebhookTelegramBot.