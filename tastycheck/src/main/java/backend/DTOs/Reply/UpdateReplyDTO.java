package backend.DTOs.Reply;

import backend.Models.Reply;

public class UpdateReplyDTO {

	private String text;

	public UpdateReplyDTO() {}

	public UpdateReplyDTO(String text, String userId) {
		this.text = text;
	}

	public UpdateReplyDTO(Reply reply) {
		this.text = reply.getText();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
