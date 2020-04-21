package soulOfSkin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import soulOfSkin.models.Blog;
import soulOfSkin.models.Comment;
import soulOfSkin.models.Image;
import soulOfSkin.models.Message;
import soulOfSkin.service.BlogService;
import soulOfSkin.service.CommentService;
import soulOfSkin.service.ImageService;
import soulOfSkin.service.MessageService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private ImageService imgService;

	@Autowired
	private CommentService commService;

	@Autowired
	private MessageService messageService;

	// @Autowired
	// private ServletContext context;

	// -----------------------BLOG PART-------------------------------------

	@RequestMapping("/home")
	public String addBlog(Model model) {
		model.addAttribute("objOfBlog", new Blog());

		List<Blog> listOfBlog = blogService.getAllBlogs();
		List<Image> listOfImg = imgService.getAllImages();
		model.addAttribute("listOfBlog", listOfBlog);
		model.addAttribute("listOfImage", listOfImg);

		return "admin/home";
	}

	@RequestMapping("/comment")
	public String updateComment(Model model) {
		model.addAttribute("objOfComment", new Comment());
		List<Blog> listOfBlog = blogService.getAllBlogs();
		List<Image> listOfImg = imgService.getAllImages();
		List<Comment> listOfComment = commService.getAllCommentOrderById();
		model.addAttribute("listOfComment", listOfComment);
		model.addAttribute("listOfBlog", listOfBlog);
		model.addAttribute("listOfImage", listOfImg);

		model.addAttribute("isUpdate", false);

		return "admin/comment";
	}

	@RequestMapping("/message")
	public String viewMessage(Model model) {
		List<Message> listOfMessage = messageService.getAllMessage();
		model.addAttribute("listOfMessage", listOfMessage);
		return "admin/message";
	}

	@GetMapping("/deleteMessage/{messageId}")
	public String deleteMessage(Model model, @PathVariable Long messageId, RedirectAttributes redirectAttr) {
		boolean succcessDelete = messageService.deleteMessageById(messageId);
		if (succcessDelete) {
			redirectAttr.addFlashAttribute("deleteSuccess", "Message erfolgreich gelöscht!");
			return "redirect:/admin/message";
		} else {
			redirectAttr.addFlashAttribute("error", "Message erfolgreich gelöscht nicht!");
			return "redirect:/admin/message";
		}
	}

	// --- EDIT BLOG ---
	@RequestMapping("/editBlog/{blogId}")
	public String editVehicle(Model model, @PathVariable Long blogId) {
		Blog blog = blogService.getBlog(blogId);
		model.addAttribute("objOfBlog", blog);

		List<Image> listOfImg = imgService.getImageByBlog(blogId);
		model.addAttribute("listOfImg", listOfImg);

		boolean isUpdated = true;
		model.addAttribute("isUpdated", isUpdated);

		return "admin/home";
	}

	// --- EDIT COMMENT ---
	@RequestMapping("/editComment/{commentId}")
	public String editComment(Model model, @PathVariable Long commentId) {
		Comment comment = commService.getComment(commentId);
		model.addAttribute("isUpdate", true);
		model.addAttribute("objOfComment", comment);
		return "admin/comment";
	}

	// ---- SAVE BLOG ----
	@RequestMapping("/saveBlog")
	public String saveBlog(@ModelAttribute Blog blog, RedirectAttributes redirectAttr) {
		String saved = blogService.saveBlog(blog);
		if (saved.equals("success")) {
			redirectAttr.addFlashAttribute("saveSuccess", "Beitrag erfolgreich gepostet!");
			return "redirect:/admin/home";
		} else {
			redirectAttr.addFlashAttribute("error", saved);
			return "redirect:/admin/home";
		}
	}

	// ---- SAVE COMMENT ----
	@RequestMapping("/saveComment")
	public String saveComment(@ModelAttribute Comment comment, RedirectAttributes redirectAttr) {
		boolean saved = commService.saveComment(comment);
		if (saved) {
			redirectAttr.addFlashAttribute("saveSuccess", "Comment erfolgreich gepostet!");
			return "redirect:/admin/comment";
		} else {
			redirectAttr.addFlashAttribute("error", saved);
			return "redirect:/admin/comment";
		}
	}

	// ---- ALLOWED COMMENT ----
	@RequestMapping("/allowedComment/{commentId}")
	public String allowedComment(@PathVariable Long commentId, RedirectAttributes redirectAttr) {
		boolean saved = commService.acceptComment(commentId);
		if (saved) {
			redirectAttr.addFlashAttribute("saveSuccess", "Änderung der Ansichtsberechtigung geändert");
			return "redirect:/admin/comment";
		} else {
			redirectAttr.addFlashAttribute("error", " Error . . .");
			return "redirect:/admin/comment";
		}
	}

	// ---- DELETE BLOG ----
	@GetMapping("/deleteBlog/{blogId}")
	public String deleteBLog(Model model, @PathVariable Long blogId, RedirectAttributes redirectAttr) {
		boolean succcessDelete = blogService.deleteBlogById(blogId);
		if (succcessDelete) {
			redirectAttr.addFlashAttribute("deleteSuccess", "Blog erfolgreich gelöscht!");
			return "redirect:/admin/home";
		} else {
			redirectAttr.addFlashAttribute("error", "Blog erfolgreich gelöscht nicht! ");
			return "redirect:/admin/home";
		}
	}

	// ---- DELETE COMMENT ----
	@GetMapping("/deleteComment/{commentId}")
	public String deleteComment(Model model, @PathVariable Long commentId, RedirectAttributes redirectAttr) {
		boolean succcessDelete = commService.deleteCommentById(commentId);
		if (succcessDelete) {
			redirectAttr.addFlashAttribute("deleteSuccess", "Comment erfolgreich gelöscht!");
			return "redirect:/admin/comment";
		} else {
			redirectAttr.addFlashAttribute("error", "Comment erfolgreich gelöscht nicht!");
			return "redirect:/admin/comment";
		}
	}

}
