# ===== 直接从 mopga-admin/target 获取 jar 包 =====
# 构建前请先在本机执行: mvn -pl mopga-admin -am package -Ptest -DskipTests
# 打包后在项目根目录执行: docker build -t mopga-admin .
# eclipse-temurin 17 JRE（Ubuntu 系，自带 fontconfig，验证码 AWT 渲染开箱即用）
FROM eclipse-temurin:17-jre
WORKDIR /app

ENV TZ=Asia/Shanghai
# 安装时区 + 字体（验证码 getCaptcha 用 AWT 渲染图片，需 fontconfig 与字体）
RUN apt-get update \
    && apt-get install -y --no-install-recommends tzdata fontconfig fonts-dejavu-core \
    && rm -rf /var/lib/apt/lists/* \
    && cp /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone

# 从已构建的 target 目录拷贝固定名称的 jar（test profile）
COPY mopga-admin/target/mopga-admin-test-3.0.0.jar /app/app.jar

EXPOSE 1024
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
