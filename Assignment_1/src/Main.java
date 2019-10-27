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

public class Main {
    public static void main(String[] args) throws IOException {
        File inputFile = new File("input.txt");
        FileOutputStream outputStream = new FileOutputStream(new File("output.txt"));
        Scanner input = new Scanner(inputFile);

        while(input.hasNextLine()) {
            String inputLine = input.nextLine();
            System.out.println(inputLine);
            String outputLine = "";
            if (inputLine.matches(".*\\s+(or|and)\\s+.*")){ // me/you, me&you
                System.out.println("111");
                outputLine = inputLine.replaceAll("\\s+or\\s+", "/");
                inputLine = outputLine;
                /*
                 * if our sentence contains both 'or' and 'and' keywords we have to check for both occurrences.
                 * Since we perform replacements of these keywords sequentially, we have to update
                 * the 'inputFile' variable after we replace 'or's with '/' so that when we replace 'and' with '&',
                 * we wouldn't lose the backslashes we previously replaced with 'or'
                */
                outputLine = inputLine.replaceAll("\\sand\\s", "&");
            }

            else if (inputLine.matches(".*said\\s+.*$")) { // "hello" said john
                System.out.println("0");
                outputLine = inputLine.replaceAll("(.*)\\s+(said\\s+.*$)", "\"$1\" $2");
            }
            else if (inputLine.matches("^(\\s*\\w+)+\\W{2,}$")) { // quick brown fox;!.: --> quick brown fox.
                System.out.println("1");
                outputLine = inputLine.replaceAll("\\W+$", ".");
            }
            else if (inputLine.matches(".*\\w+(\\d)\\1+\\D.*")) {  // Mary666st --> MaryVIst
                System.out.println("2");
                Pattern pattern = Pattern.compile("(\\d)\\1+");
                Matcher matcher = pattern.matcher(inputLine);
                if (matcher.find()) {
                    int matchedInt = Integer.parseInt(matcher.group(1));
                    outputLine = inputLine.replaceFirst("(\\d)\\1+", digitToChar(matchedInt));
                }
            }

            else if (inputLine.matches(".*\\w+(lk|ch).*")) { // talk --> talked
                System.out.println("3");
                outputLine = inputLine.replaceAll("(\\w+)(lk|ch)", "$1$2ed");
            }
            else if (inputLine.matches("\\w+[eg|ag]$")) { // drag --> dragged
                System.out.println("4");
                outputLine = inputLine.replaceAll("(eg|ag)$", "$1ged");
            }
            else if (inputLine.matches(".*'s\\s+.*")) { // the swimsuit of Mary
                System.out.println("5");
                outputLine = inputLine.replaceAll("(.*)'s\\s+(.*)", "the $2 of $1");
            }
            else if (inputLine.matches(".*\\(\\d+\\)(\\d+|\\s+)+.*")) { // (216) 5671239 --> 216-5671239
                System.out.println("6");
                // drop the spaces between numbers
                inputLine = inputLine.replaceAll("(\\d)\\s+(\\d)", "$1$2");
                outputLine = inputLine.replaceAll("\\((\\d+)\\)\\s*(\\d+)", "$1-$2");
            }

            else if (inputLine.matches("^\\W+\\w+\\W+$")) { // !!!cats;;:) --> cats!!!;;:)
                System.out.println("7");
                outputLine = inputLine.replaceAll("(\\W+)(\\w+)(\\W+)", "$2$1$3");
            }

            else if (inputLine.matches(".*\\b([a-z]*[A-Z][a-z]*)+\\b.*")){
                System.out.println("8");
                outputLine = inputLine.replaceFirst("^(\\w)", "\\L$1");
            }




            outputLine = outputLine + '\n';
            outputStream.write(outputLine.getBytes());
        }


    }

    /**
     *
     * @param num
     * @return Roman Literal equivalent of the given number
     */
    /*public static String intToRoman(int num){
        assert num > 0: "A number which is less than or equal to 0 cannot be converted to roman literals!";
        StringBuilder stringBuilder = new StringBuilder();
        int t = 1000, y = 100;
        int thousands = num / 1000;
        for (int i = 0; i < thousands; i++)
            stringBuilder.append("M");
        while (t != 1){
            int temp = (num % t) / y;
            if (temp == 4){
                stringBuilder.append(intToChar(y));
                stringBuilder.append(intToChar(5 * y));
            }
            else if (temp == 9){
                stringBuilder.append(intToChar(y));
                stringBuilder.append(intToChar(10 * y));
                ;
            }
            else if (temp >= 5){
                stringBuilder.append(intToChar(5 * y));
                for (int i = 0; i < temp - 5; i++)
                    stringBuilder.append(intToChar(y));
            }
            else {
                for (int i = 0; i < temp; i++)
                    stringBuilder.append(intToChar(y));
            }
            t /= 10;
            y /= 10;

        }
        return stringBuilder.toString();
    }

    /**
     *
     * @param num
     * @return The character if the given number has a single character representation in Roman number systems.
     * For 1, return I; for 5, return V; for 10 return X etc.
     */
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
        return null;
    }
}
