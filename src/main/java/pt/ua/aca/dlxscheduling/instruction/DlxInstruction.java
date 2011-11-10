package pt.ua.aca.dlxscheduling.instruction;

/**
 *
 * @author Carlos Gonçalves &lt;carlos.goncalves [at] ua [dot] pt&gt;
 */
public class DlxInstruction implements Instruction {
    
    private String name;
    private int rIn1;
    private int rIn2;
    private int rOut;
    private String offset;
    private String imm;
    private boolean memIn;
    private boolean memOut;
    private int nCyclesForward;
    private int nCyclesNonForward;
    private String label;

    protected DlxInstruction(final String name, final int rIn1,
            final int rIn2, final int rOut, final String offset, final String imm,
            final boolean memIn, final boolean memOut, final int nCyclesForward,
            final int nCyclesNonForward, final String label) {
        this.name = name;
        this.rIn1 = rIn1;
        this.rIn2 = rIn2;
        this.rOut = rOut;
        this.offset = offset;
        this.imm = imm;
        this.memIn = memIn;
        this.memOut = memOut;
        this.nCyclesForward = nCyclesForward;
        this.nCyclesNonForward = nCyclesNonForward;
        this.label = label;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getRIn1() {
        return this.rIn1;
    }

    @Override
    public int getRIn2() {
        return this.rIn2;
    }

    @Override
    public int getROut() {
        return this.rOut;
    }

    @Override
    public String getOffset() {
        return this.offset;
    }

    @Override
    public String getImm() {
        return this.imm;
    }

    @Override
    public boolean getMemIn() {
        return this.memIn;
    }

    @Override
    public boolean getMemOut() {
        return this.memOut;
    }

    @Override
    public int getNCyclesForward() {
        return this.nCyclesForward;
    }

    @Override
    public int getNCyclesNonForward() {
        return this.nCyclesNonForward;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
    
    @Override
    public boolean hasLabel() {
        return ( getLabel() != null && !getLabel().isEmpty() );
    }

    @Override
    public boolean isLabel() {
        return hasLabel();
    }
}
