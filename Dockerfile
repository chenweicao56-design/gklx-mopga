# ===== 直接从 mopga-admin/target 获取 jar 包 =====
# 构建前请先在本机执行: mvn -pl mopga-admin -am package -Ptest -DskipTests
# 打包后在项目根目录执行: docker build -t mopga-admin .
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

ENV TZ=Asia/Shanghai
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone

# 从已构建的 target 目录拷贝固定名称的 jar（test profile）
COPY mopga-admin/target/mopga-admin-test-3.0.0.jar /app/app.jar

EXPOSE 11024
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
