package pt.ua.aca.dlxscheduling.instruction;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxJInstruction extends DlxInstruction {

    private static enum DLXJINST {
        J, JAL, JALR, JR
    };
    
    private static HashSet<String> GET_DLXJINST_ENUMS() {

        HashSet<String> values = new HashSet<String>();

        for (DLXJINST c : DLXJINST.values()) {
            values.add(c.name());
        }

        return values;
    }

    public static final int NUM_CYCLES_FORWARD = 2;
    public static final int NUM_CYCLES_NON_FORWARD = 2;
    private static final String J_TYPE_REGEX = "(\\w+)\\s+(\\w+)";
    private static Pattern B_TYPE_PATTERN = Pattern.compile(J_TYPE_REGEX);
    
    private DlxJInstruction(String name, String label) {
        super(name, -1, -1, -1, null, null, false, true,
                NUM_CYCLES_FORWARD, NUM_CYCLES_NON_FORWARD, label);
    }
    
    public static DlxJInstruction BUILD_DLXJINSTRUCTION(String instr) throws Exception {
        Matcher matcher = B_TYPE_PATTERN.matcher(instr);
        
        if ( !matcher.find() ) {
            throw new Exception("Instruction '" + instr + "' is malformated!");
        }
        
        final String name = matcher.group(1);
        
        if (! GET_DLXJINST_ENUMS().contains(name.toUpperCase())) {
            throw new Exception("Instruction '" + instr + "' is not of a valid B type");
        }
        
        final String label;
        label = matcher.group(2);
        
        return new DlxJInstruction(name, label);
    }
    
    @Override
    public String toString() {
        StringBuilder instr = new StringBuilder();
        instr.append(getName());
        instr.append(" ");
        instr.append(getLabel());
        
        return instr.toString();
    }
}
