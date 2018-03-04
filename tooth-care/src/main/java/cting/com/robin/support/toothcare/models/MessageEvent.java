package cting.com.robin.support.toothcare.models;

public class MessageEvent {
    private String message;
    private boolean loadFinish = false;

    public MessageEvent() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLoadFinish() {
        return loadFinish;
    }

    public void setLoadFinish(boolean loadFinish) {
        this.loadFinish = loadFinish;
    }
}
