package cting.com.robin.support.teethcare.repository;

public class MessageEvent {
    private String message;
    private boolean isFinish = false;

    public MessageEvent() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean loadFinish) {
        this.isFinish = loadFinish;
    }
}
