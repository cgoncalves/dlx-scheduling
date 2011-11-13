package pt.ua.aca.dlxscheduling.instruction;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxBInstruction extends DlxInstruction {

    private static enum DLXBINST {
        BEQZ, BNEZ;
    };
    
    private static HashSet<String> GET_DLXBINST_ENUMS() {

        HashSet<String> values = new HashSet<String>();

        for (DLXBINST c : DLXBINST.values()) {
            values.add(c.name());
        }

        return values;
    }

    public static final int NUM_CYCLES_FORWARD = 3; // don't care
    public static final int NUM_CYCLES_NON_FORWARD = 3; // don't care
    private static final String B_TYPE_REGEX = "(\\w+)\\s+[R|r](\\d+),\\s*(\\w+)";
    private static Pattern B_TYPE_PATTERN = Pattern.compile(B_TYPE_REGEX);
    
    private DlxBInstruction(String name, int rIn1, String label) {
        super(name, rIn1, -1, -1, null, null, false, true,
                NUM_CYCLES_FORWARD, NUM_CYCLES_NON_FORWARD, label);
    }
    
    public static DlxBInstruction BUILD_DLXBINSTRUCTION(String instr) throws Exception {
        Matcher matcher = B_TYPE_PATTERN.matcher(instr);
        
        if ( !matcher.find() ) {
            throw new Exception("Instruction '" + instr + "' is malformated!");
        }
        
        final String name = matcher.group(1);
        
        if (! GET_DLXBINST_ENUMS().contains(name.toUpperCase())) {
            throw new Exception("Instruction '" + instr + "' is not of a valid B type");
        }
        
        final int rIn1;
        final String label;
        
        rIn1 = Integer.parseInt(matcher.group(2));
        label = matcher.group(3);
        
        return new DlxBInstruction(name, rIn1, label);
    }
    
    @Override
    public String toString() {
        StringBuilder instr = new StringBuilder();
        instr.append(getName());
        instr.append(" r");
        instr.append(getRIn1());
        instr.append(", ");
        instr.append(getLabel());
        
        return instr.toString();
    }
}
