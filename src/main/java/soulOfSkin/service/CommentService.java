package soulOfSkin.service;

import java.util.List;

import soulOfSkin.models.Comment;

public interface CommentService {

	List<Comment> listCommByBlog(Long id);

	boolean saveComment(Comment comment);

	Comment getComment(Long blogId);

	boolean deleteCommentById(Long commentId);

	boolean acceptComment(Long commentId);

	List<Comment> getAllCommentOrderById();

}
