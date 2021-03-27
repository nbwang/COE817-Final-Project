
import javax.xml.bind.DatatypeConverter;

public class ShiftRows {
    
    public static void main(String[] args) {
       
        byte[][] state = {
	        {(byte)0x87, (byte)0xF2, (byte)0x4D, (byte)0x97},
	        {(byte)0x6E, (byte)0x4C, (byte)0x90, (byte)0xEC},
	        {(byte)0x46, (byte)0xE7, (byte)0x4A, (byte)0xC3},
	        {(byte)0xA6, (byte)0x8C, (byte)0xD8, (byte)0x95}
	};
        
	// Call shift rows function and save result
        byte[][] state_out = shift_rows(state);
        
	// Print result (testing purposes)
        int row = 0;
	int column = 0;
	    
        for (row = 0; row < 4; row++){
            for(column = 0; column < 4; column++){
                System.out.print(DatatypeConverter.printHexBinary(new byte[] {state_out[row][column]}) + " ");
            }
            System.out.print("\n");
            column = 0;
        }     
    }
    
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
        
        return state;   
    }        
}   