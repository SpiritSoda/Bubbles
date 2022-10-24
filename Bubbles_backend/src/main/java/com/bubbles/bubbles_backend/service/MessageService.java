package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.dto.MessageDTO;
import com.bubbles.bubbles_backend.entity.Message;
import com.bubbles.bubbles_backend.repo.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public void saveMessage(MessageDTO messageDTO){
        Message message = new Message(messageDTO);
//        log.info(message.toString());
        this.messageRepository.save(message);
    }

    public List<Message> getFromIdWithFixedCount(int startId, int cnt){
        Sort sort = Sort.by(Sort.Direction.ASC, "timestamp");
        Specification<Message> specification = new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.lessThan(root.get("messageId"), startId));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Pageable pageable = PageRequest.of(0, cnt, sort);
        return messageRepository.findAll(specification, pageable);
    }
}
