package com.exp.liveness.bean;

/**
 * HTTP 通用返回结果
 * @author exp
 * @date 2022-05-22
 */
public class Result<T> {

    private boolean isOK;

    private int total;

    private int errorNum;

    private T errorData;

    private T sussessData;

    public Result() {
        this.isOK = true;
        this.total = 0;
        this.errorNum = 0;
        this.errorData = null;
        this.sussessData = null;
    }

    public Result(boolean isOK, int total, int errorNum, T errorData, T sussessData) {
        this.isOK = isOK;
        this.total = total;
        this.errorNum = errorNum;
        this.errorData = errorData;
        this.sussessData = sussessData;
    }

    public boolean isOK() {
        return isOK;
    }

    public Result<T> setOK(boolean OK) {
        isOK = OK;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public Result<T> setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public Result<T> setErrorNum(int errorNum) {
        this.errorNum = errorNum;
        return this;
    }

    public T getErrorData() {
        return errorData;
    }

    public Result<T> setErrorData(T errorData) {
        this.errorData = errorData;
        return this;
    }

    public T getSussessData() {
        return sussessData;
    }

    public Result<T> setSussessData(T sussessData) {
        this.sussessData = sussessData;
        return this;
    }
}
