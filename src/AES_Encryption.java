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
		
		// Read in state input
		System.out.println("Please enter the plaintext to encrypt (Length = 16): ");
		String plainText = sc.nextLine();
		
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
			
			// Print text to encrypt and ascii hex equivalent
			System.out.println("\nText to Encrypt:");
			System.out.println("Plaintext: " + plainText);
			System.out.println("ASCII Hex Conversion: " + DatatypeConverter.printHexBinary(plainTextHex));
			
			// Run AES 10 rounds
			state = KeyExpansion.add_roundKey(state, 0);
			
			for(int round = 1; round < 10; round++){
				System.out.println("\n********************");
				System.out.println("      ROUND  " + round + "      ");
				System.out.println("********************");
				state = SubBytes.sub_bytes(state);
				state = ShiftRows.shift_rows(state);
				state = MixColumns.mix_columns(state);
				state = KeyExpansion.add_roundKey(state, round);
			}
			
			
			System.out.println("\n********************");
			System.out.println("      ROUND 10      ");
			System.out.println("********************");
			state = SubBytes.sub_bytes(state);
			state = ShiftRows.shift_rows(state);
			state = KeyExpansion.add_roundKey(state, 10);
		
			// Convert encrypted state array back to string
			byte[] encryptedTextHex = new byte[16];
			character = 0;
			
			for (int column = 0; column < 4; column++){
				for (int row = 0; row < 4; row++){
					encryptedTextHex[character] = state[row][column];
					character++;
				}
			}
			
			String encryptedText = new String(encryptedTextHex, "US-ASCII");
			
			// Print out encrypted text
			System.out.println("\nEncrypted Text:");
			System.out.println("ASCII Hex: " + DatatypeConverter.printHexBinary(encryptedTextHex));
			System.out.println("ASCII to String Value: " + encryptedText);
		}else{
			System.out.println("Error: the plaintext you entered was not of length 16.");
		}	
	}
}
