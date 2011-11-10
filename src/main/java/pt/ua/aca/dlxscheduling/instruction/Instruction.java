package pt.ua.aca.dlxscheduling.instruction;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public interface Instruction {
    String getName();    
    int getRIn1();
    int getRIn2();
    int getROut();
    String getOffset();
    String getImm();
    boolean getMemIn();
    boolean getMemOut();
    int getNCyclesForward();
    int getNCyclesNonForward();
    String getLabel();
    boolean hasLabel();
}
