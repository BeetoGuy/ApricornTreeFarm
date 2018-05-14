package apritree.compat;

public interface IExternalTick {
    boolean canTick();

    void doTick();
}
