package soulOfSkin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/")
public class WebController {

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

	@RequestMapping("/")
	public String showIndex(Model model) {
		List<Blog> listOfBlog = blogService.getAllBlogs();
		List<Image> listOfImg = imgService.getAllImages();
		model.addAttribute("listOfBlog", listOfBlog);
		model.addAttribute("listOfImage", listOfImg);

		return "website/index";
	}

	@RequestMapping("/skin")
	public String showSkin(Model model) {
		List<Blog> listOfBlog = blogService.getAllBlogsForSkin();
		List<Image> listOfImg = imgService.getAllImages();
		model.addAttribute("listOfBlog", listOfBlog);
		model.addAttribute("listOfImage", listOfImg);
		return "website/skin";
	}

	@RequestMapping("/life")
	public String showLife(Model model) {
		List<Blog> listOfBlog = blogService.getAllBlogsForLife();
		List<Image> listOfImg = imgService.getAllImages();
		model.addAttribute("listOfBlog", listOfBlog);
		model.addAttribute("listOfImage", listOfImg);
		return "website/life";
	}

	@RequestMapping("/contact")
	public String showContact(Model model) {
		model.addAttribute("objOfMessage", new Message());
		return "website/contact";
	}

	@RequestMapping("/about")
	public String showAbout(Model model) {
		return "website/about";
	}

	@RequestMapping("/impressum")
	public String showImpressum(Model model) {
		return "website/impressum";
	}

	@RequestMapping("/datenschutz")
	public String showDatenshutz(Model model) {
		return "website/datenshutz";
	}

	private static long commId;

	@RequestMapping("/readBlog/{blogId}")
	public String readBlog(Model model, @PathVariable Long blogId) {
		commId = blogId;
		List<Comment> listOfComment = commService.listCommByBlog(blogId);
		model.addAttribute("listOfComment", listOfComment);

		List<Blog> listOfBlog = blogService.getAllBlogs();
		model.addAttribute("listOfBlog", listOfBlog);

		List<Image> listOfImage = imgService.getAllImages();
		model.addAttribute("listOfImage", listOfImage);

		Blog blog = blogService.getBlog(blogId);
		model.addAttribute("objOfBlog", blog);

		List<Image> listOfImg = imgService.getImageByBlog(blogId);
		model.addAttribute("listOfImg", listOfImg);

		model.addAttribute("objOfComment", new Comment());

		return "website/blog";
	}

	@RequestMapping("/saveComment")
	public String saveComment(Model model, @ModelAttribute Comment comment, RedirectAttributes redirectAttr) {
		boolean savedSuccess = commService.saveComment(comment);
		if (savedSuccess) {
			redirectAttr.addFlashAttribute("saveSuccess", "Nach der Überprüfung werden Kommentare veröffentlicht.");
			return "redirect:/readBlog/" + commId;
		} else {
			redirectAttr.addFlashAttribute("error",
					"Fehler! Einige Felder sind erforderlich (Name,E-Mail and Datenschutz).");
			return "redirect:/readBlog/" + commId;
		}

	}

	@RequestMapping("/sendMessage")
	public String sendMessage(Model model, @ModelAttribute Message message, RedirectAttributes redirectAttr) {

		boolean savedSuccess = messageService.sendMessage(message);
		if (savedSuccess) {
			redirectAttr.addFlashAttribute("saveSuccess",
					"Nachricht erfolgreich gesendet. Sie erhalten innerhalb von 48 Stunden eine Antwort.");
			return "redirect:/contact";
		} else {
			redirectAttr.addFlashAttribute("error",
					"Fehler! Einige Felder sind erforderlich (Name,E-Mail und Datenschutz).");
			return "redirect:/contact";
		}

	}

}