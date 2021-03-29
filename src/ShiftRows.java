// Import statements
import javax.xml.bind.DatatypeConverter;

public class ShiftRows {
	
	// AES Shift Rows method
    public static byte[][] shift_rows(byte[][] state_in){
        byte[][] state = new byte[4][4];

        int column = 0;
        int row = 0;
       
        if(row == 0){
            for(column = 0; column < 4; column++){              
                state[0][column] = state_in[0][column];       
            }
            row++;   
            column=0;
            if(row == 1){
                state[1][column] = state_in[1][column+1];
                state[1][column+1] = state_in[1][column+2];
                state[1][column+2] = state_in[1][column+3];
                state[1][column+3] = state_in[1][column];          
                row++; 
                
                if(row == 2){
                    state[2][column] = state_in[2][column+2];
                    state[2][column+1] = state_in[2][column+3];
                    state[2][column+2] = state_in[2][column];
                    state[2][column+3] = state_in[2][column+1];          
                    row++;
                    
                    if(row == 3){
                        state[3][column] = state_in[3][column+3];
                        state[3][column+1] = state_in[3][column];
                        state[3][column+2] = state_in[3][column+1];
                        state[3][column+3] = state_in[3][column+2];          
                        row = 0;
                    }
                }
            }
        }  
        
		printStateMatrix(state);
        return state;   
    }
	
	public static void printStateMatrix(byte[][] state){
		// Print state matrix
		int row = 0, column = 0;
		
		System.out.print("\nAfter Shift Rows:\n");
		
		for(row = 0; row < 4; row++){
			for(column = 0; column < 4; column++){
				System.out.print(DatatypeConverter.printHexBinary(new byte[] {state[row][column]}) + " ");
			}
			
			System.out.print("\n");
			column = 0;
		}
	}
}
