package com.yuas.pecker.utils.reservoir;

public interface ReservoirClearCallback {

    public void onSuccess();

    public void onFailure(Exception e);
}
