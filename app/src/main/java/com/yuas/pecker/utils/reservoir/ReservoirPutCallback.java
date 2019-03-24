package com.yuas.pecker.utils.reservoir;

public interface ReservoirPutCallback {

    public void onSuccess();

    public void onFailure(Exception e);
}
