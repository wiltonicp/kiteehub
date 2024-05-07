#FROM kiteehub-jre17
FROM openjdk:8-jre-alpine
MAINTAINER Ranger wilton.icp@gmail.com

WORKDIR /app

ADD *.jar /app/app.jar
#COPY --from=0 /target/*.jar /app/app.jar

EXPOSE 8299

# 设置容器的时区
ENV TZ=Asia/Shanghai
# 设置语言支持中文
ENV LANG C.UTF-8
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir /defaultUploadFolder

#添加中文字体
RUN echo -e 'https://mirrors.aliyun.com/alpine/v3.6/main/\nhttps://mirrors.aliyun.com/alpine/v3.6/community/' > /etc/apk/repositories \
&& apk update && apk upgrade \
&& apk --no-cache add ttf-dejavu fontconfig

COPY yahei.ttf /usr/share/fonts/ttf-dejavu/simsun.ttc

#ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
