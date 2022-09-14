FROM iilee5-docker.pkg.coding.net/iilee/open/openjdk:8-jre
MAINTAINER "me@iilee.cn"
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
ENV JAVA_OPTS="-Xms256m -Xmx256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
ENV JVM_SERVER_FIXED="-Dlog4j2.formatMsgNoLookups=true -Djava.security.egd=file:/dev/./urandom"
#ENV JAVA_CONFIG="--spring.profiles.active=pro"
ENV TZ=Asia/Shanghai
EXPOSE 8088
ENTRYPOINT exec java $JAVA_OPTS $JVM_SERVER_FIXED -jar /app.jar
# $JAVA_CONFIG
