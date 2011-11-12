package pt.ua.aca.dlxscheduling;

import pt.ua.aca.dlxscheduling.instruction.DlxInstructionList;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public interface DlxScheduler {
    DlxInstructionList schedule();
    DlxDependencyGraph getDepGraph();
}
