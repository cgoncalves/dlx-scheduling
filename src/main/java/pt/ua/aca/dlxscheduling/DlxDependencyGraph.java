package pt.ua.aca.dlxscheduling;

import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import pt.ua.aca.dlxscheduling.instruction.DlxInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxInstructionList;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxDependencyGraph {
    
    private final DlxInstructionList listInstrOriginal;
    private final ArrayList<DlxInstructionDependency> listInstrDeps;

    public DlxDependencyGraph(DlxInstructionList listInstrOriginal) {
        Validate.noNullElements(listInstrOriginal);
        
        this.listInstrOriginal = listInstrOriginal;
        listInstrDeps = new ArrayList<DlxInstructionDependency>(listInstrOriginal.size());
        
        for (DlxInstruction instr : this.listInstrOriginal) {
            listInstrDeps.add(new DlxInstructionDependency(instr));
        }
    }
    
    public void generateDependencyGraph() {
        for (int cur = 1; cur < listInstrOriginal.size(); cur++) {
            
            // if current instruction is a label, skip it
            if (listInstrOriginal.get(cur).isLabel()) {
                continue;
            }
            
            for (int prev = 0; prev < cur; prev++) {
                // if prev instruction is a label, skip it
                if (listInstrOriginal.get(prev).isLabel()) {
                    continue;
                }
                
                // WAW
                if (listInstrOriginal.get(cur).getROut() == listInstrOriginal.get(prev).getROut()) {
                    listInstrDeps.get(cur).getWaw().add(listInstrOriginal.get(prev));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        
        for (DlxInstructionDependency instrDeps : listInstrDeps) {
            ret.append(instrDeps.toString());
            ret.append("\n");
        }
        
        return ret.toString();
    }
    
}