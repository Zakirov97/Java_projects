public abstract class Middleware {
    private Middleware next;
    public Middleware linkWith(Middleware next){
        this.next = next;
        return next;
    }
    public abstract boolean parse(String txt);

    /**
     * Запускает проверку в следующем объекте или завершает проверку, если мы в
     * последнем элементе цепи.
     */
    protected boolean parseNext(String txt) {
        if (next == null) {
            return true;
        }
        return next.parse(txt);
    }
}
