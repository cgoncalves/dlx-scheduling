package pt.ua.aca.dlxscheduling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ua.aca.dlxscheduling.instruction.DlxBInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxIInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxInstructionList;
import pt.ua.aca.dlxscheduling.instruction.DlxLwInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxRInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxSwInstruction;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxInstrutionTest {
    
    private static final Logger logger = LoggerFactory.getLogger(DlxInstrutionTest.class);
    private static final String DLX_FILE_PATH = "/Users/cgoncalves/test.s";
    
    private static final String EMPTY_OR_COMMENT_REGEX = "^\\s*[;\\s*\\w*]*$"; // FIXME only supports \\w chars in comments
    private static final String LABEL_AND_INSTRUCTION_REGEX = "^(\\w+:)?\\s*(.+)\\s*(;.*)*$";
    
    private static final Pattern LINE_PATTERN = Pattern.compile(LABEL_AND_INSTRUCTION_REGEX);
    private static final Pattern EMPTY_OR_COMMENT_PATTERN = Pattern.compile(EMPTY_OR_COMMENT_REGEX);

    public DlxInstrutionTest() {
    }

    @Test
    public void instConstruction() throws IOException, Exception  {
        //final String lineR = "loop: ADD $r1,$2,         $49 ; this is a comment!".toLowerCase();
        
        final FileReader fileReader = new FileReader(DLX_FILE_PATH);
        final BufferedReader bufferReader = new BufferedReader(fileReader);
        final DlxInstructionList listInstr = new DlxInstructionList();
        
        String strLine;
        String label;
        String instr;
        Matcher matcher;
        final DlxInstructionList dlxInstrList = new DlxInstructionList();

        DlxRInstruction rInstr;
        DlxIInstruction iInstr;
        DlxSwInstruction swInstr;
        DlxLwInstruction lwInstr;
        DlxBInstruction bInstr;
        
        while ( (strLine = bufferReader.readLine()) != null) {
            
            // dummy lines
            matcher = EMPTY_OR_COMMENT_PATTERN.matcher(strLine);
            if ( matcher.find() ) {
                continue;
            }
            
            // find label and instruction
            matcher = LINE_PATTERN.matcher(strLine);
            
            // get instruction and label, if exists
            if( !matcher.find() ) {
                throw new Exception("Line '" + strLine + "' is malformated!");
            }
            
            label = StringUtils.chop(matcher.group(1)); // remove ":"
            instr = matcher.group(2); // instr
                        
            try {
                // try to initialize as a R type instr
                rInstr = DlxRInstruction.BUILD_DLXRINSTRUCTION(instr);
                dlxInstrList.add(rInstr);
                continue;
            } catch (Exception ex) {
                // ignore
            }
            
            try {
                // try to initialize as an I type instr
                iInstr = DlxIInstruction.BUILD_DLXIINSTRUCTION(instr);
                dlxInstrList.add(iInstr);
                continue;
            } catch (Exception ex) {
                // ignore
            }
            
            try {
                // try to initialize as a SW type instr
                swInstr = DlxSwInstruction.BUILD_DLXSWINSTRUCTION(instr);
                dlxInstrList.add(swInstr);
                continue;
            } catch (Exception ex) {
                // ignore
            }
            
            try {
                // try to initialize as a LW type instr
                lwInstr = DlxLwInstruction.BUILD_DLXLWINSTRUCTION(instr);
                dlxInstrList.add(lwInstr);
                continue;
            } catch (Exception ex) {
                // ignore
            }
            
            try {
                // try to initialize as a B type instr
                bInstr = DlxBInstruction.BUILD_DLXBINSTRUCTION(instr);
                dlxInstrList.add(bInstr);
                continue;
            } catch (Exception ex) {
                // ignore
            }
            
            logger.error("Unknown instruction '" + instr + "'. Aborting!");
            return; // exit
        }
        
        
        logger.info("Instructions loaded:");
        logger.info("-----------------------------");
        for (DlxInstruction i : dlxInstrList) {
            logger.info(i.toString());
        }
        logger.info("-----------------------------");
    }
}
