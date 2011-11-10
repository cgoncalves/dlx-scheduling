package pt.ua.aca.dlxscheduling.instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxLInstruction extends DlxInstruction {

    public static final int NUM_CYCLES_FORWARD = 0;
    public static final int NUM_CYCLES_NON_FORWARD = 0;
    private static final String L_TYPE_REGEX = "^(\\w+)(:\\s*(.*)\\s*(;.*)*)?$";
    private static Pattern L_TYPE_PATTERN = Pattern.compile(L_TYPE_REGEX);
    
    private DlxLInstruction(String label) {
        super(null, -1, -1, -1, null, null, false, true,
                NUM_CYCLES_FORWARD, NUM_CYCLES_NON_FORWARD, label);
    }
    
    public static DlxLInstruction BUILD_DLXLINSTRUCTION(String instr) throws Exception {
        Matcher matcher = L_TYPE_PATTERN.matcher(instr);
        
        if ( !matcher.find() ) {
            throw new Exception("Instruction '" + instr + "' is malformated!");
        }
        
        final String label;
        label = matcher.group(1);
        
        return new DlxLInstruction(label);
    }
    
    @Override
    public String toString() {
        StringBuilder instr = new StringBuilder();
        instr.append(getLabel());
        instr.append(":");
        
        return instr.toString();
    }
}
