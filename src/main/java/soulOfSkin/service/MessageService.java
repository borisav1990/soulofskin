package soulOfSkin.service;

import java.util.List;

import soulOfSkin.models.Message;

public interface MessageService {

	List<Message> getAllMessage();

	boolean deleteMessageById(Long messageId);

	boolean sendMessage(Message message);

}
