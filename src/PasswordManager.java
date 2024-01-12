import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import javax.crypto.SecretKey;


public class PasswordManager extends PasswordBasedEncryptionDecryption {
	Scanner console = new Scanner(System.in);
	protected String strMasterPassword;
	protected String strPathToPassword = "./.KeyKeepPassword.txt";
	protected String strPathToCredentials = "./.KeyKeepCredentials.txt";
	protected String strPathtToEncryptedCredentials = "./.KeyKeepCredentials.txt.cpt";
	protected boolean boolExitProgram = false;

	void welcome() {
		System.out.println("░█░█░█▀▀░█░█░█░█░█▀▀░█▀▀░█▀█");
		System.out.println("░█▀▄░█▀▀░░█░░█▀▄░█▀▀░█▀▀░█▀▀");
		System.out.println("░▀░▀░▀▀▀░░▀░░▀░▀░▀▀▀░▀▀▀░▀░░");
	}

	protected String getMasterPassword() {
		System.out.print("Enter master password: ");
		this.strMasterPassword = console.nextLine();
		System.out.print("Confirm master password: ");

		if (this.strMasterPassword.equals(console.nextLine())) {
			return this.strMasterPassword;
		} else {
			System.out.print("Passwords do not match. Please try again.\n\n");
			this.getMasterPassword();
		}

		return this.strMasterPassword;
	}

	protected boolean authenticate(String strMasterPasswordToAuth) {
		try {
			FileReader fReader = new FileReader(this.strPathToPassword);
			Scanner fScanner = new Scanner(fReader);
			String strMasterPasswordHashInFile = "";
			String strSalt = "";

			if (!fScanner.hasNextLine()) {
				System.out.println("There is no master password in the password file.");
				fReader.close();
				fScanner.close();
				System.exit(1);
			} else {
				strMasterPasswordHashInFile = fScanner.nextLine();
				strSalt = fScanner.nextLine();
			}

			String strKey = this.encode(this.generateKeySha512(strMasterPasswordToAuth, this.decode(strSalt)).getEncoded());

			if (strKey.equals(strMasterPasswordHashInFile)) {
				fReader.close();
				fScanner.close();
				this.strMasterPassword = strMasterPasswordToAuth;
				return true;
			}

			fReader.close();
			fScanner.close();
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the authenticate method.");
		}

		return false;
	}

	protected void promptOptions() {
		System.out.println();
		System.out.println("Options: List accounts [ls], View password [vp platform_name email], Append an account [a platform_name email password], Remove an account [rm platform_name email], Update an account [up platform_name email password], Generate password (Traditional) [gpt], Generate password (passphrase) [gpp], Exit program [x]");
		System.out.print(": ");
		String strUserInput = console.nextLine();
		String[] strArrUserInput = strUserInput.split("\\s");
		System.out.println();

		if (strUserInput.equals("ls")) {
			if (strArrUserInput.length != 1) {
				System.out.println("Improper use of the program.");
			} else {
				this.listAccounts();
			}
		} else if (strUserInput.startsWith("vp")) {
			if (strArrUserInput.length != 3) {
				System.out.println("Improper use of the program.");
			} else {
				try {
					this.viewPassword(strArrUserInput[1], strArrUserInput[2]);
				} catch (Exception e) {
					System.err.println(e + ": Improper use of the program.");
				}
			}

		} else if (strUserInput.startsWith("a")) {
			if (strArrUserInput.length != 4) {
				System.out.println("Improper use of the program.");
			} else {
				try {
					this.appendAccount(strArrUserInput[1], strArrUserInput[2], strArrUserInput[3]);
				} catch (Exception e) {
					System.err.println(e + ": Improper use of the program.");
				}
			}
		} else if (strUserInput.startsWith("rm")) {
			if (strArrUserInput.length != 3) {
				System.out.println("Improper use of the program.");
			} else {
				try {
					this.updateAccount(strArrUserInput[1], strArrUserInput[2]);
				} catch (Exception e) {
					System.err.println(e + ": Improper use of the program.");
				}
			}
		} else if (strUserInput.startsWith("up")) {
			if (strArrUserInput.length != 4) {
				System.out.println("Improper use of the program.");
			} else {
				try {
					this.updateAccount(strArrUserInput[1], strArrUserInput[2], strArrUserInput[3]);
				} catch (Exception e) {
					System.err.println(e + ": Improper use of the program.");
				}
			}
		} else if (strUserInput.equals("gpt")) {
			if (strArrUserInput.length != 1) {
				System.out.println("Improper use of the program.");
			} else {
				try {
					TraditionalPasswordGenerator tPasswordGenerator = new TraditionalPasswordGenerator();
					tPasswordGenerator.mainGeneratePassword();
				} catch (Exception e) {
					System.err.println(e + ": Improper use of the program.");
				}
			}
		} else if (strUserInput.equals("gpp")) {
			if (strArrUserInput.length != 1) {
				System.out.println("Improper use of the program.");
			} else {
				try {
					PassphraseGeneratorV2 pGeneratorV2 = new PassphraseGeneratorV2();
					pGeneratorV2.mainGeneratePassword();
				} catch (Exception e) {
					System.err.println(e + ": Improper use of the program.");
				}
			}
		} else if (strUserInput.startsWith("x")) {
			if (strArrUserInput.length != 1) {
				System.out.println("Improper use of the program.");
			} else {
				try {
					this.exitProgram();
				} catch (Exception e) {
					System.err.println(e + ": Improper use of the program.");
				}
			}
		} else {
			System.err.println("Improper use of the program.");
		}
	}

	protected void listAccounts() {
		try {
			FileReader fReader = new FileReader(this.strPathToCredentials);
			Scanner fScanner = new Scanner(fReader);

			while (fScanner.hasNextLine()) {
				String strBuffer = fScanner.nextLine();

				if (strBuffer.startsWith("Password: ")) {
					continue;
				}

				System.out.println(strBuffer);
			}

			fReader.close();
			fScanner.close();
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the listAccounts method.");
		}
	}

	protected void viewPassword(String strPlatform, String strAccountName) {
		try {
			FileReader fReader = new FileReader(this.strPathToCredentials);
			Scanner fScanner = new Scanner(fReader);

			if (!fScanner.hasNextLine()) {
				System.out.println("There is no account created yet.");
			} else {
				boolean boolAccountFound = false;

				while (fScanner.hasNextLine()) {
					String strBuffer = fScanner.nextLine();

					if (strBuffer.equals("Platform: " + strPlatform)) {
						while (fScanner.hasNextLine()) {
							strBuffer = fScanner.nextLine();

							if (strBuffer.equals("Email: " + strAccountName)) {
								strBuffer = fScanner.nextLine();
								System.out.println(strBuffer);
								boolAccountFound = true;
								break;
							}
						}

						break;
					}
				}

				if (!boolAccountFound) {
					System.out.println("Account not found.");
				}

				fReader.close();
				fScanner.close();
			}
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the viewPassword method.");
		}
	}

	protected void appendAccount(String strPlatform, String strAccountName, String strAccountPassword) {
		try {
			FileReader fReader = new FileReader(this.strPathToCredentials);
			Scanner fScanner = new Scanner(fReader);
			boolean boolAccountFound = false;
			boolean boolPlatformFound = false;

			// Check if the account already exists
			while (fScanner.hasNextLine()) {
				String strBuffer = fScanner.nextLine();

				if (strBuffer.equals("Platform: " + strPlatform)) {
					while (fScanner.hasNextLine()) {
						strBuffer = fScanner.nextLine();

						if (strBuffer.startsWith("Platform: ") && !strBuffer.equals("Platform: " + strPlatform)) {
							break;
						}

						if (strBuffer.equals("Email: " + strAccountName)) {
							boolAccountFound = true;
						}
					}

					boolPlatformFound = true;
					break;
				}
			}

			fReader.close();
			fScanner.close();
			fReader = new FileReader(this.strPathToCredentials);
			fScanner = new Scanner(fReader);
			String strContent = "";

			if (boolAccountFound) {
				System.out.println("Account already exists");
			} else {
				if (fScanner.hasNextLine()) {
					if (boolPlatformFound) {
						while (fScanner.hasNextLine()) {
							String strBuffer = fScanner.nextLine();

							if (strContent.equals("")) {
								strContent = strBuffer;
							} else {
								strContent += "\n" + strBuffer;
							}

							if (strBuffer.equals("Platform: " + strPlatform)) {
								strContent += "\nEmail: " + strAccountName;
								strContent += "\nPassword: " + strAccountPassword;
							}
						}

						FileWriter fWriter = new FileWriter(this.strPathToCredentials);
						fWriter.append(strContent);
						fWriter.close();
					} else {
						strContent = "Platform: " + strPlatform + "\nEmail: " + strAccountName + "\nPassword: " + strAccountPassword;

						while (fScanner.hasNextLine()) {
							String strBuffer = fScanner.nextLine();
							strContent += "\n" + strBuffer;
						}

						FileWriter fWriter = new FileWriter(this.strPathToCredentials);
						fWriter.append(strContent);
						fWriter.close();
					}
				} else {
					strContent = "Platform: " + strPlatform + "\nEmail: " + strAccountName + "\nPassword: " + strAccountPassword;
					FileWriter fWriter = new FileWriter(this.strPathToCredentials);
					fWriter.append(strContent);
					fWriter.close();
				}
			}

			fReader.close();
			fScanner.close();
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the appendAccount method.");
		}
	}

	protected void updateAccount(String strPlatform, String strAccountName) {
		try {
			FileReader fReader = new FileReader(this.strPathToCredentials);
			Scanner fScanner = new Scanner(fReader);

			if (!fScanner.hasNextLine()) {
				System.out.println("There is no account created yet.");
			} else {
				// Check if the entry exists
				boolean boolAccountFound = false;
				boolean boolMultipleAccount = false;

				while (fScanner.hasNextLine()) {
					String strBuffer = fScanner.nextLine();

					if (strBuffer.equals("Platform: " + strPlatform)) {
						while (fScanner.hasNextLine()) {
							strBuffer = fScanner.nextLine();

							if (strBuffer.startsWith("Platform: ") && !strBuffer.equals("Platform: " + strPlatform)) {
								break;
							}

							if (strBuffer.equals("Email: " + strAccountName)) {
								boolAccountFound = true;
							} else if (strBuffer.startsWith("Email: ") && !strBuffer.equals("Email: " + strAccountName)) {
								boolMultipleAccount = true;
							}
						}

						break;
					}
				}

				fReader.close();
				fScanner.close();
				fReader = new FileReader(this.strPathToCredentials);
				fScanner = new Scanner(fReader);

				if (!boolAccountFound) {
					System.out.println("Account does not exists.");
				} else {
					String strContent = "";

					while (fScanner.hasNextLine()) {
						String strBuffer = fScanner.nextLine();

						if (strBuffer.equals("Platform: " + strPlatform)) {
							if (boolMultipleAccount) {
								if (strContent.equals("")) {
									strContent = strBuffer;
								} else {
									strContent += "\n" + strBuffer;
								}

								while (fScanner.hasNextLine()) {
									strBuffer = fScanner.nextLine();

									if (strBuffer.startsWith("Platform: ") && !strBuffer.equals("Platform: " + strPlatform)) {
										strContent += "\n" + strBuffer;
										break;
									}

									if (strBuffer.equals("Email: " + strAccountName)) {
										strBuffer = fScanner.nextLine();
									} else {
										strContent += "\n" + strBuffer;
									}
								}
							} else {
								strBuffer = fScanner.nextLine();
								strBuffer = fScanner.nextLine();
							}
						} else {
							if (strContent.equals("")) {
								strContent = strBuffer;
							} else {
								strContent += "\n" + strBuffer;
							}
						}
					}

					FileWriter fWriter = new FileWriter(this.strPathToCredentials);
					fWriter.append(strContent);
					fWriter.close();
					fScanner.close();
				}
			}

		} catch (Exception e) {
			System.err.println(e + ": There was an error in the updateAccount method.");
		}
	}

	protected void updateAccount(String strPlatform, String strAccountName, String strAccountPassword) {
		try {
			FileReader fReader = new FileReader(this.strPathToCredentials);
			Scanner fScanner = new Scanner(fReader);

			// Check if there are accounts 
			if (!fScanner.hasNextLine()) {
				System.out.println("There is no account created yet.");
			} else {
				// Check if account exists and change password
				String strContent = "";
				boolean boolAccountFound = false;

				while (fScanner.hasNextLine()) {
					String strBuffer = fScanner.nextLine();

					if (strContent.equals("")) {
						strContent = strBuffer;
					} else {
						strContent += "\n" + strBuffer;
					}

					if (strBuffer.equals("Platform: " + strPlatform)) {
						while (fScanner.hasNextLine()) {
							strBuffer = fScanner.nextLine();

							if (strBuffer.startsWith("Platform: ") && !strBuffer.equals("Platform: " + strPlatform)) {
								strContent += "\n" + strBuffer;
								break;
							}

							if (strBuffer.equals("Email: " + strAccountName)) {
								boolAccountFound = true;
								strContent += "\n" + strBuffer;
								strBuffer = fScanner.nextLine();
								strContent += "\nPassword: " + strAccountPassword;
							} else {
								strContent += "\n" + strBuffer;
							}
						}
					}
				}

				if (!boolAccountFound) {
					System.out.println("Account not found.");
				} else {
					FileWriter fWriter = new FileWriter(this.strPathToCredentials);
					fWriter.append(strContent);
					fWriter.close();
				}

				fReader.close();
				fScanner.close();
			}
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the updateAccount method.");
		}
	}

	protected void exitProgram() {
		this.boolExitProgram = true;
		System.exit(0);
	}

	protected void encryptCredentials() {
		try {
			FileReader fReaderCredentials = new FileReader(this.strPathToCredentials);
			FileReader fReaderPassword = new FileReader(this.strPathToPassword);
			Scanner sCredentials = new Scanner(fReaderCredentials);
			Scanner sPassword = new Scanner(fReaderPassword);
			String strContentCredentials = "";
			String strContentPassword = "";

			while (sCredentials.hasNextLine()) {
				if (strContentCredentials.equals("")) {
					strContentCredentials = sCredentials.nextLine();
				} else {
					strContentCredentials += "\n" + sCredentials.nextLine();
				}
			}

			strContentPassword = sPassword.nextLine() + "\n" + sPassword.nextLine();
			byte[] byteArrSalt = this.generateSalt();
			SecretKey sKey = this.generateKeySha256(strMasterPassword, byteArrSalt);
			byte[] byteArrIv = this.generateRandomIV();

			String strEncryptedCredentials = this.encrypt(strContentCredentials, byteArrIv, sKey);
			strContentPassword += "\n" + this.encode(byteArrSalt) + "\n" + this.encode(byteArrIv);
			FileWriter fWriterCredentials = new FileWriter(this.strPathToCredentials);
			FileWriter fWriterPassword = new FileWriter(this.strPathToPassword);
			File fEncryptedCredentials = new File(this.strPathtToEncryptedCredentials);
			File fCredentials = new File(this.strPathToCredentials);
			fCredentials.renameTo(fEncryptedCredentials);
			fWriterCredentials.append(strEncryptedCredentials);
			fWriterPassword.append(strContentPassword);
			fReaderCredentials.close();
			fReaderPassword.close();
			sCredentials.close();
			sPassword.close();
			fWriterCredentials.close();
			fWriterPassword.close();
			System.out.println();
			System.out.println("File encrypted successfully.");
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the exitProgram method.");
		}

		System.out.println("Exiting KeyKeep...");
	}
}