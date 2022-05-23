# one-to-many liveness

> 一对多探活器
------

## 运行环境

[![](https://img.shields.io/badge/JDK-1.8%2B-brightgreen.svg)](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html) [![](https://img.shields.io/badge/SpringBoot-2.7.0-brightgreen.svg)](https://maven.apache.org/) [![](https://img.shields.io/badge/IDE-Idea-brightgreen.svg)](https://www.jetbrains.com/zh-cn/idea/) ![](https://img.shields.io/badge/Platform-windows|*nix-brightgreen.svg)


## 简介

适用于微服务等含有多个模块的应用做统一探活，主要作用是收敛探活的多个故障告警到产品本身。原理很简单：

把其他需要探活的服务接口（目前只支持 HTTP 和 Socket）配置到这个项目，由它代理对各个服务发起探活检测，最后把各个服务的存活情况聚合返回。


## 配置方法

把需要做探活的服务接口配置到 [`application.yml`](./src/main/resources/application.yml) ，配置方法参考其中的 demo :

```yml
# 需要探活的服务列表（格式一）
detected-list:
  remoteServices:
    - name: "exp"
      protocol: "http"
      address: "https://exp-blog.com"
    - name: "qq"
      protocol: "socket"
      address: "127.0.0.1:65535"

# 需要探活的服务列表（格式二）
detected-list.remoteServices[2].name: "baidu"
detected-list.remoteServices[2].protocol: "http"
detected-list.remoteServices[2].address: "https://www.baidu.com"
detected-list.remoteServices[3].name: "springboot"
detected-list.remoteServices[3].protocol: "socket"
detected-list.remoteServices[3].address: "127.0.0.1:8080"
```


## 使用方法

运行此项目后，访问 [http://localhost:8080/health/liveness](http://localhost:8080/health/liveness) 即可查看所有被测服务的结果：

```json
{
  "ok": false,
  "total": 4,
  "errorNum": 1,
  "errorData": [
    {
      "name": "qq",
      "protocol": "socket",
      "address": "127.0.0.1:65535",
      "statusCode": -1,
      "statusDesc": "Detecte Error"
    }
  ],
  "sussessData": [
    {
      "name": "exp",
      "protocol": "http",
      "address": "https://exp-blog.com",
      "statusCode": 200,
      "statusDesc": ""
    },
    {
      "name": "baidu",
      "protocol": "http",
      "address": "https://www.baidu.com",
      "statusCode": 200,
      "statusDesc": ""
    },
    {
      "name": "springboot",
      "protocol": "socket",
      "address": "127.0.0.1:8080",
      "statusCode": 0,
      "statusDesc": ""
    }
  ]
}
```

需注意，[http://localhost:8080/health/liveness](http://localhost:8080/health/liveness) 接口正常情况下只会有两种状态码：

- `HTTP 200`： 所有的被测服务均正常
- `HTTP 206`： 部分或所有的被测服务异常

> 其他状态码均表示 `/health/liveness` 接口本身的服务异常，但是自身探活建议用 `/health/self` 接口。


## 容器化

本项目以支持容器化，在项目根目录下运行以下脚本即可：

1. [`bin/clean.sh`](./bin/clean.sh) 或 [`bin/clean.ps1`](./bin/clean.ps1)： 清理镜像
2. [`bin/build.sh`](./bin/build.sh) 或 [`bin/clean.ps1`](./bin/build.ps1)： 构建镜像
3. [`bin/deploy.sh`](./bin/deploy.sh) 或 [`bin/deploy.ps1`](./bin/deploy.ps1)： 发布镜像
4. [`bin/run.sh`](./bin/run.sh) 或 [`bin/run.ps1`](./bin/run.ps1)： 运行容器
5. [`bin/stop.sh`](./bin/stop.sh) 或 [`bin/stop.ps1`](./bin/stop.ps1)： 停止容器

若需要在容器中修改 [`application.yml`](./res/apps/one-to-m-liveness/config/application.yml)， 只需把 `./res/apps/one-to-m-liveness/config/application.yml` 挂载出来修改即可。
