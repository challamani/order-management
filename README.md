# order-management
Agent app


```shell
mkdir /Users/[your_username]/Develop
mkdir /Users/[your_username]/Develop/mysql_data
mkdir /Users/[your_username]/Develop/mysql_data/8.0
```

```shell
docker network create dev-network
```

```shell
docker run --restart always --name mysql8.0 \
    --net dev-network -v /Users/[your_username]/Develop/mysql_data/8.0:/var/lib/mysql \
    -p 3306:3306 \
    -d -e MYSQL_ROOT_PASSWORD=Winthegame mysql:8.0
    
```

https://medium.com/@crmcmullen/how-to-run-mysql-in-a-docker-container-on-macos-with-persistent-local-data-58b89aec496a
