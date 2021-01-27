#!/bin/bash
#打包镜像启动服务
#author：SHIYULONG
#docker_repostory=localhost:5000/zyg/
server_name=eureka-server
server_tag=:1.0.0-SNAPSHOT
#服务路径
workdir=$(dirname  "$(pwd)")
#工程路径
topdir=${workdir%zyg-parent*}
#core包路径
#coredir="zyg-parent/核心包"
echo "workspace dir:" $workdir
echo "core dir:"$topdir$coredir
active=$1
if [ $active"x" == "devx" ]; then
	echo "打包环境：开发环境"
elif [ $active"x" == "fatx" ]; then
	echo "打包环境：测试环境"
elif [ $active"x" == "prodx" ]; then
	echo "打包环境：生产环境"
else
	echo "未知的打包环境"
	exit 1
fi
echo "----git pull----"
git merge >> /dev/null
if (( $? ))
then
	echo "git pull failed"
	exit 1
else
	echo "git pull success"
fi
echo "----hgs-ms-core install----"

cd "$topdir$coredir"
git merge >> /dev/null
mvn clean install >> /dev/null
if (( $? ))
then
	echo "hgs-ms-core：mvn install failed"
	exit 1
else
	echo "hgs-ms-core：mvn install success"
fi
#cd "$topdir$commondir"
#echo "----caption-basics-server install----"
#git pull >> /dev/null
#mvn clean install >> /dev/null
#if (( $? ))
#then
#	echo "caption-basics-server：mvn install failed"
#	exit 1
#else
#	echo "caption-basics-server：mvn install success"
#fi
cd $workdir
echo "mvn package：${workdir##*/}"
mvn clean package -P$active >> /dev/null
if (( $? ))
then
	echo "mvn package failed"
	exit 1
else
       	echo "mvn package success"
fi
#镜像名
image_name="$server_name-$1$server_tag"
if [ $active = "dev" ] || [ $active = "fat" ];
then
	#关闭容器、删除容器
	container_id=`docker ps -q --filter name=$server_name`
	if [ ! -z $container_id ];
	then
		docker stop $container_id
		echo "stop container"
		docker rm $container_id
		echo "rm container"
	fi
	echo "start running docker images：$image_name"
	docker run --name "$server_name" -d --restart=always -v "/data/zyg/logs/$server_name/":/log  --network=host "$docker_repostory$image_name"
	#启动日志
	docker logs -f $(docker ps -q --filter name=$server_name)|while read line
	do
        	is_startup=`echo $line|grep "Completed initialization"|wc -l`
		is_failed=`echo $line|grep "APPLICATION FAILED TO START"|wc -l`
        	if [ $is_startup -eq 1 ] ; then
                	echo "docker images run complete:$image_name"
			exit 1
        	fi
		if [ $is_failed -eq 1 ] ; then
			echo "docker images run failed:$image_name"
			exit 1
		fi
	done
fi
if [ $active = "dev" ] || [ $active = "fat" ];
then
	exit 1
fi
if [ $active == "prod" ];
then
docker push "$docker_repostory$image_name"
	echo -n "Enter your IP address:"
	read ip_address
	echo "pull images"
	ssh root@$ip_address docker pull "$docker_repostory$image_name"
	echo "stop container:$server_name"
	ssh root@$ip_address docker stop $(docker ps -q -a --filter name=$server_name)
	echo "images run"
	ssh root@$ip_address docker run --name $server_name"-`date +%m%d`" --restart=always -d -v /data/zyg/logs/eureka-server/"`date +%m%d`":/log  --network=host "$docker_repostory$image_name"
	echo "docker images run complete:$image_name"
	exit 1
fi