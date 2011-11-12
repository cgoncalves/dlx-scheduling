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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DlxInstruction other = (DlxInstruction) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.rIn1 != other.rIn1) {
            return false;
        }
        if (this.rIn2 != other.rIn2) {
            return false;
        }
        if (this.rOut != other.rOut) {
            return false;
        }
        if ((this.offset == null) ? (other.offset != null) : !this.offset.equals(other.offset)) {
            return false;
        }
        if ((this.imm == null) ? (other.imm != null) : !this.imm.equals(other.imm)) {
            return false;
        }
        if (this.memIn != other.memIn) {
            return false;
        }
        if (this.memOut != other.memOut) {
            return false;
        }
        if (this.nCyclesForward != other.nCyclesForward) {
            return false;
        }
        if (this.nCyclesNonForward != other.nCyclesNonForward) {
            return false;
        }
        if ((this.label == null) ? (other.label != null) : !this.label.equals(other.label)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 67 * hash + this.rIn1;
        hash = 67 * hash + this.rIn2;
        hash = 67 * hash + this.rOut;
        hash = 67 * hash + (this.offset != null ? this.offset.hashCode() : 0);
        hash = 67 * hash + (this.imm != null ? this.imm.hashCode() : 0);
        hash = 67 * hash + (this.memIn ? 1 : 0);
        hash = 67 * hash + (this.memOut ? 1 : 0);
        hash = 67 * hash + this.nCyclesForward;
        hash = 67 * hash + this.nCyclesNonForward;
        hash = 67 * hash + (this.label != null ? this.label.hashCode() : 0);
        return hash;
    }
    
    
}
