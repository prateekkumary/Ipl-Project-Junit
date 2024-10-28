package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class junitDemo {

    private static final int match_id = 0;
    private static final int season = 1;
    private static final int team_1 = 4;
    private static final int team_2 = 5;
    private static final int toss_winner = 6;
    private static final int winner = 10;
    private static final int player_of_match = 13;

    private static final int deliveryMatch_id = 0;
    private static final int inings = 1;
    private static final int batting_team = 2;
    private static final int bowling_team = 3;
    private static final int batsman_name = 6;
    private static final int bowler_name = 8;
    private static final int legbyRuns = 12;
    private static final int noballRuns = 13;
    private static final int total_runs = 17;
    private static final int extraRun = 16;
    private static final int batsmanRun = 15;
    private static final int wide_runs = 11;
    private static final int dismissedPlayerName = 18;
    private static final int dismissalKind = 19;
    private static final int superOver = 9;


    private static List<Match> matches;
    private static List<Delivery> deliveries;


    @BeforeAll
    public static void setup() {
        matches = loadMatchData();
        deliveries = loadDeliveryData();
//        System.out.println("Setup complete. Matches loaded: " + (matches != null ? matches.size() : "null"));
    }

    private static List<Match> loadMatchData(){
        List<Match> matches = new ArrayList<>();
        String filepath = "./data/matches.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line = "";
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] matchData = line.split(",");

                Match match = new Match();
                match.setSeason(matchData[season]);
                match.setWinner(matchData[winner]);
                match.setMatchId(Integer.parseInt(matchData[match_id]));
                match.setTeam1(matchData[team_1]);
                match.setTeam2(matchData[team_2]);
                match.setTossWinner(matchData[toss_winner]);
                match.setPlayerOfMatch(matchData[player_of_match]);

                matches.add(match);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(matches);
        return matches;
    }
    private static List<Delivery>loadDeliveryData(){
        List<Delivery> deliveries = new ArrayList<>();
        String filepath = "./data/deliveries.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line = "";
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");

                Delivery delivery = new Delivery();
                delivery.setBatsmanRuns(Integer.parseInt(rowData[batsmanRun]));
                delivery.setNoballRuns(Integer.parseInt(rowData[noballRuns]));
                delivery.setWideRuns(Integer.parseInt(rowData[wide_runs]));
                delivery.setBatsman(rowData[batsman_name]);
                delivery.setBolwer(rowData[bowler_name]);
                delivery.setExtraRuns(Integer.parseInt(rowData[extraRun]));
                delivery.setTotalRuns(Integer.parseInt(rowData[total_runs]));
                delivery.setDeliveryMatchId(Integer.parseInt(rowData[deliveryMatch_id]));
                delivery.setLegByRuns(Integer.parseInt(rowData[legbyRuns]));
                delivery.setBattingTeam(rowData[batting_team]);
                delivery.setBowlingTeam(rowData[bowling_team]);
                delivery.setSuperOver(Integer.parseInt(rowData[superOver]));
                delivery.setInings(Integer.parseInt(rowData[inings]));


                if (rowData.length > 18) {
                    delivery.setPlayerDismissed(rowData[dismissedPlayerName]);
                    delivery.setDismissalKind(rowData[19]);
                } else {
                    delivery.setPlayerDismissed(null);
                    delivery.setDismissalKind(null);
                }

                deliveries.add(delivery);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return deliveries;
    }
    @Test
    public void  testMatchesPlayedByPerYear() {
        Map<String, Integer> matchesPerYear = new HashMap<>();

        for (Match match : matches) {
            String year = match.getSeason();
//            System.out.println(year);
            matchesPerYear.put(year, matchesPerYear.getOrDefault(year, 0) + 1);
        }

        Map<String,Integer>actualResult=Main.getMatchesPlayedByTeamPerYear(matches);
        Assertions.assertEquals(actualResult,matchesPerYear);

    }
    @Test
    public  void testMatchesWonBYteam(){
        Map<String,Map<String,Integer>>matchesWonByTeam=new HashMap<>();

        for(Match match:matches){
            String year=match.getSeason();
            String winner=match.getWinner();
            Map<String,Integer>map=matchesWonByTeam.getOrDefault(year,matchesWonByTeam.getOrDefault(year,new HashMap<>()));
            map.put(winner,map.getOrDefault(winner,0)+1);
            matchesWonByTeam.put(year,map);
        }
        System.out.println(matchesWonByTeam);
        Map<String,Map<String,Integer>>actulResult=Main.getNumberOfMatchesWonByTeamPerYear(matches);
        Assertions.assertEquals(actulResult,matchesWonByTeam);
    }
@Test
    public void testExtraTunByTeamInParticularYear(){

        String year="2016";
     Set<Integer>ids=new HashSet<>();

     for(Match match:matches){
         if(match.getSeason().equals(year)){
             ids.add(match.getMatchId());
         }
     }
     Map<String,Integer>extraRun=new HashMap<>();

     for(Delivery delivery:deliveries){
         if(ids.contains(delivery.getDeliveryMatchId())){
             Integer runs=delivery.getExtraRuns();
             String battingTeam=delivery.getBattingTeam();
             extraRun.put(battingTeam,extraRun.getOrDefault(battingTeam,runs)+runs);
         }
     }
     System.out.println(extraRun);
     Map<String,Integer>actualResult=Main.getExtraRunByTeamsIn2016(matches,deliveries,year);
     Assertions.assertEquals(actualResult,extraRun);
    }
@Test
    public void testTopEconimyBolwer(){
        String year="2016";
        Set<Integer> ids = new HashSet<>();


        Map<String, Integer> concededRuns = new HashMap<>();
        Map<String, Integer> validBall = new HashMap<>();
        for (Match match : matches) {
            if (match.getSeason().equals(year)) {
                ids.add(match.getMatchId());
            }
        }
//        System.out.println(ids);
        Map<String, Integer> bolwerWithEconomy = new HashMap<>();
        for (Delivery delivery : deliveries) {

            if (ids.contains(delivery.getDeliveryMatchId())) {
                int isValidBalls = (delivery.getNoballRuns() == 0 && delivery.getWideRuns() == 0) ? 1 : 0;

                int totalRuns = delivery.getTotalRuns();

                String bolwer = delivery.getBolwer();
                concededRuns.put(bolwer, concededRuns.getOrDefault(bolwer, 0) + totalRuns);
                validBall.put(bolwer, validBall.getOrDefault(bolwer, 0) + isValidBalls);

            }
        }
        Map<String, Double> economyRate = new HashMap<>();
        for (String bolwer : validBall.keySet()) {
            int totalBalls = validBall.get(bolwer);
            int totalRuns = concededRuns.get(bolwer);

            double over = totalBalls / 6.0;
            double economicalBolwer;
            if (over == 0) {
                economicalBolwer = 0.0d;
            } else {
                economicalBolwer = totalRuns / over;
            }
            economyRate.put(bolwer, economicalBolwer);
        }
        String topEconomicalBolwer = "";
        double lowestEconomy = Double.MAX_VALUE;

        for (Map.Entry<String, Double> entry : economyRate.entrySet()) {
            if (entry.getValue() < lowestEconomy) {
                lowestEconomy = entry.getValue();
                topEconomicalBolwer = entry.getKey();
            }
        }
        Map<String,Double>result=new HashMap<>();
        result.put(topEconomicalBolwer,lowestEconomy);
//       String year="2016";
        Map<String,Double>actualResult=Main.getTopEconomyBowlerInParticularYear(matches,deliveries,year);

        Assertions.assertEquals(actualResult,result);

    }
    @Test
    public void testHighestRunWithId(){
        int id=1;
        Set<Integer>ids=new HashSet<>();
        for(Match match:matches){
            if(id==match.getMatchId()){
                ids.add(match.getMatchId());
            }
        }
        Map<String,Integer>highestRunWithId=new HashMap<>();

        for(Delivery delivery:deliveries){
            if(ids.contains(delivery.getDeliveryMatchId())){
                String batsMan=delivery.getBatsman();
                int batsmanRun=delivery.getBatsmanRuns();
                highestRunWithId.put(batsMan,highestRunWithId.getOrDefault(batsMan,batsmanRun)+batsmanRun);
            }

        }
        String highestBatsman="";
        Integer highestRun=Integer.MIN_VALUE;
        Map<String,Integer>result=new HashMap<>();

        for(Map.Entry<String,Integer> entry:highestRunWithId.entrySet()){
            if(entry.getValue()>highestRun){
                highestRun= entry.getValue();
                highestBatsman= entry.getKey();
            }
        }
        result.put(highestBatsman,highestRun);
        Map<String,Integer>actualResult=Main.highestRunWithMatchId(matches,deliveries,1);
        Assertions.assertEquals(actualResult,result);
    }


}
