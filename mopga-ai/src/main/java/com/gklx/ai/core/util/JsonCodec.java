package com.gklx.ai.core.util;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;

/**
 * Interface for JSON serialization and deserialization operations.
 *
 * <p>This interface provides a unified abstraction for JSON processing,
 * allowing users to replace the default Jackson-based implementation
 * with custom implementations if needed.
 *
 * <p>Usage:
 * <pre>{@code
 * // Get the codec instance
 * JsonCodec codec = JsonUtils.getJsonCodec();
 *
 * // Serialize object to JSON
 * String json = codec.toJson(myObject);
 *
 * // Deserialize JSON to object
 * MyClass obj = codec.fromJson(json, MyClass.class);
 *
 * // Deserialize with generic type
 * List<MyClass> list = codec.fromJson(json, new TypeReference<List<MyClass>>() {});
 * }</pre>
 *
 * @see JacksonJsonCodec
 * @see JsonUtils
 */
public interface JsonCodec {

    /**
     * Serialize an object to JSON string.
     *
     * @param obj the object to serialize
     * @return JSON string representation
     * @throws JsonException if serialization fails
     */
    String toJson(Object obj);

    /**
     * Serialize an object to pretty-printed JSON string.
     *
     * @param obj the object to serialize
     * @return pretty-printed JSON string representation
     * @throws JsonException if serialization fails
     */
    String toPrettyJson(Object obj);

    /**
     * Deserialize a JSON string to an object of the specified type.
     *
     * @param json the JSON string to deserialize
     * @param type the target class type
     * @param <T> the type parameter
     * @return deserialized object
     * @throws JsonException if deserialization fails
     */
    <T> T fromJson(String json, Class<T> type);

    /**
     * Deserialize a JSON string to an object with generic type.
     *
     * <p>Use this method when deserializing to generic types like {@code List<MyClass>}.
     *
     * @param json the JSON string to deserialize
     * @param typeRef the type reference for generic types
     * @param <T> the type parameter
     * @return deserialized object
     * @throws JsonException if deserialization fails
     */
    <T> T fromJson(String json, TypeReference<T> typeRef);

    /**
     * Convert an object to another type.
     *
     * <p>This is useful for converting Map to typed objects or vice versa.
     *
     * @param from the source object
     * @param toType the target class type
     * @param <T> the type parameter
     * @return converted object
     * @throws JsonException if conversion fails
     */
    <T> T convertValue(Object from, Class<T> toType);

    /**
     * Convert an object to another generic type.
     *
     * @param from the source object
     * @param toTypeRef the type reference for generic types
     * @param <T> the type parameter
     * @return converted object
     * @throws JsonException if conversion fails
     */
    <T> T convertValue(Object from, TypeReference<T> toTypeRef);

    /**
     * Convert an object to another type using Java reflection Type.
     *
     * <p>This method preserves generic type information from parameterized types
     * such as {@code List<MyClass>} or {@code Map<String, MyClass>}.
     *
     * <p>Example usage:
     * <pre>{@code
     * // For a method parameter declared as List<OrderItem>
     * Type paramType = parameter.getParameterizedType();
     * List<OrderItem> items = (List<OrderItem>) codec.convertValue(value, paramType);
     * }</pre>
     *
     * @param from the source object
     * @param toType the target type (can be Class, ParameterizedType, etc.)
     * @return converted object
     * @throws JsonException if conversion fails
     */
    Object convertValue(Object from, Type toType);
}
