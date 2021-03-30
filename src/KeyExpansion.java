//Import statements
import javax.xml.bind.DatatypeConverter;

public class KeyExpansion {
	
	// AES Add Round Key method
	public static byte[][] add_round_key(byte[][] state_in, byte[][] key_in){
		byte[][] state = new byte[4][4];
		
		// XOR each cell in the two 4x4 matrices
		for(int row = 0; row < 4; row++){
			for(int column = 0; column < 4; column++){
				state[row][column] = (byte) (state_in[row][column] ^ key_in[row][column]);
			}
		}
		
		// Print the state matrix
		printStateMatrix(state);
		
		// return state
		return state;
	}
	
	// AES Key Expansion method
	public static byte[][] key_expansion(byte[][] state_in, int roundNumber){
		byte[][] state = new byte[4][4];
		byte[] word = new byte[4];
		byte[] tempWord = new byte[4];
		String[] wordString = new String[4];
		byte[] roundByte = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
		byte temp;
		int row = 0;
		int column = 0;

		for(; row < 4; row++){
			word[row] = state_in[row][3];
		}

		temp = word[0];
        word[0] = word[1];
        word[1] = word[2];
        word[2] = word[3];
        word[3] = temp;

		switch(roundNumber){
            case 1:
                roundByte[0] = (byte)0x01;
            case 2:
                roundByte[0] = (byte)0x02;
            case 3:
                roundByte[0] = (byte)0x04;	
            case 4:
                roundByte[0] = (byte)0x08;	
            case 5:
                roundByte[0] = (byte)0x10;	
            case 6:
                roundByte[0] = (byte)0x20;	
            case 7:
                roundByte[0] = (byte)0x40;	
            case 8:
                roundByte[0] = (byte)0x80;	
            case 9:
                roundByte[0] = (byte)0x1b;	
            case 10:
                roundByte[0] = (byte)0x36;	
        }

		for(row = 0; row < 4; row++){
			state[row][0] = (byte) ((word[row])^(state_in[row][0])^(roundByte[row]));
		}

		for(column = 1; column < 4; column++){
			for(row = 0; row < 4; row++){
				state[row][column] = (byte) ((byte) (state[row-1][column-1])^(state_in[row][column]));
			}
		}

		return state;
	}
	
	public static void printStateMatrix(byte[][] state){
		// Print state matrix
		int row = 0, column = 0;
		
		System.out.print("\nAfter Add Round Key:\n");
		
		for(row = 0; row < 4; row++){
			for(column = 0; column < 4; column++){
				System.out.print(DatatypeConverter.printHexBinary(new byte[] {state[row][column]}) + " ");
			}
			
			System.out.print("\n");
			column = 0;
		}
	}
}
