package pt.ua.aca.dlxscheduling.instruction;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxSwInstruction extends DlxInstruction {

    private static enum DLXSWINST {
        SW, SB, SH;
    };
    
    private static HashSet<String> GET_DLXSWINST_ENUMS() {

        HashSet<String> values = new HashSet<String>();

        for (DLXSWINST c : DLXSWINST.values()) {
            values.add(c.name());
        }

        return values;
    }

    public static final int NUM_CYCLES_FORWARD = 0;
    public static final int NUM_CYCLES_NON_FORWARD = 1;
    private static final String SW_TYPE_REGEX = "(\\w+)\\s+([#|0X]?\\w+)\\([R|r](\\d+)\\),\\s*[R|r](\\d+)";
    private static Pattern SW_TYPE_PATTERN = Pattern.compile(SW_TYPE_REGEX);
    
    private DlxSwInstruction(String name, int rIn1, int rIn2, String offset) {
        super(name, rIn1, rIn2, -1, offset, null, false, true,
                NUM_CYCLES_FORWARD, NUM_CYCLES_NON_FORWARD, null);
    }
    
    public static DlxSwInstruction BUILD_DLXSWINSTRUCTION(String instr) throws Exception {
        Matcher matcher = SW_TYPE_PATTERN.matcher(instr);
        
        if ( !matcher.find() ) {
            throw new Exception("Instruction '" + instr + "' is malformated!");
        }
        
        final String name = matcher.group(1);
        
        if (! GET_DLXSWINST_ENUMS().contains(name.toUpperCase())) {
            throw new Exception("Instruction '" + instr + "' is not of a valid SW type");
        }
        
        final String offset;
        final int rIn1;
        final int rIn2;
        
        offset = matcher.group(2);
        rIn1 = Integer.parseInt(matcher.group(3));
        rIn2 = Integer.parseInt(matcher.group(4));
        
        return new DlxSwInstruction(name, rIn1, rIn2, offset);
    }
    
    @Override
    public String toString() {
        StringBuilder instr = new StringBuilder();
        instr.append(getName());
        instr.append(" ");
        instr.append(getOffset());
        instr.append("(r");
        instr.append(getRIn1());
        instr.append("), r");
        instr.append(getRIn2());
        
        return instr.toString();
    }
}
