package com.uddernetworks.bfjvm.brainfuck;

public class BFDataToken {

    private BFToken token;
    private int data;

    public BFDataToken(BFToken token) {
        this(token, 1);
    }

    public BFDataToken(BFToken token, int data) {
        this.token = token;
        this.data = data;
    }

    public BFToken getToken() {
        return token;
    }

    public void setToken(BFToken token) {
        this.token = token;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BFDataToken{" +
                "token=" + token +
                ", data=" + data +
                '}';
    }
}
