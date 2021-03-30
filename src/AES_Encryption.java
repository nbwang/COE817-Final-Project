// Import statements
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;

public class AES_Encryption {
	
	// Main method
	// Runs AES Encryption on input string (16 Bytes)
	public static void main(String[] args) throws UnsupportedEncodingException {
		// Create scanner class
		Scanner sc = new Scanner(System.in);
		
		// Read in state and key input
		System.out.println("Please enter the plaintext to encrypt (Length = 16): ");
		String plainText = sc.nextLine();
		System.out.println("\nPlease enter the key to encrypt with (Length = 16): ");
		String keyString = sc.nextLine();
		
		// Run encryption if plaintext length is 16
		if(plainText.length() == 16){
			
			// Convert plaintextstring to state array
			byte[][] state = new byte[4][4];
			byte[] plainTextHex = DatatypeConverter.parseHexBinary(DatatypeConverter.printHexBinary(plainText.getBytes("US-ASCII")));
			int character = 0;
			
			for (int column = 0; column < 4; column++){
				for (int row = 0; row < 4; row++){
					state[row][column] = plainTextHex[character];
					character++;
				}
			}
			
			// Convert keyString to 4x4 array
			byte[][] key = new byte[4][4];
			byte[] keyHex = DatatypeConverter.parseHexBinary(DatatypeConverter.printHexBinary(keyString.getBytes("US-ASCII")));
			character = 0;
			
			for (int column = 0; column < 4; column++){
				for (int row = 0; row < 4; row++){
					key[row][column] = keyHex[character];
					character++;
				}
			}
			// Print text to encrypt and ascii hex equivalent
			System.out.println("\n***************************");
			System.out.println("   Encryption Parameters   ");
			System.out.println("***************************");
			System.out.println("\nPlaintext: " + plainText);
			System.out.println("\nASCII Hex Conversion: " + DatatypeConverter.printHexBinary(plainTextHex));
			ConsoleOutput.printStateMatrix(state, "State Matrix");
			System.out.println("\nKey: " + keyString);
			System.out.println("\nASCII Hex Conversion: " + DatatypeConverter.printHexBinary(keyHex));
			ConsoleOutput.printStateMatrix(key, "Key Matrix");
			System.out.println("\n***************************");
			System.out.println("  Starting AES Encryption  ");
			System.out.println("***************************");
			
			// Run AES 10 rounds
			state = KeyExpansion.add_round_key(state, key);
			
			for(int round = 1; round < 10; round++){
				System.out.println("\n********************");
				System.out.println("      ROUND  " + round + "      ");
				System.out.println("********************");
				state = SubBytes.sub_bytes(state);
				state = ShiftRows.shift_rows(state);
				state = MixColumns.mix_columns(state);
				key = KeyExpansion.key_expansion(key, round);
				state = KeyExpansion.add_round_key(state, key);
			}
			
			
			System.out.println("\n********************");
			System.out.println("      ROUND 10      ");
			System.out.println("********************");
			state = SubBytes.sub_bytes(state);
			state = ShiftRows.shift_rows(state);
			key = KeyExpansion.key_expansion(key, 10);
			state = KeyExpansion.add_round_key(state, key);
		
			// Convert encrypted state array back to string
			byte[] encryptedTextHex = new byte[16];
			character = 0;
			
			for (int column = 0; column < 4; column++){
				for (int row = 0; row < 4; row++){
					encryptedTextHex[character] = state[row][column];
					character++;
				}
			}
			
			// Print out encrypted text
			System.out.println("\n********************");
			System.out.println("   Encrypted Text   ");
			System.out.println("********************");
			ConsoleOutput.printStateMatrix(state, "State Matrix:");
			System.out.println("\nASCII Hex: " + DatatypeConverter.printHexBinary(encryptedTextHex) + "\n");
		}else{
			System.out.println("Error: the plaintext you entered was not of length 16.");
		}	
	}
}
