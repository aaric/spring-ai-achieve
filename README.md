# spring-ai-achieve

## 1 相关术语

## 1.1 参数说明

| 序号 |      参数       | 说明                      |
|:--:|:-------------:|-------------------------|
| 1  |    `top_p`    | *控制着生成文本时所使用词汇范围大小*     |
| 2  | `temperature` | *决定了在这个范围内文本生成时是否具有随机性* |
| 3  | `max_tokens`  | *控制着生成文本所使用的最大长度*       |

## 1.2 角色说明

| 序号 |     角色      | 说明                          |
|:--:|:-----------:|-----------------------------|
| 1  |  `system`   | *系统角色，用来向语言模型传达开发者定义好的核心指令* |
| 2  |   `user`    | *用户角色，代表着用户自己输入或者产生出来的信息*   |
| 3  | `assistant` | *助手角色，由语言模型自动生成并回复出来*       |

### 2 数据库配置

### 2.1 Docker Compose

```yaml
services:
  mariadb-v11:
    image: mariadb:11.8.2-noble
    container_name: mariadb_community_edition
    restart: always
    ulimits:
      nofile:
        soft: 65536
        hard: 65536
    volumes:
      - ./data:/var/lib/mysql
      - ./conf.d:/etc/mysql/conf.d
    environment:
      TZ: Asia/Shanghai
      MYSQL_ROOT_PASSWORD: admin123
    ports:
      - 3306:3306
  redis-v7:
    image: redis:7.2.10
    container_name: redis_community_edition
    restart: always
    volumes:
      - ./data:/data
      - ./redis.conf:/etc/redis/redis.conf
    ports:
      - 6379:6379
    command: redis-server /etc/redis/redis.conf
```
### 2.2 Docker Build

```bash
docker run -it --rm -u "$(id -u):$(id -g)" -w /project -v "$(pwd)":/project \
  openjdk:21-m3g8-ubuntu bash -c "gradle clean build"
```
