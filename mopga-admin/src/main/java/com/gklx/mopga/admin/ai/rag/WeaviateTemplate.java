package com.gklx.mopga.admin.ai.rag;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.weaviate.client.WeaviateClient;
import io.weaviate.client.base.Result;
import io.weaviate.client.v1.batch.api.ObjectsBatcher;
import io.weaviate.client.v1.batch.model.ObjectGetResponse;
import io.weaviate.client.v1.data.model.WeaviateObject;
import io.weaviate.client.v1.graphql.model.GraphQLResponse;
import io.weaviate.client.v1.graphql.query.argument.HybridArgument;
import io.weaviate.client.v1.graphql.query.fields.Field;
import io.weaviate.client.v1.misc.model.BM25Config;
import io.weaviate.client.v1.misc.model.InvertedIndexConfig;
import io.weaviate.client.v1.schema.model.Property;
import io.weaviate.client.v1.schema.model.WeaviateClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class WeaviateTemplate extends VectorTemplate {

    private final WeaviateClient weaviateClient;

    public WeaviateTemplate(WeaviateClient weaviateClient) {
        this.weaviateClient = weaviateClient;
    }

    public boolean exists(String className) {
        Result<Boolean> result = weaviateClient.schema()
                .exists()
                .withClassName(className)
                .run();

        log.info("Exists {} class: {}", className, result.getResult());
        return !result.hasErrors() && result.getResult();
    }

    public void create(Map<String, List<String>> properties, String className) {
        WeaviateClass clazz = WeaviateClass.builder()
                .className(className)
                .description(className)
                .properties(map2Properties(properties))
                .invertedIndexConfig(InvertedIndexConfig.builder()
                        .bm25(BM25Config.builder().build())
                        .build())
                .vectorizer("none")
                .build();

        Result<Boolean> result = weaviateClient.schema().classCreator().withClass(clazz).run();
        if (result.hasErrors() || !result.getResult()) {
            throw new RuntimeException(String.format("Failed to create %s class: %s", className, result.getError()));
        }
        log.info("Created {} class", className);
    }

    public void truncate(String className) {
        Result<Boolean> result = weaviateClient.schema().classDeleter().withClassName(className).run();
        if (result.hasErrors() || !result.getResult()) {
            throw new RuntimeException(String.format("Failed to delete %s class: %s", className, result.getError()));
        }
        log.info("Deleted {} class", className);
    }

    public void batchSave(List<Map<String, Object>> data, String className) {
        try (ObjectsBatcher batcher = weaviateClient.batch().objectsBatcher()) {
            List<WeaviateObject> objects = data.stream()
                    .map(e -> buildWeaviateObjects(e, className))
                    .toList();

            Result<ObjectGetResponse[]> result = batcher.withObjects(ArrayUtil.toArray(objects, WeaviateObject.class)).run();
            if (result.hasErrors()) {
                throw new RuntimeException(String.format("Failed to batch save data to %s class: %s", className, result.getError()));
            }
            log.info("Batch saved {} objects to {} class", data.size(), className);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<WeaviateObject> list(String className, int limit) {
        Result<List<WeaviateObject>> result = weaviateClient.data().objectsGetter()
                .withClassName(className)
                .withLimit(limit)
                .run();
        if (result.hasErrors()) {
            throw new RuntimeException(String.format("Failed to list data from %s class: %s", className, result.getError()));
        }
        return result.getResult();
    }

    public JSONArray hybridSearch(String className, RetrievalVo vo, String question) {
        String keywords = this.participle(question);
        Float[] vector = embeddingModel.embedding(question);
        int limit = vo.getLimit() * 2;

        HybridArgument hybridArgument = HybridArgument.builder()
                .query(keywords)
                .alpha(vo.getAlpha())
                .properties(new String[]{"keywords"})
                .vector(vector)
                .build();

        Field contentField = Field.builder().name("content").build();
        Result<GraphQLResponse> result = weaviateClient.graphQL()
                .get()
                .withClassName(className)
                .withHybrid(hybridArgument)
                .withFields(contentField)
                .withLimit(limit)
                .run();

        if (result.hasErrors()) {
            throw new RuntimeException(String.format("Failed to hybrid search in %s class: %s", className, result.getError()));
        }

        JSONObject data = JSONUtil.parseObj(JSONUtil.toJsonStr(result.getResult().getData()));
        JSONObject get = data.getJSONObject("Get");
        return get.getJSONArray(className);
    }

    public void delete(String className, String id) {
        Result<Boolean> result = weaviateClient.data().deleter().withClassName(className).withID(id).run();
        if (result.hasErrors()) {
            throw new RuntimeException(String.format("Failed to delete %s from %s class: %s", id, className, result.getError()));
        }
    }


    public List<Property> map2Properties(Map<String, List<String>> properties) {
        return properties.entrySet().stream()
                .map(e -> Property.builder()
                        .name(e.getKey())
                        .dataType(e.getValue())
                        .build())
                .toList();
    }


    public WeaviateObject buildWeaviateObjects(Map<String, Object> data, String className) {
        String id = MapUtil.getStr(data, "id");
        Float[] vector = (Float[]) data.get("vector");
        data.remove("id");
        data.remove("vector");
        return WeaviateObject.builder()
                .className(className)
                .id(id)
                .properties(data)
                .vector(vector)
                .build();
    }

}
