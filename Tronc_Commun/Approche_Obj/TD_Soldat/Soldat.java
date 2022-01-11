public interface Soldat {
    public int force();

    public boolean parer(int force);

    void accept(SoldatVisitor visitor);
}