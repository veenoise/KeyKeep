import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PassphraseGeneratorV2 {

    public String mainPassphraseGenerator(int intLen) {

        String filePath = "dictionary.txt";
        String[] PassphraseWords = readDictionary(filePath);
        String[] PassphraseNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] PassphraseCharacters = {".", ",", "!", "@", "#", "$", "%", "&"};

        
        String combinationChoice = "A";
        int numChoice;
        
        numChoice = intLen;
        int[] combinationValues = new int[combinationChoice.length()];
        int[] combinationRandomizedIndex = new int[numChoice];
        int[] passphraseRandomizedIndex = new int[numChoice];
        StringBuilder generatedPassphrase = new StringBuilder();

        for (int i = 0; i < combinationChoice.length(); i++) {
            combinationValues[i] = combinationDeterminer(combinationChoice.charAt(i));
        }

        for (int i = 0; i < combinationRandomizedIndex.length; i++) {
            combinationRandomizedIndex[i] = getRandomIndex(combinationValues);
        }
        
        for (int i = 0; i < combinationRandomizedIndex.length; i++) {
            switch (combinationRandomizedIndex[i]) {
                case 1:
                    passphraseRandomizedIndex[i] = getRandomIndex(PassphraseWords);
                    break;
                case 2:
                    passphraseRandomizedIndex[i] = getRandomIndex(PassphraseNumbers);
                    break;
                case 3:
                    passphraseRandomizedIndex[i] = getRandomIndex(PassphraseCharacters);
                    break;
            }

        } 
        
        for (int i = 0; i < numChoice; i++) {

            switch (combinationRandomizedIndex[i]) {
                case 1:
                    generatedPassphrase.append(PassphraseWords[passphraseRandomizedIndex[i]]);
                    break;
                case 2:
                    generatedPassphrase.append(PassphraseNumbers[passphraseRandomizedIndex[i]]);
                    break;
                case 3:
                    generatedPassphrase.append(PassphraseCharacters[passphraseRandomizedIndex[i]]);
                    break;
            }

            if (combinationChoice.contains("A")) {
                if (i != (numChoice-1)) {                                          
                generatedPassphrase.append("-");}
            }
            
            }

        System.out.println("\nGENERATED PASSPHRASE: \t" + generatedPassphrase);
        return generatedPassphrase.toString();        
  
    }

    public static Boolean inputChecker(String combinationChoice) {
        Boolean ans = false;

        if (combinationChoice.length()==1 && combinationChoice.contains("D")){
            ans = false;
        } 
        
        else if (combinationChoice.matches("[ABCD]+")){
            ans = true;
        }

        return ans;
    }

    public static Boolean inputChecker(int numChoice) {
        Boolean ans = false;
        if (numChoice >= 9 || numChoice <= 14) {
            ans = true;
        }

        return (ans);
    }

    public static int combinationDeterminer(char combinationChoice) {
        int ans = 0;

        switch (combinationChoice) {
            case 'A':
                ans = 1;
                break;
            
            case 'B':
                ans = 2;
                break;
                
            case 'C':
                ans = 3;
                break;
                
        }
        return ans;
    }

    public static int getRandomIndex(int[] combinationValues) {

        Random random = new Random();
        int randomIndex;
            do { randomIndex = random.nextInt(combinationValues.length);
            } while (combinationValues[randomIndex] == 4);

        return combinationValues[randomIndex];
    }

    public static int getRandomIndex(String[] passphraseCategory) {
        Random random = new Random();
        int randomIndex = random.nextInt(passphraseCategory.length);
        return randomIndex;
    }

    private static String[] readDictionary(String filePath) {
        String[] wordsArray = new String[0];
        try {
            Path path = Path.of(filePath);
            wordsArray = Files.readAllLines(path).toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsArray;
    }

}