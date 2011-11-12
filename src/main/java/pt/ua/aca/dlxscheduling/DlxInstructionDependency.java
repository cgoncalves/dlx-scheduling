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
    
    public boolean isCandidateNode() {
        return (raw.isEmpty() && war.isEmpty() && waw.isEmpty());
    }
    
    public void removeAllNodeDependencies(DlxInstruction instrToRemove) {
        raw.remove(instrToRemove);
        war.remove(instrToRemove);
        waw.remove(instrToRemove);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DlxInstructionDependency other = (DlxInstructionDependency) obj;
        if (this.instr != other.instr && (this.instr == null || !this.instr.equals(other.instr))) {
            return false;
        }
        if (this.raw != other.raw && (this.raw == null || !this.raw.equals(other.raw))) {
            return false;
        }
        if (this.war != other.war && (this.war == null || !this.war.equals(other.war))) {
            return false;
        }
        if (this.waw != other.waw && (this.waw == null || !this.waw.equals(other.waw))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.instr != null ? this.instr.hashCode() : 0);
        hash = 59 * hash + (this.raw != null ? this.raw.hashCode() : 0);
        hash = 59 * hash + (this.war != null ? this.war.hashCode() : 0);
        hash = 59 * hash + (this.waw != null ? this.waw.hashCode() : 0);
        return hash;
    }
}
