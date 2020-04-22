package soulOfSkin.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soulOfSkin.models.Comment;
import soulOfSkin.repository.CommentRepository;

@Service
@Transactional
public class CommentSerImpl implements CommentService {

	@Autowired
	private CommentRepository commRepository;

	@Override
	public List<Comment> listCommByBlog(Long id) {
		List<Comment> listOfComment = new ArrayList<>();
		List<Comment> allComments = commRepository.getAllCommentByBlogId(id);
		for (Comment comment : allComments) {
			if (comment.getSaved() != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date date = comment.getSaved();
				String viewDate = dateFormat.format(date);
				comment.setShowDate(viewDate);

			} else {
				comment.setShowDate("00.00.0000");

			}

			listOfComment.add(comment);
		}

		return listOfComment;
	}

	@Override
	public boolean saveComment(Comment comment) {
		if ((!comment.getNickname().equals("")) && (!comment.getEmail().equals("") && comment.isAllowed())) {
			comment.setSaved(new Date());
			commRepository.save(comment);
			return true;
		}
		return false;
	}

	@Override
	public Comment getComment(Long commentId) {
		return commRepository.getOne(commentId);
	}

	@Override
	public boolean deleteCommentById(Long commentId) {
		commRepository.deleteById(commentId);
		return true;
	}

	@Override
	public boolean acceptComment(Long commentId) {
		Comment comment = commRepository.getOne(commentId);
		if (!comment.isAllowed()) {
			comment.setAllowed(true);
			commRepository.save(comment);
			return true;
		}
		comment.setAllowed(false);
		commRepository.save(comment);
		return true;
	}

	@Override
	public List<Comment> getAllCommentOrderById() {
		List<Comment> listOfComment = new ArrayList<>();
		List<Comment> allComments = commRepository.getAllCommentOrderById();
		for (Comment comment : allComments) {
			if (comment.getSaved() != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date date = comment.getSaved();
				String viewDate = dateFormat.format(date);
				comment.setShowDate(viewDate);

			} else {
				comment.setShowDate("00.00.0000");

			}

			listOfComment.add(comment);
		}

		return listOfComment;

	}

}
