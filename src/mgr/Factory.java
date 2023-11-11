package mgr;

public interface Factory<T extends Manageable> {
    public T create();

}
