package com.me.bootSecurityJPAExample.domain;

import java.util.List;


public class APIResponse<T> {
    private final String status;
    private final String message;
    private final APIResponseBody<T> result;


    public static class APIResponseBody<T> {
        private final List<T> contents;
        private final boolean last;
        private final long totalCount;


        public APIResponseBody(List<T> contents, boolean last, long totalCount) {
            this.contents = contents;
            this.last = last;
            this.totalCount = totalCount;
        }

        public List<T> getContents() { return contents; }
        public boolean isLast() { return last; }
        public long getTotalCount() { return totalCount; }
    }


    public APIResponse(String status, String message, APIResponseBody<T> result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }


    /**
     * 성공적인 API 응답 시 반환하는 표준 응답 템플릿. 페이징하지 않은 데이터에 적합하다.
     *
     * @param contents 성공 시 반환할 {@link List<T>} 형식의 응답 데이터
     * @param totalCount 페이징하지 않은 전체 검색 결과의 크기
     * @return 성공 응답용 새 {@link APIResponse APIResponse} 인스턴스
     */
    public static <T> APIResponse<T> success(List<T> contents, long totalCount) {
        // 늘 1 페이지 데이터이므로 last 값은 항상 true 가 된다.
        return success(contents, true, totalCount);
    }

    /**
     * 성공적인 API 응답 시 반환하는 표준 응답 템플릿
     *
     * @param contents 성공 시 반환할 {@link List<T>} 형식의 응답 데이터
     * @param last 이 검색 데이터가 페이징 데이터일 경우, 마지막 페이지인지 여부
     * @param totalCount 페이징하지 않은 전체 검색 결과의 크기
     * @return 성공 응답용 새 {@link APIResponse APIResponse} 인스턴스
     */
    public static <T> APIResponse<T> success(List<T> contents, boolean last, long totalCount) {
        return new APIResponse<>(
                "SUCCESS",
                "성공",
                new APIResponseBody<>(
                        contents,
                        last,
                        totalCount
                )
        );
    }

    /**
     * 실패한 API 응답 시 반환하는 표준 응답 템플릿
     *
     * @param messageOfFailure 사용자에게 간략한 원인을 알려주는 오류 메시지
     * @return 실패 응답용 새 {@link APIResponse APIResponse} 인스턴스
     */
    public static <T> APIResponse<T> failure(String messageOfFailure) {
        return new APIResponse<>(
                "FAIL",
                "실패 - " + messageOfFailure,
                null
        );
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public APIResponseBody<T> getResult() { return result; }

}
