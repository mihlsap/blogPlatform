package com.example.blogplatform.utils;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseUtils {

    public static <T>ResponseEntity<GenericResponse<T>> handleOptionalResponse(
            Optional<T> optional,
            ResponseMessage success,
            ResponseMessage failure
    ) {
        return optional
                .map(data -> GenericResponse.success(success.message, success.status, data))
                .orElseGet(() -> GenericResponse.error(failure.message, failure.status));
    }

//    public static <T>ResponseEntity<GenericResponse<T>> handleListResponse(
//            List<T> list,
//            ResponseMessage success,
//            ResponseMessage failure
//    ) {
//        return list.stream()
//                .map(data -> GenericResponse.success(success.message, success.status, data))
//                .orElseGet(() -> GenericResponse.error(failure.message, failure.status));
//    }
}
