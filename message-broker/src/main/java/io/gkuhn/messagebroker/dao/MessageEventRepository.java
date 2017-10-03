package io.gkuhn.messagebroker.dao;


import io.gkuhn.messagebroker.model.MessageEvent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageEventRepository extends JpaRepository<MessageEvent, Integer>{
	
	List<MessageEvent> findByToAndReceived(int id, boolean received);

}
