package entity;

public class ChatrecordEntity {
    private ChatrecordEntityPK chatrecordEntityPK;
    private String content;
    private String state;

    public ChatrecordEntityPK getChatrecordEntityPK() {
        return chatrecordEntityPK;
    }

    public void setChatrecordEntityPK(ChatrecordEntityPK chatrecordEntityPK) {
        this.chatrecordEntityPK = chatrecordEntityPK;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
