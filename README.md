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

docker run -i --rm -u 1000:1000 -w /project -v ./test-project-main:/project openjdk:21-m3g8-ubuntu gradle clean build
```

## 3 Docker TLS

### 3.1 生成自签名证书

```bash
# 创建目录存放证书
mkdir -p /etc/docker/certs && cd /etc/docker/certs

# 生成 CA 私钥和公钥
openssl genrsa -aes256 -out ca-key.pem 4096
openssl req -new -x509 -days 365 -key ca-key.pem -sha256 -out ca.pem

# 生成服务器密钥+签名请求+证书
openssl genrsa -out server-key.pem 4096
openssl req -subj "/CN=192.168.1.100" -sha256 -new -key server-key.pem -out server.csr
echo subjectAltName = IP:192.168.1.100,IP:127.0.0.1 >> extfile.cnf
echo extendedKeyUsage = serverAuth >> extfile.cnf
openssl x509 -req -days 365 -sha256 -in server.csr -CA ca.pem -CAkey ca-key.pem \
  -CAcreateserial -out server-cert.pem -extfile extfile.cnf

# 生成客户端密钥+签名请求+证书
openssl genrsa -out key.pem 4096
openssl req -subj '/CN=client' -new -key key.pem -out client.csr
echo extendedKeyUsage = clientAuth > extfile-client.cnf
openssl x509 -req -days 365 -sha256 -in client.csr -CA ca.pem -CAkey ca-key.pem \
  -CAcreateserial -out cert.pem -extfile extfile-client.cnf

# 清理临时文件
rm -v client.csr server.csr extfile.cnf extfile-client.cnf
```
### 3.1 配置Docker以支持TLS

```bash
#vim /etc/docker/daemon.json
#"""
#{
#  "tls": true,
#  "tlscert": "/etc/docker/certs/server-cert.pem",
#  "tlskey": "/etc/docker/certs/server-key.pem",
#  "tlscacert": "/etc/docker/certs/ca.pem",
#  "hosts": ["tcp://0.0.0.0:2376", "unix:///var/run/docker.sock"]
#}
#"""
vim /usr/lib/systemd/system/docker.service
"""
#ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2376 -H unix://var/run/docker.sock --tlsverify --tlscacert=/etc/docker/certs/ca.pem --tlscert=/etc/docker/certs/server-cert.pem --tlskey=/etc/docker/certs/server-key.pem
"""
systemctl daemon-reload
systemctl restart docker
systemctl status docker
```
### 3.3 验证TLS连接

```bash
# curl
curl --cacert ca.pem --cert cert.pem --key key.pem https://192.168.1.250:2376/version

# docker
docker --tlsverify --tlscacert=ca.pem --tlscert=cert.pem --tlskey=key.pem -H tcp://device01:2376 version
```

## 4 AI笔记

### 4.1 结构化输出有3种实现方式

- 利用大模型的 JSON Schema
- 利用 Prompt+JSONMode
- 利用 Prompt
