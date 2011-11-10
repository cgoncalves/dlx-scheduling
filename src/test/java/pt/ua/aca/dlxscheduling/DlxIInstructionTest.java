package pt.ua.aca.dlxscheduling;

import java.util.logging.Level;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ua.aca.dlxscheduling.instruction.DlxIInstruction;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxIInstructionTest {
    
    private static final Logger logger = LoggerFactory.getLogger(DlxIInstructionTest.class);

    public DlxIInstructionTest() {
    }

    @Test
    public void instrIConstruction()  {
        DlxIInstruction iInst;
        
        String instr = "addi r1, r2, #123";
        try {
            iInst = DlxIInstruction.BUILD_DLXIINSTRUCTION(instr);
            logger.debug( iInst.toString() );
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DlxIInstructionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
