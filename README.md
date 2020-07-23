# Приемник событий от устройств и отправка их в Kafka

Endpoint для приемки

```
POST /logging/device/events
Content-Type: application/x-www-form-urlencoded
X-Auth-Token: xxxx

events=%7B%22events%22....
```

Endpoint для проверки

```shell script
GET /{msg}

```

