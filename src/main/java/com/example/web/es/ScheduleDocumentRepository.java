package com.example.web.es;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ScheduleDocumentRepository extends ElasticsearchRepository<ScheduleDocument, Long> {
    List<ScheduleDocument> findByContentAndMemberId(String content, Long memberId);
    Slice<ScheduleDocument> findByContentAndMemberId(Pageable pageable, String content, Long memberId);
}
