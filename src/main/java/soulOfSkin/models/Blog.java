package soulOfSkin.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "blog")
public class Blog implements Serializable {

	private static final long serialVersionUID = -4541447824934111105L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "header")
	private String header;

	@Column(name = "body")
	private String body;

	@Column(name = "footer")
	private String footer;

	@Column(name = "published")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date published;

	@Column(name = "saved")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date saved;

	@Column(name = "side")
	private String side;

	@Column(name = "share")
	private String share;

	@Transient
	private List<MultipartFile> imageList = new ArrayList<MultipartFile>();

	@Transient
	private List<String> removeImages = new ArrayList<String>();

	@Transient
	private String viewDate;

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getViewDate() {
		return viewDate;
	}

	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Date getSaved() {
		return saved;
	}

	public void setSaved(Date saved) {
		this.saved = saved;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public List<MultipartFile> getImageList() {
		return imageList;
	}

	public void setImageList(List<MultipartFile> imageList) {
		this.imageList = imageList;
	}

	public List<String> getRemoveImages() {
		return removeImages;
	}

	public void setRemoveImages(List<String> removeImages) {
		this.removeImages = removeImages;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
