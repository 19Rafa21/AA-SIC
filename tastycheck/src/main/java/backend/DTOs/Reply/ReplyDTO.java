package backend.DTOs.Reply;

import backend.Models.Reply;

import java.util.Date;

public class ReplyDTO {
    private String text;
    private String userId;
    private String username;
    private Date date;

    public ReplyDTO() {}

    public ReplyDTO(String text, String userId, String username, Date date) {
        this.text = text;
        this.userId = userId;
        this.username = username;
        this.date = date;
    }

	public ReplyDTO(Reply reply) {
        this.text = reply.getText();
        this.userId = reply.getAuthor().getId();
        this.username = reply.getAuthor().getUsername();
        this.date = reply.getData();
	}

	public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
