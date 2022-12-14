package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.dto.MessageDTO;
import com.bubbles.bubbles_backend.dto.MessageQueryDTO;
import com.bubbles.bubbles_backend.entity.Message;
import com.bubbles.bubbles_backend.repo.MessageRepository;
import com.bubbles.bubbles_backend.utils.ValidUtils;
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

    public Message saveMessage(MessageDTO messageDTO){
        Message message = new Message(messageDTO);
//        log.info(message.toString());
        return this.messageRepository.save(message);
    }

    public List<Message> getMessage(MessageQueryDTO messageQueryDTO){
        int startId = messageQueryDTO.getStartId();
        int cnt = messageQueryDTO.getCnt();
        int chatroomId = messageQueryDTO.getChatroomId();
        if(!ValidUtils.isValid(chatroomId))
            return new ArrayList<>();

        Sort sort = Sort.by(Sort.Direction.DESC, "messageId");
        Specification<Message> specification = new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(ValidUtils.isValid(startId))
                    predicates.add(criteriaBuilder.lessThan(root.get("messageId"), startId));
                predicates.add(criteriaBuilder.equal(root.get("chatroomId"), chatroomId));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Pageable pageable = PageRequest.of(0, cnt, sort);
        return messageRepository.findAll(specification, pageable);
    }
}
