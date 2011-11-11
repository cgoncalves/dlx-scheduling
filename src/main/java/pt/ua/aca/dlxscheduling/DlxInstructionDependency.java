package pt.ua.aca.dlxscheduling;

import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import pt.ua.aca.dlxscheduling.instruction.DlxInstruction;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxInstructionDependency {
    
    private final DlxInstruction instr;
    private ArrayList<DlxInstruction> raw;
    private ArrayList<DlxInstruction> war;
    private ArrayList<DlxInstruction> waw;

    public DlxInstructionDependency(DlxInstruction instr) {
        Validate.notNull(instr);
        
        this.instr = instr;
        raw = new ArrayList<DlxInstruction>();
        war = new ArrayList<DlxInstruction>();
        waw = new ArrayList<DlxInstruction>();
    }

    public DlxInstruction getInstr() {
        return instr;
    }
    
    public ArrayList<DlxInstruction> getRaw() {
        return raw;
    }

    public ArrayList<DlxInstruction> getWar() {
        return war;
    }

    public ArrayList<DlxInstruction> getWaw() {
        return waw;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        
        ret.append(instr);
        ret.append(":\n\tRAW: ");
        ret.append(raw);
        ret.append("\n\tWAR: ");
        ret.append(war);
        ret.append("\n\tWAW: ");
        ret.append(waw);
        
        return ret.toString();
    }
}
