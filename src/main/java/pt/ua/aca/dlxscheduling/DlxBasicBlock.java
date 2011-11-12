package pt.ua.aca.dlxscheduling;

import java.util.ArrayList;
import java.util.Iterator;
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
public class DlxBasicBlock {
    
    private final ArrayList<DlxInstructionList> listBB;
    public Iterator<DlxInstructionList> iteratorListBB;


    public DlxBasicBlock(DlxInstructionList list) {
        Validate.notEmpty(list);
        
        listBB = new ArrayList<DlxInstructionList>();
        
        final Iterator<DlxInstruction> i = list.iterator();
        DlxInstructionList curList = new DlxInstructionList();
        DlxInstruction instr;
        
        while (i.hasNext()) {
            instr = i.next();

            if (instr instanceof DlxLInstruction && !curList.isEmpty()) {
                listBB.add(curList);
                curList = new DlxInstructionList();
                curList.add(instr);
                continue;
            }

            curList.add(instr);
            if (instr instanceof DlxJInstruction || instr instanceof DlxBInstruction) {
                listBB.add(curList);
                curList = new DlxInstructionList();
            }
        }
        
        if (!curList.isEmpty()) {
            listBB.add(curList);
        }

        iteratorListBB = listBB.iterator();
    }
    
    /**
     * Return a basic block
     * @return null if has no basic blocks
     */
    public DlxInstructionList next() {
        if ( !hasNext() ) {
            return null;
        }
        
        return iteratorListBB.next();
    }
    
    /**
     * Check whether has next basic block
     * @return 
     */
    public boolean hasNext() {
        return iteratorListBB.hasNext();
    }
    
    public void reset() {
        iteratorListBB = listBB.iterator();
    }
}
