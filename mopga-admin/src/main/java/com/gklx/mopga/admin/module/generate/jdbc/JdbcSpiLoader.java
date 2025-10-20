package com.gklx.mopga.admin.module.generate.jdbc;

import com.gklx.mopga.admin.module.generate.jdbc.pojo.SqlDefines;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 预加载jdbc驱动包 避免spi并发加载造成死锁
 *
 * @author ccw
 * @date 2022/12/21 15:39
 */
@Service
@Order(value = 0)
@Slf4j
public class JdbcSpiLoader implements CommandLineRunner {

    public static final Map<String, SqlDefines> SqlDefines = new ConcurrentHashMap<>();


    @Override
    public void run(String... args) throws Exception {
        loadJdbcDriver();
        loadSql();
    }

    private void loadJdbcDriver() {
        log.info("start load jdbc drivers");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            Class.forName("org.postgresql.Driver");
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            Class.forName("dm.jdbc.driver.DmDriver");
        } catch (Exception e) {
            log.error("load jdbc error: {}", e.getMessage(), e);
        }
        log.info("end load jdbc drivers");
    }

    private void loadSql() throws IOException {
        boolean loadFromFile = true;
        final List<InputStream> inputStreams = new LinkedList<>();
        // 读取app定义配置加载到内存中 define/app/*.yml
        Yaml yaml = new Yaml();
        String classpath = this.getClass().getClassLoader().getResource("").getPath();
        String defineAppPath = classpath + File.separator + "define" + File.separator + "define/sql";
        File directory = new File(defineAppPath);

        if (!directory.exists() || directory.listFiles() == null) {
            classpath = Objects.requireNonNull(this.getClass().getResource(File.separator)).getPath();
            defineAppPath = classpath
                    + File.separator
                    + "define"
                    + File.separator
                    + "define/sql";
            directory = new File(defineAppPath);
            if (!directory.exists() || directory.listFiles() == null) {
                // load define app yml in jar
                log.info("load define sql yml in internal jar");
                loadFromFile = false;
                try {
                    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                    Resource[] resources = resolver.getResources("classpath:define/sql/*.yml");
                    for (Resource resource : resources) {
                        inputStreams.add(resource.getInputStream());
                    }
                } catch (Exception e) {
                    log.error("define sql yml not exist");
                    throw e;
                }
            }
        }

        if (loadFromFile) {
            log.info("load define path {}", defineAppPath);
            for (File appFile : Objects.requireNonNull(directory.listFiles())) {
                if (appFile.exists()) {
                    try (FileInputStream fileInputStream = new FileInputStream(appFile)) {
                        SqlDefines sqlDefines = yaml.loadAs(fileInputStream, SqlDefines.class);
                        SqlDefines.put(sqlDefines.getDatabaseType(), sqlDefines);
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                        throw new IOException(e);
                    }
                }
            }
        } else {
            if (inputStreams.isEmpty()) {
                throw new IllegalArgumentException("define app directory not exist");
            } else {
                inputStreams.forEach(stream -> {
                    try {
                        SqlDefines sqlDefines = yaml.loadAs(stream, SqlDefines.class);
                        SqlDefines.put(sqlDefines.getDatabaseType(), sqlDefines);
                        stream.close();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                });
            }
        }
    }
}
