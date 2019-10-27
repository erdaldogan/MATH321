/**
 *
 * MEF University Computer Engineering Dept.
 * MATH 321 - Automata Theory and Formal Languages, Fall 2019
 * Assignment 1 - RegEx Applications
 * @author Erdal Sidal Dogan
 * Student ID: 041701076
 * @since September 17, 2019
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main1 {
    public static void main(String[] args) throws IOException {
        File inputFile = new File("input.txt");
        FileOutputStream outputStream = new FileOutputStream(new File("output1.txt"));
        Scanner input = new Scanner(inputFile);

        while (input.hasNextLine()) {
            String inputLine = input.nextLine();
            inputLine = beginning_end_punctuations(inputLine);
            inputLine = and_or(inputLine);
            inputLine = said(inputLine);
            inputLine = punctuation(inputLine);
            inputLine = repeating_digits(inputLine);
            inputLine = past_tense_ed(inputLine);
            inputLine = past_tense_ged(inputLine);
            inputLine = x_of_y(inputLine);
            inputLine = phone_number(inputLine);
            inputLine = capitals(inputLine);
            //inputLine = pluralize(inputLine);


            inputLine = inputLine + '\n';
            outputStream.write(inputLine.getBytes());

        }
    }

    private static String and_or(String inputLine) {
        Pattern pattern = Pattern.compile("\\s+(or|and)\\s+");
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find()) {
            inputLine = inputLine.replaceAll("\\s+or\\s+", "/");
            inputLine = inputLine.replaceAll("\\sand\\s", "&");
        }
        return inputLine;
    }

    private static String said(String inputLine) {
        Pattern pattern = Pattern.compile(".*said\\s+\\w+\\W*?$");
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find())
            inputLine = inputLine.replaceAll("(.*)\\s+(said\\s+.*$)", "\"$1\" $2");
        return inputLine;
    }

    private static String punctuation(String inputLine) {
        Pattern pattern = Pattern.compile("\\W{2,}$");
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find())
            inputLine = inputLine.replaceAll("\\W+$", ".");
        return inputLine;
    }

    private static String repeating_digits(String inputLine) {
        Pattern pattern = Pattern.compile("[^\\(]\\D(\\d)\\1+(\\D|\\b)");
        // put not opening parenthesis in front in order to prevent it to match with phone numbers
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find()) {
            int matchedInt = Integer.parseInt(matcher.group(1));
            inputLine = inputLine.replaceFirst("(\\d)\\1+", digitToChar(matchedInt));
        }
        return inputLine;
    }

    private static String past_tense_ed(String inputLine) {
        Pattern pattern = Pattern.compile("\\w+(lk|ch)\\b");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
            inputLine = inputLine.replaceAll("(\\w+)(lk|ch)\\b", "$1$2ed");
        return inputLine;
    }

    private static String past_tense_ged(String inputLine) {
        Pattern pattern = Pattern.compile(".*\\w+(eg|ag)\\b.*");
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find())
            inputLine = inputLine.replaceAll("(\\w+)(eg|ag)\\b", "$1$2ged");
        return inputLine;
    }

    private static String x_of_y(String inputLine) {
        Pattern pattern = Pattern.compile("\\w+'s\\s+");
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find())
            inputLine = inputLine.replaceAll("(.*)'s\\s+(.*)", "the $2 of $1");
        return inputLine;
    }

    private static String phone_number(String inputLine) {
        Pattern pattern = Pattern.compile(".*\\(\\d+\\)(\\d+|\\s+)+.*");
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find()){
            inputLine = inputLine.replaceAll("(\\d)\\s+(\\d)", "$1$2"); // drop spaces
            inputLine = inputLine.replaceAll("\\((\\d+)\\)\\s*(\\d+)", "$1-$2");
        }
        return inputLine;
    }

    private static String beginning_end_punctuations(String inputLine) {
        Pattern pattern = Pattern.compile("\\s?[^\\s\\w]+[a-zA-z[^0-9\\W]]+[^\\s\\w]+\\s?");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find()) {
            inputLine = inputLine.replaceFirst("([^\\s\\w]+)(\\w+)([^\\s\\w]+)", "$2$1$3");
        }
        return inputLine;
    }

    private static String capitals(String inputLine) {
        Pattern pattern = Pattern.compile("^(\\w+\\s+8)+(\\w*[A-Z]+\\w*)");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find()) {
            String temp =  matcher.group(2).toLowerCase();
            System.out.printf("catal %s\n", temp);
            inputLine = inputLine.replaceFirst("\\b\\w*[A-Z]+\\w*\\b", temp);
        }

        return inputLine;
    }

    private static String pluralize(String inputLine){
        Pattern pattern = Pattern.compile("\\w+(s|ss|sh|ch|z)");
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find()){
            inputLine = inputLine.replaceAll("\\w+(s|ss|sh|ch|z)","$0es");
        }
        Pattern pattern_new = Pattern.compile("\\w+");
        Matcher matcher_new = pattern_new.matcher(inputLine);
        if (matcher_new.find())
            inputLine = inputLine.replaceAll("\\w+", "$0s");
        return inputLine;
    }

    private static String digitToChar(int num){
        switch (num){
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            case 4: return "IV";
            case 5: return "V";
            case 6: return "VI";
            case 7: return "VII";
            case 8: return "VIII";
            case 9: return "IX";
        }
        return String.valueOf(num);
    }
}

