#!/bin/bash
masterip=$(ifconfig eth0|grep inet |grep -v inet6|awk '{print $2}')
windowsip=$1
docker stop mynginx
docker rm mynginx
docker run -di --network=host --name=mynginx \
	-v /home/leyou/static:/home/leyou/static \
	-v /home/fdfs/storage/data:/home/fdfs/storage/data \
	-v /home/nginx/nginx.conf:/etc/nginx/nginx.conf \
	-v /home/nginx/conf.d:/etc/nginx/conf.d \
       	-v /home/nginx/logs:/var/log/nginx \
	--add-host masterip:$masterip \
       	--add-host windowsip:$windowsip nginx
