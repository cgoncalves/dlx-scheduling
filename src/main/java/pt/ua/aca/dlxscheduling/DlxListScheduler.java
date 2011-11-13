package pt.ua.aca.dlxscheduling;

import org.apache.commons.lang.Validate;
import pt.ua.aca.dlxscheduling.instruction.DlxBInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxInstructionList;
import pt.ua.aca.dlxscheduling.instruction.DlxJInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxLInstruction;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxListScheduler implements DlxScheduler {
    
    private final DlxDependencyGraph depGraph;
    private DlxInstructionList instrListScheduled;

    public DlxListScheduler(final DlxDependencyGraph depGraph) {
        Validate.notNull(depGraph);
        Validate.notEmpty(depGraph.getCandidateNodes());
        
        this.depGraph = depGraph;
        this.instrListScheduled = new DlxInstructionList();
    }
    
    @Override
    public final DlxInstructionList schedule() {
        
        /**
         * if the first instruction is a DlxLInstruction (label), extract it and
         * append it to the final scheduled instruction list
         */
        DlxInstruction labelInstruction = depGraph.getListInstrDeps().get(0).getInstr();
        if (labelInstruction instanceof DlxLInstruction) {
            instrListScheduled.add(labelInstruction);
            depGraph.remove(labelInstruction);
        }
        
        int depGraphSize = depGraph.getListInstrDeps().size();
        
        if (depGraphSize < 1) {
            return instrListScheduled;
        }
        
        /**
         * if the last instruction is a DlxJInstruction (jump) or
         * DlxBInstruction (branch), extract it and save it temporarily.
         * It will later be appended to the end of the final scheduled
         * instruction list
         */
        DlxInstruction jumpOrBranchInstruction = depGraph.getListInstrDeps().get(depGraphSize-1).getInstr();
        if (       (jumpOrBranchInstruction instanceof DlxJInstruction)
                || (jumpOrBranchInstruction instanceof DlxBInstruction)
           ) {
            depGraph.remove(jumpOrBranchInstruction);
        }

        instrListScheduled.addAll(processHeuristics(depGraph));
        
        // append the previously saved DlxJInstruction or DlxLInstruction
        if (       (jumpOrBranchInstruction instanceof DlxJInstruction)
                || (jumpOrBranchInstruction instanceof DlxBInstruction)
           ) {
            instrListScheduled.add(jumpOrBranchInstruction);
        }
        
        return instrListScheduled;
    }
    
    private DlxInstructionList processHeuristics(DlxDependencyGraph dep) {
        DlxInstructionList outInstrList = new DlxInstructionList();
        
        DlxInstructionList candidateNodes;
        do {
            // first heuristic
            candidateNodes = heuristicCandidateNodes();
            
            // second heuristic
            if (candidateNodes.size() > 1) {
                candidateNodes = heuristicCriticalPath(candidateNodes);
                
                // third heuristic
                if (candidateNodes.size() > 1) {
                    candidateNodes = heuristicSumCriticalPath(candidateNodes);
                    outInstrList.addAll(candidateNodes);
                    depGraph.remove(candidateNodes);
                } else {
                    outInstrList.addAll(candidateNodes);
                    depGraph.remove(candidateNodes);
                }
            } else {
                outInstrList.addAll(candidateNodes);
                depGraph.remove(candidateNodes);
            }
        } while (!candidateNodes.isEmpty());
        
        
        return outInstrList;
    }
    
    private DlxInstructionList heuristicCandidateNodes() {
        return depGraph.getCandidateNodes();
    }
    
    private DlxInstructionList heuristicCriticalPath(DlxInstructionList candidateNodes) {
        Validate.notEmpty(candidateNodes);
        
        DlxInstructionList criticalPathInstrList = new DlxInstructionList();
        
        criticalPathInstrList.add(candidateNodes.get(0));
        
        for (DlxInstruction instr : candidateNodes.subList(1, candidateNodes.size())) {
            if (instr.getNCyclesNonForward() > criticalPathInstrList.get(0).getNCyclesNonForward()) {
                criticalPathInstrList = new DlxInstructionList();
                criticalPathInstrList.add(instr);
            } else if (instr.getNCyclesNonForward() == criticalPathInstrList.get(0).getNCyclesNonForward()) {
                criticalPathInstrList.add(instr);
            }
            
            // else continue
        }
        
        return criticalPathInstrList;
    }
    
    private DlxInstructionList heuristicSumCriticalPath(DlxInstructionList candidateNodes) {
        Validate.notEmpty(candidateNodes);
        
        DlxInstructionList sumCriticalPathInstrList = new DlxInstructionList();
        
        int sumTotalInstr = -1;
        int sumInstrTmp;
        
        for (DlxInstruction instr : candidateNodes) {
            sumInstrTmp = depGraph.getSumCriticalPathRAW(instr) * instr.getNCyclesNonForward();
            
            if (sumInstrTmp > sumTotalInstr) {
                sumCriticalPathInstrList = new DlxInstructionList();
                sumCriticalPathInstrList.add(instr);
            } else if (sumInstrTmp == sumTotalInstr) {
                sumCriticalPathInstrList.add(instr);
            }
            
            // else continue
        }
        
        return sumCriticalPathInstrList;
    }

    @Override
    public final DlxDependencyGraph getDepGraph() {
        return depGraph;
    }
}
