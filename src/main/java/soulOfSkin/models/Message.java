package soulOfSkin.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "text")
	private String text;

	@Column(name = "sent")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date sent;

	@Transient
	private String showDateTime;

	@Transient
	private boolean dataAccept;

	public boolean isDataAccept() {
		return dataAccept;
	}

	public void setDataAccept(boolean dataAccept) {
		this.dataAccept = dataAccept;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getSent() {
		return sent;
	}

	public void setSent(Date sent) {
		this.sent = sent;
	}

	public String getShowDateTime() {
		return showDateTime;
	}

	public void setShowDateTime(String showDateTime) {
		this.showDateTime = showDateTime;
	}

}
