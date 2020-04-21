package soulOfSkin.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soulOfSkin.models.Message;
import soulOfSkin.repository.MessageRepository;

@Service
@Transactional
public class MessageServImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public List<Message> getAllMessage() {
		List<Message> listOfMessage = new ArrayList<>();
		List<Message> allMessage = messageRepository.getAllOrdeById();
		for (Message message : allMessage) {
			if (message.getSent() != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				Date date = message.getSent();
				String viewDate = dateFormat.format(date);
				message.setShowDateTime(viewDate);

			} else {
				message.setShowDateTime("--.--.---- --.--");

			}

			listOfMessage.add(message);
		}

		return listOfMessage;
	}

	@Override
	public boolean deleteMessageById(Long messageId) {
		messageRepository.deleteById(messageId);
		return true;
	}

	@Override
	public boolean sendMessage(Message message) {
		if ((!message.getName().equals("")) && (!message.getEmail().equals(""))) {
			message.setSent(new Date());
			messageRepository.save(message);
			return true;
		}

		return false;
	}
}
