package pt.ua.aca.dlxscheduling;

import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import pt.ua.aca.dlxscheduling.instruction.DlxInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxInstructionList;
import pt.ua.aca.dlxscheduling.instruction.DlxLwInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxSwInstruction;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxDependencyGraph implements Cloneable {
    
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
                
                // WAR
                if (
                           ( listInstrOriginal.get(cur).getROut() == listInstrOriginal.get(prev).getRIn1() )
                        || ( (listInstrOriginal.get(cur).getROut() == listInstrOriginal.get(prev).getRIn1()) && (listInstrOriginal.get(prev).getRIn1() != -1) )
                        || ( (listInstrOriginal.get(cur).getROut() == listInstrOriginal.get(prev).getRIn2()) && (listInstrOriginal.get(prev).getRIn2() != -1) )
                        || ( (listInstrOriginal.get(cur) instanceof DlxSwInstruction) && (listInstrOriginal.get(prev) instanceof DlxLwInstruction) )
//                        || ( ( (listInstrOriginal.get(cur).getMemOut() == true) || (listInstrOriginal.get(cur).getMemIn() == true) )
//                                && ( ( listInstrOriginal.get(prev).getMemOut() == true) || (listInstrOriginal.get(prev).getMemIn() == true) )
//                           )
                ) {
                    listInstrDeps.get(cur).getWar().add(listInstrOriginal.get(prev));
                }
            }
            
            // RAW: compare rIn1 against rOut
            for (int prev = cur-1; prev >= 0; prev--) {
                
                // if prev instruction is a label, skip it
                if (listInstrOriginal.get(prev).isLabel()) {
                    continue;
                }
                
                if (
                        (listInstrOriginal.get(cur).getRIn1() == listInstrOriginal.get(prev).getROut())
                        && (listInstrOriginal.get(prev).getROut() != -1)
                   ) {
                    listInstrDeps.get(cur).getRaw().add(listInstrOriginal.get(prev));
                    break;
                }
            }
            
            // RAW: compare rIn2 against rOut
            for (int prev = cur-1; prev >= 0; prev--) {
                
                // if prev instruction is a label, skip it
                if (listInstrOriginal.get(prev).isLabel()) {
                    continue;
                }
                
                if (
                        (listInstrOriginal.get(cur).getRIn2() == listInstrOriginal.get(prev).getROut())
                        && (listInstrOriginal.get(prev).getROut() != -1)
                   ) {
                    listInstrDeps.get(cur).getRaw().add(listInstrOriginal.get(prev));
                    break;
                }
            }
            
            
            // RAW: mem
            for (int prev = cur-1; prev >= 0; prev--) {
                
                // if prev instruction is a label, skip it
                if (listInstrOriginal.get(prev).isLabel()) {
                    continue;
                }
                
                if ( (listInstrOriginal.get(cur) instanceof DlxLwInstruction) && (listInstrOriginal.get(prev) instanceof DlxSwInstruction) ){
                    listInstrDeps.get(cur).getRaw().add(listInstrOriginal.get(prev));
                    break;
                }
            }
            
        }
    }
    
    public DlxInstructionList getCandidateNodes() {
        DlxInstructionList candidateNodesList = new DlxInstructionList();
        
        for (DlxInstructionDependency instrDep : listInstrDeps) {
            if (instrDep.isCandidateNode()) {
                candidateNodesList.add(instrDep.getInstr());
            }
        }
        
        return candidateNodesList;
    }
    
    public int getSumCriticalPathRAW(DlxInstruction instr) {
        int sum = 0;
        
        for (DlxInstructionDependency instrDep : listInstrDeps) {
            if (instrDep.getRaw().contains(instr)) {
                sum++;
            }
        }
        
        return sum;
    }
    
    public void remove(DlxInstruction instrToRemove) {
        // remove dependent nodes first
        for (DlxInstructionDependency dep : listInstrDeps) {
            dep.removeAllNodeDependencies(instrToRemove);
        }
        
        for (int i = 0; i < listInstrDeps.size(); i++) {
            if (listInstrDeps.get(i).getInstr().equals(instrToRemove)) {
                listInstrDeps.remove(listInstrDeps.get(i));
                return;
            }
        }
        
//      FIXME the code below will throw an ConcurrentModification Exception
//            that is why I'm for now using the last for cycle code above :-/
//        for (DlxInstructionDependency dep : listInstrDeps) {
//            System.out.println("AAAAAAAAAAAA");
//            if (dep.getInstr().equals(instrToRemove)) {
//                System.out.println(dep);
//                System.out.println("BBBBBBBBBBBBB");
//                listInstrDeps.remove(dep);
////                return; FIXME there should have no problem returning at this point
//            }
//        }
    }
    
    public void remove(DlxInstructionList instrListToRemove) {
        for (DlxInstruction instr : instrListToRemove) {
            remove(instr);
        }
    }

    public ArrayList<DlxInstructionDependency> getListInstrDeps() {
        return listInstrDeps;
    }

    public DlxInstructionList getListInstrOriginal() {
        return listInstrOriginal;
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

    @Override
    public DlxDependencyGraph clone() throws CloneNotSupportedException {
        return (DlxDependencyGraph) super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DlxDependencyGraph other = (DlxDependencyGraph) obj;
        if (this.listInstrOriginal != other.listInstrOriginal && (this.listInstrOriginal == null || !this.listInstrOriginal.equals(other.listInstrOriginal))) {
            return false;
        }
        if (this.listInstrDeps != other.listInstrDeps && (this.listInstrDeps == null || !this.listInstrDeps.equals(other.listInstrDeps))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.listInstrOriginal != null ? this.listInstrOriginal.hashCode() : 0);
        hash = 37 * hash + (this.listInstrDeps != null ? this.listInstrDeps.hashCode() : 0);
        return hash;
    }
    
}
