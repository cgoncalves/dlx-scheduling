package pt.ua.aca.dlxscheduling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ua.aca.dlxscheduling.instruction.DlxBInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxIInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxInstructionList;
import pt.ua.aca.dlxscheduling.instruction.DlxJInstruction;
import pt.ua.aca.dlxscheduling.instruction.DlxLInstruction;
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
    
    private static final String EMPTY_OR_COMMENT_REGEX = "^\\s*(;.*)?$";
    private static final String INSTRUCTION_REGEX = "^(\\w+:)?\\s*(.+)\\s*(;.*)*$";
    private static final String LABEL_ONLY_REGEX = "^(\\w+):\\s*?$";
    
    private static final Pattern EMPTY_OR_COMMENT_PATTERN = Pattern.compile(EMPTY_OR_COMMENT_REGEX);
    private static final Pattern LINE_PATTERN = Pattern.compile(INSTRUCTION_REGEX);
    private static final Pattern LABEL_ONLY_PATTERN = Pattern.compile(LABEL_ONLY_REGEX);

    public DlxInstrutionTest() {
    }

    @Test
    public void instConstruction() throws IOException, Exception  {
        
        final FileReader fileReader = new FileReader(DLX_FILE_PATH);
        final BufferedReader bufferReader = new BufferedReader(fileReader);
        final DlxInstructionList dlxInstrList = new DlxInstructionList();
        
        String strLine;
        String label;
        String instr;
        Matcher matcher;

        DlxRInstruction rInstr;
        DlxIInstruction iInstr;
        DlxSwInstruction swInstr;
        DlxLwInstruction lwInstr;
        DlxBInstruction bInstr;
        DlxJInstruction jInstr;
        DlxLInstruction lInstr;
        
        while ( (strLine = bufferReader.readLine()) != null) {
            
            // dummy lines
            matcher = EMPTY_OR_COMMENT_PATTERN.matcher(strLine);
            if ( matcher.find() ) {
                logger.debug("Discarding line: " + strLine);
                System.out.println(strLine);
                continue;
            }
            
            // find label
            matcher = LABEL_ONLY_PATTERN.matcher(strLine);
            if ( matcher.find() ) {
                logger.debug("Line '" + strLine + "' has a label");
                lInstr = DlxLInstruction.BUILD_DLXLINSTRUCTION(strLine);
                dlxInstrList.add(lInstr);
                continue;
            }
            
            // find instruction
            matcher = LINE_PATTERN.matcher(strLine);
            if( !matcher.find() ) {
                throw new Exception("Line '" + strLine + "' is malformated!");
            }
            
            label = matcher.group(1); // label
            instr = matcher.group(2); // instr
      
            if (label != null) {
                logger.debug("Line '" + strLine + "' has a label");
                lInstr = DlxLInstruction.BUILD_DLXLINSTRUCTION(strLine);
                dlxInstrList.add(lInstr);
            }
            
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
            
            try {
                // try to initialize as a J type instr
                jInstr = DlxJInstruction.BUILD_DLXJINSTRUCTION(instr);
                dlxInstrList.add(jInstr);
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
        
        
        DlxBasicBlock bb = new DlxBasicBlock(dlxInstrList);
        logger.info("Basic blocks:");
        while (bb.hasNext()) {
            logger.info(bb.next().toString());
        }
        
        logger.info("Resetting basic block...");
        bb.reset();
        
        logger.info("Dependency graph for each basic block:");
        DlxDependencyGraph depGraph;
        while (bb.hasNext()) {
            depGraph = new DlxDependencyGraph(bb.next());
            depGraph.generateDependencyGraph();
            logger.info(depGraph.toString());
            
            logger.info("Candidate nodes:");
            for (DlxInstruction candidateNode : depGraph.getCandidateNodes()) {
                logger.info(candidateNode.toString());
            }
            logger.info("Candidate nodes END");
        }
        
    }
}
