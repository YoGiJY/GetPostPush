package com.example.getpostpush;

public class Data {
    private String foobar = null;

    public boolean isReady() {
        return (foobar != null);
    }

    public String getFoobar() { return foobar; }

    public void setFoobar(String result) { this.foobar = result; }

    @Override
    public String toString() {
    return "Data [Foobar=" + foobar + "]";
    }
   }