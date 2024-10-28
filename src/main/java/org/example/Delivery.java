package org.example;

public class Delivery {

    private int deliveryId;
    private int batsmanRun;
    private int totalRuns;
    private int wideRuns;
    private int noballRuns;
    private int extraRuns;
    private int superOver;
    private int legByRuns;
    private String battingTeam;
    private String bowlingTeam;
    private String dismissalKind;
    private String dismissedPlayer;
    private String bolwer;
    private String batsman;
    private int inings;

    public int getBatsmanRuns() {
        return batsmanRun;
    }

    public void setBatsmanRuns(int batsmanRun) {
        this.batsmanRun = batsmanRun;
    }

    public int getNoballRuns() {
        return noballRuns;
    }

    public void setNoballRuns(int noballRuns) {
        this.noballRuns = noballRuns;
    }

    public int getWideRuns() {
        return wideRuns;
    }

    public void setWideRuns(int wideRuns) {
        this.wideRuns = wideRuns;
    }

    public String getBatsman() {
        return batsman;
    }

    public void setBatsman(String batsman) {
        this.batsman = batsman;
    }

    public String getBolwer() {
        return bolwer;
    }

    public void setBolwer(String bolwer) {
        this.bolwer = bolwer;
    }

    public int getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(int extraRuns) {
        this.extraRuns = extraRuns;
    }

    public int getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(int totalRuns) {
        this.totalRuns = totalRuns;
    }

    public int getDeliveryMatchId() {
        return deliveryId;
    }

    public void setDeliveryMatchId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getLegByRuns() {
        return legByRuns;
    }

    public void setLegByRuns(int legByRuns) {
        this.legByRuns = legByRuns;
    }

    public String getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }

    public String getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(String bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    public int getSuperOver() {
        return superOver;
    }

    public void setSuperOver(int superOver) {
        this.superOver = superOver;
    }

    public int getInings() {
        return inings;
    }

    public void setInings(int inings) {
        this.inings = inings;
    }

    public String getPlayerDismissed() {
        return dismissedPlayer;
    }

    public void setPlayerDismissed(String dismissedPlayer) {
        this.dismissedPlayer = dismissedPlayer;
    }

    public String getDismissalKind() {
        return dismissalKind;
    }

    public void setDismissalKind(String dismissalKind) {
        this.dismissalKind = dismissalKind;
    }
}
