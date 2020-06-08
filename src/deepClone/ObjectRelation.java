package deepClone;

public class ObjectRelation {
    private String parentUUID;
    private Class parentClass;
    private int parentHash;

    private String childUUID;
    private Class childClass;
    private int childHash;

    private int depth;
    private boolean isCopy;

    public ObjectRelation(String parent, String child, Class parentClass, Class childClass, int parentHash, int childHash, int depth, boolean isCopy) {
        this.parentUUID = parent;
        this.childUUID = child;
        this.depth = depth;
        this.parentClass = parentClass;
        this.childClass = childClass;
        this.isCopy = isCopy;
        this.parentHash = parentHash;
        this.childHash = childHash;
    }
}