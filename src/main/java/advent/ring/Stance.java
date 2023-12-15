package advent.ring;

import java.util.Objects;

public class Stance {

    public static Stance EMPTY_STANCE = new Stance();

    private final Orientation leftOrientation;
    private final Orientation rightOrientation;
    private final Orientation upOrientation;
    private final Orientation downOrientation;

    private Stance() {
        leftOrientation = Orientation.NONE;
        rightOrientation = Orientation.NONE;
        upOrientation = Orientation.NONE;
        downOrientation = Orientation.NONE;
    }

    public Stance(Orientation leftOrientation, Orientation rightOrientation, Orientation upOrientation, Orientation downOrientation) {
        this.leftOrientation = leftOrientation;
        this.rightOrientation = rightOrientation;
        this.upOrientation = upOrientation;
        this.downOrientation = downOrientation;
    }

    public Orientation getLeftOrientation() {
        return leftOrientation;
    }

    public Orientation getRightOrientation() {
        return rightOrientation;
    }

    public Orientation getUpOrientation() {
        return upOrientation;
    }

    public Orientation getDownOrientation() {
        return downOrientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stance stance = (Stance) o;
        return leftOrientation == stance.leftOrientation && rightOrientation == stance.rightOrientation && upOrientation == stance.upOrientation && downOrientation == stance.downOrientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftOrientation, rightOrientation, upOrientation, downOrientation);
    }

    @Override
    public String toString() {
        return "Stance{" +
                "leftOrientation=" + leftOrientation +
                ", rightOrientation=" + rightOrientation +
                ", upOrientation=" + upOrientation +
                ", downOrientation=" + downOrientation +
                '}';
    }
}
