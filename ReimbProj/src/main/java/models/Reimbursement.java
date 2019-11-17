package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import daos.UserDao;

public class Reimbursement {

	UserDao userDao = UserDao.currentImplementation;

	private int reimbId;
	private int amount;
	private LocalDateTime created;
	private LocalDateTime resolved;
	private String description;
	private int author;
	private int resolver;
	private int status;
	private int type;

	public Reimbursement(int reimbId, int amount, LocalDateTime created, LocalDateTime resolved, String description,
			int author, int resolver, int status, int type) {
		super();
		this.reimbId = reimbId;
		this.amount = amount;
		this.created = created;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCreated() {// convert to readable text
		String createdSt = DateTimeFormatter.ISO_LOCAL_DATE.format(created).replace('-', '/') + " "
				+ DateTimeFormatter.ISO_LOCAL_TIME.format(created);
		return createdSt;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getResolved() {// convert to readable text
		if (resolved == null) {
			return " ";
		}
		String resolvedSt = DateTimeFormatter.ISO_LOCAL_DATE.format(resolved).replace('-', '/') + " "
				+ DateTimeFormatter.ISO_LOCAL_TIME.format(resolved);
		return resolvedSt;
	}

	public void setResolved(LocalDateTime resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		String u = "";
		try {
			u = userDao.findByUserName(author).getUsername();
		} catch (Exception e) {
			return " ";
		}
		return u;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public String getResolver() {
		String u = "";
		try {
			u = userDao.findByUserName(resolver).getUsername();
		} catch (Exception e) {
			return " ";
		}
		return u;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}

	public String getStatus() {
		switch (status) {
		case (1):
			return "Pending";
		case (2):
			return "Approved";
		case (3):
			return "Denied";
		}
		return " ";
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		switch (type) {
		case (1):
			return "Lodging";
		case (2):
			return "Travel";
		case (3):
			return "Food";
		case (4):
			return "Other";
		}
		return " ";
	}

	public int getAuthor(int i) {
		return author;
	}

	public int getType(int i) {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", amount=" + amount + ", created=" + getCreated() + ", resolved="
				+ resolved + ", description=" + description + ", author=" + author + ", resolver=" + resolver
				+ ", status=" + status + ", type=" + type + "]";
	}
}
