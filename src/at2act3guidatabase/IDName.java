package at2act3guidatabase;

public class IDName {
    private int id;
    private static int count;
    private String name;
    
    IDName () {
        count++;
        this.id = count;
        this.name = "unknown";
    }
    IDName (String n) {
        count++;
        this.id = count;
        this.name = n;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
