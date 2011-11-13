package pt.ua.aca.dlxscheduling.instruction;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxRInstruction extends DlxInstruction {

    private static enum DLXRINST {
        ADD, SUB, ADDU, SUBU, OR, AND, XOR, MULT, MULTU, DIV, DIVU;
    };
    
    private static HashSet<String> GET_DLXRINST_ENUMS() {

        HashSet<String> values = new HashSet<String>();

        for (DLXRINST c : DLXRINST.values()) {
            values.add(c.name());
        }

        return values;
    }

    public static final int NUM_CYCLES_FORWARD = 0;
    public static final int NUM_CYCLES_NON_FORWARD = 1;
    private static final String R_TYPE_REGEX = "(\\w+)\\s+[R|r](\\d+),\\s*[R|r](\\d+),\\s*[R|r](\\d+)";
    private static Pattern R_TYPE_PATTERN = Pattern.compile(R_TYPE_REGEX);

    private DlxRInstruction(String name, int rIn1, int rIn2, int rOut) {
        super(name, rIn1, rIn2, rOut, null, null, false, false,
                NUM_CYCLES_FORWARD, NUM_CYCLES_NON_FORWARD, null);
    }
    
    public static DlxRInstruction BUILD_DLXRINSTRUCTION(String instr) throws Exception {
        Matcher matcher = R_TYPE_PATTERN.matcher(instr);
        
        if ( !matcher.find() ) {
            throw new Exception("Instruction '" + instr + "' is malformated!");
        }
        
        final String name = matcher.group(1);
        
        if (! GET_DLXRINST_ENUMS().contains(name.toUpperCase())) {
            throw new Exception("Instruction '" + instr + "' is not of a valid R type");
        }
        
        
        final int rOut;
        final int rIn1;
        final int rIn2;
        
        rOut = Integer.parseInt(matcher.group(2));
        rIn1 = Integer.parseInt(matcher.group(3));
        rIn2 = Integer.parseInt(matcher.group(4));
        
        return new DlxRInstruction(name, rIn1, rIn2, rOut);
    }
    
    @Override
    public String toString() {
        StringBuilder instr = new StringBuilder();
        instr.append(getName());
        instr.append(" r");
        instr.append(getROut());
        instr.append(", r");
        instr.append(getRIn1());
        instr.append(", r");
        instr.append(getRIn2());
        
        return instr.toString();
    }

}
