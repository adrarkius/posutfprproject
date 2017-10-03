package io.gkuhn.messagebroker.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.gkuhn.messagebroker.dao.MessageEventRepository;
import io.gkuhn.messagebroker.model.ConfirmMessageEvent;
import io.gkuhn.messagebroker.model.MessageEvent;
import io.gkuhn.messagebroker.model.Response;

@Controller
@RequestMapping(path="/message")
public class MessageEventController {

	@Autowired
	private MessageEventRepository messageRepository;
	
	@RequestMapping(path="/user/{userId}", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody List<MessageEvent> getByUser(@PathVariable(value="userId", required = true)int id) {
		List<MessageEvent> result = new ArrayList<>();
		result = messageRepository.findByToAndReceived(id, false);
		return result;
	}
	
	@RequestMapping(value="/", consumes="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Response> sendMessage(@RequestBody List<MessageEvent> messages) {
		messages.forEach(t-> t.setMessageid(0));
		messages.forEach(t-> t.setReceived(false));
		messages.forEach(t-> t.setSenttime(new Date()));
		messageRepository.save(messages);
		return (new ResponseEntity<Response>(new Response("message sent"), HttpStatus.CREATED));
	}
	
	@RequestMapping(value="/confirm", consumes="application/json", method=RequestMethod.POST)
	public @ResponseBody List<Integer> confirmMessage(@RequestBody List<ConfirmMessageEvent> messageConfirmList) {
		List<Integer> resultList = new ArrayList<>();
		if(messageConfirmList != null) {
			for(ConfirmMessageEvent messageConfirmElement : messageConfirmList) {
				MessageEvent message = messageRepository.findOne(messageConfirmElement.getId());
				if(message != null) {
					message.setReceived(true);
					messageRepository.save(message);
					resultList.add(message.getMessageid());
				}
			}
		}
		
		return resultList;
	}
}
