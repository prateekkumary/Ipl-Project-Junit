package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

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


    // Number of matches played per year of all the years in IPL.

    public static Map<String, Integer> getMatchesPlayedByTeamPerYear(List<Match> matches) {
        Map<String, Integer> matchesPerYear = new HashMap<>();
        for (Match match : matches) {
            String year = match.getSeason();

            matchesPerYear.put(year, matchesPerYear.getOrDefault(year, 0) + 1);
        }


        return matchesPerYear;
    }


//    Number of matches won of all teams over all the years of IPL.


    public static Map<String, Map<String, Integer>> getNumberOfMatchesWonByTeamPerYear(List<Match> matches) {

        Map<String, Map<String, Integer>> matchesWonPerYear = new HashMap<>();
        for (Match match : matches) {
            String year = match.getSeason();
            String winner = match.getWinner();

            Map<String, Integer> map = matchesWonPerYear.getOrDefault(year, new HashMap<String, Integer>());
            map.put(winner, map.getOrDefault(winner, 0) + 1);
            matchesWonPerYear.put(year, map);
        }
        return matchesWonPerYear;

//        return matchesPerYear;
    }


//    For the year 2016 get the extra runs conceded per team.

    public static Map<String,Integer> getExtraRunByTeamsIn2016(List<Match> matches, List<Delivery> deliveries, String year) {
        Set<Integer> ids = new HashSet<>();
        for (Match match : matches) {
            if (match.getSeason().equals(year)) {
                ids.add(match.getMatchId());
            }
        }
//      System.out.println((ids));
        Map<String, Integer> extraRunByTeamIn2016 = new HashMap<>();

        for (Delivery delivery : deliveries) {
            String battingTeam = delivery.getBattingTeam();
            int runs = delivery.getExtraRuns();
            if (ids.contains(delivery.getDeliveryMatchId())) {
                extraRunByTeamIn2016.put(battingTeam, extraRunByTeamIn2016.getOrDefault(battingTeam, runs) + runs);
            }
        }
        return extraRunByTeamIn2016;
    }


    //    For the year 2015 get the top economical bowlers.

    public static Map<String,Double> getTopEconomyBowlerInParticularYear(List<Match> matches, List<Delivery> deliveries,String year) {
//            String year="2016";
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
//        System.out.println("Top economy bowler is: " + topEconomicalBolwer + " and with economy: " + lowestEconomy);
        return result;
    }
    public static Map<String, Integer> highestRunWithMatchId(List<Match>matches,List<Delivery>deliveries,int id){
//        Integer id=1;
        Set<Integer>ids=new HashSet<>();
        for(Match match:matches){
            if(id==match.getMatchId()){
                ids.add(match.getMatchId());
            }
        }
        Map<String,Integer>highestRunWithId=new HashMap<>();

        for(Delivery delivery:deliveries){
            if(ids.contains(delivery.getDeliveryMatchId())){
                Integer batsManRun=delivery.getBatsmanRuns();
                String batsman=delivery.getBatsman();
                highestRunWithId.put(batsman,highestRunWithId.getOrDefault(batsman,batsManRun)+batsManRun);
            }
        }
        String batsMan="";
        Integer highestRuns=Integer.MIN_VALUE;
        Map<String,Integer>result=new HashMap<>();
        for(Map.Entry<String,Integer>entry:highestRunWithId.entrySet()){
            if(entry.getValue()>highestRuns){
                highestRuns=entry.getValue();
                batsMan=entry.getKey();
            }
        }
        result.put(batsMan,highestRuns);

        return  result;
    }


    private static List<Delivery> getDeliveriesData() {
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


    private static List<Match> getMatchData() {
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
        return matches;
    }


    public static void main(String[] args) {

//        List<Match> matches = getMatchData();
//        List<Delivery> deliveries = getDeliveriesData();

//         Number of matches played per year of all the years in IPL.
//        getMatchesPlayedByTeamPerYear(matches);

//        Number of matches won of all teams over all the years of IPL.
//        getNumberOfMatchesWonByTeamPerYear(matches);

//        For the year 2016 get the extra runs conceded per team.
//       getExtraRunByTeamsIn2016(matches,deliveries,"2016");


//        For the year 2015 get the top economical bowlers.
        //getTopEconomyBowlerInParticularYear(matches, deliveries, "2010");


    }
}




