import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class PassphraseGeneratorV2 {

	public void mainGeneratePassword() {

		String filePath = "dictionary.txt";
		String[] PassphraseWords = readDictionary(filePath);
		String[] PassphraseNumbers = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
		};
		String[] PassphraseCharacters = {
			".", ",", "!", "@", "#", "$", "%", "&"
		};
		String[] PassphraseDelimiters = {
			"~", "*", "-", "+", "="
		};

		Scanner scanner = new Scanner(System.in);
		Character continueChoice;
		String combinationChoice;
		int numChoice;

		System.out.println("\nWELCOME TO RANDOMIZER");

		do {
			System.out.print("\n\nDo you want to generate a Passphrase? [Y/N] :");
			continueChoice = scanner.next().charAt(0);
			continueChoice = Character.toUpperCase(continueChoice);

			if (continueChoice == 'Y') {

				do {
					System.out.println("\n============================ Select a Combination ============================\n");
					System.out.println("[A] Passphrase Words");
					System.out.println("[B] Numbers");
					System.out.println("[C] Special Character");
					System.out.println("[D] Delimiters");
					System.out.print("\nEnter [ABCD] : ");
					combinationChoice = scanner.next().toUpperCase();

					if (inputChecker(combinationChoice) == true) {

						do {
							System.out.print("\nHow many words do you want to generate? [6 - 10 - 14 - 18] : ");
							numChoice = scanner.nextInt();

							if (inputChecker(numChoice) == true) {

								int[] combinationValues = new int[combinationChoice.length()];
								int[] combinationRandomizedIndex = new int[numChoice];
								int[] passphraseRandomizedIndex = new int[numChoice];
								int delimiterRandomizedIndex = -1;
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

									if (!Arrays.asList(combinationValues).contains(4)) {
										delimiterRandomizedIndex = getRandomIndex(PassphraseDelimiters);
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

									if (combinationChoice.contains("D")) { //KAPAG PINILI YUNG DELIMITER. MAY RANDOM SYMBOL NA ISISINGIT SA PAGITAN NG MGA WORDS. PAG LAST WORD, HINDI NA LALAGYAN
										if (i != (numChoice - 1)) {
											generatedPassphrase.append(PassphraseDelimiters[delimiterRandomizedIndex]);
										}
									} else if (!combinationChoice.contains("D")) { //KAPAG WALANG DELIMITER, SPACE LANG ILALAGAY. PERO NAG-AAPPLLY LANG IF WORDS LANG PINILI (A).
										if (combinationChoice.length() == 1 && combinationChoice.contains("A"))
											generatedPassphrase.append(" ");
									}
								}

								//MAGKAKAROON LANG NG SPACE IF (A) LANG YUNG PINILI
								//WALANG SPACE IF MAY INCLUDED NUMS AND SYMBOLS (ABC)
								//PERO IF PRESENT YUNG DELIMITER (D) SA CHOICE, LALAGYAN KADA SPACE
								System.out.println("\nGENERATED PASSPHRASE: \t" + generatedPassphrase);
								System.out.println("\n==============================================================================");

							}

						} while (inputChecker(numChoice) != true);

					} else if (inputChecker(combinationChoice) == false) {
						System.out.println("Invalid Combination Input");
					}

				} while (inputChecker(combinationChoice) != true);

			} else if (continueChoice == 'N') {
				System.out.println("Thank you!");
			} else {
				System.out.println("Invalid Input!");
			}

		} while (continueChoice != 'N');

	}

	public static Boolean inputChecker(String combinationChoice) {
		Boolean ans = false;

		if (combinationChoice.length() == 1 && combinationChoice.contains("D")) {
			ans = false;
		} else if (combinationChoice.matches("[ABCD]+")) {
			ans = true;
		}

		return ans;
	}

	public static Boolean inputChecker(int numChoice) {
		Boolean ans = false;

		if (numChoice == 6 || numChoice == 10 || numChoice == 14 || numChoice == 18) {
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

			case 'D':
				ans = 4;
				break;
		}

		return ans;
	}

	public static int getRandomIndex(int[] combinationValues) {

		Random random = new Random();
		int randomIndex;

		do {
			randomIndex = random.nextInt(combinationValues.length);
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