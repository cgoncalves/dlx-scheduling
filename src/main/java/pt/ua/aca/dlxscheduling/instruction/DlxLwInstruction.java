package pt.ua.aca.dlxscheduling.instruction;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxLwInstruction extends DlxInstruction {

    private static enum DLXLWINST {
        LW, LB, LBU, LH, LHU;
    };
    
    private static HashSet<String> GET_DLXSWINST_ENUMS() {

        HashSet<String> values = new HashSet<String>();

        for (DLXLWINST c : DLXLWINST.values()) {
            values.add(c.name());
        }

        return values;
    }

    public static final int NUM_CYCLES_FORWARD = 1;
    public static final int NUM_CYCLES_NON_FORWARD = 2;
    private static final String LW_TYPE_REGEX = "(\\w+)\\s+[R|r](\\d+),\\s*([#|0X]?\\w+)\\([R|r](\\d+)\\)";
    private static Pattern LW_TYPE_PATTERN = Pattern.compile(LW_TYPE_REGEX);
    
    private DlxLwInstruction(String name, int rIn1, int rOut, String offset) {
        super(name, rIn1, -1, rOut, offset, null, true, false,
                NUM_CYCLES_FORWARD, NUM_CYCLES_NON_FORWARD, null);
    }
    
    public static DlxLwInstruction BUILD_DLXLWINSTRUCTION(String instr) throws Exception {
        Matcher matcher = LW_TYPE_PATTERN.matcher(instr);
        
        if ( !matcher.find() ) {
            throw new Exception("Instruction '" + instr + "' is malformated!");
        }
        
        final String name = matcher.group(1);
        
        if (! GET_DLXSWINST_ENUMS().contains(name.toUpperCase())) {
            throw new Exception("Instruction '" + instr + "' is not of a valid LW type");
        }
        
        final String offset;
        final int rIn1;
        final int rOut;
        
        rOut = Integer.parseInt(matcher.group(2));
        offset = matcher.group(3);
        rIn1 = Integer.parseInt(matcher.group(4));
        
        return new DlxLwInstruction(name, rIn1, rOut, offset);
    }
    
    @Override
    public String toString() {
        StringBuilder instr = new StringBuilder();
        instr.append(getName());
        instr.append(" r");
        instr.append(getROut());
        instr.append(", ");
        instr.append(getOffset());
        instr.append("(r");
        instr.append(getRIn1());
        instr.append(")");
        
        return instr.toString();
    }
}
