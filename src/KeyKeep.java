import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.swing.JOptionPane;


public class KeyKeep {

	public static void main(String[] args) {
		PasswordManager pManager = new PasswordManager();
		Scanner console = new Scanner(System.in);

		// Print program name
		pManager.welcome();

		// Runtime catcher: call the exitProgram to encrypt file even if forcibly closed
		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {
				try {
					Thread.sleep(200);
					File credentialFile = new File(pManager.strPathToCredentials);

					if (credentialFile.exists()) {
						pManager.encryptCredentials();
					}

				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		});

		File passFile = new File(pManager.strPathToPassword);
		File credentialFile = new File(pManager.strPathToCredentials);
		File credentialFileEncrypted = new File(pManager.strPathtToEncryptedCredentials);

		// If master account already exists
		if (passFile.exists()) {

			String strMasterPasswordToAuth = "";

			do {
				System.out.print("Enter master password: ");
				strMasterPasswordToAuth = JOptionPane.showInputDialog(null, "Enter Master Password", "KeyKeep", JOptionPane.WARNING_MESSAGE);
			} while (!pManager.authenticate(strMasterPasswordToAuth));

			if (credentialFileEncrypted.exists()) {
				try {
					credentialFileEncrypted.renameTo(credentialFile);
					FileReader fReaderPassword = new FileReader(pManager.strPathToPassword);
					FileReader fReaderCredentials = new FileReader(pManager.strPathToCredentials);
					Scanner sPassword = new Scanner(fReaderPassword);
					Scanner sCredentials = new Scanner(fReaderCredentials);

					String strEncryptedCredentials = sCredentials.nextLine();
					sPassword.nextLine();
					sPassword.nextLine();
					byte[] byteArrSalt = pManager.decode(sPassword.nextLine());
					byte[] byteArrIv = pManager.decode(sPassword.nextLine());
					SecretKey sKey = pManager.generateKeySha256(strMasterPasswordToAuth, byteArrSalt);
					String strDecryptedCredentials = pManager.decrypt(strEncryptedCredentials, byteArrIv, sKey);

					FileWriter fWriterCredentials = new FileWriter(pManager.strPathToCredentials);
					fWriterCredentials.append(strDecryptedCredentials);

					fReaderPassword.close();
					fReaderCredentials.close();
					sPassword.close();
					sCredentials.close();
					fWriterCredentials.close();
				} catch (Exception e) {
					System.err.println(e + ": Decrypting process failed.");
				}
			}

			KeyKeepGui kkGui = new KeyKeepGui();
			kkGui.gui();


			// while (!pManager.boolExitProgram) {
			// 	pManager.promptOptions();
			// }

		} else {
			pManager.getMasterPassword();

			try {
				passFile.createNewFile();
				credentialFile.createNewFile();
				FileWriter fWriterPassWrite = new FileWriter(pManager.strPathToPassword);
				byte[] byteArrSalt = pManager.generateSalt();
				String strContent = pManager.encode(pManager.generateKeySha512(pManager.strMasterPassword, byteArrSalt).getEncoded());
				strContent += "\n" + pManager.encode(byteArrSalt);
				fWriterPassWrite.write(strContent);
				fWriterPassWrite.close();


				KeyKeepGui kkGui = new KeyKeepGui();
				kkGui.gui();

				// while (!pManager.boolExitProgram) {
				// 	pManager.promptOptions();
				// }
			} catch (Exception e) {
				System.err.println(e + ": Encountered an error in creating files.");
				System.out.println("Exiting...");
				System.exit(1);
			}
		}

		console.close();
	}
}