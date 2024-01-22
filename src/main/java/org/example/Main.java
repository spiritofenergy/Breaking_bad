package org.example;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        int size = 30;
        String[] quotes = new String[size];
        for (int i = 0; i < quotes.length; i++) {
            quotes[i] = "";}

        int counter = 0;
        for (int i = 0; i < quotes.length; i++){
            // Получаем строку
            String quote = getString();

            String onlyOneQuote = searchD(quotes, i, quote);
            String cleanWord =  extractedBadWord(onlyOneQuote);

            counter++;
            System.out.println(counter + " " + cleanWord);
            writerFileString(cleanWord);

        }
    }
    private static String searchD(String[] quotes, int i, String quote) {
        for (int y = 1; y < quotes.length; y++) {
            String existingQuote = Arrays.toString(quotes);
            if (existingQuote.equals(quote)) {
                 System.out.println("Уже имеется такая фраза");
            }else {
                quotes[i] = quote;
            }
        }
       // System.out.println(quotes[i]);
        return quote;
    }

    private static String extractedBadWord(String onlyOneQuote) {
        String[] badWords = new String[]{"Bitch", "bitch", "marijuana", "butts", "fucking", "can go f*ck yourself", "douchebag", "Douchebags", "Shit"};
        String cleanWords = "";
        for (int x = 0; x < badWords.length; x++){
            if(!onlyOneQuote.contains(badWords[x])){
             cleanWords = onlyOneQuote;
            }else if (onlyOneQuote.contains(badWords[x])){
                cleanWords = onlyOneQuote.replace(badWords[x], "***");
                break;}}
        return cleanWords;
    }
    private static void writerFileString(String cleanWord) throws IOException {
        BufferedWriter write = new BufferedWriter(new FileWriter("E:\\Sinergy\\Breaking_bad\\src\\main\\java\\org\\example\\text.txt", true));
        write.append(cleanWord + "\n");
        write.close();
    }
  /*  private static void writerFileString(String cleanWord) throws IOException {
        System.out.println("writerFileString " +cleanWord);
        BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\Sinergy\\Breaking_bad\\src\\main\\java\\org\\example\\text.txt", true));
        writer.append(cleanWord + "\n");
        writer.close();
    }*/
    private static String downloadWEbPage (String url) throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        URLConnection urlConnection = new URL(url).openConnection();
        try (InputStream is = urlConnection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }
    private static String getString() throws IOException {
        String page = downloadWEbPage("https://api.breakingbadquotes.xyz/v1/quotes");
        int quoteStart = page.lastIndexOf("quote");
        int quoteEnd = page.indexOf("author");
        int author_4 = page.indexOf("\"}]");
        String quote = page.substring(quoteStart + 7, quoteEnd - 2);
        String author = page.substring(quoteEnd + 9, author_4);
        // String strShortQuote = page.substring(quote4 + 7, quote4 + 50);
        return quote;
    }

}