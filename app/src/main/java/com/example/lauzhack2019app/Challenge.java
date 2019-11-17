package com.example.lauzhack2019app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Challenge {
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private BigDecimal applePrice;
    private BigDecimal googlePrice;
    private BigDecimal microsoftPrice;
    private BigDecimal spotifyPrice;
    private BigDecimal amazonPrice;

    private BigDecimal cpuMoney;
    private int cpuAppleAmount = 0;
    private int cpuGoogleAmount = 0;
    private int cpuMicrosoftAmount = 0;
    private int cpuSpotifyAmount = 0;
    private int cpuAmazonAmount = 0;

    private Random random;

    Challenge(BigDecimal applePrice, BigDecimal googlePrice, BigDecimal microsoftPrice,
              BigDecimal spotifyPrice, BigDecimal amazonPrice, BigDecimal cpuMoney) {
        random = new Random();
        this.applePrice = applePrice;
        this.googlePrice = googlePrice;
        this.microsoftPrice = microsoftPrice;
        this.spotifyPrice = spotifyPrice;
        this.amazonPrice = amazonPrice;
        this.cpuMoney = cpuMoney;
    }

    BigDecimal getApplePrice() {
        return applePrice;
    }

    BigDecimal getGooglePrice() {
        return googlePrice;
    }

    BigDecimal getMicrosoftPrice() {
        return microsoftPrice;
    }

    BigDecimal getSpotifyPrice() {
        return spotifyPrice;
    }

    BigDecimal getAmazonPrice() {
        return amazonPrice;
    }

    BigDecimal getCpuMoney() {
        return cpuMoney;
    }

    void cpuBuyOrSale() {
        buySaleApple();
        buySaleGoogle();
        buySaleMicrosoft();
        buySaleSpotify();
        buySaleAmazon();
    }

    private void buySaleApple() {
        if (cpuAppleAmount > 0) {
            if(random.nextBoolean()) { // should sell apple
                cpuMoney = cpuMoney.add(applePrice);
                cpuAppleAmount--;
            }
        }
        if (cpuMoney.compareTo(applePrice) == 1) {
            if(random.nextBoolean()) { // should buy apple
                cpuMoney = cpuMoney.subtract(applePrice);
                cpuAppleAmount++;
            }
        }
    }

    private void buySaleGoogle() {
        if (cpuGoogleAmount > 0) {
            if(random.nextBoolean()) { // should sell google
                cpuMoney = cpuMoney.add(googlePrice);
                cpuGoogleAmount--;
            }
        }
        if (cpuMoney.compareTo(googlePrice) == 1) {
            if(random.nextBoolean()) { // should buy google
                cpuMoney = cpuMoney.subtract(googlePrice);
                cpuGoogleAmount++;
            }
        }
    }

    private void buySaleMicrosoft() {
        if (cpuMicrosoftAmount > 0) {
            if(random.nextBoolean()) { // should sell microsoft
                cpuMoney = cpuMoney.add(microsoftPrice);
                cpuMicrosoftAmount--;
            }
        }
        if (cpuMoney.compareTo(microsoftPrice) == 1) {
            if(random.nextBoolean()) { // should buy microsoft
                cpuMoney = cpuMoney.subtract(microsoftPrice);
                cpuMicrosoftAmount++;
            }
        }
    }

    private void buySaleSpotify() {
        if (cpuSpotifyAmount > 0) {
            if(random.nextBoolean()) { // should sell spotify
                cpuMoney = cpuMoney.add(spotifyPrice);
                cpuSpotifyAmount--;
            }
        }
        if (cpuMoney.compareTo(spotifyPrice) == 1) {
            if(random.nextBoolean()) { // should buy spotify
                cpuMoney = cpuMoney.subtract(spotifyPrice);
                cpuSpotifyAmount++;
            }
        }
    }

    private void buySaleAmazon() {
        if (cpuAmazonAmount > 0) {
            if(random.nextBoolean()) { // should sell amazon
                cpuMoney = cpuMoney.add(amazonPrice);
                cpuAmazonAmount--;
            }
        }
        if (cpuMoney.compareTo(amazonPrice) == 1) {
            if(random.nextBoolean()) { // should buy amazon
                cpuMoney = cpuMoney.subtract(amazonPrice);
                cpuAmazonAmount++;
            }
        }
    }

    List<ComputationResult> generateRandomPrices() {
        int appleInc = getIncrement();
        int googleInc = getIncrement();
        int microsoftInc = getIncrement();
        int spotifyInc = getIncrement();
        int amazonInc = getIncrement();
        List<ComputationResult> list = new ArrayList<>();
        ComputationResult res = getResult(applePrice, appleInc);
        applePrice = res.getPrice();
        list.add(res);
        res = getResult(googlePrice, googleInc);
        googlePrice = res.getPrice();
        list.add(res);
        res = getResult(microsoftPrice, microsoftInc);
        microsoftPrice = res.getPrice();
        list.add(res);
        res = getResult(spotifyPrice, spotifyInc);
        spotifyPrice = res.getPrice();
        list.add(res);
        res = getResult(amazonPrice, amazonInc);
        amazonPrice = res.getPrice();
        list.add(res);
        return list;
    }

    private int getIncrement() {
        return random.nextInt(21) - 10;
    }

    private ComputationResult getResult(BigDecimal value, int increment) {
        BigDecimal inc = BigDecimal.valueOf(increment).add(HUNDRED).divide(HUNDRED);
        BigDecimal price = value.multiply(inc);
        return new ComputationResult(price, inc.compareTo(BigDecimal.valueOf(1)));
    }

    class ComputationResult {
        BigDecimal price;
        int increased;

        ComputationResult(BigDecimal price, int increased) {
            this.price = price;
            this.increased = increased;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public int getIncreased() {
            return increased;
        }
    }
}
