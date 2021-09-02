package dev.jihun.domain.exchangerate.domain;

import lombok.Builder;

public class ExchangeRate {

    private double krw;
    private double jpy;
    private double php;

    @Builder
    public ExchangeRate(double krw, double jpy, double php) {
        this.krw = krw;
        this.jpy = jpy;
        this.php = php;
    }
}
