import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

public class TraditionalPasswordGenerator {
    
    static Scanner console = new Scanner(System.in);
    protected static String strAnswer;
    protected static int intSize;
    protected static boolean blUseUppercase;
    protected static boolean blUseLowercase;
    protected static boolean blUsePunctuation;
    protected static boolean blUseDigit;
    private static final String strLowercaseList = "abcdefghijklmnopqrstuvwxyz";
    private static final String strUppercaseList = strLowercaseList.toUpperCase();
    private static final String strDigitList = "0123456789";                                                                                                                                                                        
    private static final String strPunctuationList = "!@#&()-[{}]:;',?/*~$^+=<>";
    private static final int intMaxLength = 20;

    private static final String strPasswordList =
            strLowercaseList + strUppercaseList + strDigitList + strPunctuationList;

    private static SecureRandom random = new SecureRandom();

    public static boolean answerChecker() {
        while (true) {
            String input = console.nextLine().trim();
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.print("Invalid input. Please enter 'Y' or 'N': ");
            }
        }
    }  

    public static int countChecker(int intInput) {
        if (intInput == 1) {
            return 1;
        } else if (intInput == 2) {
            return 4;
        } else if (intInput == 3 || intInput == 4) {
            return 2;
        } 

        return 0; // Default return statement
    }

    public static void main(String[] args) {

        boolean continueGenerating = true;

        while (continueGenerating) {    
            try {
                System.out.println("\nPassword Generator");
                System.out.print("Choose a password length between 8 and 20 characters: ");
                intSize = console.nextInt();
                while (intSize < 8 || intSize > 20) {
                    System.out.print("Invalid input. Please enter a number between 8 and 20: ");
                    intSize = console.nextInt();
                }

                //get the left out next line
                console.nextLine();

                System.out.println("\nChoose a password type:");
                System.out.println("1. Default");
                System.out.println("2. Customized");
                System.out.print("\nEnter the number of your choice: ");
                strAnswer = console.nextLine();

                while (!strAnswer.equals("1") && !strAnswer.equals("2")) {
                    System.out.println("Invalid input. Please enter 1 or 2: ");
                    strAnswer = console.nextLine();
                }

                if (strAnswer.equals("1")) {
                    System.out.println("\nYour password is: " + generateStrongPassword());
                } else {
                    do {
                        System.out.println("\nCustomize your password:");
                        System.out.print("Do you want uppercase letters? (Y/N): ");
                        blUseUppercase = answerChecker();
                        System.out.print("Do you want lowercase letters? (Y/N): ");
                        blUseLowercase = answerChecker();
                        System.out.print("Do you want numbers? (Y/N): ");
                        blUseDigit = answerChecker();
                        System.out.print("Do you want punctuations? (Y/N): ");
                        blUsePunctuation = answerChecker();
                        System.err.print("\n");

                        if (!(blUseUppercase || blUseLowercase || blUseDigit || blUsePunctuation)) {
                            System.out.print("Please select at least one option (Y/N): ");
                            console.nextLine();
                        }

                    } while (!blUseUppercase && !blUseLowercase && !blUseDigit && !blUsePunctuation);

                    //count how many true values
                    int intCount = 0;
                    if (blUseUppercase) {
                        intCount++;
                    }
                    if (blUseLowercase) {
                        intCount++;
                    }
                    if (blUseDigit) {
                        intCount++;
                    }
                    if (blUsePunctuation) {
                        intCount++;
                    }

                    if (blUseUppercase && blUseLowercase && blUseDigit && blUsePunctuation) {
                        System.out.println("\nYour password is: " + generateStrongPassword());
                    } else if (blUseUppercase && blUseLowercase && blUseDigit) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, true, true, true, false));
                    } else if (blUseUppercase && blUseLowercase && blUsePunctuation) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, true, true, false, true));
                    } else if (blUseUppercase && blUseDigit && blUsePunctuation) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, true, false, true, true));
                    } else if (blUseLowercase && blUseDigit && blUsePunctuation) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, false, true, true, true));
                    } else if (blUseUppercase && blUseLowercase) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, true, true, false, false));
                    } else if (blUseUppercase && blUseDigit) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, true, false, true, false));
                    } else if (blUseUppercase && blUsePunctuation) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, true, false, false, true));
                    } else if (blUseLowercase && blUseDigit) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, false, true, true, false));
                    } else if (blUseLowercase && blUsePunctuation) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, false, true, false, true));
                    } else if (blUseDigit && blUsePunctuation) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, false, false, true, true));
                    } else if (blUseUppercase) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, true, false, false, false));
                    } else if (blUseLowercase) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, false, true, false, false));
                    } else if (blUseDigit) {  
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, false, false, true, false));
                    } else if (blUsePunctuation) {
                        System.out.println("\nYour password is: " + generateStrongPassword(intCount, false, false, false, true));
                    }
            
                }
                
                System.out.print("\nDo you want to generate another password? (Y/N): ");
                char userChoice;
                while (true) {
                    try {
                        userChoice = Character.toUpperCase(console.next().charAt(0));
                
                        if (userChoice == 'Y') {
                            break;
                        } else if (userChoice == 'N') {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                            System.out.print("Do you want to generate another password? (Y/N): ");
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                        System.out.print("Do you want to generate another password? (Y/N): ");
                    }
                }   continueGenerating = (userChoice == 'Y');

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                console.nextLine();
            }
        }
    }

    public static String generateStrongPassword() {

        StringBuilder result = new StringBuilder(intMaxLength);

        // at least 2 chars (lowercase)
        String strLowerCase = generateRandomString(strLowercaseList, 2);
        result.append(strLowerCase);

        // at least 2 chars (uppercase)
        String strUppercaseCase = generateRandomString(strUppercaseList, 2);
        result.append(strUppercaseCase);

        // at least 2 digits
        String strDigit = generateRandomString(strDigitList, 2);
        result.append(strDigit);

        // at least 2 punctuations
        String strPunctuation = generateRandomString(strPunctuationList, 2);
        result.append(strPunctuation);

        if (intSize == 8) {
            String strPassword = result.toString();
            System.out.format("%-20s: %s%n", "Unshuffled Password", shuffleString(strPassword));
            System.out.format("%-20s: %s%n%n", "Password Length", strPassword.length());
            return strPassword;
        }

        // remaining, just random
        String strOther = generateRandomString(strPasswordList, intSize - 8);
        result.append(strOther);

        String strPassword = result.toString();

        // shuffle
        System.out.format("%-20s: %s%n", "Unshuffled Password", shuffleString(strPassword));
        System.out.format("%-20s: %s%n%n", "Password Length", strPassword.length());
        return strPassword;
    }

    public static String generateStrongPassword(int intCount, boolean blUppercase, boolean blLowercase, boolean blDigit, boolean blPunctuation) {

        StringBuilder result = new StringBuilder(intMaxLength);
        String strPasswordChoice = "";

        if (blUppercase) {
            String strUppercaseCase = generateRandomString(strUppercaseList, countChecker(intCount));
            strPasswordChoice = strUppercaseList;
            result.append(strUppercaseCase);
        }

        if (blLowercase) {
            String strLowerCase = generateRandomString(strLowercaseList, countChecker(intCount));
            strPasswordChoice = strPasswordChoice + strLowercaseList;
            result.append(strLowerCase);
        }

        if (blDigit) {
            String strDigit = generateRandomString(strDigitList, countChecker(intCount));
            strPasswordChoice = strPasswordChoice + strDigitList;
            result.append(strDigit);
        }

        if (blPunctuation) {
            String strPunctuation = generateRandomString(strPunctuationList, countChecker(intCount));
            strPasswordChoice = strPasswordChoice + strPunctuationList;
            result.append(strPunctuation);
        }

        if (intSize == 8 && result.length() == 8) {
            String strPassword = result.toString();
            System.out.format("%-20s: %s%n", "Unshuffled Password", shuffleString(strPassword));
            System.out.format("%-20s: %s%n%n", "Password Length", strPassword.length());
            return strPassword;
        }

        // remaining, just random
        String strOther = generateRandomString(strPasswordChoice, intSize - result.length());
        result.append(strOther);

        String strPassword = result.toString();

        // shuffle
        System.out.format("%-20s: %s%n", "Unshuffled Password", shuffleString(strPassword));
        System.out.format("%-20s: %s%n%n", "Password Length", strPassword.length());
        return strPassword;
    }

    // generate a random char[], based on `input`
    private static String generateRandomString(String input, int size) {

        if (input == null || input.length() <= 0) 
            throw new IllegalArgumentException("Invalid input.");
        if (size < 1) throw new IllegalArgumentException("Invalid size.");

        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            // produce a random order
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }

    // for final password, make it more random
    public static String shuffleString(String input) {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);
        // java 8
        return result.stream().collect(Collectors.joining());
    }

}
