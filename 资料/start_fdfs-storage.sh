#!/bin/bash
ip=$(ifconfig eth0|grep inet |grep -v inet6|awk '{print $2}')
docker stop fdfs-storage
docker rm fdfs-storage
docker run -dti --network=host --name fdfs-storage \
	-e TRACKER_SERVER=$ip:22122 \
       	-v /home/fdfs/storage:/var/fdfs delron/fastdfs storage

