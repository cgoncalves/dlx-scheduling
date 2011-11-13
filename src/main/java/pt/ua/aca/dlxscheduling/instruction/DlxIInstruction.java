package pt.ua.aca.dlxscheduling.instruction;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxIInstruction extends DlxInstruction {

    private static enum DLXIINST {
        ADDI, SUBI, ORI, ADDUI, SUBUI, ANDI;
    };
    
    private static HashSet<String> GET_DLXIINST_ENUMS() {

        HashSet<String> values = new HashSet<String>();

        for (DLXIINST c : DLXIINST.values()) {
            values.add(c.name());
        }

        return values;
    }

    public static final int NUM_CYCLES_FORWARD = 0;
    public static final int NUM_CYCLES_NON_FORWARD = 1;
    private static final String I_TYPE_REGEX = "(\\w+)\\s+[R|r](\\d+),\\s*[R|r](\\d+),\\s*(.+)";
    private static Pattern I_TYPE_PATTERN = Pattern.compile(I_TYPE_REGEX);
    
    private DlxIInstruction(String name, int rIn1, int rOut, String imm) {
        super(name, rIn1, -1, rOut, null, imm, false, false,
                NUM_CYCLES_FORWARD, NUM_CYCLES_NON_FORWARD, null);
    }
    
    public static DlxIInstruction BUILD_DLXIINSTRUCTION(String instr) throws Exception {
        Matcher matcher = I_TYPE_PATTERN.matcher(instr);
        
        if ( !matcher.find() ) {
            throw new Exception("Instruction '" + instr + "' is malformated!");
        }
        
        final String name = matcher.group(1);
        
        if (! GET_DLXIINST_ENUMS().contains(name.toUpperCase())) {
            throw new Exception("Instruction '" + instr + "' is not of a valid I type");
        }
        
        final int rOut;
        final int rIn1;
        final String imm;
        
        rOut = Integer.parseInt(matcher.group(2));
        rIn1 = Integer.parseInt(matcher.group(3));
        imm = matcher.group(4);
        
        return new DlxIInstruction(name, rIn1, rOut, imm);
    }
    
    @Override
    public String toString() {
        StringBuilder instr = new StringBuilder();
        instr.append(getName());
        instr.append(" r");
        instr.append(getROut());
        instr.append(", r");
        instr.append(getRIn1());
        instr.append(", ");
        instr.append(getImm());
        
        return instr.toString();
    }
}
