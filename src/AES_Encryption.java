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
			byte[] plainTextHex = {(byte)0x32, (byte)0x43, (byte)0xf6, (byte)0xa8, (byte)0x88, (byte)0x5a, (byte)0x30, (byte)0x8d, (byte)0x31, (byte)0x31, (byte)0x98, (byte)0xa2, (byte)0xe0, (byte)0x37, (byte)0x07, (byte)0x34};//DatatypeConverter.parseHexBinary(DatatypeConverter.printHexBinary(plainText.getBytes("US-ASCII")));
			int character = 0;
			
			for (int column = 0; column < 4; column++){
				for (int row = 0; row < 4; row++){
					state[row][column] = plainTextHex[character];
					character++;
				}
			}
			
			// Convert keyString to 4x4 array
			byte[][] key = new byte[4][4];
			byte[] keyHex = {(byte)0x2b, (byte)0x7e, (byte)0x15, (byte)0x16, (byte)0x28 , (byte)0xae, (byte)0xd2, (byte)0xa6, (byte)0xab, (byte)0xf7, (byte)0x15, (byte)0x88, (byte)0x09, (byte)0xcf, (byte)0x4f, (byte)0x3c};//DatatypeConverter.parseHexBinary(DatatypeConverter.printHexBinary(keyString.getBytes("US-ASCII")));
			character = 0;
			
			for (int column = 0; column < 4; column++){
				for (int row = 0; row < 4; row++){
					key[row][column] = keyHex[character];
					character++;
				}
			}
			// Print text to encrypt and ascii hex equivalent
			System.out.println("\nEncryption Parameters:");
			System.out.println("Plaintext: " + plainText);
			System.out.println("ASCII Hex Conversion: " + DatatypeConverter.printHexBinary(plainTextHex));
			System.out.println("Key: " + keyString);
			System.out.println("ASCII Hex Conversion: " + DatatypeConverter.printHexBinary(keyHex));
			
			// Run AES 10 rounds
			state = KeyExpansion.add_round_key(state, key);
			
			for(int round = 1; round < 10; round++){
				System.out.println("\n********************");
				System.out.println("      ROUND  " + round + "      ");
				System.out.println("********************");
				state = SubBytes.sub_bytes(state);
				state = ShiftRows.shift_rows(state);
				state = MixColumns.mix_columns(state);
				state = KeyExpansion.add_round_key(state, KeyExpansion.key_expansion(key, round));
			}
			
			
			System.out.println("\n********************");
			System.out.println("      ROUND 10      ");
			System.out.println("********************");
			state = SubBytes.sub_bytes(state);
			state = ShiftRows.shift_rows(state);
			state = KeyExpansion.add_round_key(state, KeyExpansion.key_expansion(key, 10));
		
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
