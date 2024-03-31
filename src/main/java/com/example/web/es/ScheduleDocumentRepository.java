package com.example.web.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ScheduleDocumentRepository extends ElasticsearchRepository<ScheduleDocument, Long> {
    Page<ScheduleDocument> findByContentAndMemberId(Pageable pageable, String content, Long memberId);
}
